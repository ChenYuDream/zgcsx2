<div class="dialogBox" ms-important="evenPeople">
	<div class="row">
		<!-- ztree S-->
		<div class="col-sm-3">
			<div class="zTreeWrap">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
		<!-- ztree E-->
		<div class="col-sm-9">
			<!-- 条件搜索 S-->
			<form class="searchBox" onsubmit="return false;">
				<ul class="clearfix">
					<li>
						<input id="name" class="form-control input-sm" type="text" name="" style="width: 158px;" placeholder="请输入姓名">
					</li>
					<li :if="@port.type == 'teachers' ">
						<!--<input id="idCard" class="form-control input-sm" type="text" name=""  placeholder="请输入证件号">-->
					</li>
					<li :if="@port.type == 'students' ">
						<input id="idCard" class="form-control input-sm" type="text" name="" placeholder="请输入学籍号">
					</li>
					<li>
						<select id="sex" class="form-control input-sm">
							<option value="">性别</option>
							<option value="1">男</option>
							<option value="2">女</option>
						</select>
					</li>
					<li>
						<button :click="@search" class="btn btn-primary btn-sm" type="submit">查询</button>
					</li>
					<li>
						<button :click="@clearFn" class="btn btn-default btn-sm" type="reset">重置</button>
					</li>
					<li :if="@port.type == 'teachers' ">
						<button :click="@chooseAll" class="btn btn-default btn-sm" type="submit">选择所有教师</button>
					</li>
				</ul>
			</form>
			<!-- 条件搜索 E-->
			<!-- 搜索列表 S-->
			<div class="tableWrap">
				<table>
					<thead>
						<tr>
							<td><input ms-duplex-checked="@allCheck"
									   data-duplex-changed="@checkAll($event,el)"
									   type="checkbox"
                                       :if="@port.type == 'teachers' || @port.type == 'students'">姓名</td>
							<td>性别</td>
							<td :if=" @port.type == 'students'">学籍号</td>
							<td>所在节点</td>
						</tr>
					</thead>
					<tbody>
						<tr :for="(index,el) in @dataList">
							<td>
								<label :if="@port.type == 'initiator'">
                                    <input :duplex="@port.echoData.userId"
                                           :attr="{value:el.id}"
                                           data-duplex-changed="@callBackFn($event,el)"
                                           type="radio" name="initiator-id">&nbsp;{{el.name}}
                                </label>
                                <label :if="@port.type == 'teachers' || @port.type == 'students'">
                                    <input :duplex="@port.echoData.userId"
                                           :attr="{value:el.id}"
                                           data-duplex-changed="@callBackFn($event,el)"
                                           type="checkbox">&nbsp;{{el.name}}
                                </label>
							</td>
							<td>{{el.sex}}</td>
							<td :if=" @port.type == 'students'">
								{{el.xjh}}
							</td>
							<td>{{el.nodeName}}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 搜索列表 E-->
			<!-- 分页操作 S-->
			<div id="pages"></div>
			<!-- 分页操作 E-->
		</div>
	</div>
</div>
<script type="text/javascript">
    /**
     * Array.prototype.[method name] allows you to define/overwrite an objects method
     * needle is the item you are searching for
     * this is a special variable that refers to "this" instance of an Array.
     * returns true if needle is in the array, and false otherwise
	 * 定义数组的contains方法
     */
    Array.prototype.contains = function ( needle ) {
        for (i in this) {
            if (this[i] == needle) return true;
        }
        return false;
    }
    /**
	 * 删除元素
     * @param needle
     * @returns {boolean}
     */
    Array.prototype.removeArrayElement = function ( needle ) {
        for (i in this) {
            if (this[i] == needle){
              this.splice(i,1);
			}
        }
        return true;
    }


var zTreeObj,
setting = {
    data:{},
    callback:{
    	onClick:function(event,treeId,treeNode){
    		vmPeople._data.nodeids = treeNode.id;
            vmPeople._data.current='1';
    		vmPeople.clearFn();
            vmPeople.search();
            vmPeople.port.echoData.userId = [];
            vmPeople.port.echoData.name = [];
    	}
    },
    view:{
        selectedMulti:false
    }
};

