package com.example.demo.utils;

import cn.hutool.core.convert.Convert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.example.demo.common.HttpStatus;
import com.example.demo.listener.DefaultExcelListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: EasyExcel工具类
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.utils
 * @Author: Suellen
 * @CreateDate: 2021/12/27 10:00
 */
public class EasyExcelUtils {

    /**
     * 同步无模型读（默认读取sheet0,从第2行开始读）
     * @param filePath
     * @return
     */
    public static List<Map<Integer, String>> syncRead(String filePath){
        return EasyExcelFactory.read(filePath).sheet().doReadSync();
    }

    /**
     * 同步无模型读（默认表头占一行，从第2行开始读）
     * @param filePath
     * @param sheetNo sheet页号，从0开始
     * @return
     */
    public static List<Map<Integer, String>> syncRead(String filePath, Integer sheetNo){
        return EasyExcelFactory.read(filePath).sheet(sheetNo).doReadSync();
    }

    /**
     * 同步无模型读（指定sheet和表头占的行数）
     * @param inputStream
     * @param sheetNo sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return List<Map<colNum, cellValue>>
     */
    public static List<Map<Integer, String>> syncRead(InputStream inputStream, Integer sheetNo, Integer headRowNum){
        return EasyExcelFactory.read(inputStream).sheet(sheetNo).headRowNumber(headRowNum).doReadSync();
    }

    /**
     * 同步无模型读（指定sheet和表头占的行数）
     * @param file
     * @param sheetNo sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return List<Map<colNum, cellValue>>
     */
    public static List<Map<Integer, String>> syncRead(File file, Integer sheetNo, Integer headRowNum){
        return EasyExcelFactory.read(file).sheet(sheetNo).headRowNumber(headRowNum).doReadSync();
    }

    /**
     * 同步无模型读（指定sheet和表头占的行数）
     * @param filePath
     * @param sheetNo sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return List<Map<colNum, cellValue>>
     */
    public static List<Map<Integer, String>> syncRead(String filePath, Integer sheetNo, Integer headRowNum){
        return EasyExcelFactory.read(filePath).sheet(sheetNo).headRowNumber(headRowNum).doReadSync();
    }

    /**
     * 同步按模型读（默认读取sheet0,从第2行开始读）
     * @param filePath
     * @param clazz 模型的类类型（excel数据会按该类型转换成对象）
     * @return
     */
    public static List<T> syncReadModel(String filePath, Class clazz){
        return EasyExcelFactory.read(filePath).sheet().head(clazz).doReadSync();
    }

    /**
     * 同步按模型读（默认表头占一行，从第2行开始读）
     * @param filePath
     * @param clazz 模型的类类型（excel数据会按该类型转换成对象）
     * @param sheetNo sheet页号，从0开始
     * @return
     */
    public static List<T> syncReadModel(String filePath, Class clazz, Integer sheetNo){
        return EasyExcelFactory.read(filePath).sheet(sheetNo).head(clazz).doReadSync();
    }

    /**
     * 同步按模型读（指定sheet和表头占的行数）
     * @param inputStream
     * @param clazz 模型的类类型（excel数据会按该类型转换成对象）
     * @param sheetNo sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return
     */
    public static List<T> syncReadModel(InputStream inputStream, Class clazz, Integer sheetNo, Integer headRowNum){
        return EasyExcelFactory.read(inputStream).sheet(sheetNo).headRowNumber(headRowNum).head(clazz).doReadSync();
    }

    /**
     * 同步按模型读（指定sheet和表头占的行数）
     * @param file
     * @param clazz 模型的类类型（excel数据会按该类型转换成对象）
     * @param sheetNo sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return
     */
    public static List<T> syncReadModel(File file, Class clazz, Integer sheetNo, Integer headRowNum){
        return EasyExcelFactory.read(file).sheet(sheetNo).headRowNumber(headRowNum).head(clazz).doReadSync();
    }

    /**
     * 同步按模型读（指定sheet和表头占的行数）
     * @param filePath
     * @param clazz 模型的类类型（excel数据会按该类型转换成对象）
     * @param sheetNo sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return
     */
    public static List<T> syncReadModel(String filePath, Class clazz, Integer sheetNo, Integer headRowNum){
        return EasyExcelFactory.read(filePath).sheet(sheetNo).headRowNumber(headRowNum).head(clazz).doReadSync();
    }

