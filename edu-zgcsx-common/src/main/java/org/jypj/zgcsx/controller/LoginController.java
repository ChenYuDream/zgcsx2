package org.jypj.zgcsx.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.jypj.zgcsx.common.utils.HttpUtil;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.dto.DtoSys;
import org.jypj.zgcsx.entity.Role;
import org.jypj.zgcsx.entity.User;
import org.jypj.zgcsx.enums.UserType;
import org.jypj.zgcsx.service.RoleService;
import org.jypj.zgcsx.service.UserService;
import org.jypj.zgcsx.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by jian_wu on 2017/11/22.
 */
@Slf4j
@Controller
public class LoginController {

    @Value("${OLD_ZGCSX}")
    private String OLD_ZGCSX;

    @Value("${PERMIS_URL}")
    private String PERMIS_URL;

    @Value("${YSJ_URL}")
    private String JSY_URL;

    @Value("${DKC_URL}")
    private String DKC_URL;

    @Value("${SXJP_URL}")
    private String SXJP_URL;

    @Value("${WYXS_URL}")
    private String WYXS_URL;

    @Value("${ZCPS_URL}")
    private String ZCPS_URL;

    @Value("${XSXK_URL}")
    private String XSXK_URL;

    @Value("${CJLR_URL}")
    private String CJLR_URL;

    @Value("${CASOUT}")
    private String CASOUT;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DtoSys dtoSys;

    @RequestMapping("/")
    public String welcome() {
        return "redirect:/login";
    }

    @RequestMapping("login")
    public String Login(HttpSession session, HttpServletRequest request, String systemNum) {
        request.setAttribute("ctx", request.getContextPath());
        request.setAttribute("currentTimeStr", StringUtil.getCurrentTimeAndWeek());
        request.setAttribute("uri", StringUtil.getUriWithOutCtx(request));
        request.setAttribute("commonProjectUrl", StringUtil.getLocalUrl(request) + "/");
        request.setAttribute("systemNum", systemNum);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Object object = session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
            Assertion assertion = (Assertion) object;
            Map<String, Object> map = assertion.getPrincipal().getAttributes();
            user = userService.selectOne(new EntityWrapper<User>().eq("USERID", map.get("userid")).eq("IS_ENABLED", "1"));
        }

