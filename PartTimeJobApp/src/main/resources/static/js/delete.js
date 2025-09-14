function deleteObj(apiUrl, id) {
    if (confirm('Bạn có chắc chắn muốn xóa?')) {
        fetch(apiUrl + id, { method: 'DELETE' })
            .then(res => {
                if (res.ok) {
                    // Xóa thành công, làm mới trang hoặc xóa dòng khỏi bảng
                    location.reload();
                } else {
                    alert('Xóa thất bại!');
                }
            });
    }
}