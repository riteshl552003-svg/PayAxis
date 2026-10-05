// Toggle password visibility
function togglePassword() {
    const pwd = document.getElementById('password');
    const icon = document.getElementById('eyeIcon');
    if (!pwd) return;

    if (pwd.type === 'password') {
        pwd.type = 'text';
        icon.classList.replace('bi-eye', 'bi-eye-slash');
    } else {
        pwd.type = 'password';
        icon.classList.replace('bi-eye-slash', 'bi-eye');
    }
}

// Confirm before delete
function confirmDelete(msg) {
    return confirm(msg || 'Are you sure you want to delete this record?');
}

// Auto-hide alerts after 4 seconds
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.alert-auto-hide').forEach(el => {
        setTimeout(() => el.style.display = 'none', 4000);
    });
});