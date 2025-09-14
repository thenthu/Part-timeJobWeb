document.addEventListener("DOMContentLoaded", function () {
    const salarySlider = document.getElementById("salaryRange");

    if (salarySlider) {
        noUiSlider.create(salarySlider, {
            start: [2000000, 10000000],
            connect: true,
            step: 100000,
            range: {
                min: 1000000,
                max: 30000000
            },
            tooltips: true,
            format: {
                to: function (value) {
                    return Math.round(value).toLocaleString('vi-VN') + ' VNĐ';
                },
                from: function (value) {
                    return Number(value.replace(' VNĐ', ''));
                }
            }
        });

        const minSalaryInput = document.getElementById("iMinSalary");
        const maxSalaryInput = document.getElementById("iMaxSalary");

        salarySlider.noUiSlider.on("update", function (values, handle) {
            minSalaryInput.value = values[0].replace(' VNĐ', '').replace(/[^\d]/g, '');
            maxSalaryInput.value = values[1].replace(' VNĐ', '').replace(/[^\d]/g, '');
        });
    }
});
