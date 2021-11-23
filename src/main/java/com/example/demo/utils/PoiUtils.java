package com.example.demo.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerBorderImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author zhk
 * @Date 2021/11/19 22:18
 * @Version 1.0
 **/
public class PoiUtils {
    /**
     * Excel导出工具类
     * @param list 待转换实体集合
     * @param title 表单表头
     * @param sheetName sheet名称
     * @param pojoClass 实体的Class对象
     * @param fileName Excel文件名称
     * @param response 响应:Excel将放入响应的输出流中
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) throws Exception {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setStyle(ExcelExportStylerBorderImpl.class);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) throws Exception {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null)
            downLoadExcel(fileName, response, workbook);
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws Exception {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName+".xls", "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Excel导入工具类
     * @param file 上传的Excel文件
     * @param classType 待转化的Class对象
     * @param <T> T 方法独立
     * @return 转化完毕的对象集合
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> classType) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        return ExcelImportUtil.importExcel(file.getInputStream(), classType, params);
    }
}
