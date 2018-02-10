<#include "/layout/layout.ftl"/>
<@page title="我上传的资源" hasFooter=false breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"我上传的资源"}]>
    <#include "/upload/tab/resource_tab.ftl">

<script type="text/javascript">
    vmUploadResource.show = true;
</script>
</@page>