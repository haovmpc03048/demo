package com.fpoly.duantotnghiep.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.duantotnghiep.Entity.BinhLuan;

public interface BinhLuanRepository extends JpaRepository<BinhLuan, Integer> {
    // You can add custom query methods here if needed
    List<BinhLuan> findAll();

    List<BinhLuan> findByBaiDangId(int id);

}
