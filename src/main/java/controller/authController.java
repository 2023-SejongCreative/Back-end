package controller;

import dto.loginDto;
import dto.userDto;
import entity.userEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.userService;


@Controller

public class authController {

    private final userService userService;

    public authController(userService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam("email") String email,
                        @RequestParam("password") String password){


        loginDto loginDto = new loginDto(email, password);

        userEntity userEntity = userService.login(loginDto);

        return ResponseEntity.ok("성공");
    }
}
