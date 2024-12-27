package com.example.TTCN2.controller;

import com.example.TTCN2.domain.*;
import com.example.TTCN2.repository.*;
import com.example.TTCN2.service.TreeImageService;
import com.example.TTCN2.service.TreesService;
import com.example.TTCN2.service.uploadFileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    uploadFileService uploadFileService;
    @Autowired
    TreeImageService treeImageService;
    @Autowired
    EvaluateRepository evaluateRepository;
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
        model.addAttribute("evaluates",evaluateRepository.findAll());
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
        model.addAttribute("category",categoryRepository.findById(treeRepository.findDetailTreeById(idTree).getIdCategory()));
        model.addAttribute("evaluates",evaluateRepository.findAll());
        return "admin/trees/detailTree";
    }
    // edit tree
    @GetMapping("/admin/editTree/{idTree}")
    public String editTree(Model model,HttpSession session,
                           @PathVariable("idTree") Integer idTree,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        if (admin.getPower() == 3){
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
            model.addAttribute("message","Bạn không đủ quyền hạn để sửa");
            return "admin/trees/showTrees";
        }
        // add image theo Tree
        model.addAttribute("category",categoryRepository.getAllCategory());
        model.addAttribute("tree_image",imageRepository.findByIdTree_Images(idTree));
        model.addAttribute("tree",treeRepository.findDetailTreeById(idTree));
        return "admin/trees/editTree";
    }
    @PostMapping("/admin/saveEditTree/{idTree}")
    public String editSaveCate(Model model,HttpSession session,
                               @PathVariable("idTree") Integer idTree,
                               @RequestParam("name") String name,
                               @RequestParam("quantity")Integer quantity,
                               @RequestParam("notes")String notes,
                               @RequestParam("price")String priceStr,
                               @RequestParam("is_active")Integer is_active,
                               @RequestParam("is_delete")Integer is_delete,
//                               @RequestParam("image") Integer image,
                               @RequestParam("imageID")Integer imageID){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));

        double price = Double.parseDouble(priceStr.replace(".",""));

        Tree tree = treeRepository.findDetailTreeById(idTree);
        tree.setName(name);
        tree.setQuantity(quantity);
        tree.setNotes(notes);
        tree.setMoney(price);
        tree.setIsActive(is_active);
        tree.setIsDelete(is_delete);
        tree.setUpdateDate(String.valueOf(LocalDateTime.now()));
        tree.setRepairer(admin.getId());
        treesService.save(tree);
        List<TreesImage> images = imageRepository.findByIdTree_Images(tree.getId());
        for (TreesImage treesImage : images) {
            if (imageID == treesImage.getId()){
                treesImage.setMainImage(1);
                treesImage.setUpdateDate(String.valueOf(LocalDateTime.now()));
                treesImage.setRepairer(admin.getId());
                imageService.save(treesImage);
            }else {
                treesImage.setMainImage(0);
                treesImage.setUpdateDate(String.valueOf(LocalDateTime.now()));
                treesImage.setRepairer(admin.getId());
                imageService.save(treesImage);
            }
        }
        return "redirect:/tree/admin/detailTree/{idTree}";
    }
    // new tree
    @GetMapping("/admin/newTree")
    public String newTree(Model model,HttpSession session,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        if (admin.getPower() == 3){
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
            model.addAttribute("message","Bạn không đủ quyền hạn để sửa");
            return "admin/trees/showTrees";
        }
        model.addAttribute("category",categoryRepository.getAllCategory());
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        return "admin/trees/newTree";
    }
    @PostMapping("/admin/saveNewTree")
    public String newSaveCate(Model model,HttpSession session,
                               @RequestParam("name") String name,
                               @RequestParam("quantity")Integer quantity,
                               @RequestParam("notes")String notes,
                               @RequestParam("price")String priceStr,
                              @RequestParam("category") Integer category){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));

        double price = Double.parseDouble(priceStr.replace(".",""));

        Tree tree = new Tree();
        tree.setIdCategory(category);
        tree.setName(name);
        tree.setQuantity(quantity);
        tree.setNotes(notes);
        tree.setMoney(price);
        tree.setIsActive(0);
        tree.setIsDelete(0);
        tree.setCreateDate(String.valueOf(LocalDateTime.now()));
        tree.setRepairer(admin.getId());
        treesService.save(tree);
        // add image theo tree
        for (int i = 0; i < 3; i++) {
            TreesImage treesImage =new TreesImage();
            treesImage.setUrl(null);
            if (i == 0) {
                treesImage.setMainImage(1);
            }else {
                treesImage.setMainImage(0);
            }
            treesImage.setIdTree(tree.getId());
            treesImage.setCreateDate(String.valueOf(LocalDateTime.now()));
            treesImage.setRepairer(admin.getId());
            imageService.save(treesImage);
        }

        return "redirect:/tree/admin/showTrees";
    }
    // repair image
    @GetMapping("/admin/repairImg/{idTree}/{idImage}")
    public String repairImg(@PathVariable("idImage") Integer idImage,
                            @PathVariable("idTree") Integer idTree,
                            Model model,HttpSession session){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        model.addAttribute("tree", treeRepository.findDetailTreeById(idTree));
        model.addAttribute("images",imageRepository.findByIdTree_Images(idTree));
        model.addAttribute("imageById",treesImageRepository.getByIdImage(idImage));
        return "/admin/trees/editTreeImage";
    }
    // save image
    @PostMapping("/admin/saveRepairImg/{idTree}/{idImage}")
    public String saveRepairImg(@PathVariable("idImage")Integer idImage,
                                @PathVariable("idTree") Integer idTree,
                                HttpSession session,
                                @RequestParam("file") MultipartFile file,Model model){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        uploadFileService.store(file);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        TreesImage treesImage = treesImageRepository.getByIdImage(idImage);
        treesImage.setUrl(fileName);
        treesImage.setUpdateDate(String.valueOf(LocalDateTime.now()));
        treesImage.setRepairer(admin.getId());
        treesImageRepository.save(treesImage);
        return "redirect:/tree/admin/detailTree/{idTree}";
    }
    // remove tree vs power 1
    @GetMapping("/admin/removeTree/{idTree}")
    public String removeTree(HttpSession session,Model model,
                             @PathVariable("idTree")Integer idTree,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        if (admin.getPower() == 1){
            Tree tree = treeRepository.findAllById(idTree);
            List<TreesImage> images = treesImageRepository.findByIdTree_Images(tree.getId());
            for (TreesImage image : images) {
                treeImageService.delete(image);
            }
//            treeImageService.deleteByTreeId(idTree);
            treeRepository.delete(tree);
        }
        else {
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
            model.addAttribute("message","Bạn không đủ quyền hạn để xóa");
            return "redirect:/tree/admin/showTrees";
    }
}
