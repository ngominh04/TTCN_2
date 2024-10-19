package com.example.TTCN2.controller;

import com.example.TTCN2.domain.Tree;
import com.example.TTCN2.domain.TreesImage;
import com.example.TTCN2.repository.TreeRepository;
import com.example.TTCN2.repository.TreesImageRepository;
import com.example.TTCN2.service.TreeImageService;
import com.example.TTCN2.service.TreesService;
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

}
