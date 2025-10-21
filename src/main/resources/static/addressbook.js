$(document).ready(function () {

    // Load all address books from API
    function loadAddressBooks() {
        $.get("/api/addressbooks", function (data) {
            $("#addressbooks").empty();
            data.forEach(renderAddressBook);
        });
    }

    // Render a single address book
    function renderAddressBook(ab) {
        const template = $("#addressbook-template").contents().clone();
        template.find(".ab-id").text(ab.id);

        // Delete AddressBook
        template.find(".delete-ab").click(function () {
            $.ajax({
                url: "/api/addressbooks/" + ab.id,
                type: "DELETE",
                success: loadAddressBooks
            });
        });

        // Load buddies
        ab.buddies.forEach(buddy => {
            template.find(".buddy-list").append(
                `<li>${buddy.name} (${buddy.phone})${buddy.address ? " [" + buddy.address + "]" : ""} 
                <button class="delete-buddy" data-id="${buddy.id}">X</button></li>`
            );
        });

        // Delete buddy
        template.find(".delete-buddy").click(function () {
            const buddyId = $(this).data("id");
            $.ajax({
                url: `/api/addressbooks/${ab.id}/buddies/${buddyId}`,
                type: "DELETE",
                success: loadAddressBooks
            });
        });

        // Add buddy
        template.find(".add-buddy").click(function () {
            const name = template.find(".buddy-name").val();
            const phone = template.find(".buddy-phone").val();
            const address = template.find(".buddy-address").val();
            if (!name || !phone) return alert("Name and phone required.");

            $.ajax({
                url: `/api/addressbooks/${ab.id}/buddies`,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({name, phone, address}),
                success: loadAddressBooks,
                error: function (xhr, status, err) {
                    console.error("Add buddy failed:", status, err, xhr.responseText);
                    alert("Failed to add buddy. See console for details.");
                }
            });
        });

        $("#addressbooks").append(template);
    }

    // Create new AddressBook
    $("#new-addressbook").click(function () {
        $.post("/api/addressbooks", {}, loadAddressBooks);
    });

    // Initial load
    loadAddressBooks();
});
