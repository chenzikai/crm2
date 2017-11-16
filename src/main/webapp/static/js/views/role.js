$(function () {
    var roleDatagrid,searchBtn,roleDialog,roleForm;
     roleDatagrid = $("#role_datagrid")
     searchBtn = $("#search-btn");
     roleDialog = $('#role_dialog');
     roleForm = $('#role_form')
     roleDatagrid.datagrid({
        url:"role/list",
        title:"角色列表",
        pagination:true,
        fit:true,
        fitColumns:true,
        toolbar:"#role_tb",
        singleSelect:true,
        columns:[[
        {field:'name',title:'角色名称',width:100,align:'center'},
        {field:'sn',title:'角色编码',width:100,align:'center'},
         ]],
    });

    searchBtn.textbox({
        label:"关键字:",
        labelWidth:50,
        prompt:"请输入搜索关键字",
        buttonText:'搜索',
        buttonIcon:'icon-search',
        onClickButton:function () {
            var keyword = $("input[name='keyword']").val();
            roleDatagrid.datagrid("load",{
                keyword:keyword,
            })
        }
    })

    roleDialog.dialog({
        width: 600,
        height: 420,
        closed: true,
        buttons:"#role_bb",
    });
    var allData;
    //所有权限
   $("#allPermissions").datagrid({
        url:"permission/queryAllPermission",
        width:230,
        height:280,
        title:"所有权限",
        singleSelect:true,
        fitColumns:true,
        rownumbers:true,
        columns:[[
            {field:'name',width:100,align:"center"},
        ]],
       //在allPermission数据加载成功时候把数据先存在一个变量中
       onLoadSuccess:function (data) {
           allData = $.extend(true,{},data);
       },
       onDblClickRow:function (index, row) {
           $("#selfPermissions").datagrid("appendRow",row);
           $("#allPermissions").datagrid("deleteRow",index);
       },


    })
    //已有权限
    $("#selfPermissions").datagrid({
        width:230,
        height:280,
        title:"已有权限",
        singleSelect:true,
        fitColumns:true,
        rownumbers:true,
        columns:[[
            {field:'name',width:100,align:"center"},
        ]],
        onDblClickRow:function (index, row) {
            $("#allPermissions").datagrid("appendRow",row)
            $("#selfPermissions").datagrid("deleteRow",index);
        },
        //去重操作
        //1.在自身权限加载完毕之后,获取到自身权限的所有id集合
        onLoadSuccess:function (data) {
           var ids = $.map(data.rows,function (item) {
                return item.id;
            });
            console.log(data);
            console.log(ids);
            //2.获取到所有的权限集合
            var allPermissions = $("#allPermissions").datagrid("getRows");
            //3.循环遍历所有权限的每一行数据
            for(var i = allPermissions.length-1;i>=0;i--){
                if($.inArray(allPermissions[i].id,ids)>=0){
                    $("#allPermissions").datagrid("deleteRow",i);
                }
            }
        }


    })



    var cmdObj = {
        add: function () {
            //打开对话框
            roleForm.form("clear")
            //点击新增时,清空自身权限
            $("#selfPermissions").datagrid("loadData",{total:0,rows:[]});
            //重新加载之前存放好的所有权限的数据
            $("#allPermissions").datagrid("loadData",allData);
            roleDialog.dialog("open");
            roleDialog.dialog("setTitle", "角色新增");
        },

        cancel: function () {
            roleDialog.dialog("close");
        },
        refresh: function () {
            //清空高级查询的条件
            $("#search-btn").textbox("clear")
            //回到第一页
            roleDatagrid.datagrid("load", {})
        },
        save: function () {
            var url;
            var idVal = $("[name='id']").val();
            if (idVal) {
                url = "role/update"
            } else {
                url = "role/save";
            }
            roleForm.form('submit', {
                url: url,
                onSubmit:function (param) {
                    //1.获取自身权限的所有的数据
                    var rows = $("#selfPermissions").datagrid("getRows");
                    //2.将自身权限进行遍历,封装数据
                    for(var i = 0;i<rows.length;i++){
                        param["permissions["+i+"].id"] = rows[i].id;
                    }
                },
                success: function (data) {
                    data = $.parseJSON(data)
                    if (data.success) {
                        $.messager.alert('温馨提示', data.msg, 'info', function () {
                            roleDatagrid.datagrid("reload");
                            roleDialog.dialog("close");
                        });
                    } else {
                        $.messager.alert("温馨提示", data.msg, "error");
                    }
                }
            });
        },
        edit: function () {
            //获取选中的数据
            var rowData = roleDatagrid.datagrid("getSelected");
            if (rowData) {
                roleDialog.dialog("open");
                roleDialog.dialog("setTitle", "角色编辑");
            //重新加载之前存放好的所有权限的数据
            $("#allPermissions").datagrid("loadData",allData);
            //3.为自身权限设置url属性,发送ajax请求向后台查询自身权限的数据
                var options = $("#selfPermissions").datagrid("options");
                options.url="permission/querySelfPermissionByRoleId?roleId="+rowData.id;
                $("#selfPermissions").datagrid("load");
                if (rowData.dept) {
                    rowData["dept.id"] = rowData.dept.id;
                }
                roleForm.form("load", rowData);

            } else {
                $.messager.alert("警告", "请选择需要编辑的数据", "warning");
            }
        },
    }

  $("a[data-cmd]").on("click",function () {
      var cmd = $(this).data("cmd");
      cmdObj[cmd]();
  })
});



























































