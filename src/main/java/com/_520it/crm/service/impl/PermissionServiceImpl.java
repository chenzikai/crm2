package com._520it.crm.service.impl;

import com._520it.crm.domain.Permission;
import com._520it.crm.mapper.PermissionMapper;
import com._520it.crm.mapper.RoleMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.PermissionQueryObject;
import com._520it.crm.service.IPermissionService;
import com._520it.crm.util.RequiresName;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private  PermissionMapper permissionMapper;

    @Autowired
    private RequestMappingHandlerMapping rmhm;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Permission record) {
        return permissionMapper.insert(record);
    }

    @Override
    public Permission selectByPrimaryKey(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Permission> selectAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Permission record) {
        return permissionMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageResult queryForPage(PermissionQueryObject qo) {
        int count = permissionMapper.queryForCount(qo);
        if (count==0){
            return new PageResult(count, Collections.EMPTY_LIST);
        }
        List<Permission> data = permissionMapper.queryForData(qo);
        return new PageResult(count,data);
    }

    @Override
    public void load() {
        List<Permission> permissionList = permissionMapper.selectAll();
        Set<String> PermissionSet = new HashSet<>();
        for (Permission permission : permissionList) {
            PermissionSet.add(permission.getResource());
        }

        //1.获取所有控制器类中的方法
        Map<RequestMappingInfo, HandlerMethod> methods = rmhm.getHandlerMethods();
        //2.遍历所有方法,判断方法上是否贴有权限标签
        Collection<HandlerMethod> values = methods.values();
        for (HandlerMethod method : values) {
            RequiresPermissions methodAnnotation = method.getMethodAnnotation(RequiresPermissions.class);
            if (methodAnnotation != null) {
                String resource = methodAnnotation.value()[0];
                String name = method.getMethodAnnotation(RequiresName.class).value();
                if (!PermissionSet.contains(resource)) {
                    Permission p = new Permission();
                    p.setResource(resource);
                    p.setName(name);
                    permissionMapper.insert(p);
                }

            }
        }


    }

    @Override
    public List<Permission> querySelfPermissionByRoleId(Long roleId) {
        return permissionMapper.querySelfPermissionByRoleId(roleId);

    }

    @Override
    public List<String> queryPermissionByEmployeeId(Long id) {
        return permissionMapper.queryPermissionByEmployeeId(id);
    }
}



