<div class="ms-controller" ms-controller="eventList">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>事件标题：</span>
                    <input id="noticeTitle"  class="input" type="text" name="" placeholder="请输入事件标题..." style="width: 137px">
                </li>
                <li>
                    <span>事件时间：</span>
                    <input id="dateTime"  class="input" type="text" name="" placeholder="请选择事件日期...">
                </li>
              <#--  <li>
                    <span>事件级别：</span>
                    <select id="evenLevel" :attr="{value:'== 请选择 =='}"  class="select">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @opt.evenLevel" :attr="{value:el.value}">{{el.text}}</option>
                    </select>
                </li>-->
                <li>
                    <span>事件等级：</span>
                    <select id="noticeLevel"  :attr="{value:'== 请选择 =='}" class="select">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @opt.noticeLevel" :attr="{value:el.value}">{{el.text}}</option>
                    </select>
                </li>
            </ul>
            <div class="toolList pull-left">
                <ul class="clearfix">
                    <li>
                        <!-- 查找 S-->
                        <a :click="@search" class="item-btn" href="#this"><i class="iconfont icon-chazhao"></i>查找</a>
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
    <div class="table-content">
        <table class="table">
            <thead>
                <tr>
                    <th width="40%"><p class="text-left pl-5">事件标题</p></th>
                    <th width="20%">事件时间(开始)</th>
                    <th>事件级别</th>
                    <th>事件等级</th>
                    <th>事件操作</th>
                </tr>
            </thead>
            <tbody>
                <tr :for="(index,el) in @dataList">
                    <td ><p class="text-left pl-5" ><span ms-if="el.creater == '${user.userid!}'" style="margin-right: 5px" class="layui-badge layui-bg-green">本人发起</span>
                        <span ms-attr="{title:el.noticeTitle}">{{el.noticeTitle| truncate(25,'...')}}</span></p></td>
                    <td>{{el.start}}</td>
                    <td>{{el.evenLevel}}</td>

                    <td :if="el.noticeLevel == '一般'"><b class="text-success">{{el.noticeLevel}}</b></td>
                    <td :if="el.noticeLevel == '重要'" class="bg-warning text-warning"><b>{{el.noticeLevel}}</b></td>
                    <td :if="el.noticeLevel == '紧急'" class="bg-danger text-danger"><b>{{el.noticeLevel}}</b></td>
                    <td>
                        <a class="btn btn-default btn-xs" :click="@toDetail(el.id)" title="查看">查看</a>
                        <span ms-if="el.creater == '${user.userid!}'">
                            <a class="btn btn-default btn-xs" :click="@feedbackList(el.id)" :if="@el.evenStyleType=='2'" title="反馈详情">反馈详情</a>
                        </span>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <!-- table表单模块 E-->
    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox">
        <div id="pages" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->
</div>
<script type="text/javascript">
var vmList = avalon.define({
    $id:'eventList',
    _data:{
        evenTime:'',//事件启止日期
        evenLevel:'',//事件级别
        noticeLevel:'',//事件等级
        noticeTitle:'',
        current:'1',//*当前页
        size:'20'//*分页大小
    },
    opt:{
        evenLevel:[],//事件级别;
        noticeLevel:[],//事件等级;
    },
    dataList:[],
    dataListFn:function(){
        $.ajax({
            url:'${ctx}/notice/list',
            type:'POST',
            dataType:'json',
            data:this.$model._data,
            success:function(data){
                var eventData = data.result.records;
                vmList.dataList = eventData;
                layui.use('laypage', function(){
                    var laypage = layui.laypage;
                    laypage.render({
                        elem: 'pages',
                        limit: vmList._data.size,
                        count: data.result.total,
                        groups: 4,
                        curr : data.result.current || 1,
                        jump: function(obj, first){
                            if(!first){
                                vmList._data.current = obj.curr;
                                vmList.dataListFn();
                            }
                        }
                    });
                });
            }
        });
    },
    loadEvent:function(){
        $.ajax({
            url: '${ctx}/dict/all',
            type: 'GET',
            dataType: 'json',
            success:function(data){
                var _data = data.result;
                vmList.opt.noticeLevel = _data.cims_event_level;//事件等级;
                vmList.opt.evenLevel = _data.even_level;//事件级别;
            }
        })
    },
    toDetail:function(noticeId){
        //判断事件是否存在
        $.ajax({
            url:"${ctx}/notice/detail/exist/"+noticeId,
            type:"post",
            success:function (data) {
                if(data.code!='0'){
                    vmList.dataListFn();
                    layer.msg(data.msg,function(){
                        return ;
                    });
                }else{
                    location.href="${ctx}/notice/detail/"+noticeId;
                }
            }
        });
    },
    clearFn:function(){
        $("#dateTime").val('');//事件启止日期
        $("#evenLevel").val('');//事件级别
        $("#noticeLevel").val('');//事件等级
        $("#noticeTitle").val('');//事件等级
    },
    search:function () {
        this._data = {
            evenTime:$("#dateTime").val(),//事件启止日期
            evenLevel:$("#evenLevel").val(),//事件级别
            noticeLevel:$("#noticeLevel").val(),//事件等级
            noticeTitle:$("#noticeTitle").val(),
            current:'1',//*当前页
            size:'20'//*分页大小
        }
        this.dataListFn();
    },
    toDelete:function (noticeId) {
        layer.confirm('<div class="text-center">您确认要删除该事件吗?</div>',{btnAlign:'c'},function(){
            $.ajax({
                url: '${ctx}/notice/delete/'+noticeId,
                type: 'GET',
                dataType: 'json',
                success:function(data){
                    layer.msg("事件删除成功!",{time:500},function(){
                        vmList.dataListFn();
                    });
                }
            });
        });
    },
    feedbackList:function (noticeId) {
        //判断事件是否存在
        $.ajax({
            url:"${ctx}/notice/detail/exist/"+noticeId,
            type:"post",
            success:function (data) {
                if(data.code!='0'){
                    vmList.dataListFn();
                    layer.msg(data.msg,function(){
                        return ;
                    });
                }else{
                    $.ajax({
                        url:"${ctx}/notice/layer/feedback/"+noticeId,
                        type:"post",
                        success:function (data) {
                            layer.open({
                                type: 1,
                                area: '700px',
                                content: data,
                                title:'反馈详情',
                                btn: ['关闭'],
                                btnAlign :'c',
                                success:function(){
                                    avalon.scan(document.body);
                                },
                                yes:function(index){
                                    layer.close(index);
                                }
                            })
                        }
                    });
                }
            }
        });
    },
    toUpdate:function (noticeId) {
        //判断事件是否存在
        $.ajax({
            url:"${ctx}/notice/detail/exist/"+noticeId,
            type:"post",
            success:function (data) {
                if(data.code!='0'){
                    vmList.dataListFn();
                    layer.msg(data.msg,function(){
                        return ;
                    });
                }else{
                    if(data.result.ifBegin == '1'){
                        layer.msg("事件已经开始",function(){
                            return ;
                        });
                    }else if(data.result.ifBegin == '2'){
                        layer.msg("事件已经结束",function(){
                            return ;
                        });
                    }else{
                        location.href="${ctx}/notice/update/page/"+noticeId;
                    }
                }
            }
        });
    }
});
vmList.loadEvent();
vmList.dataListFn();
layui.use(['layer','laydate'], function(){
    var laydate = layui.laydate,
        layer = layui.layer;
    laydate.render({
        elem: '#dateTime',
        range: true,
        theme: 'grid',
        done: function(value, date, endDate){
        }
    });
});
</script>