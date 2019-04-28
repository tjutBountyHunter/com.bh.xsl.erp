<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/18
  Time: 9:56
  To change this template use File | Settings | File Templates.
  用户活跃度监控
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html style="height: 100%">
<head>
    <base href="<%=basePath%>"/>
    <meta charset="utf-8">

</head>

<body>

<!-- 写一个表格的名字 -->
<div id="myChart" style="height: 100% ;height:450px;">
</div>
<script type="text/javascript" src="<%=basePath%>js/echarts.min.js">
</script>
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js">
</script>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('myChart'));

    var resdata = getData();

    var reslink = getLink();

    var option = {
        title: {
            text: '用户关系网络图'
        },
        tooltip: {},
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        series : [
            {
                type: 'graph',
                layout: 'none',
                symbolSize: 50,
                roam: true,
                label: {
                    normal: {
                        show: true
                    }
                },
                edgeSymbol: ['circle', 'arrow'],
                edgeSymbolSize: [0, 0],
                edgeLabel: {
                    normal: {
                        textStyle: {
                            fontSize: 10
                        }
                    }
                },
                data: getData(),
                // links: [],
                links: getLink(),
                lineStyle: {
                    normal: {
                        opacity: 0.9,
                        width: 2,
                        curveness: 0.2
                    }
                }
            }
        ]
    };

    if (option && typeof option === "object") {
        console.log(JSON.stringify(option));
        myChart.setOption(option, true);
    }

    function getData() {
        var rDate = "";
        $.ajax({
            url:"xsl/user/network/getNode",
            type:"post",
            contentType:"application/json;charset=UTF-8",
            async: false,
            dataType:"json",
            success:function(data){
                rDate = data;
            },
            error : function(errorMsg) {
                //请求失败时执行该函数
                mini.alert(errorMsg.msg);
            }
        });

        return rDate;
    }

    function getLink() {
        var rDate = "";
        $.ajax({
            url:"xsl/user/network/getLink",
            type:"post",
            contentType:"application/json;charset=UTF-8",
            async: false,
            dataType:"json",
            success:function(data){
                rDate = data;
            },
            error : function(errorMsg) {
                //请求失败时执行该函数
                mini.alert(errorMsg.msg);
            }
        });

        return rDate;
    }
</script>
</body>

</html>
