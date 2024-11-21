package com.example.TTCN2.controller;

import com.example.TTCN2.domain.Admin;
import com.example.TTCN2.domain.Shipper;
import com.example.TTCN2.repository.DetailAdminRepository;
import com.example.TTCN2.repository.ShipperRepository;
import com.example.TTCN2.service.ShipperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/shipper")
public class ShipperController {
    @Autowired
    ShipperRepository shipperRepository;
    @Autowired
    ShipperService shipperService;
    @Autowired
    DetailAdminRepository detailAdminRepository;
    // show all shipper to admin
    @GetMapping("/admin/showShipper")
    public String showShipper(HttpSession session, Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        // phân trang cho trang chủ
        int currentPage = page.orElse(1); // số trang
        int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
        Page<Shipper> shippers = shipperService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("shippers", shippers);

        int totalPages = shippers.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("maxPageNumber",pageNumbers.size());
        }
        return "admin/shipper/showShipper";
    }
    // block shipper
    @GetMapping("/admin/blockShipper/{idShipper}")
    public String blockShipper(HttpSession session, Model model,
                            @PathVariable("idShipper")Integer idShipper){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        Shipper shipper = shipperRepository.getReferenceById(idShipper);
        if (shipper.getBlock() == 0){
            shipper.setBlock(1);
            shipperService.save(shipper);
            return "redirect:/shipper/admin/showShipper";
        }else {
            shipper.setBlock(0);
            shipperService.save(shipper);
        }
        return "redirect:/shipper/admin/showShipper";
    }
}
