package com.fpoly.duantotnghiep.API.ClientRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.jparepository.NguoiDungRepository;

@RestController
@RequestMapping("/api/account")
public class UpdateAccountRestController {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @GetMapping("/info")
    public ResponseEntity<NguoiDung> getAccountInfo(Authentication authentication) {
        // Lấy thông tin người dùng từ đối tượng Authentication
        String username = authentication.getName();
        NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(username);

        if (nguoiDung != null) {
            return ResponseEntity.ok(nguoiDung);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<NguoiDung> updateAccountInfo(Authentication authentication, @RequestBody NguoiDung updatedNguoiDung) {
        // Lấy thông tin người dùng từ đối tượng Authentication
        String username = authentication.getName();
        NguoiDung existingNguoiDung = nguoiDungRepository.findByTaiKhoan(username);

        if (existingNguoiDung != null) {
            // Cập nhật thông tin người dùng
            existingNguoiDung.setHoTen(updatedNguoiDung.getHoTen());
            existingNguoiDung.setEmail(updatedNguoiDung.getEmail());
            // Các trường thông tin khác

            NguoiDung updatedAccount = nguoiDungRepository.save(existingNguoiDung);
            return ResponseEntity.ok(updatedAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}