var vmPeople = avalon.define({
    $id:'evenPeople',
    port:{},
	allCheck:false,
    _data:{
    	idCard:'',//身分证
    	name:'',//姓名
    	sex:'',//性别
    	nodeids:'',//树结构ID
        current:'1',//*当前页
        size:'15'//*分页大小
    },
	dataSize:'0',
    userId:[],
    dataList:[],
    ztreeList:function(){
        $.ajax({
            url: vmPeople.port.treeUrl,
            type: 'GET',
            dataType: 'json',
            success:function(data){
                var ztreeData = data.result;
                zTreeObj = $.fn.zTree.init($("#treeDemo"),setting,ztreeData);
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = treeObj.getNodes();
                for (var i = 0; i < nodes.length; i++) { //设置节点展开
                    treeObj.expandNode(nodes[i], true, false, true);
                }
            }
        })
    },
	chooseAll:function () {
		$.ajax({
			url:"${ctx}/tree/all/teacherIds",
			success:function (data) {
				var ids = data.result.split(",");
                vmPeople.userId = ids;
                vmPeople.dataListFn();
            }
		});
    },
    dataListFn:function(){
        $.ajax({
            url: vmPeople.port.url,
            type: 'POST',
            dataType: 'json',
            data: vmPeople.$model._data,
            success:function(data){
                vmPeople.dataList = data.result.records;
                var userList = [];
                //向数组中加入已经添加的人的userId
                for(var i=0;i<vmPeople.dataList.length;i++){
                    if( !userList.contains(vmPeople.dataList[i].id)){
                        userList.push(vmPeople.dataList[i].id);
                    }
                }
                vmPeople.dataSize = userList.length;
                layui.use('laypage', function(){
                    var laypage = layui.laypage;
                    laypage.render({
                        elem:'pages',
                        limit:vmPeople._data.size,
                        count:data.result.total,
                        groups:4,
                        theme:'pages',
                        curr:data.result.current || 1,
                        jump:function(obj, first){
                            if(!first){
                                vmPeople._data.current = obj.curr;
                            	vmPeople.dataListFn();
                            }
                        }
                    });
                });
                vmPeople.port.echoData.userId = [];
                vmPeople.port.echoData.name = [];
                //向数组中加入已经添加的人的userId
				for(var i=0;i<vmPeople.dataList.length;i++){
				    if( vmPeople.userId.contains(vmPeople.dataList[i].id)){
				        if(!vmPeople.port.echoData.userId.contains(vmPeople.dataList[i].id)){
                            vmPeople.port.echoData.userId.push(vmPeople.dataList[i].id);
						}
					}
				}
				if(vmPeople.port.echoData.userId.length == vmPeople.dataSize){
				    if(vmPeople.port.echoData.userId.length!=0){
                        vmPeople.allCheck = true;
					}else{
                        vmPeople.allCheck = false;
					}
				}else{
                    vmPeople.allCheck = false;
				}
            },
            error:function(data){
                layer.msg(data.msg);
            }
        })
    },
    callBackFn:function(event,el){
        var _name = el.name,
            _userId = el.id,
            _this = $(event.target);
        if(_this.attr('checked')){  //选中点击
            if(!vmPeople.userId.contains(_userId)){
                vmPeople.userId.push(_userId);
			}
			//如果全部选中
            var userIds = vmPeople.port.echoData.userId;
            if(userIds.length == vmPeople.dataSize){
                vmPeople.allCheck = true;
			}
        }else{
            if(vmPeople.userId.contains(_userId)){
                vmPeople.userId.removeArrayElement(_userId);
            }
            vmPeople.allCheck = false;
        }
    },
	checkAll:function (event,el) {
        var _this = $(event.target);
        if(_this.attr('checked')){  //选中点击
			for(var i=0;i<vmPeople.dataList.length ; i++){
			    if(!vmPeople.port.echoData.userId.contains(vmPeople.dataList[i].id)){
                    vmPeople.port.echoData.userId.push(vmPeople.dataList[i].id);
				}
				if(!vmPeople.userId.contains(vmPeople.dataList[i].id)){
                    vmPeople.userId.push(vmPeople.dataList[i].id)
				}
			}
        }else{
            for(var i=0;i<vmPeople.dataList.length ; i++) {
                if (vmPeople.port.echoData.userId.contains(vmPeople.dataList[i].id)) {
                    vmPeople.port.echoData.userId.removeArrayElement(vmPeople.dataList[i].id);
                }
                if (vmPeople.userId.contains(vmPeople.dataList[i].id)) {
                    vmPeople.userId.removeArrayElement(vmPeople.dataList[i].id)
                }
            }
        }
    },
	search:function () {
        this._data.idCard=$("#idCard").val();
        this._data.name=$("#name").val();
        this._data.sex=$("#sex").val();
        this._data.current='1';
        this.dataListFn();
    },
    clearFn:function(){
        $("#idCard").val('');
		$("#name").val('');
		$("#sex").val('');
    }
});
</script>