    /**
     * 异步无模型读（默认读取sheet0,从第2行开始读）
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param filePath 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return
     */
    public static void asyncRead(String filePath, AnalysisEventListener<T> excelListener){
        EasyExcelFactory.read(filePath, excelListener).sheet().doRead();
    }

    /**
     * 异步无模型读（默认表头占一行，从第2行开始读）
     * @param filePath 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param sheetNo sheet页号，从0开始
     * @return
     */
    public static void asyncRead(String filePath, AnalysisEventListener<T> excelListener, Integer sheetNo){
        EasyExcelFactory.read(filePath, excelListener).sheet(sheetNo).doRead();
    }

    /**
     * 异步无模型读（指定sheet和表头占的行数）
     * @param inputStream
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param sheetNo sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return
     */
    public static void asyncRead(InputStream inputStream, AnalysisEventListener<T> excelListener, Integer sheetNo, Integer headRowNum){
        EasyExcelFactory.read(inputStream, excelListener).sheet(sheetNo).headRowNumber(headRowNum).doRead();
    }

    /**
     * 异步无模型读（指定sheet和表头占的行数）
     * @param file
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param sheetNo sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return
     */
    public static void asyncRead(File file, AnalysisEventListener<T> excelListener, Integer sheetNo, Integer headRowNum){
        EasyExcelFactory.read(file, excelListener).sheet(sheetNo).headRowNumber(headRowNum).doRead();
    }

    /**
     * 异步无模型读（指定sheet和表头占的行数）
     * @param filePath
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param sheetNo sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     * @return
     */
    public static void asyncRead(String filePath, AnalysisEventListener<T> excelListener, Integer sheetNo, Integer headRowNum){
        EasyExcelFactory.read(filePath, excelListener).sheet(sheetNo).headRowNumber(headRowNum).doRead();
    }

    /**
     * 异步按模型读取（默认读取sheet0,从第2行开始读）
     * @param filePath
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param clazz 模型的类类型（excel数据会按该类型转换成对象）
     */
    public static void asyncReadModel(String filePath, AnalysisEventListener<T> excelListener, Class clazz){
        EasyExcelFactory.read(filePath, clazz, excelListener).sheet().doRead();
    }

    /**
     * 异步按模型读取（默认表头占一行，从第2行开始读）
     * @param filePath
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param clazz 模型的类类型（excel数据会按该类型转换成对象）
     * @param sheetNo  sheet页号，从0开始
     */
    public static void asyncReadModel(String filePath, AnalysisEventListener<T> excelListener, Class clazz, Integer sheetNo){
        EasyExcelFactory.read(filePath, clazz, excelListener).sheet(sheetNo).doRead();
    }

    /**
     * 异步按模型读取
     * @param inputStream
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param clazz 模型的类类型（excel数据会按该类型转换成对象）
     * @param sheetNo  sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     */
    public static void asyncReadModel(InputStream inputStream, AnalysisEventListener<T> excelListener, Class clazz, Integer sheetNo, Integer headRowNum){
        EasyExcelFactory.read(inputStream, clazz, excelListener).sheet(sheetNo).headRowNumber(headRowNum).doRead();
    }

    /**
     * 异步按模型读取
     * @param file
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param clazz 模型的类类型（excel数据会按该类型转换成对象）
     * @param sheetNo  sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     */
    public static void asyncReadModel(File file, AnalysisEventListener<T> excelListener, Class clazz, Integer sheetNo, Integer headRowNum){
        EasyExcelFactory.read(file, clazz, excelListener).sheet(sheetNo).headRowNumber(headRowNum).doRead();
    }

    /**
     * 异步按模型读取
     * @param filePath
     * @param excelListener 监听器，在监听器中可以处理行数据LinkedHashMap，表头数据，异常处理等
     * @param clazz 模型的类类型（excel数据会按该类型转换成对象）
     * @param sheetNo  sheet页号，从0开始
     * @param headRowNum 表头占的行数，从0开始（如果要连表头一起读出来则传0）
     */
    public static void asyncReadModel(String filePath, AnalysisEventListener<T> excelListener, Class clazz, Integer sheetNo, Integer headRowNum){
        EasyExcelFactory.read(filePath, clazz, excelListener).sheet(sheetNo).headRowNumber(headRowNum).doRead();
    }

