package com.example.TTCN2.controller;

import com.example.TTCN2.domain.Admin;
import com.example.TTCN2.domain.PaymentMethod;
import com.example.TTCN2.domain.TransportMethod;
import com.example.TTCN2.repository.DetailAdminRepository;
import com.example.TTCN2.repository.TransportMethodRepository;
import com.example.TTCN2.service.TransportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/transport")
public class TransportController {
    @Autowired
    TransportMethodRepository transportMethodRepository;
    @Autowired
    TransportService transportService;
    @Autowired
    DetailAdminRepository detailAdminRepository;
    // showTransport
    @GetMapping("/admin/showTransport")
    public String showTransport(Model model, HttpSession session,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        // phân trang cho trang chủ
        int currentPage = page.orElse(1); // số trang
        int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
        Page<TransportMethod> transportMethods = transportService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("transports", transportMethods);

        int totalPages = transportMethods.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("maxPageNumber",pageNumbers.size());
        }
        return "admin/transport/showTransport";
    }

    // active payment
    @GetMapping("/admin/activeTransport/{idTransport}")
    public String showActiveTransport(@PathVariable("idTransport") Integer idTransport) {
        TransportMethod transportMethod = transportMethodRepository.getTransportById(idTransport);
        if (transportMethod.getIsActive() == 0){
            transportMethod.setIsActive(1);
            transportMethodRepository.save(transportMethod);
        }else {
            transportMethod.setIsActive(0);
            transportMethodRepository.save(transportMethod);
        }
        return "redirect:/transport/admin/showTransport";
    }
    // new transport
    @GetMapping("/admin/newTransport")
    public String newTransport(Model model,HttpSession session,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        if (admin.getPower() == 3){
            model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
            // phân trang cho trang payment in admin
            int currentPage = page.orElse(1); // số trang
            int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
            Page<TransportMethod> transportMethods = transportService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
            model.addAttribute("transportMethods", transportMethods);

            int totalPages = transportMethods.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
                model.addAttribute("maxPageNumber",pageNumbers.size());
            }
            model.addAttribute("message","Bạn không đủ quyền hạn để thêm");
            return "admin/transport/showTransport";
        }
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        return "admin/transport/newTransport";
    }
    @PostMapping("/admin/newSaveTransport")
    public String newSavePayment(@RequestParam("name") String name,
                                 @RequestParam("notes")String notes,
                                 @RequestParam("price")String priceStr,
                                 HttpSession session,Model model){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        TransportMethod transportMethod = new TransportMethod();
        transportMethod.setName(name);
        transportMethod.setNotes(notes);
        Double price = Double.valueOf(priceStr.replace(".",""));
        transportMethod.setMoney(price);
        transportMethod.setIsActive(0);
        transportMethod.setCreateDate(String.valueOf(LocalDateTime.now()));
        transportMethodRepository.save(transportMethod);
        return "redirect:/transport/admin/showTransport";
    }
    // remove payment
    @GetMapping("/admin/removeTransport/{idTransport}")
    public String removeTransport(@PathVariable("idTransport") Integer idTransport,
                                HttpSession session,Model model){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        TransportMethod transportMethod = transportMethodRepository.getTransportById(idTransport);
        transportService.delete(transportMethod);
        return "redirect:/transport/admin/showTransport";
    }
    // edit category
    @GetMapping("/admin/editTransport/{idTransport}")
    public String editPayment(Model model,HttpSession session,
                              @PathVariable("idTransport") Integer idTransport){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        model.addAttribute("transport",transportMethodRepository.getTransportById(idTransport));

        return "admin/transport/editTransport";
    }
    @PostMapping ("/admin/saveEditTransport/{idTransport}")
    public String saveEditTransport(Model model,HttpSession session,
                                  @PathVariable("idTransport") Integer idTransport,
                                  @RequestParam("name") String name,
                                  @RequestParam("notes")String notes,
                                  @RequestParam("price")String priceStr,
                                  @RequestParam("is_active")Integer is_active){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        TransportMethod transportMethod = transportMethodRepository.getTransportById(idTransport);
        transportMethod.setName(name);
        transportMethod.setNotes(notes);
        Double price = Double.valueOf(priceStr.replace(".",""));
        transportMethod.setMoney(price);
        transportMethod.setIsActive(is_active);
        transportMethod.setUpdateDate(String.valueOf(LocalDateTime.now()));
        transportMethodRepository.save(transportMethod);
        return "redirect:/transport/admin/showTransport";
    }
}
