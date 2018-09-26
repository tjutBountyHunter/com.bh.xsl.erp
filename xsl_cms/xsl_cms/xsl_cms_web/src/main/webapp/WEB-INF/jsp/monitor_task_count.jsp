<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/7/18
  Time: 14:06
  To change this template use File | Settings | File Templates.
  任务数量监控
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
            text: '任务数量图',
        },
        color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [{
                offset: 0, color: 'red' // 0% 处的颜色
            }, {
                offset: 1, color: 'yellow' // 100% 处的颜色
            }],
            globalCoord: false // 缺省为 false
        },
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
            data:['任务总数量',"任务接收数量"]
        },
        toolbox: { //工具栏组件
            show: true,
            feature: {
                magicType: {    //动态类型切换
                    type: ['line', 'bar']
                },
                dataView: {readOnly: false},
                //restore: {},         还原
                saveAsImage: {}     //保存
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
            },
            {
                type: 'category'
            }
        ],
        yAxis: [
            {
                type: 'value',
                scale: true,
                name: '数量/人',
                boundaryGap: [0, '70%']  //Y 一直处于y轴的70%
            },
            {
                type: 'value',
            }
        ],
        series: [     //添加数据
            {
                name:'任务总数量',
                type:'bar',
                smooth:true,
                areaStyle: {
                    normal: {}
                },
                symbol: 'none',
                data:(function (){
                    var res = [];
                    var len = 0;
                    while (len < 10) {
                        res.push(0);     //初始数据，就是0
                        len++;
                    }
                    return res;
                })()
            },
            {
                show:false,
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0, color: 'blue' // 0% 处的颜色
                    }, {
                        offset: 1, color: 'red' // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
                name:'任务接收数量',
                type:'line',
                smooth:true,
                areaStyle: {
                    normal: {}
                },
                symbol: 'none',
                data:(function (){
                    var res = [];
                    var len = 0;
                    while (len < 10) {
                        res.push(0);     //初始数据，就是0
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
        var data1 = option.series[1].data;
        data0.shift();
        data1.shift();

        //json数据传值，每次过来传输一个值就可以了，因为这是实时监控的
        $.ajax({
            url:"/monitor/sum",
            type:"post",
            contentType:"application/json;charset=UTF-8",
            dataType:"json",
            success:function(data){
                if(data != null){
                    data0.push(data[0]);  //随着时间推进的数据
                    data1.push(data[1]);
                }
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
