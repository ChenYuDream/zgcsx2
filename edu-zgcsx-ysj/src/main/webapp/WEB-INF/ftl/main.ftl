<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <#include "common-tpl/commonHead.ftl"/>
</head>
<body :class="@skin" class="skin ms-controller" ms-controller="main">
<#include "common-tpl/right-wrap.ftl"/>
<div class="container">
    <#include "common-tpl/left-menu.ftl"/>
    <!-- 主内容区域 S-->
    <div class="main">
        <#include "common-tpl/common-nav.ftl"/>
        <!-- table切换 E-->
        <#if model??>
            <#if model=='txl'>
                <section class="main-wrap  ms-controller" ms-controller="txlwh">
            <#else>
                <section class="main-wrap">
            </#if>
        </#if>

        <div class="content">
                <#if model??>
                    <#if model=='calendar'>
                    <#else>
                        <#include "common-tpl/quick-nav.ftl"/>
                    </#if>
                </#if>

            <!-- 模版切换 S-->
                <#if model??>
                    <#if model=='event_add' || model=='event_update' || model == 'addNoticeForOther'>
                        <#include "main-tpl/eventForm.ftl"/>
                        <#include "common-tpl/footer.ftl"/>
                    </#if>
                    <#if model=='event_list'>
                        <#include "main-tpl/eventList.ftl"/>
                    </#if>
                    <#if model=='event_detail'>
                        <#include "main-tpl/eventDetails.ftl"/>
                        <#include "common-tpl/footer.ftl"/>
                    </#if>
                    <#if model=='event_list_all'>
                        <#include "main-tpl/eventListAll.ftl"/>
                    </#if>
                    <#if model=='time_shaft'>
                        <#include "main-tpl/timeShaft.ftl"/>
                        <#include "common-tpl/footer.ftl"/>
                    </#if>
                    <#if model=='txl'>
                        <#include "main-tpl/txlwh.ftl"/>
                        <#include "common-tpl/footer.ftl"/>
                    </#if>
                    <#if model=='calendar'>
                        <#include "main-tpl/calendar.ftl"/>
                    </#if>
                    <#if model=='spaceList'>
                        <#include "main-tpl/spaceList.ftl"/>
                    </#if>
                    <#if model=='spaceEdit'||model=='spaceAdd'>
                        <#include "main-tpl/spaceEdit.ftl"/>
                    </#if>
                </#if>
            <!-- 模版切换 E-->
        </div>
    </section>
    </div>
    <!-- 主内容区域 E-->
</div>
</body>
</html>