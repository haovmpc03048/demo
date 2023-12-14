var app = angular.module("myApp", ['ui.bootstrap']);

app.controller("CauHoi-ctrl", function ($scope, $http, $window) {
    $scope.itemsCauHoi = [];
    $scope.formTaiLieu = {};
    $scope.formCauHoi = {};
    $scope.KhoaHoc = [];

    $scope.itemsKhoaHoc = [];
    $scope.formKhoaHoc = {};
    $scope.inputString = [];
    $scope.MucLuc = [];

    $scope.currentPage = 1; // Trang hiện tại
    $scope.itemsPerPage = 5; // Số hàng trên mỗi trang
    $scope.totalItems = 0; // Tổng số tài liệu
    $scope.khoaHoc1 = {}; // Khởi tạo mảng khoaHoc trống


    let selectedAnswer = ""; // Biến để theo dõi đáp án được chọn
    let answerCount = 2; // Starting count for dynamic answer fields
    let cauTraLoiString = ""; // Biến để theo dõi chuỗi câu trả lời
    $scope.editMode = false; // Ban đầu ở chế độ không chỉnh sửa

    // Hàm để chuyển đổi trạng thái editMode
    $scope.toggleEditMode = function () {
        $scope.editMode = !$scope.editMode;
    };
    $scope.initialize = function () {
        $http.get("/rest/admin/KhoaHoc").then(function (resp) {
            $scope.khoaHoc = resp.data;
        });
        // Load tài liệu
        $http.get("/rest/admin/CauHoi").then(function (resp) {
            $scope.itemsCauHoi = resp.data;
            $scope.totalItems = $scope.itemsCauHoi.length;
            $scope.pageChanged(); // Hiển thị trang đầu tiên

            // Tạo mảng mới chứa thông tin câu hỏi, câu trả lời và mảng đáp án
            const questionAnswerArray = [];

            for (const item of $scope.itemsCauHoi) {
                if (item.cauTraLoi) {
                    const splitAnswers = item.cauTraLoi.split(',').map(answer => answer.trim());
                    // Tạo đối tượng chứa thông tin và đưa vào mảng mới
                    const questionObject = {
                        cauHoi: item.cauHoi,
                        cauTraLoi: splitAnswers,
                        dapAn: item.dapAn
                    };

                    questionAnswerArray.push(questionObject);
                } else {
                    console.log("'cauTraLoi' does not exist or has no value for an item.");
                }
            }

        }).catch(function (error) {
            console.error("An error occurred:", error);
        });

        $http.get("/rest/admin/KhoaHoc").then(function (resp) {
            $scope.itemsKhoaHoc = resp.data;
        });
    };

    // Khởi đầu
    $scope.initialize();

    $scope.loadDocuments = function (id) {
        const selectedCourseId = id;

        $http.get("/rest/admin/MucLuc/KhoaHoc/" + $scope.selectedCourse.id).then(resp => {
            $scope.MucLuc = resp.data;
            console.log($scope.MucLuc);
        });

        if (selectedCourseId === null || selectedCourseId === undefined) {
            $http.get("/rest/admin/CauHoi").then(function (resp) {
                $scope.itemsCauHoi = resp.data;
                $scope.totalItems = $scope.itemsCauHoi.length;
                $http.get(`/rest/admin/KhoaHoc`)
                    .then(function (resp) {
                        $scope.KhoaHoc = resp.data;
                    });
                $scope.pageChanged(); // Hiển thị trang đầu tiên
            });
        }
    };

    $scope.pageChanged = function () {
        var startIndex = ($scope.currentPage - 1) * $scope.itemsPerPage;
        var endIndex = startIndex + $scope.itemsPerPage;
        $scope.displayedItems1 = $scope.itemsCauHoi.slice(startIndex, endIndex);
    };

    $scope.goToFirstPage = function () {
        $scope.currentPage = 1;
        $scope.pageChanged();
    };

    $scope.goToPreviousPage = function () {
        if ($scope.currentPage > 1) {
            $scope.currentPage--;
            $scope.pageChanged();
        }
    };

    $scope.goToNextPage = function () {
        if ($scope.currentPage < $scope.totalItems / $scope.itemsPerPage) {
            $scope.currentPage++;
            $scope.pageChanged();
        }
    };

    $scope.goToLastPage = function () {
        $scope.currentPage = Math.ceil($scope.totalItems / $scope.itemsPerPage);
        $scope.pageChanged();
    };

    $scope.addAnswerField = function () {
        if (answerCount <= 4) { // Limit to 5 dynamic answer fields
            const dynamicAnswers = document.getElementById('dynamicAnswers');
            const newAnswerField = document.createElement('div');
            newAnswerField.classList.add('form-group');
            newAnswerField.innerHTML = `<input type="radio" name="correctAnswer" class="answer-radio">
        <input type="text" class="form-control" placeholder="TraLoi${answerCount + 1}" value="">`;
            dynamicAnswers.appendChild(newAnswerField);
            answerCount++;

            // Gán sự kiện "change" cho radio button mới thêm
            const newRadio = newAnswerField.querySelector('.answer-radio');
            newRadio.addEventListener('change', $scope.showSelectedAnswer);

            // Tạo nút xóa cho input câu trả lời mới
            const deleteButton = document.createElement('button');
            deleteButton.classList.add('btn', 'btn-danger', 'ml-2');
            deleteButton.textContent = 'Xóa';
            deleteButton.addEventListener('click', function () {
                // Xóa input câu trả lời khi nút xóa được nhấn
                dynamicAnswers.removeChild(newAnswerField);
                answerCount--;

                // Cập nhật biến chuỗi câu trả lời sau khi xóa
                const deletedAnswerValue = newAnswerField.querySelector('input[type="text"]').value;
                cauTraLoiString = cauTraLoiString.replace(deletedAnswerValue, '').trim();

                // Cập nhật giá trị của input nối chuỗi
                const cauHoiInput = document.getElementById('cauHoiInput');
                cauHoiInput.value = cauTraLoiString;
            });

            // Thêm nút xóa vào input câu trả lời mới
            newAnswerField.appendChild(deleteButton);
        }
    }
    $scope.trang = {
        page: 0,
        size: 5,
        get itemsCauHoi() {
            var start = this.page * this.size;
            return $scope.itemsCauHoi.slice(start, start + this.size);
        },

        get count() {
            return Math.ceil(1.0 * $scope.itemsCauHoi.length / this.size);
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
    $scope.addNewAnswer = function () {
        const newAnswer = document.getElementById('newAnswer').value;
        if (newAnswer !== "") {
            const dynamicAnswers = document.getElementById('dynamicAnswers');
            const newAnswerField = document.createElement('div');
            newAnswerField.classList.add('form-group');
            newAnswerField.innerHTML = `<input type="radio" name="correctAnswer" class="answer-radio">
            <input type="text" class="form-control" placeholder="${newAnswer}" value="${newAnswer}">`;
            dynamicAnswers.appendChild(newAnswerField);
            answerCount++;
            document.getElementById('newAnswer').value = ""; // Clear the input field

            // Cập nhật biến chuỗi câu trả lời sau khi thêm
            if (cauTraLoiString === "") {
                cauTraLoiString = newAnswer;
            } else {
                // Thêm dấu ',' vào chuỗi câu trả lời nếu chuỗi hiện tại không kết thúc bằng dấu ','
                if (!cauTraLoiString.endsWith(',')) {
                    cauTraLoiString += ',';
                }
                cauTraLoiString += newAnswer;

                // Sử dụng regex để xóa các dấu ',' liên tiếp (2 hoặc nhiều dấu ',') và thay thế bằng một dấu ','
                cauTraLoiString = cauTraLoiString.replace(/,+/g, ',').trim();
            }

            // Cập nhật giá trị của input nối chuỗi
            const cauHoiInput = document.getElementById('cauHoiInput');
            cauHoiInput.value = cauTraLoiString;

            // Gán sự kiện "change" cho radio button mới thêm
            const newRadio = newAnswerField.querySelector('.answer-radio');
            newRadio.addEventListener('change', $scope.showSelectedAnswer);

            // Tạo nút xóa cho input câu trả lời mới
            const deleteButton = document.createElement('button');
            deleteButton.classList.add('btn', 'btn-danger', 'ml-2');
            deleteButton.textContent = 'Xóa';
            deleteButton.addEventListener('click', function () {
                // Xóa input câu trả lời khi nút xóa được nhấn
                dynamicAnswers.removeChild(newAnswerField);
                answerCount--;

                // Cập nhật biến chuỗi câu trả lời sau khi xóa
                const deletedAnswerValue = newAnswerField.querySelector('input[type="text"]').value;
                cauTraLoiString = cauTraLoiString.replace(deletedAnswerValue + ',', '').trim();

                // Sử dụng regex để xóa các dấu ',' liên tiếp (2 hoặc nhiều dấu ',') và thay thế bằng một dấu ','
                cauTraLoiString = cauTraLoiString.replace(/,+/g, ',').trim();

                // Kiểm tra nếu chỉ còn một câu trả lời thì không thực hiện xóa nữa
                if (cauTraLoiString === newAnswer) {
                    cauTraLoiString = "";
                }

                // Cập nhật giá trị của input nối chuỗi
                cauHoiInput.value = cauTraLoiString;
            });

            // Thêm nút xóa vào input câu trả lời mới
            newAnswerField.appendChild(deleteButton);
        }
    }



    $scope.showSelectedAnswer = function () {
        const answerRadios = document.querySelectorAll('.answer-radio'); // Lấy tất cả các radio buttons

        answerRadios.forEach(radio => {
            if (radio.checked) {
                // Nếu radio được chọn, lấy giá trị của nó
                selectedAnswer = radio.nextElementSibling.value;
            }
        });

        if (selectedAnswer !== "") {
            alert(`Đáp án đã chọn: ${selectedAnswer}`);
        } else {
            alert("Bạn chưa chọn đáp án nào.");
        }
    }


    $scope.showSelectedAnswer = function () {
        const answerRadios = document.querySelectorAll('.answer-radio'); // Lấy tất cả các radio buttons

        answerRadios.forEach(radio => {
            if (radio.checked) {
                // Nếu radio được chọn, lấy giá trị của nó
                selectedAnswer = radio.nextElementSibling.value;
            }
        });

        if (selectedAnswer !== "") {
            alert(`Đáp án đã chọn: ${selectedAnswer}`);
        } else {
            alert("Bạn chưa chọn đáp án nào.");
        }
    }
    // Trong hàm $scope.create
    $scope.create = function () {
        if ($scope.formCauHoi.cauHoi === "" || $scope.formCauHoi.cauHoi === undefined) {
            alert("Vui lòng nhập câu hỏi.");
            return; // Dừng hàm nếu selectedAnswer không hợp lệ
        } else if (document.getElementById('cauHoiInput').value === "") {
            alert("Vui lòng nhập câu trả lời.");
            return; // Dừng hàm nếu cauTraLoi không hợp lệ
        } else if (selectedAnswer === "") {
            alert("Vui lòng chọn đáp án.");
            return; // Dừng hàm nếu selectedAnswer không hợp lệ
        }

        $http.get("/rest/admin/MucLuc/" + $scope.selectedMucLuc).then(function (resp) {
            $scope.MucLuc = resp.data;

            $scope.formCauHoi.cauTraLoi = document.getElementById('cauHoiInput').value;
            $scope.formCauHoi.dapAn = selectedAnswer;
            $scope.formCauHoi.ngayTao = new Date();
            $scope.formCauHoi.mucLuc = $scope.MucLuc;
            console.log($scope.formCauHoi);


            $http.post(`/rest/admin/CauHoi`, $scope.formCauHoi)
                .then(function (resp) {
                    $scope.itemsCauHoi.push(resp.data);
                    $scope.formCauHoi = {};
                    $scope.initialize();
                    alert("Thêm mới thành công");
                    $scope.reset();
                })
                .catch(function (err) {
                    alert("Error: " + err);
                });
        });

    };


    $scope.reset = function () {
        $scope.formCauHoi = {};
    }
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
        $scope.loadDocuments($scope.selectedCourse.id);

    };
    $scope.getSelectedMucLuc = function (selectedMucLuc) {
        console.log("Selected Muc Luc ID: " + selectedMucLuc);
        $scope.selectedMucLuc = selectedMucLuc;
    };

    $scope.delete = function (item) {
        var isConfirmed = confirm(`Bạn có chắc chắn muốn xóa tài liệu "${item.cauHoi}"?`);
        if (isConfirmed) {
            $http.delete(`/rest/admin/CauHoi/${item.id}`)
                .then(function (resp) {
                    var index = $scope.itemsCauHoi.findIndex(item => item.id == resp.data.id);
                    $scope.itemsCauHoi.splice(index, 1);
                    $scope.initialize();
                    alert("Xóa thành công");
                    $scope.reset();
                    $scope.loadDocuments();
                })
                .catch(function (err) {
                    alert("Error: " + err);
                });
        }
    }
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
