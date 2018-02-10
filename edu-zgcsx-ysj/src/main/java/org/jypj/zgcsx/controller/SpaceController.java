package org.jypj.zgcsx.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.jypj.zgcsx.common.dto.Result;
import org.jypj.zgcsx.common.utils.StringUtil;
import org.jypj.zgcsx.entity.CodeMap;
import org.jypj.zgcsx.entity.Space;
import org.jypj.zgcsx.service.CodeMapService;
import org.jypj.zgcsx.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 空间管理控制器
 *
 * @author yu_chen
 * @create 2018-02-06 9:45
 **/
@Controller
@RequestMapping("space")
public class SpaceController {


    private CodeMapService codeMapService;

    private SpaceService spaceService;


    @Autowired
    public SpaceController(CodeMapService codeMapService, SpaceService spaceService) {
        this.codeMapService = codeMapService;
        this.spaceService = spaceService;
    }

    /**
     * 跳转到空间管理列表页
     *
     * @param model
     * @return
     */
    @RequestMapping("to/list")
    public String toList(Model model) {
        model.addAttribute("model", "spaceList");
        initModel(model);
        return "main";
    }


    /**
     * 跳转到新增页面
     *
     * @param model 属性
     * @return
     */
    @RequestMapping("to/add")
    public String toAdd(Model model) {
        Space space = new Space();
        model.addAttribute("space", space);
        model.addAttribute("model", "spaceAdd");
        initModel(model);
        return "main";
    }

    /**
     * 跳转到编辑页面
     *
     * @param model 模块
     * @param id    主键ID
     * @return
     */
    @RequestMapping("to/edit/{id}")
    public String toEdit(Model model, @PathVariable("id") String id) {
        Space space = spaceService.selectById(id);
        model.addAttribute("space", space);
        model.addAttribute("model", "spaceEdit");
        initModel(model);
        return "main";
    }

    /**
     * 分页查询空间管理数据
     *
     * @param page        分页对象
     * @param name        建筑名称
     * @param campusValue 校区
     * @param typeValue   类型值
     * @param floorValue  楼层
     * @return Result
     */
    @RequestMapping("query/page")
    @ResponseBody
    public Result selectAllSpace(Page<Space> page, String name, String campusValue, String typeValue, String floorValue) {
        //查询出分页数据
        Page<Space> spacePage = spaceService.selectPage(page, new EntityWrapper<Space>()
                .like(StringUtil.isNotEmpty(name), "mc", name)
                .eq(StringUtil.isNotEmpty(campusValue), "campus", campusValue)
                .eq(StringUtil.isNotEmpty(typeValue), "sptype", typeValue)
                .eq(StringUtil.isNotEmpty(floorValue), "floorid", floorValue)
                .orderBy("campus,floorid")
        );
        //翻译字典数据
        List<Space> records = spacePage.getRecords();
        for (Space record : records) {
            record.setCampusTag(codeMapService.queryItemText("cims_campus", record.getCampus()));
            record.setSptypeTag(codeMapService.queryItemText("cims_sptype", record.getSptype()));
            record.setFlooridTag(codeMapService.queryItemText("cims_floor", record.getFloorid()));
        }
        return new Result(spacePage);
    }

    /**
     * 保存或者更新空间数据
     *
     * @param space 空间管理对象
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public Result saveOrUpdateSpace(Space space) {
        if (StringUtil.isNotEmpty(space.getId())) {
            space.setMtime(new Date());
        } else {
            space.setCtime(new Date());
        }
        boolean flag = spaceService.insertOrUpdate(space);
        return new Result(flag ? "0" : "-1", "");
    }

    /**
     * 按ID删除记录
     *
     * @param id
     * @return
     */
    @RequestMapping("delete/{id}")
    @ResponseBody
    public Result deleteSpace(@PathVariable("id") String id) {
        boolean b = spaceService.deleteById(id);
        return new Result(b ? "0" : "-1");
    }

    /**
     * 初始化字典数据
     *
     * @param model
     */
    private void initModel(Model model) {
        List<CodeMap> xiaoQuList = codeMapService.queryCodeMap("cims_campus");
        List<CodeMap> louCengList = codeMapService.queryCodeMap("cims_floor");
        List<CodeMap> leiXingList = codeMapService.queryCodeMap("cims_sptype");
        List<CodeMap> jianZhuList = codeMapService.queryCodeMap("cims_arch");

        model.addAttribute("xiaoQuList", xiaoQuList);
        model.addAttribute("louCengList", louCengList);
        model.addAttribute("leiXingList", leiXingList);
        model.addAttribute("jianZhuList", jianZhuList);
    }
}
