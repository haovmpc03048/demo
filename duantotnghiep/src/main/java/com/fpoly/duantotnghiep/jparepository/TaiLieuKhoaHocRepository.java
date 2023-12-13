package com.fpoly.duantotnghiep.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.duantotnghiep.Entity.TaiLieuKhoaHoc;

public interface TaiLieuKhoaHocRepository extends JpaRepository<TaiLieuKhoaHoc, Integer> {
    // You can add custom query methods here if needed
    List<TaiLieuKhoaHoc> findByKhoaHocId(int idKhoaHoc);

}
