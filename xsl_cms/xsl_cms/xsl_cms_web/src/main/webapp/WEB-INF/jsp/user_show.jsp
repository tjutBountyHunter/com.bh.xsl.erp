<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/16
  Time: 11:10
  To change this template use File | Settings | File Templates.
  用户展示页面
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户展示 </title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link href="../css/demo.css" rel="stylesheet" type="text/css"/>

    <script src="../js/boot.js" type="text/javascript"></script>
    <script src="../js/miniui/locale/en_US.js" type="text/javascript"></script>
    <script src="../js/ColumnsMenu.js" type="text/javascript"></script>

</head>
<body>
<div class="mini-fit">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true" tooltip="增加...">增加</a>
                    <a class="mini-button" iconCls="icon-remove" onclick="removeRow('/user/show/delete')" plain="true">删除</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-save" onclick="saveData('/user/show')" plain="true">保存</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="key" class="mini-textbox" emptyText="请输入用户ID" style="width:150px;" onenter="onKeyEnter"/>
                    <input id="key1" class="mini-combobox" emptyText="请输入用户状态" style="width:150px;" url="../data/state.txt" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:380px;"
         url="/user/show/list" idField="id" ajaxOptions="{type:'get', async: true, data: {},
        dataType: 'text', contentType: 'application/json;charset=utf-8'}"
         allowResize="true" pageSize="10"
         allowCellEdit="true" allowCellSelect="true" multiSelect="true"
         editNextOnEnterKey="true" editNextRowCell="true"

    >
        <div property="columns">
            <div type="indexcolumn"></div>
            <div id="checked" type="checkcolumn"></div>
            <div name="id" field="id" headerAlign="center" allowSort="true" width="150">用户ID</div>
            <div field="hunterid" width="100" allowSort="true">猎人ID</div>
            <div field="masterid" width="100" allowSort="true" >雇主ID</div>
            <div name="name" field="name" width="100" allowSort="true" >昵称
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200"/>
            </div>
            <div name="password" field="password" width="100" allowSort="true" >密码
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200"/>
            </div>
            <!--ComboBox：本地数据-->
            <div type="comboboxcolumn" autoShowPopup="true" name="sex" field="sex" width="100" allowSort="true"
                 align="center" headerAlign="center">性别
                <input property="editor" class="mini-combobox" style="width:100%;" data="Genders"/>
            </div>
            <div name="phone" field="phone" width="100" allowSort="true" >电话
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200"/>
            </div>
            <div name="email" field="email" width="100" allowSort="true" >邮箱
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
            </div>
            <div name="schoolinfo" field="schoolinfo" width="100" allowSort="true" >学校信息
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
            </div>
            <div field="signature" width="120" headerAlign="center" allowSort="true">个性签名
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-textarea" style="width:200px;" minWidth="200" minHeight="50"/>
            </div>
            <!--ComboBox：远程数据,下面javascript的数组就是，因为不用改变，所以没有使用txt文件-->
            <div type="comboboxcolumn" field="state" width="100" headerAlign="center">状态
                <!-- 因为不能连接国家的库，所以写了一个txt,如需添加其他国籍，手动添加 -->
                <input property="editor" class="mini-combobox" style="width:100%;" url="../data/state.txt"/>
            </div>
            <div name="createdate" field="createdate" width="100" allowSort="true" valueType="String" dateFormat="yyyy-MM-dd">创建日期
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-datepicker" style="width:100%;"/>
            </div>
            <div name="updatedate" field="updatedate" width="100" allowSort="true" dateFormat="yyyy-MM-dd">更新日期
                <!-- 添加编辑信息 -->
                <input property="editor" class="mini-datepicker" style="width:100%;"/>
            </div>
        </div>
    </div>
</div>
<script src="../js/operation.js" type="text/javascript"></script>
</body>
</html>
