package com._520it.crm.mapper;

import com._520it.crm.domain.Role;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.RoleQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    int queryForCount(RoleQueryObject qo);

    List<Role> queryForData(RoleQueryObject qo);

    void insertRelation( @Param("roleId") Long roleId,@Param("permissionId") Long permissionId);

    void deleteRelation(Long id);

    List<String> queryRoleByEmployeeId(Long employeeId);

    List<Long> queryRoleIdsByEmployeeId(Long employeeId);
}