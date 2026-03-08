# Requirements - mobile-shoes-store

## 1. Mục tiêu hệ thống
Ứng dụng bán giày thể thao trên thiết bị di động, hỗ trợ người dùng xem sản phẩm, đặt hàng, đánh giá sản phẩm và hỗ trợ admin quản lý toàn bộ hệ thống.

## 2. Đối tượng sử dụng
- Guest
- User
- Admin

## 3. Chức năng phía Guest
- Xem danh sách sản phẩm
- Xem sản phẩm theo danh mục
- Xem chi tiết sản phẩm
- Tìm kiếm sản phẩm
- Xem đánh giá sản phẩm
- Đăng ký tài khoản
- Đăng nhập bằng Email
- Đăng nhập bằng Google
- Đăng nhập bằng Facebook

## 4. Chức năng phía User
- Tất cả chức năng của Guest
- Chọn size và số lượng sản phẩm
- Thêm sản phẩm vào giỏ hàng
- Xem giỏ hàng
- Cập nhật số lượng trong giỏ
- Xóa sản phẩm khỏi giỏ
- Đặt hàng
- Chọn phương thức thanh toán
- Xem lịch sử đơn hàng
- Xem chi tiết đơn hàng
- Đánh giá sản phẩm đã mua
- Đăng xuất

## 5. Chức năng phía Admin
- Đăng nhập admin
- Quản lý user
- Quản lý danh mục sản phẩm
- Quản lý sản phẩm
- Quản lý đơn hàng
- Xem thống kê sản phẩm bán chạy
- Xem thống kê đơn hàng theo ngày/tháng
- Xem thống kê theo user hoặc danh mục

## 6. Yêu cầu kỹ thuật
- Firebase Authentication cho Email, Google, Facebook
- Firestore lưu dữ liệu online
- Firebase Storage lưu ảnh
- SQLite/Room lưu giỏ hàng local
- Phân quyền theo role