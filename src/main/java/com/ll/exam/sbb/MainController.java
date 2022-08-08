package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    @GetMapping("/plus2")
    @ResponseBody
    public void showPlus2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));

        resp.getWriter().append(a + b + "");
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

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String showMbti(@PathVariable String name) {
        return switch (name) {
            case "홍길동" -> "INFP";
            case "홍길순" -> "INFJ";
            case "임꺽정" -> "ENFP";
            case "권재순" -> "ESFJ";
            default -> "모름";
        };
    }

    @GetMapping("/saveSessionAge/{age}")
    @ResponseBody
    public String saveSession(@PathVariable int age, HttpServletRequest req) {
        // req 안에 쿠키가 있기 때문에 선언 => JSESSIONID => 세션을 얻는다.
        HttpSession session = req.getSession();

        session.setAttribute("age", age);

        return """
                <h1>세션 저장 완료!</h1>
                """;
    }

    @GetMapping("/getSessionAge")
    @ResponseBody
    public String showSession(HttpSession session) {
        int age = (int)session.getAttribute("age");

        return """
                <h1>저장한 나이</h1>
                <span>%d</span>
                """.formatted(age);
    }
}
