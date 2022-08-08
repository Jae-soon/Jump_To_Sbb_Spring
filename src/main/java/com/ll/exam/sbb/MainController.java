package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {
    private int increaseNumber = -1;
    @RequestMapping("/sbb") // GET POST 둘 다 Mapping 가능
    @ResponseBody
    public String index() {
        return "안녕하세요. ㅎㅇㅎㅇ^^.";
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showPage1() {
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이" />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, GET 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/plus")
    @ResponseBody
    public String showPlus(@RequestParam(defaultValue = "0") int a, int b) {
        return """
                <h1>계산기 - 덧셈</h1>
                <h1>결과 값은 : %d</h1>
                """.formatted(a + b);
    }

    @GetMapping("/minus")
    @ResponseBody
    public String showMinus(@RequestParam(defaultValue = "0") int a, int b) {
        return """
                <h1>계산기 - 뺄셈</h1>
                <h1>결과 값은 : %d</h1>
                """.formatted(a - b);
    }

    @GetMapping("/increase")
    @ResponseBody
    public String showIncrease() {
        increaseNumber++;

        return "%d".formatted(increaseNumber);
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String showGugudan(int dan, int limit) {
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(dan, i, dan * i))
                .collect(Collectors.joining("<br>\n"));
    }

    @GetMapping("/mbti")
    @ResponseBody
    public String showMbti(@RequestParam(defaultValue = "권재순") String name) {
        if (name == "홍길동") {
            return "INFP";
        } else if (name == "홍길순") {
            return "ENFP";
        } else if (name == "임꺽정") {
            return "INFJ";
        } else if (name == "권재순") {
            return "ESFJ";
        }
        return "";
    }
}
