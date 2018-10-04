<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/19
  Time: 9:30
  To change this template use File | Settings | File Templates.
  任务审计页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=basePath%>css/demo.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/boot.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ColumnsMenu.js" type="text/javascript"></script>

</head>
<body >
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:380px;"
     url="task/approve/list"
     idField="id" allowResize="true"
     sizeList="[20,30,50,100]" pageSize="10"
     ondrawcell="draw"
     onmouseup="return datagrid1_onmouseup()">
    <div property="columns">
        <div type="indexcolumn" ></div>
        <div name="id"  field="id" headerAlign="center" align="center" allowSort="true" width="100" >任务ID</div>
        <div field="descr" width="150" allowSort="true" headerAlign="center"  >任务描述</div>
        <div name="sendid" field="sendid" width="100" headerAlign="center" align="center" allowSort="true" >发送者ID</div>
        <div field="money" width="100"  headerAlign="center" align="center" allowSort="true">赏金</div>
        <div name="createdate" field="createdate" headerAlign="center" align="center" width="100" allowSort="true" dateFormat="yyyy-MM-dd">创建日期</div>
        <div name="approve" field="approve" width="120" headerAlign="center" align="center"  allowSort="true" >审核</div>

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
            url:"task/approve/approve",
            type:"post",
            data:JSON.stringify({id:id,state:0}),
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
        var json = JSON.stringify({"id":id,"state":"-4"});
        alert(json);
        //
        $.ajax({
            url:"task/approve/approve",
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
