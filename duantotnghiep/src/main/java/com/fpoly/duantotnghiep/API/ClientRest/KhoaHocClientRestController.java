package com.fpoly.duantotnghiep.API.ClientRest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.duantotnghiep.Entity.KhoaHoc;
import com.fpoly.duantotnghiep.Entity.LoaiKhoaHoc;
import com.fpoly.duantotnghiep.jparepository.LoaiKhoaHocRepository;
import com.fpoly.duantotnghiep.service.KhoaHocService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/khoahoc")
public class KhoaHocClientRestController {

    @Autowired
    KhoaHocService khoaHocService;

    @Autowired
    LoaiKhoaHocRepository loaiKhoaHocRepository;

    @Autowired
    public KhoaHocClientRestController(KhoaHocService khoaHocService) {
        this.khoaHocService = khoaHocService;
    }

    @GetMapping
    public ResponseEntity<List<KhoaHoc>> getAllKhoaHoc() {
        List<KhoaHoc> khoaHocList = khoaHocService.findAll();
        return new ResponseEntity<>(khoaHocList, HttpStatus.OK);
    }

    @GetMapping("/loai/{loai}")
    public ResponseEntity<List<LoaiKhoaHoc>> getAllKhoaHocDuyet(@PathVariable Integer loai) {
        List<LoaiKhoaHoc> khoaHocList = loaiKhoaHocRepository.findAllById(loai);
        return new ResponseEntity<>(khoaHocList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhoaHoc> getKhoaHocById(@PathVariable int id) {
        KhoaHoc khoaHoc = khoaHocService.findById(id);
        if (khoaHoc != null) {
            return new ResponseEntity<>(khoaHoc, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byLoai/{loai}")
    public List<KhoaHoc> getKhoaHocByLoai(@PathVariable Integer loai) {
        return khoaHocService.getKhoaHocByLoai(loai);
    }

    @PutMapping("/{id}")
    public KhoaHoc update(
            @PathVariable("id") Integer id,
            @RequestBody KhoaHoc khoaHoc,
            HttpServletRequest request) throws IOException {
        return khoaHocService.update(khoaHoc);
    }

    @PostMapping()
    public KhoaHoc create(@RequestParam("hinhAnh") MultipartFile file, @RequestPart("khoaHoc") KhoaHoc khoaHoc,
            HttpServletRequest request) throws IOException {

        // Process the uploaded file
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();

            // Specify the directory where you want to save the file
            String uploadPath = "src/main/resources/static/img/";

            // Generate a unique filename
            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            // Create the complete file path
            String filePath = uploadPath + fileName;

            // Save the file
            java.nio.file.Path nioPath = Paths.get(filePath); // Sử dụng java.nio.file.Path

            Files.write(nioPath, bytes);
            khoaHoc.setHinhAnh(fileName);
        }

        return khoaHocService.create(khoaHoc);
    }

}
