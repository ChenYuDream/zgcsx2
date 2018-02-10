package org.jypj.zgcsx.course.error;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping({"/error"})
public class CourseErrorController extends BasicErrorController {

    public CourseErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, new ErrorProperties(), errorViewResolvers);
    }

    @RequestMapping(produces = {"text/html"})
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        final HttpStatus status = getStatus(request);
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> map = getErrorAttributes(request, true);
        modelAndView.addObject("errors", map);
        modelAndView.setStatus(status);
        modelAndView.setViewName("error/error");
        SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        try {
            modelAndView.addObject("onlineUser", securityContext.getAuthentication().getPrincipal());
        } catch (NullPointerException ignored) {
        }
        return modelAndView;
    }

    @RequestMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        HttpStatus status = getStatus(request);
        Map<String, Object> map = getErrorAttributes(request, true);

        switch (status) {
            case UNAUTHORIZED:
                break;
            case FORBIDDEN:
                break;
            case NOT_FOUND:
                break;
            case INTERNAL_SERVER_ERROR:
                break;
            default:
                break;
        }
        body.put("code", status.value());
        body.put("success", false);
        String exception = (String) map.get("exception");
        String msg;
        if (StringUtils.isEmpty(exception)) {
            msg = (String) map.get("error");
        } else if (Objects.equals(exception, "org.jypj.zgcsx.course.error.CourseException")||map.get("errors") != null) {
            msg = (String) map.get("message");
        } else {
            msg = exception;
        }
        body.put("msg", msg);
        return new ResponseEntity<>(body, status);
    }
}
