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

const baseURL = "http://localhost:8080/api";

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
    document.getElementById(
      "webiste-link"
    ).innerHTML = `<a href="${websiteLink}" target="_blank">${websiteLink}</a>`;
    document.getElementById("webiste-link").closest("tr").style.display =
      "block";
  } else {
    document.getElementById("webiste-link").closest("tr").style.display =
      "none";
  }

  if (linkedInLink) {
    document.getElementById(
      "linkedin-link"
    ).innerHTML = `<a href="${linkedInLink}" target="_blank">${linkedInLink}</a>`;
    document.getElementById("linkedin-link").closest("tr").style.display =
      "block";
  } else {
    document.getElementById("linkedin-link").closest("tr").style.display =
      "none";
  }
  openModal();
}

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

function openModal() {
  contactModal.show();
}

function closeModal() {
  contactModal.hide();
}
