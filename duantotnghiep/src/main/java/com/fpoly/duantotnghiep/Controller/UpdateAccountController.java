package com.fpoly.duantotnghiep.Controller;
import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.service.NguoiDungService;

@Controller
public class UpdateAccountController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/courseOnline/updateAccount")
    public String showUpdateAccountForm(Model model, Principal principal) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (principal == null) {
            // Nếu chưa đăng nhập, chuyển hướng hoặc xử lý theo ý bạn
            return "redirect:/dangnhap"; // Chuyển hướng đến trang đăng nhập
        }

        String currentUsername = principal.getName();
        NguoiDung nguoiDung = nguoiDungService.findByTaiKhoan(currentUsername);

        // Kiểm tra xem người dùng có tồn tại không
        if (nguoiDung != null) {
            // Lấy id của người dùng
            int userId = nguoiDung.getId();

            // Đặt thông tin người dùng vào model để hiển thị trên form
            model.addAttribute("nguoiDung", nguoiDung);
            model.addAttribute("userId", userId);
            return "updateAccount";
        } else {
            // Xử lý khi không tìm thấy thông tin người dùng
            return "redirect:/courseOnline/index";
        }
    }

    @PostMapping("/courseOnline/updateAccount")
    public String capNhatThongTinNguoiDung(@RequestParam("file") MultipartFile file,
                                           @ModelAttribute("nguoiDung") NguoiDung nguoiDung,
                                           Principal principal) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (principal == null) {
            // Nếu chưa đăng nhập, chuyển hướng hoặc xử lý theo ý bạn
            return "redirect:/dangnhap"; // 
        }

        String currentUsername = principal.getName();
        NguoiDung currentUser = nguoiDungService.findByTaiKhoan(currentUsername);

        // Kiểm tra xem người dùng có tồn tại không
        if (currentUser != null) {
            // Lấy id của người dùng
            int userId = currentUser.getId();

            try {
                // Cập nhật thông tin người dùng và hình ảnh
                nguoiDung.setId(userId);
                nguoiDungService.capNhatNguoiDung(userId, nguoiDung, file);
            } catch (IOException e) {
                // Xử lý lỗi khi cập nhật hình ảnh
                e.printStackTrace();
                // (Bạn có thể thêm xử lý lỗi phù hợp với ứng dụng của bạn)
            }

            return "redirect:/courseOnline/index";
        } else {
            // Xử lý khi không tìm thấy thông tin người dùng
            return "redirect:/courseOnline/index";
        }
    }
}
