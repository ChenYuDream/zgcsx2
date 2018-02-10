package org.jypj.zgcsx.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jypj.zgcsx.common.dto.Result;
import org.jypj.zgcsx.common.utils.IpUtil;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jian_wu on 2017/10/25.
 *
 * 切所有的requestMapping注解
 * 1.统一处理异常
 * 2.给页面加常量
 * @author jian_wu
 */
@Aspect
@Component
public class ControllerAop {

    private static final Logger log = Logger.getLogger(ControllerAop.class);

    private final String STRING = "java.lang.String";

    @Value("${common.project.url}")
    private String commonProjectUrl;

    /**
     * 控制器异常处理
     * 切所有的requestmapping注解
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void webPointcutMapping() {}


    @Around("webPointcutMapping()")
    public Object handleThrowingRequestMapping(ProceedingJoinPoint jp) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object result = null;
        //获取查询方法的相关参数
        Object[] obj = jp.getArgs();
        MethodSignature methodSignature = (MethodSignature)jp.getSignature();
        //获得返回类型
        Class<?> returnType = methodSignature.getReturnType();
        try {
            if(STRING.equals(returnType.getName())){
                //给页面加些常量
                request.setAttribute("ctx",request.getContextPath());
                request.setAttribute("currentTimeStr", StringUtil.getCurrentTimeAndWeek());
                request.setAttribute("uri", StringUtil.getUriWithOutCtx(request));
                request.setAttribute("commonProjectUrl",commonProjectUrl);
            }
            result = jp.proceed(obj);
            //无异常，成功
            return result;
        }catch (Exception e){
            e.printStackTrace();
            log.error("Controller异常，异常类型:{"+e+"},错误方法:{"+methodSignature+"},客户端IP:{"+ IpUtil.getIpAddress(request)+"}");
            //页面错误
            if(returnType.getName().equals(STRING)){
                request.setAttribute("error",e.getMessage());
                return "error";
            }
            //接口错误
            if("org.jypj.zgcsx.util.Result".equals(returnType.getName())){
                return new Result(Result.FAIL,"服务器错误",e.getMessage());
            }
            return new Result(Result.FAIL,"服务器错误");
        }
    }

}
