package com.example.TTCN2.controller;

import com.example.TTCN2.domain.Admin;
import com.example.TTCN2.domain.Category;
import com.example.TTCN2.domain.PaymentMethod;
import com.example.TTCN2.domain.Shipper;
import com.example.TTCN2.repository.DetailAdminRepository;
import com.example.TTCN2.repository.PaymentMethodRepository;
import com.example.TTCN2.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    DetailAdminRepository detailAdminRepository;
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    PaymentService paymentService;

    // showPayment
    @GetMapping("/admin/showPayment")
    public String showPayment(Model model, HttpSession session,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        // phân trang cho trang chủ
        int currentPage = page.orElse(1); // số trang
        int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
        Page<PaymentMethod> paymentMethods = paymentService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("paymentMethods", paymentMethods);

        int totalPages = paymentMethods.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("maxPageNumber",pageNumbers.size());
        }
        return "admin/payment/showPayment";
    }

    // active payment
    @GetMapping("/admin/activePayment/{idPayment}")
    public String showActivePayment(@PathVariable("idPayment") Integer idPayment) {
        PaymentMethod paymentMethod = paymentMethodRepository.getPaymentById(idPayment);
        if (paymentMethod.getIsActive() == 0){
            paymentMethod.setIsActive(1);
            paymentMethodRepository.save(paymentMethod);
        }else {
            paymentMethod.setIsActive(0);
            paymentMethodRepository.save(paymentMethod);
        }
        return "redirect:/payment/admin/showPayment";
    }
    // new payment
    @GetMapping("/admin/newPayment")
    public String newPayment(Model model,HttpSession session,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        if (admin.getPower() == 3){
            model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
            // phân trang cho trang payment in admin
            int currentPage = page.orElse(1); // số trang
            int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
            Page<PaymentMethod> paymentMethods = paymentService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
            model.addAttribute("paymentMethods", paymentMethods);

            int totalPages = paymentMethods.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
                model.addAttribute("maxPageNumber",pageNumbers.size());
            }
            model.addAttribute("message","Bạn không đủ quyền hạn để thêm");
            return "admin/payment/showPayment";
        }
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        return "admin/payment/newPayment";
    }
    @PostMapping("/admin/newSavePayment")
    public String newSavePayment(@RequestParam("name") String name,
                                  @RequestParam("notes")String notes,
                                  HttpSession session,Model model){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName(name);
        paymentMethod.setNotes(notes);
        paymentMethod.setCreateDate(String.valueOf(LocalDateTime.now()));
        paymentMethod.setIsActive(0);
        paymentMethodRepository.save(paymentMethod);
        return "redirect:/payment/admin/showPayment";
    }
    // remove payment
    @GetMapping("/admin/removePayment/{idPayment}")
    public String removePayment(@PathVariable("idPayment") Integer idPayment,
                                 HttpSession session,Model model){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        PaymentMethod paymentMethod = paymentMethodRepository.getPaymentById(idPayment);
        paymentService.delete(paymentMethod);
        return "redirect:/payment/admin/showPayment";
    }
    // edit category
    @GetMapping("/admin/editPayment/{idPayment}")
    public String editPayment(Model model,HttpSession session,
                           @PathVariable("idPayment") Integer idPayment){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        model.addAttribute("payment",paymentMethodRepository.getPaymentById(idPayment));

        return "admin/payment/editPayment";
    }
    @PostMapping ("/admin/saveEditPayment/{idPayment}")
    public String saveEditPayment(Model model,HttpSession session,
                               @PathVariable("idPayment") Integer idPayment,
                               @RequestParam("name") String name,
                               @RequestParam("notes")String notes,
                               @RequestParam("is_active")Integer is_active){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        PaymentMethod paymentMethod = paymentMethodRepository.getPaymentById(idPayment);
        paymentMethod.setName(name);
        paymentMethod.setNotes(notes);
        paymentMethod.setIsActive(is_active);
        paymentMethod.setCreateDate(String.valueOf(LocalDateTime.now()));
        paymentMethodRepository.save(paymentMethod);
        return "redirect:/payment/admin/showPayment";
    }
}
