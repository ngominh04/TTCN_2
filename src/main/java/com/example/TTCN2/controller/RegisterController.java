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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    ChatBoxRepository chatBoxRepository;
    @Autowired
    ReceiverRepository receiverRepository;
    @Autowired
    DetailAdminRepository detailAdminRepository;
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

                    // remove sesion
//                    session.removeAttribute("idRole");

                    // thay đổi lại pass vs những người dùng cũ
                    model.addAttribute("message","Vì lí do bảo mật của chúng tôi với khách hàng cũ bạn vui lòng thay lại password: " +
                            "Mật khẩu bạn phải hơn 8 kí tự, bao gồm ít nhất 1 số, 1 chữ thường, 1 chữ hoa, 1 kí tự đặc biệt");
//                    session.removeAttribute("idRole");
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
//                    session.removeAttribute("idRole");

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
//                    session.removeAttribute("idRole");
                    return "redirect:/admin/"+admin.getId();
                }
                else if(!admin.getPassword().equals(sha_256_password.GM_SHA_password(password))){
                    model.addAttribute("message","Mật Khẩu Sai __");
                }
                else if(admin.getPassword().equals(sha_256_password.GM_SHA_password(password))){
                    session.setAttribute("saveAdmin",admin);
                    model.addAttribute("customer",adminRepository.getAdmin(username));
//                    session.removeAttribute("idRole");
                    return "redirect:/admin/"+admin.getId();
                }
            }
            // shipper
            if(roleSS == 3){
                Shipper shipper = shipperRepository.checkLogin(username);
                if(shipper.getPassword().equals(password)){
                    session.setAttribute("saveShipper",shipper);
                    model.addAttribute("shipper",shipper);
//                    session.removeAttribute("idRole");
                    return "redirect:/shipper/"+shipper.getId();
                }
                else if(!shipper.getPassword().equals(sha_256_password.GM_SHA_password(password))){
                    model.addAttribute("message","Mật Khẩu Sai __");
                }
                else if (shipper.getBlock() == 1) {
                    model.addAttribute("message","Tài khoản của bạn bị khóa");
                } else {
                    session.setAttribute("saveShipper",shipper);
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
        session.removeAttribute("saveChatBox");
        session.removeAttribute("idRole");
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
            model.addAttribute("message","Không tồn tại email hoặc ten tài khoản này");
        }
        return "/user/register/forgotPass";
    }
    // khi nguoi dung chua login
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
//            session.removeAttribute("idRole");
            userService.save(customer);
        }
        return "redirect:/";
    }
    // thay đổi pass trong trang người dùng (khi nguoi dung da login)
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
        return "redirect:/";
    }
    // đăng kí người dùng
    @GetMapping("/newCus")
    public String newCus(){
        return "/user/register/newCus";
    }
    // lưu tài khoản mới
    @PostMapping("/newCustomer")
    public String newCustomer(@RequestParam("name")String name,
                              @RequestParam("email")String email,
                              @RequestParam("address")String address,
                              @RequestParam("phone")String phone,
                              @RequestParam("username")String username,
                              @RequestParam("password")String password,
                              Model model){
        boolean checkCus = false;
        List<User> customers = userRepository.getAll();
        for (User cus:customers) {
            if (email.isBlank()){
                model.addAttribute("message","email không được để trống");
                checkCus = false;
                break;
            }if (cus.getUsername().equals(username)){
                model.addAttribute("message","Đã tồn tại tên tài khoản");
                checkCus = false;
                break;
            }
            if (phone.length() != 10 ){
                model.addAttribute("message","Số điện thoại phải 10 kí tự ");
                checkCus = false;
                break;
            }
            if (password.length() < 8  ){
                model.addAttribute("message","Mật khẩu phải trên 8 kí tự");
                checkCus = false;
                break;
            }
            //Dùng regex
            Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).+$");
            Matcher matcher = pattern.matcher(password);
            boolean checkPass = matcher.matches();
            if (!checkPass){
                model.addAttribute("message",
                        "Mật khẩu phải chứa ít nhất 1 kí tự số, " +
                                "1 chữ thường, 1 chữ hoa, 1 ký tự đặc biệt");
                checkCus = false;
                break;
            }
            else {
                checkCus =true;
            }
        }
        if(checkCus) {
            User customer = new User();
            customer.setBlock(0);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setIdRole(2);
            // mã hóa pass theo sha_256
            customer.setPassword(sha_256_password.SHA_password(password));

            customer.setCreateDate(String.valueOf(LocalDateTime.now()));
            customer.setUsername(username);
            customer.setAddress(address);
            customer.setName(name);
            userService.save(customer);
            // đồng thời tạo luôn receiver mặc định khi ng dùng tạo xong tài khoản mới
            Receiver receiver = new Receiver();
            receiver.setIdUser(customer.getId());
            receiver.setName(customer.getName());
            receiver.setIsDelete(1);
            receiver.setAddress(customer.getAddress());
            receiver.setPhone(customer.getPhone());
            receiverRepository.save(receiver);
            return "user/register/login";
        }
        return "user/register/newCus";
    }

    // create new admin with role admin == 1
    @GetMapping("/admin/createAdmin")
    public String createAdmin(){
        return "/admin/account/createNewAdmin";
    }
    @PostMapping("/admin/createNewAdmin")
    public String createNewAdmin(@RequestParam("username")String username,
                                 @RequestParam("password")String password,
                                 @RequestParam("name")String name,
                                 @RequestParam("phone")String phone,
                                 @RequestParam("address")String address,
                                 @RequestParam("id_card")String idCard,
                                 @RequestParam("power") Integer power,
                                 @RequestParam("note_power") String notePower,
                                 Model model,HttpSession session){
        Admin admin1 = (Admin) session.getAttribute("saveAdmin");
        List<Admin> admins = adminRepository.findAll();
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) ) {
                model.addAttribute("userError", "Đã tồn tại tên tài khoản");
                return "/admin/account/createNewAdmin";
            }
            if (username.isBlank() ) {
                model.addAttribute("userError", "Bạn đang để trống tên tài khoản ");
                return "/admin/account/createNewAdmin";
            }
            if (password.isBlank() ) {
                model.addAttribute("passwordError", "Bạn đang để trống password ");
                return "/admin/account/createNewAdmin";
            }
            if (password.length() < 5 ) {
                model.addAttribute("passwordError", "Số password phải > 5 số");
                return "/admin/account/createNewAdmin";
            }

            if (name.isBlank() ) {
                model.addAttribute("nameError", "Bạn đang để trống họ tên ");
                return "/admin/account/createNewAdmin";
            }
            if (phone.length() != 10 ) {
                model.addAttribute("phoneError", "Số điện thoại phải 10 số");
                return "/admin/account/createNewAdmin";
            }
            if (address.isBlank() ) {
                model.addAttribute("addressError", "Bạn đang để trống địa chỉ");
                return "/admin/account/createNewAdmin";
            }
            if (idCard.length() != 12 ) {
                model.addAttribute("idCardError", "CCCD phải 12 số");
                return "/admin/account/createNewAdmin";
            }
        }
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setCreateDate(String.valueOf(LocalDateTime.now()));
        admin.setPower(power);
        admin.setNotePower(notePower);
        adminRepository.save(admin);

        DetailAdmin detailAdmin = new DetailAdmin();
        detailAdmin.setIdAdmin(admin);
        detailAdmin.setName(name);
        detailAdmin.setAddress(address);
        detailAdmin.setCreateDate(String.valueOf(LocalDateTime.now()));
        detailAdmin.setPhone(phone);
        detailAdmin.setIdCard(idCard);
        detailAdminRepository.save(detailAdmin);
        return "redirect:/admin/"+admin1.getId();
    }
}
