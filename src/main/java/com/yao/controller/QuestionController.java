package com.yao.controller;


import com.yao.common.CustomizeResponseCode;
import com.yao.common.Result;
import com.yao.entity.Question;
import com.yao.entity.dto.PublishQuestionDto;
import com.yao.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author long
 * @since 2023-03-14
 */
@Api(tags = "问题管理")
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @ApiOperation("问题展示")
    @GetMapping("/questions")
    public Result questions(Integer currentPage){
        return questionService.questions(currentPage);
    }

    @ApiOperation("根据id查看问题")
    @GetMapping("/question/{id}")
    public Result questionById(@PathVariable("id") Long id){
        Question question = questionService.getById(id);
        if (question==null){
            return Result.fail(CustomizeResponseCode.QUESTION_NOT_FOUND.getMessage());
        }
        return Result.succ(CustomizeResponseCode.QUESTION_FOUND_SUCCESS.getMessage(),question);
    }

    @ApiOperation("新增问题或修改问题")
    @PostMapping("/publish")
    public Result publishQuestion(@RequestBody @Validated PublishQuestionDto publishQuestionDto, BindingResult result){
        if (result.hasErrors()){
            return Result.fail(CustomizeResponseCode.QUESTION_NOT_NULL.getMessage());
        }
        return questionService.saveorUpdateQuestion(publishQuestionDto);
    }

    @ApiOperation("删除问题")
    @GetMapping("/delete{id}")
    public Result deleteQuestion(@PathVariable("id") Long id){
        boolean b = questionService.removeById(id);
        if (b==true){
            return Result.succ(CustomizeResponseCode.QUESTION_DELETE_SUCCES.getMessage());
        }
        return Result.fail(CustomizeResponseCode.QUESTION_DELETE_FAIL.getMessage());
    }

    @ApiOperation("逻辑删除问题")
    @GetMapping("/delete1/{id}")
    public Result deleteQuestion1(@PathVariable("id") Long id) {
        return questionService.deleteQuestion(id);
    }

    @ApiOperation("根据用户id查问题")
    @GetMapping("/questions/{id}")
    public Result questionsById(@PathVariable("id") Long id){
        return questionService.selectById(id);
    }


    @ApiOperation("增加阅读数")
    @GetMapping("/increaseView")
    public Result increaseView(@RequestParam Long id){
        if (id==null){
            return Result.fail(CustomizeResponseCode.QUESTION_NOT_FOUND.getMessage());
        }
        return questionService.increaseView(id);
    }


}
