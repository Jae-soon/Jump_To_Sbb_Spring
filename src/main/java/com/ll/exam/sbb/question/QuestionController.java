package com.ll.exam.sbb.question;

import com.ll.exam.sbb.answer.AnswerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }
}