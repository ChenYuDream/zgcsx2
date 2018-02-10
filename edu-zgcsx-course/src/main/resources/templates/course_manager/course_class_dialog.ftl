<!-- 顶部工具栏 -->
<div class="dialogBox" ms-important="classes">
    <div class="classesWrap">
        <ul class="clearfix">
            <li :for="(index,el) in @dataList" :class="[(@opt.active == index ? 'active':'')]"
                :click="@clickFn(index,el)"><a href="javascript:void(0)">({{el.name}})班</a></li>
        </ul>
    </div>
</div>
<script type="text/javascript">
    var vmClasses = avalon.define({
        $id: 'classes',
        opt: {
            url: '',
            active: null,
            data: {}
        },
        dataList: [],
        clickFn: function (_index, _el) {
            this.opt.active = _index;
            this.opt.data = _el.$model;
        }
    });
</script>