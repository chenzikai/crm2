package com._520it.crm.mapper;

import com._520it.crm.domain.Permission;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.PermissionQueryObject;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    int queryForCount(PermissionQueryObject qo);

    List<Permission> queryForData(PermissionQueryObject qo);

    List<Permission> querySelfPermissionByRoleId(Long roleId);

    List<String>queryPermissionByEmployeeId(Long id);
}