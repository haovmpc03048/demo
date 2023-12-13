package com.fpoly.duantotnghiep.service;

import java.util.Date;
import java.util.List;

import com.fpoly.duantotnghiep.Entity.ThongKeDATA;

public interface ThongKeService {
 List<ThongKeDATA> getRevenueDatas();

List<ThongKeDATA> getThongKeTheoThoiGian(String batDau, String ketThuc);

List<ThongKeDATA> getThongKeTheoKhoaHoc(int idKhoaHoc);
List<ThongKeDATA> getThongKeTheoKhoaHocTG(int idKhoaHoc,String batDau, String ketThuc);

long countUsersInKhoaHoc(Long idKhoaHoc);
long countUsersInKhoaHocTG(Long idKhoaHoc,String batDau, String ketThuc);

 List<ThongKeDATA> ThongKeTheoNgay();
 List<ThongKeDATA> ThongKeThangNay();
 List<ThongKeDATA> ThongKeNamNay();
 List<ThongKeDATA> ThongKeNamTruoc();
}
