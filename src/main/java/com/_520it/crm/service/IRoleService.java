package com._520it.crm.service;

import com._520it.crm.domain.Role;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.RoleQueryObject;

import java.util.List;

public interface IRoleService {

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    PageResult queryForPage(RoleQueryObject qo);

    List<String> queryRoleByEmployeeId(Long id);

    List<Long> queryRoleIdsByEmployeeId(Long employeeId);
}
