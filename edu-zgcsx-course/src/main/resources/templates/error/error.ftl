<#include "/layout/layout.ftl"/>
<@page title="出错了！">
status------>${errors["status"]}<br>
error------->${errors["error"]}<br>
message--->${errors["message"]}<br>
path-------->${errors["path"]!}<br>
timestamp->${errors["timestamp"]?string("yyyy-MM-dd HH:mm:ss zzzz")}<br>
exception->${errors["exception"]!}<br>
</@page>