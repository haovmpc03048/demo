package com.fpoly.duantotnghiep.service;



import java.util.List;

import com.fpoly.duantotnghiep.Entity.HocVienDTO;
import com.fpoly.duantotnghiep.Entity.KhoaHoc;


public interface HocVienService {
    List<HocVienDTO> layDanhSachHocVien();
	List<KhoaHoc> layDanhSachKhoaHoc();
	
}

