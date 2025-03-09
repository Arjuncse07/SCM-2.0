$(document).ready(function () {
    console.log("homeCategory.js loaded!");

    window.getHomeCategoryBasedOnId = function (checkbox) {
        var homeId = $(checkbox).attr("id"); // Get checkbox ID
        var categoryContainer = $("#categoryList_" + homeId); // Select the correct category div

        if ($(checkbox).is(":checked")) {
            categoryContainer.empty(); // Clear previous content

            // Find the selected home object
            var selectedHome = homeServiceObject.find(home => home.homeId === homeId);

            if (selectedHome && selectedHome.homeCategoriesList.length > 0) {
                $.each(selectedHome.homeCategoriesList, function (index, category) {
                    categoryContainer.append(
                        `<p>${category.homeCategoryName} - ₹${category.homeCategoryPrice}</p>`
                    );
                });
            } else {
                categoryContainer.append("<p>No categories available</p>");
            }
        } else {
            // Remove categories when checkbox is unchecked
            categoryContainer.empty();
        }
    };
});
