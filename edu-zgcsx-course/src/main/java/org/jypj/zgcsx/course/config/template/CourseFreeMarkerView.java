package org.jypj.zgcsx.course.config.template;

import freemarker.template.SimpleHash;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.course.constant.CommonConstant;
import org.jypj.zgcsx.course.entity.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class CourseFreeMarkerView extends FreeMarkerView {

    @Override
    protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        SimpleHash fmModel = super.buildTemplateModel(model, request, response);
        UserInfo userInfo;
        try {
            userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (NullPointerException | ClassCastException e) {
            userInfo = new UserInfo();
        }
        fmModel.put(CommonConstant.SESSION_USER_INFO_KEY, userInfo);
        fmModel.put("ctx", request.getContextPath());
        fmModel.put("uri", StringUtil.getUriWithOutCtx(request));
        return fmModel;
    }

    /*@Override
    protected void doRender(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Expose model to JSP tags (as request attributes).
        exposeModelAsRequestAttributes(model, request);
        // Expose all standard FreeMarker hash models.
        SimpleHash fmModel = buildTemplateModel(model, request, response);

        if (logger.isDebugEnabled()) {
            logger.debug("Rendering FreeMarker template [" + getUrl() + "] in FreeMarkerView '" + getBeanName() + "'");
        }
        // Grab the locale-specific version of the template.
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        processTemplate(getTemplate(locale), fmModel, response);
    }*/
}
