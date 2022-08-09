package com.ll.exam.sbb;

import com.ll.exam.sbb.article.dto.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        int age = (int) session.getAttribute("age");

        return """
                <h1>저장한 나이</h1>
                <span>%d</span>
                """.formatted(age);
    }

    private List<ArticleDto> articles = new ArrayList<>(
            Arrays.asList(
                    new ArticleDto("제목", "내용"),
                    new ArticleDto("제목", "내용"))
    );

    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(String title, String body) {
        ArticleDto articleDto = new ArticleDto(title, body);

        articles.add(articleDto);


        return """
                <h1>%d번 글이 등록되었습니다!</h1>
                """.formatted(articleDto.getId());
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public ArticleDto getArticleById(@PathVariable int id) {
        ArticleDto articleDto = articles.stream().filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        return articleDto;
    }

    @GetMapping("/modifyArticle/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id, String title, String body) {
        ArticleDto articleDto = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if (articleDto == null) {
            return "%d번 글은 존재하지 않는다.".formatted(id);
        }

        articleDto.setTitle(title);
        articleDto.setBody(body);

        return """
                <h1>%d번 글이 수정되었습니다!</h1>
                """.formatted(articleDto.getId());
    }

    @GetMapping("/deleteArticle/{id}")
    @ResponseBody
    public String deleteArticle(@PathVariable int id) {
        ArticleDto articleDto = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if (articleDto.getId() == id) {
            articles.remove(articleDto);
        }

        return """
                <h1>%d번 글이 삭제되었습니다!
                """.formatted(id);
    }

    List<Person> people = new ArrayList<>();

    @GetMapping("/addPerson")
    @ResponseBody
    public String addPerson(@ModelAttribute Person p) {
        people.add(p);

        return """
                <h1>id : %d / name = %s / age = %d</h1>
                """.formatted(p.getId(), p.getName(), p.getAge());
    }

    @GetMapping("/addPerson2/{id}")
    @ResponseBody
    public Person addPerson2(Person p) {
        return p;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {
    private int id;
    private int age;
    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
