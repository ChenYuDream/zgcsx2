<!-- 快捷操作栏 S-->
<section class="clearfix">
    <!-- 面包绡 S-->
    <div class="bread-wrap pull-left">

        <#if model??>
            <#if model=='event_add'>
                <i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">云事件</a>&gt;
                <span class="last" href="#this" title="">添加事件</span>
            </#if>
            <#if model=='event_update'>
                <i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">云事件</a>&gt;
                <span class="last" href="#this" title="">修改事件</span>
            </#if>
            <#if model=='event_list'>
                <i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">云事件</a>&gt;
                <span class="last" href="#this" title="">事件列表</span>
            </#if>
            <#if model=='event_detail'>
                <i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">云事件</a>&gt;
                <span class="last" href="#this" title="">事件详情</span>
            </#if>
            <#if model=='event_list_all'>
                <i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">云事件</a>&gt;
                <span class="last" href="#this" title="">事件管理</span>
            </#if>
            <#if model=='addNoticeForOther'>
                <i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">云事件</a>&gt;
                <span class="last" href="#this" title="">事件代发</span>
            </#if>
            <#if model=='time_shaft'>
                <i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">云事件</a>&gt;
                <span class="last" href="#this" title="">时间轴</span>
            </#if>
            <#if model=='txl'><i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">通讯录</a>&gt;
                <span class="last" href="#this" title="">通讯录管理</span>
            </#if>
            <#if model=='calendar'>

            </#if>
            <#if model=='spaceList'>
                <i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">空间管理</a>&gt;
                <span class="last" href="#this" title="">空间管理</span>
            </#if>
            <#if model=='spaceEdit'>
                <i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">空间管理</a>&gt;
                <span class="last" href="#this" title="">空间管理编辑</span>
            </#if>
            <#if model=='spaceAdd'>
                <i class="iconfont icon-daohangshouye"></i>
                <a href="#this" title="">空间管理</a>&gt;
                <span class="last" href="#this" title="">空间管理新增</span>
            </#if>
        </#if>

    </div>
    <!-- 面包绡 S-->

    <div class="toolList pull-right">
        <ul class="clearfix">
            <!--返回只出现在事件详情页面-->
            <#if model??>
                <#if model=='event_add'>

                </#if>
                <#if model=='event_list'>

                </#if>
                <#if model=='event_detail'>
                    <li>
                        <!-- 返回上一页 S-->
                        <a class="item-btn"
                           href="javascript:history.go(-1)">
                            <i class="iconfont icon-icon"></i>返回</a>
                        <!-- 返回上一页 E-->
                    </li>
                </#if>
                <#if model=='event_list_all'>

                </#if>
                <#if model=='time_shaft'>

                </#if>
                <#if model=='txl'>
                    <li>
                        <!-- 新增 S-->
                        <a :if="@show" :click="@addedFn('新增主节点','主节点')" class="item-btn" href="#this">
                            <i class="iconfont icon-xinzeng"></i>新增</a>
                        <!-- 新增 E-->
                    </li>
                    <li>
                        <!-- 编辑 S-->
                        <a :click="@editFn($event)" :class="[(@show ? 'active':'')]" class="item-btn" href="#this">
                            <span :if="!@show"><i class="iconfont icon-htmal5icon16"></i>编辑</span>
                            <span :if="@show"><i class="iconfont icon-jian-copy"></i>完成</span>
                        </a>
                        <!-- 编辑 E-->
                    </li>
                </#if>
                <#if model=='calendar'>

                </#if>
                <#if model=='spaceEdit'||model=='spaceAdd'>
                    <li>
                        <!-- 返回上一页 S-->
                        <a class="item-btn" href="javascript:history.go(-1)">
                            <i class="iconfont icon-icon"></i>返回</a>
                        <!-- 返回上一页 E-->
                    </li>
                </#if>
            </#if>

        </ul>
    </div>
</section>
<!-- 快捷操作栏 E-->