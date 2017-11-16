package com._520it.crm.web.controller;

import com._520it.crm.domain.Role;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.RoleQueryObject;
import com._520it.crm.service.IRoleService;
import com._520it.crm.util.AjaxResult;
import com._520it.crm.util.RequiresName;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("")
    @RequiresPermissions("role:index")
    @RequiresName("角色页面")
    public String  index(){
        return "role";
    }

    @RequestMapping("list")
    @ResponseBody
    @RequiresPermissions("role:list")
    @RequiresName("角色列表")
    public PageResult list(RoleQueryObject qo){
        System.out.println("qo = " + qo);
        PageResult result = roleService.queryForPage(qo);
        return result;
    }
    @RequestMapping("save")
    @ResponseBody
    @RequiresPermissions("role:save")
    @RequiresName("角色保存")
    public AjaxResult save(Role role){
        AjaxResult result = null;
        System.out.println("role = " + role);
        try {
            roleService.insert(role);
            result = new AjaxResult(true,"保存成功");
        }catch (Exception e){
            result = new AjaxResult(false,"保存失败");
            e.printStackTrace();

        }
        return result;
    }

    @RequestMapping("update")
    @ResponseBody
    @RequiresPermissions("role:update")
    @RequiresName("角色更新")
    public AjaxResult update(Role role){
            AjaxResult result = null;
            try {
                roleService.updateByPrimaryKey(role);
                result = new AjaxResult(true,"更新成功");
            }catch (Exception e){
                result = new AjaxResult(false,"更新失败");
                e.printStackTrace();
            }
            return result;
    }
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Role> queryAllRoles(){
        List<Role> roles = roleService.selectAll();
        return roles;
    }
    @RequestMapping("queryRoleIdsByEmployeeId")
    @ResponseBody
    public List<Long>queryRoleIdsByEmployeeId(Long employeeId){
        List<Long> result = roleService.queryRoleIdsByEmployeeId(employeeId);
        return  result;
    }
}
