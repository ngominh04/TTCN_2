package com.example.TTCN2.controller;

import com.example.TTCN2.domain.*;
import com.example.TTCN2.repository.*;
import com.example.TTCN2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    DetailAdminRepository detailAdminRepository;
    @Autowired
    ChatBoxRepository chatBoxRepository;
    @Autowired
    ChatBoxDetailRepository chatBoxDetailRepository;
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

        //thay doi lai sender phia bang chat_box_detail
        List<ChatBoxDetail> chatBoxDetails = chatBoxDetailRepository.findAll();
        for (ChatBoxDetail chatBoxDetail : chatBoxDetails){
            if(Objects.equals(chatBoxDetail.getSender(), customer.getName())){
                chatBoxDetail.setSender(fullName);
                chatBoxDetailRepository.save(chatBoxDetail);
            }
        }
        //end save sender in chat_box_detail

        // save recipient in chat_box_detail when replace updateCus
        for (ChatBoxDetail chatBoxDetail1 : chatBoxDetails){
            if(Objects.equals(chatBoxDetail1.getRecipient(),customer.getName())){
                chatBoxDetail1.setRecipient(fullName);
                chatBoxDetailRepository.save(chatBoxDetail1);
            }
        }
        //end save recipient

        customer.setName(fullName);
        customer.setEmail(email);
        customer.setUsername(username);
        customer.setUpdateDate(String.valueOf(LocalDateTime.now()));
//        customer.setPassword(password);
        userService.save(customer);

        return "redirect:/receiver/receiver/{idCus}";
    }
    // show all user to admin
    @GetMapping("/admin/showUser")
    public String showUser(HttpSession session, Model model,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        // phân trang cho trang chủ
        int currentPage = page.orElse(1); // số trang
        int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
        Page<User> users = userService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("users", users);

        int totalPages = users.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("maxPageNumber",pageNumbers.size());
        }
        return "admin/user/showUser";
    }
    // block user
    @GetMapping("/admin/blockUser/{isUser}")
    public String blockUser(HttpSession session, Model model,
                            @PathVariable("isUser")Integer idUser){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        User user = userRepository.getCustomerById(idUser);
        if (user.getBlock() == 0){
            user.setBlock(1);
            userRepository.save(user);
            return "redirect:/user/admin/showUser";
        }else {
            user.setBlock(0);
            userRepository.save(user);
        }
        return "redirect:/user/admin/showUser";
    }
}
