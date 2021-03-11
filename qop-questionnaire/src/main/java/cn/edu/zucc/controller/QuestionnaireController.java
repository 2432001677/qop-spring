package cn.edu.zucc.controller;

import cn.edu.zucc.common.vo.ResultPageVo;
import cn.edu.zucc.common.vo.ResultVo;
import cn.edu.zucc.questionnaire.po.Foo;
import cn.edu.zucc.repository.questionnaire.FooRepository;
import cn.edu.zucc.utils.ResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.TreeMap;

@Api(tags = "问卷")
@RefreshScope
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    private int a = 1;
    @Resource
    private FooRepository fooRepository;

    @ApiOperation("测试MongoDB")
    @GetMapping
    public ResultPageVo<Foo> getAllUsers() {
        Foo foo = new Foo();
        foo.setName("xx");
        Map<String, String> map = new TreeMap<>();
        map.put("key" + a++, "3");
        foo.setOptions(map);
        fooRepository.save(foo);
        return ResponseBuilder.buildSuccessPageableResponse(fooRepository.findAll(PageRequest.of(0, 10)));
    }

    @ApiOperation("ggg")
    @GetMapping("/get")
    public ResultVo<Foo> getOne(@RequestParam("id")String id) {
        Foo foo = fooRepository.findById(id).orElseThrow();
        return ResponseBuilder.buildSuccessResponse(foo);
    }
}
