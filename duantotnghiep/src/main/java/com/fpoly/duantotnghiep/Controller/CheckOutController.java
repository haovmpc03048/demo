package com.fpoly.duantotnghiep.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.duantotnghiep.Entity.DangKyKhoaHoc;
import com.fpoly.duantotnghiep.Entity.KhoaHoc;
import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.Entity.ThanhToan;
import com.fpoly.duantotnghiep.config.VNPayService;
import com.fpoly.duantotnghiep.service.DangKyKhoaHocService;
import com.fpoly.duantotnghiep.service.PaypalService;
import com.fpoly.duantotnghiep.service.ThanhToanService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CheckOutController {
	@Autowired
	private VNPayService vnPayService;

	@Autowired
	private ThanhToanService thanhToanService;

	@Autowired
	DangKyKhoaHocService dangKyKhoaHocService;

	@Autowired
	PaypalService service;

	public static final String SUCCESS_URL = "success";
	public static final String CANCEL_URL = "cancel";

	@GetMapping("/courseOnline/CheckOut")
	public String CheckOut() {
		return "checkout";
	}

	@GetMapping("/courseOnline/ordersuccess2")
	public String ordersuccess2() {
		return "ordersuccess2";
	}
	@PostMapping("/courseOnline/submitOrder")
	public String submidOrder(@RequestParam("amount") int orderTotal, @RequestParam("tenNguoiDung") String orderInfo,
			HttpServletRequest request, @CookieValue(value = "username", defaultValue = "0") String userIdCookie,
			@RequestParam("paymentMethod") String paymentMenThod, @RequestParam("idKhoaHoc") String idKhoaHoc,
			@RequestParam("idNguoiDung") String idNguoiDung, @ModelAttribute("order") ThanhToan order) throws UnsupportedEncodingException {

		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);

		HttpSession session = request.getSession();

		if (paymentMenThod.equals("paypal")) {
			try {
				Payment payment = service.createPayment((double) orderTotal, "USD", "paypal",
						"sale", order.getDescription(), "http://localhost:8080/" + CANCEL_URL,
						"http://localhost:8080/" + SUCCESS_URL, 0.0000412414);
				for (Links link : payment.getLinks()) {
					if (link.getRel().equals("approval_url")) {
						session.setAttribute("idNguoiDung", idNguoiDung);
						session.setAttribute("idKhoaHoc", idKhoaHoc);
						session.setAttribute("totalprice", orderTotal);
						return "redirect:" + link.getHref();
					}
				}

			} catch (PayPalRESTException e) {

				e.printStackTrace();
			}
			return "redirect:/";
		} else {
			session.setAttribute("idNguoiDung", idNguoiDung);
			session.setAttribute("idKhoaHoc", idKhoaHoc);
			session.setAttribute("totalprice", orderTotal);
			return "redirect:" + vnpayUrl;
		}

	}
	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "cancel";
	}

	@GetMapping(value = SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
			HttpServletRequest request, Model model) {
		try {
			Payment payment = service.executePayment(paymentId, payerId);

			if (payment.getState().equals("approved")) {

				HttpSession session = request.getSession();

				Integer idNguoiDung = Integer.parseInt(session.getAttribute("idNguoiDung").toString());
				Integer idKhoaHoc = Integer.parseInt(session.getAttribute("idKhoaHoc").toString());
				Double total = Double.parseDouble(session.getAttribute("totalprice").toString());

				ThanhToan thanhToan = new ThanhToan();

				// Chuyển đối tượng NguoiDung từ idNguoiDung
				NguoiDung nguoiDung = new NguoiDung();
				nguoiDung.setId(idNguoiDung);
				thanhToan.setNguoiDung(nguoiDung);

				// Chuyển đối tượng KhoaHoc từ idKhoaHoc
				KhoaHoc khoaHoc = new KhoaHoc();
				khoaHoc.setId(idKhoaHoc);
				thanhToan.setKhoaHoc(khoaHoc);
				thanhToan.setTongTien(total);
				thanhToan.setThoiGian(new Date());
				thanhToan.setTrangThai(true);
				thanhToan.setLoaiThanhToan("paypal");
				thanhToanService.save(thanhToan);

				DangKyKhoaHoc dangKyKhoaHoc = new DangKyKhoaHoc();
				dangKyKhoaHoc.setKhoaHoc(khoaHoc);
				dangKyKhoaHoc.setNguoiDung(nguoiDung);
				dangKyKhoaHoc.setNgayDangKy(new Date());
				dangKyKhoaHoc.setTienDo(String.valueOf(0));
				dangKyKhoaHoc.setTrangThai("Đang học");
				dangKyKhoaHocService.save(dangKyKhoaHoc);
				return "success";
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/courseOnline/index";
	}

	@GetMapping("/vnpay-payment")
	public String getVnpayPayment(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		int paymentStatus = vnPayService.orderReturn(request);
		HttpSession session = request.getSession();

		Integer idNguoiDung = Integer.parseInt(session.getAttribute("idNguoiDung").toString());
		Integer idKhoaHoc = Integer.parseInt(session.getAttribute("idKhoaHoc").toString());
		Double total = Double.parseDouble(session.getAttribute("totalprice").toString());

		String paymentTimeString = request.getParameter("vnp_PayDate");
		String Txnref = request.getParameter("vnp_TxnRef");
		String totalPrice = request.getParameter("vnp_Amount");
		String orderInfo = URLDecoder.decode(request.getParameter("vnp_OrderInfo"), StandardCharsets.UTF_8.toString());


		double totalAmount = Double.parseDouble(String.valueOf(Double.valueOf(totalPrice) / 100));
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');
		DecimalFormat currencyFormatter = new DecimalFormat("###,###,### VND");

		String formattedTotalAmount = currencyFormatter.format(totalAmount);

		// Chuyển đổi chuỗi thời gian sang đối tượng LocalDateTime
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime paymentTime = LocalDateTime.parse(paymentTimeString, inputFormatter);

		// Định dạng thời gian sang "dd/MM/yyyy HH:mm:ss"
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String formattedPaymentTime = paymentTime.format(outputFormatter);

		if (paymentStatus == 1) {
			ThanhToan thanhToan = new ThanhToan();

			// Chuyển đối tượng NguoiDung từ idNguoiDung
			NguoiDung nguoiDung = new NguoiDung();
			nguoiDung.setId(idNguoiDung);
			thanhToan.setNguoiDung(nguoiDung);

			// Chuyển đối tượng KhoaHoc từ idKhoaHoc
			KhoaHoc khoaHoc = new KhoaHoc();
			khoaHoc.setId(idKhoaHoc);
			thanhToan.setKhoaHoc(khoaHoc);
			thanhToan.setTongTien(total);
			thanhToan.setThoiGian(new Date());
			thanhToan.setTrangThai(true);
			thanhToan.setLoaiThanhToan("vnpay");
			thanhToanService.save(thanhToan);

			DangKyKhoaHoc dangKyKhoaHoc = new DangKyKhoaHoc();
			dangKyKhoaHoc.setKhoaHoc(khoaHoc);
			dangKyKhoaHoc.setNguoiDung(nguoiDung);
			dangKyKhoaHoc.setNgayDangKy(new Date());
			dangKyKhoaHoc.setTienDo(String.valueOf(0));
			dangKyKhoaHoc.setTrangThai("Đang học");
			dangKyKhoaHocService.save(dangKyKhoaHoc);
		}

		model.addAttribute("orderId", orderInfo);
		model.addAttribute("totalPrice", formattedTotalAmount);
		model.addAttribute("paymentTime", formattedPaymentTime);
		model.addAttribute("Txnref", Txnref);

		return paymentStatus == 1 ? "ordersuccess" : "orderfail";
	}

}