    /**
     * 无模板写文件
     * @param filePath
     * @param head 表头数据
     * @param data 表内容数据
     */
    public static void write(String filePath, List<List<String>> head, List<List<Object>> data){
        EasyExcel.write(filePath).head(head).sheet().doWrite(data);
    }

    /**
     * 无模板写文件
     * @param filePath
     * @param head 表头数据
     * @param data 表内容数据
     * @param sheetNo sheet页号，从0开始
     * @param sheetName sheet名称
     */
    public static void write(String filePath, List<List<String>> head, List<List<Object>> data, Integer sheetNo, String sheetName){
        EasyExcel.write(filePath).head(head).sheet(sheetNo, sheetName).doWrite(data);
    }

    /**
     * 根据excel模板文件写入文件
     * @param filePath
     * @param templateFileName
     * @param headClazz
     * @param data
     */
    public static void writeTemplate(String filePath, String templateFileName, Class headClazz, List data){
        EasyExcel.write(filePath, headClazz).withTemplate(templateFileName).sheet().doWrite(data);
    }

    /**
     * 根据excel模板文件写入文件
     * @param filePath
     * @param templateFileName
     * @param data
     */
    public static void writeTemplate(String filePath, String templateFileName, List data){
        EasyExcel.write(filePath).withTemplate(templateFileName).sheet().doWrite(data);
    }

    /**
     * 按模板写文件
     * @param filePath
     * @param headClazz 表头模板
     * @param data 数据
     */
    public static void write(String filePath, Class headClazz, List data){
        EasyExcel.write(filePath, headClazz).sheet().doWrite(data);
    }

    /**
     * 按模板写文件
     * @param filePath
     * @param headClazz 表头模板
     * @param data 数据
     * @param sheetNo sheet页号，从0开始
     * @param sheetName sheet名称
     */
    public static void write(String filePath, Class headClazz, List data, Integer sheetNo, String sheetName){
        EasyExcel.write(filePath, headClazz).sheet(sheetNo, sheetName).doWrite(data);
    }

    /**
     * 按模板写文件
     * @param filePath
     * @param headClazz 表头模板
     * @param data 数据
     * @param writeHandler 自定义的处理器，比如设置table样式，设置超链接、单元格下拉框等等功能都可以通过这个实现（需要注册多个则自己通过链式去调用）
     * @param sheetNo sheet页号，从0开始
     * @param sheetName sheet名称
     */
    public static void write(String filePath, Class headClazz, List data, WriteHandler writeHandler, Integer sheetNo, String sheetName){
        EasyExcel.write(filePath, headClazz).registerWriteHandler(writeHandler).sheet(sheetNo, sheetName).doWrite(data);
    }

    /**
     * 按模板写文件（包含某些字段）
     * @param filePath
     * @param headClazz 表头模板
     * @param data 数据
     * @param includeCols 过滤包含的字段，根据字段名称过滤
     * @param sheetNo sheet页号，从0开始
     * @param sheetName sheet名称
     */
    public static void writeInclude(String filePath, Class headClazz, List data, Set<String> includeCols, Integer sheetNo, String sheetName){
        EasyExcel.write(filePath, headClazz).includeColumnFiledNames(includeCols).sheet(sheetNo, sheetName).doWrite(data);
    }

    /**
     * 按模板写文件（排除某些字段）
     * @param filePath
     * @param headClazz 表头模板
     * @param data 数据
     * @param excludeCols 过滤排除的字段，根据字段名称过滤
     * @param sheetNo sheet页号，从0开始
     * @param sheetName sheet名称
     */
    public static void writeExclude(String filePath, Class headClazz, List data, Set<String> excludeCols, Integer sheetNo, String sheetName){
        EasyExcel.write(filePath, headClazz).excludeColumnFiledNames(excludeCols).sheet(sheetNo, sheetName).doWrite(data);
    }

