<!--开放层考勤缺勤学生弹框-->
<div ms-controller="chooseAttendanceStudent" class="ms-controller">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>姓名：</span>
                    <input :duplex="@opt.studentName" class="form-control input-sm"/>
                </li>
            </ul>
            <div class="toolList pull-left">
                <ul class="clearfix">
                    <li>
                        <a :click="@init('find')" class="item-btn" href="javascript:void(0);">
                            <i class="iconfont icon-chazhao"></i>查找
                        </a>
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
                <th width="20%">姓名</th>
                <th width="15%">性别</th>
                <th width="20%">联系电话</th>
                <th width="20%">班级</th>
                <th width="25%">缺勤原因</th>
            </tr>
            </thead>
            <tbody>
                <tr :for="el in @data.records">
                    <td> {{el.student.name | empty}}</td>
                    <td> {{@sex[el.student.sex-1]}}</td>
                    <td> {{el.student.mobile | empty}}</td>
                    <td> {{el.student.gradeName | empty}}{{el.student.clazzName | empty}}班</td>
                    <td> {{el.description | empty}}</td>
                </tr>
                <tr :if="data.pages == 0">
                    <td colspan="5">暂无相关数据！</td>
                </tr>
            </tbody>
        </table>
    </div>

    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox" :if="@data.pages > 1">
        <div id="pagination_dlg" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->

    <div class="clearfix pull-right">
        <button onclick="javascript:layer.closeAll();" class="btn btn-default btn-sm" style="margin: 10px 5px;" type="button">关闭</button>
    </div>
</div>

<script type="text/javascript">
    var vmChooseAttendanceStudent = avalon.define({
        $id:"chooseAttendanceStudent",
        current : 1,
        size : 10,
        sex : ["男", "女"],
        data:{},
        opt : {
            studentName:'',
            optionalCourseId : '',
            workDayId : ''
        },
        dataOpt: {},
        init : function(type){
            if(type === 'find'){       //点击了查询按钮
                vmChooseAttendanceStudent.current = 1;
                vmChooseAttendanceStudent.dataOpt = vmChooseAttendanceStudent.$model.opt;
            }

            $.ajax({
                url: "${ctx}/studentAttendance/attendance/choose/students",
                data : {
                    optionalCourseId: this.dataOpt.optionalCourseId,
                    workDayId : this.dataOpt.workDayId,
                    studentName : this.dataOpt.studentName,
                    current: this.current,
                    size: this.size
                },
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmChooseAttendanceStudent.data = data.result;

                    $(window).resize();
                    mainVm.renderPage("pagination_dlg", vmChooseAttendanceStudent.$model.data, function(curr){
                        vmChooseAttendanceStudent.current = curr;
                        vmChooseAttendanceStudent.init();
                    });
                }
            });
        }
    });

    vmChooseAttendanceStudent.opt.optionalCourseId = vmAttendanceChooseDetail.$model.showData.optionalCourseId;
    vmChooseAttendanceStudent.opt.workDayId = vmAttendanceChooseDetail.$model.showData.workDayId;
    vmChooseAttendanceStudent.dataOpt = vmChooseAttendanceStudent.$model.opt;
    vmChooseAttendanceStudent.init();
</script>