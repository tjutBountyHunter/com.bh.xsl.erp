<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/16
  Time: 11:10
  To change this template use File | Settings | File Templates.
  用户审计页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="../css/demo.css" rel="stylesheet" type="text/css" />
    <script src="../js/boot.js" type="text/javascript"></script>
    <script src="../js/ColumnsMenu.js" type="text/javascript"></script>

</head>
<body >
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:380px;"
     url="/user/approve/list"
     idField="id" allowResize="true"
     sizeList="[20,30,50,100]" pageSize="10"
     ondrawcell="draw">
    <div property="columns">
        <div type="indexcolumn"></div>
        <div field="id" width="120" headerAlign="center" align="center" allowSort="true">ID</div>
        <div field="name" width="120" headerAlign="center" align="center"  allowSort="true">昵称</div>
        <div field="phone" width="100" headerAlign="center" align="center" headerAlign="center">电话</div>
        <div field="email" align="center" headerAlign="center" width="100" allowSort="true">邮箱</div>
        <div name="createdate" field="createdate" width="100" align="center" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd">申请日期</div>
        <div name="approve" field="approve" width="120" headerAlign="center"  allowSort="true" align="center" >审核</div>
    </div>
</div>

<script type="text/javascript">

    mini.parse();

    var grid = mini.get("datagrid1");

    grid.load();

    //为每一行添加 同意和拒绝按钮
    function draw(e) {
        var record = e.record;
        var column = e.column;
        if (column.name == "approve") {
            e.cellHtml = '<div class="mini-button" iconcls="icon-add" onclick="success()">同意</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<div  class="mini-button" iconcls="icon-remove" onclick="unsuccess()">拒绝</div>'
        }
    }

    //通过审核
    function success(){
        var row = grid.getSelected();
        var id = row.id;
        //进行ajax数据操作
        $.ajax({
            url:"/user/approve/approve",
            type:"post",
            data:JSON.stringify({id:id,state:1}),
            contentType:"application/json;charset=UTF-8",
            dataType:"json",
            success:function(data){
                if(data ==  true){
                    mini.alert("审核通过！");
                    grid.reload();//重新加载数据
                }else{
                    mini.alert("审核失败,请重新审核！");
                }
            },
            error : function(errorMsg) { //失败操作
                //请求失败时执行该函数
                mini.alert(errorMsg);
            }
        });
    }

    //未通过审核
    function unsuccess(){
        var row = grid.getSelected();
        var id = row.id;
        var json = JSON.stringify({"id":id,"state":"-2"});
        alert(json);
        //
        $.ajax({
            url:"/user/approve/approve",
            type:"post",
            data:json,
            contentType:"application/json;charset=UTF-8",
            dataType:"json",
            success:function(data){
                if(data ==  true){
                    mini.alert("未通过审核！");
                    grid.reload();//重新加载数据
                }else{
                    mini.alert("审核失败,请重新审核！");
                }
            },
            error : function(errorMsg) { //失败操作
                //请求失败时执行该函数
                mini.alert(errorMsg);
            }
        });
    }

</script>
</body>
</html>
