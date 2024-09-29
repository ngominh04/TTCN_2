package com.example.TTCN2.controller;


import com.example.TTCN2.repository.TreeRepository;
import com.example.TTCN2.domain.Tree;
import com.example.TTCN2.domain.TreesImage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.TTCN2.repository.TreesImageRepository;
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
        Page<Tree> productPage = treesService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("trees", productPage);

        // Tạo status cho web :1 trang chủ, 2 trang xem chi tiết
        model.addAttribute("statusWeb",1);

        // add image theo Tree
        List<TreesImage>images=new ArrayList<>();
        for (Tree tree : productPage) {
            TreesImage treesImage = treesImageRepository.findByMainTreeId_Image(tree.getId());
            images.add(treesImage);
        }
        model.addAttribute("tree_image",images);
        // end add img
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


    @GetMapping("/moreData") // thêm dữ liệu bảng trees
    public String moreData() {
        for (int i = 0; i < 5; i++) {
            Tree tree = new Tree();
            tree.setCreateDate(String.valueOf(LocalDateTime.now()));
            tree.setUpdateDate(String.valueOf(LocalDateTime.now()));
            tree.setIdCategory(5);
            tree.setQuantity(100);
            tree.setMoney(150000.0);
            tree.setRepairer(1);
            tree.setIsActive(0);
            tree.setIsDelete(0);
            tree.setQuantity(0);
            tree.setNotes("Hạt giống");
            treesService.save(tree);
            for (int j = 0; j < 3; j++) {
                TreesImage treesImage = new TreesImage();
                treesImage.setCreateDate(String.valueOf(LocalDateTime.now()));
                treesImage.setUpdateDate(String.valueOf(LocalDateTime.now()));
                treesImage.setMainImage(0);
                treesImage.setUrl("NGO_TIM.jpg");
                treesImage.setRepairer(1);
                treesImage.setIdTree(tree.getId());
                treeImageService.save(treesImage);
            }
        }

        return "/user/index";
    }


}
