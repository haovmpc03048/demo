package com.fpoly.duantotnghiep.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.duantotnghiep.Entity.ThongKeDATA;
import com.fpoly.duantotnghiep.jparepository.DangKyKhoaHocRepository;
import com.fpoly.duantotnghiep.jparepository.ThanhToanRepository;
import com.fpoly.duantotnghiep.service.ThongKeService;

@Service
public class ThongKeImpl implements ThongKeService {
@Autowired
ThanhToanRepository thanhToanRepository;

    @Override
    public List<ThongKeDATA> getRevenueDatas() {
        List<Object[]> result = thanhToanRepository.fetchRevenueData();
        List<ThongKeDATA> revenueDataList = new ArrayList<>();

        for (Object[] row : result) {
            ThongKeDATA revenueData = new ThongKeDATA();
            revenueData.setYear((int) row[0]);
            revenueData.setMonth((int) row[1]);
            revenueData.setTotalRevenue((BigDecimal) row[2]);

            revenueDataList.add(revenueData);
        }

        return revenueDataList;
    }

    @Override
    public List<ThongKeDATA> getThongKeTheoThoiGian(String batDau, String ketThuc) {
        List<Object[]> result = thanhToanRepository.ThongKeTheoThoiGian(batDau, ketThuc);
        List<ThongKeDATA> revenueDataList = new ArrayList<>();

        for (Object[] row : result) {
            ThongKeDATA revenueData = new ThongKeDATA();
            revenueData.setYear((int) row[0]);
            revenueData.setMonth((int) row[1]);
            revenueData.setTotalRevenue((BigDecimal) row[2]);

            revenueDataList.add(revenueData);
        }

        return revenueDataList;
    }

     @Override
    public List<ThongKeDATA> getThongKeTheoKhoaHoc(int idKhoaHoc) {
        List<Object[]> result = thanhToanRepository.ThongKeTheoKhoaHoc(idKhoaHoc);
        List<ThongKeDATA> revenueDataList = new ArrayList<>();

        for (Object[] row : result) {
            ThongKeDATA revenueData = new ThongKeDATA();
            revenueData.setYear((int) row[0]);
            revenueData.setMonth((int) row[1]);
            revenueData.setTotalRevenue((BigDecimal) row[2]);

            revenueDataList.add(revenueData);
        }

        return revenueDataList;
    }

    @Override
    public List<ThongKeDATA> getThongKeTheoKhoaHocTG(int idKhoaHoc,String batDau, String ketThuc) {
        List<Object[]> result = thanhToanRepository.ThongKeTheoKhoaHocTG(idKhoaHoc, batDau, ketThuc);
        List<ThongKeDATA> revenueDataList = new ArrayList<>();

        for (Object[] row : result) {
            ThongKeDATA revenueData = new ThongKeDATA();
            revenueData.setYear((int) row[0]);
            revenueData.setMonth((int) row[1]);
            revenueData.setTotalRevenue((BigDecimal) row[2]);

            revenueDataList.add(revenueData);
        }

        return revenueDataList;
    }
    @Autowired
    private DangKyKhoaHocRepository dangKyKhoaHocRepository;
    @Override
    public long countUsersInKhoaHoc(Long idKhoaHoc) {
        return dangKyKhoaHocRepository.countByKhoaHocId(idKhoaHoc);
    }
    @Override
    public long countUsersInKhoaHocTG(Long idKhoaHoc,String batDau, String ketThuc) {
        return dangKyKhoaHocRepository.countByKhoaHocIdTG(idKhoaHoc, batDau, ketThuc);
    }

    @Override
    public List<ThongKeDATA> ThongKeTheoNgay() {
        List<Object[]> result = thanhToanRepository.ThongKeTrongNgay();
        List<ThongKeDATA> revenueDataList = new ArrayList<>();

        for (Object[] row : result) {
            ThongKeDATA revenueData = new ThongKeDATA();
            revenueData.setYear((int) row[0]);
            revenueData.setMonth((int) row[1]);
            revenueData.setTotalRevenue((BigDecimal) row[2]);

            revenueDataList.add(revenueData);
        }

        return revenueDataList;
    }

    @Override
    public List<ThongKeDATA> ThongKeThangNay() {
        List<Object[]> result = thanhToanRepository.ThongKeThangNay();
        List<ThongKeDATA> revenueDataList = new ArrayList<>();

        for (Object[] row : result) {
            ThongKeDATA revenueData = new ThongKeDATA();
            revenueData.setYear((int) row[0]);
            revenueData.setMonth((int) row[1]);
            revenueData.setTotalRevenue((BigDecimal) row[2]);

            revenueDataList.add(revenueData);
        }

        return revenueDataList;
    }

    @Override
    public List<ThongKeDATA> ThongKeNamNay() {
        List<Object[]> result = thanhToanRepository.ThongKeNamNay();
        List<ThongKeDATA> revenueDataList = new ArrayList<>();

        for (Object[] row : result) {
            ThongKeDATA revenueData = new ThongKeDATA();
            revenueData.setYear((int) row[0]);
            revenueData.setMonth((int) row[1]);
            revenueData.setTotalRevenue((BigDecimal) row[2]);

            revenueDataList.add(revenueData);
        }

        return revenueDataList;
    }

     @Override
    public List<ThongKeDATA> ThongKeNamTruoc() {
        List<Object[]> result = thanhToanRepository.ThongKeNamTruoc();
        List<ThongKeDATA> revenueDataList = new ArrayList<>();

        for (Object[] row : result) {
            ThongKeDATA revenueData = new ThongKeDATA();
            revenueData.setYear((int) row[0]);
            revenueData.setMonth((int) row[1]);
            revenueData.setTotalRevenue((BigDecimal) row[2]);

            revenueDataList.add(revenueData);
        }

        return revenueDataList;
    }
}
