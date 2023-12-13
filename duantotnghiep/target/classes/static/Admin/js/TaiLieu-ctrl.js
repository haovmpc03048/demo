var app = angular.module("myApp", ['ui.bootstrap']);

app.controller("TaiLieu-ctrl", function ($scope, $http, $window) {
    $scope.itemsTaiLieu = [];
    $scope.formTaiLieu = {};

    $scope.itemsKhoaHoc = [];
    $scope.itemKhoaHoc = {};
    $scope.formKhoaHoc = {};
    $scope.KhoaHoc = {};
    var tenTaiLieu = $scope.formTaiLieu.tenSlide;
    $scope.selectedItem = null; // Biến để lưu trữ item được chọn khi bạn bấm vào nút "Sửa"
    $scope.selectedCourse = null; // Biến để lưu trữ khóa học được chọn

    $scope.currentPage = 1; // Trang hiện tại
    $scope.itemsPerPage = 5; // Số hàng trên mỗi trang
    $scope.totalItems = 0; // Tổng số tài liệu
    var selectedFile = null;


    // Hàm tải danh sách khóa học
    function loadKhoaHoc() {
        $http.get("/rest/admin/KhoaHoc").then(function (resp) {
            $scope.itemsKhoaHoc = resp.data;
        }).catch(function (error) {
            console.log("Lỗi tải danh sách khóa học", error);
        });
    }

    function loadTaiLieu() {
        $http.get("/rest/admin/TaiLieu").then(function (resp) {
            $scope.itemsTaiLieu = resp.data;
            $scope.totalItems = $scope.itemsTaiLieu.length;

            // Định dạng lại ngày thành "giờ ngày tháng năm"
            for (var i = 0; i < $scope.itemsTaiLieu.length; i++) {
                var ngayTao = moment($scope.itemsTaiLieu[i].ngayTao).format("HH:mm DD-MM-YYYY");
                $scope.itemsTaiLieu[i].ngayTao = ngayTao;
            }

            console.log($scope.itemsTaiLieu);
            $scope.pageChanged();
        }).catch(function (error) {
            console.log("Lỗi tải danh sách tài liệu", error);
        });
    }

    // Hàm tải danh sách tài liệu dựa trên khóa học đã chọn
    $scope.loadDocuments = function (id) {

        $scope.formTaiLieu.khoaHoc = id;
        console.log($scope.formTaiLieu.khoaHoc);
        if (!$scope.formTaiLieu.khoaHoc) {
            loadTaiLieu(); // Tải lại danh sách tài liệu nếu không có khóa học được chọn
            $scope.formTaiLieu.thuTu = "";
            return; // Thoát khỏi hàm nếu không có khoá học được chọn
        }
        $http.get("/rest/admin/KhoaHoc/" + $scope.formTaiLieu.khoaHoc).then(function (resp) {
            $scope.KhoaHoc = resp.data;
            // Định dạng lại ngày thành "giờ ngày tháng năm"
            for (var i = 0; i < $scope.itemsTaiLieu.length; i++) {
                var ngayTao = moment($scope.itemsTaiLieu[i].ngayTao).format("HH:mm DD-MM-YYYY");
                $scope.itemsTaiLieu[i].ngayTao = ngayTao;
            }


        }).catch(function (error) {
            console.log("Lỗi tải danh sách tài liệu", error);
        });
        if ($scope.formTaiLieu.khoaHoc) {
            $http.get("/rest/admin/TaiLieu/" + $scope.formTaiLieu.khoaHoc).then(function (resp) {
                $scope.itemsTaiLieu = resp.data;
                $scope.pageChanged(); // Hiển thị trang đầu tiên
                console.log($scope.itemsTaiLieu);
                // Định dạng lại ngày thành "giờ ngày tháng năm"
                for (var i = 0; i < $scope.itemsTaiLieu.length; i++) {
                    var ngayTao = moment($scope.itemsTaiLieu[i].ngayTao).format("HH:mm DD-MM-YYYY");
                    $scope.itemsTaiLieu[i].ngayTao = ngayTao;
                }

                $scope.formTaiLieu.thuTu = $scope.displayedItems.length + 1;

            });
        } else {
            $http.get("/rest/admin/TaiLieu").then(function (resp) {
                $scope.itemsTaiLieu = resp.data;
                $scope.pageChanged(); // Hiển thị trang đầu tiên
                // Định dạng lại ngày thành "giờ ngày tháng năm"
                for (var i = 0; i < $scope.itemsTaiLieu.length; i++) {
                    var ngayTao = moment($scope.itemsTaiLieu[i].ngayTao).format("HH:mm DD-MM-YYYY");
                    $scope.itemsTaiLieu[i].ngayTao = ngayTao;
                }
                $scope.formTaiLieu.thuTu = "";
            });

        }

    };

    // Khởi đầu
    $scope.initialize = function () {
        $scope.isEditting = true;
        $scope.isRemoving = true;
        loadKhoaHoc(); // Tải danh sách khóa học
        loadTaiLieu(); // Tải danh sách tài liệu

    };

    $scope.initialize();

    $scope.pageChanged = function () {
        var startIndex = ($scope.currentPage - 1) * $scope.itemsPerPage;
        var endIndex = startIndex + $scope.itemsPerPage;
        $scope.displayedItems = $scope.itemsTaiLieu.slice(startIndex, endIndex);
    };

    $scope.goToFirstPage = function (event) {
        if ($scope.currentPage !== 1) {
            $scope.currentPage = 1;
            $scope.pageChanged();
        }
    };

    $scope.goToPreviousPage = function (event) {
        if ($scope.currentPage > 1) {
            $scope.currentPage--;
            $scope.pageChanged();
        }
    };

    $scope.goToNextPage = function (event) {
        if ($scope.currentPage < Math.ceil($scope.totalItems / $scope.itemsPerPage)) {
            $scope.currentPage++;
            $scope.pageChanged();
        }
    };

    $scope.goToLastPage = function (event) {
        var lastPage = Math.ceil($scope.totalItems / $scope.itemsPerPage);
        if ($scope.currentPage !== lastPage) {
            $scope.currentPage = lastPage;
            $scope.pageChanged();
        }
    };



    $scope.create = function () {
        var tenTaiLieu = $scope.formTaiLieu.tenSlide;
        var selectedFile = document.getElementById('fileInput').files[0];
        var selectedKhoaHoc = $scope.selectedCourse;

        // Kiểm tra nếu tên tài liệu không được bỏ trống
        if (!tenTaiLieu) {
            alert("Tên tài liệu không được bỏ trống");
            return;
        }

        // Kiểm tra nếu khóa học đã được chọn
        if (!selectedKhoaHoc) {
            alert("Vui lòng chọn một khóa học");
            return;
        }

        // Kiểm tra nếu người dùng đã chọn một tệp tin
        if (!selectedFile) {
            alert("Vui lòng chọn tệp tin");
            return;
        }

        // Kiểm tra nếu tệp tài liệu có phải là một loại tệp hợp lệ (ví dụ: .pdf, .docx)
        var allowedFileExtensions = ["pdf", "docx", "pptx"]; // Các phần mở rộng tệp hợp lệ
        var fileNameParts = selectedFile.name.split(".");
        var fileExtension = fileNameParts[fileNameParts.length - 1].toLowerCase();
        if (allowedFileExtensions.indexOf(fileExtension) === -1) {
            alert("Loại tệp tài liệu không hợp lệ. Hãy chọn tệp .pdf, .docx hoặc .pptx.");
            return;
        }

        // Nếu tất cả kiểm tra đều thành công, tiếp tục với quá trình thêm tài liệu
        var ngayGioHienTai = moment().format("YYYY-MM-DDTHH:mm:ss.SSSZ");
        var item = angular.copy($scope.formTaiLieu);
        item.khoaHoc = selectedKhoaHoc;
        item.ngayTao = ngayGioHienTai;
        item.tenFile = selectedFile.name;

        var formData = new FormData();
        formData.append('file', selectedFile);

        $http.post('/api/upload', formData, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(function (uploadResp) {
            console.log(uploadResp.data);

            $http.post(`/rest/admin/TaiLieu`, item).then(function (resp) {
                $scope.itemsTaiLieu.push(resp.data);
                alert("Thêm mới thành công");
                $scope.reset();
            }).catch(function (error) {
                alert("Lỗi thêm mới tài liệu");
                console.log("Error", error);
            });
        }).catch(function (uploadError) {
            alert("Lỗi tải lên tệp tin");
            console.log("Upload Error", uploadError);
        });
    };


    $scope.edit = function (item) {
        var pdfViewerContainer = document.getElementById("pdfViewerContainer");
        pdfViewerContainer.style.display = "block";
        $scope.formTaiLieu = angular.copy(item);
        $scope.formTaiLieu.khoaHoc = item.khoaHoc.id;
        $scope.formTaiLieu.tenFile = item.tenFile;
        $scope.selectedCourse = item.khoaHoc;
        selectedFile = item.tenFile; // Gán selectedFile với thông tin tệp của item
        console.log(selectedFile);

        $scope.isCreateing = true; // Đặt chế độ chỉnh sửa thành true
        $scope.isEditting = false;
        $scope.isRemoving = false;
        console.log($scope.formTaiLieu);
        // Lấy tên tệp PDF từ item
        var tenTepPDF = item.tenFile;

        // Xây dựng đường dẫn đầy đủ đến tệp PDF
        var duongDanTepPDF = 'pdf/' + tenTepPDF;

        // Gọi hàm để hiển thị tệp PDF
        displayPDFFromFile(duongDanTepPDF);

        $http.get("/rest/admin/KhoaHoc/" + $scope.formTaiLieu.khoaHoc).then(function (resp) {
            $scope.KhoaHoc = resp.data;
        })
    };


    $scope.update = function () {
        $scope.formErrors = {};
        var item = angular.copy($scope.formTaiLieu);
        var selectedKhoaHoc = $scope.formTaiLieu.khoaHoc;
        console.log(selectedKhoaHoc);




        if (document.getElementById('fileInput').files[0]) {
            selectedFile = document.getElementById('fileInput').files[0];
            // Kiểm tra nếu tệp tài liệu có phải là một loại tệp hợp lệ (ví dụ: .pdf, .docx)
            var allowedFileExtensions = ["pdf", "docx", "pptx"]; // Các phần mở rộng tệp hợp lệ
            var fileNameParts = selectedFile.name.split(".");
            var fileExtension = fileNameParts[fileNameParts.length - 1].toLowerCase();
            if (allowedFileExtensions.indexOf(fileExtension) === -1) {
                alert("Loại tệp tài liệu không hợp lệ. Hãy chọn tệp .pdf, .docx hoặc .pptx.");
                return;
            }

            // Nếu tất cả kiểm tra đều thành công, tiếp tục với quá trình cập nhật tài liệu
            var ngayGioHienTai = moment().format("YYYY-MM-DDTHH:mm:ss.SSSZ");
            item.khoaHoc = selectedKhoaHoc;
            item.ngayTao = ngayGioHienTai;
            item.tenFile = selectedFile.name;

            var formData = new FormData();
            formData.append('file', selectedFile);

            $http.post('/api/upload', formData, {
                transformRequest: angular.identity,
                headers: { 'Content-Type': undefined }
            }).then(function (uploadResp) {
                item.khoaHoc = $scope.KhoaHoc;
                $http.put(`/rest/admin/TaiLieu/${item.id}`, item).then(function (resp) {
                    var index = $scope.itemsTaiLieu.findIndex(p => p.id == item.id);
                    $scope.itemsTaiLieu[index] = item;
                    alert("Cập nhật thành công");
                    $scope.loadDocuments();
                    $scope.reset();
                }).catch(function (error) {
                    alert("Lỗi cập nhật tài liệu");
                    console.log("Error", error);
                });
            }).catch(function (uploadError) {
                alert("Lỗi tải lên tệp tin1");
                console.log("Upload Error", uploadError);
            });
        } else {
            item.khoaHoc = $scope.KhoaHoc;
            var ngayGioHienTai = moment().format("YYYY-MM-DDTHH:mm:ss.SSSZ");
            item.ngayTao = ngayGioHienTai;
            $http.put(`/rest/admin/TaiLieu/${item.id}`, item).then(function (resp) {
                var index = $scope.itemsTaiLieu.findIndex(p => p.id == item.id);
                $scope.itemsTaiLieu[index] = item;
                alert("Cập nhật thành công");
                $scope.loadDocuments();
                $scope.reset();
            }).catch(function (error) {
                alert("Lỗi cập nhật tài liệu2");
                console.log("Error", error);
            });
        }

    };


    $scope.trang = {
        page: 0,
        size: 5,
        get itemsTaiLieu() {
            var start = this.page * this.size;
            return $scope.itemsTaiLieu.slice(start, start + this.size);
        },

        get count() {
            return Math.ceil(1.0 * $scope.itemsTaiLieu.length / this.size);
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

    $scope.reset = function () {
        $scope.formTaiLieu = {};
        $scope.isCreateing = false;
        $scope.isEditting = true;
        $scope.isRemoving = true;

        // Ẩn phần tử có id là "pdfViewerContainer" cùng với các phần tử con bên trong
        var pdfViewerContainer = document.getElementById("pdfViewerContainer");
        pdfViewerContainer.style.display = "none";

        // Đặt giá trị của trường input type="file" thành chuỗi rỗng
        var fileInput = document.getElementById("fileInput");
        fileInput.value = ""; // Đặt giá trị thành chuỗi rỗng
    };

    $scope.delete = function (item) {

        if ($scope.formTaiLieu.tenSlide) {
            item = angular.copy($scope.formTaiLieu);
            console.log(item);
        }
        var isConfirmed = confirm(`Bạn có chắc chắn muốn xóa tài liệu "${item.tenSlide}"?`);
        if (isConfirmed) {
            $http.delete(`/rest/admin/TaiLieu/${item.id}`).then(function (resp) {
                var index = $scope.itemsTaiLieu.findIndex(p => p.id == item.id);
                $scope.itemsTaiLieu.splice(index, 1);
                alert("Xóa thành công");
                $scope.loadDocuments();
                $scope.reset();
            }).catch(function (error) {
                alert("Lỗi xóa tài liệu");
                console.log("Error", error);
            });
        }
    };


    $scope.itemsKhoaHoc = []; // Danh sách khóa học của bạn
    $scope.selectedCourse = null; // Khởi tạo biến selectedCourse
    $scope.showAllCourses = function () {
        $scope.selectedCourse = null; // Đặt selectedCourse thành null
        // Hiển thị tất cả các khóa học bằng cách áp dụng bộ lọc
        $scope.searchCourse = "";
        console.log("selectedCourse is null");
        $scope.loadDocuments();
    };
    $scope.selectCourse = function (course) {
        $scope.selectedCourse = course;
        console.log($scope.selectedCourse);
        $scope.loadDocuments($scope.selectedCourse.id);

    };
    function displayPDFFromFile(duongDan) {
        var pdfjsLib = window['pdfjs-dist/build/pdf'];
        pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.10.377/pdf.worker.js';

        var pdfCanvas = document.getElementById('pdfCanvas');
        var pdfScrollContainer = document.getElementById('pdfScrollContainer'); // Container cho phần có thể kéo

        pdfjsLib.getDocument(duongDan).promise.then(function (loadedPdf) {
            pdf = loadedPdf;
            renderPage(currentPageNum); // Hiển thị trang đầu tiên mặc định
            updateNavButtons();
        });
    }

});
app.filter('limitWords', function () {
    return function (input, limit) {
        if (!input) return '';
        var words = input.split(' ');
        if (words.length <= limit) return input;
        return words.slice(0, limit).join(' ') + '...';
    };
});

const selected = document.querySelector(".selected");
const optionsContainer = document.querySelector(".options-container");
const searchBox = document.querySelector(".search-box input");

const optionsList = document.querySelectorAll(".option");

selected.addEventListener("click", () => {
    optionsContainer.classList.toggle("active");
    searchBox.value = "";
    filterList("");

    if (optionsContainer.classList.contains("active")) {
        searchBox.focus();
    }
});

optionsList.forEach(o => {
    o.addEventListener("click", () => {
        selected.innerHTML = o.querySelector("label").innerHTML;
        optionsContainer.classList.remove("active");
    });
});

searchBox.addEventListener("input", function () {
    if (searchBox.value.length = 0) {
        filterList("");
    } else {
        filterList(searchBox.value);
    }

});

const filterList = searchTerm => {
    searchTerm = searchTerm.toLowerCase();
    optionsList.forEach(option => {
        let label = option.firstElementChild.nextElementSibling.innerText.toLowerCase();
        if (label.indexOf(searchTerm) !== -1) {
            option.style.display = "block";
        } else {
            option.style.display = "none";
        }
    });
};


