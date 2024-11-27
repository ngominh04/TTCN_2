package com.example.TTCN2.controller;

import com.example.TTCN2.domain.*;
import com.example.TTCN2.projection.IOrder;
import com.example.TTCN2.repository.*;
import com.example.TTCN2.service.ParamService;
import com.example.TTCN2.service.SHA_256_password;
import com.example.TTCN2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    ParamService paramService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    SHA_256_password sha_256_password;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    ShipperRepository shipperRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ShipperOrderRepository shipperOrderRepository;
    // chon quyen dang nhap
    @GetMapping("/role")
    public String role(){
        return "user/register/role";
    }
    // vao login
    @GetMapping("/login/{idRole}")
    public String login(Model model, @PathVariable Integer idRole,HttpSession session){
        model.addAttribute("role",idRole);
        session.setAttribute("idRole",idRole);
        return "user/register/login";
    }
    // ktra login
    @PostMapping("/login_check")
    public String login(Model model,
                        @RequestParam(name = "username") String username,
                        HttpSession session, Cart item){
        String password = paramService.getString("password","");
        Integer roleSS = (Integer) session.getAttribute("idRole");
        try {
            // user
            if(roleSS == 2){
                User customer= userRepository.getCustomer(username);
//                session.setAttribute("saveCus",customer);
                if(customer.getPassword().equals(password)){
                    session.setAttribute("saveCus",customer);
                    model.addAttribute("customer",userRepository.getCustomer(username));
                    // thay đổi lại pass vs những người dùng cũ
                    model.addAttribute("message","Vì lí do bảo mật của chúng tôi với khách hàng cũ bạn vui lòng thay lại password: " +
                            "Mật khẩu bạn phải hơn 8 kí tự, bao gồm ít nhất 1 số, 1 chữ thường, 1 chữ hoa, 1 kí tự đặc biệt");
                    session.removeAttribute("idRole");
                    return "/user/register/forgotPass2";
                }
                if(!customer.getPassword().equals(sha_256_password.GM_SHA_password(password))){
                    model.addAttribute("message","Mật Khẩu Sai __");
                }
                else if (customer.getBlock() == 1) {
                    model.addAttribute("message","Tài khoản của bạn bị khóa");
                } else if (customer.getPassword().equals(sha_256_password.GM_SHA_password(password))) {
                    session.setAttribute("saveCus",customer);
                    model.addAttribute("customer",userRepository.getCustomer(username));
                    // remove sesion
                    session.removeAttribute("idRole");

                    // ktra order status 3 -> 4
                    List<Order> orders = orderRepository.findByCusAndStatus(customer.getId(), 3);
                    for (Order order : orders) {
                        IOrder iOrder =orderRepository.getOrderByStatus4(order.getId());
                        ShipperOrder shipperOrder = shipperOrderRepository.findByOrderId(order.getId());
                        if (iOrder.getDateOrder() >3 && shipperOrder.getStatus()==2){
                            order.setStatus(4);
                            orderRepository.save(order);
                        }
                    }

                    return "redirect:/";
                }
            }
            // admin
            if(roleSS == 1){
                Admin admin= adminRepository.getAdmin(username);
                if(admin.getPassword().equals(password)){
                    session.setAttribute("saveAdmin",admin);
                    model.addAttribute("customer",adminRepository.getAdmin(username));
                    session.removeAttribute("idRole");
                    return "redirect:/admin/"+admin.getId();
                }
                else if(!admin.getPassword().equals(sha_256_password.GM_SHA_password(password))){
                    model.addAttribute("message","Mật Khẩu Sai __");
                }
                else if(admin.getPassword().equals(sha_256_password.GM_SHA_password(password))){
                    session.setAttribute("saveAdmin",admin);
                    model.addAttribute("customer",adminRepository.getAdmin(username));
                    session.removeAttribute("idRole");
                    return "redirect:/admin/"+admin.getId();
                }
            }
            // shipper
            if(roleSS == 3){
                Shipper shipper = shipperRepository.checkLogin(username);
                if(shipper.getPassword().equals(password)){
                    session.setAttribute("saveShipper",shipper);
                    model.addAttribute("shipper",shipper);
                    session.removeAttribute("idRole");
                    return "redirect:/shipper/"+shipper.getId();
                }
                else if(!shipper.getPassword().equals(sha_256_password.GM_SHA_password(password))){
                    model.addAttribute("message","Mật Khẩu Sai __");
                }
                else if (shipper.getBlock() == 0) {
                    model.addAttribute("message","Tài khoản của bạn bị khóa");
                } else {

                    model.addAttribute("shipper",shipper);

                    return "redirect:/shipper/"+shipper.getId();
                }
            }

        }catch (Exception e){
            model.addAttribute("message","Không tồn tại tài khoản");
        }


        return "user/register/login";
    }
    // dang xuat
    @GetMapping("/")
    public String logout(HttpSession session){
        session.removeAttribute("saveCus");
        session.removeAttribute("saveAdmin");
        session.removeAttribute("countCate");
        session.removeAttribute("countTree");
        session.removeAttribute("countUser");
        session.removeAttribute("countOrder");
        session.removeAttribute("countShipper");
        session.removeAttribute("countCart_user");
        return "redirect:/";
    }

    // ng dùng quên pass
    @GetMapping("/forgotPass")
    public String forgotPass(){
        return "/user/register/forgotPass";
    }
    @PostMapping("/forgotPass")
    public String forgotPass(Model model,
                             @RequestParam("email")String email,
                             @RequestParam("username")String username,
                             HttpSession session){
        List<User> customers = userRepository.getAll();
        boolean check = false;
        for (User customer:customers) {
            if (customer.getEmail().equals(email)){
                if (customer.getUsername().equals(username)){
                    check = false;
                    session.setAttribute("usernameForgot",username);
                    return "/user/register/forgotPass2";
                }
            }
            else {
                check = true;
            }
        }
        if(check){
            model.addAttribute("message","Không tồn tại email hoặc tài khoản này");
        }
        return "/user/register/forgotPass";
    }
    @PostMapping("forgotPass2")
    public String forgotPass2(Model model,
                              @RequestParam("password")String password,
                              @RequestParam("password2")String password2,
                              HttpSession session){
        if (!password.equals(password2)){
            model.addAttribute("message","2 mật khẩu phải giống nhau");
            return "/user/register/forgotPass2";
        }
        if (password.length() <8){
            model.addAttribute("message","Mật khẩu phải trên 8 ký tự");
            return "/user/register/forgotPass2";
        }
        //Dùng regex
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).+$");
        Matcher matcher = pattern.matcher(password);
        boolean checkPass = matcher.matches();
        if (!checkPass){
            model.addAttribute("message",
                    "Mật khẩu phải chứa ít nhất 1 kí tự số, " +
                            "1 chữ thường, 1 chữ hoa, 1 ký tự đặc biệt");
            return "/user/register/forgotPass2";
        }
        else {
            User customer = userRepository.getCustomer((String) session.getAttribute("usernameForgot"));
            customer.setPassword(SHA_256_password.SHA_password(password));
            session.removeAttribute("usernameForgot");
            session.removeAttribute("idRole");
            userService.save(customer);
        }
        return "/user/register/Login";
    }
    // thay đổi pass trong trang người dùng
    @GetMapping("/repairPassG/{idCus}")
    public String repairPass(@PathVariable("idCus") Integer idCus){
        return "/user/register/forgotPass2";
    }
    @PostMapping("/repairPassP/{idCus}")
    public String repairPass(Model model,@PathVariable("idCus") Integer idCus,
                             @RequestParam("password")String password,
                             @RequestParam("password2")String password2,
                             HttpSession session){
        if (!password.equals(password2)){
            model.addAttribute("message","2 mật khẩu phải giống nhau");
            return "/user/register/forgotPass2";
        }
        if (password.length() <8){
            model.addAttribute("message","Mật khẩu phải trên 8 ký tự");
            return "/user/register/forgotPass2";
        }
        //Dùng regex
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).+$");
        Matcher matcher = pattern.matcher(password);
        boolean checkPass = matcher.matches();
        if (!checkPass){
            model.addAttribute("message",
                    "Mật khẩu phải chứa ít nhất 1 kí tự số, " +
                            "1 chữ thường, 1 chữ hoa, 1 ký tự đặc biệt");
            return "/user/register/forgotPass2";
        }
        else {
            User customer = (User) session.getAttribute("saveCus");
            customer.setPassword(SHA_256_password.SHA_password(password));
            userService.save(customer);
        }
        return "redirect:/receiver/receiver/{idCus}";
    }
}