    /**
     * 多个sheet页的数据链式写入
     * ExcelUtil.writeWithSheets(outputStream)
     *                 .writeModel(ExcelModel.class, excelModelList, "sheetName1")
     *                 .write(headData, data,"sheetName2")
     *                 .finish();
     * @param outputStream
     * @return
     */
    public static EasyExcelWriterFactory writeWithSheets(OutputStream outputStream){
        EasyExcelWriterFactory excelWriter = new EasyExcelWriterFactory(outputStream);
        return excelWriter;
    }

    /**
     * 多个sheet页的数据链式写入
     * ExcelUtil.writeWithSheets(file)
     *                 .writeModel(ExcelModel.class, excelModelList, "sheetName1")
     *                 .write(headData, data,"sheetName2")
     *                 .finish();
     * @param file
     * @return
     */
    public static EasyExcelWriterFactory writeWithSheets(File file){
        EasyExcelWriterFactory excelWriter = new EasyExcelWriterFactory(file);
        return excelWriter;
    }

    /**
     * 多个sheet页的数据链式写入
     * ExcelUtil.writeWithSheets(filePath)
     *                 .writeModel(ExcelModel.class, excelModelList, "sheetName1")
     *                 .write(headData, data,"sheetName2")
     *                 .finish();
     * @param filePath
     * @return
     */
    public static EasyExcelWriterFactory writeWithSheets(String filePath){
        EasyExcelWriterFactory excelWriter = new EasyExcelWriterFactory(filePath);
        return excelWriter;
    }

    /**
     * 多个sheet页的数据链式写入（失败了会返回一个有部分数据的Excel）
     * ExcelUtil.writeWithSheets(response, exportFileName)
     *                 .writeModel(ExcelModel.class, excelModelList, "sheetName1")
     *                 .write(headData, data,"sheetName2")
     *                 .finish();
     * @param response
     * @param exportFileName 导出的文件名称
     * @return
     */
    public static EasyExcelWriterFactory writeWithSheetsWeb(HttpServletResponse response, String exportFileName) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode(exportFileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcelWriterFactory excelWriter = new EasyExcelWriterFactory(response.getOutputStream());
        return excelWriter;
    }




    /** ===================================================================================================== **/
    /**
     *  通用导出
     * @param list 数据集合
     * @param response
     * @param clazz 对象类型  *.class
     */
    public static void fileExport(List<?> list, HttpServletResponse response,  Class<?> clazz) {
        // 流方式返回 可自定义fileName和表头名
        try {
            writeExcel(response, list, System.currentTimeMillis() + "", System.currentTimeMillis() + "", clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出
     *
     * @param response
     * @param data
     * @param fileName
     * @param sheetName
     * @param clazz
     * @throws Exception
     */
    public static void writeExcel(HttpServletResponse response, List<? extends Object> data, String fileName, String sheetName, Class clazz) throws Exception {
        if(sheetName == null || "".equals(sheetName)){
            sheetName = "sheet1";
        }
        //表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置内容靠左对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy).doWrite(data);
    }

    /**
     * 流方式响应给前端
     * @param fileName
     * @param response
     * @return
     * @throws Exception
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setStatus(HttpStatus.SUCCESS);
        return response.getOutputStream();
    }

    /**
     * 读取Excel（多个sheet可以用同一个实体类解析）
     * @param excelInputStream
     * @param fileName
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> readExcel(InputStream excelInputStream, String fileName,Class<T> clazz) {
        DefaultExcelListener excelListener = new DefaultExcelListener();
        ExcelReader excelReader = getReader(excelInputStream, fileName,clazz, excelListener);
        if (excelReader == null) {
            return new ArrayList<>();
        }
        List<ReadSheet> readSheetList = excelReader.excelExecutor().sheetList();
        for (ReadSheet readSheet : readSheetList) {
            excelReader.read(readSheet);
        }
        excelReader.finish();
        return Convert.toList(clazz, excelListener.getRows());
    }

    /**
     * 返回ExcelReader
     *
     * @param clazz         实体类
     * @param excelListener
     */
    private static <T> ExcelReader getReader(InputStream inputStream, String filename, Class<T> clazz, DefaultExcelListener excelListener) {
        try {
            if (filename == null ||
                    (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
                return null;
            }
            ExcelReader excelReader = EasyExcel.read(inputStream, clazz, excelListener).build();
            inputStream.close();
            return excelReader;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

