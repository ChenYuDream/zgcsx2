<!-- form表单提交模块 S-->
<div class="form-wrap" ms-important="evenForm">
    <form id="form-validator" class="form-horizontal">
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>事件标题：</label>
            <div class="col-sm-6">
                <input ms-duplex="@evenData.noticeTitle" name="headline" type="text" class="form-control" placeholder="请填写事件标题...">
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>事件时间：</label>
            <div class="col-sm-4">
                <input ms-duplex="@evenData.evenTime" id="dateTime" name="dateTime" type="text" class="form-control" placeholder="开始时间 至 结束时间">
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>事件地址：</label>
            <div class="col-sm-5">
                <input ms-duplex="@evenData.place" name="place" id="place" type="text" class="form-control" placeholder="请填写事件地址...">
            </div>
            <div class="col-sm-3">
                <button :click="@addCommonFn('address','place')" type="button" class="btn btn-danger btn-xs">添加</button>
                <button :click="@clearFn('place')" :if="@evenData.place !== ''" type="button" class="btn btn-default btn-xs">清空</button>
            </div>
        </div>
        <br/>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>发起人：</label>
            <#if ifForOther?? &&  ifForOther=='1'>
                <div class="col-sm-3">
                    <input id="initiator" name="initiator" value="${user.username}" type="text" class="form-control" readonly placeholder="请选择发起人...">
                </div>
                <div class="col-sm-3">
                    <button :click="@addCommonFn('people','initiator')" type="button" class="btn btn-danger btn-xs">选择</button>
                    <button :click="@clearFn('initiator')" :if="@viewData.initiator.length" type="button" class="btn btn-default btn-xs">清空</button>
                </div>
                <#else>
                    <div class="col-sm-3">
                        <input  value="${user.username}" name="initiator" id="initiator" type="text" class="form-control" readonly placeholder="请选择发起人...">
                    </div>
            </#if>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label">参与者(教师)：</label>
            <div class="col-sm-3">
                <input :attr="{value:@viewData.teachers,title:@viewData.teachers}" type="text" name="teachers" id="teachers" class="form-control" readonly placeholder="请添加参与教师...">
            </div>
            <div class="col-sm-3">
                <button :click="@addCommonFn('people','teachers')" type="button" class="btn btn-danger btn-xs">添加</button>
                <button :click="@clearFn('teachers')" :if="@viewData.teachers.length" type="button" class="btn btn-default btn-xs">清空</button>
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label">参与者(学生)：</label>
            <div class="col-sm-3">
                <input :attr="{value:@viewData.students,title:@viewData.students}" name="students" type="text" id="students" class="form-control" readonly placeholder="请添加参与学生...">
            </div>
            <div class="col-sm-3">
                <button :click="@addCommonFn('people','students')" type="button" class="btn btn-danger btn-xs">添加</button>
                <button :click="@clearFn('students')" :if="@viewData.students.length" type="button" class="btn btn-default btn-xs">清空</button>
            </div>
        </div>
        <br/>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>事件性质：</label>
            <div class="col-sm-3">
                <label class="radio-inline">
                    <input ms-duplex="@evenData.evenProperty" name="sjxz" type="radio" value="1">个人事件
                </label>
                <label class="radio-inline">
                    <input ms-duplex="@evenData.evenProperty" name="sjxz" type="radio" value="2">部门事件
                </label>
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>事件等级：</label>
            <div class="col-sm-10">
                <label ms-for="(index,el) in @opt.noticeLevel" class="radio-inline">
                    <input ms-duplex="@evenData.noticeLevel" :attr="{value:el.value}" name="sjdj" type="radio">{{el.text}}
                </label>
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>事件类型：</label>
            <div class="col-sm-10">
                <label ms-for="(index,el) in @opt.evenStyle" class="radio-inline">
                    <input ms-duplex="@evenData.evenStyle" :attr="{value:el.value}" name="sjlx" type="radio">{{el.text}}
                </label>
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>事件级别：</label>
            <div class="col-sm-10">
                <label ms-for="(index,el) in @opt.evenLevel" class="radio-inline">
                    <input ms-duplex="@evenData.evenLevel" :attr="{value:el.value}" name="level" type="radio">{{el.text}}
                </label>
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>事件体系：</label>
            <div class="col-sm-10">
                <label ms-for="(index,el) in @opt.evenTypes" class="checkbox-inline">
                    <input ms-duplex="@evenData.evenTypes" :attr="{value:el.value}" name="evenTypes" type="checkbox">{{el.text}}
                </label>
            </div>
        </div>
        <br/>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>事件描述：</label>
            <div class="col-sm-9">
                <textarea ms-duplex="@evenData.noticeContent" name="description" class="form-control" rows="8" placeholder="请描述事件基本信息"></textarea>
            </div>
        </div>
        <br/>


        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label">事件附件：</label>
            <div class="col-sm-9">
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
                <div id="uploader">
                    <p>你的浏览器没有Flash,Silverlight或HTML5支持。</p>
                </div>
            </div>
        </div>
        <br/>
        <div class="form-group form-group-sm">
            <div class="col-sm-offset-2 col-sm-9">
                <#if model=='event_update'>
                    <button type="submit" class="btn btn-primary">修改</button>
                </#if>
                <#if model=='event_add'>
                    <button type="submit" class="btn btn-primary">提交</button>
                </#if>
                <#if model=='addNoticeForOther'>
                    <button type="submit" class="btn btn-primary">提交</button>
                </#if>
            </div>
        </div>
    </form>
