<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/16
  Time: 11:10
  To change this template use File | Settings | File Templates.
  用户展示页面
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
    <title>用户展示 </title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link href="<%=basePath%>css/demo.css" rel="stylesheet" type="text/css"/>

    <script src="<%=basePath%>js/boot.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/miniui/locale/en_US.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ColumnsMenu.js" type="text/javascript"></script>

</head>
<body>
报表
<div class="mini-fit">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true" tooltip="增加...">增加</a>
                    <a class="mini-button" iconCls="icon-remove" onclick="removeRow('user/show/delete')" plain="true">删除</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-save" onclick="saveData('user/show')" plain="true">保存</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="key" class="mini-textbox" emptyText="请输入用户手机号码" style="width:150px;" onenter="onKeyEnter"/>
                    <input id="key1" class="mini-combobox" emptyText="请选择用户状态" style="width:150px;" url="<%=basePath%>data/state.txt" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:380px;"
         url="account/show/list" idField="id" ajaxOptions="{type:'get', async: true, data: {},
        dataType: 'text', contentType: 'application/json;charset=utf-8'}"
         allowResize="true" pageSize="10"
         allowCellEdit="true" allowCellSelect="true" multiSelect="true"
         editNextOnEnterKey="true" editNextRowCell="true">
        <div property="columns">
            <div type="indexcolumn"></div>
            <div id="checked" type="checkcolumn"></div>


            <div name="phone" field="totalUser" width="100" allowSort="true" align="center" headerAlign="center">总用户数
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200"/>
            </div>
            <div name="phone" field="newUser" width="100" allowSort="true" align="center" headerAlign="center">当日新用户
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200"/>
            </div>

            <div name="sno" field="totalTask" width="100" allowSort="true" align="center" headerAlign="center">总任务数
                <!-- 添加编辑信息 -->
            </div>

            <div field="newTask" width="120" headerAlign="center" allowSort="true" align="center" headerAlign="center">当日新任务
            </div>

            <div field="totalDoneTask" width="120" headerAlign="center" allowSort="true" align="center" headerAlign="center">总完成任务
            </div>
            <div field="newDoneTask" width="120" headerAlign="center" allowSort="true" align="center" headerAlign="center">当日完成任务
            </div>

            <!--ComboBox：远程数据,下面javascript的数组就是，因为不用改变，所以没有使用txt文件-->


            <div name="createdate" field="time" width="100" allowSort="true" valueType="String" dateFormat="yyyy-MM-dd" align="center" headerAlign="center">日期
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-datepicker" style="width:100%;"/>
            </div>

            </div>

        </div>
    </div>
</div>
<script src="<%=basePath%>js/operation.js" type="text/javascript"></script>
</body>
</html>
