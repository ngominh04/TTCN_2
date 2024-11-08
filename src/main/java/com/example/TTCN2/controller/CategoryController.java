package com.example.TTCN2.controller;

import com.example.TTCN2.domain.Admin;
import com.example.TTCN2.domain.Category;
import com.example.TTCN2.domain.Tree;
import com.example.TTCN2.domain.TreesImage;
import com.example.TTCN2.repository.CategoryRepository;
import com.example.TTCN2.repository.DetailAdminRepository;
import com.example.TTCN2.repository.TreeRepository;
import com.example.TTCN2.repository.TreesImageRepository;
import com.example.TTCN2.service.CategoryService;
import com.example.TTCN2.service.TreesService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TreeRepository treeRepository;
    @Autowired
    TreesService treesService;
    @Autowired
    TreesImageRepository findByMainTreeId_Image;
    @Autowired
    TreesImageRepository treesImageRepository;
    @Autowired
    DetailAdminRepository  detailAdminRepository;

    @GetMapping(value = "/{idCate}")
    public String category(@PathVariable("idCate") Integer idCate, Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        model.addAttribute("category",categoryRepository.getAllCategory());
        // Tạo status cho web :1 trang chủ, 2 trang xem chi tiết,3 trang xem category
        model.addAttribute("statusWeb",3);
        // phân trang cho trang chủ
        int currentPage = page.orElse(1); // số trang
        Integer count = treeRepository.findAllTreeToIdCategory(idCate).size();
        model.addAttribute("count",count);
        model.addAttribute("idCate",idCate);
        int pageSize = 0 ;
        if (count <8 ){
             pageSize = size.orElse(count); // số sản phẩn trên 1 trang
        }
        else {
             pageSize = size.orElse(8); // số sản phẩn trên 1 trang
        }
        Page<Tree> productPage = treesService.pageCategory(PageRequest.of(currentPage - 1, pageSize),idCate);

        model.addAttribute("trees", productPage);
        // add image theo Tree
        List<TreesImage>images=new ArrayList<>();
        for (Tree tree : productPage) {
            TreesImage treesImage = treesImageRepository.findByMainTreeId_Image(tree.getId());
            images.add(treesImage);
        }
        model.addAttribute("tree_image",images);
//        model.addAttribute("trees", trees);
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("maxPageNumber",pageNumbers.size());
        }
        return "user/treesToCategory";
    }
    // show cate admin
    @GetMapping("/admin/showCate")
    public String showCategory(Model model, HttpSession session,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size) {
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));

        // phân trang cho trang category in admin
        int currentPage = page.orElse(1); // số trang
        int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
        Page<Category> productPage = categoryService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("showCategory",productPage);
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("maxPageNumber",pageNumbers.size());
        }
        return "admin/category/showCategory";
    }
    // edit category
    @GetMapping("/admin/editCategory/{idCate}")
    public String editCate(Model model,HttpSession session,
                           @PathVariable("idCate") Integer idCate,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        if (admin.getPower() == 3){
            model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
            // phân trang cho trang category in admin
            int currentPage = page.orElse(1); // số trang
            int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
            Page<Category> productPage = categoryService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

            model.addAttribute("showCategory",productPage);
            int totalPages = productPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
                model.addAttribute("maxPageNumber",pageNumbers.size());
            }
            model.addAttribute("message","Bạn không đủ quyền hạn để sửa");
            return "admin/category/showCategory";
        }
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        model.addAttribute("category",categoryRepository.getCategoryById(idCate));

        return "admin/category/editCategory";
    }
    @PostMapping ("/admin/saveEditCategory/{idCate}")
    public String editSaveCate(Model model,HttpSession session,
                                @PathVariable("idCate") Integer idCate,
                               @RequestParam("name") String name,
                               @RequestParam("notes")String notes,
                               @RequestParam("is_delete")Integer is_delete){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
//        model.addAttribute("category",categoryRepository.getCategoryById(idCate));
        Category category = categoryRepository.getCategoryById(idCate);
        category.setUpdateDate(String.valueOf(LocalDateTime.now()));
        category.setRepairer(admin.getId());
        category.setIsDelete(is_delete);
        category.setName(name);
        category.setNotes(notes);
        categoryService.save(category);
        return "redirect:/category/admin/showCate";
    }
    // new cate
    @GetMapping("/admin/newCategory")
    public String newCategory(Model model,HttpSession session,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        if (admin.getPower() == 3){
            model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
            // phân trang cho trang category in admin
            int currentPage = page.orElse(1); // số trang
            int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
            Page<Category> productPage = categoryService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

            model.addAttribute("showCategory",productPage);
            int totalPages = productPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
                model.addAttribute("maxPageNumber",pageNumbers.size());
            }
            model.addAttribute("message","Bạn không đủ quyền hạn để thêm");
            return "admin/category/showCategory";
        }
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        return "admin/category/newCategory";
    }
    @PostMapping("/admin/newSaveCategory")
    public String newSaveCategory(@RequestParam("name") String name,
                                  @RequestParam("notes")String notes,
                                  HttpSession session,Model model){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
        Category category = new Category();
        category.setName(name);
        category.setNotes(notes);
        category.setRepairer(admin.getId());
        category.setIsDelete(0);
        category.setCreateDate(String.valueOf(LocalDateTime.now()));
        categoryService.save(category);
        return "redirect:/category/admin/showCate";
    }
    // remove cate
    @GetMapping("/admin/removeCategory/{idCate}")
    public String removeCategory(@PathVariable("idCate") Integer idCate,
                                 HttpSession session,Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size){
        Admin admin = (Admin) session.getAttribute("saveAdmin");
        if (admin.getPower() == 1){
            Category category = categoryRepository.getCategoryById(idCate);
            categoryService.delete(category);
        }
        else {
            model.addAttribute("detailAdmin",detailAdminRepository.findDetailAdminById(admin.getId()));
            // phân trang cho trang category in admin
            int currentPage = page.orElse(1); // số trang
            int pageSize = size.orElse(8); // số sản phẩn trên 1 trang
            Page<Category> productPage = categoryService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

            model.addAttribute("showCategory",productPage);
            int totalPages = productPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
                model.addAttribute("maxPageNumber",pageNumbers.size());
            }
            model.addAttribute("message","Bạn không đủ quyền hạn để xóa");
            return "admin/category/showCategory";
        }
        return "redirect:/category/admin/showCate";
    }
}
