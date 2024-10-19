package com.example.TTCN2.controller;

import com.example.TTCN2.domain.Cart;
import com.example.TTCN2.domain.Receiver;
import com.example.TTCN2.domain.User;
import com.example.TTCN2.repository.CartItemRepository;
import com.example.TTCN2.repository.CartRepository;
import com.example.TTCN2.repository.ReceiverRepository;
import com.example.TTCN2.repository.UserRepository;
import com.example.TTCN2.service.ReceiverService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/receiver")
public class ReceiverController {
    @Autowired
    ReceiverRepository receiverRepository;
    @Autowired
    ReceiverService receiverService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartRepository cartRepository;

    // show người nhận
    @GetMapping("/receiver/{idCus}")
    public String showReceiver(Model model, @PathVariable("idCus")Integer idCus, HttpSession session){
        User customer = (User) session.getAttribute("saveCus");
        model.addAttribute("customer",customer);
        model.addAttribute("receiver",receiverRepository.getAllByReceiverId(idCus));
        model.addAttribute("count", cartItemRepository.getCartItemCount(cartRepository.findByIdUser(idCus).getId()));
        session.removeAttribute("newReceiverOrder");
        return "user/receiver/showReceiver";
    }
    // đường dẫn tới table sửa người nhận
    @GetMapping("/repairReceiver/{idRece}")
    public String repairReceiver(@PathVariable("idRece") Integer idRece, Model model, HttpSession session){
        session.setAttribute("receiverById",idRece);
        model.addAttribute("receiverById",receiverRepository.findAllById(idRece));
        return "user/receiver/repairReceiver";
    }

    // lưu lại khi sửa người nhận
    @PostMapping("/repairReceiverId/{idCus}/{idRece}")
    public String repairReceiverId(@ModelAttribute("receiver") Receiver receiver, // lấy receiver ở form khi nhập lên
                                   @PathVariable("idRece")Integer idRece
            , @PathVariable("idCus") Integer idCus){
        Receiver receiver1 = receiverRepository.findAllById(idRece); // lấy receiver theo id rồi sửa
        receiver1.setName(receiver.getName());
        receiver1.setPhone(receiver.getPhone());
        receiver1.setAddress(receiver.getAddress());
        receiverService.save(receiver1);
        return "redirect:/receiver/receiver/{idCus}";
    }
    // xóa địa chỉ
    @GetMapping("/remove/{idCus}/{idRece}")
    public String removeReceiver(@PathVariable("idCus") Integer idCus
            , @PathVariable("idRece") Integer idRece){
        Receiver receiver = receiverRepository.findAllById(idRece);
        receiver.setIsDelete(0);
        receiverService.save(receiver);
        return "redirect:/receiver/receiver/{idCus}";
    }
    // thêm địa chỉ mới
    @GetMapping("/newReceiver") // thêm mới khi đang ở nơi kiểm soát tài khoản  của ng dùng
    public String newRece(Model model){
        model.addAttribute("newRece",new Receiver());
        return "/user/receiver/newReceiver";
    }
    @PostMapping("/newReceiver/{idCus}") // lưu thêm mới ở chỗ kiểm soát tài khoản người dùng
    public String newReceiver(@ModelAttribute("receiver")Receiver receiver,@PathVariable("idCus")Integer idCus){
        receiver.setIsDelete(1);
        User customer = userRepository.getCustomerById(idCus);
        receiver.setIdUser(customer.getId());
        receiverService.save(receiver);
        return "redirect:/receiver/receiver/{idCus}";
    }
}
