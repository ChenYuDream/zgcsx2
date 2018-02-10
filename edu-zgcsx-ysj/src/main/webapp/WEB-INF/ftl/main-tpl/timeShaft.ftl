<div class="ms-controller" ms-controller="timeShaft">
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
               <#-- <li>
                    <span>事件级别：</span>
                    <select id="evenLevel" class="select">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @opt.evenLevel" :attr="{value:el.value}">{{el.text}}</option>
                    </select>
                </li>-->
                <li>
                    <span>事件等级：</span>
                    <select id="noticeLevel" class="select">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @opt.noticeLevel" :attr="{value:el.value}">{{el.text}}</option>
                    </select>
                </li>
            </ul>
            <div class="toolList pull-left">
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
    <!-- 时间轴 S-->
    <div  class="timeShaft-wrap">
        <h1 class="title">中关村三小-云事件</h1>
        <div :for="(index,el) in @dataList" class="year">
            <h2><a href="#this">{{el.year}}年<i></i></a></h2>
            <div class="list">
                <ul id="js_itemList">
                    <li :for="(index_0,el_0) in el.data" class="clearfix highlight">
                        <p class="date pull-left text-right">{{el_0.monthDay}}</p>
                        <div class="more pull-left">
                            <a :for="(index_1,el_1) in el_0.data | limitBy(10) as items" href="javasript:void(0)"  title="">
                                <span :if="el_1.noticeLevel == '一般'" class="flag bg-default">一般</span>
                                <span :if="el_1.noticeLevel == '重要'" class="flag bg-warning">重要</span>
                                <span :if="el_1.noticeLevel == '紧急'" class="flag bg-danger">紧急</span>
                                <span ms-click="detail(el_1.id)" ms-attr="{title:el_1.noticeTitle}">{{el_1.noticeTitle| truncate(25,'...')}}</span>
                            </a>
                            <button :if="el_0.data.length > 10" class="btn btn-default btn-xs" style="margin-left:40px;margin-top:10px">查看更多</button>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!-- 时间轴 E-->
<script>
layui.use(['layer'], function(){
    var layer = layui.layer;
});
var vmTimeShaft = avalon.define({
    $id:'timeShaft',
    _data:{
        evenTime:'',//事件启止日期
        evenLevel:'',//事件级别
        noticeLevel:'',//事件等级
        noticeTitle:'',
        current:'1',//*当前页
        size:'15'//*分页大小
    },
    opt:{
        evenLevel:[],//事件级别;
        noticeLevel:[],//事件等级;
    },
    dataList:[],
    dataListFn:function(){
        $.ajax({
            url:'${ctx}/notice/timeLine',
            type:'POST',
            dataType:'json',
            data:this.$model._data,
            success:function(data){
                var _result = data.result;
                vmTimeShaft.dataList = _result.record;
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
                vmTimeShaft.opt.noticeLevel = _data.cims_event_level;//事件等级;
                vmTimeShaft.opt.evenLevel = _data.even_level;//事件级别;
            }
        })
    },
    clearFn:function(){
        $("#dateTime").val('');
        $("#evenLevel").val('');
        $("#noticeLevel").val('');
        $("#noticeTitle").val('');
    },
    detail:function (noticeId) {
        safelyGoToLoaction("${ctx}/notice/detail/"+noticeId);
    },
    search:function () {
        vmTimeShaft._data.evenTime = $("#dateTime").val();
        vmTimeShaft._data.evenLevel = $("#evenLevel").val();
        vmTimeShaft._data.noticeLevel = $("#noticeLevel").val();
        vmTimeShaft._data.noticeTitle = $("#noticeTitle").val();
        vmTimeShaft._data.current = "1";
        vmTimeShaft.dataListFn();
    }
});
vmTimeShaft.loadEvent();
vmTimeShaft.dataListFn();
layui.use(['laydate'], function(){
    var laydate = layui.laydate;
    laydate.render({
        elem: '#dateTime',
        range: true,
        theme: 'grid',
        done: function(value, date, endDate){
        }
    });
});
$(document).ready(function() {
    var i =1;
    $(window).scroll(function() {
        if(($(window).height() + $(window).scrollTop()) >= $("body").height()){
            i++;
            vmTimeShaft._data.size = 30 * i;
            vmTimeShaft.dataListFn();
        }
    });
});
</script>
</html>