</div>
<!-- form表单提交模块 E-->
<script type="text/javascript">
var vmForm = avalon.define({
    $id:'evenForm',
    fileList:[],
    evenData:{
        id:'',//事件ID;
        noticeTitle:'',//事件标题;
        evenTime:'',//事件时间;
        place:'',//事件地点;
        initiator:'${user.userid}',//发起人;
        teachers:[],//参与教师;
        students:[],//参与学生;
        evenProperty:'',//事件性质;
        noticeLevel:'',//事件等级;
        evenStyle:'',//事件类型;
        evenLevel:'',//事件级别;
        evenTypes:[],//事件体系;
        noticeContent:''//事件描述;
    },
    viewData:{//存储姓名;
        initiator:[],//发起人;
        teachers:[],//参与教师;
        students:[],//参与学生;
    },
    opt:{
        noticeLevel:[],//事件等级;
        evenStyle:[],//事件类型;
        evenLevel:[],//事件级别;
        evenTypes:[],//事件体系;
    },
    loadEvent:function(){
        $.ajax({
            url: '${ctx}/dict/all',
            type: 'GET',
            dataType: 'json',
            success:function(data){
                var _data = data.result;
                vmForm.opt.noticeLevel = _data.cims_event_level;//事件等级;
                vmForm.opt.evenStyle = _data.even_style;//事件类型;
                vmForm.opt.evenLevel = _data.even_level;//事件级别;
                vmForm.opt.evenTypes = _data.even_type;//事件体系;
            }
        });
    },
    addCommonFn:function(template,_type){
        var opt = {
            url:''
        }
        switch(template){
            case 'address':
                opt.url = '${ctx}/notice/layer/address';
                break;
            case 'people':
                opt.url = '${ctx}/notice/layer/people';
                break;
        };
        $.post(opt.url,{async:false,cache:false},function(str) {
            layer.open({
                type: 1,
                area:'755px',
                content: str,
                btn: ['确定','取消'],
                btnAlign :'c',
                scrollbar: false,
                success:function(){
                    switch(_type){
                        case 'place':
                            vmAddress.port = {
                                type:'place',
                                echoData:'',
                                treeUrl:'',
                                url:'${ctx}/tree/space/all'
                            };
                            vmAddress.loadListFn(vmAddress.firstActive);
                            break;
                        case 'initiator':
                            var arr = [];
                            arr.push(vmForm.$model.evenData.initiator);
                            vmPeople.userId = arr;
                            vmPeople.port = {
                                type:'initiator',
                                echoData:{
                                    userId:vmForm.$model.evenData.initiator
                                },
                                treeUrl:'${ctx}/tree/all',
                                url:'${ctx}/tree/teacher/search'
                            };
                            setting.data = {
                                key:{
                                    title:'text',
                                    name:'text'
                                },
                                simpleData: {
                                    enable: true,
                                    idKey: "id",
                                    pIdKey: "parentId",
                                    rootPId: 0
                                }
                            };
                            vmPeople.ztreeList();
                            vmPeople.dataListFn();
                            break;
                        case 'teachers':
                            vmPeople.userId = vmForm.$model.evenData.teachers;
                            vmPeople.port = {
                                type:'teachers',
                                echoData:{
                                    userId:vmForm.$model.evenData.teachers,
                                    name:vmForm.$model.viewData.teachers
                                },
                                treeUrl:'${ctx}/tree/all',
                                url:'${ctx}/tree/teacher/search'
                            };
                            setting.data = {
                                key:{
                                    title:'text',
                                    name:'text'
                                },
                                simpleData: {
                                    enable: true,
                                    idKey: "id",
                                    pIdKey: "parentId",
                                    rootPId: 0
                                }
                            };
                            vmPeople.ztreeList();
                            vmPeople.dataListFn();
                            break;
                        case 'students':
                            vmPeople.userId = vmForm.$model.evenData.students;
                            vmPeople.port = {
                                type:'students',
                                echoData:{
                                    userId:vmForm.$model.evenData.students,
                                    name:vmForm.$model.viewData.students
                                },
                                treeUrl:'${ctx}/tree/school',
                                url:'${ctx}/tree/student/search'
                            };
                            setting.data = {
                                key:{
                                    title:'nodeName',
                                    name:'nodeName',
                                    children: "subTree"
                                },
                                simpleData: {
                                    idKey: "id"
                                }
                            };
                            vmPeople.ztreeList();
                            vmPeople.dataListFn();
                            break;
                    }
                    avalon.scan(document.body);
                },
                yes:function(index,layero){
                    switch(_type){
                        case 'place':
                            vmForm.evenData.place = vmAddress.$model.port.echoData;
                            $("#place").focus();
                            $("#place").blur();
                            break;
                        case 'initiator':
                            vmForm.evenData.initiator = vmPeople.$model.port.echoData.userId;
                            var arr = [];
                            //后台只能接受数组，而单选框传回来的是字符串，不选择又传回数组，所以这里判断下
                            if(vmPeople.$model.port.echoData.userId instanceof Array){
                                arr = vmPeople.$model.port.echoData.userId;
                            }else{
                                arr.push(vmPeople.$model.port.echoData.userId);
                            }
                            $.ajax({
                                url: '${commonProjectUrl}/api/teachers',
                                type: 'POST',
                                dataType: 'json',
                                data:{"teacherIds":arr},
                                success:function(data){
                                    if(data.code!='0'){
                                        console.log(data.msg);
                                        return ;
                                    }
                                    if(data.result[0].bm !=null){
                                        var nameStr = data.result[i].teacherName+"("+data.result[0].bm+")";
                                        $("#initiator").val(nameStr);
                                    }else{
                                        $("#initiator").val(data.result[0].teacherName);
                                    }
                                    $("#initiator").focus();
                                    $("#initiator").blur();
                                }
                            });
                        break;
                        case 'teachers':
                            vmForm.evenData.teachers = vmPeople.$model.userId;
                            if(vmForm.evenData.teachers.length==0){
                                vmForm.viewData.teachers=[];
                                break;
                            }
                            showTeacherNames();
                        break;
                        case 'students':
                            vmForm.evenData.students = vmPeople.$model.userId;
                            if(vmForm.evenData.students.length==0){
                                vmForm.viewData.students=[];
                                break;
                            }
                            showStudentNames();
                        break;
                    }
                    layer.close(index);
                }
            });
        });
    },
    submitEventFn:function(){
        $.ajax({
            url: '${ctx}/notice/add',
            type: 'POST',
            data: this.$model.evenData,
            beforeSend:function(){
                index = layer.load(2, {shade: false});
            },
            success:function(data){
                layer.close(index);
                if(data.code!=0){
                    layer.msg(data.msg, {icon: 7},function(){
                    });
                    return ;
                }
                layer.msg(data.msg,{time:500},function(){
                    safelyGoToLoaction("${ctx}/notice/to/list");
                });
            },
            error:function(data){
                layer.msg("添加失败，请联系管理员",function(){
                    window.location.reload()
                });
            }
        })
    },
    enclosureListFn:function(){//附件列表;
        $.ajax({
            url:'${ctx}/notice/files/'+ vmForm.evenData.id,
            type:'POST',
            dataType:'json',
            data:this.$model._data,
            success:function(data){
                var fileData = data.result;
                vmForm.fileList = fileData;
            }
        });
    },downloadFn:function(el){//附件下载;
        safelyGoToLoaction('${ctx}/notice/downLoad?fileId='+el.fileId);
    },
    delFn:function(el){//附件删除;
        $.ajax({
            url:'${ctx}/notice/file/del/'+ el.fileId,
            type:'POST',
            dataType:'json',
            success:function(){
                var _index = layer.confirm("<div class='text-center'>你确定要删除该文件吗?</div>",{title:'文件信息',btnAlign:'c'},function(){
                    vmForm.enclosureListFn();
                    layer.close(_index);
                });
            }
        });
    },
    clearFn:function(item){
        switch(item){
            case 'place':
                this.evenData.place =''//事件地点;
                break;
            case 'initiator':
                this.evenData.initiator =[]//事件发起人;
                this.viewData.initiator =[]//事件发起人;
                break;
            case 'teachers':
                this.evenData.teachers = []//参与教师;
                this.viewData.teachers = []//参与教师;
                break;
            case 'students':
                this.evenData.students = []//参与学生;
                this.viewData.students = []//参与学生;
                break;
        }
    }
});
layui.use(['layer','laydate'], function(){
    var laydate = layui.laydate,
        layer = layui.layer;
    laydate.render({
        elem: '#dateTime',
        type: 'datetime',
        range: true,
        theme: 'grid',
        min:0,
        done: function(value, date, endDate){
            $('#form-validator').validator({
                timely: 3,
                validClass: "has-succes",
                invalidClass: "has-error",
                bindClassTo: ".form-group",
                rules: {
                    //教师或学生选一个即可
                    pickStudentAndTeacher: function () {
                        var studentStr = $("#students").val();
                        var teacherStr = $("#teachers").val();
                        if(studentStr==''&&teacherStr==''){
                            return '教师和学生至少参加一人';
                        }
                        return true;
                    }
                },
                fields: {
                    'headline': {
                        rule: 'required;length(1~30)',//标题;
                        msg: {
                            required: '事件标题不能为空 !'
                        }
                    },
                    'dateTime':{
                        rule: 'required',//事件时间;
                        msg: {
                            required: '事件时间必填 !',
                            data: '请填写有效的日期时间，格式:yyyy-MM-dd HH:mm:ss'
                        }
                    },
                    'place':{
                        rule: 'required;length(1~50)',//事件地址;
                        msg: {
                            required: '事件地址必填 !'
                        }
                    },
                    'initiator': {
                        rule: 'required;',//发起人;
                        msg: {
                            required: '发起人不能为空 !'
                        }
                    },
                    'teachers': {
                        rule: 'pickStudentAndTeacher;',//参与教师;
                        must:true
                    },
                    'students': {
                        rule: 'pickStudentAndTeacher;',//参与教师;
                        must:true
                    },
                    'sjxz': {
                        rule: 'checked',//事件性质;
                        msg: {
                            checked: '事件性质必选一个 !'
                        },
                        msgClass:"block"
                    },
                    'sjdj': {
                        rule: 'checked',//事件等级;
                        msg: {
                            checked: '事件等级必选一个 !'
                        },
                        msgClass:"block"
                    },
                    'sjlx': {
                        rule: 'checked',//事件类型;
                        msg: {
                            checked: '事件类型必选一个 !'
                        },
                        msgClass:"block"
                    },
                    'level': {
                        rule: 'checked',//事件级别;
                        msg: {
                            checked: '事件级别必选一个 !'
                        },
                        msgClass:"block"
                    },
                    'evenTypes': {
                        rule: 'checked',//事件体系;
                        msg: {
                            checked: '事件体系必选一个 !'
                        },
                        msgClass:"block"
                    },
                    'description': {
                        rule: 'required;length(1~1000)',//事件描述;
                        msg: {
                            required: '事件描述不能为空 !'
                        }
                    }
                },
                valid: function(form){
                    vmForm.submitEventFn();
                }
            });
        }
    });
});

