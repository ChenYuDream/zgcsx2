<style>
    .detailTable tbody th{
        text-align: center;
    }
</style>
<!-- 事件详情 S-->
<section class="ms-controller" ms-important="eventDetails">
    <table class="detailTable">
        <caption class="text-center">{{@dataList.noticeTitle}}</caption>
        <tbody>
            <tr>
                <th width="15%">开始时间:</th>
                <td colspan="2">{{@dataList.start}}</td>
                <th width="15%">结束时间:</th>
                <td colspan="2">{{@dataList.end}}</td>
            </tr>
            <tr>
                <th width="15%">事件地址:</th>
                <td colspan="5">{{@dataList.place}}</td>
            </tr>
            <tr>
                <th width="15%">发起人:</th>
                <td colspan="2"><button type="button" class="btn btn-danger btn-xs" disabled>{{@dataList.initiator[0].teacherName}}</button></td>
                <th width="15%">事件类型:</th>
                <td colspan="2">{{@dataList.evenStyle}}</td>
            </tr>
            <tr>
                <th width="15%">参与教师:</th>
                <td colspan="2">
                    <p class="text-overflow w250" :attr="{title: @teacherStr}| title(800,'...')" >
                        <span>{{teacherStr}}</span>
                    </p>
                </td>
                <th width="15%">参与学生:</th>
                <td colspan="2">
                    <p class="text-overflow w250" :attr="{title:@studentStr}">
                        <span>{{studentStr}}</span>
                    </p>
                </td>
            </tr>
            <tr>
                <th width="15%">事件体系:</th>
                <td colspan="5">
                    <button :for="(index,el) in @dataList.evenTypes" type="button" class="btn btn-default btn-xs" disabled>{{el}}</button>
                </td>
            </tr>
            <tr>
                <th width="15%">事件性质:</th>
                <td>{{@dataList.evenProperty}}</td>
                <th width="15%">事件级别:</th>
                <td class="text-center">{{@dataList.evenLevel}}</td>
                <th width="15%">事件等级:</th>

                <td :if="@dataList.noticeLevel == '一般'" class="bg-default text-success text-center"><b>{{@dataList.noticeLevel}}</b></td>
                <td :if="@dataList.noticeLevel == '重要'" class="bg-warning text-warning text-center"><b>{{@dataList.noticeLevel}}</b></td>
                <td :if="@dataList.noticeLevel == '紧急'" class="bg-danger text-danger text-center"><b>{{@dataList.noticeLevel}}</b></td>
            </tr>
            <tr>
                <th width="15%">事件描述:</th>
                <td colspan="5"><textarea rows="3" :attr="{value:@dataList.noticeContent}" disabled></textarea></td>
            </tr>
        </tbody>
    </table>
    <!--活动事件反馈-->
    <#if ifShowFeedback?? && ifShowFeedback =='1'>
        <fieldset class="layui-elem-field">
            <legend>活动事件 - 事件反馈</legend>
            <div class="layui-field-box">
                <#if alreadyFeedback?? && alreadyFeedback=='1'>
                    <#if noticePerson??>
                        <#if noticePerson.oper=='1'>
                            <blockquote class="layui-elem-quote">您已经同意参与了此活动！您的备注内容是：
                            ${noticePerson.reason!}</blockquote>
                        </#if>
                        <#if noticePerson.oper=='2'>
                            <blockquote class="layui-elem-quote">您已经拒绝了此活动！您的备注内容是：
                            ${noticePerson.reason!}</blockquote></blockquote>
                        </#if>
                    </#if>

                </#if>
                <#if alreadyFeedback?? && alreadyFeedback=='0'>
                    <#if timeOut?? && timeOut=='1'>
                        <blockquote class="layui-elem-quote">反馈时间已经结束！</blockquote>
                    </#if>
                    <#if timeOut?? && timeOut=='0'>
                        <blockquote class="layui-elem-quote">温馨提示：您可以选择参加或拒绝此活动！</blockquote>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label" style="width: 110px;"><span class="text-danger">*</span>参与事件：</label>
                            <div class="col-sm-3">
                                <label class="radio-inline">
                                    <input ms-duplex="@feedBack" name="feedBack" type="radio" value="1">同意
                                </label>
                                <label class="radio-inline">
                                    <input ms-duplex="@feedBack" name="feedBack" type="radio" value="2">拒绝
                                </label>
                                <label class="radio-inline">
                                    <button type="button" class="btn btn-default btn-xs" :click="@feedBackFun">提交</button>
                                </label>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group form-group-sm" >
                            <label class="col-sm-2 control-label" style="width: 110px;"><span class="text-danger">*</span>备注：</label>
                            <div class="col-sm-9" style="padding-bottom: 10px;">
                                <textarea ms-duplex="@reason" name="reason" id="reason" class="form-control" rows="8" placeholder="请填写备注"></textarea>
                            </div>
                        </div>
                    </#if>
                </#if>
            </div>
        </fieldset>
    </#if>
    <!--活动事件评价-->

    <!--已经评价的情况-->
    <#if evaluate??>
        <fieldset class="layui-elem-field">
            <legend>活动事件 - 事件评价</legend>
            <div class="layui-field-box">
                <blockquote class="layui-elem-quote">您已经评价了该事件，评价等级是
                    <#if evaluate.evaluate?? &&evaluate.evaluate=='1' >‘优秀’</#if>
                    <#if evaluate.evaluate?? &&evaluate.evaluate=='2' >‘良好’</#if>
                    <#if evaluate.evaluate?? &&evaluate.evaluate=='3' >‘合格’</#if>
                    <#if evaluate.evaluate?? &&evaluate.evaluate=='4' >‘不合格’</#if>
                    <#if evaluate.content??>，您的评价内容是：
                        ${evaluate.content!}
                    </#if>
                </blockquote>
            </div>
        </fieldset>
    </#if>
    <!--参与 未评价  过期 的情况-->
    <#if commentTimeOutDiv?? &&commentTimeOutDiv=='1'>
        <fieldset class="layui-elem-field">
            <legend>活动事件 - 事件评价</legend>
            <div class="layui-field-box">
                <blockquote class="layui-elem-quote">评价时间已经结束！
                </blockquote>
            </div>
        </fieldset>
    </#if>
    <!--参与者未评价情况-->
    <#if !evaluate?? && ifShowCommentDiv=='1'>
        <fieldset class="layui-elem-field">
            <legend>活动事件 - 事件评价</legend>
            <div class="layui-field-box">
                <blockquote class="layui-elem-quote">温馨提示：您可以对事件进行评价！</blockquote>
                <div class="form-group form-group-sm">
                    <label class="col-sm-2 control-label" style="width: 80px;"><span class="text-danger">*</span>评价：</label>
                    <div class="col-sm-5">
                        <label class="radio-inline">
                            <input ms-duplex="@evaluate" name="evaluate" type="radio" value="1">优秀
                        </label>
                        <label class="radio-inline">
                            <input ms-duplex="@evaluate" name="evaluate" type="radio" value="2">良好
                        </label>
                        <label class="radio-inline">
                            <input ms-duplex="@evaluate" name="evaluate" type="radio" value="3">合格
                        </label>
                        <label class="radio-inline">
                            <input ms-duplex="@evaluate" name="evaluate" type="radio" value="4">不合格
                        </label>
                        <label class="radio-inline">
                            <button type="button" class="btn btn-default btn-xs" :click="@commentCommit">提交</button>
                        </label>

                    </div>
                </div>
                <br/>
                <div class="form-group form-group-sm">
                    <label class="col-sm-2 control-label" style="width: 80px;"><span class="text-danger">*</span>备注：</label>
                    <div class="col-sm-9" style="padding-bottom: 10px;">
                        <textarea ms-duplex="@commentContent" name="commentContent" id="commentContent" class="form-control" rows="8" placeholder="请填写备注"></textarea>
                    </div>
                </div>
            </div>
        </fieldset>
    </#if>
    <#if endFileUpload?? &&endFileUpload=='0'>
        <fieldset class="layui-elem-field">
            <legend>附件下载 - 附件补充</legend>
            <div class="layui-field-box">
                <blockquote class="layui-elem-quote">温馨提示：若需要补充附件，可点击“增加文件”继续上传！</blockquote>
                <div class="downloadList table-content">
                    <table class="table" :visible="@fileList.length">
                        <thead>
                        <tr><th width="10%">上传者</th><th>上传文件</th><th width="20%">上传时间</th><th width="15%">操作</th></tr>
                        </thead>
                        <tbody>
                        <tr :for="(index,el) in @fileList">
                            <td><b>{{el.createUser | truncate(6,'...')}}</b></td>
                            <td>
                                <span :attr="{title:el.fileName}">{{el.fileName | truncate(28,'...')}}</span>
                            </td>
                            <td>{{el.createTime}}</td>
                            <td>
                                <button :click="@downloadFn(el)" type="button" class="btn btn-primary btn-xs">下载</button>
                                <button :click="@delFn(el)"
                                        :if="el.createUserId == '${user.userid}' || '${ifCanDelFile!}'=='1'" type="button" class="btn btn-danger btn-xs">删除</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </fieldset>
        <div id="uploader">
            <p>你的浏览器没有Flash,Silverlight或HTML5支持。</p>
        </div>
    <#else>
        <fieldset class="layui-elem-field">
            <legend>附件下载 - 附件补充</legend>
            <div class="layui-field-box">
                <blockquote class="layui-elem-quote">温馨提示：时间已过期，不再支持附件上传！</blockquote>
                <div class="downloadList table-content">
                    <table class="table" :visible="@fileList.length">
                        <thead>
                        <tr><th width="10%">上传者</th><th>上传文件</th><th width="20%">上传时间</th><th width="15%">操作</th></tr>
                        </thead>
                        <tbody>
                        <tr :for="(index,el) in @fileList">
                            <td><b>{{el.createUser | truncate(6,'...')}}</b></td>
                            <td>
                                <span :attr="{title:el.fileName}">{{el.fileName | truncate(28,'...')}}</span>
                            </td>
                            <td>{{el.createTime}}</td>
                            <td>
                                <button :click="@downloadFn(el)" type="button" class="btn btn-primary btn-xs">下载</button>
                                <button :click="@delFn(el)"
                                        :if="el.createUserId == '${user.userid}'" type="button" class="btn btn-danger btn-xs">删除</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </fieldset>
    </#if>

