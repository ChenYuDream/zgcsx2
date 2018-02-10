<!--资源tab-->
<div ms-controller="uploadResourse" class="ms-controller">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li :if="@opt.tab != 'student'">
                    <span>文件类型：</span>
                    <select ms-duplex="@opt.uploadFileType" class="form-control input-sm" style="width:120px;">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @uploadFileTypes" :attr="{value:el.itemValue}" :if="el.itemValue < 100">{{el.itemText}}</option>
                    </select>
                </li>
                <li :if="@opt.tab == 'student'">
                    <span>文件类型：</span>
                    <select ms-duplex="@opt.uploadFileType" class="form-control input-sm" style="width:120px;">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @uploadFileTypes" :attr="{value:el.itemValue}" :if="el.itemValue > 100">{{el.itemText}}</option>
                    </select>
                </li>
                <li :if="@opt.tab != 'student'">
                    <span>上传对象：</span>
                    <select ms-duplex="@opt.uploadType" class="form-control input-sm" style="width:120px;">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @uploadTypes" :attr="{value:el.id}">{{el.name}}</option>
                    </select>
                </li>
                <li :if="@opt.tab == 'student'">
                    <span>上传人：</span>
                    <input :duplex="@opt.studentName" class="form-control input-sm" style="width:120px;">
                </li>
                <li :if="@show">
                    <span>基础层课程：</span>
                    <select ms-duplex="@opt.courseId" data-duplex-changed="@duplexFn($event,'basic')"  class="form-control input-sm js-courseId" style="width:120px;">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @basicCourse" :attr="{value:el.id}">{{el.courseName}}</option>
                    </select>
                </li>
                <li :if="@show">
                    <span>拓展/开放层课程：</span>
                    <select ms-duplex="@opt.optionalCourseId" data-duplex-changed="@duplexFn($event,'choose')" class="form-control input-sm js-optionalCourseId" style="width: 120px;">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @chooseCourse" :attr="{value:el.optionalCourseId}">{{formatChooseCourseName(el.courseName, el.aliasName)}}</option>
                    </select>
                </li>
            </ul>
        </div>
        <hr/>
        <div class="clearfix item-group">
            <div class="toolList text-center">
                <a :click="@init('find')" class="item-btn" href="javascript:void(0);">
                    <i class="iconfont icon-chazhao"></i>查找
                </a>
                <a :click="@clear()" class="item-btn" href="javascript:void(0);">
                    <i class="iconfont icon-qingkong"></i>清空
                </a>
            </div>
        </div>
    </form>
    <!-- 查询条件 E-->
    <!-- 我的资源 S-->
    <div class="wdzy-wrap">
        <fieldset class="layui-elem-field">
            <legend>已上传资源（缩略图）</legend>
            <div class="layui-field-box">
                <blockquote class="layui-elem-quote">温馨提示：点击缩略图可预览图型类资源。</blockquote>
                <div class="litimgList clearfix">
                    <a href="javascript:void(0);" :for="(index, el) in @data.records">
                        <img :if="@validateImg(el.attachment.fileType)" :click="@showImg(el.attachment.id)" :attr="{src : '${ctx}/file/showImg?filePath='+el.attachment.filePathS}">
                        <p :if="@validateImg(el.attachment.fileType)" :click="@showImg(el.attachment.id)" :attr="{title:el.attachment.fileName}" class="text-overflow text-center text-primary">{{el.attachment.fileName | truncate(15,'...')}}</p>

                        <img :if="!validateImg(el.attachment.fileType)" :click="@downLoad(el.attachment.id)" src="${ctx}/static/common/images/wenjian.png">
                        <p :if="!validateImg(el.attachment.fileType)" :click="@downLoad(el.attachment.id)" :attr="{title:el.attachment.fileName}"class="text-overflow text-center text-primary">{{el.attachment.fileName | truncate(15,'...')}}</p>
                    </a>
                </div>
            </div>
        </fieldset>
        <fieldset class="layui-elem-field">
            <legend>已上传资源（文件列表）</legend>
            <div class="layui-field-box">
                <!-- 评价确定按钮 -->
                <div :if="@opt.tab == 'student' && showEvaluate == true && @data.pages > 0" class="clearfix pull-right">
                    <button class="btn btn-primary btn-sm" type="button" style="margin: 10px 5px;" :click="updateEvaluate()">保存</button>
                </div>

                <div class="downloadList table-content">
                    <table class="table">
                        <thead>
                        <#--教师查看自己上传资源-->
                        <tr :if="@opt.tab == 'res'">
                            <th width="25%">文件名称</th>
                            <th width="15%">文件类型</th>
                            <th width="15%">上传对象</th>
                            <th width="20%">上传人</th>
                            <th width="20%">上传时间</th>
                            <th width="8%">操作</th>
                        </tr>
                        <#--教师查看学生上传资源-->
                        <tr :if="@opt.tab == 'student'">
                            <th width="20%">文件名称</th>
                            <th width="10%">文件类型</th>
                            <th width="10%">上传人</th>
                            <th width="10%">所在校区</th>
                            <th width="10%">所在年级</th>
                            <th width="10%">所在班级</th>
                            <th width="17%">上传时间</th>
                            <th width="13%">评价</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr :if="@opt.tab == 'res'" :for="(index, el) in @data.records">
                            <td :attr="{title:el.attachment.fileName}">
                                <a :if="@validateImg(el.attachment.fileType)" :click="@showImg(el.attachment.id)" :attr="{title:el.attachment.fileName}" class="text-primary" href="javascript:void(0);">
                                    <b>{{el.attachment.fileName | truncate(15,'...')}}</b>
                                </a>
                                <a :if="!validateImg(el.attachment.fileType)" :click="@downLoad(el.attachment.id)" :attr="{title:el.attachment.fileName}" class="text-primary" href="javascript:void(0);">
                                    <b>{{el.attachment.fileName | truncate(15,'...')}}</b>
                                </a>
                            </td>
                            <td :attr="{title:el.uploadFileName}">{{el.uploadFileName}}</td>
                            <td :attr="{title:{'1':'年级','2':'班级'}[el.uploadType]}">{{{'1':'年级','2':'班级'}[el.uploadType]}}</td>
                            <td :attr="{title:el.attachment.userName}">{{el.attachment.userName}}</td>
                            <td :attr="{title:el.attachment.uploadTime}">{{el.attachment.uploadTime | date("yyyy-MM-dd HH:mm:ss")}}</td>
                            <td><button type="button" class="btn btn-danger btn-xs" :click="@delete(el.id, el.attachmentId)">删除</button></td>
                        </tr>
                        <tr :if="@opt.tab == 'student'" :for="(index, el) in @data.records">
                            <td :attr="{title:el.attachment.fileName}">
                                <a :if="@validateImg(el.attachment.fileType)" :click="@showImg(el.attachment.id)" :attr="{title:el.attachment.fileName}" class="text-primary" href="javascript:void(0);">
                                    <b>{{el.attachment.fileName | truncate(15,'...')}}</b>
                                </a>
                                <a :if="!validateImg(el.attachment.fileType)" :click="@downLoad(el.attachment.id)" :attr="{title:el.attachment.fileName}" class="text-primary" href="javascript:void(0);">
                                    <b>{{el.attachment.fileName | truncate(15,'...')}}</b>
                                </a>
                            </td>
                            <td :attr="{title:el.uploadFileName}">{{el.uploadFileName}}</td>
                            <td :attr="{title:el.attachment.userName}">{{el.attachment.userName}}</td>
                            <td :attr="{title:el.campusName}">{{el.campusName}}</td>
                            <td :attr="{title:el.gradeName}">{{el.gradeName}}</td>
                            <td :attr="{title:el.clazzName}">{{el.clazzName}}</td>
                            <td :attr="{title:el.attachment.uploadTime}">{{el.attachment.uploadTime | date("yyyy-MM-dd HH:mm:ss")}}</td>
                            <td>
                                <select :attr="{disabled : !showEvaluate}" :duplex="@el.evaluate.state" class="form-control input-sm" name="evaluate_teacher">
                                    <option value="" :if="el.evaluate.state == ''">未评价</option>
                                    <option :for="ev in @evaluate" :attr="{value:ev.itemValue, studentId:el.userId, courseResourceId:el.id, evaluateId:el.evaluate.id}">{{ev.itemText}}</option>
                                </select>
                            </td>
                        </tr>

                        <tr :if="@opt.tab == 'res' && data.pages == 0">
                            <td colspan="5">暂无相关数据！</td>
                        </tr>
                        <tr :if="@opt.tab == 'student' && data.pages == 0">
                            <td colspan="8">暂无相关数据！</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </fieldset>
    </div>
    <!-- 我的资源 E-->

    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox" :if="@data.pages > 1">
        <div id="pagination" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->
