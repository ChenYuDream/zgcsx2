<div :controller="campus">
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>所属校区：</span>
                    <select ms-duplex="@campusId" :attr="{value:'== 请选择 =='}" class="form-control input-sm">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @campus" :attr="{value:el.id}">{{el.name}}</option>
                    </select>
                </li>
            </ul>
        </div>
    </form>

    <div class="clearfix pull-right">
        <button :click="ok()" class="btn btn-primary btn-sm" style="margin: 10px 5px;" type="button">确定</button>
        <button onclick="javascript:layer.closeAll();" class="btn btn-default btn-sm" style="margin: 10px 5px;" type="button">关闭</button>
    </div>
</div>
<script type="text/javascript">
    var vmCampus = avalon.define({
        $id: 'campus',
        campus: [],
        campusId : '',
        loadCampus : function () {
            $.ajax({
                url: "${ctx}/api/campus",
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmCampus.campus = data.result;
                }
            });
        },
        ok : function(){
            if(vmCampus.$model.campusId !== ""){
                msgFn.load(function (end) {
                    $.ajax({
                        url: "${ctx}/optional/auto/course",
                        data : {campusId : vmCampus.$model.campusId},
                        type: 'POST',
                        dataType: 'json',
                        success: function (data) {
                            layer.closeAll();
                            end();
                            msgFn.msg(data);
                            if(data.success){
                                mainVm.condition.campusId = vmCampus.$model.campusId;
                                mainVm.condition.gradeId = "";
                                mainVm.condition.clazzId = "";
                                mainVm.condition.studentName = "";
                                mainVm.condition.autoAllot = "1";
                                mainVm.search();
                            }
                        }
                    });
                });
            }else{
                msgFn.msg(false, "请选择校区");
            }
        }
    });
    vmCampus.loadCampus();
</script>