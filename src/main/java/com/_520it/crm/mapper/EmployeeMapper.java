package com._520it.crm.mapper;

import com._520it.crm.domain.Employee;
import com._520it.crm.query.EmployeeQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    int queryForCount(EmployeeQueryObject qo);

    List<Employee> queryForData(EmployeeQueryObject qo);

    void quit(@Param("id") Long id, @Param("state") int state);

    void insertRelation(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);

    void deleteRelation(Long employeeId);

    Employee queryByUsername(String username);
}