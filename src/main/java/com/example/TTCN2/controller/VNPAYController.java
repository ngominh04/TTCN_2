package com.example.TTCN2.controller;

import com.example.TTCN2.domain.*;
import com.example.TTCN2.repository.*;
import com.example.TTCN2.service.CartItemService;
import com.example.TTCN2.service.CartService;
import com.example.TTCN2.service.TreesService;
import com.example.TTCN2.service.VNPService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
//@RequestMapping("/vnPay")
public class VNPAYController {
    @Autowired
    VNPService vnPayService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    TransportMethodRepository transportMethodRepository;
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderPaymentRepository orderPaymentRepository;
    @Autowired
    OrderTransportRepository orderTransportRepository;
    @Autowired
    TreeRepository treeRepository;
    @Autowired
    OrderRepository orderRepository;


//    @GetMapping({"", "/"})
//    public String home(){
//        return "/user/order/createOrder";
//    }

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("totalMoney") Double orderTotalD,
                              @RequestParam("idTrans")Integer idTrans,
                              @RequestParam("idPay")Integer idPay,
                              @RequestParam("idReceiver")Integer idReceiver,
                              HttpServletRequest request, HttpSession session
                              ){
        User user = (User) session.getAttribute("saveCus");
        Cart cart = cartRepository.findByIdUser(user.getId());
        List<CartItem> cartItems1 = cartItemRepository.getAllCartItem_idCart(cart.getId());
        // ep kieu double
        String orderTotalStr = String.valueOf(orderTotalD);
        int orderTotal = Integer.parseInt(orderTotalStr.replace(".0",""));
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        // save order
        Order order = new Order();
        order.setTotalQuantity(cart.getTotalQuantity());
        order.setIdPayment(idPay);
        order.setIdTransport(idTrans);
        order.setTotalMoney(cart.getTotalMoney()+transportMethodRepository.getTransportById(idTrans).getMoney());
        order.setIdUser(user.getId());
        order.setIdReceiver(idReceiver);
        order.setIdShipper(1);
        order.setCreateDate(String.valueOf(LocalDateTime.now()));
        // status: 1 cho xac nhan ; 2 : cho shipper den lay ; 3 dang giao ; 4 da giao ;
        // 5 cho hoan don ; 6 dang giao lai admin; 7: da giao cho admin
        //;; 0 huy don ;
        order.setStatus(2);
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
            // reduce quantity tree
            Tree tree = treeRepository.findAllById(cartItem.getIdTree());
            tree.setQuantity(tree.getQuantity()-cartItem.getQuantity());
            treeRepository.save(tree);// save tree
            cartItemService.delete(cartItem);// xoa cartItem
        }
        // xoa cart
        cartService.delete(cart);
        session.removeAttribute("countCart_user");
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, String.valueOf(order.getId()), baseUrl);
        return "redirect:" + vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

//        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
        return paymentStatus == 1 ? "/user/order/orderSuccess" : "/user/order/orderFail";
    }
}
