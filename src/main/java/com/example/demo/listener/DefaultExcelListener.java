package com.example.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 默认按模型读取的监听器
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.listener
 * @Author: Suellen
 * @CreateDate: 2021/12/27 10:08
 */
@Slf4j
public class DefaultExcelListener<T> extends AnalysisEventListener<T> {

    private List<T> rows = new ArrayList();

    /**
     * 批量导入
     */
    final static int IMPORT_COUNT = 200;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }

    @Override
    public void invoke(T object, AnalysisContext context) {
        rows.add(object);
        // 实际数据量比较大时，rows里的数据可以存到一定量之后进行批量处理（比如存到数据库），
        // 然后清空列表，以防止内存占用过多造成OOM
        //如数据过大，可以进行定量分批处理
        if(rows.size()>=IMPORT_COUNT){
            handleBusinessLogic(rows);
            rows.clear();
        }
        handleBusinessLogic(rows);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("读取的数据数量:{}", rows.size());
        log.info("读取的数据信息:{}", JSON.toJSONString(rows));
        System.out.println("导入的数据条数为: " + rows.size());
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
        }
    }

    /**
     * 根据业务自行实现该方法，例如将解析好的dataList存储到数据库中。
     */
    private void handleBusinessLogic(Object date) {

    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
