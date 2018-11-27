<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/16
  Time: 11:10
  To change this template use File | Settings | File Templates.
  资金流入账单管理页面
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>"/>
    <title>猎人管理 </title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=basePath%>css/demo.css" rel="stylesheet" type="text/css" />

    <script src="<%=basePath%>js/boot.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/miniui/locale/en_US.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ColumnsMenu.js" type="text/javascript"></script>

</head>
<body>
<div class="mini-fit">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-save" onclick="saveData('user/hunter')" plain="true">保存</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="key" class="mini-textbox" emptyText="请输入流入账单ID" style="width:150px;" onenter="onKeyEnter"/>
                    <input id="key1" class="mini-textbox" emptyText="请输入流入账单金额" style="width:150px;" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
<div id="datagrid1" class="mini-datagrid" style="height:380px;"
     url="<%=basePath%>bill/inputBill/list?page=1&rows=10" idField="id"
     allowResize="true" pageSize="10"
     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
     editNextOnEnterKey="true"  editNextRowCell="true"

>
    <div property="columns">
        <div type="indexcolumn"></div>
        <div type="checkcolumn"></div>
        <div name="inputid" field="inputid" headerAlign="center" allowSort="true" width="100" >入账流水id</div>
        <div name ="inputMoney" field="inputMoney" width="100" allowSort="true" >入账金额
            <input property="editor" class="mini-spinner"  minValue="0" maxValue="200" value="25" style="width:100%;"/>
        </div>
        <div name="tradetime" field="tradetime" width="100" allowSort="true" dateFormat="yyyy-MM-dd:HH:mm:ss">资金流入时间
            <!-- 添加编辑信息 -->
            <input property="editor" class="mini-datepicker" style="width:100%;"/>
        </div>
    </div>
</div>
</div>
<script src="<%=basePath%>js/operation.js" type="text/javascript"></script>
</body>
</html>
