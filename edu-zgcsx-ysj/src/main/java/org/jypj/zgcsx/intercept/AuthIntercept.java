package org.jypj.zgcsx.intercept;

import org.apache.log4j.Logger;
import org.jypj.zgcsx.common.dto.DtoMenu;
import org.jypj.zgcsx.controller.LoginController;
import org.jypj.zgcsx.entity.User;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by jian_wu on 2017/11/17.
 */
public class AuthIntercept extends HandlerInterceptorAdapter {

    private Logger log = Logger.getLogger(HandlerInterceptorAdapter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean flag = true;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        //登录不拦截
        String login = "login";
        String index = "index";
        if (uri.contains(login) || uri.contains(index)) {
            return true;
        }

        User user = LoginController.getUser(request);
        //没有登录，无法访问
        if (user == null) {
            //1.异步请求
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
                response.setHeader("sessionstatus", "timeout");
                response.setStatus(408);
                return false;
            } else {
                //2.普通请求
                //无用户则登录
                response.sendRedirect(StringUtil.getLocalUrl(request) + "/login");
                return false;
            }

        }

        //是否重复登录
        String oldSessionId = LoginController.getUserMap().get(user.getUserid());
        if (session.getId().equals(oldSessionId)) {
            flag = true;
        } else {
            //重复登录，旧用户登出
            if (request.getHeader("x-requested-with") != null
                    && "XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
                response.setHeader("sessionstatus", "timeout");
                response.setStatus(408);
            } else {
                //重复登录，旧用户登出
                response.sendRedirect(StringUtil.getLocalUrl(request) + "/loginOut");
            }
            return false;
        }

        //用户角色信息为空或者为0拦截
        if (StringUtil.isEmpty(user.getRoleSize())) {
            response.sendRedirect(StringUtil.getLocalUrl(request) + "/login");
            return false;
        }

        //3.不能在地址栏输入其他角色的菜单，而不拦截
        //3.1异步不拦
        if (request.getHeader("x-requested-with") != null
                && "XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
            return true;
        }
        //3.2拦截菜单
        String ctx = request.getContextPath();
        String uriWithOutCtx = uri.substring(ctx.length());
        flag = checkMenu(user.getMenuList(), StringUtil.removeHenggang(uriWithOutCtx));
        //3.3排除事件详情,排除上传文件,排除下载文件
        if (uri.contains("notice/detail")
                || uri.contains("notice/upload")
                || uri.contains("notice/downLoad")
                || uri.contains("space/to/add")
                || uri.contains("space/to/edit")) {
            flag = true;
        }
        //3.4 修改事件的权限在控制器里判断
        if (uri.contains("update/page")) {
            flag = true;
        }
        if (flag == false) {
            response.sendRedirect(StringUtil.getLocalUrl(request) + "/index");
        }
        return true;
    }

    /**
     * 检查输入的uri是否在菜单中
     *
     * @param menuList
     * @param uri
     * @return
     */
    public static Boolean checkMenu(List<DtoMenu> menuList, String uri) {
        if (menuList != null) {
            for (DtoMenu menu : menuList) {
                if (StringUtil.isNotEmpty(menu.getUrl()) && uri.equals(StringUtil.removeHenggang(menu.getUrl()))) {
                    return true;
                }
                if (menu.getSubMenu() != null && checkMenu(menu.getSubMenu(), uri)) {
                    return true;
                }
            }
        }
        return false;
    }

}
