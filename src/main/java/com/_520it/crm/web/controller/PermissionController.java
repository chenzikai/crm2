package com._520it.crm.web.controller;

import com._520it.crm.domain.Permission;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.PermissionQueryObject;
import com._520it.crm.service.IPermissionService;
import com._520it.crm.util.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("")
    public String index(){
        return "permission";
    }

    @RequestMapping("list")
    @ResponseBody
    public PageResult list(PermissionQueryObject qo){
        PageResult result = permissionService.queryForPage(qo);
        return result;
    }

    @RequestMapping("load")
    @ResponseBody
    public AjaxResult load(){
        AjaxResult result = null;
        try {
             permissionService.load();
            result = new AjaxResult(true,"加载成功");
        }catch (Exception e){
            result = new AjaxResult(false,"加载失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("queryAllPermission")
    @ResponseBody
    public PageResult queryAllPermission(){
        List<Permission> allPermission = permissionService.selectAll();
        return new PageResult(allPermission.size(),allPermission);
    }
    @RequestMapping("querySelfPermissionByRoleId")
    @ResponseBody
    public  PageResult querySelfPermissionByRoleId(Long roleId){
        List<Permission>selfPermission = permissionService.querySelfPermissionByRoleId(roleId);
        return new PageResult(selfPermission.size(),selfPermission);
    }
}

