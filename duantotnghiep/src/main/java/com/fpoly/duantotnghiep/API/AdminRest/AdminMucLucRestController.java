package com.fpoly.duantotnghiep.API.AdminRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.MucLuc;
import com.fpoly.duantotnghiep.service.MucLucService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/admin/MucLuc")
public class AdminMucLucRestController {
    @Autowired
    MucLucService mucLucService;

    @GetMapping()
    public List<MucLuc> getAll() {
        return mucLucService.findAll();
    }

    @GetMapping("/KhoaHoc/{KhoaHocId}")
    public List<MucLuc> getAllByKhoaHoc(@PathVariable("KhoaHocId") Integer KhoaHocId) {
        return mucLucService.findByKhoaHoc(KhoaHocId);
    }

    @GetMapping("/{id}")
    public MucLuc getById(@PathVariable("id") int id) {
        return mucLucService.findById(id);
    }

    @PostMapping()
    public MucLuc save(@RequestBody MucLuc mucLuc) {
        return mucLucService.save(mucLuc);
    }

    @PutMapping("/{id}")
    public MucLuc update(@RequestBody MucLuc mucLuc) {
        return mucLucService.save(mucLuc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        mucLucService.deleteById(id);
    }
}
