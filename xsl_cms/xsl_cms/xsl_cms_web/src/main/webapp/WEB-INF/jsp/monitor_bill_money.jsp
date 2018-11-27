<%--
  Created by IntelliJ IDEA.
  User: asus
  Time: 11:04
  To change this template use File | Settings | File Templates.
  资金流水监控
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
            text: '资金流水情况',
        },
        color:'#00FFFF',
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#FF0000'
                }
            }
        },
        legend: {
            data:['资金流水情况']
        },
        toolbox: {
            show: true,
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: true,
                data: (function (){
                    var now = new Date();
                    var res = [];
                    var len = 15;
                    while (len--) {
                        res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));   //时间推进
                        now = new Date(now - 1000);    //时间差
                    }
                    return res;
                })()
            }
        ],
        yAxis: [
            {
                type: 'value',
                scale: true,
                name: '资金',
                // boundaryGap: false  //Y 一直处于y轴的一半
            }
        ],
        series: [     //添加数据
            {
                name:'资金流水情况',
                type:'line',
                smooth:true,
                areaStyle: {
                    normal: {}
                },
                symbol: 'none',
                data:(function (){
                    var res = [];
                    var len = 0;
                    while (len < 15) {
                        res.push(100*Math.random());     //初始数据
                        len++;
                    }
                    return res;
                })()
            }
        ]
    };

    app.count = 11;
    setInterval(function (){
        axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');

        var data0 = option.series[0].data;
        data0.shift();
        $.ajax({
            url:"monitor/billMoney",
            type:"post",
            contentType:"application/json;charset=UTF-8",
            dataType:"json",
            success:function(data){
                if(data != null){
                    data0.push(data);  //随着时间推进的数据
                }
            }
        });
        // $.ajax({
        //     url:"monitor/bill",
        //     type:"post",
        //     contentType:"application/json;charset=UTF-8",
        //     dataType:"json",
        //     success:function(data){
        //         if(data.getStatus()== 200){
        //             data0.push(data[0]);  //随着时间推进的数据
        //         }
        //     }
        // });
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
