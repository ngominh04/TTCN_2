package com.example.TTCN2.controller;

import com.example.TTCN2.domain.*;
import com.example.TTCN2.repository.*;
import com.example.TTCN2.service.CartItemService;
import com.example.TTCN2.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
            model.addAttribute("cartItem",cartItemRepository.getAllCartItem_idCart(cart.getId()));
            model.addAttribute("cart",cart);
            model.addAttribute("category",categoryRepository.getAllCategory());
            model.addAttribute("count", cartItemRepository.getCartItemCount(cart.getId()));
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
}
