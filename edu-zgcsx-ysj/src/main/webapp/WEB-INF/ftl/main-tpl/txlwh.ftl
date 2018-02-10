 <!-- 通讯录维护 S-->
<div class="txlwh-wrap" >

    <table class="detailTable">
        <tbody>
            <tr :for="(index,el) in @nodeData | limitBy(4) as items_1">
                <th width="10%"><span class="title">{{el.nodeName}}:</span></th>
                <td colspan="2">
                    <div :if="el.nodeName != '校区'">
                        <a :for="(index_0,el_0) in el.teacherNode"
                            class="btn btn-default btn-xs disabled">{{el_0.teacherName}}
                            <i :click="@delFn(el,index_0,'ter')" :if="@show" class="iconfont icon-jian-copy"></i></a>
                            <button :click="@addTeacherFn(el)" :if="@show" class="btn btn-default btn-xs" type="button">
                                   <i class="iconfont icon-xinzeng"></i>添加</button>
                    </div>
                    <div :if="el.nodeName == '校区'">
                        <a :for="(_index,el_0) in el.childrenNode"
                            :class="[(_index == @toggle ? 'btn-primary':'')]"
                            :click="@activeFn(_index);"
                            class="btn btn-default btn-xs items disabled">{{el_0.nodeName}}
                            <i :click="@delFn(el_0,_index,'node')" :if="@show" class="iconfont icon-jian-copy"></i></a>
                            <button :click="@addFn(el)" :if="@show" class="btn btn-default btn-xs" type="button">
                                <i class="iconfont icon-xinzeng"></i>新增校区</button>
                        (点击按钮切换校区)
                        <div class="campus">
                            <fieldset :for="(index_0,el_0) in @nodeData[3].childrenNode" :visible="@toggle == index_0"
                                        class="layui-elem-field">
                                <legend>{{el_0.nodeName}}</legend>
                                <div class="layui-field-box">
                                    <blockquote class="layui-elem-quote clearfix">
                                        <span style="line-height: 34px;">
                                            温馨提示：以下是{{el_0.nodeName}}通讯录情况。
                                        </span>
                                        <button :click="@addFn(el_0)" :if="@show"
                                            class="btn btn-default btn-xs pull-right" type="button">
                                            <i class="iconfont icon-xinzeng"></i>新增子节点</button>
                                    </blockquote>
                                    <table class="detailTable">
                                        <tbody>
                                            <tr :for="(index_1,el_1) in el_0.childrenNode">
                                                <th width="10%"><span class="title">{{el_1.nodeName}}:</span></th>
                                                <td class="clearfix">
                                                    <div :if="@show && !(el_1.teacherNode.length>0) && !(el_1.childrenNode.length>0)">
                                                        <button :click="@addTeacherFn(el_1)"
                                                                    class="btn btn-default btn-xs" type="button">
                                                                <i class="iconfont icon-xinzeng"></i>添加
                                                        </button>
                                                        <button :click="@addFn(el_1)"
                                                                    class="btn btn-default btn-xs" type="button">
                                                                <i class="iconfont icon-xinzeng"></i>新增子节点
                                                        </button>
                                                    </div>
                                                    <div :if="el_1.teacherNode.length>0">
                                                        <a :for="(index_2,el_2) in el_1.teacherNode"
                                                            class="btn btn-default btn-xs disabled">{{el_2.teacherName}}
                                                            <i :click="@delFn(el_1,index_2,'ter')" :if="@show" class="iconfont icon-jian-copy"></i></a>
                                                        <button :click="@addTeacherFn(el_1)" :if="@show"
                                                                class="btn btn-default btn-xs" type="button">
                                                            <i class="iconfont icon-xinzeng"></i>添加
                                                        </button>
                                                    </div>
                                                    <div :if="el_1.childrenNode.length>0" style="padding:5px;">
                                                        <table class="detailTable" style="margin: 0">
                                                            <tbody>
                                                                <tr :for="(index_2,el_2) in el_1.childrenNode">
                                                                    <th width="12%"><span class="title">{{el_2.nodeName}}:</span></th>
                                                                    <td class="clearfix">
                                                                        <a :for="(index_3,el_3) in el_2.teacherNode"
                                                                            class="btn btn-default btn-xs disabled">{{el_3.teacherName}}
                                                                            <i :click="@delFn(el_2,index_3,'ter')" :if="@show"
                                                                            class="iconfont icon-jian-copy"></i></a>
                                                                        <button :click="@addTeacherFn(el_2)" :if="@show" class="btn btn-default btn-xs" type="button">
                                                                            <i class="iconfont icon-xinzeng"></i>添加
                                                                        </button>
                                                                    </td>
                                                                    <th :if="@show" class="text-center" width="10%">
                                                                        <button :click="@delFn(el_2,index_2,'node')"
                                                                                class="btn btn-default btn-xs" type="button">
                                                                            <i class="iconfont icon-jian-copy"></i>删除此节点
                                                                        </button>
                                                                    </th>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </td>
                                                <td :if="@show" class="text-center" width="10%">
                                                    <button :click="@addFn(el_1)"
                                                        :if="el_1.childrenNode.length>0" class="btn btn-default btn-xs" type="button">
                                                        <i class="iconfont icon-xinzeng"></i>新增子节点
                                                    </button>
                                                    <button :click="@delFn(el_1,index_1,'node')" class="btn btn-default btn-xs" type="button">
                                                        <i class="iconfont icon-jian-copy"></i>删除此节点
                                                    </button>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                </td>
            </tr>
            <tr :for="(index_1,el_1) in @nodeData | limitBy(100,4) as items_2">
                <th width="10%"><span class="title">{{el_1.nodeName}}:</span></th>
                <td class="clearfix">
                    <div :if="@show && !(el_1.teacherNode.length>0) && !(el_1.childrenNode.length>0)">
                        <button :click="@addTeacherFn(el_1)"
                                    class="btn btn-default btn-xs" type="button">
                                <i class="iconfont icon-xinzeng"></i>添加
                        </button>
                        <button :click="@addFn(el_1)"
                                    class="btn btn-default btn-xs" type="button">
                                <i class="iconfont icon-xinzeng"></i>新增子节点
                        </button>
                    </div>
                    <div :if="el_1.teacherNode.length>0">
                        <a :for="(index_2,el_2) in el_1.teacherNode"
                            class="btn btn-default btn-xs disabled">{{el_2.teacherName}}
                            <i :click="@delFn(el_1,index_2,'ter')" :if="@show" class="iconfont icon-jian-copy"></i></a>
                        <button :click="@addTeacherFn(el_1)"
                                class="btn btn-default btn-xs" type="button">
                            <i class="iconfont icon-xinzeng"></i>添加
                        </button>
                    </div>
                    <div :if="el_1.childrenNode.length>0" style="padding:5px;">
                        <table class="detailTable" style="margin: 0">
                            <tbody>
                                <tr :for="(index_2,el_2) in el_1.childrenNode">
                                    <th width="12%"><span class="title">{{el_2.nodeName}}:</span></th>
                                    <td class="clearfix">
                                        <a :for="(index_3,el_3) in el_2.teacherNode"
                                            class="btn btn-default btn-xs disabled">{{el_3.teacherName}}
                                            <i :if="@show" :click="@delFn(el_2,index_3,'ter')" class="iconfont icon-jian-copy"></i></a>
                                        <button :click="@addTeacherFn(el_2)" :if="@show" class="btn btn-default btn-xs" type="button">
                                            <i class="iconfont icon-xinzeng"></i>添加
                                        </button>
                                    </td>
                                    <th :if="@show" class="text-center" width="10%">
                                        <button :click="@delFn(el_2,index_2,'node')"
                                                class="btn btn-default btn-xs" type="button">
                                            <i class="iconfont icon-jian-copy"></i>删除此节点
                                        </button>
                                    </th>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </td>
                <td :if="@show" class="text-center" width="10%">
                    <button :click="@addFn(el_1)" :if="el_1.childrenNode.length>0" class="btn btn-default btn-xs" type="button">
                        <i class="iconfont icon-xinzeng"></i>新增子节点
                    </button>
                    <button :click="@delFn(el_1,index_1 + 4,'node')" class="btn btn-default btn-xs" type="button">
                        <i class="iconfont icon-jian-copy"></i>删除此节点
                    </button>
                </td>
            </tr>
        </tbody>
    </table>
