avalon.filters.checkEmpty = function (str) {
    return !str ? '' : '(' + str + ')';
}
var _tpl = heredoc(function () {
    /*
    <div class="nameFilWrap">
        <div class="head">
            <input :attr="{value:@_data.name}"
                   class="form-control input-sm" type="text"
                   readonly placeholder="请添加...">
            <button :click="@addFn($event) | prevent" type="button" class="btn btn-danger btn-xs">添加</button>
        </div>
        <div :visible="@isShow" class="listWrap">
            <div class="select-tab">
                <a class="active">全部</a>
            </div>
            <!-- 查询条件 S-->
            <div class="select-search">
                <form class="form-inline" onsubmit="return false;">
                    <div class="clearfix item-group">
                        <ul class="clearfix pull-left">
                            <li>
                                <span>教师姓名：</span>
                                <input :duplex="@searchValue" data-duplex-changed="@searchValueFn" class="form-control input-sm" type="text" name="" placeholder="请输入姓名...">
                            </li>
                        </ul>
                        <div class="toolList pull-right">
                            <ul class="clearfix">
                                <li>
                                    <!-- 查找 S-->
                                    <a :click="@search = @searchValue | prevent" class="item-btn" href="#this"><i class="iconfont icon-chazhao"></i>查找</a>
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
            </div>
            <!-- 查询条件 E-->
            <div class="select-content">
                <a :for="(index,el) in @dataList | filterBy(@searchFn,@search)"
                   :click="@activeFn(index,el)"
                   :class="[(@active==el.id)?'active':'']">{{el.name}}{{el.aliasName|checkEmpty}}
                  </a>
            </div>
        </div>
    </div>
    */
});

var vmNameFiltrate;
avalon.component("ms-nameFiltrate", {
    template: _tpl,
    defaults: {
        onInit: function (e) {
            vmNameFiltrate = e.vmodel;
        },
        onReady: function () {
            $(document).click(function (event) {
                var _con = $('.nameFilWrap');   // 设置目标区域
                if (!_con.is(event.target) && _con.has(event.target).length === 0) { // Mark 1
                    vmNameFiltrate.isShow = false;
                    vmNameFiltrate.search = "";
                }
            });
        },
        isShow: false,
        active: null,
        _data: {
            id: '',
            name: ''
        },
        searchValue: "",
        search: "",
        dataList: [],
        searchValueFn: function () {
            if (this.searchValue == '') {
                this.search = "";
                this.searchValue = "";
            }
        },
        searchFn: function (el, index, xxx) {
            if (xxx == '') {
                return el.name;
            }
            return el.name.indexOf(xxx) != -1;
        },
        clearFn: function () {
            this.search = "";
            this.searchValue = "";
        },
        addFn: function (e) {
            this.isShow = true;
        },
        activeFn: function (index, el) {
            this._data = el.$model;
            this.isShow = false;
            var tempTeacherName = vmAddTimeTable.$model.opt.teacherName;
            var tempTeacherId = vmAddTimeTable.$model.opt.teacherId;
            vmAddTimeTable.opt.teacherName = el.$model.name;
            vmAddTimeTable.opt.teacherId = el.$model.id;
            if (!vmAddTimeTable.teacherChangeFn()) {
                vmAddTimeTable.opt.teacherName = tempTeacherName;
                vmAddTimeTable.opt.teacherId = tempTeacherId;
                this.active = tempTeacherId;
            } else {
                this.active = el.$model.id;
            }
        }
    }
});
