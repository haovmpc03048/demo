package com.fpoly.duantotnghiep.API.ClientRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.CauHoi;
import com.fpoly.duantotnghiep.Entity.TaiLieuKhoaHoc;
import com.fpoly.duantotnghiep.service.CauHoiService;
import com.fpoly.duantotnghiep.service.TaiLieuService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/tracnghiem")
public class TracnghiemRestController {
    @Autowired
    CauHoiService cauHoiService;

    @GetMapping()
    public List<CauHoi> getAll() {
        return cauHoiService.findAll();
    }

    @GetMapping("/{idKhoaHoc}")
    public List<CauHoi> getById(@PathVariable("idKhoaHoc") Integer id) {
        return cauHoiService.findByKhoaHocId(id);
    }

}

