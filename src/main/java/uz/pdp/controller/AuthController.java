package uz.pdp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.dto.UserSignupDto;
import uz.pdp.model.User;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping
    public String auth() {
        return "auth";
    }

    @PostMapping("/signup")
    public ModelAndView login(
            @ModelAttribute UserSignupDto signupDto,
            ModelAndView modelAndView
    ) {
        final var user = User.builder()
                .username(signupDto.username())
                .email(signupDto.email())
                .password(signupDto.password())
                .gender(signupDto.gender())
                .build();

        modelAndView.addObject("user", user);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
