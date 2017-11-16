$(function () {
    var permDatagrid;
     permDatagrid = $("#perm_datagrid")
     permDatagrid.datagrid({
        url:"permission/list",
        title:"权限列表",
        pagination:true,
        fit:true,
        fitColumns:true,
        toolbar:"#perm_tb",
        singleSelect:true,
         rownumbers:true,
        columns:[[
        {field:'name',title:'权限名称',width:100,align:'center'},
        {field:'resource',title:'表达式',width:100,align:'center'},
         ]],
    });

    var cmdObj = {

        refresh: function () {
                $.messager.confirm("温馨提示", "您确定重新要加载权限吗?", function (yes) {
                    if (yes) {
                        $.get("permission/load", function (data) {
                            permDatagrid.datagrid("reload");
                            if (data.success) {
                                $.messager.alert("温馨提示", data.msg, "info");
                            } else {
                                $.messager.alert("温馨提示", data.msg, "error");
                            }
                        });
                    }
                })
        },
    }
  $("a[data-cmd]").on("click",function () {
      var cmd = $(this).data("cmd");
      cmdObj[cmd]();
  })
});




























































