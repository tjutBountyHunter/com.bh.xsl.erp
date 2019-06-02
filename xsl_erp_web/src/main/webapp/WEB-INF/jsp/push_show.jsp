<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/19
  Time: 9:29
  To change this template use File | Settings | File Templates.
  任务展示页面
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
    <title>任务展示 </title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=basePath%>css/demo.css" rel="stylesheet" type="text/css" />

    <script src="<%=basePath%>js/boot.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/miniui/locale/en_US.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ColumnsMenu.js" type="text/javascript"></script>
</head>
<body>
push show
<div class="mini-fit">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true" tooltip="增加...">增加</a>
                    <a class="mini-button" iconCls="icon-remove" onclick="removeRow('push/delete')" plain="true">删除</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-save" onclick="saveData('push/')" plain="true">保存</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="id" class="mini-textbox" emptyText="请输入任务ID" style="width:150px;" onenter="onKeyEnter"/>
                    <input id="state" class="mini-combobox" emptyText="请输入任务状态" style="width:150px;" url="<%=basePath%>data/task_state.txt" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
    <div id="datagrid1" class="mini-datagrid" style="height:380px;"
         url="push/list" idField="pushId" msg_title="msg_title" msg_content = "msg_content"  notification_content = "notification_content"
         allowResize="true" pageSize="10"
         allowCellEdit="true" allowCellSelect="true" multiSelect="true"
         editNextOnEnterKey="true"  editNextRowCell="true">
        <div property="columns">
            <div type="indexcolumn"></div>
            <div type="checkcolumn"></div>
            <div name="id"  field="id" headerAlign="center" allowSort="true" width="0" >任务ID</div>
            <div field="pushId" width="100" allowSort="true" >推送id
            </div>
            <div field="msg_title" width="150" allowSort="true" >消息标题
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200"/>
            </div>
            <div name="phone" field="msg_content" width="100" allowSort="true" >消息内容
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200"/>
            </div>
            <div field="notification_content" width="100" headerAlign="center" allowSort="true">通知标题
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-textbox" style="width:200px;" />
            </div>
            <!--ComboBox：远程数据,下面javascript的数组就是，因为不用改变，所以没有使用txt文件-->
<%--            <div name="approve" field="approve" width="30" headerAlign="center" align="center"  allowSort="true" >操作</div>--%>
        </div>
    </div>
</div>
<script src="<%=basePath%>js/operation.js" type="text/javascript"></script>
</body>
</html>
