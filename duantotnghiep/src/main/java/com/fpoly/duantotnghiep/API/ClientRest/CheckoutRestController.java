package com.fpoly.duantotnghiep.API.ClientRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.duantotnghiep.Entity.ThanhToan;
import com.fpoly.duantotnghiep.jparepository.ThanhToanRepository;
import com.fpoly.duantotnghiep.service.ThanhToanService;

@RestController
@RequestMapping("/api/Checkout")
public class CheckoutRestController {
    @Autowired
    private ThanhToanService thanhToanService;
    @Autowired
    ThanhToanRepository thanhToanRepository;

    @GetMapping("/check/{idNguoiDung}/{idKhoaHoc}")
    public ThanhToan check(@PathVariable("idNguoiDung") int idNguoiDung, @PathVariable("idKhoaHoc") int idKhoaHoc) {
        List<ThanhToan> thanhToanList = thanhToanRepository.findByNguoiDungIdAndKhoaHocId(idNguoiDung, idKhoaHoc);

        // Lấy bản ghi đầu tiên từ danh sách hoặc trả về null nếu danh sách rỗng
        ThanhToan thanhToan = thanhToanList.isEmpty() ? null : thanhToanList.get(0);

        // hoặc sử dụng phương thức findFirst
        // ThanhToan thanhToan = thanhToanList.stream().findFirst().orElse(null);

        return thanhToan;
    }
}
