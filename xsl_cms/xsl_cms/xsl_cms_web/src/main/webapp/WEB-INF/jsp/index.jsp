<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>悬赏令后台</title>
    <script src="js/boot.js" type="text/javascript"></script>
    <link href="res/third-party/scrollbar/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />
    <script src="res/third-party/scrollbar/jquery.mCustomScrollbar.concat.min.js" type="text/javascript"></script>
    <link href="res/menu/menu.css" rel="stylesheet" type="text/css" />
    <script src="res/menu/menu.js" type="text/javascript"></script>
    <script src="res/menutip.js" type="text/javascript"></script>
    <link href="res/tabs.css" rel="stylesheet" type="text/css" />
    <link href="res/frame.css" rel="stylesheet" type="text/css" />
    <link href="res/index.css" rel="stylesheet" type="text/css" />
  <body>
    
<div class="navbar">
    <div class="navbar-header">
        <div class="navbar-brand">悬赏令后台</div>
        <div class="navbar-brand navbar-brand-compact">XSL</div>
    </div>
    <ul class="nav navbar-nav">
        <li><a id="toggle"><span class="fa fa-bars" ></span></a></li>
        <li class="icontop"><a href="#"><i class="fa fa-hand-pointer-o"></i><span >系统演示</span></a></li>
        <%--<li class="icontop"><a href="#"><i class="fa fa-puzzle-piece"></i><span >登录信息</span></a></li>--%>
        <li class="icontop"><a href="#"><i class="fa fa-sort-amount-asc"></i><span >人力资源</span></a></li>
        <li class="icontop"><a href="#"><i class="fa  fa-cog"></i><span >系统设置</span></a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle userinfo">
                <img class="user-img" src="res/images/user.jpg" />管理员资料<i class="fa fa-angle-down"></i>
            </a>
            <ul class="dropdown-menu pull-right">
                <li ><a onclick="updatePasswoed()"><i class="fa fa-eye "></i> 修改密码</a></li>
                <li><a href="#"><i class="fa fa-user"></i> 退出登录</a></li>
            </ul>
        </li>
    </ul>
</div>

<div class="container">
    
    <div class="sidebar">
        <div class="sidebar-toggle"><i class = "fa fa-fw fa-dedent" ></i></div>
        <div id="mainMenu"></div>
    </div>

    <div class="main">
        <div id="mainTabs" class="mini-tabs main-tabs" activeIndex="0" style="height:100%;" plain="false"
             buttons="#tabsButtons" arrowPosition="side" >
            <div name="index" iconCls="fa-android" title="控制台">
                Welcome 悬赏令-后台主界面 ！
            </div>
        </div>
        <div id="tabsButtons">
            <a class="tabsBtn"><i class="fa fa-home"></i></a>
            <a class="tabsBtn"><i class="fa fa-refresh"></i></a>
            <a class="tabsBtn"><i class="fa fa-remove"></i></a>
            <a class="tabsBtn"><i class="fa fa-arrows-alt"></i></a>
        </div>   
    </div>
   
</div>


</body>
</html>
<script>


    function activeTab(item) {
        var tabs = mini.get("mainTabs");
        var tab = tabs.getTab(item.id);
        if (!tab) {
            tab = { name: item.id, title: item.text, url: item.url, iconCls: item.iconCls, showCloseButton: true };
            tab = tabs.addTab(tab);
        }
        tabs.activeTab(tab);
    }

    $(function () {

        //menu
        var menu = new Menu("#mainMenu", {
            itemclick: function (item) {
                if (!item.children) {
                    activeTab(item);
                }
            }
        });

        $(".sidebar").mCustomScrollbar({ autoHideScrollbar: true });

        new MenuTip(menu);

        $.ajax({
            url: "data/menu.txt",
            success: function (text) {
                var data = mini.decode(text);
                menu.loadData(data);
            }
        })

        //toggle
        $("#toggle, .sidebar-toggle").click(function () {
            $('body').toggleClass('compact');
            mini.layout();
        });

        //dropdown
        $(".dropdown-toggle").click(function (event) {
            $(this).parent().addClass("open");
            return false;
        });

        $(document).click(function (event) {
            $(".dropdown").removeClass("open");
        });
    });

    //修改密码，弹出一个模态窗口
    function updatePasswoed() {
        mini.open({
            targetWindow: window,
            url: "update/password",
            title: "修改密码", width: 300, height: 200,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = { action: "new" };
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                grid.reload();
            }
        });
    }

</script>

