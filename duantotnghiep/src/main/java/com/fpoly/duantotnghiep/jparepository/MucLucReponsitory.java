package com.fpoly.duantotnghiep.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.duantotnghiep.Entity.MucLuc;

public interface MucLucReponsitory extends JpaRepository<MucLuc, Integer> {
    MucLuc findById(int id);

    @Query("SELECT ml FROM MucLuc ml JOIN ml.khoaHoc kh WHERE kh.id = :KhoaHocId")
    List<MucLuc> findByKhoaHoc(@Param("KhoaHocId") Integer KhoaHocId);

}
