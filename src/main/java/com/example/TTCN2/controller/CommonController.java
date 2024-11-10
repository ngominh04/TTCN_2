package com.example.TTCN2.controller;


import com.example.TTCN2.domain.*;
import com.example.TTCN2.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.TTCN2.service.TreeImageService;
import com.example.TTCN2.service.TreesService;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("")
public class CommonController {
    @Autowired
    TreeRepository treeRepository;
    @Autowired
    TreesService treesService;
    @Autowired
    TreesImageRepository treesImageRepository;
    @Autowired
    TreeImageService treeImageService;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DetailAdminRepository detailAdminRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShipperRepository shipperRepository;
    @Autowired
    OrderRepository orderRepository;

    // khởi đầu khi vào đường dẫn trang usser
    @GetMapping("/")
    public String index(Model model, HttpSession session,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
//        List<Tree> trees = treeRepository.findAllTree();
//        model.addAttribute("trees",trees);

        // phân trang cho trang chủ
        int currentPage = page.orElse(1); // số trang
        int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
        // Tạo status cho web :1 trang chủ, 2 trang xem chi tiết,3 trang xem category
        model.addAttribute("statusWeb",1);
        Page<Tree> productPage = treesService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("trees", productPage);
        // add image theo Tree
        List<TreesImage>images=new ArrayList<>();
        for (Tree tree : productPage) {
            TreesImage treesImage = treesImageRepository.findByMainTreeId_Image(tree.getId());
            images.add(treesImage);
        }
        model.addAttribute("tree_image",images);
        // end add img
        model.addAttribute("category",categoryRepository.getAllCategory());
        User user = (User) session.getAttribute("saveCus");
        if (user != null) {
            Cart cart = cartRepository.findByIdUser(user.getId());
            model.addAttribute("cart", cart);
            if (cart != null) {
                model.addAttribute("count", cartItemRepository.getCartItemCount(cart.getId()));
            }
        }
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("maxPageNumber",pageNumbers.size());
        }
        return "user/index";
    }

    // khi vao admin
    @GetMapping("/admin/{idAdmin}")
    public String admin(Model model, HttpSession session,
                        @PathVariable("idAdmin") Integer idAdmin) {
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(idAdmin));
        session.setAttribute("countCate",categoryRepository.countAllById());
        session.setAttribute("countTree",treeRepository.countAllById());
        session.setAttribute("countUser",userRepository.countAllById());
        session.setAttribute("countOrder",orderRepository.countAllById());
        session.setAttribute("countShipper",shipperRepository.countAllById());
        model.addAttribute("order_limit5",orderRepository.findOrderByReceiverDate());
        return "admin/index";
    }
}
