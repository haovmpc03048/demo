package com.fpoly.duantotnghiep.jparepository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.duantotnghiep.Entity.ThanhToan;

public interface ThanhToanRepository extends JpaRepository<ThanhToan, Integer> {
	List<ThanhToan> findByNguoiDungId(int idNguoiDung);

	List<ThanhToan> findByKhoaHocId(int idKhoaHoc);

	List<ThanhToan> findByNguoiDungIdAndKhoaHocId(int idNguoiDung, int idKhoaHoc);

	@Query(nativeQuery = true, value = "Select YEAR(thoi_gian) AS year, MONTH(thoi_gian) AS month, SUM(tong_tien) AS total_revenue  "+
            "from dbo.thanh_toan " +
            "WHERE thoi_gian BETWEEN DATEFROMPARTS(YEAR(GETDATE()), 1, 1) AND GETDATE() " +
            "GROUP BY YEAR(thoi_gian), MONTH(thoi_gian) " +
            "ORDER BY year, month")
    List<Object[]> fetchRevenueData();
    	@Query(nativeQuery = true, value = "Select YEAR(thoi_gian) AS year, MONTH(thoi_gian) AS month, SUM(tong_tien) AS total_revenue  "+
            "from dbo.thanh_toan " +
            "WHERE thoi_gian BETWEEN :batDau AND :ketThuc " +
            "GROUP BY YEAR(thoi_gian), MONTH(thoi_gian) " +
            "ORDER BY year, month")
    List<Object[]> ThongKeTheoThoiGian(String batDau, String ketThuc);

    @Query(nativeQuery = true, value = "Select YEAR(thoi_gian) AS year, MONTH(thoi_gian) AS month, SUM(tong_tien) AS total_revenue  "+
            "from dbo.thanh_toan " +
            "WHERE id_khoa_hoc = ?1 " +
            "GROUP BY YEAR(thoi_gian), MONTH(thoi_gian) " +
            "ORDER BY year, month")
    List<Object[]> ThongKeTheoKhoaHoc(int idKhoaHoc);

    @Query(nativeQuery = true, value = "Select YEAR(thoi_gian) AS year, MONTH(thoi_gian) AS month, SUM(tong_tien) AS total_revenue  "+
            "from dbo.thanh_toan " +
            "WHERE id_khoa_hoc = :idKhoaHoc AND thoi_gian BETWEEN :batDau AND :ketThuc " +
            "GROUP BY YEAR(thoi_gian), MONTH(thoi_gian) " +
            "ORDER BY year, month")
    List<Object[]> ThongKeTheoKhoaHocTG(int idKhoaHoc,String batDau, String ketThuc);

    @Query(nativeQuery = true, value = "Select YEAR(thoi_gian) AS year, MONTH(thoi_gian) AS month, SUM(tong_tien) AS total_revenue  "+
            "from dbo.thanh_toan " +
            "WHERE CONVERT(DATE, thoi_gian) = CONVERT(DATE, GETDATE()) " +
            "GROUP BY YEAR(thoi_gian), MONTH(thoi_gian) " +
            "ORDER BY year, month")
    List<Object[]> ThongKeTrongNgay();

      @Query(nativeQuery = true, value = "Select YEAR(thoi_gian) AS year, MONTH(thoi_gian) AS month, SUM(tong_tien) AS total_revenue  "+
            "from dbo.thanh_toan " +
            "WHERE MONTH(thoi_gian) = MONTH(GETDATE()) AND YEAR(thoi_gian) = YEAR(GETDATE()) " +
            "GROUP BY YEAR(thoi_gian), MONTH(thoi_gian) " +
            "ORDER BY year, month")
    List<Object[]> ThongKeThangNay();

    @Query(nativeQuery = true, value = "Select YEAR(thoi_gian) AS year, MONTH(thoi_gian) AS month, SUM(tong_tien) AS total_revenue  "+
            "from dbo.thanh_toan " +
            "WHERE YEAR(thoi_gian) = YEAR(GETDATE()) " +
            "GROUP BY YEAR(thoi_gian), MONTH(thoi_gian) " +
            "ORDER BY year, month")
    List<Object[]> ThongKeNamNay();

    
    @Query(nativeQuery = true, value = "Select YEAR(thoi_gian) AS year, MONTH(thoi_gian) AS month, SUM(tong_tien) AS total_revenue  "+
            "from dbo.thanh_toan " +
            "WHERE YEAR(thoi_gian) = YEAR(GETDATE()) - 1 " +
            "GROUP BY YEAR(thoi_gian), MONTH(thoi_gian) " +
            "ORDER BY year, month")
    List<Object[]> ThongKeNamTruoc();
}
