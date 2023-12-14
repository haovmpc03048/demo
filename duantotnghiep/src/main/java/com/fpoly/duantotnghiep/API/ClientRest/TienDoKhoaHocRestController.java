package com.fpoly.duantotnghiep.API.ClientRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.DangKyKhoaHoc;
import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.jparepository.NguoiDungRepository;
import com.fpoly.duantotnghiep.service.DangKyKhoaHocService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/tiendokhoahoc")
public class TienDoKhoaHocRestController {
    @Autowired
    DangKyKhoaHocService dangKyKhoaHocService;

    @Autowired
    NguoiDungRepository dungRepository;

    @GetMapping("/{id}/{idkhoahoc}")
    public DangKyKhoaHoc getTienDoKhoaHoc(@PathVariable int id, @PathVariable int idkhoahoc) {
        System.out.println("id: " + id + " idkhoahoc: " + idkhoahoc);
        return dangKyKhoaHocService.findByNguoiDungIdAndKhoaHocId(id, idkhoahoc);
    }

    @PutMapping("/{id}/{idkhoahoc}/{idvideo}/{tienDo}")
    public DangKyKhoaHoc updateTienDoKhoaHoc(@PathVariable int id, @PathVariable int idkhoahoc,
            @PathVariable String idvideo, @PathVariable String tienDo) {
        DangKyKhoaHoc dangKyKhoaHoc = dangKyKhoaHocService.findByNguoiDungIdAndKhoaHocId(id, idkhoahoc);
        try {
            dangKyKhoaHoc.setTienDo(idvideo + "/" + tienDo);
            return dangKyKhoaHocService.save(dangKyKhoaHoc);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            return null;

        }
    }

    @PutMapping("/upload/{id}/{idkhoahoc}/{trangthai}")
    public DangKyKhoaHoc updateTienDoKhoaHoc2(@PathVariable int id, @PathVariable int idkhoahoc,
            @PathVariable String trangthai) {
        DangKyKhoaHoc dangKyKhoaHoc = dangKyKhoaHocService.findByNguoiDungIdAndKhoaHocId(id, idkhoahoc);
        System.out.println(dangKyKhoaHoc);
        try {
            dangKyKhoaHoc.setTrangThai(trangthai);
            return dangKyKhoaHocService.save(dangKyKhoaHoc);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    @GetMapping
    public List<DangKyKhoaHoc> findAll() {
        return dangKyKhoaHocService.findAll();
    }

    @GetMapping("/duyetnhasangtao")
    public List<NguoiDung> findAll1() {
        return dungRepository.findAll();
    }
}
