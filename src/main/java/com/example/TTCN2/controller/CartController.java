package com.example.TTCN2.controller;

import com.example.TTCN2.domain.*;
import com.example.TTCN2.repository.*;
import com.example.TTCN2.service.CartItemService;
import com.example.TTCN2.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    TreesImageRepository treesImageRepository;
    @Autowired
    TreeRepository treeRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ReceiverRepository receiverRepository;
    @Autowired
    TransportMethodRepository transportMethodRepository;
    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    // enter cart
    @GetMapping("/{userID}")
    public String cart(@PathVariable("userID") Integer userID, Model model, HttpSession session) {
        User customer = (User) session.getAttribute("saveCus");
        model.addAttribute("receiver",receiverRepository.getAllByReceiverId(customer.getId()));
        model.addAttribute("transport",transportMethodRepository.getAllById());
        model.addAttribute("payment",paymentMethodRepository.getAllById());
        Cart cart = cartRepository.findByIdUser(userID);
        if (cart == null) {
            model.addAttribute("message","Không có sản phẩm nào trong giỏ hàng bạn");
        }
        else {
            List<CartItem> cartItems =cartItemRepository.getAllCartItem_idCart(cart.getId());
            if (cartItems.isEmpty()){
                model.addAttribute("message","Không có sản phẩm nào trong giỏ hàng bạn");
                return "user/cart/cart";
            }else {
                model.addAttribute("cartItem",cartItems);
                model.addAttribute("cart",cart);
                model.addAttribute("category",categoryRepository.getAllCategory());
            }
        }

        return "user/cart/cart";
    }
    // them cart
    @GetMapping("/add/{idCus}/{idPro}")
    public String getAddCart(@PathVariable("idCus")Integer idCus, @PathVariable("idPro") Integer idPro, HttpSession session,Model model){
        User customer= (User) session.getAttribute("saveCus");
        Tree product = treeRepository.findAllById(idPro);
        TreesImage treesImage = treesImageRepository.findByMainTreeId_Image(idPro);
        boolean checkCart = false; // biến kiểm tra idPro trong giỏ
        if (product != null){
            Cart cart = cartRepository.findByIdUser(idCus);
            if(cart != null ){
                List<Cart> carts = cartRepository.getAllCart();
                List<CartItem> cartItems = cartItemRepository.getAllCartItem_idCart(cart.getId());
                for (Cart cart1: carts){
                    if(cart1.getIdUser().equals(idCus)){
                        for (CartItem cartItem: cartItems) {
                            if (product.getId().equals(cartItem.getIdTree())){ // nếu tồn tại idPro của cart == id của tree
                                cartItem.setQuantity(cartItem.getQuantity()+1);
                                cartItemService.save(cartItem);
                                // tang money , quantity in cart
                                cart1.setTotalMoney(cart1.getTotalMoney()+cartItem.getMoney());
                                cart1.setTotalQuantity(cart1.getTotalQuantity()+1);
                                cartService.save(cart1);

                                checkCart = false;
                                break; // thoát vòng lặp nếu tồn tại điều kiện
                            }else  {
                                checkCart=true;
                            }
                        }
                        if (checkCart || cartItems.size() == 0){ // khi duyệt hết vòng lặp nếu checkCart == true thì tạo 1 cartItem mới
                            CartItem cartItem = new CartItem();
                            cartItem.setIdCart(cart1.getId());
                            cartItem.setNameTree(product.getName());
                            cartItem.setMoney(product.getMoney());
                            cartItem.setQuantity(1);
                            cartItem.setImage(treesImage.getUrl());
                            cartItem.setIdTree(product.getId());
                            cartItemRepository.save(cartItem);
                            // tang money , quantity in cart
                            cart1.setTotalMoney(cart1.getTotalMoney()+cartItem.getMoney());
                            cart1.setTotalQuantity(cart1.getTotalQuantity()+cartItem.getQuantity());
                            cartService.save(cart1);
                        }
                    }
                }
            }
            if (cart == null){ // cart cua user == null tao cart moi
                Cart cart2 = new Cart();
                cart2.setIdUser(customer.getId());
                cart2.setTotalMoney(product.getMoney());
                cart2.setTotalQuantity(1);
                cartService.save(cart2);

                CartItem cartItem = new CartItem();
                cartItem.setIdCart(cart2.getId());
                cartItem.setNameTree(product.getName());
                cartItem.setMoney(product.getMoney());
                cartItem.setQuantity(1);
                cartItem.setImage(treesImage.getUrl());
                cartItem.setIdTree(product.getId());
                cartItemRepository.save(cartItem);
                model.addAttribute("cartItem",cartItem);
                model.addAttribute("cart",cart2);
            }
        }

        return "redirect:/cart/{idCus}";
    }

    // nut sua quantity quantity
    @PostMapping("/{idCus}/{idCartItem}")
    public String getCartItem(@PathVariable("idCartItem")Integer idCartItem,
                              @PathVariable("idCus")Integer idCus,
                                @RequestParam("quantity") Integer quantity
                                , HttpSession session) {
        User customer= (User) session.getAttribute("saveCus");
        CartItem cartItem = cartItemRepository.getCartItemById(idCartItem);
        if (quantity <= 0 ){
            cartItem.setQuantity(1);
            cartItemService.save(cartItem);
        }
        else {
            cartItem.setQuantity(quantity);
            cartItemService.save(cartItem);
        }
        List<CartItem> cartItems = cartItemRepository.getAllCartItem_idCart(cartItem.getIdCart());
        Cart cart = cartRepository.findByIdUser(customer.getId());
        double totalMoney = 0;
        int totalQuantity = 0;
        for (CartItem cartItem1: cartItems){
            totalMoney += cartItem1.getMoney()*cartItem1.getQuantity();
            totalQuantity += cartItem1.getQuantity();
        }
        cart.setTotalQuantity(totalQuantity);
        cart.setTotalMoney(totalMoney);
        cartService.save(cart);
        return "redirect:/cart/{idCus}";
    }
    // remove cart
    @GetMapping("/removeCart/{idCus}/{idCartItem}")
    public String removeCart(@PathVariable("idCus")Integer idCus,
                             @PathVariable("idCartItem") Integer idCartItem,
                             HttpSession session){
        User customer= (User) session.getAttribute("saveCus");
        CartItem cartItem = cartItemRepository.getCartItemById(idCartItem);
        List<CartItem> cartItems = cartItemRepository.getAllCartItem_idCart(cartItem.getIdCart());
        if (!cartItems.isEmpty()){
            Cart cart = cartRepository.findByIdUser(customer.getId());
            cart.setTotalQuantity(cartItem.getQuantity() - 1);
            cart.setTotalMoney(cart.getTotalMoney() - cartItem.getMoney()*cartItem.getQuantity());
            cartService.save(cart);
        }
        cartItemService.delete(cartItem);
        return "redirect:/cart/{idCus}";
    }

}
