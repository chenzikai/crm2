package com._520it.crm.service;

import com._520it.crm.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringMybatisTest {

    @Autowired
    private  IEmployeeService employeeService;

    @Test
    public void saveTest(){
        Employee employee = new Employee();
        employee.setUsername("neld");
        employee.setPassword("d3333");
        employee.setEmail("fasjfl");
        //System.out.println(1/0);
        employeeService.insert(employee);
    }
}
