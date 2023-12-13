package com.fpoly.duantotnghiep.service;

import java.util.List;

import com.fpoly.duantotnghiep.Entity.DangKyKhoaHoc;

public interface DangKyKhoaHocService {
    DangKyKhoaHoc save(DangKyKhoaHoc entity);

    List<DangKyKhoaHoc> findAll();

    DangKyKhoaHoc findByNguoiDungIdAndKhoaHocId(int idNguoiDung, int idKhoaHoc);
}
