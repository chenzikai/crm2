$(function () {
    var empDatagrid,quitBtn,searchBtn,empDialog,empForm,empExcelImport;
     empDatagrid = $("#emp_datagrid")
     quitBtn = $('#del-btn,#edit-btn')
     searchBtn = $("#search-btn");
     empDialog = $('#emp_dialog');
     empForm = $('#emp_form');
     empExcelImport=$("#employee_excelImport");
     empDatagrid.datagrid({
        url:"employee/list",
        title:"员工列表",
        pagination:true,
        fit:true,
        fitColumns:true,
        toolbar:"#emp_tb",
        singleSelect:true,
        columns:[[
        {field:'username',title:'用户名',width:100,align:'center'},
        {field:'realname',title:'真实名称',width:100,align:'center'},
        {field:'tel',title:'联系方式',width:100,align:'right',align:'center'},
        {field:'email',title:'邮箱',width:100,align:'right',align:'center'},
        {field:'inputtime',title:'入职时间',width:100,align:'right',align:'center'},
        {field:'state',title:'状态',width:100,align:'right',align:'center',formatter:stateFormatter},
        {field:'admin',title:'是否管理员',width:100,align:'right',align:'center',formatter:adminFormatter},
        {field:'dept',title:'部门',width:100,align:'right',align:'center',formatter:deptFormatter},
         ]],
        onClickRow:function (index, row) {
            if(row.state==0){
                quitBtn.linkbutton('enable');
            }else{
                quitBtn.linkbutton('disable');
            }
        },
    });

    searchBtn.textbox({
        label:"关键字:",
        labelWidth:50,
        prompt:"请输入搜索关键字",
        buttonText:'搜索',
        buttonIcon:'icon-search',
        onClickButton:function () {
            var keyword = $("input[name='keyword']").val();
            empDatagrid.datagrid("load",{
                keyword:keyword,
            })
        }
    })

    empDialog.dialog({
        width: 300,
        height: 370,
        closed: true,
        buttons:"#emp_bb",
    });

    var cmdObj = {
        add: function () {
            //打开对话框
            empForm.form("clear")
            empDialog.dialog("open");
            empDialog.dialog("setTitle", "员工新增");
        },

        cancel: function () {
            empDialog.dialog("close");
        },
        refresh: function () {
            //清空高级查询的条件
            $("#search-btn").textbox("clear")
            //回到第一页
            empDatagrid.datagrid("load", {})
        },
        save: function () {
            var url;
            var idVal = $("[name='id']").val();
            if (idVal) {
                url = "employee/update"
            } else {
                url = "employee/save";
            }
            empForm.form('submit', {
                url: url,
                onSubmit:function(param){
                    //获取所有的角色信息
                    var roleIds = $("#role_combobox").combobox("getValues");
                    //把角色信息放入到表单中
                    for(var i=0;i<roleIds.length;i++){
                        param["roles["+i+"].id"] = roleIds[i];
                    }
                    return true;
                },
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {
                        $.messager.alert('温馨提示', data.msg, 'info', function () {
                            empDatagrid.datagrid("reload");
                            empDialog.dialog("close");
                        });
                    } else {
                        $.messager.alert("温馨提示", data.msg, "error");
                    }
                }
            });
        },
        edit: function () {
            //获取选中的数据
            var rowData = empDatagrid.datagrid("getSelected");
            console.log(rowData);
            if (rowData) {
                empDialog.dialog("open");
                empDialog.dialog("setTitle", "员工编辑");
                if (rowData.dept) {
                    rowData["dept.id"] = rowData.dept.id;
                }
                empForm.form("load", rowData);
                //角色数据的回显
                $.post("role/queryRoleIdsByEmployeeId?employeeId="+rowData.id,function (data) {
                    $("#role_combobox").combobox("setValues",data);
                })

            } else {
                $.messager.alert("警告", "请选择需要编辑的数据", "warning");
            }
        },
        del: function () {
            var rowData = empDatagrid.datagrid("getSelected");
            if (rowData) {
                $.messager.confirm("温馨提示", "您确定要离职该员工吗?", function (yes) {
                    if (yes) {
                        $.get("employee/quit?id=" + rowData.id, function (data) {
                            empDatagrid.datagrid("reload");
                            if (data.success) {
                                $.messager.alert("温馨提示", data.msg, "info");
                            } else {
                                $.messager.alert("温馨提示", data.msg, "error");
                            }
                        });
                    }
                })
            } else {
                $.messager.alert("警告", "请选择要离职的员工", "warning")
            }
        },
        //Excel文件的导出
        exportExcel: function () {
            window.location.href="employee/export";
        },
        import: function () {
            //打开对话框
            empExcelImport.dialog("open");
            empExcelImport.dialog("setTitle", "Excel文件导入");
        },
        excelcancel: function () {
            empExcelImport.dialog("close");
        },
        //Excel文件的保存
        excelsave:function () {
            $("#employee_excelForm").form("submit",{
                url:"employee/importExcel",
                success:function (data) {
                    console.log(data);
                    if (data.success){
                        //提示信息,当点击确定时候,关闭对话框,刷新数据表格
                        $.messager.alert("温馨提示",data.msg,"info",function () {
                            empDatagrid.datagrid("reload");
                        });
                        }else{
                           $.messager.alert("温馨提示",data.msg,"error");
                    }
                }
            })
        }
    }
   //excel文件的导入
    empExcelImport.dialog({
        width:280,
        height:120,
        buttons:"#emp_excel_bb",
        closed:true,
    })


  $("a[data-cmd]").on("click",function () {
      var cmd = $(this).data("cmd");
      cmdObj[cmd]();
  })
});

function stateFormatter(value,row,index) {
    return value==0?"<font color='green'>在职</font>":"<font color='red'>离职</font>";
}
function adminFormatter(value,row,index) {
    return value?"是":"否";
}
function deptFormatter(value,row,index) {
    return value?value.name:value;
}




























































