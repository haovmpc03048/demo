package com.fpoly.duantotnghiep.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.ThanhToan;


public interface ThanhToanService {
	void save(ThanhToan thanhToan);
	 List<ThanhToan> findAllByNguoiDungId(int idNguoiDung);
}
