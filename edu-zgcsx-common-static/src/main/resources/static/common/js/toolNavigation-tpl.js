var _tpl = heredoc(function(){
    /*
    <section id="js_fixed_header" class="header-wrap">
        <!-- 系统菜单分类 S-->
        <div :visible="@isShow" class="systematics-wrap">
            <ul class="clearfix">
                <li :for="(index,el) in @navList" :class="[(el.sysNum == @sysNum ? 'active':'')]">
                    <a :click="@gotoFn(el)">
                        <i :class="el.sysIcon" class="iconfont"></i>
                        <span class="text-overflow">{{el.sysName}}</span>
                    </a>
                </li>
            </ul>
        </div>
        <!-- 系统菜单分类 E-->
        <div class="clearfix">
            <div class="info pull-left">
                <em class="userImg"></em>
                你好！<strong>{{@userInfo.userName + (@userInfo.roleName?('('+@userInfo.roleName+')'):'')}} </strong>今天是:<strong>{{@userInfo.time}}</strong>
            </div>
            <div class="toolList pull-right">
                <ul class="clearfix">
                    <li :if="@roleSize != 1">
                        <!-- 切换角色 S-->
                            <a :click="@roleGotoFn($event)" :class="[(@roleActive ? 'active':'')]" class="item-btn" href="javascript:viod(0)" title=""><i class="iconfont icon-qiehuanyonghu"></i>切换角色</a>
                        <!-- 切换角色 S-->
                    </li>
                    <li>
                        <!-- 系统导航 S-->
                        <a :click="@isShowFn" :class="[(@isShow ? 'active':'')]" class="item-btn" href="#this" title=""><i class="iconfont icon-fenlei"></i>系统导航</a>
                        <!-- 系统导航 S-->
                    </li>
                    <li>
                        <!-- 退出系统 S-->
                        <a :click="@logoutFn" class="item-btn" href="#this" title=""><i class="iconfont icon-tuichu"></i>退出系统</a>
                        <!-- 退出系统 S-->
                    </li>
                    <li>
                        <!-- 其它 S-->
                        <a :click="@skinFn()" :class="[(@isActive ? 'active':'')]" class="item-btn" href="#this" title="系统操作">
                            <i class="iconfont icon-fenlei1" style="margin-right: 0"></i>
                        </a>
                        <!-- 其它 S-->
                    </li>
                </ul>
            </div>
        </div>
    </section>
    */
});

var vmToolNavigation;
avalon.component("ms-toolNavigation",{
    template:_tpl,
    defaults:{
        onInit:function(e){
            vmToolNavigation = e.vmodel;
        },
        isShow:false,
        isActive:false,
        roleActive:false,
        sysNum:'',
        roleSize:'',
        navList:[],
        userInfo:{},
        logoutPath:'',
        commonUrl:'',
        skinFn:avalon.noop,
        isShowFn:function(){
            this.isShow =! this.isShow;
        },
        gotoFn:function(el){

            if(el.sysNum != this.sysNum){
                layer.confirm("<div class='text-center'>你是否要跳转到<b>《"+ el.sysName +"》</b>?</div>",{title:'系统跳转',btnAlign:'c'},function(){
                    window.location.href=el.sysUrl;
                });
            }
            return;
        },
        logoutFn:function(){
            layer.confirm("<div class='text-center'>你确定要退出当前系统吗?</div>",{title:'系统退出',btnAlign:'c'},function(){
                window.location.href=vmToolNavigation.logoutPath;
            });
        },
        roleGotoFn:function(e){
            e.preventDefault();
            if(vmToolNavigation.commonUrl != ''){
                window.location.href=vmToolNavigation.commonUrl+'login?systemNum='+vmToolNavigation.sysNum;
            }
            return;
        }
    }
});