function showTeacherNames() {
    //通过userId查教师姓名
    $.ajax({
        url: '${commonProjectUrl}/api/teachers',
        type: 'POST',
        dataType: 'json',
        data:{"teacherIds":vmForm.evenData.teachers},
        success:function(data){
            if(data.code!='0'){
                console.log(data.msg);
                return ;
            }
            vmForm.viewData.teachers=[];
            for(var i=0;i<data.result.length;i++){
                if(data.result[i].bm !=null){
                    var nameStr = data.result[i].teacherName+"("+data.result[i].bm+")";
                    vmForm.viewData.teachers.push(nameStr);
                }else{
                    vmForm.viewData.teachers.push(data.result[i].teacherName);
                }
            }
            $("#students").focus();
            $("#students").blur();
            $("#teachers").focus();
            $("#teachers").blur();
        }
    });
}

function showStudentNames() {
    //通过userId查学生姓名
    $.ajax({
        url: '${commonProjectUrl}/api/students',
        type: 'POST',
        dataType: 'json',
        data:{"studentIds":vmForm.evenData.students},
        success:function(data){
            if(data.code!='0'){
                console.log(data.msg);
                return ;
            }
            vmForm.viewData.students=[];
            for(var i=0;i<data.result.length;i++){
                vmForm.viewData.students.push(data.result[i].xm);
            }
            $("#teachers").focus();
            $("#teachers").blur();
            $("#students").focus();
            $("#students").blur();
        }
    });
}

