package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.HttpStatus;
import com.example.demo.common.R;
import com.example.demo.entity.SpringScheduledCron;
import com.example.demo.exception.CustomExceptionHandler;
import com.example.demo.mapper.SpringScheduledCronMapper;
import com.example.demo.task.ScheduledOfTask;
import com.example.demo.utils.TaskUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 定时任务表 前端控制器
 * </p>
 *
 * @author zhk
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/scheduled")
@Validated
public class SpringScheduledCronController {

    @Resource
    private ApplicationContext context;
    @Resource
    private SpringScheduledCronMapper cronRepository;

    /**
     * 查看任务列表
     */
    @GetMapping("/taskList")
    @ApiOperation(value = "查看任务列表",notes = "查看任务列表")
    public List<SpringScheduledCron> taskList() {
        System.out.println(cronRepository.selectList(null).toString());
        return cronRepository.selectList(null);
    }
    /**
     * 编辑任务cron表达式
     */
    @PostMapping("/editTaskCron")
    @ApiOperation(value = "编辑任务cron表达式",notes = "编辑任务cron表达式")
    public R editTaskCron(@NotNull(message = "cron表达式id不能为空")Integer cronId,
                          @NotBlank(message = "cron表达式不能为空")String newCron) {
        if (!TaskUtils.isValidExpressionStr(newCron)) {
            throw new CustomExceptionHandler(HttpStatus.ERROR,"非法表达式:" + newCron);
        }
        SpringScheduledCron springScheduledCron = new SpringScheduledCron();
        springScheduledCron.setCronExpression(newCron);
        cronRepository.update(springScheduledCron,new QueryWrapper<SpringScheduledCron>().eq("cron_id",cronId));
        return R.ok(HttpStatus.SUCCESS, "更新成功");
    }

    /**
     * 新增定时任务
     */
    @PostMapping("/addTaskCron")
    @ApiOperation(value = "新增定时任务",notes = "新增定时任务")
    public R addTaskCron(@RequestBody @Validated SpringScheduledCron springScheduledCron) {
        if (!TaskUtils.isValidExpressionStr(springScheduledCron.getCronExpression())) {
            throw new CustomExceptionHandler(HttpStatus.ERROR,"非法表达式:" + springScheduledCron.getCronExpression());
        }
        cronRepository.insert(springScheduledCron);
        return R.ok(HttpStatus.SUCCESS, "新增成功");
    }

    /**
     * 执行定时任务
     */
    @PostMapping("/runTaskCron")
    @ApiOperation(value = "执行定时任务",notes = "执行定时任务")
    public R runTaskCron(@NotBlank(message = "定时任务完整类名不能为空")String cronKey) throws Exception {
        ((ScheduledOfTask) context.getBean(Class.forName(cronKey))).execute();
        return R.ok(HttpStatus.SUCCESS, "执行成功");
    }
    /**
     * 启用或禁用定时任务
     */
    @PostMapping("/changeStatusTaskCron")
    @ApiOperation(value = "启用或禁用定时任务",notes = "启用或禁用定时任务")
    public R changeStatusTaskCron(@NotNull(message = "状态不能为空") Integer status,
                                  @NotNull(message = "cron表达式id不能为空")Integer cronId) {
        SpringScheduledCron springScheduledCron = new SpringScheduledCron();
        springScheduledCron.setStatus(status);
        cronRepository.update(springScheduledCron,new QueryWrapper<SpringScheduledCron>().eq("cron_id",cronId));
        return R.ok(HttpStatus.SUCCESS, "操作成功");
    }
}

