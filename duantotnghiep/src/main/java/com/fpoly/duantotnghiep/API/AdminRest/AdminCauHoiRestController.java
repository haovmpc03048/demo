package com.fpoly.duantotnghiep.API.AdminRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.CauHoi;
import com.fpoly.duantotnghiep.Entity.TaiLieuKhoaHoc;
import com.fpoly.duantotnghiep.service.CauHoiService;
import com.fpoly.duantotnghiep.service.TaiLieuService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/admin/CauHoi")
public class AdminCauHoiRestController {
    @Autowired
    CauHoiService cauHoiService;

    @GetMapping()
    public List<CauHoi> getAll() {
        return cauHoiService.findAll();
    }

    @PostMapping()
    public CauHoi save(@RequestBody CauHoi cauHoi) {
        return cauHoiService.save(cauHoi);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        cauHoiService.deleteById(id);
    }

}
