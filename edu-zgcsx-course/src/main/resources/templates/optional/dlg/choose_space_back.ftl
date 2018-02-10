<style>
    .dialogBox .tabTitle {
        height: 306px;
    }
</style>
<div class="dialogBox" :controller="chooseSpaceController">
    <div class="row">
        <!-- tableTitle S-->
        <div class="col-sm-2">
            <div class="tabTitle ">
                <ul>
                    <li :for="(index,el) in @list"
                        :click="@a = @index,@b = 0,@c = 0,@space = @el.list[0].list[0].list[0]"
                        :class="@a == @index ? 'active':''">{{el.name}}
                    </li>
                </ul>
            </div>
        </div>
        <div class="col-sm-2">
            <div class="tabTitle ">
                <ul>
                    <li :for="(index,el) in @list[@a].list"
                        :click="@b = @index,@c = 0"
                        :class="@b == @index ? 'active':''">{{el.name}}
                    </li>
                </ul>
            </div>
        </div>
        <div class="col-sm-2">
            <div class="tabTitle ">
                <ul>
                    <li :for="(index,el) in @list[a].list[b].list"
                        :click="@c = @index,@space = @el.list[0]"
                        :class="@c == @index ? 'active':''">{{el.name}}
                    </li>
                </ul>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="tabTitle">
                <ul>
                    <li :for="(index,el) in @list[a].list[b].list[c].list"
                        :click="@space = @el"
                        :class="@space.id == @el.id ? 'active':''">{{'('+el.typeName+')'+el.name}}
                    </li>
                </ul>
            </div>
        </div>
        <!-- tableTitle E-->
    </div>
</div>
<script type="text/javascript">
    var chooseSpaceVm = avalon.define({
        $id: 'chooseSpaceController',
        list: [],
        space: {},
        a: 0, b: 0, c: 0,
        loadData: function (space) {
            $.post('${ctx}/api/space', function (list) {
                var _list = list;
                if (space) {
                    for (var i = 0; i < _list.length; i++) {
                        if (space.campusCode === _list[i].code) {
                            chooseSpaceVm.a = i;
                            _list = _list[i].list;
                            break;
                        }
                    }
                    for (i = 0; i < _list.length; i++) {
                        if (space.archCode === _list[i].code) {
                            chooseSpaceVm.b = i;
                            _list = _list[i].list;
                            break;
                        }
                    }
                    for (i = 0; i < _list.length; i++) {
                        if (space.floorCode === _list[i].code) {
                            chooseSpaceVm.c = i;
                            break;
                        }
                    }
                } else {
                    space = list[0].list[0].list[0].list[0];
                }
                chooseSpaceVm.list = list;
                chooseSpaceVm.space = space;
            })
        }
    });
</script>