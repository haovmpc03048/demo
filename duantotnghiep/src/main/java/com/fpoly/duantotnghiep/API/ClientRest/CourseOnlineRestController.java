package com.fpoly.duantotnghiep.API.ClientRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.CauHoi;
import com.fpoly.duantotnghiep.Entity.CourseOnlineResponse;
import com.fpoly.duantotnghiep.Entity.DangKyKhoaHoc;
import com.fpoly.duantotnghiep.Entity.KhoaHoc;
import com.fpoly.duantotnghiep.Entity.MucLuc;
import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.Entity.VideoKhoaHoc;
import com.fpoly.duantotnghiep.service.CauHoiService;
import com.fpoly.duantotnghiep.service.DangKyKhoaHocService;
import com.fpoly.duantotnghiep.service.KhoaHocService;
import com.fpoly.duantotnghiep.service.MucLucService;
import com.fpoly.duantotnghiep.service.VideoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/courseOnline")
public class CourseOnlineRestController {

    @Autowired
    private KhoaHocService daoHocService;

    @Autowired
    private MucLucService mucLucService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private CauHoiService cauHoiService;

    @Autowired
    private DangKyKhoaHocService dangKyKhoaHocService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<CourseOnlineResponse> getCourseOnline(@PathVariable("id") int id,HttpServletRequest request) {
        CourseOnlineResponse response = new CourseOnlineResponse();

        ArrayList<VideoKhoaHoc> listVideo = new ArrayList<>();
        ArrayList<CauHoi> listCauHoi = new ArrayList<>();
        KhoaHoc khoaHoc = daoHocService.findById(id);
        
     
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');
		DecimalFormat currencyFormatter = new DecimalFormat("###,###,### VND");
		
		String formattedTotalAmount = currencyFormatter.format(khoaHoc.getChiPhi()* 0.88);
		HttpSession session = request.getSession();
		session.setAttribute("donGiaFormat", formattedTotalAmount);
		
        List<MucLuc> list = mucLucService.findByKhoaHoc(id);
        for (MucLuc mucLuc : list) {
            List<VideoKhoaHoc> mucLucVideos = videoService.findByMucHocId(mucLuc.getId());
            listVideo.addAll(mucLucVideos);

            List<CauHoi> mucLucCauHoi = cauHoiService.findByMucLuc(mucLuc.getId());
            listCauHoi.addAll(mucLucCauHoi);

           
        }

        response.setVideoKhoaHoc(listVideo);
        response.setCourseOnline(khoaHoc);
        response.setMucLuc(list);
        response.setCauHois(listCauHoi);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public DangKyKhoaHoc AddTaiLieu(@RequestBody DangKyKhoaHoc entity) {
        return dangKyKhoaHocService.save(entity);
    }

    @GetMapping()
    public List<DangKyKhoaHoc> getAll() {
        return dangKyKhoaHocService.findAll();
    }

    @GetMapping("/check/{idNguoiDung}/{idKhoaHoc}")
    public DangKyKhoaHoc check(@PathVariable("idNguoiDung") int idNguoiDung, @PathVariable("idKhoaHoc") int idKhoaHoc) {
        return dangKyKhoaHocService.findByNguoiDungIdAndKhoaHocId(idNguoiDung, idKhoaHoc);
    }

}
