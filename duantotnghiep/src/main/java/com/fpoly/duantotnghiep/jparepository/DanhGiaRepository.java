package com.fpoly.duantotnghiep.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.duantotnghiep.Entity.DanhGia;

public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {
    // You can add custom query methods here if needed
}
