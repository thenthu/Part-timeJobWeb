window.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.getElementById('sidebar');
    if (sidebar) {
        sidebar.classList.add('active');
        document.body.classList.add('sidebar-open');
        setTimeout(() => {
            const sidebarToggle = document.getElementById('sidebarToggle');
            if (sidebarToggle) {
                sidebarToggle.onclick = () => {
                    const isOpen = sidebar.classList.contains('active');
                    sidebar.classList.toggle('active');
                    document.body.classList.toggle('sidebar-open', !isOpen);
                };
            } else {
                console.warn("Không tìm thấy #sidebarToggle");
            }
        }, 100);
    } else {
        console.warn("Không tìm thấy #sidebar");
    }
});

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') {
        document.getElementById('sidebar')?.classList.remove('active');
        document.body.classList.remove('sidebar-open');
    }
});