package com.fpoly.duantotnghiep.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.CauHoi;
import com.fpoly.duantotnghiep.Entity.TaiLieuKhoaHoc;
import com.fpoly.duantotnghiep.jparepository.CauHoiRepository;
import com.fpoly.duantotnghiep.jparepository.TaiLieuKhoaHocRepository;
import com.fpoly.duantotnghiep.service.CauHoiService;
import com.fpoly.duantotnghiep.service.TaiLieuService;

@Service
public class CauHoiServiceImpl implements CauHoiService {
    @Autowired
    CauHoiRepository cauHoiRepository;

    @Override
    public List<CauHoi> findAll() {
        return cauHoiRepository.findAll();
    }

    @Override
    public CauHoi save(CauHoi cauHoi) {
        return cauHoiRepository.save(cauHoi);
    }

    @Override
    public void deleteById(int id) {
        cauHoiRepository.deleteById(id);
    }

    @Override
    public ArrayList<CauHoi> findByMucLuc(int mucLucId) {
        return cauHoiRepository.findByMucLuc(mucLucId);
    }

    @Override
    public ArrayList<CauHoi> findByKhoaHocId(int khoaHocId) {
        return cauHoiRepository.findByKhoaHocId(khoaHocId);
    }
    
}
