<div class="nameSearch-wrap" ms-important="teacherContent">
    <!-- 查询条件 S-->
    <form class="form-inline" onsubmit="return false;">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>课程名称：</span>
                    <input :duplex="@_data.kcmc" class="input" type="text" name="" placeholder="请输入名称...">
                </li>
                <li>
                    <span>教师姓名：</span>
                    <input :duplex="@_data.teacherName" class="input" type="text" name="" placeholder="请输入姓名...">
                </li>
                <li>
                    <span>性别：</span>
                    <select :duplex="@_data.sex" class="select">
                        <option value="">=性别=</option>
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
                </li>
                <li>
                    <span>联系电话：</span>
                    <input :duplex="@_data.phoneNum" data-duplex-changed="@formatphoneNum" class="input" type="text" name="" placeholder="请输入电话...">
                </li>
            </ul>
            <div class="toolList pull-right">
                <ul class="clearfix">
                    <li>
                        <!-- 查找 S-->
                        <a :click="this.dataListFn()" class="item-btn" href="#this"><i class="iconfont icon-chazhao"></i>查找</a>
                        <!-- 查找 E-->
                    </li>
                    <li>
                        <!-- 清空 S-->
                        <a :click="@clearFn" class="item-btn" href="#this"><i class="iconfont icon-qingkong"></i>清空</a>
                        <!-- 清空 E-->
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
                    <th width="5%">全选</th>
                    <th width="12%">姓名(别名)</th>
                    <th width="5%">性别</th>
                    <th width="8%">民族</th>
                    <th width="12%">联系电话</th>
                    <th>年级</th>
                    <th>课程名称</th>
                </tr>
            </thead>
            <tbody>
                <tr :for="(index,el) in @_dataList">
                    <td>
                        <input ms-duplex-checked="el.checked" data-duplex-changed="@checkOne($event,el)" type="checkbox" name="">
                    </td>
                    <td>{{el.teacherName}}<span :if="el.bm">({{el.bm}})</span></td>
                    <td>{{el.sex}}</td>
                    <td>{{el.mzm}}</td>
                    <td>{{el.phoneNum}}</td>
                    <td>{{el.grade}}</td>
                    <td>{{el.kcmc}}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <!-- table表单模块 E-->
    <!-- 分页+全选模块 S-->
    <div class="clearfix footerBox">
        <div class="pull-left checkAllBox">
            <label><input ms-duplex-checked="@allchecked" data-duplex-changed="@checkAll" type="checkbox" name="" value="">全选</label>
        </div>
        <div id="pages" class="pull-right pages"></div>
    </div>
    <!-- 分页+全选模块 E-->
</div>
<script type="text/javascript">
    var vmTeacherContent = avalon.define({
        $id:'teacherContent',
        _data:{
            kcmc:'',
            teacherName:'',
            sex:'',
            phoneNum:'',
            size:10,
            current:1
        },
        _dataList:[],
        _flagData:[],
        allchecked:false,
        clearFn:function(){
            this._data={
                kcmc:'',
                teacherName:'',
                sex:'',
                phoneNum:''
            }
            vmTeacherContent.dataListFn();
        },
        dataListFn:function(){
            var index;
            $.ajax({
                url: '${ctx}/tree/contact/teacher/search',
                type: 'POST',
                dataType: 'json',
                data: vmTeacherContent.$model._data,
                beforeSend:function(){
                    index = layer.load(1, {shade: false});
                },
                success:function(data){
                    layer.close(index)
                    vmTeacherContent._dataList = data.result.records;
                    layui.use('laypage', function(){
                        var laypage = layui.laypage;
                        laypage.render({
                            elem: 'pages',
                            limit: vmTeacherContent.$model._data.size,//当前分页显示条数;
                            count: data.result.total,//服务器得数据总条数;
                            curr : data.result.current || vmTeacherContent.$model._data.current,//起始页;
                            groups: 10,
                            theme : '#1E9FFF',
                            jump: function(obj, first){
                                if(!first){
                                    vmTeacherContent._data.current = obj.curr;
                                    vmTeacherContent.allchecked = false;
                                    vmTeacherContent.dataListFn();
                                }
                            }
                        });
                    });
                    for(var i=0;i<vmTeacherContent._dataList.length;i++){
                        var _dataID = vmTeacherContent._dataList[i].teacherId;
                        for(var j=0;j<vmTeacherContent._flagData.length;j++){
                            var _flagID = vmTeacherContent._flagData[j];
                            if(_dataID == _flagID){
                                vmTeacherContent._dataList[i].checked = true;
                                if(i==9){
                                    vmTeacherContent.allchecked = true;
                                }
                            }
                        }
                    }
                },
                error:function(data){
                    layer.msg(data.msg);
                }
            })
        },
        checkOne:function (e,el) {
            var checked = e.target.checked;
            if (checked === false) {
                this.allchecked = false;
                this._flagData.remove(el.teacherId);
            } else {
                this._flagData.push(el.teacherId);
                this.allchecked = this._dataList.every(function (el) {
                    return el.checked
                })
            }
        },
        checkAll:function (e) {
            var checked = e.target.checked
            this._dataList.forEach(function (el) {
                el.checked = checked;
                if(el.checked){
                    for(var i=0;i<vmTeacherContent._dataList.length;i++){
                        var _dataID = vmTeacherContent._dataList[i].teacherId;
                        for(var j=0;j<vmTeacherContent._flagData.length;j++){
                            var _flagID = vmTeacherContent._flagData[j].teacherId;
                            if(_dataID == _flagID){
                                vmTeacherContent._flagData.remove(el.teacherId);
                            }
                        }
                    }
                    vmTeacherContent._flagData.push(el.teacherId);
                }else{
                    vmTeacherContent._flagData.remove(el.teacherId);
                }
            })
        },
        formatphoneNum: function(e){//只能输入数字
            this._data.phoneNum = e.target.value.replace(/\D/g,'')
        }
    });
    vmTeacherContent.dataListFn();
    avalon.config({
        debug: false
    })
</script>