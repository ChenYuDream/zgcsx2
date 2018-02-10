<style>
    .nspop-tabs {
        margin-top: 10px;
        margin-bottom: 10px;
        border-bottom:1px solid #ddd;
    }
    .nspop-tabs li {
        float: left;
        margin-left: 5px;
        margin-right: 5px;
        padding:5px 10px;
        font-size: 14px;
        font-weight: 700;
        cursor: pointer;
    }
    .nspop-tabs li.nspop-active {
        color: #1E9FFF;
        border-bottom:2px solid #1E9FFF;
    }
</style>
<div class="nameSearch-wrap" ms-important="feedback">
    <input type="hidden" id="noticeId" value="${noticeId!}">
    <!-- 查询条件 S-->
    <div class="clearfix item-group nspop-tabs">
        <ul class="clearfix pull-left ">
            <li class="nspop-active" id="feedbackLi" onclick="changeDiv(1,this)">
                <span>反馈列表</span>
            </li>
            <li  id="commentLi" onclick="changeDiv(2,this)">
                <span >评价列表</span>
            </li>
        </ul>
    </div>
    <div id="feedbackDiv" >
        <form class="form-inline" onsubmit="return false;">
            <div class="clearfix item-group">
                <ul class="clearfix pull-left">
                    <li>
                        <span>姓名：</span>
                        <input class="input" type="text" name="" id="searchName" placeholder="请输入姓名...">
                    </li>
                    <li>
                        <span>反馈结果：</span>
                        <select id="type" class="select" style="width: 107px">
                            <option value="">=反馈结果=</option>
                            <option value="0">未处理</option>
                            <option value="1">同意</option>
                            <option value="2">拒绝</option>
                        </select>
                    </li>
                </ul>
                <div class="toolList pull-right">
                    <ul class="clearfix">
                        <li>
                            <!-- 查找 S-->
                            <a :click="@search()" class="item-btn" href="#this"><i class="iconfont icon-chazhao"></i>查找</a>
                            <!-- 查找 E-->
                        </li>
                        <li>
                            <!-- 清空 S-->
                            <a :click="@clearFn" class="item-btn" href="#this"><i class="iconfont icon-qingkong"></i>清空</a>
                            <!-- 清空 E-->
                        </li>
                    </ul>
                </div>
            </div>
        </form>
        <!-- 查询条件 E-->
        <!-- table表单模块 S-->
        <div class="table-content" style="height: 268px;">
            <table class="table">
                <thead>
                <tr>
                    <th width="25%">姓名(别名)</th>
                    <th width="10%">反馈结果</th>
                    <th width="20%">反馈时间</th>
                    <th width="45%">备注</th>
                </tr>
                </thead>
                <tbody>
                <tr :for="(index,el) in @_dataList">
                    <td>{{el.name}}<span :if="el.bm">({{el.bm}})</span></td>
                    <td><span :if="@el.type == '0'">未处理</span><span :if="@el.type == '1'">同意</span><span :if="@el.type == '2'">拒绝</span></td>
                    <td :attr="{title:el.mtime==null?'':el.mtime}">{{el.mtime}}</td>
                    <td :attr="{title:el.reason==null?'':el.reason}">{{el.reason|truncate(33)}}</td>
                </tr>
                <tr :if="@_dataList.length == 0">
                    <td colspan="4"><span class="text-overflow colorRed" >暂无数据!</span></td>
                </tr>
                </tbody>
            </table>
        </div>
        <!-- table表单模块 E-->
        <!-- 分页+全选模块 S-->
        <div class="clearfix footerBox">
            <div id="pages2" class="pull-right pages"></div>
        </div>
        <!-- 分页+全选模块 E-->
    </div>


    <div id="commentDiv" style="display: none">
        <form class="form-inline" onsubmit="return false;">
            <div class="clearfix item-group">
                <ul class="clearfix pull-left">
                    <li>
                        <span>姓名：</span>
                        <input class="input" type="text" name="" id="comment_searchName" placeholder="请输入姓名...">
                    </li>
                    <li>
                        <span>评价结果：</span>
                        <select id="comment_type" class="select" style="width: 107px">
                            <option value="">=评价结果=</option>
                            <option value="1">优秀</option>
                            <option value="2">良好</option>
                            <option value="3">合格</option>
                            <option value="4">不合格</option>
                        </select>
                    </li>
                </ul>
                <div class="toolList pull-right">
                    <ul class="clearfix">
                        <li>
                            <!-- 查找 S-->
                            <a :click="@comment_search()" class="item-btn" href="#this"><i class="iconfont icon-chazhao"></i>查找</a>
                            <!-- 查找 E-->
                        </li>
                        <li>
                            <!-- 清空 S-->
                            <a :click="@comment_clearFn" class="item-btn" href="#this"><i class="iconfont icon-qingkong"></i>清空</a>
                            <!-- 清空 E-->
                        </li>
                    </ul>
                </div>
            </div>
        </form>
        <!-- 查询条件 E-->
        <!-- table表单模块 S-->
        <div class="table-content" style="height: 268px;">
            <table class="table">
                <thead>
                <tr>
                    <th width="30%">姓名(别名)</th>
                    <th width="10%">评价结果</th>
                    <th width="60%">备注</th>
                </tr>
                </thead>
                <tbody>
                <tr :for="(index,el) in @comment_dataList">
                    <td>{{el.name}}<span :if="el.bm">({{el.bm}})</span></td>
                    <td>
                        <span :if="@el.evaluate == '1'">优秀</span><span :if="@el.evaluate == '2'">良好</span>
                        <span :if="@el.evaluate == '3'">合格</span><span :if="@el.evaluate == '4'">不合格</span>
                    </td>
                    <td :attr="{title:el.content==null?'':el.content}">{{el.content|truncate(33)}}</td>
                </tr>
                <tr :if="@comment_dataList.length == 0">
                    <td colspan="3"><span class="text-overflow colorRed" >暂无数据!</span></td>
                </tr>
                </tbody>
            </table>
        </div>
        <!-- table表单模块 E-->
        <!-- 分页+全选模块 S-->
        <div class="clearfix footerBox">
            <div id="pages3" class="pull-right pages"></div>
        </div>
        <!-- 分页+全选模块 E-->
    </div>

