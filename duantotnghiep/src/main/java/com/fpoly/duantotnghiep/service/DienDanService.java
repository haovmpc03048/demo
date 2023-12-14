package com.fpoly.duantotnghiep.service;

import java.util.List;

import com.fpoly.duantotnghiep.Entity.DienDan;

public interface DienDanService {
    List<DienDan> findAll();

    DienDan save(DienDan dienDan);

    DienDan deleteById(int id);

    DienDan findById(int id);
}
