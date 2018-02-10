<div class="form-wrap">
    <form class="layui-form form-horizontal" method="post">
        <input type="hidden" name="id" value="${space.id!}">
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>教室名称：</label>
            <div class="col-sm-4">
                <input name="mc" type="text" class="form-control" lay-verify="required|mc"
                       value="${space.mc!}"
                       placeholder="请输入教室名称">
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>可容纳人数：</label>
            <div class="col-sm-4">
                <input name="volume" type="number" class="form-control" lay-verify="required|number|volume"
                       value="${space.volume!}"
                       placeholder="请输入可容纳人数">
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>编码：</label>
            <div class="col-sm-4">
                <input name="buildingid" type="text" class="form-control" lay-verify="required|buildingid"
                       value="${space.buildingid!}"
                       placeholder=" 请输入编码">
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>设备情况：</label>
            <div class="col-sm-6">
                <input name="content" type="text" class="form-control" lay-verify="required|content"
                       value="${space.content!}"
                       placeholder="请输入设备情况">
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>校区：</label>
            <div class="col-sm-4">
                <select class="form-control" name="campus" lay-ignore="" lay-verify="required">
                    <option value="">== 选择校区 ==</option>
                        <#list xiaoQuList as xq>
                            <#if (space.campus!)==xq.itemValue>
                                <option value="${xq.itemValue}" selected>${xq.itemText}</option>
                            <#else>
                                <option value="${xq.itemValue}">${xq.itemText}</option>
                            </#if>
                        </#list>
                </select>
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>建筑楼：</label>
            <div class="col-sm-4">
                <select class="form-control" name="architecture" lay-ignore="" lay-verify="required">
                    <option value="">== 选择建筑楼 ==</option>
                        <#list jianZhuList as jz>
                            <#if (space.architecture!)==jz.itemValue>
                                <option value="${jz.itemValue}" selected>${jz.itemText}</option>
                            <#else>
                                <option value="${jz.itemValue}">${jz.itemText}</option>
                            </#if>
                        </#list>
                </select>
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>类型：</label>
            <div class="col-sm-4">
                <select class="form-control" name="sptype" lay-ignore="" lay-verify="required">
                    <option value="">== 选择类型 ==</option>
                        <#list leiXingList as lx>
                            <#if (space.sptype!)==lx.itemValue>
                                <option value="${lx.itemValue}" selected>${lx.itemText}</option>
                            <#else>
                                <option value="${lx.itemValue}">${lx.itemText}</option>
                            </#if>
                        </#list>
                </select>
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>楼层：</label>
            <div class="col-sm-4">
                <select class="form-control" name="floorid" lay-ignore="" lay-verify="required">
                    <option value="">== 选择楼层 ==</option>
                        <#list louCengList as lc>
                            <#if (space.floorid!)==lc.itemValue>
                                <option value="${lc.itemValue}" selected>${lc.itemText}</option>
                            <#else>
                                <option value="${lc.itemValue}">${lc.itemText}</option>
                            </#if>
                        </#list>
                </select>
            </div>
        </div>

        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger"></span>教室描述：</label>
            <div class="col-sm-9">
                <textarea name="describe" class="form-control" rows="8" lay-verify="describe"
                          placeholder="请输入教室描述">${space.describe!}</textarea>

            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger"></span>地址：</label>
            <div class="col-sm-6">
                <input name="addr" type="text" class="form-control"
                       value="${space.addr!}"
                       lay-verify="addr"
                       placeholder="请输入地址">
            </div>
        </div>
        <div class="form-group form-group-sm">
            <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否允许重复：</label>
            <div class="col-sm-3">
                <label class="radio-inline">
                    <input name="flag" type="radio" value="1" lay-ignore
                           <#if (space.flag!)=='1'>
                                checked
                           </#if>
                    >是
                </label>
                <label class="radio-inline">
                    <input name="flag" type="radio" value="2" lay-ignore
                           <#if (space.flag!)!='1'>
                                checked
                           </#if>
                    >否
                </label>
            </div>
        </div>
        <div class="form-group form-group-sm">
            <div class="col-sm-offset-2 col-sm-9">
                <button lay-submit="" lay-filter="demo1" class="btn btn-primary">保存</button>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript">
    layui.use(['form'], function () {
        var form = layui.form;

        form.render();

        //自定义验证规则
        form.verify({
            mc: function (value) {
                if (value.length > 20) {
                    return '教室名称不能超过20字符';
                }
            },
            describe: function (value) {
                if (value.length > 300) {
                    return '教室描述不能超过300字符';
                }
            },
            volume: function (value) {
                if (value < 1 || value > 300) {
                    return '可容纳人数应该在1~300之间';
                }
            },
            buildingid: function (value) {
                if (value.length > 15) {
                    return '编码不能超过15字符';
                }
            },
            content: function (value) {
                if (value.length > 20) {
                    return '设备情况不能超过20字符';
                }
            },
            addr: function (value) {
                if (value.length > 180) {
                    return '地址不能超过180字符';
                }
            }
        });
        //监听提交
        form.on('submit(demo1)', function (data) {
            layer.load(1);
            $.ajax({
                url: '${ctx}/space/save',
                type: 'POST',
                dataType: 'json',
                data: data.field,
                success: function (data) {
                    layer.closeAll();
                    if (data.code == '0') {
                        layer.alert('保存成功', function () {
                            window.location.href = '${ctx}/space/to/list';
                        });
                    } else {
                        layer.alert('保存失败');
                    }
                }
            });
            return false;
        });
    })
</script>