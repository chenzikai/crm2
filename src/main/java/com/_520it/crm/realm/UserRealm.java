package com._520it.crm.realm;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.Permission;
import com._520it.crm.domain.Role;
import com._520it.crm.service.IEmployeeService;
import com._520it.crm.service.IPermissionService;
import com._520it.crm.service.IRoleService;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Setter
    private IEmployeeService employeeService;
    @Setter
    private IRoleService roleService;
    @Setter
    private IPermissionService permissionService;
    @Override
    public String getName() {
        return "UserName";
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        Employee current = employeeService.queryByUsername(username);
        if (current==null){
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(current,current.getPassword(),getName());
        return info;
    }
    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录用户
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();
        List<String>roles = null;
        List<String>perms =null;
        if (employee.getAdmin()){
            //是超级管理员拥有所有角色和所用权限
            roles = new ArrayList<>();
            List<Role> roleList = roleService.selectAll();
            for (Role role :roleList) {
                roles.add(role.getSn());
            }
            perms = new ArrayList<>();
            perms.add("*:*");
        }else{
            //不是管理员拥有对应的角色和对应的权限
            roles = roleService.queryRoleByEmployeeId(employee.getId());
            perms = permissionService.queryPermissionByEmployeeId(employee.getId());

        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(perms);
        return info;
    }

}
