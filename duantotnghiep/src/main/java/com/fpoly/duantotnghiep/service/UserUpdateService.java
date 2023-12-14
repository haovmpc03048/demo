package com.fpoly.duantotnghiep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; 
import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.jparepository.NguoiDungRepository;

@Service
public class UserUpdateService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public ResponseEntity<NguoiDung> updateLoggedInUser(NguoiDung updatedNguoiDung, Authentication authentication) {
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
