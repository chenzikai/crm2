package com._520it.crm.web.controller;

import com._520it.crm.domain.Employee;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.EmployeeQueryObject;
import com._520it.crm.service.IEmployeeService;
import com._520it.crm.util.AjaxResult;
import com._520it.crm.util.RequiresName;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("")
    @RequiresPermissions("employee:index")
    @RequiresName("员工页面")
    public String  index(){
        return "employee";
    }

    @RequestMapping("list")
    @ResponseBody
    @RequiresPermissions("employee:list")
    @RequiresName("员工列表")
    public PageResult list(EmployeeQueryObject qo){
        PageResult result = employeeService.queryForPage(qo);
        return result;
    }
    @RequestMapping("save")
    @ResponseBody
    @RequiresPermissions("employee:save")
    @RequiresName("员工保存")
    public AjaxResult save(Employee employee){
        AjaxResult result = null;
        System.out.println("employee = " + employee);
        try {
            employeeService.insert(employee);
            result = new AjaxResult(true,"保存成功");
        }catch (Exception e){
            result = new AjaxResult(false,"保存失败");
            e.printStackTrace();

        }
        return result;
    }

    @RequestMapping("update")
    @ResponseBody
    @RequiresPermissions("employee:update")
    @RequiresName("员工更新")
    public AjaxResult update(Employee employee){
            AjaxResult result = null;
            try {
                employeeService.updateByPrimaryKey(employee);
                result = new AjaxResult(true,"更新成功");
            }catch (Exception e){
                result = new AjaxResult(false,"更新失败");
                e.printStackTrace();
            }
            return result;
    }
    @RequestMapping("quit")
    @ResponseBody
    @RequiresPermissions("employee:quit")
    @RequiresName("员工离职")
    public AjaxResult quit(Long id){
        AjaxResult result = null;
        try {
            employeeService.quit(id);
            result = new AjaxResult(true,"离职成功");
        }catch (Exception e){
            result = new AjaxResult(false,"离职失败");
            e.printStackTrace();
        }
        return result;
    }
    //excel文件的导出
    @RequestMapping("export")
    @ResponseBody
    public void export(HttpServletResponse response) throws Exception {

        //设置文件下载的响应头
        response.setHeader("Content-Disposition", "attachment;filename=a.xls");
        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
        //创建工作簿
        WritableSheet sheet = workbook.createSheet("day01", 0);
        //设置列标题
        sheet.addCell(new Label(0,0,"用户名"));
        sheet.addCell(new Label(1,0,"真实姓名"));
        sheet.addCell(new Label(2,0,"联系方式"));
        sheet.addCell(new Label(3,0,"邮箱"));
        //把员工数据填充到工作簿中
        List<Employee>list = employeeService.selectAll();
        for (int i = 0,j=1; i <list.size() ; i++,j++) {
            Employee employee = list.get(i);
            sheet.addCell(new Label(0,j,employee.getUsername()));
            sheet.addCell(new Label(1,j,employee.getRealname()));
            sheet.addCell(new Label(2,j,employee.getTel()));
            sheet.addCell(new Label(3,j,employee.getEmail()));
        }
        //写入数据
        workbook.write();
        //关闭资源
        workbook.close();
    }
    //Excel文件的导入
    @RequestMapping("importExcel")
    @ResponseBody
    public AjaxResult importExcel(MultipartFile file){
        AjaxResult result = null;
        try {
            //获取用户上传的文件
            Workbook workbook = Workbook.getWorkbook(file.getInputStream());
            //获取工作簿
            Sheet sheet = workbook.getSheet(0);
            //获取总行数
            int rows = sheet.getRows();
            for (int i = 1; i < rows ; i++) {
                Employee employee = new Employee();
                employee.setUsername(sheet.getCell(0,i).getContents());
                employee.setRealname(sheet.getCell(1,i).getContents());
                employee.setTel(sheet.getCell(2,i).getContents());
                employee.setEmail(sheet.getCell(3,i).getContents());
                //添加到数据库中
                employeeService.insert(employee);
            }
            //关闭资源
            workbook.close();
            result = new AjaxResult(true,"导入成功");
        }catch (Exception e){
            result = new AjaxResult(false,"导入失败");
            e.printStackTrace();
        }
        return result;
    }


}



