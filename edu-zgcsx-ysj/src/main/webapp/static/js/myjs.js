/**
 * Created by jian_wu on 2017/11/29.
 */
//jian_wu 页面跳转，先检查地址是否有效（用于页面跳转，或者下载）
function safelyGoToLoaction(url) {
    $.ajax({
        url: url,
        type: 'GET',
        complete: function(response){
            if(response.status == 404){
                alert("链接地址已经失效");
            }else if(response.status == 500){
                alert("链接地址打开错误");
            }else if(response.status == 200){
                location.href = url;
            }else if(response.status == 409){
                //后台定义的409 文件下载错误
                alert("文件链接失效");
            }else if(response.status == 408){
                //后台定义的408 登录失效
                top.location.href = _loginOut;
            }else{
                alert("未知错误");
            }
        }
    });
}

/**
 * 设置未来(全局)的AJAX请求默认选项
 * 主要设置了AJAX请求遇到Session过期的情况
 */
$.ajaxSetup({
    type: 'POST',
    complete: function (xhr, status) {
        var sessionStatus = xhr.getResponseHeader('sessionstatus');
        if (sessionStatus == 'timeout') {
            top.location.href = _loginOut;
        }
    }
});
