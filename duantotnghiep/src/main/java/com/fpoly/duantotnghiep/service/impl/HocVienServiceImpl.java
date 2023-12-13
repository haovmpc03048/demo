package com.fpoly.duantotnghiep.service.impl;

import com.fpoly.duantotnghiep.Entity.DangKyKhoaHoc;
import com.fpoly.duantotnghiep.Entity.HocVienDTO;
import com.fpoly.duantotnghiep.Entity.KhoaHoc;
import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.jparepository.DangKyKhoaHocRepository;
import com.fpoly.duantotnghiep.jparepository.HocVienRepository;
import com.fpoly.duantotnghiep.jparepository.KhoaHocRepository;
import com.fpoly.duantotnghiep.service.HocVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class HocVienServiceImpl implements HocVienService {
	private final HocVienRepository hocVienRepository;
	private final DangKyKhoaHocRepository dangKyKhoaHocRepository;
	private final KhoaHocRepository khoaHocRepository;

	@Autowired
	public HocVienServiceImpl(HocVienRepository hocVienRepository, DangKyKhoaHocRepository dangKyKhoaHocRepository,
			KhoaHocRepository khoaHocRepository) {
		this.hocVienRepository = hocVienRepository;
		this.dangKyKhoaHocRepository = dangKyKhoaHocRepository;
		this.khoaHocRepository = khoaHocRepository;
	}

	@Override
	public List<HocVienDTO> layDanhSachHocVien() {
		List<HocVienDTO> hocVienList = new ArrayList<>();

		List<DangKyKhoaHoc> dangKyKhoaHocList = dangKyKhoaHocRepository.findAll();

		for (DangKyKhoaHoc dangKyKhoaHoc : dangKyKhoaHocList) {
			HocVienDTO hocVienDTO = new HocVienDTO();
			hocVienDTO.setId(dangKyKhoaHoc.getId());
			hocVienDTO.setIdNguoiDung(dangKyKhoaHoc.getNguoiDung().getId());
			hocVienDTO.setTrangThai(dangKyKhoaHoc.getTrangThai());
			// Tìm thông tin người dùng
			NguoiDung nguoiDung = dangKyKhoaHoc.getNguoiDung();
			if (nguoiDung != null) {
				hocVienDTO.setHoTen(nguoiDung.getHoTen());
				hocVienDTO.setSoDienThoai(nguoiDung.getSoDienThoai());
				hocVienDTO.setChucVu(nguoiDung.getChucVu());
			}

			// Tìm thông tin khóa học
			KhoaHoc khoaHoc = dangKyKhoaHoc.getKhoaHoc();
			if (khoaHoc != null) {
				hocVienDTO.setId(khoaHoc.getId());
				hocVienDTO.setTenKhoaHoc(khoaHoc.getTenKhoaHoc());
				hocVienDTO.setChiPhi(khoaHoc.getChiPhi());
			}

			hocVienList.add(hocVienDTO);
		}

		return hocVienList;
	}

	@Override
	public List<KhoaHoc> layDanhSachKhoaHoc() {
		// TODO Auto-generated method stub
		return null;
	}
}
