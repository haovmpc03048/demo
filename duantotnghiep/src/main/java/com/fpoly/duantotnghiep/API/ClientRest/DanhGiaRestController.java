package com.fpoly.duantotnghiep.API.ClientRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.DanhGia;
import com.fpoly.duantotnghiep.service.DanhGiaService;

@RestController
@RequestMapping("/api/danhgia")
public class DanhGiaRestController {

    @Autowired
    private DanhGiaService danhGiaService;

    @GetMapping()
    public List<DanhGia> count() {
        return danhGiaService.findAll();
    }

    @PostMapping()
    public DanhGia save(DanhGia danhGia) {
        return danhGiaService.save(danhGia);
    }
}
