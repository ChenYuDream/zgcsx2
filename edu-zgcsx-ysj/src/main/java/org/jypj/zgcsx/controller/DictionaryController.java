package org.jypj.zgcsx.controller;

import org.jypj.zgcsx.cache.CommonCache;
import org.jypj.zgcsx.common.dto.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jian_wu on 2017/11/10.
 * @author jian_wu
 */
@Controller
@RequestMapping("dict")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DictionaryController {

    @ResponseBody
    @RequestMapping("all")
    public Result getAll(){
        return new Result(Result.SUCCESS,"接口调用成功", CommonCache.getCodeMap());
    }

    @ResponseBody
    @RequestMapping("one/{code}")
    public Result getOne(@PathVariable("code") String code){
        return new Result(Result.SUCCESS,"接口调用成功", CommonCache.getCodeMap().get(code));
    }

}
