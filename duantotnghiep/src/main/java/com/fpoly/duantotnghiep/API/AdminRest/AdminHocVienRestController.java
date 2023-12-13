package com.fpoly.duantotnghiep.API.AdminRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.HocVienDTO;
import com.fpoly.duantotnghiep.service.HocVienService;

@RestController
@RequestMapping("/rest/admin/HocVien")
public class AdminHocVienRestController {

    @Autowired
    private HocVienService hocVienService;

    @GetMapping("/*")
    public List<HocVienDTO> danhSachHocVien() {
        List<HocVienDTO> danhSach = hocVienService.layDanhSachHocVien();
        return danhSach;
    }
}