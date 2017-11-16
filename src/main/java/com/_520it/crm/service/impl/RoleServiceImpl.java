package com._520it.crm.service.impl;

import com._520it.crm.domain.Permission;
import com._520it.crm.domain.Role;
import com._520it.crm.mapper.RoleMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.RoleQueryObject;
import com._520it.crm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private  RoleMapper roleMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        //1.先删除中间表之间的关系
        roleMapper.deleteRelation(id);
        //2.在删除角色
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Role record) {
        //1.先保存角色数据
        int count = roleMapper.insert(record);
        //2.再保存中间表的关系
        List<Permission> permissions = record.getPermissions();
        for (Permission permission :permissions) {
            roleMapper.insertRelation(record.getId(),permission.getId());
        }
        return count;
    }


    @Override
    public Role selectByPrimaryKey(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        //1.先删除中间表之间的关系
        roleMapper.deleteRelation(record.getId());
        //2.保存角色和权限之间的关系
        List<Permission> permissions = record.getPermissions();
        for (Permission permission :permissions) {
            roleMapper.insertRelation(record.getId(),permission.getId());
        }
        return roleMapper.updateByPrimaryKey(record);
    }


    @Override
    public PageResult queryForPage(RoleQueryObject qo) {
        int count = roleMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(count, Collections.EMPTY_LIST);
        }
        List<Role> data = roleMapper.queryForData(qo);
        return new PageResult(count,data);
    }

    @Override
    public List<String> queryRoleByEmployeeId(Long id) {
        return roleMapper.queryRoleByEmployeeId(id);
    }

    @Override
    public List<Long> queryRoleIdsByEmployeeId(Long employeeId) {
        return roleMapper.queryRoleIdsByEmployeeId(employeeId);
    }
}
