package com.example.demo.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.example.demo.entity.Dept;
import com.example.demo.entity.User;
import com.example.demo.mapper.DeptMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.ExcelUtils;
import com.example.demo.utils.PoiUtils;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhk
 * @Date 2021/11/19 22:22
 * @Version 1.0
 **/
@RequestMapping(value = "/accidentInfo")
@RestController
public class EasyPoiController {

    @Resource
    UserMapper userMapper;
    @Resource
    UserService userService;
    @Resource
    DeptMapper deptMapper;

    /**序号*/
    Integer serialNumber = 1;

    /**导出*/
    @GetMapping(value = "/exportExcel")
    @ApiOperation(value = "导出事故信息")
    public void export(HttpServletResponse response){

        System.out.println("开始导出");
        // 模拟从数据库获取需要导出的数据 (偷懒，嘻嘻！)
        List<User> personList = userMapper.selectList(null);
        // 设置序号（将id字段作为序号，导出后实现序号递增）
        for (User user : personList) {
            user.setId(serialNumber++);
            Dept dept = deptMapper.selectById(user.getDeptId());
            user.setDeptName(dept.getDeptName());
            //serialNumber++;
        }
        // 导出操作
        ExcelUtils.exportExcel(personList, "easyPoi导出功能(用户表)", "导出sheet1", User.class, "测试User.xls", response);
    }


    @PostMapping("/importExcel")
    public String importExcel2(@RequestParam("file") MultipartFile file) {
        ImportParams importParams = new ImportParams();
        // 数据处理
        // 表格标题行数,默认0
        importParams.setHeadRows(1);
        // 表头行数,默认1
        importParams.setTitleRows(1);
        // 是否需要校验上传的Excel,默认false
        // importParams.setNeedVerfiy(false);

        try {
            ExcelImportResult<User> result = ExcelImportUtil.importExcelMore(file.getInputStream(), User.class, importParams);

        } catch (Exception ignored) {
        }
        return "导入成功";
    }

    @PostMapping(value = "/importAccidentInfo")
    @ApiOperation(value = "导入事故信息")
    public void importAccidentInfo(@RequestBody MultipartFile file) throws Exception {
        List<User> users = PoiUtils.importExcel(file, User.class);
        List<User> collect = users.stream().peek(user -> {
            user.setId(null);
            user.setCreateTime(null);
            user.setUpdateTime(null);
        }).collect(Collectors.toList());

        userService.saveBatch(collect);
    }
}
