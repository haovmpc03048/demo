// Đăng ký module
var app = angular.module('detail-app', []);

app.controller('detail-controller', function ($scope, $http, $window) {

    $scope.DangKy = {};
    $scope.check = {};
    $scope.hoc = {};
    $scope.chungChiList = {};
    $scope.idNguoiDung2 = {};
    $scope.TenNguoiDung = {};
    $scope.khoaHoc = {};
    var idNguoiDung = null;
    var idKhoaHoc = null;
    $scope.trangThai = null;
    // Hàm để lấy giá trị từ cookie bằng tên
    function getCookieValue(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length === 2)
            return parts.pop().split(";").shift();
    }
    // Lấy phần tử input bằng ID
    var inputElement = document.getElementById('idLogin');



    if (inputElement == null || inputElement == "") {
        value = 0;
    }
    else {
        // Lấy giá trị của input
        var value = inputElement.value;
        value = inputElement.value;
    }

    // Lấy id từ cookie
    var id = getCookieValue("id");


    $scope.checkCourse = function (IdUser, id) {
        $http({
            method: 'GET',
            url: '/api/courseOnline/check/' + IdUser + '/' + id
        }).then(function (response) {
            $scope.check = response.data;
        }, function (response) {
            console.log(response);
            $scope.check = null;
        });

        $http({
            method: 'GET',
            url: '/api/tiendokhoahoc/' + IdUser + '/' + id
        }).then(function (response) {
            $scope.trangThai = response.data.trangThai;
        }, function (response) {
            console.log(response);
        });


    }

    // Hàm để khởi tạo thông tin khóa học
    $scope.init = function () {
        // Gửi yêu cầu GET đến API với id lấy từ cookie
        $http({
            method: 'GET',
            url: '/api/courseOnline/detail/' + id
        }).then(function (response) {
            // Gán dữ liệu khóa học cho biến $scope.hoc
            $scope.hoc = response.data;
            idKhoaHoc = $scope.hoc.courseOnline.id;

            $scope.checkCourse(value, idKhoaHoc);
            $scope.showTaiLieu(idKhoaHoc);

        }, function (response) {
            console.log(response);
        });

        // Lấy thông tin người dùng
        if (value != 0) {
            $http.get("/rest/admin/NguoiDung/" + value)
                .then(function (resp) {
                    idNguoiDung = resp.data.id;
                    console.log(idNguoiDung + " :id Người dùng");

                });
        }
        else {
            idNguoiDung = 0;
            $scope.check = null;
        }

        $http.get("/rest/ChungChi/" + id + '/' + value)
            .then(function (resp) {
                $scope.chungChiList = resp.data; // Lưu trữ mảng dữ liệu vào $scope.chungChiList

                for (var i = 0; i < $scope.chungChiList.length; i++) {
                    var rawDate = new Date($scope.chungChiList[i].ngayCap);
                    var formattedDate = `${rawDate.getDate()}/${rawDate.getMonth() + 1}/${rawDate.getFullYear()}`;
                    console.log(formattedDate);
                    $scope.chungChiList[i].ngayCapFormatted = formattedDate;
                }
            });


    }
    $scope.getIdNguoiDung = function () {
        $http({
            method: 'GET',
            url: "/rest/admin/NguoiDung/" + value
        }).then(function (response) {
            // Gán dữ liệu người dùng cho biến $scope.idNguoiDung2

            $scope.idNguoiDung2 = response.data.id;
            $scope.TenNguoiDung = response.data.hoTen;


        }, function (response) {

        });
    };

    $scope.getid = function (id) {
        $window.sessionStorage.setItem('videoId', id);
        $window.location.href = '/courseOnline/video/' + id;
    }

    $scope.continueCourse = function (id) {
        $scope.getid(id);
    }

    $scope.showTaiLieu = function (id) {
        $http.get("/rest/admin/TaiLieu/" + id)
            .then(function (resp) {
                $scope.tailieu = resp.data;
            });
    }

    $scope.baseFileURL = '/Admin/pdf/'; // Đường dẫn cơ sở tới thư mục chứa file

    $scope.downloadPDF = function (fileName, tentailieu) {
        var fileURL = $scope.baseFileURL + fileName;
        fetch(fileURL)
            .then(response => response.blob())
            .then(blob => {
                saveAs(blob, tentailieu + '.pdf'); // Lưu file với tên tài liệu
            })
            .catch(error => {
                console.error('Lỗi khi tải file:', error);
                // Xử lý lỗi tại đây nếu cần thiết
            });
    };
    $scope.goToLoginForm = function () {
        // Chuyển hướng đến form đăng nhập
        window.location.href = '/courseOnline/dangnhap'; // Thay đổi đường dẫn tùy theo định dạng URL của bạn
    };


    $scope.addCourse = function (id) {
        $http.get("/rest/admin/NguoiDung/" + value)
            .then(function (resp) {
                idNguoiDung = resp.data.id;
                console.log(idNguoiDung + " :id Người dùng");

            });

        var isLogin = document.getElementById("idLogin").value;
        if (isLogin == 'null') {
            console.log("Bạn chưa đăng nhập");
            window.location.href = 'http://localhost:8080/courseOnline/dangnhap';
        } else {
            $http.get("/rest/admin/KhoaHoc/" + id)
                .then(function (resp) {
                    idKhoaHoc = resp.data.id;
                    console.log(idKhoaHoc + " :id Khóa học");
                    $scope.khoaHoc = resp.data;

                    console.log($scope.khoaHoc.chiPhi);
                    alert("Bạn có muốn đăng ký khóa học này không?");

                    if ($scope.khoaHoc.chiPhi != 0) {
                        $http({
                            method: 'GET',
                            url: '/api/Checkout/check/' + value + '/' + id
                        }).then(function (response) {
                            console.log(response.data);
                            if (!response.data.trangThai) {
                                // Chưa thanh toán, chuyển hướng đến trang Checkout
                                window.location.href = '/courseOnline/CheckOut';
                            } else {

                            }
                        }, function (response) {
                            console.log(response);
                        });
                    } else {
                        $http.get("/rest/admin/NguoiDung/" + value)
                            .then(function (resp) {
                                $scope.DangKy.nguoiDung = resp.data;
                                $scope.DangKy.khoaHoc = $scope.hoc.courseOnline;
                                $scope.DangKy.ngayDangKy = new Date();
                                $scope.DangKy.tienDo = 0;
                                $scope.DangKy.trangThai = 0;
                                console.log($scope.DangKy);
                                // Gửi POST request để đăng ký khóa học
                                $http.post("/api/courseOnline", $scope.DangKy)
                                    .then(function (response) {
                                        $scope.init();
                                        console.log(response);
                                        $scope.getid(id);
                                    }, function (response) {
                                        console.log(response);
                                    });
                            });
                    }
                });

        }
    }


    // Gọi hàm init để khởi tạo thông tin khóa học
    $scope.init();
});
