package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.dao.UserDao;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDao userDao;

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("users");
        modelAndView.addObject("user", userDao.getById(id));
        return modelAndView;
    }
}
