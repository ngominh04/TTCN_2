package com.example.TTCN2.controller;

import com.example.TTCN2.domain.*;
import com.example.TTCN2.repository.*;
import com.example.TTCN2.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderPaymentRepository orderPaymentRepository;
    @Autowired
    OrderTransportRepository orderTransportRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    OrderPaymentService orderPaymentService;
    @Autowired
    OrderTransportService orderTransportService;
    @Autowired
    TransportMethodRepository transportMethodRepository;
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    DetailAdminRepository detailAdminRepository;


    @PostMapping("/{idCus}")
    public String order(@PathVariable Integer idCus,  HttpSession session,
                        @RequestParam("idTrans")Integer idTrans,
                        @RequestParam("idPay")Integer idPay,
                        @RequestParam("idReceiver")Integer idReceiver
                        ) {
        User user = (User) session.getAttribute("saveCus");
        Cart cart = cartRepository.findByIdUser(user.getId());
        List<CartItem> cartItems1 = cartItemRepository.getAllCartItem_idCart(cart.getId());
        if (cartItems1.isEmpty()){

            return "redirect:/cart/{idCus}";
        }

        // save order
        Order order = new Order();
        order.setTotalQuantity(cart.getTotalQuantity());
        order.setIdPayment(idPay);
        order.setIdTransport(idTrans);
        order.setTotalMoney(cart.getTotalMoney()+transportMethodRepository.getTransportById(idTrans).getMoney());
        order.setIdUser(idCus);
        order.setIdReceiver(idReceiver);
        order.setIdShipper(0);
        order.setCreateDate(String.valueOf(LocalDateTime.now()));
        // status: 1 cho xac nhan ; 2 : cho shipper den lay ; 3 dang giao ; 4 da giao ;
        // 5 cho hoan don ; 6 dang giao lai admin; 7: da giao cho admin
        //;; 0 huy don ;
        order.setStatus(1);
        // save order
        orderRepository.save(order);


        // orderPayment
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setIdOrder(order.getId());
        orderPayment.setNamePayment(paymentMethodRepository.getPaymentById(idPay).getName());
        orderPayment.setMoney((double) 0);
        orderPaymentRepository.save(orderPayment);

        // orderTransport
        OrderTransport orderTransport = new OrderTransport();
        orderTransport.setIdOrder(order.getId());
        orderTransport.setNameTransport(transportMethodRepository.getTransportById(idTrans).getName());
        orderTransport.setMoney(transportMethodRepository.getTransportById(idTrans).getMoney());
        orderTransportRepository.save(orderTransport);

        // save orderDetail
        List<CartItem> cartItems = cartItemRepository.getAllCartItem_idCart(cart.getId());
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail= new OrderDetail();
            orderDetail.setIdOrder(order.getId());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setMoney(cartItem.getMoney());
            orderDetail.setNameTree(cartItem.getNameTree());
            orderDetailRepository.save(orderDetail);
            cartItemService.delete(cartItem);// xoa cartItem
        }
        // xoa cart 
        cartService.delete(cart);

        return "redirect:/";
    }

    // click xem don hang
    // status: 1 cho xac nhan ; 2 : cho shipper den lay ; 3 dang giao ; 4 da giao ;
    // 5 cho hoan don ; 6 dang giao lai admin; 7: da giao cho admin
    //;; 0 huy don ;
    @GetMapping("/{idCus}/{status}")
    public String orderStatus(@PathVariable Integer idCus, @PathVariable Integer status,
                              HttpSession session,Model model) {
        User user = (User) session.getAttribute("saveCus");
        List<Order> orders = orderRepository.findByCusAndStatus(user.getId(),status);
        model.addAttribute("orders",orders);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(order.getId());
            orderDetails.addAll(orderDetailList);
        }
        model.addAttribute("orderDetail",orderDetails);
        model.addAttribute("status",status);

        return "user/order/order";
    }
    // huy don status -> 0
    @GetMapping("/closeOrder/{idCus}/{status}/{idOrder}")
    public String closeOrder(@PathVariable Integer idCus, @PathVariable Integer status,
                             @PathVariable Integer idOrder){
        Order order = orderRepository.findByIdOrder(idOrder);
        order.setStatus(0);
        orderRepository.save(order);
        return "redirect:/order/{idCus}/{status}";
    }
    // mua lai status -> 1
    @GetMapping("/putOrder/{idCus}/{status}/{idOrder}")
    public String putOrder(@PathVariable Integer idCus, @PathVariable Integer status,
                             @PathVariable Integer idOrder){
        Order order = orderRepository.findByIdOrder(idOrder);
        order.setStatus(1);
        orderRepository.save(order);
        return "redirect:/order/{idCus}/{status}";
    }
    // da nhan hang status -> 4
    @GetMapping("/receiveOrder/{idCus}/{status}/{idOrder}")
    public String receiveOrder(@PathVariable Integer idCus, @PathVariable Integer status,
                             @PathVariable Integer idOrder){
        Order order = orderRepository.findByIdOrder(idOrder);
        order.setStatus(4);
        orderRepository.save(order);
        return "redirect:/order/"+idCus+"/"+status;
    }
    // hoàn don status -> 5
    @GetMapping("/refundOrder/{idCus}/{status}/{idOrder}")
    public String refundOrder(@PathVariable Integer idCus, @PathVariable Integer status,
                               @PathVariable Integer idOrder){
        Order order = orderRepository.findByIdOrder(idOrder);
        order.setStatus(5);
        orderRepository.save(order);
        return "redirect:/order/"+idCus+"/"+status;
    }

    ////////////////// admin ///////////////////
    // show order
    @GetMapping("/admin/showOrder/{status}")
    public String showOrder(Model model,HttpSession session,
                            @PathVariable Integer status){

        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        List<Order> orders = orderRepository.getAllOrderByStatus(status);
        model.addAttribute("orders",orders);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(order.getId());
            orderDetails.addAll(orderDetailList);
        }
        model.addAttribute("countByOrder_status0",orderRepository.countAllByStatus(0));
        model.addAttribute("countByOrder_status1",orderRepository.countAllByStatus(1));
        model.addAttribute("countByOrder_status2",orderRepository.countAllByStatus(2));
        model.addAttribute("countByOrder_status3",orderRepository.countAllByStatus(3));
        model.addAttribute("countByOrder_status4",orderRepository.countAllByStatus(4));
        model.addAttribute("countByOrder_status5",orderRepository.countAllByStatus(5));
        model.addAttribute("countByOrder_status6",orderRepository.countAllByStatus(6));
        model.addAttribute("countByOrder_status7",orderRepository.countAllByStatus(7));
        model.addAttribute("orderDetail",orderDetails);
        model.addAttribute("status",status);
        return "admin/order/showOrder";
    }
    // xac nhan don 1->2; chap nhan hoan don 5->2 (2: cho shipper den lay)
    @GetMapping("/admin/orderStatus/{idOrder}")
    public String orderStatus(@PathVariable Integer idOrder,Model model){
        Order order = orderRepository.findByIdOrder(idOrder);
        if (order.getStatus() == 1) {
            order.setStatus(2);
            orderRepository.save(order);
        }if (order.getStatus() == 5) {
            order.setStatus(2);
            orderRepository.save(order);
        }
        return "redirect:/order/admin/showOrder/"+order.getStatus();
    }
}