</section>
<!-- 事件详情 E-->
<script type="text/javascript">
var _ID ='${id}';
var _isNew = false;
var vmDetails = avalon.define({
    $id:'eventDetails',
    noticeId:'${id}',//事件ID;
    dataList:[],
    fileList:[],
    teacherStr:'',
    studentStr:'',
    feedBack:'1', //1同意 2拒绝
    reason:'',  //拒绝理由
    evaluate:'1',   //评价
    commentContent:'',  //评价备注
    commentCommit:function () {  //提交评价
        if(vmDetails.commentContent.length > 100){
            layer.msg('备注内容不能超过100个字', {icon: 7});
            $("#commentContent").focus();
            return;
        }
        if(vmDetails.commentContent ==''){
            layer.msg('备注不能为空', {icon: 7});
            $("#commentContent").focus();
            return;
        }
        $.ajax({
            url:"${ctx}/notice/comment",
            data:{"noticeId":_ID,"evaluate":vmDetails.evaluate,"content":vmDetails.commentContent},
            type:"post",
            beforeSend:function(){
                index = layer.load(2, {shade: false});
            },
            success:function (data) {
                if(data.code != '0'){
                    console.log(data.msg);
                }else{
                    layer.msg("评价成功",{time:500},function(){
                        window.location.reload();
                    });
                }
                layer.close(index);
            }
        });
    },
    feedBackFun:function () { //反馈
        if(vmDetails.reason == ''){
            layer.msg('请填备注', {icon: 7});
            $("#reason").focus();
            return;
        }
        if( vmDetails.reason.length >200){
            layer.msg('备注不能超过200字', {icon: 7});
            $("#reason").focus();
            return;
        }
        $.ajax({
            url:"${ctx}/notice/feedback",
            data:{"noticeId":_ID,"feedback":vmDetails.feedBack,"reason":vmDetails.reason},
            type:"post",
            beforeSend:function(){
                index = layer.load(1, {shade: false});
            },
            success:function (data) {
                if(data.code != '0'){
                    console.log("反馈失败");
                }else{
                    layer.msg("反馈成功",{time:500},function(){
                        if("1" == vmDetails.feedBack){
                            window.location.reload();
                        }else{
                            safelyGoToLoaction("${ctx}/notice/to/list");
                        }
                    });
                }
                layer.close(index);
            }
        });
    },
    dataListFn:function(){
        $.ajax({
            url:'${ctx}/notice/one/'+ this.noticeId,
            type:'POST',
            dataType:'json',
            data:this.$model._data,
            success:function(data){
                var eventData = data.result;
                vmDetails.dataList = eventData;
                var teachers = [];
                var students =[];
                teachers = eventData.teachers;
                students = eventData.students;
                var tempStr='';
                for(var i =0 ; i<teachers.length;i++){
                    var bm = '('+teachers[i].teacherBm+')';
                    tempStr = tempStr+teachers[i].teacherName  ;
                    if(teachers[i].teacherBm != null){
                        tempStr = tempStr+bm;
                    }
                    tempStr = tempStr+",";
                }
                tempStr = tempStr.substring(0,tempStr.length-1);
                vmDetails.teacherStr = tempStr;
                tempStr='';
                for(var i =0 ; i<students.length;i++){
                    tempStr = tempStr+students[i].studentName+",";
                }
                tempStr = tempStr.substring(0,tempStr.length-1);
                vmDetails.studentStr = tempStr;
            }
        });
    },
    enclosureListFn:function(){//附件列表;
        $.ajax({
            url:'${ctx}/notice/files/'+ this.noticeId,
            type:'POST',
            dataType:'json',
            data:this.$model._data,
            success:function(data){
                var fileData = data.result;
                vmDetails.fileList = fileData;
            }
        });
    },
    downloadFn:function(el){//附件下载;
        safelyGoToLoaction('${ctx}/notice/downLoad?fileId='+el.fileId);
        //window.location.href='${ctx}/notice/downLoad?filePath='+_path+'&fileName='+_name;
    },
    delFn:function(el){//附件删除;
        $.ajax({
            url:'${ctx}/notice/file/del/'+ el.fileId,
            type:'POST',
            dataType:'json',
            success:function(){
                var _index = layer.confirm("<div class='text-center'>你确定要删除该文件吗?</div>",{title:'文件信息',btnAlign:'c'},function(){
                    vmDetails.enclosureListFn();
                    layer.close(_index);
                });
            }
        });
    }
});
vmDetails.dataListFn();
vmDetails.enclosureListFn();
function refleshEventFile() {
    vmDetails.enclosureListFn();
}

avalon.filters.title = function (obj, a, b) {
    var title = obj.title
    var newTitle = avalon.filters.truncate(title, a, b)
    obj.title = newTitle
    return obj
}

</script>
<#include "../common-tpl/upload.ftl"/>