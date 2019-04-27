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

    var app = {};
    var option = null;
    option = {
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
                data: [{
                    name: '用户Aaaaaaaaa',
                    x: 300,
                    y: 300
                }, {
                    name: '用户B',
                    x: 800,
                    y: 300
                }, {
                    name: '用户C',
                    x: 550,
                    y: 100
                }, {
                    name: '用户D',
                    x: 550,
                    y: 500
                }, {
                    name: '用户E',
                    x: 900,
                    y: 200
                }],
                // links: [],
                links: [{
                    source: 0,
                    target: 1,
                    lineStyle: {
                        normal: {
                            width: 5,
                            curveness: 0.1
                        }
                    }
                }, {
                    source: 0,
                    target: 2,
                    Style: {
                        normal: { curveness: 0.2 }
                    }
                }, {
                    source: 1,
                    target: 2
                }, {
                    source: 1,
                    target: 3
                }, {
                    source: 0,
                    target: 3
                }, {
                    source: 4,
                    target: 1
                }, {
                    source: 4,
                    target: 0
                }],
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

    app.count = 11;
    setInterval(function (){
        axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');

        var data0 = option.series[0].data;
        data0.shift();
        var mount;
        //ajax传值
        $.ajax({
            url:"monitor/user/mount",
            type:"post",
            contentType:"application/json;charset=UTF-8",
            dataType:"json",
            success:function(data){
                data0.push(data);  //随着时间推进的数据
            }
        });

        option.xAxis[0].data.shift();
        option.xAxis[0].data.push(axisData);

        myChart.setOption(option);
    }, 1100);
    ;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>
</body>

</html>
