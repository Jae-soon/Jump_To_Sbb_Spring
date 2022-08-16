package com.ll.exam.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor // 생성자 주입 -> final을 달면 autowired
public class QuestionController {
    private final QuestionService questionService; // QuestionRepository 직접연결이 아닌 QuestionService에 연결

    @RequestMapping("/list")
    // @ResponseBody 어노테이션이 없으면 resources/question_list/question_list.html 파일을 보여준다.
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();

        // 실행될 question_list.html에서
        model.addAttribute("questionList", questionList);

        return "question_list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}