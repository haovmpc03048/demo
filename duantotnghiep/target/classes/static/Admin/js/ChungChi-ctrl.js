var app = angular.module("myApp", ['ui.bootstrap']);
app.controller("ChungChi-ctrl", function ($scope, $http, $window) {
    $scope.itemsChungChi = [];
    $scope.formTaiLieu = {};

    $scope.itemsKhoaHoc = [];
    $scope.formKhoaHoc = {};
    $scope.inputString = [];
    $scope.itemsNguoiDung = [];
    $scope.select2ChosenText = ''; // Biến này lưu trữ nội dung của <span class='select2-chosen'>


    $scope.currentPage = 1; // Trang hiện tại
    $scope.itemsPerPage = 5; // Số hàng trên mỗi trang
    $scope.totalItems = 0; // Tổng số tài liệu

    $scope.selectedCourse = null; // Khởi tạo biến selectedCourse
    $scope.selectedUser = null; // Khởi tạo biến selectedUser
    $scope.itemsChungChi = []; // Danh sách chứng chỉ
    $scope.currentIndex = 0; // Chỉ số hiện tại của chứng chỉ đang hiển thị


    $scope.initialize = function () {

        // load tài liệu
        $http.get("/rest/admin/ChungChi").then(resp => {
            $scope.itemsChungChi = resp.data;
            console.log($scope.itemsChungChi);

            $scope.loadDocuments();

        }).catch(error => {
            console.error("An error occurred:", error);
        });

        $http.get("/rest/admin/KhoaHoc").then(resp => {
            $scope.itemsKhoaHoc = resp.data;
            console.log($scope.itemsKhoaHoc);
        });

        $http.get("/rest/admin/NguoiDung").then(resp => {
            $scope.itemsNguoiDung = resp.data;
            console.log($scope.itemsNguoiDung);
        });
        

    };
    $scope.$watch('select2ChosenText', function (newVal, oldVal) {
        // Xử lý sự kiện khi nội dung của <span class='select2-chosen'> thay đổi
        if (newVal !== oldVal) {
            loadDocuments();
        }
    });

    // Khởi đầu
    $scope.initialize();


    $scope.loadDocuments = function (khoaHoc, nguoidung) {
        $scope.selectedCourse = khoaHoc;
        $scope.selectedUser = nguoidung;
        var url = "/rest/admin/ChungChi";
        if ($scope.selectedCourse && $scope.selectedUser) {
            console.log("1");
            url = "/rest/admin/ChungChi/" + $scope.selectedCourse + "/" + $scope.selectedUser;
        } else if ($scope.selectedCourse) {
            url = "/rest/admin/ChungChi/" + $scope.selectedCourse;
        } else if ($scope.selectedUser) {
            url = "/rest/admin/ChungChi/nguoidung/" + $scope.selectedUser;
        }

        $http.get(url).then(function (resp) {
            $scope.itemsChungChi = resp.data;

            // Định dạng ngày tháng
            for (var i = 0; i < $scope.itemsChungChi.length; i++) {
                var rawDate = new Date($scope.itemsChungChi[i].ngayCap);
                var formattedDate = `${rawDate.getDate()}/${rawDate.getMonth() + 1}/${rawDate.getFullYear()}`;
                console.log(formattedDate);
                $scope.itemsChungChi[i].ngayCapFormatted = formattedDate;
            }

            $scope.totalItems = $scope.itemsChungChi.length;
        });
    };

    $scope.changeCertificate = function (offset) {
        $scope.currentIndex += offset;
        $scope.currentIndex = Math.min(Math.max($scope.currentIndex, 0), $scope.itemsChungChi.length - 1);
    };

    $scope.NguoiDungChon = null;
    $scope.KhoaHocChon = null;
    $scope.nguoiDungHienThi = null;
    $scope.khoaHocHienThi = null;
    $scope.showAllUsers = function () {
        $scope.nguoiDungHienThi = null; // Đặt selectedCourse thành null
        // Hiển thị tất cả các khóa học bằng cách áp dụng bộ lọc
        $scope.searchUser = "";
        $scope.NguoiDungChon = null;
        console.log("selectedUser is null");
        $scope.loadDocuments($scope.KhoaHocChon, $scope.NguoiDungChon);

    };
    $scope.chonNguoiDung = function (user) {
        $scope.nguoiDungHienThi = user;
        $scope.NguoiDungChon = user.id;
        $scope.loadDocuments($scope.KhoaHocChon, $scope.NguoiDungChon);
    };
    $scope.showAllCourses = function () {
        $scope.khoaHocHienThi = null; // Đặt selectedCourse thành null
        // Hiển thị tất cả các khóa học bằng cách áp dụng bộ lọc
        $scope.searchCourse = "";
        $scope.KhoaHocChon = null;
        console.log("selectedCourse is null");
        $scope.loadDocuments($scope.KhoaHocChon, $scope.NguoiDungChon);

    };
    $scope.ChonKhoaHoc = function (course) {
        $scope.khoaHocHienThi = course;
        $scope.KhoaHocChon = course.id;
        $scope.loadDocuments($scope.KhoaHocChon, $scope.NguoiDungChon);
    };
});

// Chọn tất cả các phần tử .selected, .options-container, .search-box input và .option
const selectedElements = document.querySelectorAll(".selected");
const optionsContainers = document.querySelectorAll(".options-container");
const searchBoxes = document.querySelectorAll(".search-box input");
const optionsLists = document.querySelectorAll(".option");

// Lặp qua từng cặp select và thực hiện xử lý cho mỗi cặp
selectedElements.forEach((selected, index) => {
    const optionsContainer = optionsContainers[index];
    const searchBox = searchBoxes[index];
    const optionsList = optionsLists[index];

    // Kiểm tra xem tất cả các phần tử đã được tìm thấy trước khi thực hiện xử lý
    if (selected && optionsContainer && searchBox && optionsList) {
        selected.addEventListener("click", () => {
            optionsContainer.classList.toggle("active");
            searchBox.value = "";
            filterList("", optionsList);

            if (optionsContainer.classList.contains("active")) {
                searchBox.focus();
            }
        });

        optionsList.addEventListener("click", (e) => {
            if (e.target.tagName === "LABEL") {
                selected.innerHTML = e.target.innerHTML;
                optionsContainer.classList.remove("active");
            }
        });

        searchBox.addEventListener("input", function () {
            if (searchBox.value.length === 0) {
                filterList("", optionsList);
            } else {
                filterList(searchBox.value, optionsList);
            }
        });

        const filterList = (searchTerm, list) => {
            searchTerm = searchTerm.toLowerCase();
            list.querySelectorAll("label").forEach((label) => {
                const labelText = label.innerText.toLowerCase();
                const option = label.parentElement;

                if (labelText.indexOf(searchTerm) !== -1) {
                    option.style.display = "block";
                } else {
                    option.style.display = "none";
                }
            });
        };
    }
});
