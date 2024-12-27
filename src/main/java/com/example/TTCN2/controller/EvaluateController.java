package com.example.TTCN2.controller;

import com.example.TTCN2.domain.*;
import com.example.TTCN2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/evaluate")
public class EvaluateController {
    @Autowired
    EvaluateRepository evaluateRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TreeRepository treeRepository;

    @PostMapping("/saveEvaluate/{idCus}/{idOrderDetail}")
    public String saveEvaluate(@PathVariable("idOrderDetail") Integer idOrderDetail,
                               @PathVariable("idCus") Integer idCus,
//                               @PathVariable("idOrder")Integer idOrder,
                               @RequestParam("content")String content) {
        User user = userRepository.getReferenceById(idCus);
//        Order order = orderRepository.getReferenceById(idOrder);
        OrderDetail orderDetail = orderDetailRepository.getReferenceById(idOrderDetail);
        Evaluate evaluate = evaluateRepository.getReferenceById(idOrderDetail);
        evaluate.setEvaluateDate(String.valueOf(LocalDateTime.now()));
        evaluate.setContent(content);
        evaluate.setUsernameCus(user.getName());
        evaluate.setIdOrderDetail(orderDetail);
        evaluateRepository.save(evaluate);
        return "redirect:/order/detailOrder/"+orderDetail.getIdOrder();
    }

    // is_active off
    @GetMapping("/admin/isActiveOff/{idTree}/{idEva}")
    public String isActiveOff(@PathVariable Integer idTree,
                              @PathVariable Integer idEva) {
        Evaluate evaluate = evaluateRepository.getReferenceById(idEva);
        evaluate.setIsActive(0);
        evaluateRepository.save(evaluate);
        return "redirect:/tree/admin/detailTree/{idTree}";
    }
    // is_active off
    @GetMapping("/admin/isActiveOn/{idTree}/{idEva}")
    public String isActiveOn(@PathVariable Integer idTree,
                              @PathVariable Integer idEva) {
        Evaluate evaluate = evaluateRepository.getReferenceById(idEva);
        evaluate.setIsActive(1);
        evaluateRepository.save(evaluate);
        return "redirect:/tree/admin/detailTree/{idTree}";
    }
}
