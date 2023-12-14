package com.fpoly.duantotnghiep.service;

import java.util.List;

import com.fpoly.duantotnghiep.Entity.ChungChi;

public interface ChungChiService {
    List<ChungChi> findAll();

    List<ChungChi> findByKhoaHocId(int idKhoaHoc);

    List<ChungChi> findByNguoiDungId(int idNguoiDung);

    List<ChungChi> findByKhoaHocIdAndNguoiDungId(int idKhoaHoc, int idNguoiDung);

    ChungChi save(ChungChi chungChi);
}
