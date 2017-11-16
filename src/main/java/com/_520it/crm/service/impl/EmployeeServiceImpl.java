package com._520it.crm.service.impl;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Role;
import com._520it.crm.mapper.EmployeeMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.EmployeeQueryObject;
import com._520it.crm.service.IEmployeeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Employee record) {
        record.setInputtime(new Date());
        record.setAdmin(false);
        record.setState(Employee.NORMAL);
        //1.保存员工
        int count = employeeMapper.insert(record);
        //2.在保存员工和角色之间中间表的关系
        for (Role role :record.getRoles()) {
            employeeMapper.insertRelation(record.getId(),role.getId());
        }
        return count;
    }



    @Override
    public Employee selectByPrimaryKey(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> selectAll() {
        return employeeMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Employee record) {
        //1.先删除中间表之间的关系
        employeeMapper.deleteRelation(record.getId());
        //2.插入中间表之间的关系
        for (Role role :record.getRoles()) {
            employeeMapper.insertRelation(record.getId(),role.getId());
        }
        return employeeMapper.updateByPrimaryKey(record);

    }

    @Override
    public PageResult queryForPage(EmployeeQueryObject qo) {
        Subject subject = SecurityUtils.getSubject();
        //判断当前登录用户的是否用有HR的角色
        if (!subject.hasRole("HR")){
                Employee currentUser = (Employee) subject.getPrincipal();
                qo.setCurrentUserId(currentUser.getId());
        }
        int count= employeeMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(count,Collections.EMPTY_LIST);
        }
        List<Employee> rows = employeeMapper.queryForData(qo);
        return new PageResult(count,rows);
    }

    @Override
    public void quit(Long id) {
       employeeMapper.quit(id,Employee.LEAVE);
    }

    @Override
    public Employee queryByUsername(String username) {
        return employeeMapper.queryByUsername(username);
    }

}
