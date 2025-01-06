package com.example.TTCN2.controller;

import com.example.TTCN2.domain.Admin;
import com.example.TTCN2.domain.DetailAdmin;
import com.example.TTCN2.domain.User;
import com.example.TTCN2.repository.AdminRepository;
import com.example.TTCN2.repository.DetailAdminRepository;
import com.example.TTCN2.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AccountAdminController {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    DetailAdminRepository detailAdminRepository;
    @Autowired
    AdminService adminService;

    @GetMapping("/account/{idAdmin}")
    public String account(@PathVariable Integer idAdmin, Model model) {
        model.addAttribute("admin", adminRepository.getAdminById(idAdmin));
        model.addAttribute("detailAdmin", detailAdminRepository.findDetailAdminById(idAdmin));
        return "/admin/account/account";
    }

    // edit
    @GetMapping("/editAccount/{idAdmin}")
    public String edit(@PathVariable Integer idAdmin, Model model) {
        model.addAttribute("admin", adminRepository.getAdminById(idAdmin));
        model.addAttribute("detailAdmin", detailAdminRepository.findDetailAdminById(idAdmin));
        return "/admin/account/editAccount";
    }

    @PostMapping("/editAccountSave/{idAdmin}")
    public String editAccount(@PathVariable Integer idAdmin,
                              @RequestParam("username")String username,
                              @RequestParam("name")String name,
                              @RequestParam("phone")String phone,
                              @RequestParam("address")String address,
                              @RequestParam("id_card")String idCard,
                              Model model) {
        List<Admin> admins = adminRepository.findAll();
        model.addAttribute("admin", adminRepository.getAdminById(idAdmin));
        model.addAttribute("detailAdmin", detailAdminRepository.findDetailAdminById(idAdmin));

        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && !Objects.equals(admin.getId(), idAdmin)) {
                model.addAttribute("userError", "Đã tồn tại tên tài khoản");
                return "/admin/account/editAccount";
            }
            if (username.isBlank() && !Objects.equals(admin.getId(), idAdmin)) {
                model.addAttribute("userError", "Bạn đang để trống tên tài khoản ");
                return "/admin/account/editAccount";
            }
            if (name.isBlank() && !Objects.equals(admin.getId(), idAdmin)) {
                model.addAttribute("nameError", "Bạn đang để trống họ tên ");
                return "/admin/account/editAccount";
            }

            if (phone.length() != 10 && !Objects.equals(admin.getId(), idAdmin)) {
                model.addAttribute("phoneError", "Số điện thoại phải 10 số");
                return "/admin/account/editAccount";
            }
            if (address.isBlank() && !Objects.equals(admin.getId(), idAdmin)) {
                model.addAttribute("addressError", "Bạn đang để trống địa chỉ");
                return "/admin/account/editAccount";
            }
            if (idCard.length() != 12 && !Objects.equals(admin.getId(), idAdmin)) {
                model.addAttribute("idCardError", "CCCD phải 12 số");
                return "/admin/account/editAccount";
            }
        }
        Admin admin = adminRepository.getAdminById(idAdmin);
        admin.setUsername(username);
        admin.setUpdateDate(String.valueOf(LocalDateTime.now()));
        admin.setUsername(username);
        adminRepository.save(admin);

        DetailAdmin detailAdmin = detailAdminRepository.findDetailAdminById(idAdmin);
        detailAdmin.setAddress(address);
        detailAdmin.setUpdateDate(String.valueOf(LocalDateTime.now()));
        detailAdmin.setPhone(phone);
        detailAdmin.setIdCard(idCard);
        detailAdminRepository.save(detailAdmin);
        return "redirect:/admin/account/" + idAdmin;
    }

    // edit password admin
    @GetMapping("/editPassword/{idAdmin}")
    public String editPassword(@PathVariable Integer idAdmin, Model model) {
        model.addAttribute("admin", adminRepository.getAdminById(idAdmin));
        return "/admin/account/editPassword";
    }
    @PostMapping("/editPasswordSave/{idAdmin}")
    public String editPasswordSave(@PathVariable Integer idAdmin,
                                   @RequestParam("current_password")String current_password,
                                   @RequestParam("new_password")String new_password,
                                   @RequestParam("confirm_password")String confirm_password,
                                   Model model){
        Admin admin = adminRepository.getAdminById(idAdmin);
        if (!admin.getPassword().equals(current_password)) {
            model.addAttribute("errorCurrentPassword","Mật khẩu không đúng");
            return "/admin/account/editPassword";
        }
        if (!Objects.equals(new_password, confirm_password)) {
            model.addAttribute("errorNewPass","2 mật khẩu phải giống nhau");
            return "/admin/account/editPassword";
        }
        if (confirm_password.length()<5){
            model.addAttribute("errorConfirmPass","Mật khẩu trên 5 kí tự");
            return "/admin/account/editPassword";
        }
        admin.setPassword(confirm_password);
        admin.setUpdateDate(String.valueOf(LocalDateTime.now()));
        adminRepository.save(admin);
        return "redirect:/admin/account/" + idAdmin;
    }
    // show all user to admin
    @GetMapping("/showAllAdmin")
    public String showUser(HttpSession session, Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        // phân trang cho trang chủ
        int currentPage = page.orElse(1); // số trang
        int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
        Page<Admin> admins = adminService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("admins", admins);

        int totalPages = admins.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("maxPageNumber",pageNumbers.size());
        }
        return "admin/account/showAllAdmin";
    }
}