</div>
<!-- 通讯录维护 E-->

<script>
layui.use(['layer'], function(){
    var layer = layui.layer;
});
var vmTxlwh = avalon.define({
    $id:'txlwh',
    nodeData:[],
    toggle:'',
    show:false,
    dataListFn:function(){
        $.ajax({
            url: '${ctx}/tree/contact',
            type: 'POST',
            dataType: 'json',
            success:function(data){
                vmTxlwh.nodeData = data.result;
            }
        })
    },
    editFn:function(e){
        if(this.show){
            $.ajax({
                url: '',
                type: 'POST',
                dataType: 'json',
                data:'',
                success:function(data){
                    if(data.code!='0'){
                        layer.confirm(data.msg);
                    }else{
                        layer.msg("节点变动成功");
                    }
                    vmTxlwh.dataListFn();
                }
            })
        }
        this.show =! this.show;
    },
    activeFn:function(index){
        this.toggle = index;
    },
    addedFn:function(_titleName,_el){//新增主节点;
        var parentId;
        if(_el == '主节点'){
            parentId ='';
        }else{
            parentId = _el.$model.nodeId
        }
        layer.prompt({
                title: _titleName,
                formType: 3
            },function(text,index){
                if(text.length>15){
                    layer.msg('节点名称最大输入15个字符', {icon: 7},function(){
                    });
                    return ;
                }
                var opt = {
                        nodeName:text,
                        parentId:parentId
                    };
                $.ajax({
                    url: '${ctx}/tree/add',
                    type: 'POST',
                    dataType: 'json',
                    data: opt,
                    success:function(data){
                        if(data.code!='0'){
                            layer.confirm(data.msg);
                        }else{
                            layer.msg("节点变动成功");
                        }
                        vmTxlwh.dataListFn();
                    }
                })
                layer.close(index);
            }
        );
    },
    addFn:function(el){
        this.addedFn('添加'+el.nodeName+'子节点',el);
    },
    delFn:function(_el,_index,_items){
        switch(_items){
            case 'node':
                var opt = {
                    url:'${ctx}/tree/del/'+ _el.nodeId,
                    text:'<div class="text-center">是否确认要删除此节点?</div>',
                    msg:'节点删除成功!'
                }
                layer.confirm(opt.text,{title:'操作',btnAlign:'c'},function(){
                    $.ajax({
                        url: opt.url,
                        type: 'POST',
                        dataType: 'json',
                        success:function(data){
                            if(data.code!='0'){
                                layer.confirm(data.msg);
                            }else{
                                layer.msg(opt.msg);
                            }
                            vmTxlwh.dataListFn();
                        }
                    })
                });
                break;
            case 'ter':
                console.log(_el.nodeId,_index);
                var opt = {
                    url:'${ctx}/tree/del/teacher',
                    text:'<div class="text-center">是否确认要删除此教师?</div>',
                    msg:'删除教师成功!',
                    nodeId: _el.nodeId,
                    teacherIds: _el.teacherNode[_index].teacherId
                }
                layer.confirm(opt.text,{title:'操作',btnAlign:'c'},function(){
                    $.ajax({
                        url: opt.url,
                        type: 'POST',
                        dataType: 'json',
                        data: {nodeId: opt.nodeId,teacherId:opt.teacherIds},
                        success:function(){
                            layer.msg(opt.msg);
                            vmTxlwh.dataListFn();
                        }
                    })
                });
                break;
        }
    },
    addTeacherFn:function(el){
        $.post('${ctx}/notice/txl/add/teacher/layer',function(str){
            layer.open({
                type: 1,
                area: '1000px',
                content: str,
                title:'教师查询',
                btn: ['确定','取消'],
                btnAlign :'c',
                success:function(){
                    avalon.scan(document.body);
                },
                yes:function(index){
                    var opt = {
                        nodeId:el.nodeId,
                        teacherIds:vmTeacherContent.$model._flagData
                    }
                    $.ajax({
                        url: '${ctx}/tree/add/teacher',
                        type: 'POST',
                        dataType: 'json',
                        data: opt,
                        success:function(data){
                            if(data.code!='0'){
                                layer.confirm(data.msg);
                            }else{
                                layer.msg('你已成功添加教师!');
                            }
                            vmTxlwh.dataListFn();
                            layer.close(index);
                        }
                    })
                }
            })
        });
    }
});

$(function () {
    vmTxlwh.dataListFn();
})
</script>