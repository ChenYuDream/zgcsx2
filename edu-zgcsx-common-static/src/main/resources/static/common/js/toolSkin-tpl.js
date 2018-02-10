var _tpl = heredoc(function(){
    /*
    <section :class="[(@isShow ? 'show':'')]" class="js_winHeight fixed-lr-wrap right-wrap">
        <div class="text-center close-btn">
            <i :click="@isShowFn" class="iconfont icon-X" title="关闭"></i>
        </div>
        <h3 class="text-center f24">功能操作区</h3>
        <div class="fn-tabList">
            <ul class="clearfix">
                <li>
                    <a :click="@passwordFn" class="item-btn" href="#this" title="">
                        <span class="icon"><i class="iconfont icon-artboard12"></i>&nbsp;密码</span>
                        <span class="text">修改密码</span>
                    </a>
                </li>
                <li>
                    <a class="item-btn active" href="#this" title="">
                        <span class="icon"><i class="iconfont icon-huanfu"></i>&nbsp;皮肤</span>
                        <span class="text">设置皮肤</span>
                    </a>
                    <div class="skin-wrap" dir="rtl">
                        <ul>
                            <li :for="(index,el) in @skinData"
                                :class="[(@skin == el.skinName ? 'active':''),(@flag == el.skinName ? 'flag' :'')]">
                                <div class="layout">
                                    <h5 class="text-center">?确认选择吗</h5>
                                    <a :click="@previewFn(el)" class="item-btn" href="#this" title="">预览</a>
                                    <a :click="@saveFn(el)" class="item-btn" href="#this" title="">设置</a>
                                </div>
                                <i class="sj iconfont icon-sjg"></i>
                                <div :css="{backgroundImage: 'url('+ @_path + el.url +')'}" class="img"></div>
                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
        <div class="footer-ico">
            <img :attr="{src:@_path + '/static/common/images/footer-ico.png'}" alt="">
        </div>
    </section>
    */
});

var _tplPW = heredoc(function(){
    /*
    <div class="password-wrap">
        <form id="formPW" class="form-horizontal" autocomplete="off"
              data-validator-option='{
                    stopOnError:false,
                    timely:3,
                    validClass: "has-succes",
                    invalidClass: "has-error",
                    bindClassTo: ".form-group",
                    focusCleanup:true
                }'>
            <div class="form-group">
                <label for="passwordOld" class="col-sm-3 control-label">原始密码<em class="text-danger">*</em>&nbsp;:</label>
                <div class="col-sm-9">
                    <input id="passwordOld" type="text" onfocus="this.type='password'" name="passwordOld"
                           data-rule="required;length(6~12);"
                           data-msg-required="原始密码不能为空"
                           data-tip="密码由6位~12位字符组成"
                           class="form-control" placeholder="输入原始密码">
                    <span id="msg-error" style="display:none;font-size:12px;margin-left:6px;color:#888;">原密码不正确，请重新输入</span>
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword1" class="col-sm-3 control-label">新密码<em class="text-danger">*</em>&nbsp;:</label>
                <div class="col-sm-9">
                    <input type="password" name="passwordNew" disabled
                           data-rule="新密码:required;length(6~12)"
                           data-msg-required="新密码不能为空"
                           data-tip="新密码必须由6~12位字符组成"     
                           class="form-control" id="inputPassword1" placeholder="输入新密码">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword2" class="col-sm-3 control-label">确认新密码<em class="text-danger">*</em>&nbsp;:</label>
                <div class="col-sm-9">
                    <input type="password" disabled
                           data-rule="确认密码:required;length(6~12);match(passwordNew)"
                           data-msg-required="你需再次确认新密码!"
                           class="form-control" id="inputPassword2" placeholder="确认新密码">
                </div>
            </div>
        </form>
    </div>
    */
});

var vmToolSkin;
avalon.component("ms-toolSkin",{
    template:_tpl,
    defaults:{
        onInit:function(e){
            vmToolSkin = e.vmodel;
            this.flag = avalon.vmodels.main.skin
        },
        flag:'',
        skin:'',
        _path:'',
        commonUrl:'',
        userId:'',
        skinData:[
            {
                skinName:'skin-night',
                url:'/static/common/images/skin-night.jpg'
            },
            {
                skinName:'skin-qinzhu',
                url:'/static/common/images/skin-qinzhu.jpg'
            }
        ],
        isShow:false,
        isShowFn:avalon.noop,
        saveFn:avalon.noop,
        passwordFn:function(){
            var _valNew;
            layer.open({
                type: 1,
                area: '400px',
                content: _tplPW,
                offset: '100px',
                anim: 1,
                title:'设置密码',
                btn: ['确定','取消'],
                btnAlign :'c',
                success:function(){
                    $.ajax({
                        url:vmToolSkin.$model.commonUrl + "api/get/password",
                        type:'POST',
                        dataType:'json',
                        data:{userId:vmToolSkin.$model.userId},
                        success:function(data){
                            var pwOld = data.result;
                            $('#passwordOld').on('valid.field',function(e,result){
                                _valNew = result.value;
                                if(pwOld == _valNew){
                                    $("#passwordOld").attr("readonly",true).css("background",'#dff0d8');
                                    $(":disabled",".password-wrap").attr("disabled",false);
                                    $("#msg-error").css('display','none');
                                }else{
                                    $("#passwordOld").addClass("bg-danger");
                                    $("#msg-error").css('display','block');
                                }
                            });
                        }
                    })
                },
                yes:function(index){
                    var _newPW = $("#inputPassword1")[0].value,
                        _newPW2 = $("#inputPassword2")[0].value;
                    if(_newPW == _valNew || _newPW2 == _valNew){
                        layer.msg("新密码不能与原密码相同!",{time:2000,offset:'200px'});
                        return;
                    }
                    if(_newPW && _newPW2 && _newPW==_newPW2){
                        $.ajax({
                            url:vmToolSkin.$model.commonUrl + "api/change/password",
                            type:'POST',
                            dataType:'json',
                            data:{newPassword:_newPW,userId:vmToolSkin.$model.userId},
                            success:function(){
                                layer.msg("密码修改成功!",{time:1000,offset:'200px'},function(){
                                    location.href = vmMain.logoutPath;
                                    layer.close(index);
                                });
                            }
                        });
                    }else{
                        $("#formPW").submit();
                    }                    
                }
            });
        },
        previewFn:function(el){
            avalon.vmodels.main.skin = el.skinName;
        }
    }
});