package com.fpoly.duantotnghiep.service;

import java.util.List;

import com.fpoly.duantotnghiep.Entity.BinhLuan;

public interface BinhLuanService {
    List<BinhLuan> findAll();

    List<BinhLuan> findByBaiDangId(int id);

    BinhLuan deleteById(int id);

    BinhLuan save(BinhLuan binhLuan);
}
