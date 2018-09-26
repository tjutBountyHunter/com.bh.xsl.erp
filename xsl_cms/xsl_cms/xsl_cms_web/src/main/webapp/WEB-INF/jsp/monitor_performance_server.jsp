<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/21
  Time: 11:04
  To change this template use File | Settings | File Templates.
  性能监控-server运行监控
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html style="height: 100%">
<head>

    <meta charset="utf-8">

</head>

<body>

<!-- 写一个表格的名字 -->
<div id="myChart" style="height: 100% ;height:450px;">
</div>
<script type="text/javascript" src="../../js/echarts.js">
</script>
<script type="text/javascript" src="../../js/jquery.min.js">
</script>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('myChart'));

    var app = {};
    var option = null;
    option = {
        title: {
            text: 'Server运行监控',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['运行状态']
        },
        toolbox: {
            show: true,
            feature: {
                saveAsImage: {}//下载
            }
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: true,
                data: (function (){
                    var now = new Date();
                    var res = [];
                    var len = 10;
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
                min:0,
                max:2,
                minInterval : 1,
                boundaryGap : [ 0, 0.5 ],
                type: 'value',
                name: '状态(1:启动  0:关闭)',
                axisLabel : {
                    formatter :  '{value}'
                }
            }
        ],
        series: [     //添加数据
            {
                name:'运行状态',
                type:'line',
                step:'start',
                symbol: 'none',
                data:(function (){
                    var res = [];
                    var len = 0;
                    while (len < 10) {
                        res.push(0);     //初始数据
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
        if(Math.random()>0.5){
            data0.push(1);  //随着时间推进的数据
        }else{
            data0.push(0);  //随着时间推进的数据
        }

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
