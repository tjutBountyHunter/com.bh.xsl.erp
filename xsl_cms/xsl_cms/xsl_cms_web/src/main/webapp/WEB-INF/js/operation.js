//datagrid增删改查的js通用操作

//id为接收时候的参数，接收到对应的id，反馈一个对应的text，到时候看数据库那边设计
    var Genders = [{id: '男', text: '男'}, {id: '女', text: '女'}];

    mini.parse();

    var grid = mini.get("datagrid1");
    //第一次加载的时候
    grid.load();

    var menu = new ColumnsMenu(grid);

    //////////////////////////////////////////////////////

    function search() {
        var key = mini.get("key").getValue();
        var key1 = mini.get("key1");
        //后台直接接受数据即可，使用Param改名

    if(key1 != null){
        var key11 = key1.getValue();
        grid.load({key: key,key1:key11});//重新加载数据，还是在以前的那个url。后台进行条件的查询，条件语句查询
    }else{
        grid.load({key: key});//重新加载数据，还是在以前的那个url。后台进行条件的查询，条件语句查询
    }
    }

    function onKeyEnter(e) {//按enter的时候触发search（）方法
        search();
    }

    var isAdd = false;

    function addRow() {
        isAdd = true;
        var newRow = {name: "New Row"};
        grid.addRow(newRow, 0);     //增加一个行，进行添加
        grid.beginEditCell(newRow, "id");//开始编辑
        grid.select(newRow);//添加时选中该行，可同时对多行进行保存
    }

    //移除操作
    function removeRow(url) {
        //选中的行对象数组，可进行多表操作
        var rows = grid.getSelecteds();
        var json = mini.encode(rows);
        //进行ajax操作，进行数据的逻辑删除
        $.ajax({
            url:url,
            type:"post",
            data:json,
            contentType:"application/json;charset=UTF-8",
            dataType:"json",
            success:function(data){
                mini.alert(data.msg);
            },
            error : function(errorMsg) {
                //请求失败时执行该函数
                mini.alert(errorMsg.msg);
            }
       });
    }

    //保存，添加和修改,,,,,,由于add也算是航变化，所以add放到修改后面，便于操作
    function saveData(url) {
        var chanages = grid.getChanges("modified",true); //获取多行修改的参数,true只填写改过的
        if (chanages.length > 0) { //修改
            var json = mini.encode(chanages);
            $.ajax({
                url:url + "/update",
                type:"post",
                data:json,
                contentType:"application/json;charset=UTF-8",
                dataType:"json",
                success:function(data){
                    mini.alert(data.msg);
                },
                error : function(errorMsg) {
                    //请求失败时执行该函数
                    mini.alert(errorMsg.msg);
                }
            });
        }

        if (isAdd) { //添加
            var selects = grid.getChanges("added",true);
            var json = mini.encode(selects);//将js对象序列化成json字符串，mini.decode()将json字符串序列化成js对象
            //使用ajax将数据传向后台，进行数据的更改使用restful风格传     add字段
            $.ajax({
                url:url + "/add",
                type:"post",
                data:json,
                contentType:"application/json;charset=UTF-8",
                dataType:"json",
                success:function(data){
                    mini.alert(data.msg);
                },
                error : function(errorMsg) {
                    //请求失败时执行该函数
                    mini.alert(errorMsg.msg);
                }
            });
            isAdd = false;
        }
        grid.accept();//关闭所选行，取消修改的标记
    }


    grid.on("celleditenter", function (e) {//编辑时，按enter进行向下移动
        var index = grid.indexOf(e.record);
    });

    grid.on("beforeload", function (e) {
        if (grid.getChanges().length > 0) {
            mini.confirm("有增删改的数据未保存，是否取消本次操作？", "提示", function (data) {
                if ("ok" != data) {//取消,但是也要恢复以前的数，所以要reload。确定呢，比取消多一个操作，那就是修改
                    mini.alert("数据已经修改了！");
                }
                grid.reload();
                grid.accept();
            });
        }
    });

    //设置编辑时候不能写的数据
    grid.on("cellcommitedit", function (e) {
        if (e.field == "loginname") {
            if (e.value == "") {
                mini.alert("名字不允许为空!");
                e.cancel = true;
            }
        }
    });