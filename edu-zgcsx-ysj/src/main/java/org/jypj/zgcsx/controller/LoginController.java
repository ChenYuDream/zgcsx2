package org.jypj.zgcsx.controller;


import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.jypj.zgcsx.common.dto.DtoMenu;
import org.jypj.zgcsx.common.dto.Result;
import org.jypj.zgcsx.common.utils.HttpUtil;
import org.jypj.zgcsx.common.utils.MenuUtil;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.entity.User;
import org.jypj.zgcsx.enums.UserType;
import org.jypj.zgcsx.service.UserService;
import org.jypj.zgcsx.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jian_wu on 2017/11/14.
 */
@Controller
public class LoginController {

    private Logger log = Logger.getLogger(LoginController.class);

    //存用户id和sessionId
    private static ConcurrentMap<String,String> UserMap = new ConcurrentHashMap<>();

    @Autowired
    private UserService userService;

    @Value("${CASOUT}")
    private String CASOUT;

    @Value("${common.project.url}")
    private String commonProjectUrl;

    //登录
    @RequestMapping("login")
    public String login(HttpSession session,String roleId,String roleSize,String roleName){
        User user = (User) session.getAttribute("user");
        if(user == null){
            Object object = session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
            Assertion assertion = (Assertion) object;
            Map<String, Object> map = assertion.getPrincipal().getAttributes();
            user =  userService.getObjectWithXnXq((String) map.get("userid"));
            if(user == null){
                //admin登录
                return "redirect:"+commonProjectUrl+"login?systemNum=7";
            }
            //加学校id和学区id
            setUserXXidAndXQid(user);
            //为判断是否重复登录，将sessionid 放到map中
            UserMap.put(user.getUserid(),session.getId());
            session.setAttribute("user",user);
            log.info(user.getUsername()+"--->login");
        }
        //从接口中获取角色和菜单信息
        if(StringUtil.isEmpty(roleId)||StringUtil.isEmpty(roleSize)){
            return "redirect:"+commonProjectUrl+"login?systemNum=7";
        }else{
            user.setRoleId(roleId);
            user.setRoleSize(roleSize);
            user.setRoleName(roleName);
            List<DtoMenu> menuList = new ArrayList<>();
            menuList=new MenuUtil(commonProjectUrl).getMenu(roleId);
            if(CollectionUtil.isNotEmpty(menuList)){
                user.setMenuList(menuList);
            }else{
                log.debug("未获取到菜单，user："+user.getUsername()+",userId:"+user.getUserid());
            }
        }
        return "redirect:index";
    }

    //登录成功后，赋予菜单，重定向到首页
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        final String noUrl = "#this";
        User user = getUser(request);
        String localUrl = StringUtil.getLocalUrl(request);
        if(user.getMenuList()!=null&&user.getMenuList().size()>0){
            DtoMenu dtoMenu = user.getMenuList().get(0);
            String parentUrl = dtoMenu.getUrl();
            if(StringUtil.isNotEmpty(parentUrl)&&!noUrl.equals(parentUrl)){
                return "redirect:"+localUrl+parentUrl;
            }
            if(dtoMenu.getSubMenu()!=null&&dtoMenu.getSubMenu().size()>0){
                String url = dtoMenu.getSubMenu().get(0).getUrl();
                return "redirect:"+localUrl+url;
            }
        }
        return "redirect:notice/to/add";
    }

    //欢迎页，直接重定向到登录
    @RequestMapping("/")
    public String welCome(){
        return "redirect:login";
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpServletRequest request,HttpSession session){
        session.invalidate();
        String url;
        if(request.getLocalPort() == 80){
            url = request.getLocalAddr()+request.getContextPath()+"/login";
        }else{
            url = request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath()+"/login";
        }
        return "redirect:"+CASOUT+"?service=http://"+url;
    }

    /**
     * 获取密码
     * @return
     */
    @ResponseBody
    @RequestMapping("password/get")
    public Result getPassword(HttpServletRequest request){
        String userId = getUserId(request);
        JSONObject object = HttpUtil.getJson(commonProjectUrl+"/api/get/password?userId="+userId);
        if(Result.FAIL.equals(object.get("code").toString())){
            return new Result(Result.FAIL,"密码查询失败");
        }
        return new Result(object.get("result").toString());
    }

    /**
     * 获取密码
     * @return
     */
    @ResponseBody
    @RequestMapping("password/change")
    public Result changePassword(HttpServletRequest request,String newPassword){
        Integer pwdMinSize = 6;
        if(StringUtil.isEmpty(newPassword)){
            return new Result(Result.FAIL,"密码参数错误");
        }
        if(newPassword.length()<pwdMinSize){
            return new Result(Result.FAIL,"密码长度不能少于6位");
        }
        String userId = getUserId(request);
        JSONObject object = HttpUtil.getJson(commonProjectUrl+"/api/change/password?userId="+userId+"&newPassword="+newPassword);
        if(Result.FAIL.equals(object.get("code").toString())){
            return new Result(Result.FAIL,"密码修改失败");
        }
        return new Result();
    }

    //获取user
    public static User getUser(HttpServletRequest request){
        Object attr = request.getSession().getAttribute("user");
        if (attr == null) {
            return null;
        }
        return (User) attr;
    }

    //获取userId
    public static String getUserId(HttpServletRequest request){
        Object attr = request.getSession().getAttribute("user");
        if (attr == null) {
            return null;
        }
        return ((User) attr).getUserid();
    }

    //set学年，学期
    public void  setUserXXidAndXQid(User user){
        if(StringUtil.isNotEmpty(user.getUsertype())){
            if(user.getUsertype().equals(UserType.TEACHER.getCode())){
                User tempUser = userService.getTeacherXxidXqId(user.getUserid());
                user.setXxid(tempUser.getXxid());
                user.setXqid(tempUser.getXqid());
            }else if(user.getUsertype().equals(UserType.STUDENT.getCode())){
                User tempUser = userService.getStudentXxidXqId(user.getUserid());
                user.setXxid(tempUser.getXxid());
                user.setXqid(tempUser.getXqid());
            }else if(user.getUsertype().equals(UserType.WUYE.getCode())){
                User tempUser = userService.getWuYeXxidXqId(user.getUserid());
                user.setXxid(tempUser.getXxid());
                user.setXqid(tempUser.getXqid());
            }
        }
    }

    public static Map<String, String> getUserMap() {
        return UserMap;
    }
}
