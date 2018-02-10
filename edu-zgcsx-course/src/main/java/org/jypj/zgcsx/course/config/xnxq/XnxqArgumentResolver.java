package org.jypj.zgcsx.course.config.xnxq;

import org.jypj.zgcsx.course.service.XnxqService;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class XnxqArgumentResolver implements HandlerMethodArgumentResolver {
    private final XnxqService xnxqService;

    public XnxqArgumentResolver(XnxqService xnxqService) {
        this.xnxqService = xnxqService;
    }

    /**
     * 参数注入
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(CurrentXnxq.class);
    }

    /**
     * 参数注入 将被过滤到的参数设置值
     *
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return xnxqService.selectCurrentXnxq();
    }
}
