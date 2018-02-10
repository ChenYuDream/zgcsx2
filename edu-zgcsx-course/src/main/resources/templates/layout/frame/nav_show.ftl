<#macro navShow breadWraps=[{"name":"首页"}]>
<!-- 快捷操作栏 S-->
<section class="clearfix">
    <!-- 面包绡 S-->
    <div class="bread-wrap pull-left">
        <a class="first" href="javascript:void(0)" title=""><strong><i class="iconfont icon-daohangshouye"></i>导航</strong></a>&gt;
        <#list breadWraps as bread>
            <#if bread_has_next>
                <a href="${bread.href}" title="${bread.name}">${bread.name}</a>&gt;
            <#else>
                <span class="last" href="${bread.href!}" title="${bread.name}">${bread.name}</span>
            </#if>
        </#list>
    </div>
    <!-- 面包绡 S-->
    <div class="toolList pull-right">
        <ul class="clearfix">
            <li>
                <!-- 返回上一页 S-->
                <a :for="el in @toolBtnList" class="item-btn" :attr="{href:@el.href}">
                    <i class="iconfont" :class="'icon-'+@el.icon"></i>{{el.name}}
                </a>
                <#--<a class="item-btn" href="javascript:history.go(-1)">-->
                    <#--<i class="iconfont icon-icon"></i>返回-->
                <#--</a>-->
                <!-- 返回上一页 E-->
            </li>
        </ul>
    </div>
</section>
<!-- 快捷操作栏 E-->
</#macro>