var app = angular.module("myApp", []);

app.controller("revenue-ctrl", function ($scope, $http, $window) {
    $scope.items = [];
    $scope.form = {};
    $scope.thoigian = {};
    $scope.thoigiankhoahoc = {};
    $scope.loadKhoaHoc = [];
    $scope.itemsThongKeTheoKhoaHoc = [];
    $scope.modalItem = {}; // Đối tượng lưu trữ dữ liệu của mục được chọn
    //định dạng tiền
    $scope.formatCurrency = function (amount) {
        // Chuyển đổi số tiền thành chuỗi và loại bỏ số 0 ở cuối
        return numeral(amount).format('0,0') + ' VND';
    };

    $scope.ThongKeTheoThoiGian = function () {
        $scope.convertToMoment();

        $http.get(apiEndpoints.thongKeThoiGian, {
            params: {
                batDau: $scope.thoigian.batDau,
                ketThuc: $scope.thoigian.ketThuc
            }
        }).then(resp => {
            $scope.items = resp.data;
            $scope.renderChart("revenue-chart");
        });
    }

    $scope.ThongKeNguoiHoc = function (id) {
        $http.get(apiEndpoints.ThongKeNguoiHoc, {
            params: {
                idKhoaHoc: id
            }
        }).then(resp => {
            $scope.SoNguoi = resp.data;
        });
    }
    $scope.ThongKeThangNay = function () {
        $scope.setupHelpers();
        $http.get(apiEndpoints.ThongKeThangNay).then(resp => {
            $scope.items = resp.data;
            $scope.renderChart("revenue-chart");
        });
    }
    $scope.ThongKeNamNay = function () {
        $scope.setupHelpers();
        $http.get(apiEndpoints.ThongKeNamNay).then(resp => {
            $scope.items = resp.data;
            $scope.renderChart("revenue-chart");
        });
    }
    $scope.ThongKeNamTruoc = function () {
        $scope.setupHelpers();
        $http.get(apiEndpoints.ThongKeNamTruoc).then(resp => {
            $scope.items = resp.data;
            $scope.renderChart("revenue-chart");
        });
    }

    $scope.ThongKeTheoKhoaHoc = function () {

        $scope.convertToMoment2();

        //Thông kê số người học theo mốc thời gian
        $http.get(apiEndpoints.ThongKeNguoiHocTG, {
            params: {
                idKhoaHoc: $scope.modalItem.id,
                batDau: $scope.thoigiankhoahoc.batDau,
                ketThuc: $scope.thoigiankhoahoc.ketThuc
            }
        }).then(resp => {
            $scope.SoNguoi = resp.data;
        });

        $http.get(apiEndpoints.ThongKeKhoaHocTG, {
            params: {
                idKhoaHoc: $scope.modalItem.id,
                batDau: $scope.thoigiankhoahoc.batDau,
                ketThuc: $scope.thoigiankhoahoc.ketThuc
            }
        }).then(resp => {
            $scope.itemsThongKeTheoKhoaHoc = resp.data;

            // Extract data for chart
         const labels = $scope.itemsThongKeTheoKhoaHoc.map(item => $scope.formatDate(item.year, item.month));
         const data = $scope.itemsThongKeTheoKhoaHoc.map(item => item.totalRevenue);
         // Tính tổng doanh thu
         $scope.totalRevenue2 = $scope.itemsThongKeTheoKhoaHoc.reduce((total, item) => total + item.totalRevenue, 0);
         // Get the canvas element
         const canvas = document.getElementById("revenue-chart2");
        if ($scope.myChart2) {
                    $scope.myChart2.destroy();
                }
         // Create the chart using Chart.js
         const ctx = canvas.getContext("2d");
         $scope.myChart2 = new Chart(ctx, {
             type: "bar",
             data: {
                 labels: labels,
                 datasets: [{
                     label: "Tổng doanh thu/tháng",
                     data: data,
                     backgroundColor: "rgba(75, 192, 192, 0.2)",
                     borderColor: "rgba(75, 192, 192, 1)",
                     borderWidth: 1
                 }]
             },
             options: {
                 scales: {
                     y: {
                         beginAtZero: true,
                         ticks: {
                             callback: function (value, index, values) {
                                 return $scope.formatTotalPrice(value);
                             }
                         }
                     }
                 }
             }
         });
            
        });
         

    };

    
    $scope.xoaformthongke = function () {
        $scope.itemsThongKeTheoKhoaHoc =[];
        $scope.myChart2.destroy();
    }
    $scope.editbr = function (item) {
        
        $scope.modalItem = angular.copy(item);
        
        $scope.openModal();
        $scope.ThongKeNguoiHoc($scope.modalItem.id);
        
        $http.get(apiEndpoints.ThongKeKhoaHoc, {
            params: {
                idKhoaHoc: $scope.modalItem.id,
            }
        }).then(resp => {
            $scope.itemsThongKeTheoKhoaHoc = resp.data;

            // Extract data for chart
         const labels = $scope.itemsThongKeTheoKhoaHoc.map(item => $scope.formatDate(item.year, item.month));
         const data = $scope.itemsThongKeTheoKhoaHoc.map(item => item.totalRevenue);
         // Tính tổng doanh thu
         $scope.totalRevenue2 = $scope.itemsThongKeTheoKhoaHoc.reduce((total, item) => total + item.totalRevenue, 0);
         // Get the canvas element
         const canvas = document.getElementById("revenue-chart2");
 
         // Create the chart using Chart.js
         const ctx = canvas.getContext("2d");
         $scope.myChart2 = new Chart(ctx, {
             type: "bar",
             data: {
                 labels: labels,
                 datasets: [{
                     label: "Tổng doanh thu/tháng",
                     data: data,
                     backgroundColor: "rgba(75, 192, 192, 0.2)",
                     borderColor: "rgba(75, 192, 192, 1)",
                     borderWidth: 1
                 }]
             },
             options: {
                 scales: {
                     y: {
                         beginAtZero: true,
                         ticks: {
                             callback: function (value, index, values) {
                                 return $scope.formatTotalPrice(value);
                             }
                         }
                     }
                 }
             }
         });
            
        });
         

    };
    
    $scope.openModal = function () {
        document.getElementById('myModal').style.display = 'block';
    };
    
    $scope.closeModal = function () {
        $scope.xoaformthongke();
        document.getElementById('myModal').style.display = 'none';
    };
    

    $scope.initialize = function () {
        $scope.setupHelpers();
        $http.get(apiEndpoints.report).then(resp => {
            $scope.items = resp.data;
            $scope.renderChart("revenue-chart");
        });
        $http.get(apiEndpoints.ThongKeTheoNgay).then(resp => {
             resp.data;
            // Tính tổng doanh thu trong ngày
            $scope.DoanhThuTrongNgay = resp.data.reduce((total, item) => total + item.totalRevenue, 0);
        });

        // load Khóa học
        $http.get("/rest/admin/KhoaHoc").then(resp => {
            // Chuyển đổi ngày giờ sang múi giờ Việt Nam
            resp.data.forEach(item => {
                item.ngayTao = moment(item.ngayTao).utcOffset(7).format('DD-MM-YYYY HH:mm:ss');
            });
            $scope.loadKhoaHoc = resp.data;
        });

    }

    $scope.setupHelpers = function () {
        $scope.formatTotalPrice = function (totalPrice) {
            return numeral(totalPrice).format('0,0') + ' VND';
        };

        $scope.formatDate = function (year, month) {
            const date = moment(`${year}-${month}`, 'YYYY-M').format('MM YYYY');
            return date.charAt(0).toUpperCase() + date.slice(1);
        };
    }

    $scope.renderChart = function (name) {
        
        
        // Extract data for chart
        const labels = $scope.items.map(item => $scope.formatDate(item.year, item.month));
        const data = $scope.items.map(item => item.totalRevenue);
        // Tính tổng doanh thu
        $scope.totalRevenue = $scope.items.reduce((total, item) => total + item.totalRevenue, 0);
        // Get the canvas element
        const canvas = document.getElementById(name);

        // Destroy the existing chart if any
        if ($scope.myChart) {
            $scope.myChart.destroy();
        }

        // Create the chart using Chart.js
        const ctx = canvas.getContext("2d");
        $scope.myChart = new Chart(ctx, {
            type: "bar",
            data: {
                labels: labels,
                datasets: [{
                    label: "Tổng doanh thu/tháng",
                    data: data,
                    backgroundColor: "rgba(75, 192, 192, 0.2)",
                    borderColor: "rgba(75, 192, 192, 1)",
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: function (value, index, values) {
                                return $scope.formatTotalPrice(value);
                            }
                        }
                    }
                }
            }
        });
    }

    $scope.convertToMoment = function () {

        $scope.thoigian.batDau = moment($scope.thoigian.batDau).toDate();
        $scope.thoigian.ketThuc = moment($scope.thoigian.ketThuc).toDate();

    }
    $scope.convertToMoment2 = function () {

        $scope.thoigiankhoahoc.batDau = moment($scope.thoigiankhoahoc.batDau).toDate();
        $scope.thoigiankhoahoc.ketThuc = moment($scope.thoigiankhoahoc.ketThuc).toDate();

    }
    

    // Constants for API endpoints
    var apiEndpoints = {
        report: "/rest/Report",
        thongKeThoiGian: "/rest/Report/thong-ke-thoi-gian",
        ThongKeKhoaHoc: "/rest/Report/thong-ke-Khoa-hoc",
        ThongKeKhoaHocTG: "/rest/Report/thong-ke-Khoa-hoc-TG",
        ThongKeNguoiHoc: "/rest/Report/thong-ke-nguoi-hoc",
        ThongKeNguoiHocTG: "/rest/Report/thong-ke-nguoi-hoc-theo-moc-thoi-gian",
        ThongKeTheoNgay: "/rest/Report/thong-ke-theo-ngay",
        ThongKeThangNay: "/rest/Report/thong-ke-thang-nay",
        ThongKeNamNay: "/rest/Report/thong-ke-nam-nay",
        ThongKeNamTruoc: "/rest/Report/thong-ke-nam-truoc",
    };

    // Initialize
    $scope.initialize();

    $scope.pager = {
        page: 0,
        size: 5,
        get loadKhoaHoc() {
            var start = this.page * this.size;
            return $scope.loadKhoaHoc.slice(start, start + this.size);
        },

        get count() {
            return Math.ceil(1.0 * $scope.loadKhoaHoc.length / this.size);
        },

        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if (this.page < 0) {
                this.last();
            }
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.first();
            }
        },
        last() {
            this.page = this.count - 1;
        }
    }
});
