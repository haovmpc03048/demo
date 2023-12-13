package com.fpoly.duantotnghiep.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.duantotnghiep.Entity.CauHoi;
import com.fpoly.duantotnghiep.Entity.ChungChi;
import com.fpoly.duantotnghiep.Entity.DangKyKhoaHoc;
import com.fpoly.duantotnghiep.Entity.DanhGia;
import com.fpoly.duantotnghiep.Entity.KhoaHoc;
import com.fpoly.duantotnghiep.Entity.LoaiKhoaHoc;
import com.fpoly.duantotnghiep.Entity.MucLuc;
import com.fpoly.duantotnghiep.Entity.VideoKhoaHoc;
import com.fpoly.duantotnghiep.jparepository.LoaiKhoaHocRepository;
import com.fpoly.duantotnghiep.service.CauHoiService;
import com.fpoly.duantotnghiep.service.ChungChiService;
import com.fpoly.duantotnghiep.service.CookieService;
import com.fpoly.duantotnghiep.service.DangKyKhoaHocService;
import com.fpoly.duantotnghiep.service.DanhGiaService;
import com.fpoly.duantotnghiep.service.KhoaHocService;
import com.fpoly.duantotnghiep.service.MucLucService;
import com.fpoly.duantotnghiep.service.VideoService;
import com.paypal.api.openidconnect.Session;

@Controller
public class ClientController {
	@Autowired
	KhoaHocService daoHocService;

	@Autowired
	MucLucService mucLucService;

	@Autowired
	VideoService videoService;

	@Autowired
	CauHoiService cauHoiService;

	@Autowired
	CookieService cookieService;

	@Autowired
	DangKyKhoaHocService dangKyKhoaHocService;

	@Autowired
	DanhGiaService danhGiaService;

	@Autowired
	LoaiKhoaHocRepository loaiKhoaHocRepository;

	@Autowired
	ChungChiService chungchiService;

	@GetMapping("/courseOnline/index")
	public String index(Model model, @RequestParam("cid") Optional<Integer> cid) {
		List<KhoaHoc> findAllNameCategory = daoHocService.findAll();
		List<String> findAlIdCategory = daoHocService.findAlIdCategory();

		// Loại bỏ các giá trị trùng lặp từ danh sách findAllNameCategory
		Set<String> uniqueCategories = new HashSet();
		List<KhoaHoc> distinctCategories = new ArrayList<>();
		for (KhoaHoc khoaHoc : findAllNameCategory) {
			String tenLoaiKhoaHoc = khoaHoc.getLoaiKhoaHoc().getTenLoaiKhoaHoc();
			if (uniqueCategories.add(tenLoaiKhoaHoc)) {
				distinctCategories.add(khoaHoc);
			}
		}
		
		model.addAttribute("catesName", distinctCategories);
		model.addAttribute("catesId", findAlIdCategory);

		if (cid.isPresent()) {
			List<KhoaHoc> page = daoHocService.findByCategory(cid.get());
			List<DangKyKhoaHoc> list = dangKyKhoaHocService.findAll();
			List<DanhGia> list2 = danhGiaService.findAll();

			Map<Long, Integer> courseCountMap = createCourseCountMap(list);
			Map<Long, Integer> danhGiaMap = createDanhGiaMap(list2);
			Map<Long, Double> DiemDanhGiaMap = createDiemDanhGiaMap(list2, danhGiaMap);

			List<Object[]> courseCountList = createCourseCountList(page, courseCountMap);
			List<Object[]> danhGiaList = createDanhGiaList(page, danhGiaMap);
			List<Object[]> danhGiaList2 = createDanhGiaList2(page, DiemDanhGiaMap);
			model.addAttribute("danhGiaList2", danhGiaList2);
			model.addAttribute("danhGiaList", danhGiaList);
			model.addAttribute("list", courseCountList);
			model.addAttribute("courseOnline", page);
		} else {
			List<KhoaHoc> page = daoHocService.findAll();
			List<DangKyKhoaHoc> list = dangKyKhoaHocService.findAll();
			List<DanhGia> list2 = danhGiaService.findAll();

			Map<Long, Integer> courseCountMap = createCourseCountMap(list);
			Map<Long, Integer> danhGiaMap = createDanhGiaMap(list2);
			Map<Long, Double> DiemDanhGiaMap = createDiemDanhGiaMap(list2, danhGiaMap);

			List<Object[]> courseCountList = createCourseCountList(page, courseCountMap);
			List<Object[]> danhGiaList = createDanhGiaList(page, danhGiaMap);
			List<Object[]> danhGiaList2 = createDanhGiaList2(page, DiemDanhGiaMap);
			model.addAttribute("danhGiaList2", danhGiaList2);
			model.addAttribute("danhGiaList", danhGiaList);
			model.addAttribute("list", courseCountList);
			model.addAttribute("courseOnline", page);
		}

		return "index";
	}

