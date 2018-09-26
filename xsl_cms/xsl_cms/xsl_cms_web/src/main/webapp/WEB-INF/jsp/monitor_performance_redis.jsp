<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/21
  Time: 11:04
  To change this template use File | Settings | File Templates.
  性能监控-reids访问监控
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
            text: 'Redis访问情况',
        },
        color: "#aad1ff",
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#283b56'
                }
            }
        },
        legend: {
            data:['Redis访问量']
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
                name: '数量',
                boundaryGap: [0, '80%']  //Y 一直处于y轴的一半
            }
        ],
        series: [     //添加数据
            {
                name:'Redis访问量',
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
        data0.push(100*Math.random());  //随着时间推进的数据

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
