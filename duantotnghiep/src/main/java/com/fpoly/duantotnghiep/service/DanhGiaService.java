package com.fpoly.duantotnghiep.service;

import java.util.List;

import com.fpoly.duantotnghiep.Entity.DanhGia;

public interface DanhGiaService {
    List<DanhGia> findAll();

    DanhGia save(DanhGia danhGia);

}
