package com._520it.crm.service;

import com._520it.crm.domain.Department;

import java.util.List;

public interface IDepartmentService {

    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);
}
