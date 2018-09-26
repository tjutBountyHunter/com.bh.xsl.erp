<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/21
  Time: 10:29
  To change this template use File | Settings | File Templates.
  Controller日志
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Controller 日志</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="../css/demo.css" rel="stylesheet" type="text/css" />
    <script src="../js/boot.js" type="text/javascript"></script>
    <script src="../js/miniui/locale/en_US.js" type="text/javascript"></script>
    <script src="../js/ColumnsMenu.js" type="text/javascript"></script>
</head>
<body>
<div class="mini-fit">
<div id="datagrid1" class="mini-datagrid" style="height:380px;"
     url="/log/controller/list" idField="id"
     allowResize="true" pageSize="10"
     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
     editNextOnEnterKey="true"  editNextRowCell="true"

>
    <div property="columns">
        <div type="indexcolumn"></div>
        <div type="checkcolumn"></div>
        <div name="id"  field="id" align="center" headerAlign="center" allowSort="true" width="150" >ID</div>
        <div name="ip"  field="ip" align="center" headerAlign="center" allowSort="true" width="150" >操作IP</div>
        <div name="operation"  field="operation" align="center" headerAlign="center" allowSort="true" width="150" >操作记录</div>
        <div name="operationer"  field="operationer" align="center" headerAlign="center" allowSort="true" width="150" >操作人员</div>
        <div name="operationtime" field="operationtime" align="center" width="150" allowSort="true" dateFormat="yyyy-MM-dd hh:mm:ss">操作时间</div>
    </div>
</div>
</div>
<script src="../js/operation.js" type="text/javascript"></script>
</body>
</html>
