package com.fpoly.duantotnghiep.API.AdminRest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import com.fpoly.duantotnghiep.Entity.FileMoveRequest;
import com.fpoly.duantotnghiep.Entity.TaiLieuKhoaHoc;
import com.fpoly.duantotnghiep.service.TaiLieuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/admin/TaiLieu")
public class AdminTaiLietRestController {
    @Autowired
    TaiLieuService taiLieuService;

    @GetMapping()
    public List<TaiLieuKhoaHoc> getAll() {
        return taiLieuService.findAll();
    }

    @GetMapping("/{idKhoaHoc}")
    public List<TaiLieuKhoaHoc> getByKhoaHoc(@PathVariable int idKhoaHoc) {
        return taiLieuService.findByKhoaHocId(idKhoaHoc);
    }

    @PostMapping()
    public TaiLieuKhoaHoc AddTaiLieu(@RequestBody TaiLieuKhoaHoc entity) {
        // TODO: process POST request
        this.taiLieuService.save(entity);
        return entity;
    }

    @PostMapping("/move-rename")
    public ResponseEntity<String> moveAndRenameFile(@RequestBody FileMoveRequest request) {
        String tenCu = UriUtils.decode(request.getTenCu(), StandardCharsets.UTF_8.toString());
        String tenMoi = UriUtils.decode(request.getTenMoi(), StandardCharsets.UTF_8.toString());

        File fileCu = new File(tenCu);
        File fileMoi = new File(tenMoi);

        if (fileCu.exists()) {
            if (fileCu.renameTo(fileMoi)) {
                return ResponseEntity.ok("Tệp đã được di chuyển và đổi tên thành công.");
            } else {
                return ResponseEntity.badRequest().body("Không thể di chuyển và đổi tên tệp.");
            }
        } else {
            return ResponseEntity.badRequest().body("Tệp không tồn tại.");
        }
    }

    @PutMapping({ "/{id}" })
    public TaiLieuKhoaHoc updateTaiLieu(@PathVariable int id, @RequestBody TaiLieuKhoaHoc entity) {
        this.taiLieuService.save(entity);
        return entity;
    }

    @DeleteMapping("/{id}")
    public void deleteTaiLieu(@PathVariable int id) {
        this.taiLieuService.deleteById(id);
    }
}