</div>
<script type="text/javascript">
    var vmFeedback = avalon.define({
        $id:'feedback',
        _data:{
            name:'',
            type:'',
            noticeId:$("#noticeId").val(),
            size:7,
            current:1
        },
        comment_data:{
            name:'',
            evaluate:'',
            noticeId:$("#noticeId").val(),
            size:7,
            current:1
        },
        _dataList:[],
        comment_dataList:[],
        clearFn:function(){
            $("#searchName").val('');
            $("#type").val('');
        },
        comment_clearFn:function(){
            $("#comment_searchName").val('');
            $("#comment_type").val('');
        },
        search:function () {
            vmFeedback._data.name = $("#searchName").val();
            vmFeedback._data.type = $("#type").val();
            vmFeedback._data.current = 1;
            this.dataListFn();
        },
        comment_search:function () {
            vmFeedback.comment_data.name = $("#comment_searchName").val();
            vmFeedback.comment_data.evaluate = $("#comment_type").val();
            vmFeedback.comment_data.current = 1;
            this.comment_dataListFn();
        },
        dataListFn:function(){
            var index;
            $.ajax({
                url: '${ctx}/notice/feedback/list',
                type: 'POST',
                dataType: 'json',
                data: vmFeedback.$model._data,
                beforeSend:function(){
                    index = layer.load(1, {shade: false});
                },
                success:function(data){
                    layer.close(index);
                    console.log(data.result.records);
                    vmFeedback._dataList = data.result.records;
                    layui.use('laypage', function(){
                        var laypage = layui.laypage;
                        laypage.render({
                            elem: 'pages2',
                            limit: vmFeedback.$model._data.size,//当前分页显示条数;
                            count: data.result.total,//服务器得数据总条数;
                            curr : data.result.current || vmFeedback.$model._data.current,//起始页;
                            groups: 7,
                            theme : '#1E9FFF',
                            jump: function(obj, first){
                                if(!first){
                                    vmFeedback._data.current = obj.curr;
                                    vmFeedback.dataListFn();
                                }
                            }
                        });
                    });
                },
                error:function(data){
                    layer.msg(data.msg);
                }
            })
        },
        comment_dataListFn:function(){
            var index;
            $.ajax({
                url: '${ctx}/notice/comment/list',
                type: 'POST',
                dataType: 'json',
                data: vmFeedback.$model.comment_data,
                beforeSend:function(){
                    index = layer.load(1, {shade: false});
                },
                success:function(data){
                    layer.close(index);
                    console.log(data.result.records);
                    vmFeedback.comment_dataList = data.result.records;
                    layui.use('laypage', function(){
                        var laypage = layui.laypage;
                        laypage.render({
                            elem: 'pages3',
                            limit: vmFeedback.$model.comment_data.size,//当前分页显示条数;
                            count: data.result.total,//服务器得数据总条数;
                            curr : data.result.current || vmFeedback.$model.comment_data.current,//起始页;
                            groups: 7,
                            theme : '#1E9FFF',
                            jump: function(obj, first){
                                if(!first){
                                    vmFeedback.comment_data.current = obj.curr;
                                    vmFeedback.comment_dataListFn();
                                }
                            }
                        });
                    });
                },
                error:function(data){
                    layer.msg(data.msg);
                }
            })
        }
    });
    vmFeedback.dataListFn();
    vmFeedback.comment_dataListFn();
    function changeDiv(num) {
        if(num == '1'){
            $("#feedbackDiv").show();
            $("#commentDiv").hide();
            $("#feedbackLi").removeClass("nspop-active");
            $("#commentLi").removeClass("nspop-active");
            $("#feedbackLi").addClass("nspop-active");
        }
        if(num == '2'){
            $("#feedbackDiv").hide();
            $("#commentDiv").show();
            $("#feedbackLi").removeClass("nspop-active");
            $("#commentLi").removeClass("nspop-active");
            $("#commentLi").addClass("nspop-active");
        }
    }
    avalon.config({
        debug: false
    })
</script>