// Trong file JavaScript của bạn
var app = angular.module("myApp", []);

app.controller("HocVienCtrl", function ($scope, $http) {
    $scope.itemsHocVien = [];

    $scope.initialize = function () {
        // Tải danh sách học viên từ API REST
        $http.get("/rest/admin/HocVien/HocVien").then(function (response) {
            $scope.itemsHocVien = response.data;
        });
    };
	
    // Khởi đầu
    $scope.initialize();
});
