package com.example.TTCN2.controller;

import com.example.TTCN2.domain.User;
import com.example.TTCN2.repository.UserRepository;
import com.example.TTCN2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    // sửa thông tin tài khoản
    @GetMapping("/updateCus/{idCus}")
    public String updateCus(HttpSession session, Model model, @PathVariable("idCus") Integer idCus){
        User customer = (User) session.getAttribute("saveCus");
        model.addAttribute("customer",customer);
        return "user/receiver/updateCus";
    }
    @PostMapping("/updateCus/{idCus}")
    public String updateCus(@PathVariable("idCus") Integer idCus,
                            @RequestParam("fullName")String fullName,
                            @RequestParam("email")String email,
                            @RequestParam("username")String username,
                            HttpSession session,
                            Model model){
        User customer = (User) session.getAttribute("saveCus");
        if (fullName.isBlank() ){ // isBlank : ktra kí tu khoảng trắng và emty
            model.addAttribute("message","Full name Không hợp lệ");
            model.addAttribute("customer",customer);
            return "user/receiver/updateCus";
        }
        String emailCut = email.replace("@gmail.com",""); // replace thay kí tự na thành kí tự khác
        if (emailCut.isBlank() ){ // isBlank : ktra kí tu khoảng trắng và emty
            model.addAttribute("message","email Không hợp lệ");
            model.addAttribute("customer",customer);
            return "user/receiver/updateCus";
        }
        if (username.isBlank() ){ // isBlank : ktra kí tu khoảng trắng và emty
            model.addAttribute("message","Username Không hợp lệ");
            model.addAttribute("customer",customer);
            return "user/receiver/updateCus";
        }
        customer.setName(fullName);
        customer.setEmail(email);
        customer.setUsername(username);
//        customer.setPassword(password);
        userService.save(customer);
        return "redirect:/receiver/receiver/{idCus}";
    }
}
