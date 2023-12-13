package com.fpoly.duantotnghiep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.duantotnghiep.Entity.KhoaHoc;
import com.fpoly.duantotnghiep.Entity.LoaiKhoaHoc;
import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.jparepository.KhoaHocRepository;
import com.fpoly.duantotnghiep.jparepository.LoaiKhoaHocRepository;
import com.fpoly.duantotnghiep.service.KhoaHocService;

@Service
public class KhoaHocServiceImpl implements KhoaHocService {
	@Autowired
	KhoaHocRepository khoaHocRepository;

	@Autowired
	LoaiKhoaHocRepository loaiKhoaHocRepository;

	@Override
	public List<KhoaHoc> findAll() {
		return khoaHocRepository.findAll();
	}

	@Override
	public List<KhoaHoc> findDuyet() {
		return khoaHocRepository.findByDuyet();
	}

	@Override
	public KhoaHoc findByKhoaHocId(Integer id) {
		// TODO Auto-generated method stub
		return khoaHocRepository.findById(id).get();
	}
	@Override
	public List<KhoaHoc> findByTenKhoaHoc(String tenKhoaHoc) {
		// TODO Auto-generated method stub
		return khoaHocRepository.findByTenKhoaHoc(tenKhoaHoc);
	}
	@Override
	public List<KhoaHoc> findByTenKhoaHoc2(String tenKhoaHoc) {
		// TODO Auto-generated method stub
		return khoaHocRepository.findByTenKhoaHoc2(tenKhoaHoc);
	}

	@Override
	public KhoaHoc findById(int id) {
		// TODO Auto-generated method stub
		return khoaHocRepository.findById(id);
	}

	@Override
	public List<KhoaHoc> getKhoaHocByLoai(Integer loaiKhoaHoc) {
		return khoaHocRepository.findByLoaiKhoaHocId(loaiKhoaHoc);
	}

	@Override
	public List<String> findAllTenKhoaHoc() {
		return khoaHocRepository.findAllTenKhoaHoc();
	}

	@Override
	public KhoaHoc create(KhoaHoc khoaHoc) {
		return khoaHocRepository.save(khoaHoc);
	}

	@Override
	public void deleteById(Integer id) {
		khoaHocRepository.deleteById(id);
	}

	@Override
	public KhoaHoc update(KhoaHoc khoaHoc) {
		// Kiểm tra xem có tồn tại trong cơ sở dữ liệu không
		KhoaHoc existingBrand = khoaHocRepository.findById(khoaHoc.getId());
		if (existingBrand != null) {
			existingBrand.setTenKhoaHoc(khoaHoc.getTenKhoaHoc());
			existingBrand.setChiPhi(khoaHoc.getChiPhi());
			existingBrand.setMoTa(khoaHoc.getMoTa());
			if (khoaHoc.getHinhAnh() != null) {
				existingBrand.setHinhAnh(khoaHoc.getHinhAnh());
			}
			if (khoaHoc.isDuyet() != existingBrand.isDuyet()) {
				existingBrand.setDuyet(khoaHoc.isDuyet());
			}
			if (khoaHoc.getTrangThai() != existingBrand.getTrangThai()) {
				existingBrand.setTrangThai(khoaHoc.getTrangThai());
			}
			if (khoaHoc.getLoaiKhoaHoc() != existingBrand.getLoaiKhoaHoc()) {
				existingBrand.setLoaiKhoaHoc(khoaHoc.getLoaiKhoaHoc());
			}
			return khoaHocRepository.save(existingBrand);
		} else {
			// Nếu không tồn tại, không thực hiện cập nhật và trả về null hoặc thông báo lỗi
			// tùy vào logic ứng dụng của bạn
			return null;
		}
	}

	@Override
	public List<KhoaHoc> findByCategory(Integer categoryId) {
		// Triển khai logic tìm kiếm theo danh mục ở đây
		return khoaHocRepository.findByCategory(categoryId);
	}

	@Override
	public List<String> findAllNameCategory() {
		// TODO Auto-generated method stub
		return khoaHocRepository.findAllNameCategory();
	}

	@Override
	public List<String> findAlIdCategory() {
		// TODO Auto-generated method stub
		return khoaHocRepository.findAllIdCategory();
	}

	@Override
	public List<LoaiKhoaHoc> findAllLoaiKhoaHoc() {
		return loaiKhoaHocRepository.findAll();
	}

	@Override
	public List<KhoaHoc> findByNguoiTao(NguoiDung nguoiDung) {
		return khoaHocRepository.findByNguoiDung(nguoiDung.getId());

	}
}
