package com.fpoly.duantotnghiep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.DienDan;
import com.fpoly.duantotnghiep.jparepository.DienDanRepository;
import com.fpoly.duantotnghiep.service.DienDanService;

@Service
public class DienDanServiceImpl implements DienDanService {

    @Autowired
    DienDanRepository dienDanRepository;

    @Override
    public List<DienDan> findAll() {
        return dienDanRepository.findAll();
    }

    @Override
    public DienDan save(DienDan dienDan) {
        return dienDanRepository.save(dienDan);
    }

    @Override
    public DienDan deleteById(int id) {
        DienDan dienDan = dienDanRepository.findById(id).get();
        dienDanRepository.deleteById(id);
        return dienDan;
    }

    @Override
    public DienDan findById(int id) {
        return dienDanRepository.findById(id).get();
    }
}
