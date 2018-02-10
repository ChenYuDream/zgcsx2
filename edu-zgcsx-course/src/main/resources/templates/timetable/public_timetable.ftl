<#include "/layout/layout.ftl"/>
<@page title="公共课表" breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"公共课表"}]>
<div ms-controller="publicTimetable" class="ms-controller">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>校区：</span>
                    <select ms-duplex="@opt.campusId" data-duplex-changed="@duplexFn($event,'campus')" :attr="{value:'== 请选择 =='}" class="form-control input-sm" style="width:100px;">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @campus" :attr="{value:el.id}">{{el.name}}
                        </option>
                    </select>
                </li>
                <li>
                    <span>年级：</span>
                    <select :duplex="@opt.gradeId" data-duplex-changed="@duplexFn($event,'grade')" :attr="{value:'== 请选择 =='}" class="form-control input-sm" style="width:100px;">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @grade" :attr="{value:el.id}">{{el.name}}</option>
                    </select>
                </li>
                <li>
                    <span>班级：</span>
                    <select :duplex="@opt.clazzId" :attr="{value:'== 请选择 =='}" class="form-control input-sm" style="width:100px;">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @clazz" :attr="{value:el.id}">{{el.name}}(班)</option>
                    </select>
                </li>
            </ul>
            <div class="toolList pull-left">
                <ul class="clearfix">
                    <li>
                        <a :click="@init()" class="item-btn" href="javascript:void(0);">
                            <i class="iconfont icon-chazhao"></i>查找
                        </a>
                    </li>
                    <li>
                        <a :click="@clear()" class="item-btn" href="javascript:void(0);">
                            <i class="iconfont icon-qingkong"></i>清空
                        </a>
                    </li>
                    <li>
                        <a :click="@export" class="item-btn" href="javascript:void(0);">
                            <i class="iconfont icon-quanbudaochu"></i>导出班级
                        </a>
                    </li>
                    <li>
                        <a :click="@exportAll" class="item-btn" href="javascript:void(0);">
                            <i class="iconfont icon-quanbudaochu"></i>导出校区
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </form>
    <!-- 查询条件 E-->
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="timeTable">
            <thead>
            <tr>
                <th width="12%">星期</th>
                <th width="12%">周一</th>
                <th width="12%">周二</th>
                <th width="12%">周三</th>
                <th width="12%">周四</th>
                <th width="12%">周五</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="el in @defaultX">
                <td :for="elem in @defaultX">
                    <span>
                        <p :if="elem == 0">{{el+1}}</p>
                    </span>
                    <span :attr="{id:elem + '-'+ (el+1)}" class="js-clear">
                        <p :if="elem != 0">-</p>
                    </span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    var _opt = {
        campusId: '',//校区
        gradeId: '',//年级
        clazzId: '' //班级
    }
    var vmPublicTimetable = avalon.define({
        $id: 'publicTimetable',
        campus : [], //校区
        grade  : [], //年级
        clazz  : [], //班级
        data   : [],
        opt: _opt,
        defaultOpt : {
            campusId: '',
            gradeId: '',
            clazzId: '',
            type : true //标记clazz首次加载时初始化init()
        }, //记录初始校区，年级，班级
        defaultX: [0,1,2,3,4,5], //占位
        defaultY: [0,1,2,3,4,5], //占位
        showData : {}, //弹出框数据
        clearDefaultOpt : function(){ //清空默认值
            vmPublicTimetable.defaultOpt.campusId = "";
            vmPublicTimetable.defaultOpt.gradeId = "";
            vmPublicTimetable.defaultOpt.clazzId = "";
            vmPublicTimetable.defaultOpt.type = false;
        },
        duplexFn: function (e, type) {
            switch (type) {
                case 'campus':
                    if(this.opt.campusId){
                        vmPublicTimetable.loadGrade();
                    }else{
                        vmPublicTimetable.grade = [];
                        vmPublicTimetable.clazz = [];
                    }
                    vmPublicTimetable.opt.gradeId = "";
                    vmPublicTimetable.opt.clazzId = "";

                    vmPublicTimetable.clearDefaultOpt();
                    break;
                case 'grade':
                    if(this.opt.gradeId){
                        vmPublicTimetable.loadClazz();
                    }else{
                        vmPublicTimetable.clazz = [];
                    }
                    vmPublicTimetable.opt.clazzId = "";

                    vmPublicTimetable.clearDefaultOpt();
                    break;
            }
        },
        loadTeacherOwn : function(){ //获取教师的默认 校区，年级， 班级
            $.ajax({
                url: "${ctx}/teacher/teacher/own",
                data : {teacherId : "${onlineUser.id}"},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    if(data.success && data.result.length > 0){
                        var own = data.result[0];
                        vmPublicTimetable.defaultOpt.campusId = own.campusId;
                        vmPublicTimetable.defaultOpt.gradeId = own.gradeId;
                        vmPublicTimetable.defaultOpt.clazzId = own.clazzId;
                    }

                    vmPublicTimetable.loadCampus();
                }
            });
        },
        loadCampus : function () {
            $.ajax({
                url: "${ctx}/api/campus",
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmPublicTimetable.campus = data.result;

                    if(vmPublicTimetable.$model.defaultOpt.campusId){
                        vmPublicTimetable.opt.campusId = vmPublicTimetable.$model.defaultOpt.campusId;
                        vmPublicTimetable.loadGrade();
                    }
                }
            });
        },
        loadGrade : function () {
            if(this.opt.campusId){
                $.ajax({
                    url: "${ctx}/api/grade",
                    data : {campusId : this.opt.campusId},
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        vmPublicTimetable.grade = data.result;

                        if(vmPublicTimetable.$model.defaultOpt.gradeId){
                            vmPublicTimetable.opt.gradeId = vmPublicTimetable.$model.defaultOpt.gradeId;
                            vmPublicTimetable.loadClazz();
                        }
                    }
                });
            }
        },
        loadClazz : function () {
            if(this.opt.campusId && this.opt.gradeId){
                $.ajax({
                    url: "${ctx}/api/clazz",
                    data : {campusId : this.opt.campusId, gradeId : this.opt.gradeId},
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        vmPublicTimetable.clazz = data.result;

                        if(vmPublicTimetable.$model.defaultOpt.type){
                            vmPublicTimetable.opt.clazzId = vmPublicTimetable.$model.defaultOpt.clazzId;
                            vmPublicTimetable.defaultOpt.type = false;
                            vmPublicTimetable.init();
                        }
                    }
                });
            }
        },
        clear : function(){
            vmPublicTimetable.opt = _opt;
        },
        export : function(){ //导出
            if(this.opt.campusId === ""){
                return msgFn.msg(false, "校区不能为空！");
            }
            if(this.opt.gradeId === ""){
                return msgFn.msg(false, "年级不能为空！");
            }
            if(this.opt.clazzId === ""){
                return msgFn.msg(false, "班级不能为空！");
            }
            window.location.href = "${ctx}/teacher/public/timetable/export?clazzId="+this.opt.clazzId+"&gradeId="+this.opt.gradeId+"&campusId="+this.opt.campusId;
        },
        exportAll : function(){
            if(this.opt.campusId === ""){
                return msgFn.msg(false, "校区不能为空！");
            }
            window.location.href = "${ctx}/teacher/public/timetable/exportAll?campusId="+this.opt.campusId;
        },
        init : function () {
            if(this.opt.campusId === ""){
                return msgFn.msg(false, "校区不能为空！");
            }
            if(this.opt.gradeId === ""){
                return msgFn.msg(false, "年级不能为空！");
            }
            if(this.opt.clazzId === ""){
                return msgFn.msg(false, "班级不能为空！");
            }

            if(this.opt.campusId && this.opt.gradeId && this.opt.clazzId) {
                $.ajax({
                    url: "${ctx}/teacher/public/timetable",
                    data : {campusId : this.opt.campusId, gradeId : this.opt.gradeId, clazzId : this.opt.clazzId},
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        var list = data.result;
                        vmPublicTimetable.data = list;

                        $(".js-clear").html(""); //清空数据

                        for (var i = 0; i < list.length; i++) {
                            var listi = list[i];
                            var showHtml = "";
                            if(listi.kcdj){
                                showHtml += "<h5><a herf=\"javascript:void(0);\" onclick='show("+ JSON.stringify(listi) +")';>"+ listi.courseName +"</h5>";
                            }else{
                                showHtml += "<h5>"+ listi.courseName +"</h5>";
                            }
                            $("#" + listi.workDay.dayOfWeek + "-" + listi.workDay.period).html(showHtml);
                        }
                    }
                });
            }
        }
    });

    vmPublicTimetable.loadTeacherOwn();

    //弹出框
    function show(el) {
        vmPublicTimetable.showData = el;
        msgFn.dlg({
            url: "${ctx}/course/page/public/timetable/detail",
            title: "详情",
            width: "600px",
            success:function(ok){
                vmPublicTimetableDetail.data = vmPublicTimetable.$model.showData;
                ok();
            }
        });
    }
</script>
</@page>