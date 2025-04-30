document.addEventListener("DOMContentLoaded", function () {
    const toggleBtn = document.getElementById("toggleDarkMode");
    const htmlElement = document.documentElement; // Get the html element

    if (toggleBtn) {
        // Check for saved user preference or use system preference
        const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
        const savedMode = localStorage.getItem('darkMode');

        // Apply dark mode if previously set or if system prefers dark
        if (savedMode === 'enabled' || (savedMode === null && prefersDark)) {
            htmlElement.classList.add('dark-mode');
            toggleBtn.textContent = "Light Mode";
        }

        toggleBtn.addEventListener("click", function () {
            htmlElement.classList.toggle("dark-mode");

            // Update button text and save preference
            const isDark = htmlElement.classList.contains("dark-mode");
            toggleBtn.textContent = isDark ? "Light Mode" : "Dark Mode";
            localStorage.setItem('darkMode', isDark ? 'enabled' : 'disabled');
        });
    }
});