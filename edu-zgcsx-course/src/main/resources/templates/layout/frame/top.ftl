<!-- 顶部工具栏 -->
<#macro top title>
<h1 class="title-pj text-center">${title}</h1>
<xmp :widget="{is:'ms-toolNavigation',navList:@navList,userInfo:@userInfo,skinFn:@skinFn,isActive:@skinShow,sysNum:@sysNum,logoutPath:@logoutPath,commonUrl:@commonUrl,roleSize:@roleSize}"/></xmp>
</#macro>