        List<DtoSys> dtoSysList = dtoSys.init();
        if (StringUtils.isNotEmpty(systemNum)) {
            List<DtoSys> syses = dtoSysList.stream().filter(sys -> Objects.equals(sys.getSysNum(), systemNum)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(syses)) {
                request.setAttribute("currentSystem", syses.get(0));
            }
        }
        if (user == null || user.getUsertype().equals(UserType.ADMIN.getCode())) {
            //admin 登录的情况，
            request.setAttribute("systemList", new ArrayList<>());
            return "NoRole";
        }
        List<Role> roleList = roleService.selectByLoginName(user.getLoginname());
        Map<String, List<Role>> roleMap = roleList.stream().collect(Collectors.groupingBy(Role::getRoleSystem));
        dtoSysList = dtoSysList.stream().filter(sys -> roleMap.containsKey(sys.getSysNum())).collect(Collectors.toList());
        request.setAttribute("systemList", dtoSysList);
        session.setAttribute("user", user);
        //1.如果不是教师，则跳到旧的三小的项目去
        if (!UserType.TEACHER.getCode().equals(user.getUsertype()) && !UserType.WUYE.getCode().equals(user.getUsertype())) {
            return "redirect:" + OLD_ZGCSX;
        }
        //2.如果是教师，则依次判断三个系统(7.云事件，8.大课程，9双向竞聘)，是否有角色，若只有一个角色，则直接跳转，如有多个角色，则跳转到角色选择页面
        for (int i = 7; i <= 13; i++) {
            //如果有传所属系统，则只判断传过来了的系统编号
            if (StringUtil.isNotEmpty(systemNum) && !systemNum.equals(i + "")) {
                continue;
            }
            JSONArray jsonRoles = checkHaveRole(user.getLoginname(), i + "", request);
            if (jsonRoles == null || jsonRoles.size() == 0) {
                continue;
            }
            if (jsonRoles.size() == 1) {
                String roleId = ((JSONObject) jsonRoles.get(0)).get("id").toString();
                String roleName = ((JSONObject) jsonRoles.get(0)).get("roleName").toString();
                String roleSize = "1";
                //直接跳转相应的系统
                switch (i) {
                    case 7:
                        System.out.println("跳转云事件");
                        try {
                            return "redirect:" + JSY_URL + "?roleId=" + roleId + "&roleSize=" + roleSize + "&roleName=" + URLEncoder.encode(roleName, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    case 8:
                        System.out.println("跳转大课程");
                        try {
                            return "redirect:" + DKC_URL + "?roleId=" + roleId + "&roleSize=" + roleSize + "&roleName=" + URLEncoder.encode(roleName, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    case 9:
                        System.out.println("跳转双向竞聘");
                        try {
                            return "redirect:" + SXJP_URL + "?roleId=" + roleId + "&roleSize=" + roleSize + "&roleName=" + URLEncoder.encode(roleName, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    case 10:
                        System.out.println("跳转物业修缮");
                        try {
                            return "redirect:" + WYXS_URL + "?roleId=" + roleId + "&roleSize=" + roleSize + "&roleName=" + URLEncoder.encode(roleName, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    case 11:
                        System.out.println("跳转职称评审");
                        try {
                            return "redirect:" + ZCPS_URL + "?roleId=" + roleId + "&roleSize=" + roleSize + "&roleName=" + URLEncoder.encode(roleName, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    case 12:
                        System.out.println("成绩录入");
                        try {
                            return "redirect:" + CJLR_URL + "?roleId=" + roleId + "&roleSize=" + roleSize + "&roleName=" + URLEncoder.encode(roleName, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    case 13:
                        System.out.println("学生选课");
                        try {
                            return "redirect:" + XSXK_URL + "?roleId=" + roleId + "&roleSize=" + roleSize + "&roleName=" + URLEncoder.encode(roleName, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    default:
                        break;
                }
            } else {
                Integer roleSize = jsonRoles.size();
                //跳转选择角色页面
                switch (i) {
                    case 7:
                        request.setAttribute("url", JSY_URL);
                        break;
                    case 8:
                        request.setAttribute("url", DKC_URL);
                        break;
                    case 9:
                        request.setAttribute("url", SXJP_URL);
                        break;
                    case 10:
                        request.setAttribute("url", WYXS_URL);
                        break;
                    case 11:
                        request.setAttribute("url", ZCPS_URL);
                        break;
                    case 12:
                        request.setAttribute("url", CJLR_URL);
                        break;
                    case 13:
                        request.setAttribute("url", XSXK_URL);
                        break;
                    default:
                        break;
                }
                //传递角色信息
                request.setAttribute("rolesStr", jsonRoles.toString());
                request.setAttribute("roles", jsonRoles);
                request.setAttribute("roleSize", roleSize);
                request.setAttribute("sysNum", i);
                //处理角色头像
                handleImage(jsonRoles, request);
                return "ChoseRole";
            }
        }
        return "NoRole";
    }

    public void handleImage(JSONArray jsonRoles, HttpServletRequest request) {
        //把角色头像存到static/image下面，存的名称是 角色编码+(状态).png
        try {
            String staticPath = request.getServletContext().getRealPath("/") + "static";
            String imagePath = staticPath + "/image";
            for (int i = 0; i < jsonRoles.size(); i++) {
                String roleCode = (String) jsonRoles.getJSONObject(i).get("roleCode");
                String roleName = (String) jsonRoles.getJSONObject(i).get("roleName");
                File baseImage = new File(staticPath + "/baseImage/1.png");
                BufferedImage image = ImageIO.read(baseImage);
                image = ImageUtil.insertText(image, roleName, 1, 0);
                ImageIO.write(image, "png", new FileImageOutputStream(new File(imagePath + "/" + roleCode + "(1).png")));
                File baseImage2 = new File(staticPath + "/baseImage/2.png");
                BufferedImage image2 = ImageIO.read(baseImage2);
                image2 = ImageUtil.insertText(image2, roleName, 2, 0);
                ImageIO.write(image2, "png", new FileImageOutputStream(new File(imagePath + "/" + roleCode + "(2).png")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("loginOut")
    public String loginOut(HttpServletRequest request, HttpSession session, String systemNum) {
        if (session != null) {
            session.invalidate();
        }
        String url = request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath() + "/login";
        if (StringUtil.isNotEmpty(systemNum)) {
            url = url + "?systemNum=" + systemNum;
        }
        return "redirect:" + CASOUT + "?service=http://" + url;
    }

    /**
     * 判断是否有角色，有则返回角色数据
     */
    public JSONArray checkHaveRole(String loginName, String systemNum, HttpServletRequest request) {
        String localUrl = StringUtil.getLocalUrl(request);
        JSONObject object = HttpUtil.getJson(localUrl + "/api/getRole?loginName=" + loginName + "&systemNum=" + systemNum);
        JSONArray jsonRoles = object.getJSONArray("result");
        if (jsonRoles == null) {
            return null;
        }
        return jsonRoles;
    }

    /**
     * 动态获取头像
     */
    @ResponseBody
    @RequestMapping("role/image")
    public void getRoleImage(String imageName, HttpServletResponse response, HttpServletRequest request) {
        String staticPath = request.getServletContext().getRealPath("/") + "static";
        String imagePath = staticPath + "/image";
        try {
            // 设置相应信息的类型
            File file = new File(imagePath + "/" + imageName);
            InputStream is = new FileInputStream(file);
            response.reset();
            response.setContentType("image/jpeg");
            OutputStream os = null;
            os = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int bytesRead;
            byte[] buffer = new byte[5 * 1024];
            while ((bytesRead = bis.read(buffer)) > 0) {
                // 将文件发送到客户端
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            bis.close();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
