package com.fpoly.duantotnghiep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.DangKyKhoaHoc;
import com.fpoly.duantotnghiep.jparepository.DangKyKhoaHocRepository;
import com.fpoly.duantotnghiep.service.DangKyKhoaHocService;

@Service
public class DangKyKhoaHocImpl implements DangKyKhoaHocService {
    @Autowired
    DangKyKhoaHocRepository dangKyKhoaHocRepository;

    @Override
    public DangKyKhoaHoc save(DangKyKhoaHoc entity) {
        return dangKyKhoaHocRepository.save(entity);
    }

    @Override
    public List<DangKyKhoaHoc> findAll() {
        return dangKyKhoaHocRepository.findAll();
    }

    @Override
    public DangKyKhoaHoc findByNguoiDungIdAndKhoaHocId(int idNguoiDung, int idKhoaHoc) {
        return dangKyKhoaHocRepository.findByNguoiDungIdAndKhoaHocId(idNguoiDung, idKhoaHoc);
    }
}
