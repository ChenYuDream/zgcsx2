<!-- 左侧菜单栏 -->
<#macro right>
<!-- 右侧功能模块 S-->
<xmp :widget="{is:'ms-toolSkin',isShow:@skinShow,isShowFn:@skinFn,skin:@skin,saveFn:@saveSkinFn,_path:@sysPath,commonUrl:@commonUrl,userId:@_data.userId}"/></xmp>
<!-- 右侧功能模块 E-->
</#macro>