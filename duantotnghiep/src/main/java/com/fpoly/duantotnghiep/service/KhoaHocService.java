package com.fpoly.duantotnghiep.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.duantotnghiep.Entity.KhoaHoc;
import com.fpoly.duantotnghiep.Entity.LoaiKhoaHoc;
import com.fpoly.duantotnghiep.Entity.NguoiDung;

public interface KhoaHocService {

	List<KhoaHoc> findByCategory(Integer categoryId);

	List<KhoaHoc> findAll();

	List<KhoaHoc> findDuyet();

	KhoaHoc findByKhoaHocId(Integer id);

	KhoaHoc findById(int id);

	List<String> findAllTenKhoaHoc();

	List<KhoaHoc> getKhoaHocByLoai(Integer loaiKhoaHoc);

	KhoaHoc create(KhoaHoc khoaHoc);

	void deleteById(Integer id);

	KhoaHoc update(KhoaHoc khoaHoc);

	List<String> findAllNameCategory();

	List<String> findAlIdCategory();

	List<LoaiKhoaHoc> findAllLoaiKhoaHoc();

	List<KhoaHoc> findByNguoiTao(NguoiDung nguoiDung);

	List<KhoaHoc> findByTenKhoaHoc(String tenKhoaHoc);
	List<KhoaHoc> findByTenKhoaHoc2(String tenKhoaHoc);
}
