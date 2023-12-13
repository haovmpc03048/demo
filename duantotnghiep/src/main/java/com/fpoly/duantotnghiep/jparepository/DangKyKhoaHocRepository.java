package com.fpoly.duantotnghiep.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.duantotnghiep.Entity.DangKyKhoaHoc;

public interface DangKyKhoaHocRepository extends JpaRepository<DangKyKhoaHoc, Integer> {
    // You can add custom query methods here if needed

    DangKyKhoaHoc save(DangKyKhoaHoc entity);

    List<DangKyKhoaHoc> findAll();

    DangKyKhoaHoc findByNguoiDungIdAndKhoaHocId(int idNguoiDung, int idKhoaHoc);

    @Query("SELECT COUNT(d) FROM DangKyKhoaHoc d WHERE d.khoaHoc.id = :idKhoaHoc")
    long countByKhoaHocId(@Param("idKhoaHoc") Long idKhoaHoc);

    @Query(nativeQuery = true, value ="SELECT COUNT(*) FROM dbo.dang_ky_khoa_hoc d WHERE d.id_khoa_hoc = :idKhoaHoc AND d.ngay_dang_ky BETWEEN :batDau AND :ketThuc")
    long countByKhoaHocIdTG(long idKhoaHoc,String batDau, String ketThuc);

}
