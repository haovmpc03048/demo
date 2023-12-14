package com.fpoly.duantotnghiep.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.duantotnghiep.Entity.DienDan;

public interface DienDanRepository extends JpaRepository<DienDan, Integer> {
    // You can add custom query methods here if needed
    List<DienDan> findAll();
}