	@GetMapping("/courseOnline/course")
	public String course(Model model) {
		List<KhoaHoc> page = daoHocService.findAll();
		List<DangKyKhoaHoc> list = dangKyKhoaHocService.findAll();
		List<DanhGia> list2 = danhGiaService.findAll();

		Map<Long, Integer> courseCountMap = createCourseCountMap(list);
		Map<Long, Integer> danhGiaMap = createDanhGiaMap(list2);
		Map<Long, Double> DiemDanhGiaMap = createDiemDanhGiaMap(list2, danhGiaMap);

		List<Object[]> courseCountList = createCourseCountList(page, courseCountMap);
		List<Object[]> danhGiaList = createDanhGiaList(page, danhGiaMap);
		List<Object[]> danhGiaList2 = createDanhGiaList2(page, DiemDanhGiaMap);

		model.addAttribute("danhGiaList2", danhGiaList2);
		model.addAttribute("danhGiaList", danhGiaList);
		model.addAttribute("list", courseCountList);
		model.addAttribute("courseOnline", page);

		return "course";
	}

	// Các phương thức tạo Map
	private Map<Long, Integer> createCourseCountMap(List<DangKyKhoaHoc> list) {
		Map<Long, Integer> courseCountMap = new HashMap<>();
		int count1 = 0;

		for (DangKyKhoaHoc dangKyKhoaHoc : list) {
			KhoaHoc khoaHoc = dangKyKhoaHoc.getKhoaHoc();
			long khoaHocId = khoaHoc.getId();

			if (courseCountMap.containsKey(khoaHocId)) {
				count1 = courseCountMap.get(khoaHocId);
				count1++;
				courseCountMap.put(khoaHocId, count1);
			} else {
				courseCountMap.put(khoaHocId, 1);
			}
		}

		return courseCountMap;
	}

	private Map<Long, Integer> createDanhGiaMap(List<DanhGia> list2) {
		Map<Long, Integer> danhGiaMap = new HashMap<>();
		int count1 = 0;

		for (DanhGia danhGia : list2) {
			KhoaHoc khoaHoc = danhGia.getKhoaHoc();
			long khoaHocId = khoaHoc.getId();

			if (danhGiaMap.containsKey(khoaHocId)) {
				count1 = danhGiaMap.get(khoaHocId);
				count1++;
				danhGiaMap.put(khoaHocId, count1);
			} else {
				danhGiaMap.put(khoaHocId, 1);
			}
		}

		return danhGiaMap;
	}

	private Map<Long, Double> createDiemDanhGiaMap(List<DanhGia> list2, Map<Long, Integer> danhGiaMap) {
		Map<Long, Double> DiemDanhGiaMap = new HashMap<>();
		int count1 = 0;

		for (DanhGia danhGia : list2) {
			KhoaHoc khoaHoc = danhGia.getKhoaHoc();
			long khoaHocId = khoaHoc.getId();

			if (DiemDanhGiaMap.containsKey(khoaHocId)) {
				double count = DiemDanhGiaMap.get(khoaHocId);
				count += danhGia.getSoDiemDanhGia();
				DiemDanhGiaMap.put(khoaHocId, count / count1);
			} else {
				DiemDanhGiaMap.put(khoaHocId, Double.valueOf(danhGia.getSoDiemDanhGia()));
			}
		}

		return DiemDanhGiaMap;
	}

