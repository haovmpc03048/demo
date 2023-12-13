var app = angular.module('myApp', []);
app.controller('KhoaHocController', function($scope, $http, $window) {
	$scope.khoaHocList = []; // Khởi tạo mảng rỗng để lưu danh sách khóa học

	// Gọi API để lấy danh sách khóa học từ server
	$http.get('/api/khoahoc')
		.then(function(response) {
			$scope.khoaHocList = response.data;
		})
		.catch(function(error) {
			console.error('Error fetching data:', error);
		});

	// Hàm chuyển đến trang chi tiết khi nhấn vào tên khoá học
	$scope.goToKhoaHocDetail = function(khoaHocId) {
		$window.location.href = '/detail/' + khoaHocId;
	};
	// Định nghĩa hàm loadProductsByCategory và các biến categories, selectedCategory, products ở đây.
	// Ví dụ:

	$scope.categories = [];
	$scope.products = [];
	$scope.selectedCategory = null;

	$scope.loadProductsByCategory = function(category) {
		$http.get('/api/products/' + category.loai)
			.then(function(response) {
				$scope.selectedCategory = category;
				$scope.products = response.data;
			});
	};

	// Hàm để load danh mục sản phẩm ban đầu
	$scope.loadCategories = function() {
		$http.get('/api/categories')
			.then(function(response) {
				$scope.categories = response.data;
			});
	};

	// Gọi hàm loadCategories khi trang web được tải
	$scope.loadCategories();
});
app.controller('LoaiController', function($scope, $http) {
	$scope.loaiList = []; // Khởi tạo mảng rỗng để lưu danh sách Loại sản phẩm

	// Gọi API để lấy danh sách Loại sản phẩm từ server
	$http.get('/api/loai')
		.then(function(response) {
			$scope.loaiList = response.data;
		})
		.catch(function(error) {
			console.error('Error fetching Loai data:', error);
		});
});

app.controller('DetailController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
	var productId = $routeParams.id; // Lấy id sản phẩm từ URL

	$http.get('/api/khoahoc/' + productId) // Gọi API Spring Boot để lấy thông tin chi tiết sản phẩm
		.then(function(response) {
			$scope.product = response.data;
		})
		.catch(function(error) {
			console.error('Error fetching product detail:', error);
		});

}]);

