<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
        <%@ include file="/static/commons/common.jsp"%>
        <script type="text/javascript" src="/static/js/views/employee.js"></script>
</head>
<body>
<table id="emp_datagrid"></table>
<div id="emp_tb">
    <div>
        <shiro:hasPermission name="employee:save">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" data-cmd="add">新增</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="employee:update">
        <a href="#" class="easyui-linkbutton" id="edit-btn" data-options="iconCls:'icon-edit',plain:true"
           data-cmd="edit">编辑</a>
        </shiro:hasPermission>

        <a href="#" class="easyui-linkbutton" id="del-btn" data-options="iconCls:'icon-remove',plain:true"
           data-cmd="del">离职</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" data-cmd="refresh">刷新</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-export',plain:true" data-cmd="exportExcel">Excel导出</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" data-cmd="import">Excel导入</a>
    </div>
    <%--文件上传对话框--%>
    <div id="employee_excelImport">
        <form method="post" id="employee_excelForm" enctype="multipart/form-data">
            <table>
                <tbody>
                <tr>
                    <td><input type="file" name="excel"></td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>

    <div>
        <input id="search-btn" type="text" name="keyword" style="width:220px">
    </div>

</div>

    <div id="emp_bb">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" data-cmd="save">保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" data-cmd="cancel">返回</a>
    </div>
    <div id="emp_excel_bb">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" data-cmd="excelsave">保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" data-cmd="excelcancel">取消</a>
    </div>



    <div id="emp_dialog" align="center">
        <form id="emp_form" method="post">
            <input type="hidden" name="id">
            <div>
                <input type="text" name="username" class="easyui-textbox" data-options="label:'用户名:',labelPosition:'top'">
            </div>
            <div>
                <input type="text" name="realname" class="easyui-textbox" data-options="label:'真实名称:',labelPosition:'top'">
            </div>
            <div>
                <input type="text" name="tel" class="easyui-textbox" data-options="label:'联系方式:',labelPosition:'top'">
            </div>
            <div>
                <input type="text" name="email" class="easyui-textbox" data-options="label:'邮箱:',labelPosition:'top'">
            </div>
            <div>
                   <input id="emp_combobox" class="easyui-combobox" name="dept.id" data-options="
                        valueField: 'id',
                        textField: 'name',
                        url: 'department/list',
                        label:'所属部门:',
                        panelHeight:'auto',
                        labelPosition:'top'"
                     />
            </div>
            <div>
                   <input id="role_combobox" class="easyui-combobox"  data-options="
                        valueField: 'id',
                        textField: 'name',
                        url: 'role/queryAllRoles',
                        label:'拥有的角色',
                        panelHeight:'auto',
                        labelPosition:'top'"
                     />
            </div>
        </form>
    </div>
</body>
</html>




