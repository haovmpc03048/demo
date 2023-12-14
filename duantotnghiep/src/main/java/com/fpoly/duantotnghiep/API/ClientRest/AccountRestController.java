package com.fpoly.duantotnghiep.API.ClientRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.config.MailService;
import com.fpoly.duantotnghiep.jparepository.NguoiDungRepository;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountRestController {
    @Autowired
    HttpSession session;
    @Autowired
    NguoiDungRepository nguoiDungRepository;
    @Autowired
    MailService mailService;

    @PostMapping("/nguoidung")
    public NguoiDung createNguoiDung(@RequestBody NguoiDung nguoiDung) {
        nguoiDung.setTrangThai("false");
        nguoiDung.setChucVu("false");
        nguoiDung.setXac_minh(false);
        try {
            mailService.activeAccountEmail(nguoiDung.getEmail(), nguoiDung.getHoTen(),
                    "http://localhost:8080/api/xacminh/" + nguoiDung.getEmail());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return nguoiDungRepository.save(nguoiDung);
    }

    @GetMapping("/nguoidung")
    public List<NguoiDung> layDanhSachNguoiDung() {
        return nguoiDungRepository.findAll();
    }

    @GetMapping("/nguoidung/checkUsername/{username}")
    public boolean checkIfUsernameExists(@PathVariable String username) {
        // Kiểm tra xem tên tài khoản đã tồn tại trong cơ sở dữ liệu hay chưa
        NguoiDung existingUser = nguoiDungRepository.findByTaiKhoan(username);
        return existingUser != null;
    }

    @GetMapping("/nguoidung/checkEmail/{email}")
    public boolean checkIfEmailExists(@PathVariable String email) {
        // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu hay chưa
        NguoiDung existingUser = nguoiDungRepository.findByEmail(email);
        return existingUser != null;
    }

    @GetMapping("/xacminh/{email}")
    public RedirectView verifyEmail(@PathVariable("email") String email) {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email);
        nguoiDung.setXac_minh(true);
        nguoiDungRepository.save(nguoiDung);
        return new RedirectView("/courseOnline/dangnhap");
    }

    @GetMapping("/otp")
    public RedirectView quenmk(@RequestParam("Email") String Email) {
        if (nguoiDungRepository.findByEmail(Email) == null) {
            return new RedirectView("/courseOnline/EmailError");
        }
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        String otp = String.valueOf(randomNumber);
        session.setAttribute("otp", randomNumber);
        session.setMaxInactiveInterval(60);
        session.setAttribute("email", Email);
        System.out.println(otp);
        try {
            mailService.otpAccountEmail(Email, otp);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return new RedirectView("/courseOnline/confirmotp");
    }

    @GetMapping("/confirm")
    public RedirectView confom(@RequestParam("otp") String otp) {
        String mck = String.valueOf(session.getAttribute("otp"));
        if (session.getAttribute("otp") == null) {

            return new RedirectView("/courseOnline/ErorOTP");
        }
        if (otp.equals(mck)) {
            session.setAttribute("kiemtraotp", "KT");
            System.out.println(otp);
            return new RedirectView("/courseOnline/doimk");
        }
        return new RedirectView("/courseOnline/FaillOTP");
    }

    @GetMapping("/doimaukhau")
    public RedirectView doimaukhau(@RequestParam("maukhaumoi") String password) {
        System.out.println(session.getAttribute("kiemtraotp"));
        if (session.getAttribute("kiemtraotp") == null) {
            return new RedirectView("/courseOnline/OTPError");
        }
        String email = String.valueOf(session.getAttribute("email"));
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email);
        if (nguoiDung == null) {
            return new RedirectView("/courseOnline/Faillsesion");
        }
        nguoiDung.setMatKhau(password);
        nguoiDungRepository.save(nguoiDung);
        return new RedirectView("/courseOnline/dangnhap");
    }
}
