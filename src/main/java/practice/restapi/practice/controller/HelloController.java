package practice.restapi.practice.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/string")
    @ResponseBody
    public String helloWorld(){
        return "helloworld";
    }

    @GetMapping("/json")
    @ResponseBody
    public Hello helloWorldJson(){
        Hello hello = new Hello();
        hello.message = "helloworld";
        return hello;
    }

    @GetMapping("/page")
    public String helloWorldPage(){
        return "/helloWorld.html";
    }

    @Data
    public static class Hello{
        private String message;
    }

}
