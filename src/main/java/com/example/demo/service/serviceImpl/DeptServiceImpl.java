package com.example.demo.service.serviceImpl;

import com.example.demo.entity.Dept;
import com.example.demo.mapper.DeptMapper;
import com.example.demo.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhk
 * @since 2021-11-20
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

}