	// Các phương thức tạo danh sách cuối cùng
	private List<Object[]> createCourseCountList(List<KhoaHoc> page, Map<Long, Integer> courseCountMap) {
		List<Object[]> courseCountList = new ArrayList<>();

		for (KhoaHoc khoaHoc : page) {
			long khoaHocId = khoaHoc.getId();
			int count = courseCountMap.getOrDefault(khoaHocId, 0);
			Object[] item = { count, khoaHocId };
			courseCountList.add(item);
		}

		return courseCountList;
	}

	private List<Object[]> createDanhGiaList(List<KhoaHoc> page, Map<Long, Integer> danhGiaMap) {
		List<Object[]> danhGiaList = new ArrayList<>();

		for (KhoaHoc khoaHoc : page) {
			long khoaHocId = khoaHoc.getId();
			int count = danhGiaMap.getOrDefault(khoaHocId, 0);
			Object[] item = { count, khoaHocId };
			danhGiaList.add(item);
		}

		return danhGiaList;
	}

	private List<Object[]> createDanhGiaList2(List<KhoaHoc> page, Map<Long, Double> DiemDanhGiaMap) {
		List<Object[]> danhGiaList2 = new ArrayList<>();

		for (KhoaHoc khoaHoc : page) {
			long khoaHocId = khoaHoc.getId();
			Double count = DiemDanhGiaMap.getOrDefault(khoaHocId, 0.0).doubleValue();
			Object[] item = { count, khoaHocId };
			danhGiaList2.add(item);
		}

		return danhGiaList2;
	}

	@GetMapping("/courseOnline/detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		cookieService.add("id", id, 1);
		return "detail";
	}

	@GetMapping("/courseOnline/about")
	public String about() {
		return "about";
	}

	@GetMapping("/courseOnline/contact")
	public String contact() {
		return "contact";
	}

	@GetMapping("/courseOnline/cart")
	public String cart() {
		return "cart";
	}

	@GetMapping("/courseOnline/checkout")
	public String checkout() {
		return "checkout";
	}

	@GetMapping("/courseOnline/mucluc")
	public String mucluc() {
		return "mucluc";
	}
	

	@GetMapping("/courseOnline/chungchi/{id}")
	public String chungchi(@PathVariable("id") String id, Model model) {
		// Mã hóa ID bằng Base64 trực tiếp trong controller
		ChungChi chungChi1 = new ChungChi();
		List<ChungChi> list = chungchiService.findAll();
		for (ChungChi chungChi : list) {
			// Mã hóa id của mỗi đối tượng ChungChi và thêm vào danh sách encodedIds
			String encodedId = java.util.Base64.getEncoder()
					.encodeToString(String.valueOf(chungChi.getId()).getBytes());
			System.out.println(encodedId);
			if (encodedId.equals(id)) {
				chungChi1 = chungChi;
				System.out.println(chungChi1.getNguoiDung().getHoTen());
				model.addAttribute("chungChi", chungChi1);

			}
		}
		for (DangKyKhoaHoc dangKyKhoaHoc : chungChi1.getKhoaHoc().getDangKyKhoaHocs()) {
			if (dangKyKhoaHoc.getNguoiDung().getId() == chungChi1.getNguoiDung().getId()) {
				model.addAttribute("dangKyKhoaHoc", dangKyKhoaHoc);
			}
		}
		return "ChungChi";
	}

	@GetMapping("/courseOnline/tracnghiem/{id}")
	public String tracngiem(@PathVariable("id") String id, Model model) {
		// Truyền id vào model để sử dụng trong view
		model.addAttribute("id", id);
		return "tracnghiem";
	}
}
