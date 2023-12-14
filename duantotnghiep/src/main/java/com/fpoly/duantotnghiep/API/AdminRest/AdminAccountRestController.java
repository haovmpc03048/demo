package com.fpoly.duantotnghiep.API.AdminRest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.jparepository.NguoiDungRepository;
import com.fpoly.duantotnghiep.service.NguoiDungService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/admin/NguoiDung")
public class AdminAccountRestController {
    @Autowired
    NguoiDungRepository nguoiDungRepository;
    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping()
    public List<NguoiDung> getAll() {
        return nguoiDungRepository.findAll();
    }

    @PostMapping("/them")
    public ResponseEntity<?> addNguoiDung(NguoiDung nguoiDung, MultipartFile file) {
        try {
            NguoiDung addedNguoiDung = nguoiDungService.addNguoiDung(nguoiDung, file);
            return ResponseEntity.ok(addedNguoiDung);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Lỗi khi lưu file hình ảnh: " + e.getMessage());
        }
    }

    @PostMapping("/them1")
    public ResponseEntity<?> addNguoiDung1(@RequestBody NguoiDung nguoiDung) {
        NguoiDung addedNguoiDung = nguoiDungService.save(nguoiDung);
        return ResponseEntity.ok(addedNguoiDung);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NguoiDung> layNguoiDung(@PathVariable("id") int id) {
        NguoiDung nguoiDung = nguoiDungService.getNguoiDungById(id);
        if (nguoiDung != null) {
            return ResponseEntity.ok(nguoiDung);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteNguoiDung(@PathVariable("id") int id) {
        nguoiDungRepository.deleteById(id);
    }

}
