package com.fpoly.duantotnghiep.jparepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.duantotnghiep.Entity.CauHoi;

public interface CauHoiRepository extends JpaRepository<CauHoi, Integer> {
    // You can add custom query methods here if needed
    @Query("SELECT ch FROM CauHoi ch JOIN ch.mucLuc ml WHERE ml.id = :mucLucId")
    ArrayList<CauHoi> findByMucLuc(Integer mucLucId);

    @Query("SELECT ch FROM CauHoi ch JOIN ch.mucLuc ml JOIN ml.khoaHoc kh WHERE kh.id = :khoaHocId")
    ArrayList<CauHoi> findByKhoaHocId(int khoaHocId);

}
