package edu.zera.student.manager.controller;

import edu.zera.student.manager.model.User;
import edu.zera.student.manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index(HttpServletRequest request,Model model){
        String username = (String) request.getSession().getAttribute("user");
        if (username==null) {
            return "index";
        }
        model.addAttribute("un",userService.findAllUsername(username));
        return "index";
    }
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }
    @PostMapping("/register")
    public String getUserRegister(@RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  Model model,HttpServletRequest request){
       String un =  userService.register(username,password);
       if (un.equals("")){
           model.addAttribute("error",true);
           return "register";
       }
        request.getSession().setAttribute("user",username);

       return "redirect:/";
    }
    @GetMapping("login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("login")
    public String loginResult(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              Model model,
                              HttpServletRequest request){
        if (userService.checkLogin(username,password)){


            request.getSession().setAttribute("user",username);
            return "redirect:/";
        }
        model.addAttribute("error","user is not exist or had disable");
        return  "login";
    }

    @GetMapping("profile")
    public String showProfile(HttpServletRequest request,Model model){
        String username = (String) request.getSession().getAttribute("user");
        if (username==null) {
            model.addAttribute("error",true);
             return "/";
        }
        model.addAttribute("user",username);
        model.addAttribute("enable",userService.isEnableUser(username));

        return "profile";
    }
    @GetMapping("enable")
    public String enableUser(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("user");
        userService.enableUser(username,!userService.isEnableUser(username));
        return "redirect:/profile";
    }
    @GetMapping("enable/{name}")
    public String enableUserByAdmin(
            @PathVariable(name ="name" ) String name
            ,HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("user");
        if (!userService.isAdmin(username)){
            return "redirect:/profile";
        }
        userService.enableUser(name,!userService.isEnableUser(name));
        return "redirect:/";
    }
    @GetMapping("logout")
    public String logout(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("user");
        if (username==null) {
            return "/";
        }
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
