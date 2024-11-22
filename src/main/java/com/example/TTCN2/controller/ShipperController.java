package com.example.TTCN2.controller;

import com.example.TTCN2.domain.Admin;
import com.example.TTCN2.domain.Order;
import com.example.TTCN2.domain.Shipper;
import com.example.TTCN2.domain.ShipperOrder;
import com.example.TTCN2.projection.IOrder;
import com.example.TTCN2.repository.*;
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

import java.time.LocalDateTime;
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
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ReceiverRepository receiverRepository;
    @Autowired
    ShipperOrderRepository shipperOrderRepository;

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

    // show orderShipper
    @GetMapping("/shipperOrder/{orderStatus}")
    public String showOrderStatus_Shipper(@PathVariable Integer orderStatus, HttpSession session, Model model){
        Shipper shipper = (Shipper) session.getAttribute("saveShipper");
        List<IOrder> orders = orderRepository.getAllOrderByStatusJoinReceiver(orderStatus,shipper.getId());
        model.addAttribute("orders", orders);
        if (orderStatus == 4){
            return "shipper/shipperOrder/showShipperOrder4";
        }
        return "shipper/shipperOrder/showShipperOrder3";
    }

    //detail order
    @GetMapping("/shipperOrder/detailOrder/{idOrder}")
    public String showOrderStatus(@PathVariable Integer idOrder, HttpSession session, Model model){
        Order order = orderRepository.getReferenceById(idOrder);
        model.addAttribute("order",order);
        model.addAttribute("trees",orderDetailRepository.findByOrderId(order.getId()));
        model.addAttribute("receiver",receiverRepository.findAllById(order.getIdReceiver()));
        return "shipper/shipperOrder/detailOrder";
    }

    // nhan don de giao phia shipper
    @GetMapping("/shipperOrder/confirmOrder/{idOrder}")
    public String confirmOrder(@PathVariable Integer idOrder, HttpSession session, Model model){
        Shipper shipper = (Shipper) session.getAttribute("saveShipper");
        // repair table order
        Order order = orderRepository.getReferenceById(idOrder);
        order.setIdShipper(shipper.getId());
        order.setStatus(3);
        orderRepository.save(order);
        // save table shipper_order
        ShipperOrder shipperOrder = new ShipperOrder();
        shipperOrder.setIdShipper(shipper.getId());
        shipperOrder.setIdOrder(order.getId());
        shipperOrder.setReceiverDate(String.valueOf(LocalDateTime.now()));
        shipperOrder.setStatus(1);
        // Trạng thái ở shippeOrder : 1 đang giao , 2 đã giao tới khách
        shipperOrderRepository.save(shipperOrder);
        return "redirect:/shipper/"+shipper.getId();
    }
}
