var winH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
window.onload=function(){
    $(".js_winHeight").css({height:winH + 'px'});
    var contentOffH = $(".content").offset().top,
        minH = winH - (contentOffH + 30);
    $('.content').css({'min-height':minH + 'px'});
    $(window).resize(function(){
        var winH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight,
            contentOffH = $(".content").offset().top,
            minH = winH - (contentOffH + 30);
        $(".js_winHeight").css({height:winH + 'px'});
        $('.content').css({'min-height':minH + 'px'});
    });
    //吸顶操作;
    scrollFn("#js_fixed_header");
};
//吸顶公共方法操作;
function scrollFn(id){
    var offTop = $(id).offset().top,
        maxW = $(id).outerWidth();
    $(window).scroll(function(){
        var fixed_header_height = $(id).outerHeight(),
            scrollH = $(document).scrollTop();
        if(scrollH >= offTop){
            $(id).css({
                'position':'fixed',
                'margin-top':'0px',
                'max-width':maxW
            });
            $(".main-wrap").css({
                'padding-top':(fixed_header_height + 30)+'px'
            })
        }else {
            $(id).css({
                'position':'static',
                'margin-top':'30px'
            });
            $(".main-wrap").css({
                'padding-top':'0px'
            })
        }
    });
}

//克隆
function clone(target) {   
    var buf;   
    if (target instanceof Array) {   
        buf = [];  //创建一个空的数组 
        var i = target.length;   
        while (i--) {   
            buf[i] = clone(target[i]);   
        }   
        return buf;
    }else if (target instanceof Object){   
        buf = {};  //创建一个空对象 
        for (var k in target) {  //为这个对象添加新的属性 
            buf[k] = clone(target[k]);   
        }   
        return buf;   
    }else{   
        return target;   
    }
}
/**随机生成UUID唯一标识码*/
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 32; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    var uuid = s.join("");
    return uuid;
}

/**Html文本片段截取*/
function heredoc(fn) {
    return fn.toString().replace(/^[^\/]+\/\*!?\s?/,'').replace(/\*\/[^\/]+$/, '').trim().replace(/>\s*</g, '><')
}

/**动态导入JS文件*/
function Import() { 
    for( var i=0; i<arguments.length; i++ )
    { 
        var file = arguments; 
        if ( file.match(/\\.js$/i))  
            document.write('<script type=\\"text/javascript\\" src=\\"' + file + '\\"></sc' + 'ript>'); 
        else 
            document.write('<style type=\\"text/css\\">@import \\"' + file + '\\" ;</style>'); 
    } 
}

//jian_wu 页面跳转，先检查地址是否有效（用于页面跳转，或者下载）
function safelyGoToLoaction(url) {
    $.ajax({
        url: url,
        type: 'GET',
        complete: function (response) {
            if (response.status == 404) {
                alert("链接地址已经失效");
            } else if (response.status == 500) {
                alert("链接地址打开错误");
            } else if (response.status == 200) {
                location.href = url;
            } else if (response.status == 409) {
                //后台定义的409 文件下载错误
                alert("文件链接失效");
            } else if (response.status == 408) {
                //后台定义的408 登录失效
                top.location.href = _loginOut;
            } else {
                alert("未知错误");
            }
        }
    });
}