// 初始化验证
$(function(){
    $('#form-validator').validator({
        timely: 3,
        validClass: "has-succes",
        invalidClass: "has-error",
        bindClassTo: ".form-group",
        rules: {
            //教师或学生选一个即可
            pickStudentAndTeacher: function () {
                var studentStr = $("#students").val();
                var teacherStr = $("#teachers").val();
                if(studentStr==''&&teacherStr==''){
                    return '教师和学生至少参加一人';
                }
                return true;
            }
        },
        fields: {
            'headline': {
                rule: 'required;length(1~30)',//标题;
                msg: {
                    required: '事件标题不能为空 !'
                }
            },
            'dateTime':{
                rule: 'required',//事件时间;
                msg: {
                    required: '事件时间必填 !',
                    data: '请填写有效的日期时间，格式:yyyy-MM-dd HH:mm:ss'
                }
            },
            'place':{
                rule: 'required;length(1~50)',//事件地址;
                msg: {
                    required: '事件地址必填 !'
                }
            },
            'initiator': {
                rule: 'required;',//发起人;
                msg: {
                    required: '发起人不能为空 !'
                }
            },
            'teachers': {
                rule: 'pickStudentAndTeacher;',//参与教师;
                must:true
            },
            'students': {
                rule: 'pickStudentAndTeacher;',//参与教师;
                must:true
            },
            'sjxz': {
                rule: 'checked',//事件性质;
                msg: {
                    checked: '事件性质必选一个 !'
                },
                msgClass:"block"
            },
            'sjdj': {
                rule: 'checked',//事件等级;
                msg: {
                    checked: '事件等级必选一个 !'
                },
                msgClass:"block"
            },
            'sjlx': {
                rule: 'checked',//事件类型;
                msg: {
                    checked: '事件类型必选一个 !'
                },
                msgClass:"block"
            },
            'level': {
                rule: 'checked',//事件级别;
                msg: {
                    checked: '事件级别必选一个 !'
                },
                msgClass:"block"
            },
            'evenTypes': {
                rule: 'checked',//事件体系;
                msg: {
                    checked: '事件体系必选一个 !'
                },
                msgClass:"block"
            },
            'description': {
                rule: 'required;length(1~1000)',//事件描述;
                msg: {
                    required: '事件描述不能为空 !'
                }
            }
        },
        valid: function(form){
            vmForm.submitEventFn();
        }
    });
});
$(function(){
    vmForm.loadEvent();
    // Handle the case when form was submitted before uploading has finished
    $('#form-horizontal').submit(function(e) {
        // Files in queue upload them first
        if ($('#uploader').plupload('getFiles').length > 0) {

            // When all files are uploaded submit form
            $('#uploader').on('complete', function() {
                $('#form-horizontal')[0].submit();
            });

            $('#uploader').plupload('start');
        } else {
            alert("You must have at least one file in the queue.");
        }
        return false; // Keep the form from submitting
    });
})

