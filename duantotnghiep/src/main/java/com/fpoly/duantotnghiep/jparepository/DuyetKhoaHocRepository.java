package com.fpoly.duantotnghiep.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.duantotnghiep.Entity.DuyetKhoaHoc;

public interface DuyetKhoaHocRepository extends JpaRepository<DuyetKhoaHoc, Integer> {
    // You can add custom query methods here if needed
}
