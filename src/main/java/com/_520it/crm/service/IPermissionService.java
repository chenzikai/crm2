package com._520it.crm.service;

import com._520it.crm.domain.Permission;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.PermissionQueryObject;

import java.util.List;

public interface IPermissionService {

    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    PageResult queryForPage(PermissionQueryObject qo);

    void load();

    List<Permission> querySelfPermissionByRoleId(Long roleId);

    List<String> queryPermissionByEmployeeId(Long id);
}