function refleshEventFile() {
    vmForm.enclosureListFn();
}
/**随机生成UUID唯一标识码*/
vmForm.evenData.id = uuid();
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 32; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    var uuid = s.join("");
    return uuid;
}
var _ID = vmForm.evenData.id;
var _isNew = true;
</script>
<!--修改的情况-->
<#if model=='event_update'>
    <script>
         vmForm.evenData.id='${courseNotice.id}';//事件ID;
         _ID = vmForm.evenData.id;
         vmForm.evenData.noticeTitle='${courseNotice.noticeTitle}';//事件标题;
         vmForm.evenData.place='${courseNotice.place}';//事件地点;
         vmForm.evenData.initiator='${courseNotice.creater}';//发起人;
         vmForm.evenData.evenProperty='${courseNotice.evenProperty}';//事件性质;
         vmForm.evenData.noticeLevel='${courseNotice.noticeLevel}';//事件等级;
         vmForm.evenData.evenStyle='${courseNotice.evenStyle}';//事件类型;
         vmForm.evenData.evenLevel='${courseNotice.evenLevel}';//事件级别;
         vmForm.evenData.evenTypes='${courseNotice.evenType}'.split(",");//事件体系;
         vmForm.evenData.noticeContent='${courseNotice.noticeContent}';//事件描述;
         var evenTime = '${courseNotice.noticeStart?string("yyyy-MM-dd HH:mm:ss")}' + ' - ' + '${courseNotice.noticeEnd?string("yyyy-MM-dd HH:mm:ss")}';
         vmForm.evenData.evenTime = evenTime; //事件时间;
         vmForm.evenData.teachers = '${teacherIds}'.split(",");//参与教师;
         vmForm.evenData.students = '${studentIds}'.split(",");//参与学生;
         showTeacherNames();
         showStudentNames();
         vmForm.enclosureListFn();
         //发起人姓名
         $.ajax({
             url: '${commonProjectUrl}/api/teachers',
             type: 'POST',
             dataType: 'json',
             data:{"teacherIds":vmForm.evenData.initiator.split()},
             success:function(data){
                 if(data.code!='0'){
                     console.log(data.msg);
                     return ;
                 }else{
                     console.log(data.result);
                     $("#initiator").val(data.result[0].teacherName);
                 }
             }
         });
    </script>
</#if>
<#include "../common-tpl/upload.ftl"/>

