// Custom JavaScript for Student Portal

document.addEventListener("DOMContentLoaded", function () {
    // Auto-hide alerts after 5 seconds
    setTimeout(function () {
        const alerts = document.querySelectorAll(".alert");
        alerts.forEach(function (alert) {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);

    // Confirm delete actions
    const deleteLinks = document.querySelectorAll('a[href*="/delete/"]');
    deleteLinks.forEach(function (link) {
        link.addEventListener("click", function (event) {
            if (!confirm("Are you sure you want to delete this item?")) {
                event.preventDefault();
            }
        });
    });
    function renderStudentList(studentArray) {
        const studentList = document.getElementById("studentList");
        studentList.innerHTML = ""; // Clear existing rows

        studentArray.forEach(student => {
            const row = document.createElement("tr");
            row.innerHTML = `<td>${student.name}</td><td>${student.age}</td><td>${student.course}</td>`;
            studentList.appendChild(row);
        });
    }

// Initial render of the student list
    renderStudentList(students);

// Sort button event listener
    document.getElementById("sortButton").addEventListener("click", function () {
        const sortField = document.getElementById("sortField").value;
        const sortDir = document.getElementById("sortDir").value;

        // Sort the student array based on the selected field and direction
        students.sort((a, b) => {
            let valA = a[sortField];
            let valB = b[sortField];

            if (typeof valA === "string") {
                valA = valA.toLowerCase(); // Convert string values to lowercase for case-insensitive comparison
                valB = valB.toLowerCase();
            }

            if (valA < valB) return sortDir === "asc" ? -1 : 1;
            if (valA > valB) return sortDir === "asc" ? 1 : -1;
            return 0;
        });

        // Re-render the sorted student list
        renderStudentList(students);
    });


});