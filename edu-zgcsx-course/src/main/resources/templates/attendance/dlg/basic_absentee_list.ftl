<!--基础层考勤缺勤学生弹框-->
<div ms-controller="attendanceStudent" class="ms-controller">
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th>姓名</th>
                <th width="15%">性别</th>
                <th width="30%">联系电话</th>
                <th width="30%">身份证号码</th>
            </tr>
            </thead>
            <tbody>
                <tr :for="el in @data.records">
                    <td> {{el.name | empty}}</td>
                    <td> {{@sex[el.sex-1]}}</td>
                    <td> {{el.mobile | empty}}</td>
                    <td> {{el.idCard | idCard}}</td>
                </tr>
                <tr :if="data.pages == 0">
                    <td colspan="4">暂无相关数据！</td>
                </tr>
            </tbody>
        </table>
    </div>

    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox" :if="@data.pages > 1">
        <div id="pagination" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->

    <div class="clearfix pull-right">
        <button onclick="javascript:layer.closeAll();" class="btn btn-default btn-sm" style="margin: 10px 5px;" type="button">关闭</button>
    </div>
</div>

<script type="text/javascript">
    var vmAttendanceStudent = avalon.define({
        $id:"attendanceStudent",
        current : 1,
        size : 10,
        sex : ["男", "女"],
        data:{},
        opt : {},
        init : function(){
            $.ajax({
                url: "${ctx}/studentAttendance/attendance/students",
                data : {
                    clazzId: this.opt.clazzId,
                    workDayId : this.opt.workDayId,
                    current: this.current,
                    size: this.size
                },
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmAttendanceStudent.data = data.result;

                    $(window).resize();
                    mainVm.renderPage(vmAttendanceStudent.$model.data, function(curr){
                        vmAttendanceStudent.current = curr;
                        vmAttendanceStudent.init();
                    });
                }
            });
        }
    });

    vmAttendanceStudent.opt = vmAttendanceDetail.$model.showData;
    vmAttendanceStudent.init();
</script>