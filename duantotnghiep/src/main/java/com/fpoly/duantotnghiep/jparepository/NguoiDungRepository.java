package com.fpoly.duantotnghiep.jparepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.duantotnghiep.Entity.NguoiDung;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {

	NguoiDung findByHoTen(String hoTen);

	NguoiDung findByTaiKhoan(String taiKhoan);

	NguoiDung findByEmail(String email);

	Optional<NguoiDung> findById(int id);

	Optional<NguoiDung> findByTaiKhoanAndMatKhau(String taiKhoan, String matKhau);

	boolean existsByTaiKhoan(String taiKhoan);

	boolean existsByEmail(String email);

}
