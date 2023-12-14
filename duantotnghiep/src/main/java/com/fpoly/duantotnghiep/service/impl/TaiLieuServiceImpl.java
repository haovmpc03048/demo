package com.fpoly.duantotnghiep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.TaiLieuKhoaHoc;
import com.fpoly.duantotnghiep.jparepository.TaiLieuKhoaHocRepository;
import com.fpoly.duantotnghiep.service.TaiLieuService;

@Service
public class TaiLieuServiceImpl implements TaiLieuService {
    @Autowired
    TaiLieuKhoaHocRepository taiLieuKhoaHocRepository;

    @Override
    public List<TaiLieuKhoaHoc> findAll() {
        return taiLieuKhoaHocRepository.findAll();
    }

    @Override
    public List<TaiLieuKhoaHoc> findByKhoaHocId(int idKhoaHoc) {
        return taiLieuKhoaHocRepository.findByKhoaHocId(idKhoaHoc);
    }

    @Override
    public TaiLieuKhoaHoc save(TaiLieuKhoaHoc entity) {
        return taiLieuKhoaHocRepository.save(entity);
    }

    @Override
    public void deleteById(int id) {
        taiLieuKhoaHocRepository.deleteById(id);
    }
}
