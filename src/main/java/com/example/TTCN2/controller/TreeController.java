package com.example.TTCN2.controller;

import com.example.TTCN2.domain.Admin;
import com.example.TTCN2.domain.Tree;
import com.example.TTCN2.domain.TreesImage;
import com.example.TTCN2.repository.DetailAdminRepository;
import com.example.TTCN2.repository.TreeRepository;
import com.example.TTCN2.repository.TreesImageRepository;
import com.example.TTCN2.service.TreeImageService;
import com.example.TTCN2.service.TreesService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tree")
public class TreeController {
    @Autowired
    TreeRepository treeRepository;
    @Autowired
    TreesImageRepository imageRepository;
    @Autowired
    TreesService treesService;
    @Autowired
    TreeImageService imageService;
    @Autowired
    DetailAdminRepository detailAdminRepository;
    @Autowired
    TreesImageRepository treesImageRepository;

    // xem chi tiet san pham
    @GetMapping("/detailTree/{idCate}/{idTree}")
    public String detailTree(Model model, @PathVariable(name = "idTree") Integer idTree,
                             @PathVariable(name = "idCate") Integer idCate,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {

        // Tạo status cho web :1 trang chủ, 2 trang xem chi tiết
        model.addAttribute("statusWeb",2);

        model.addAttribute("tree", treeRepository.findDetailTreeById(idTree));
        model.addAttribute("images",imageRepository.findByIdTree_Images(idTree));
        model.addAttribute("treesToCategory",treeRepository.findDetailTree_CategoryId(idCate,idTree));
        // add image theo Tree
        List<Tree> trees = treeRepository.findAllTree();
        List<TreesImage> images=new ArrayList<>();
        for (Tree tree : trees) {
            TreesImage treesImage = imageRepository.findByMainTreeId_Image(tree.getId());
            images.add(treesImage);
        }
        model.addAttribute("tree_image",images);

        return "user/detailTree";
    }
    @GetMapping("/admin/showTrees")
    public String showTrees(Model model, HttpSession session,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        // phân trang cho trang chủ
        int currentPage = page.orElse(1); // số trang
        int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
        Page<Tree> productPage = treesService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("trees", productPage);
        // add image theo Tree
        List<TreesImage>images=new ArrayList<>();
        for (Tree tree : productPage) {
            TreesImage treesImage = treesImageRepository.findByMainTreeId_Image(tree.getId());
            images.add(treesImage);
        }
        model.addAttribute("tree_image",images);
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("maxPageNumber",pageNumbers.size());
        }
        return "admin/trees/showTrees";
    }
    // xem chi tiet san pham admin
    @GetMapping("/admin/detailTree/{idTree}")
    public String detailTreeAdmin(Model model, @PathVariable(name = "idTree") Integer idTree,
                                  HttpSession  session) {
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        model.addAttribute("tree", treeRepository.findDetailTreeById(idTree));
        model.addAttribute("images",imageRepository.findByIdTree_Images(idTree));
        // add image theo Tree
        List<Tree> trees = treeRepository.findAllTree();
        List<TreesImage> images=new ArrayList<>();
        for (Tree tree : trees) {
            TreesImage treesImage = imageRepository.findByMainTreeId_Image(tree.getId());
            images.add(treesImage);
        }
        model.addAttribute("tree_image",images);

        return "admin/trees/detailTree";
    }

}
