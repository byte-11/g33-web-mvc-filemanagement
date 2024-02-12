package uz.pdp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.controller.exception.UserNotFoundException;
import uz.pdp.dao.UserDao;
import uz.pdp.dto.UserLoginDto;
import uz.pdp.dto.UserSignupDto;
import uz.pdp.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserDao userDao;

    @GetMapping
    public String auth(Model model) {
        model.addAttribute("dto", new UserSignupDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String login(
            @Valid @ModelAttribute("dto") UserSignupDto signupDto,
            BindingResult bindingResult, Model model
    ) {
        if (bindingResult.hasErrors()){
            return "signup";
        }

        final var user = User.builder()
                .username(signupDto.getUsername())
                .email(signupDto.getEmail())
                .password(signupDto.getPassword())
                .gender(signupDto.getGender())
                .build();

//        userDao.save(user);
//        userDao.saveByNamedParameter(user);
        userDao.saveBySimpleInsert(user);
        model.addAttribute("user", user);
        return "index";
    }

    @PostMapping("/login")
    public String login(Model modelAndView, @ModelAttribute UserLoginDto loginDto) {
        User user;
        user = userDao.findByEmail(loginDto.getEmail());
        try {
//            user = userDao.findByEmail(loginDto.getEmail());
        } catch (Exception e) {
//            modelAndView.addAttribute("error", "User not found");
//            return "auth";
            throw new UserNotFoundException(loginDto.getEmail());
        }
        modelAndView.addAttribute("user", user);
//        modelAndView.setViewName("index");
        return "index";
    }

    /*@ExceptionHandler({UserNotFoundException.class, EmptyResultDataAccessException.class})
    public String handle(Model model, Exception ex) {
//        modelAndView.setViewName("auth");
//        modelAndView.addObject("error", ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "auth";
    }*/
}
