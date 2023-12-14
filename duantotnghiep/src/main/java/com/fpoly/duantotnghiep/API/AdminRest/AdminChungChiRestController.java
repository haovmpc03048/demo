package com.fpoly.duantotnghiep.API.AdminRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.CauHoi;
import com.fpoly.duantotnghiep.Entity.ChungChi;
import com.fpoly.duantotnghiep.service.ChungChiService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/admin/ChungChi")
public class AdminChungChiRestController {
    @Autowired
    ChungChiService chungChiService;

    @GetMapping()
    public List<ChungChi> getAll() {
        return chungChiService.findAll();
    }

    @GetMapping("/{idKhoaHoc}")
    public List<ChungChi> getByKhoaHoc(@PathVariable int idKhoaHoc) {
        return chungChiService.findByKhoaHocId(idKhoaHoc);
    }

    @GetMapping("/nguoidung/{idNguoiDung}")
    public List<ChungChi> getByNguoiDung(@PathVariable int idNguoiDung) {
        return chungChiService.findByNguoiDungId(idNguoiDung);
    }

    @GetMapping("/{idKhoaHoc}/{idNguoiDung}")
    public List<ChungChi> getByKhoaHocAndNguoiDung(@PathVariable int idKhoaHoc, @PathVariable int idNguoiDung) {
        return chungChiService.findByKhoaHocIdAndNguoiDungId(idKhoaHoc, idNguoiDung);
    }

}
