package com.example.TTCN2.controller;

import com.example.TTCN2.domain.*;
import com.example.TTCN2.projection.IOrder;
import com.example.TTCN2.repository.*;
import com.example.TTCN2.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

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
    @Autowired
    ShipperOrderRepository shipperOrderRepository;
    @Autowired
    ReceiverRepository receiverRepository;
    @Autowired
    TreeRepository treeRepository;
    @Autowired
    EvaluateRepository evaluateRepository;


    @PostMapping("/{idCus}")
    public String order(@PathVariable Integer idCus,  HttpSession session,Model model,
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
        // ktra neu thanh toan vnPay
        if(idPay == 2){
            model.addAttribute("transport",transportMethodRepository.getTransportById(idTrans));
            model.addAttribute("payment",paymentMethodRepository.getPaymentById(2));
            model.addAttribute("receiver",receiverRepository.findAllById(idReceiver));
            model.addAttribute("totalMoney",cart.getTotalMoney()+transportMethodRepository.getTransportById(idTrans).getMoney());
            return "/user/order/createOrder";
        }
        // save order
        Order order = new Order();
        order.setTotalQuantity(cart.getTotalQuantity());
        order.setIdPayment(idPay);
        order.setIdTransport(idTrans);
        order.setTotalMoney(cart.getTotalMoney()+transportMethodRepository.getTransportById(idTrans).getMoney());
        order.setIdUser(idCus);
        order.setIdReceiver(idReceiver);
        order.setIdShipper(1);
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
            // reduce quantity tree
            Tree tree = treeRepository.findAllById(cartItem.getIdTree());
            tree.setQuantity(tree.getQuantity()-cartItem.getQuantity());
            treeRepository.save(tree);// save tree
            cartItemService.delete(cartItem);// xoa cartItem
        }
        // xoa cart 
        cartService.delete(cart);
        session.removeAttribute("countCart_user");

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
        List<ShipperOrder> shipperOrders = new ArrayList<>();
        List<IOrder> dateDiffs = new ArrayList<>();
        for (Order order : orders) {
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(order.getId());
            orderDetails.addAll(orderDetailList);
            shipperOrders.add(shipperOrderRepository.findByOrderId(order.getId()));
            dateDiffs.add(orderRepository.getOrderByStatus4(order.getId()));
        }
        model.addAttribute("dateDiffs",dateDiffs);
        model.addAttribute("orderDetail",orderDetails);
        model.addAttribute("shipperOrders",shipperOrders);
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
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(idOrder);
        for (OrderDetail orderDetail : orderDetails) {
            Tree tree = treeRepository.findAllById(orderDetail.getIdTree());
            tree.setQuantity(tree.getQuantity()+orderDetail.getQuantity());
            treeRepository.save(tree);
        }
        return "redirect:/order/{idCus}/{status}";
    }
    // mua lai status -> 1
    @GetMapping("/putOrder/{idCus}/{status}/{idOrder}")
    public String putOrder(@PathVariable Integer idCus, @PathVariable Integer status,
                             @PathVariable Integer idOrder){
        Order order = orderRepository.findByIdOrder(idOrder);
        order.setStatus(1);
        orderRepository.save(order);
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(idOrder);
        for (OrderDetail orderDetail : orderDetails) {
            Tree tree = treeRepository.findAllById(orderDetail.getIdTree());
            tree.setQuantity(tree.getQuantity()-orderDetail.getQuantity());
            treeRepository.save(tree);
        }
        return "redirect:/order/{idCus}/{status}";
    }
    // da nhan hang status -> 4
    @GetMapping("/receiveOrder/{idCus}/{status}/{idOrder}")
    public String receiveOrder(@PathVariable Integer idCus, @PathVariable Integer status,
                             @PathVariable Integer idOrder){
        // chuyen trang thai order
        Order order = orderRepository.findByIdOrder(idOrder);
        order.setReceiverDate(String.valueOf(LocalDateTime.now()));
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
    // khong chap nhan hoan don phia admin
    @GetMapping("/admin/notPermitOrder5/{idOrder}")
    public String notPermitOrder5(Model model,@PathVariable Integer idOrder){
        Order order = orderRepository.findByIdOrder(idOrder);
        if (order.getStatus() == 5) {
            order.setStatus(4);
            orderRepository.save(order);
        }
        return "redirect:/order/admin/showOrder/"+order.getStatus();
    }

    // detail order 'user'
    @GetMapping("/detailOrder/{idOrder}")
    public String detailOrder(@PathVariable Integer idOrder,Model model){
        Order order = orderRepository.findByIdOrder(idOrder);
        model.addAttribute("order",order);
        model.addAttribute("trees",orderDetailRepository.findByOrderId(order.getId()));
        model.addAttribute("receiver",receiverRepository.findAllById(order.getIdReceiver()));
        model.addAttribute("shipperOrder",shipperOrderRepository.findByOrderId(order.getId()));
        List<OrderDetail> orderDetails =  orderDetailRepository.findByOrderId(order.getId());
        model.addAttribute("detailOrder",orderDetails);
        List<Evaluate> evaluates = new ArrayList<>();
        List<Evaluate> evaluates1 = evaluateRepository.findAll();
        Map<Integer, Evaluate> evaluateMap = new HashMap<>();
        for (Evaluate evaluate : evaluates1) {
            evaluateMap.put(evaluate.getIdOrderDetail().getId(), evaluate);
        }
        for (OrderDetail orderDetail : orderDetails) {
            Evaluate evaluate1 = evaluateMap.get(orderDetail.getId());

            if (evaluate1 != null) {
                evaluates.add(evaluate1);
            } else {
                evaluate1 = new Evaluate();
                evaluate1.setIdOrderDetail(orderDetail);
                evaluateRepository.save(evaluate1);
                evaluates.add(evaluate1);
            }
        }
        model.addAttribute("evaluateMap",evaluateMap);
        model.addAttribute("evaluates", evaluates);
        return "user/order/detailOrder";
    }
    // detail order 'admin'
    @GetMapping("/admin/detailOrder/{idOrder}")
    public String detailOrderAdmin(@PathVariable Integer idOrder,Model model,HttpSession session){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));

        Order order = orderRepository.findByIdOrder(idOrder);
        model.addAttribute("order",order);
        model.addAttribute("trees",orderDetailRepository.findByOrderId(order.getId()));
        model.addAttribute("receiver",receiverRepository.findAllById(order.getIdReceiver()));
        model.addAttribute("shipperOrder",shipperOrderRepository.findByOrderId(order.getId()));
        return "admin/order/detailOrder";
    }
}
