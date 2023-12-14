package com.fpoly.duantotnghiep.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.duantotnghiep.Entity.ChungChi;

public interface ChungChiRepository extends JpaRepository<ChungChi, Integer> {
    // You can add custom query methods here if needed
        List<ChungChi> findByKhoaHocId(int idKhoaHoc);

        List<ChungChi> findByNguoiDungId(int idNguoiDung);

        List<ChungChi> findByKhoaHocIdAndNguoiDungId(int idKhoaHoc, int idNguoiDung);

}
