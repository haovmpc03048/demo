package com.fpoly.duantotnghiep.API.AdminRest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.ThongKeDATA;
import com.fpoly.duantotnghiep.service.ThongKeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/Report")
public class ThongKeRestController {
    @Autowired
    ThongKeService thongKeService;

      @GetMapping()
    public List<ThongKeDATA> getRevenueData() {
        return thongKeService.getRevenueDatas();
    }

    @GetMapping("/thong-ke-thoi-gian")
    public List<ThongKeDATA> getThongKeTheoThoiGian(
            @RequestParam(name = "batDau", required = false) String batDau,
            @RequestParam(name = "ketThuc", required = false) String ketThuc) {
        return thongKeService.getThongKeTheoThoiGian(batDau, ketThuc);
    }

    @GetMapping("/thong-ke-Khoa-hoc")
    public List<ThongKeDATA> getThongKeTheoKhoaHoc(
            @RequestParam(name = "idKhoaHoc", required = false) int idKhoaHoc) {
        return thongKeService.getThongKeTheoKhoaHoc(idKhoaHoc);
    }
    @GetMapping("/thong-ke-Khoa-hoc-TG")
    public List<ThongKeDATA> getThongKeTheoKhoaHocTG(
            @RequestParam(name = "idKhoaHoc", required = false) int idKhoaHoc,
            @RequestParam(name = "batDau", required = false) String batDau,
            @RequestParam(name = "ketThuc", required = false) String ketThuc) {
        return thongKeService.getThongKeTheoKhoaHocTG(idKhoaHoc, batDau, ketThuc);
    }

     @GetMapping("/thong-ke-nguoi-hoc")
    public long getThongKeNguoiHoc(
            @RequestParam(name = "idKhoaHoc", required = false) long idKhoaHoc) {
        return thongKeService.countUsersInKhoaHoc(idKhoaHoc);
    }
    @GetMapping("/thong-ke-nguoi-hoc-theo-moc-thoi-gian")
    public long getThongKeNguoiHocTheoMocThoiGian(
            @RequestParam(name = "idKhoaHoc", required = false) long idKhoaHoc,
            @RequestParam(name = "batDau", required = false) String batDau,
            @RequestParam(name = "ketThuc", required = false) String ketThuc) {
        return thongKeService.countUsersInKhoaHocTG(idKhoaHoc, batDau, ketThuc);
    }

     @GetMapping("/thong-ke-theo-ngay")
    public List<ThongKeDATA> ThongKeTheoNgay() {
        return thongKeService.ThongKeTheoNgay();
    }

    @GetMapping("/thong-ke-thang-nay")
    public List<ThongKeDATA> ThongKeThangnay() {
        return thongKeService.ThongKeThangNay();
    }

    @GetMapping("/thong-ke-nam-nay")
    public List<ThongKeDATA> ThongKeNamNay() {
        return thongKeService.ThongKeNamNay();
    }

    @GetMapping("/thong-ke-nam-truoc")
    public List<ThongKeDATA> ThongKeNamTruoc() {
        return thongKeService.ThongKeNamTruoc();
    }
}
