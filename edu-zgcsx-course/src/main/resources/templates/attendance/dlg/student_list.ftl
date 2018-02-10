<!--添加考勤 学生弹框-->
<div ms-controller="student" class="ms-controller">
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th><input type="checkbox" ms-duplex-checked="@allchecked" data-duplex-changed="@checkAll"></th>
                <th>姓名</th>
                <th width="20%">别名</th>
                <th width="20%">性别</th>
                <th width="20%">班级</th>
                <th width="20%">联系电话</th>
            </tr>
            </thead>
            <tbody>
                <tr :for="el in @data.records">
                    <td>
                        <input type="checkbox" name="student" :attr="{value:el.id, showName:el.name, id:el.id}" :click="@checkOwn">
                    </td>
                    <td> {{el.name | empty}}</td>
                    <td> {{el.student.aliasName | empty}}</td>
                    <td> {{@sex[el.sex-1]}}</td>
                    <td> {{el.gradeName | empty}}{{el.clazzName | empty}}班</td>
                    <td> {{el.mobile | empty}}</td>
                </tr>
                <tr :if="data.pages == 0">
                    <td colspan="6">暂无相关数据！</td>
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
        <button :click="ok()" class="btn btn-primary btn-sm" style="margin: 10px 5px;" type="button">确定</button>
        <button :click="close()" class="btn btn-default btn-sm" style="margin: 10px 5px;" type="button">关闭</button>
    </div>
</div>

<script type="text/javascript">
    var vmStudent = avalon.define({
        $id:"student",
        current : 1,
        size : 10,
        allchecked : false,
        clazzId : '',
        sex : ["男", "女"],
        data:{},
        ok : function(){ //确定
            vmStudent.set();

            vmAddAttendance.studentIds = [];
            vmAddAttendance.studentNames = [];

            for(var i in students){
                if(typeof students[i] !== "undefined"){
                    vmAddAttendance.studentIds.pushArray(students[i].ids);
                    vmAddAttendance.studentNames.pushArray(students[i].names);
                }
            }
            $("#studentName").val(vmAddAttendance.$model.studentNames.join(","));
            vmAddAttendance.studentObj = students;
            layer.closeAll();
        },
        checkAll : function(e){ //全选
            $("input[name='student']").prop("checked", e.target.checked);
        },
        checkOwn : function (e) {
            if($("input[name='student']:checked").length === vmStudent.$model.data.records.length){
                vmStudent.allchecked = true;
            }else{
                vmStudent.allchecked = false;
            }
        },
        set : function(){ //保存当前页数据
            var studentIds = [];
            var studentNames = [];
            var _data = {};
            $("input[name='student']:checked").each(function(){
                studentIds.push($(this).val());
                studentNames.push($(this).attr("showName"));
            });

            _data.page = this.current;
            _data.ids = studentIds;
            _data.names = studentNames;

            students[this.current-1] = _data;
        },
        close : function(){
            layer.closeAll();
        },
        init : function(){
            $.ajax({
                url: "${ctx}/api/students/all",
                data : {
                    clazzId: this.clazzId,
                    current: this.current,
                    size: this.size
                },
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmStudent.data = data.result;
                    //清空选中
                   $("[name='student']").removeAttr("checked");

                    //设置默认选中
                    var _page_data = students[vmStudent.$model.current-1];
                    if(typeof(_page_data) !== "undefined"){
                        $.each(_page_data.ids, function(i, v){
                            $("#"+v).prop("checked", true);
                        });
                    }
                    vmStudent.checkOwn();

                    $(window).resize();
                    mainVm.renderPage(vmStudent.$model.data, function(curr){
                        vmStudent.set();
                        vmStudent.allchecked = false;

                        vmStudent.current = curr;
                        vmStudent.init();
                    });
                }
            });
        }
    });

    vmStudent.clazzId   = vmAddAttendance.$model.clazzId;
    vmStudent.init();
</script>