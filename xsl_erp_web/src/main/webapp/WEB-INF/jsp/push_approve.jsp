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
push approve
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:380px;"
     url="push/list"
     idField="pushId" allowResize="true"
     sizeList="[20,30,50,100]" pageSize="10"
     ondrawcell="draw"
     onmouseup="return datagrid1_onmouseup()">
    <div property="columns">
        <div type="indexcolumn" ></div>
        <div name="pushId"  field="pushId" headerAlign="center" align="center" allowSort="true" width="100" >推送ID</div>
        <div name="msg_title" field="msg_title" width="150" allowSort="true" headerAlign="center"  >消息标题</div>
        <div name="msg_content" field="msg_content" width="100" headerAlign="center" align="center" allowSort="true" >消息内容</div>
        <div name="notification_content" field="notification_content" width="100"  headerAlign="center" align="center" allowSort="true">通知标题</div>
        <div name="approve" field="approve" width="120" headerAlign="center" align="center"  allowSort="true" >推送</div>
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
            e.cellHtml = '<div class="mini-button" iconcls="icon-add" onclick="success()">确认推送</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
        }
    }

    //通过审核
    function success(){
        var row = grid.getSelected();
        var id = row.pushId;
        var mtitle=row.msg_title;
        var mcontent=row.msg_content;
        var notifytitle=row.notification_content;
        //进行ajax数据操作
        $.ajax({
            url:"push/send",
            type:"post",
            data:JSON.stringify({pushId:id,msg_title:mtitle,msg_content:mcontent,notification_content:notifytitle}),
            contentType:"application/json;charset=UTF-8",
            dataType:"json",
            success:function(data){
                if(data ==  true){
                    mini.alert("推送成功！");
                    grid.reload();//重新加载数据
                }else{
                    mini.alert("推送失败,请联系后台人员！");
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
