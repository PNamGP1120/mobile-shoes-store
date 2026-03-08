# User Flow - mobile-shoes-store

## 1. Flow đăng ký
Mở app
-> Chọn đăng ký
-> Nhập họ tên, email, số điện thoại, mật khẩu
-> Kiểm tra dữ liệu hợp lệ
-> Tạo tài khoản Firebase Auth
-> Tạo profile user trong Firestore
-> Chuyển tới màn hình đăng nhập hoặc home

## 2. Flow đăng nhập
Mở app
-> Chọn đăng nhập
-> Đăng nhập bằng Email hoặc Google hoặc Facebook
-> Firebase Auth xác thực
-> Lấy thông tin role trong Firestore
-> Điều hướng tới Home user hoặc Admin dashboard

## 3. Flow xem và mua sản phẩm
Mở Home
-> Xem danh mục hoặc tìm kiếm
-> Chọn sản phẩm
-> Xem chi tiết
-> Chọn size
-> Chọn số lượng
-> Thêm vào giỏ hàng local
-> Mở giỏ hàng
-> Chọn checkout
-> Nhập thông tin nhận hàng
-> Chọn phương thức thanh toán
-> Tạo order trên Firestore
-> Xóa giỏ hàng local
-> Xem lịch sử đơn hàng

## 4. Flow đánh giá sản phẩm
Mở lịch sử đơn hàng
-> Chọn đơn hàng đã giao
-> Chọn sản phẩm
-> Nhập rating và comment
-> Lưu review lên Firestore
-> Cập nhật rating trung bình sản phẩm

## 5. Flow admin
Đăng nhập admin
-> Vào dashboard
-> Chọn quản lý user / danh mục / sản phẩm / đơn hàng
-> Thực hiện thêm, sửa, xóa, cập nhật trạng thái
-> Xem thống kê