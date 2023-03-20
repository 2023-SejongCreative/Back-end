package controller;


import dto.userDto;
import entity.userEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import service.userService;

@Controller
@RequiredArgsConstructor
public class authController {
    //생성자 주입
    private final userService userService;


    @PostMapping("/register")
    public String register(@ModelAttribute userDto userDto){
        userService.register(userDto);
        return "/";
    }


}
