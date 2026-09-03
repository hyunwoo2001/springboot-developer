package me.hwjo.springdeveloper;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {

        // "hello" 요청을 보내면 hello() 메서드 호출
        // http://localhost:8080/hello
        @GetMapping("/hello")
        public String hello() {
            return "Hello World";
        }

        @GetMapping("/test")
        public String test() {
            return "Hello test";
        }

        @DeleteMapping ("/test")
        public String DeleteTest() {
            return "Delete Test";
        }

        @PutMapping("/test")
        public String putTest() {
            return "putTest";
        }
}
