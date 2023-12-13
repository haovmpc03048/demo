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

import com.fpoly.duantotnghiep.Entity.DienDan;
import com.fpoly.duantotnghiep.service.DienDanService;

@RestController
@RequestMapping("/api/diendan")
public class DienDanRestController {
    @Autowired
    DienDanService dienDanService;

    @GetMapping
    public List<DienDan> findAll() {
        return dienDanService.findAll();
    }

    @GetMapping("/{id}")
    public DienDan findById(@PathVariable int id) {
        return dienDanService.findById(id);
    }

    @PostMapping
    public DienDan save(@RequestBody DienDan dienDan) {
        return dienDanService.save(dienDan);
    }

    @DeleteMapping("/{id}")
    public DienDan deleteById(@PathVariable int id) {
        return dienDanService.deleteById(id);
    }

}
