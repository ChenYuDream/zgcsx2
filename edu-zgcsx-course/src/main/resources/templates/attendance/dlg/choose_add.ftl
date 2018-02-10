<#include "/layout/layout.ftl"/>
<!--添加开放课程考勤页面-->
<@page title="添加学生考勤" hasFooter=false breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"添加学生考勤"}]>
<div ms-controller="addChooseAttendanct" class="ms-controller">
    <!-- 课程详情 S-->
    <table class="detailTable">
        <tbody>
        <tr>
            <th width="10%">课程定义:</th>
            <td>
                {{course.courseName}}
            </td>
            <th width="10%">课程名称:</th>
            <td>
                {{course.aliasName}}
            </td>
        </tr>
        </tbody>
    </table>
    <!-- 课程详情 E-->
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th><input type="checkbox" ms-duplex-checked="@allchecked" data-duplex-changed="@checkAll">全选</th>
                <th width="20%">学生姓名</th>
                <th width="15%">性别</th>
                <th width="25%">班级</th>
                <th width="30%">缺勤原因</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index, el) in @data.records">
                <td>
                    <input type="checkbox" name="student" :click="@checkOwn" :attr="{value:el.id, studentId:el.student.id, id:'checkbox_'+el.id}">
                </td>
                <td> {{el.student.name | empty}}</td>
                <td> {{@sex[el.student.sex-1]}}</td>
                <td> {{el.student.gradeName | empty}}{{el.student.clazzName | empty}}班</td>
                <td> <input type="text" :attr="{id:el.id, value:el.description || ''}" name="description" class="form-control input-sm" placeholder="请输入缺勤原因！" maxlength="15"> </td>
            </tr>
            <tr :if="data.pages == 0">
                <td colspan="5">暂无相关数据！</td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox" :if="@data.pages > 1">
        <div id="pagination" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->

    <div :visible="@isShowSave" class="text-center">
        <button :click="save" class="btn btn-primary" type="button">保存</button>
    </div>
</div>

<script type="text/javascript">
    var vmAddChooseAttendance = avalon.define({
        $id: 'addChooseAttendanct',
        sex : ["男", "女"],
        current : 1,
        size : 20,
        opt : {},
        allchecked : false,
        students : [],    //保存缺勤学生
        studentObjs : [], //选中学生id和缺勤原因
        course : {},
        data : {},
        isShowSave : false,
        checkAll : function(e){ //全选
            $("input[name='student']").prop("checked", e.target.checked);
        },
        checkOwn : function (e) {
            if(this.data.records.length === 0){
                return;
            }
            if($("input[name='student']:checked").length === this.data.records.length){
                vmAddChooseAttendance.allchecked = true;
            }else{
                vmAddChooseAttendance.allchecked = false;
            }
        },
        set : function(){ //写入值
            var list = [];
            $("input[name='student']:checked").each(function(){
                var _data = {};
                _data.id  = $(this).val();                      //学生选课表id
                _data.studentId   =  $(this).attr("studentId"); //学生id
                _data.description =  $("#"+_data.id).val();     //考勤描述
                list.push(_data);
            });
            vmAddChooseAttendance.studentObjs[this.current-1] = list;
        },
        save : function(){ //保存
            vmAddChooseAttendance.set();
            var load = true;
            $.each(vmAddChooseAttendance.$model.studentObjs, function(index, value){
                if(!load){ return false; }
                if(typeof(value) !== "undefined"){
                    $.each(value, function(i, v){
                        if(!v.description){
                            msgFn.msg(false, "第"+(index+1)+"页，选中的学生中，有未填写缺勤原因的记录，请确认！");
                            load = false;
                            vmAddChooseAttendance.students = [];
                            return false;
                        }else{
                            var $data = {};
                            $data.studentId   = v.studentId;
                            $data.description = v.description;
                            vmAddChooseAttendance.students.push($data);
                        }
                    });
                }
            });
            if(load && vmAddChooseAttendance.students.length === 0){
                msgFn.msg(false, "请选择缺勤学生！");
            }
            if(load && vmAddChooseAttendance.students.length > 0){
                msgFn.load(function (end) {
                    $.ajax({
                        url: "${ctx}/studentAttendance/save/choose/attendance",
                        data : {
                            chooseAttendance : vmAddChooseAttendance.$model.students,
                            workDayId : vmAddChooseAttendance.$model.opt.workDayId,
                            courseType: vmAddChooseAttendance.$model.course.xxlb},
                        type: 'POST',
                        dataType: 'json',
                        success: function (data) {
                            end();
                            msgFn.msg(data);
                            window.history.go(-1);
                            /*layer.msg(data.msg, {icon: data.success ? 1 : 2}, function(){
                                window.history.go(-1);
                            });*/
                        }
                    });
                });
            }
        },
        loadCourse: function () {
            $.ajax({
                url: "${ctx}/optional/detail",
                data: {id: this.opt.optionalCourseId},
                type: "post",
                success: function (data) {
                    vmAddChooseAttendance.course = data.result;
                }
            });
        },
        init : function(){
            $.ajax({
                url: "${ctx}/teacher/choose/students",
                data: {
                    optionalCourseId: this.opt.optionalCourseId,
                    workDayId: this.opt.workDayId,
                    notShow: false, //不过滤缺勤学生
                    current: this.current,
                    size: this.size
                },
                type: "post",
                success: function (data) {
                    vmAddChooseAttendance.data = data.result;
                    vmAddChooseAttendance.isShowSave =  vmAddChooseAttendance.$model.data.records.length > 0 ? true : false;

                    $("[name='student']").removeAttr("checked"); //清空
                    //$("[name='description']").val("");

                    //设置默认选中
                    var _page_data = vmAddChooseAttendance.$model.studentObjs[vmAddChooseAttendance.$model.current-1];
                    if(typeof(_page_data) !== "undefined"){
                        $.each(_page_data, function(i, v){
                            $("#checkbox_"+v.id).prop("checked", true);
                            $("#"+v.id).val(v.description);
                        });
                    }
                    vmAddChooseAttendance.checkOwn();

                    mainVm.renderPage(vmAddChooseAttendance.$model.data, function(curr){
                        vmAddChooseAttendance.set();
                        vmAddChooseAttendance.allchecked = false;

                        vmAddChooseAttendance.current = curr;
                        vmAddChooseAttendance.init();
                    });
                }
            });
        }
    });

    vmAddChooseAttendance.opt = {
        optionalCourseId : "${optionalCourseId!''}",
        workDayId : "${workDayId!''}"
    };
    vmAddChooseAttendance.loadCourse();
    vmAddChooseAttendance.init();
</script>
</@page>