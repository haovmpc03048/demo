package com.fpoly.duantotnghiep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.ThanhToan;
import com.fpoly.duantotnghiep.jparepository.ThanhToanRepository;
import com.fpoly.duantotnghiep.service.ThanhToanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThanhToanServiceImpl implements ThanhToanService {
    @Autowired
    private ThanhToanRepository thanhToanRepository;

    @Override
    public void save(ThanhToan thanhToan) {
        thanhToanRepository.save(thanhToan);
    }

    @Override
    public List<ThanhToan> findAllByNguoiDungId(int idNguoiDung) {
        return thanhToanRepository.findByNguoiDungId(idNguoiDung);
    }
}
