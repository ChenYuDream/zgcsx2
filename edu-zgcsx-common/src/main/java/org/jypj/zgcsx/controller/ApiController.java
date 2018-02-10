package org.jypj.zgcsx.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.jypj.zgcsx.common.dto.Result;
import org.jypj.zgcsx.common.utils.HttpUtil;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.dto.DtoMenu;
import org.jypj.zgcsx.dto.DtoSys;
import org.jypj.zgcsx.entity.Role;
import org.jypj.zgcsx.entity.Student;
import org.jypj.zgcsx.entity.Teacher;
import org.jypj.zgcsx.entity.User;
import org.jypj.zgcsx.service.*;
import org.jypj.zgcsx.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jian_wu on 2017/11/22.
 */
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api")
public class ApiController {

    @Value("${PERMIS_URL}")
    private String PERMIS_URL;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private DtoSys dtoSys;

    @Autowired
    private PurviewService purviewService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    /**
     * 这是老接口，需要permis系统支持
     *
     * @param loginName
     * @param systemNum
     * @return
     */
    @RequestMapping("menuWithRole")
    public Result getUserNemuAndRole(String loginName, String systemNum) {
        if (StringUtil.isEmpty(loginName) || StringUtil.isEmpty(systemNum)) {
            return new Result(Result.FAIL, "参数不全");
        }
        JSON jsonObject = HttpUtil.getJson(PERMIS_URL + "api/purview/" + loginName + "-" + systemNum);
        return new Result(jsonObject);
    }

    /**
     * 新接口，只查所属系统对应的角色
     *
     * @return
     */
    @RequestMapping("getRole")
    public Result getUserRole(String loginName, String systemNum) {
        Map<String, Object> queryMap = new HashMap<>(16);
        if (StringUtil.isEmpty(loginName) || StringUtil.isEmpty(systemNum)) {
            return new Result(Result.FAIL, "参数不全");
        }
        queryMap.put("loginName", loginName);
        queryMap.put("systemNum", systemNum);
        List<Role> list = roleService.selectByUsernameAndSys(queryMap);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        return new Result(jsonArray);
    }

    /**
     * 获取角色菜单（root菜单的子集是页面显示的菜单数组）
     *
     * @param roleId
     * @return
     */
    @RequestMapping("menu")
    public Result getMenuByRoleId(String roleId) {
        DtoMenu menu = resourceService.selcetMenu(roleId);
        return new Result(menu);
    }

    /**
     * 查背景图片编号
     *
     * @return
     */
    @RequestMapping("background/image")
    public Result getBackImage(String userId) {
        if (StringUtil.isEmpty(userId)) {
            return new Result(Result.FAIL, "参数错误");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("USERID", userId).eq("IS_ENABLED", "1"));
        return new Result(user.getBackgroundImage());
    }

    /**
     * 换背景图片
     *
     * @return
     */
    @RequestMapping("background/change")
    public Result changeBackImage(String skin, String userId) {
        if (StringUtil.isEmpty(userId)) {
            return new Result(Result.FAIL, "userId错误");
        }
        if (StringUtil.isEmpty(skin)) {
            return new Result(Result.FAIL, "参数错误");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("USERID", userId).eq("IS_ENABLED", "1"));
        if (user == null) {
            return new Result(Result.FAIL, "用户不存在");
        }
        user.setBackgroundImage(skin);
        userService.updateAllColumnById(user);
        return new Result(user.getBackgroundImage());
    }

