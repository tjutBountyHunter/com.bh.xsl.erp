<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/21
  Time: 16:18
  To change this template use File | Settings | File Templates.
  //*****************管理员登录界面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
       "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Login</title>
    <style type="text/css">
        body
        {
            width:100%;height:100%;margin:0;overflow:hidden;
        }
    </style>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="../css/demo.css" rel="stylesheet" type="text/css" />
    <script src="../js/boot.js" type="text/javascript"></script>
</head>
<body >
<div id="loginWindow" class="mini-window" title="用户登录" style="width:350px;height:165px;"
     showModal="true" showCloseButton="false"
>

    <div id="loginForm" style="padding:15px;padding-top:10px;">
        <table >
            <tr>
                <td style="width:60px;"><label>帐号：</label></td>
                <td>
                    <input id="username" name="username" onvalidation="onUserNameValidation" class="mini-textbox" required="true" style="width:150px;"/>
                </td>
            </tr>
            <tr>
                <td style="width:60px;"><label>密码：</label></td>
                <td>
                    <input id="pwd" name="pwd" onvalidation="onPwdValidation" class="mini-password" requiredErrorText="密码不能为空" required="true" style="width:150px;" onenter="onLoginClick"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td style="padding-top:5px;">
                    <a onclick="onLoginClick" class="mini-button" style="width:60px;">登录</a>
                    <a onclick="onResetClick" class="mini-button" style="width:60px;">重置</a>
                </td>
            </tr>
        </table>
    </div>

</div>





<script type="text/javascript">
    mini.parse();

    var loginWindow = mini.get("loginWindow");
    loginWindow.show();

    function onLoginClick(e) {
        var form = new mini.Form("#loginWindow");

        form.validate();
        if (form.isValid() == false) return;

        loginWindow.hide();
        mini.loading("登录成功，马上转到系统...", "登录成功");
        setTimeout(function () {
            window.location = "/";
        }, 1500);
    }
    function onResetClick(e) {
        var form = new mini.Form("#loginWindow");
        form.clear();
    }
    /////////////////////////////////////
    function isEmail(s) {
        if (s.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
            return true;
        else
            return false;
    }

    function onUserNameValidation(e) {
        if (e.isValid) {
            //添加密码框的验证条件
            // if (isEmail(e.value) == false) {
            //     e.errorText = "必须输入邮件地址";
            //     e.isValid = false;
            // }
        }
    }
    function onPwdValidation(e) {
        if (e.isValid) {
            if (e.value.length > 5) {
            }else{
                e.errorText = "密码不能少于5个字符";
                e.isValid = false;
            }
        }
    }
</script>

</body>
</html>