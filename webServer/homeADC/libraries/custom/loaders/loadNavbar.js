async function loadNavbar() {
    const response = await fetch('/libraries/templates/navbar.html');
    const html = await response.text();
    const container = document.getElementById('navbar-placeholder');
    container.innerHTML = html;
}



// Automatically load navbar
document.addEventListener('DOMContentLoaded', loadNavbar);