</div>

<script type="text/javascript">
    var vmUploadResource = avalon.define({
        $id: 'uploadResourse',
        current : 1,
        size : 10,
        data : {},
        uploadFileTypes : [],
        uploadTypes  : [{id: 1, name : "年级"}, {id: 2, name : "班级"}],
        basicCourse  : [],
        chooseCourse : [],
        evaluate : [], //评价列
        opt: {
            tab : 'res',  //点击的tab列(默认为我的资源)
            studentName : '', //查看学生资源：上传人
            uploadFileType : '', //文件类型
            uploadType : '',     //上传对象（1、年级  2、班级）
            createType : '',     //操作人类型(1、学生  2、教师)
            courseId : '',       //课程id
            optionalCourseId : '', //开放课程id
            clazzId : '',          //班级id
            gradeId : ''           //年级id
        },
        dataOpt: {
            studentName: '', //查看学生资源：上传人
            uploadFileType : '', //文件类型
            uploadType : '',     //上传对象（1、年级  2、班级）
            createType : '',     //操作人类型(1、学生  2、教师)
            courseId : '',       //课程id
            optionalCourseId : '', //开放课程id
            clazzId : '',          //班级id
            gradeId : ''           //年级id
        },
        userId : "${onlineUser.id}",
        show : false, //是否显示后两个查询条件
        showEvaluate : false, //是否显示评价按钮
        duplexFn : function (el, type) {
            switch (type) {
                case 'basic':
                    if (this.opt.courseId) {
                        vmUploadResource.opt.optionalCourseId = "";
                        $(".js-optionalCourseId").attr("disabled", true);
                    }else{
                        $(".js-optionalCourseId").attr("disabled", false);
                    }
                    break;
                case 'choose':
                    if (this.opt.optionalCourseId) {
                        vmUploadResource.opt.courseId = "";
                        $(".js-courseId").attr("disabled", true);
                    }else{
                        $(".js-courseId").attr("disabled", false);
                    }
                    break;
            }
        },
        delete : function (id, attachmentId) { // 删除
            msgFn.confirm({msg:'您确认要删除选中的资源吗？',title:'删除资源',callback:function(){
                $.ajax({
                    url: "${ctx}/courseResource/delete",
                    data : {id : id, attachmentId : attachmentId},
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        msgFn.msg(data);
                        if(data.success){
                            vmUploadResource.current = 1;
                            vmUploadResource.init();
                        }
                    }
                });
            }});
        },
        clear: function () {  //清空
            vmUploadResource.opt.uploadFileType = "";
            vmUploadResource.opt.uploadType = "";
            vmUploadResource.opt.createType = "";
            vmUploadResource.opt.studentName = "";
            if(vmUploadResource.$model.show){ //只有当从我上传的资源页面进入时，才清空下面列
                vmUploadResource.opt.courseId = "";
                vmUploadResource.opt.optionalCourseId = "";
                vmUploadResource.opt.clazzId = "";
                vmUploadResource.opt.gradeId = "";
                $(".js-courseId").attr("disabled", false);
                $(".js-optionalCourseId").attr("disabled", false);
            }
            if(vmCourseResource.$model.opt.tab === "student"){ //查询学生上传的资源
                vmUploadResource.userId = "";
                vmUploadResource.opt.createType = "1";
            }
            vmUploadResource.dataOpt = vmUploadResource.$model.opt;
        },
        validateImg : function(fileType){ //是否为图片类型
            if(fileType.toLowerCase().indexOf("jpg") !== -1  || fileType.toLowerCase().indexOf("jpeg") !== -1 ||
               fileType.toLowerCase().indexOf("gif") !== -1 || fileType.toLowerCase().indexOf("png") !== -1){
                return true;
            }
            return false;
        },
        showImg: function(id){ //显示图片
            msgFn.dlg({
                url: "${ctx}/file/page/img?id="+id,
                title: "预览",
                width : "1000px",
                height : "600px"
            });
        },
        downLoad : function(id){
            var url = "${ctx}/file/downLoad/"+id;
            safelyGoToLoaction(url);
        },
        loadCodeMap : function () { //文件类型
            $.ajax({
                url: "${ctx}/api/codemap",
                data : {code : "sys_fileType"},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmUploadResource.uploadFileTypes = data;
                }
            });
        },
        loadBasicCourse : function () { //基础层课程
            $.ajax({
                url: "${ctx}/courseResource/basic/teacher/course",
                data : {teacherId : this.userId},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmUploadResource.basicCourse = data.result;
                }
            });
        },
        loadChooseCourse : function () { //拓展/开放层课程
            $.ajax({
                url: "${ctx}/courseResource/choose/teacher/course",
                data : {teacherId : this.userId},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmUploadResource.chooseCourse = data.result;
                }
            });
        },
        validateEvaluate : function(){ //验证是否显示评价按钮
            if(vmUploadResource.$model.dataOpt.optionalCourseId){
                $.ajax({
                    url: "${ctx}/courseResource/validate/evaluate",
                    type: 'POST',
                    data : {id : vmUploadResource.$model.dataOpt.optionalCourseId},
                    dataType: 'json',
                    success: function (data) {
                        vmUploadResource.showEvaluate = data;
                    }
                });
            }
        },
        loadEvaluate : function (callback) { //加载评价下拉
            $.ajax({
                url: "${ctx}/api/evaluate",
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmUploadResource.evaluate = data;

                    callback();
                }
            });
        },
        updateEvaluate : function(){ //编辑评价
            msgFn.confirm({msg:'确认要保存评价吗？',title:'评价',callback:function(){
                var evaluates = new Array();
                $("select[name='evaluate_teacher'] option:selected").each(function(index, value){
                    var evaluateId = $(this).attr("evaluateId");
                    var val = $(this).val();
                    if(val){
                        evaluates.push(
                                {
                                    evaluateId : evaluateId,  //评价id
                                    courseResourceId : $(this).attr("courseResourceId"), //课程资源id
                                    state : val,  //状态值
                                    studentId : $(this).attr("studentId")//被评论的学生
                                }
                        );
                    }
                });

                $.ajax({
                    url: "${ctx}/courseResource/edit/evaluate",
                    data : {evaluates : evaluates},
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        msgFn.msg(data);
                        vmUploadResource.init();
                    }
                });
            }});
        },
        init : function (type) {
            if(type === 'find'){       //点击了查询按钮
                vmUploadResource.current = 1;
                vmUploadResource.dataOpt = vmUploadResource.$model.opt;
            }

            var $data = {
                current : this.current,
                size : this.size,
                userId : this.userId,
                studentName : this.dataOpt.studentName,
                uploadFileType : this.dataOpt.uploadFileType,
                uploadType : this.dataOpt.uploadType,
                createType : this.dataOpt.createType,
                courseId : this.dataOpt.courseId,
                optionalCourseId : this.dataOpt.optionalCourseId,
                clazzId : this.dataOpt.clazzId,
                gradeId : this.dataOpt.gradeId
            };
            $.ajax({
                url: "${ctx}/courseResource/course/resource",
                data : $data,
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    var _data = data.result;
                    for (var i = 0; i < _data.records.length; i++) {
                        var list = _data.records[i];
                        if(typeof(_data.records[i].evaluate) === "undefined"){
                            var obj = {};
                            obj.id = "";
                            obj.state = "";
                            list.evaluate = obj;
                        }
                    }
                    vmUploadResource.data = _data;

                    if(vmUploadResource.$model.show){
                        if(vmUploadResource.$model.dataOpt.courseId){
                            $(".js-optionalCourseId").attr("disabled", false);
                        }
                        if(vmUploadResource.$model.dataOpt.optionalCourseId){
                            $(".js-courseId").attr("disabled", false);
                        }
                    }
                    mainVm.renderPage(vmUploadResource.$model.data, function(curr){
                        vmUploadResource.current = curr;
                        vmUploadResource.init();
                    });
                }
            });
        },
        initFirst : function(){ //首次加载需要顺序的数据
            vmUploadResource.loadEvaluate(function(){vmUploadResource.init()});
        },
        loadOpt : function(){ //加载参数
            if(typeof(vmCourseResource) !== "undefined"){
                vmUploadResource.opt.tab = vmCourseResource.$model.opt.tab;
                vmUploadResource.opt.courseId = vmCourseResource.$model.opt.courseId;
                vmUploadResource.opt.optionalCourseId = vmCourseResource.$model.opt.optionalCourseId;
                vmUploadResource.opt.clazzId = vmCourseResource.$model.opt.clazzId;
                vmUploadResource.opt.gradeId = vmCourseResource.$model.opt.gradeId;
                if(vmCourseResource.$model.opt.tab === "student"){ //查询学生上传的资源
                    vmUploadResource.userId = "";
                    vmUploadResource.opt.createType = "1";
                }

                vmUploadResource.dataOpt = vmUploadResource.$model.opt;
            }
        }
    });
    vmUploadResource.loadOpt();
    vmUploadResource.validateEvaluate();
    vmUploadResource.loadCodeMap();
    vmUploadResource.loadBasicCourse();
    vmUploadResource.loadChooseCourse();
    vmUploadResource.initFirst();
</script>