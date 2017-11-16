<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
        <%@ include file="/static/commons/common.jsp"%>
    <script type="text/javascript" src="/static/js/views/role.js"></script>
</head>
<body>
<table id="role_datagrid"></table>
<div id="role_tb">
    <div>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" data-cmd="add">新增</a>
        <a href="#" class="easyui-linkbutton" id="edit-btn" data-options="iconCls:'icon-edit',plain:true"
           data-cmd="edit">编辑</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" data-cmd="refresh">刷新</a>
    </div>

    <div>
        <input id="search-btn" type="text" name="keyword" style="width:220px">
    </div>

</div>

    <div id="role_bb">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" data-cmd="save">保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" data-cmd="cancel">返回</a>
    </div>



    <div id="role_dialog" align="center">
        <form id="role_form" method="post">
            <input type="hidden" name="id">
            <table align="center" style="margin-top: 15px">
                <tr>
                    <td><input name="name" class="easyui-textbox" data-options="label:'角色名称:',labelPosition:'left',width:230"></td>
                    <td><input name="sn" class="easyui-textbox" data-options="label:'角色编码:',labelPosition:'left',width:230"></td>
                </tr>
                <tr>
                    <td><table id="allPermissions"></table> </td>
                    <td><table id="selfPermissions"></table> </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>




