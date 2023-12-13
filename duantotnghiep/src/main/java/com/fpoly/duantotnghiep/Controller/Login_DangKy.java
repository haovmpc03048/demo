package com.fpoly.duantotnghiep.Controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fpoly.duantotnghiep.Entity.NguoiDung;
import org.springframework.ui.Model;
import com.fpoly.duantotnghiep.jparepository.NguoiDungRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class Login_DangKy {
    @Autowired
    NguoiDungRepository nguuoidungRepository;
    @Autowired
    HttpSession httpSession;

    @RequestMapping("/courseOnline/dangky")
    public String showsinupFrom(Model model) {
        model.addAttribute("user", new NguoiDung());
        return "dangky";
    }

    @RequestMapping("/auth/blocked")
    public String xacminh(Model model) {
        return "dangnhap";
    }

    @GetMapping("/courseOnline/dangnhap")
    public String dangnhap() {
        httpSession.removeAttribute("user");
        httpSession.removeAttribute("admin");
        return "dangnhap";
    }

    @RequestMapping("/courseOnline/capnhat")
    public String capnhatform(Model model) {
        return "capnhattaikhoan";
    }

    @RequestMapping("/auth/login_error")
    public String loginrror() {
        return "dangnhap";
    }

    @GetMapping("/courseOnline/EmailError")
    public String emailerror() {
        return "dangnhap";
    }

    @GetMapping("/courseOnline/OTPError")
    public String otpError() {
        return "dangnhap";
    }

    @GetMapping("/courseOnline/confirmotp")
    public String confirmotp() {

        return "nhapmaotp";
    }

    @GetMapping("/courseOnline/doimk")
    public String doimk() {
        return "doimk";
    }

    @GetMapping("/courseOnline/ErorOTP")
    public String ErorrOTP(Model model) {
        model.addAttribute("message",
                "Xin lỗi, OTP của bạn đã hết hạn");

        return "nhapmaotp";
    }
    @GetMapping("/courseOnline/FaillOTP")
    public String failotp(Model model) {
        model.addAttribute("message", "Sai OTP vui lòng nhập lại ");

        return "nhapmaotp";
    }


    @RequestMapping("/oauth2/login/success")
    public String success(OAuth2AuthenticationToken oauth2) {
        String email = oauth2.getPrincipal().getAttribute("email");
        String password = Long.toHexString(System.currentTimeMillis());
        String name = oauth2.getPrincipal().getAttribute("name");
        NguoiDung nguoiDung = nguuoidungRepository.findByEmail(email);
        if (nguoiDung != null) {
            httpSession.setAttribute("user", nguoiDung);
            return "redirect:/courseOnline/index";
        } else {
            NguoiDung nguoixai = new NguoiDung(email, password, name, email, "false", "false", true, false);
            nguuoidungRepository.save(nguoixai);
            httpSession.setAttribute("user", nguoixai);
            return "redirect:/courseOnline/index";
        }

    }

    @GetMapping("/courseOnline/Faillsesion")
    public String sesion(Model model) {
        model.addAttribute("message", "Đổi mật khẩu thất bạn ");

        return "doimk";
    }

    @GetMapping("/courseOnline/huy")
    public String huy(Model model) {
        return "dangnhap";
    }
}
