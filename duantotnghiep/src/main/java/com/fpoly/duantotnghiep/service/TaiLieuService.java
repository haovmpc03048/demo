package com.fpoly.duantotnghiep.service;

import java.util.List;

import com.fpoly.duantotnghiep.Entity.TaiLieuKhoaHoc;

public interface TaiLieuService {
    List<TaiLieuKhoaHoc> findAll();

    List<TaiLieuKhoaHoc> findByKhoaHocId(int idKhoaHoc);

    TaiLieuKhoaHoc save(TaiLieuKhoaHoc entity);

    void deleteById(int id);
}
