package com.fpoly.duantotnghiep.API.ClientRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.BinhLuan;
import com.fpoly.duantotnghiep.service.BinhLuanService;

@RestController
@RequestMapping("/api/binhluan")
public class BinhLuanRestController {
    @Autowired
    BinhLuanService binhLuanService;

    @GetMapping
    public List<BinhLuan> findAll() {
        return binhLuanService.findAll();
    }

    @GetMapping("/{id}")
    public List<BinhLuan> findByBaiDangId(@PathVariable int id) {
        return binhLuanService.findByBaiDangId(id);
    }

    @DeleteMapping("/{id}")
    public BinhLuan deleteById(@PathVariable int id) {
        return binhLuanService.deleteById(id);
    }

    @PostMapping
    public BinhLuan save(@RequestBody BinhLuan binhLuan) {
        return binhLuanService.save(binhLuan);
    }
}
