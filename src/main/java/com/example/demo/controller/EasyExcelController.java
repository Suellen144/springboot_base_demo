package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.SysUser;
import com.example.demo.service.SysUserService;
import com.example.demo.utils.EasyExcelUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Description: EasyExcel控制层
 * @ProjectName: springboot-base-demo
 * @Package: com.example.demo.controller
 * @Author: Suellen
 * @CreateDate: 2021/12/27 10:49
 */

@RequestMapping(value = "/info")
@RestController
@Slf4j
public class EasyExcelController {

    @Resource
    SysUserService sysUserService;
    /**序号*/
    Integer serialNumber = 1;

    /**导出*/
    @GetMapping(value = "/easyExcel")
    @ApiOperation(value = "导出")
    public void export(HttpServletResponse response){
        try {
            /**序号*/
            AtomicInteger atomicInteger = new AtomicInteger(1);
            System.out.println("开始导出");
            log.info("开始导出");
            Object data = sysUserService.findUserAll().get("data");
            String jsonString = JSON.toJSONString(data);
            List<SysUser> sysUsers = JSON.parseArray(jsonString, SysUser.class);
            //EasyExcelUtil.write("E:/文件/测试导出Excel",null,sysUsers,0,"用户信息");
            // 根据创建时间排序(降序)
           /* List<SysUser> sysUserList = sysUsers.stream().sorted(Comparator.comparing(SysUser::getCreateTime)
                    .reversed()).collect(Collectors.toList());*/
            // 根据创建时间排序(升序)
            List<SysUser> sysUserList = sysUsers.stream().sorted(Comparator.comparing(SysUser::getCreateTime))
                    .peek(sysUser -> {
                        sysUser.setUserId(atomicInteger.toString());
                        atomicInteger.incrementAndGet();
                    }).collect(Collectors.toList());

            /*for (SysUser sysUser : sysUserList) {
                sysUser.setUserId(atomicInteger.toString());
                atomicInteger.incrementAndGet();
            }*/
            EasyExcelUtils.writeExcel(response,sysUserList,"用户信息","用户信息1",SysUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**导入*/
    @PostMapping(value = "/importExcel")
    @ApiOperation(value = "导入")
    public List<SysUser> importExcel(@RequestBody MultipartFile file) throws IOException {
        System.out.println("开始导入");
        log.info("开始导入");
        return EasyExcelUtils.readExcel(file.getInputStream(),file.getOriginalFilename(),SysUser.class);
    }
}
