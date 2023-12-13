package com.fpoly.duantotnghiep.service;

import java.util.ArrayList;
import java.util.List;

import com.fpoly.duantotnghiep.Entity.CauHoi;

public interface CauHoiService {
    List<CauHoi> findAll();

    CauHoi save(CauHoi cauHoi);

    void deleteById(int id);

    ArrayList<CauHoi> findByMucLuc(int mucLucId);

    ArrayList<CauHoi> findByKhoaHocId(int khoaHocId);

    
}
