<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/21
  Time: 9:29
  To change this template use File | Settings | File Templates.
  客户端控件名
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
    <title>任务展示 </title>
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
                    <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true" tooltip="增加...">增加</a>
                    <a class="mini-button" iconCls="icon-remove" onclick="removeRow('<%=basePath%>../data/taskController')" plain="true">删除</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-save" onclick="saveData('<%=basePath%>../data/taskUpdateController')" plain="true">保存</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="key" class="mini-textbox" emptyText="请输入控件ID" style="width:150px;" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
<div id="datagrid1" class="mini-datagrid" style="height:380px;"
     url="<%=basePath%>data/task_show.json" idField="id"
     allowResize="true" pageSize="10"
     allowCellEdit="true" allowCellSelect="true" multiSelect="true"
     editNextOnEnterKey="true"  editNextRowCell="true"

>
    <div property="columns">
        <div type="indexcolumn"></div>
        <div type="checkcolumn"></div>
        <div name="LoginName"  field="loginname" headerAlign="center" allowSort="true" width="150" >员工帐号
            <!-- 添加编辑信息 editor -->
            <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
        </div>
        <div field="age" width="100" allowSort="true" >年龄
            <!-- 添加编辑信息 -->
            <input property="editor" class="mini-spinner"  minValue="0" maxValue="200" value="25" style="width:100%;"/>
        </div>
        <div name="birthday" field="birthday" width="100" allowSort="true" dateFormat="yyyy-MM-dd">出生日期
            <!-- 添加编辑信息 -->
            <input property="editor" class="mini-datepicker" style="width:100%;"/>
        </div>
        <div field="remarks" width="120" headerAlign="center" allowSort="true">备注
            <!-- 添加编辑信息 -->
            <input property="editor" class="mini-textarea" style="width:200px;" minWidth="200" minHeight="50"/>
        </div>
        <!--ComboBox：本地数据-->
        <div type="comboboxcolumn" autoShowPopup="true" name="gender" field="gender" width="100" allowSort="true"  align="center" headerAlign="center">性别
            <input property="editor" class="mini-combobox" style="width:100%;" data="Genders" />
        </div>
        <!--ComboBox：远程数据,下面javascript的数组就是，因为不用改变，所以没有使用txt文件-->
        <div type="comboboxcolumn" field="country" width="100" headerAlign="center" >国家
            <!-- 因为不能连接国家的库，所以写了一个txt,如需添加其他国籍，手动添加 -->
            <input property="editor" class="mini-combobox" style="width:100%;" url="<%=basePath%>data/countrys.txt" />
        </div>
        <div type="checkboxcolumn" field="married" trueValue="1" falseValue="0" width="60" headerAlign="center">婚否</div>
    </div>
</div>
</div>
<script src="<%=basePath%>js/operation.js" type="text/javascript"></script>
</body>
</html>
