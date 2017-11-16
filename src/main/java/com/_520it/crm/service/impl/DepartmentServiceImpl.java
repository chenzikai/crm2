package com._520it.crm.service.impl;

import com._520it.crm.domain.Department;
import com._520it.crm.mapper.DepartmentMapper;
import com._520it.crm.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private  DepartmentMapper departmentMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Department record) {
        return departmentMapper.insert(record);
    }

    @Override
    public Department selectByPrimaryKey(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Department> selectAll() {
        return departmentMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Department record) {
        return departmentMapper.updateByPrimaryKey(record);
    }
}