    /**
     * 查询原始密码
     *
     * @param session
     * @return
     */
    @RequestMapping("get/password")
    public Result getPassword(HttpSession session, String userId) {
        //String userId = getUserId(session);
        if (StringUtil.isEmpty(userId)) {
            return new Result(Result.FAIL, "未登录");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("USERID", userId).eq("IS_ENABLED", "1"));
        if (user == null) {
            return new Result(Result.FAIL, "用户不存在");
        }
        return new Result(user.getPasswordReal());
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping("change/password")
    public Result changePassword(String newPassword, HttpSession session, String userId) {
        //String userId = getUserId(session);
        if (StringUtil.isEmpty(userId)) {
            return new Result(Result.FAIL, "未登录");
        }
        if (StringUtil.isEmpty(newPassword)) {
            return new Result(Result.FAIL, "参数错误");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("USERID", userId).eq("IS_ENABLED", "1"));
        if (user == null) {
            return new Result(Result.FAIL, "用户不存在");
        }
        try {
            user.setPassword(MD5Util.MD5(newPassword));
            user.setPasswordReal(newPassword);
            userService.updateById(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Result.FAIL, "未知错误", e);
        }
        return new Result();
    }

    /**
     * 系统导航
     *
     * @return
     */
    @RequestMapping("sys/guid")
    public Result sysGuid() {
        return new Result(dtoSys.init());
    }

    /**
     * 获得用户的名字和时间信息
     *
     * @return
     */
    @RequestMapping("helloInfo")
    public Result getHelloInfo(String userId) {
        User user = userService.selectOne(new EntityWrapper<User>().eq("userId", userId).eq("IS_ENABLED", "1"));
        if (user == null) {
            return new Result(Result.FAIL, "用户不存在");
        }
        String time = StringUtil.getCurrentTimeAndWeek();
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("userName", user.getUsername());
        resultMap.put("time", time);
        return new Result(resultMap);
    }


    /**
     * 把getHelloInfo和sysGuid数据合并
     * 方便前端处理
     *
     * @return
     */
    @RequestMapping("pageInfo")
    public Result getPageInfo(String userId, String roleId) {
        if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(roleId)) {
            return new Result(Result.FAIL, "参数错误");
        }
        Result getHelloInfo = getHelloInfo(userId);
        Result sysGuid = sysGuid();
        if (Result.FAIL.equals(getHelloInfo.getCode()) || Result.FAIL.equals(sysGuid.getCode())) {
            return new Result(Result.FAIL, "接口错误");
        }
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("helloInfo", getHelloInfo.getResult());
        List<DtoSys> sysList = (List<DtoSys>) sysGuid.getResult();
        List<Role> roleList = roleService.selectByUserId(userId);
        Map<String, List<Role>> roleMap = roleList.stream().collect(Collectors.groupingBy(Role::getRoleSystem));
        sysList = sysList.stream().filter(sys -> roleMap.containsKey(sys.getSysNum())).collect(Collectors.toList());
        resultMap.put("sysGuid", sysList);
        //目录
        DtoMenu menu = resourceService.selcetMenu(roleId);
        if (menu.getSubMenu() != null) {
            resultMap.put("menu", menu.getSubMenu());
        }
        //背景
        Result backImage = getBackImage(userId);
        if (backImage.getCode().equals(Result.FAIL)) {
            return new Result(Result.FAIL, "背景接口错误");
        }
        resultMap.put("skin", backImage.getResult());
        return new Result(resultMap);
    }

    /**
     * 数据范围
     *
     * @param loginName
     * @param roleId
     * @return
     */
    @RequestMapping("user/data")
    public Result getUserData(String loginName, String roleId) {
        return purviewService.getUserData(loginName, roleId);
    }

    /**
     * userId查教师
     *
     * @return
     */
    @RequestMapping("teachers")
    public Result getTeacherInfo(@RequestParam(value = "teacherIds[]", required = false) String[] teacherIds) {
        if (teacherIds == null || teacherIds.length == 0) {
            return new Result(Result.FAIL, "参数错误");
        }
        List<Teacher> list = teacherService.selectList(new EntityWrapper<Teacher>().in("id", teacherIds).eq("valid", "1"));
        return new Result(list);
    }

    /**
     * userId查学生
     *
     * @return
     */
    @RequestMapping("students")
    public Result getStudentsInfo(@RequestParam(value = "studentIds[]", required = false) String[] studentIds) {
        if (studentIds == null || studentIds.length == 0) {
            return new Result(Result.FAIL, "参数错误");
        }
        List<Student> list = studentService.selectList(new EntityWrapper<Student>().in("id", studentIds).eq("valid", "1"));
        return new Result(list);
    }

    /**
     * 封装，从cas获取userId
     */
    public String getUserId(HttpSession session) {
        Object object = session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        if (object == null) {
            return null;
        }
        Assertion assertion = (Assertion) object;
        Map<String, Object> map = assertion.getPrincipal().getAttributes();
        String userId = (String) map.get("userid");
        return userId;
    }

}
