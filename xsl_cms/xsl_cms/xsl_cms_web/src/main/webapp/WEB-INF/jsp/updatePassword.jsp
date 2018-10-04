<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/21
  Time: 15:55
  To change this template use File | Settings | File Templates.
  修改密码的一个模态窗口
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
    <title>修改密码窗口</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=basePath%>css/demo.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/boot.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ColumnsMenu.js" type="text/javascript"></script>
</head>
<body>
<div id="form1" style="padding-top: 20px">
    <table>
        <tr>
            <td><label>&nbsp;&nbsp;&nbsp;&nbsp;新密码：</label></td>
            <td>
                <input name="pwd" class="mini-password" vtype="minLength:5" onvalidation="onPassword" required="true" minLengthErrorText="密码不能少于5个字符" />

            </td>
        </tr>
        <tr>
            <td><label>重复密码：</label></td>
            <td>
                <input name="pwd1" class="mini-password" vtype="minLength:5" onvalidation="onRePassword" required="true" minLengthErrorText="密码不一致" />
            </td>
        </tr>
        <tr  align="center" >
            <td style="padding-top:5px;" colspan="2">
                <a onclick="onLoginClick" class="mini-button" style="width:60px;">登录</a>
                <a onclick="onResetClick" class="mini-button" style="width:60px;">重置</a>
            </td>
            <td>

            </td>
        </tr>
    </table>
</div>
</body>
<script type="text/javascript">
    mini.parse();

    function submitForm() {
        var form = new mini.Form("#form1");
        //提交数据
        var data = form.getData();
        var json = mini.encode(data);
        $.ajax({
            url: "#",
            type: "post",
            data: { submitData: json },
            success: function (text) {
                alert("修改成功，返回结果:" + text);
            }
        });
    }

    //密码框的验证
    var password;
    function onPassword(e) {
        if (e.isValid) {
            if (e.value.length < 5) {
                e.errorText = "密码长度必须大于5";
                e.isValid = false;
            }
        }
        password = e.value;
    }

    //重复密码框的验证
    function onRePassword(e) {
        if (e.isValid) {
            if (e.value != password) {
                e.errorText = "密码不一致";
                e.isValid = false;
            }
        }
    }
</script>
</html>
