<#include "/layout/layout.ftl"/>
<@page title="课表信息查询" breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"课表信息查询"}]>
<div ms-controller="timetableDetail" class="ms-controller">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>校区：</span>
                    <select ms-duplex="@opt.campusId" data-duplex-changed="@duplexFn($event,'campus')" :attr="{value:'== 请选择 =='}" class="form-control input-sm">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @campus" :attr="{value:el.id}">{{el.name}}
                        </option>
                    </select>
                </li>
                <li>
                    <span>年级：</span>
                    <select :duplex="@opt.gradeId" data-duplex-changed="@duplexFn($event,'grade')" :attr="{value:'== 请选择 =='}" class="form-control input-sm">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @grade" :attr="{value:el.id}">{{el.name}}</option>
                    </select>
                </li>
                <li>
                    <span>班级：</span>
                    <select :duplex="@opt.clazzId" :attr="{value:'== 请选择 =='}" class="form-control input-sm">
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
                <td :for="elem in @defaultY">
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
        clazzId: '' //学科
    };
    var vmTimetableDetail = avalon.define({
        $id: 'timetableDetail',
        campus : [], //校区
        grade  : [], //年级
        clazz  : [], //班级
        data   : [],
        showData : {},
        opt: _opt,
        defaultX: [0,1,2,3,4,5], //占位
        defaultY: [0,1,2,3,4,5], //占位
        duplexFn: function (e, type) {
            switch (type) {
                case 'campus':
                    if(this.opt.campusId){
                        vmTimetableDetail.loadGrade();
                    }else{
                        vmTimetableDetail.grade = [];
                        vmTimetableDetail.clazz = [];
                    }
                    vmTimetableDetail.opt.gradeId = "";
                    vmTimetableDetail.opt.clazzId = "";
                    break;
                case 'grade':
                    if(this.opt.gradeId){
                        vmTimetableDetail.loadClazz();
                    }else{
                        vmTimetableDetail.clazz = [];
                    }
                    vmTimetableDetail.opt.clazzId = "";
                    break;
            }
        },
        loadCampus : function () {
            $.ajax({
                url: "${ctx}/api/campus",
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmTimetableDetail.campus = data.result;
                }
            });
        },
        loadGrade : function () {
            $.ajax({
                url: "${ctx}/api/grade",
                data : {campusId : this.opt.campusId},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmTimetableDetail.grade = data.result;
                }
            });
        },
        loadClazz : function () {
            $.ajax({
                url: "${ctx}/api/clazz",
                data : {campusId : this.opt.campusId, gradeId : this.opt.gradeId},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmTimetableDetail.clazz = data.result;
                }
            });
        },
        clear : function(){
            vmTimetableDetail.opt = _opt;
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
                        vmTimetableDetail.data = list;

                        $(".js-clear").html(""); //清空数据

                        for (var i = 0; i < list.length; i++) {
                            var listi = list[i];
                            var showHtml = "";
                            if(listi.kcdj){
                                showHtml += "<h5><a herf=\"javascript:void(0);\" onclick='show("+ JSON.stringify(listi) +")';>"+ listi.courseName +"</h5>";
                                showHtml += "<h5>";
                                if(listi.teachers){
                                    showHtml = setTeacher(listi, showHtml);
                                }else{
                                    showHtml += "<span style='color: red;'>[设置教师]</span>";
                                }
                                showHtml += "</h5>";
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

    vmTimetableDetail.loadCampus();

    //弹出框
    function show(el) {
        vmTimetableDetail.showData = el;
        msgFn.dlg({
            url: "${ctx}/course/page/timetable/detail",
            title: "详情",
            width: "600px"
        });
    }

    //修改任课教师
    function updateTeacher(clazzId, workDayId, teacherId) {
        msgFn.dlg({
            url: "${ctx}/course/page/teacher/list?campusId="+vmTimetableDetail.$model.opt.campusId+"&clazzId="+clazzId+"&workDayId="+workDayId+"&teacherId="+teacherId,
            title: "教师列表",
            width: "600px"
        });
    }

    /**
     * 拼接教师
     */
    function setTeacher(listi, showHtml){
        for(var j=0; j<listi.teachers.length; j++){
            if(listi.teachers[j]){
                if(j === 0){
                    showHtml += "<a herf=\"javascript:void(0);\" onclick=\"updateTeacher('"+ listi.clazzId + "','" + listi.workDay.id + "','" + listi.teachers[j].id +"');\">" + listi.teachers[j].name+"</a>";
                }else{
                    showHtml += "," + "<a herf=\"javascript:void(0);\" onclick=\"updateTeacher('"+ listi.clazzId + "','" + listi.workDay.id + "','" + listi.teachers[j].id +"');\">" + listi.teachers[j].name+"</a>";
                }
            }else{
                //showHtml += "<a herf=\"javascript:void(0);\" onclick=\"updateTeacher('"+ listi.id +"');\">" + "<span style='color: red;'>[设置教师]</span>";
                showHtml += "<a herf=\"javascript:void(0);\" onclick=\"updateTeacher('"+ listi.clazzId + "','" + listi.workDay.id + "','" + '' +"');\">" + "<span style='color: red;'>[设置教师]</span>";
            }
        }
        return showHtml;
    }
</script>
</@page>