// set the modal menu element
const $viewContactModal = document.getElementById("viewContactModal");

// options with default values
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {},
  onShow: () => {},
  onToggle: () => {},
};

// instance options object
const instanceOptions = {
  id: "viewContactModal",
  override: true,
};

const contactModal = new Modal($viewContactModal, options, instanceOptions);

const baseURL = "http://192.168.100.7:8080/api";

// Get single contact
async function fetchContact(contactId) {
  const response = await (
    await fetch(`${baseURL}/contacts/${contactId}`)
  ).json();
  // Destructure necessary variables from the response
  const {
    name,
    email,
    phoneNumber,
    address,
    picture,
    description,
    favorite,
    websiteLink,
    linkedInLink,
  } = response;

  // Populate elements
  document.getElementById("name").innerHTML = name;
  document.getElementById("email").innerHTML = email;
  document.getElementById("picture").src = picture;
  document.getElementById("fav").checked = !!favorite;

  if (description) {
    document.getElementById("description").innerHTML = description;

    document.getElementById("description").style.display = "block";
  } else {
    document.getElementById("description").style.display = "none";
  }

  if (address) {
    document.getElementById("address").innerHTML = address;

    document.getElementById("address").closest("tr").style.display = "block";
  } else {
    document.getElementById("address").closest("tr").style.display = "none";
  }

  if (phoneNumber) {
    document.getElementById("phone").innerHTML = phoneNumber;
    document.getElementById("phone").closest("tr").style.display = "block";
  } else {
    document.getElementById("phone").closest("tr").style.display = "none";
  }

  if (websiteLink) {
    var wbLink = websiteLink.startsWith("https://")
      ? websiteLink
      : "https://" + websiteLink;
    document.getElementById(
      "webiste-link"
    ).innerHTML = `<a href="${wbLink}" target="_blank">${wbLink}</a>`;
    document.getElementById("webiste-link").closest("tr").style.display =
      "block";
  } else {
    document.getElementById("webiste-link").closest("tr").style.display =
      "none";
  }

  if (linkedInLink) {
    var lnLink = websiteLink.startsWith("https://")
      ? websiteLink
      : "https://" + websiteLink;
    document.getElementById(
      "linkedin-link"
    ).innerHTML = `<a href="${lnLink}" target="_blank">${lnLink}</a>`;
    document.getElementById("linkedin-link").closest("tr").style.display =
      "block";
  } else {
    document.getElementById("linkedin-link").closest("tr").style.display =
      "none";
  }
  openModal();
}

function openModal() {
  contactModal.show();
}

function closeModal() {
  contactModal.hide();
}

// Delete contact
function deleteContact(id, contactName) {
  const isDarkMode = document.documentElement.classList.contains("dark");
  Swal.fire({
    title: `<span style="color: ${
      isDarkMode ? "#E2E8F0" : "#1E293B"
    }">Are you sure? You want to delete this contact?</span>`,
    html: "<p class='text-center'>You won't be able to revert this!</p>",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#D33",
    cancelButtonColor: "#9333EA",
    background: isDarkMode ? "#374151" : "#fff",
    confirmButtonText: "Yes, delete it!",
    backdrop: "rgba(13, 18, 28, 0.7)",
  }).then(async (result) => {
    if (result.isConfirmed) {
      const data = await (
        await fetch(`${baseURL}/contacts/${id}`, {
          method: "DELETE",
        })
      ).json();
      Swal.fire({
        title: `<span style="color: ${
          isDarkMode ? "#E2E8F0" : "#1E293B"
        }">${contactName} Deleted!</span>`,
        html: "<p class='text-center'>Your Contact has been deleted.</p>",
        icon: "success",
        background: isDarkMode ? "#374151" : "#fff",
      }).then(() => {
        window.location.reload();
      });
    }
  });
}

// Update favorite status
function updateFavoriteStatus(contactId) {
  console.log("updateFavoriteStatus");
  const url = `contacts/update-favorite/${contactId}`;

  fetch(url, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to update favorite status");
      }
      return response.json();
    })
    .then((data) => {
      console.log("Favorite status updated:", data);
    })
    .catch((error) => {
      console.error("Error updating favorite status:", error);
    });
}

// Export Contact in Excel
function exportContacts() {
  TableToExcel.convert(document.getElementById("table"), {
    name: "contacts.xlsx",
    sheet: { name: "Sheet 1" },
  });
}

// Heart Shape Checkbox Javascript
document.addEventListener("DOMContentLoaded", () => {
  // Select all favorite checkboxes
  const favoriteCheckboxes = document.querySelectorAll(".favorite-checkbox");

  favoriteCheckboxes.forEach((checkbox) => {
    checkbox.addEventListener("change", (event) => {
      const contactId = event.target.getAttribute("data-contact-id");

      // Call the update function
      updateFavoriteStatus(contactId);
    });
  });
});

// Drop down page size selection
document.querySelectorAll("#selectSizeAction a").forEach((item) => {
  item.addEventListener("click", (event) => {
    event.preventDefault();
    const selectedValue = item.getAttribute("data-value");
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set("size", selectedValue);
    currentUrl.searchParams.set("page", 0); // Reset to the first page
    window.location.href = currentUrl.toString();
  });
});
