package org.jypj.zgcsx.course.config.security;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RoleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        String roleId = request.getParameter("roleId");
        String roleSize = request.getParameter("roleSize");
        String roleName = request.getParameter("roleName");
        if (StringUtils.isNoneEmpty(roleId, roleSize, roleName)) {
            HttpSession session = request.getSession();
            session.setAttribute("roleId", roleId);
            session.setAttribute("roleSize", roleSize);
            session.setAttribute("roleName", roleName);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
