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

async function fetchContact(contactId) {
  const response = await (
    await fetch(`http://localhost:8080/api/contacts/${contactId}`)
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

  contactModal.show();
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
        await fetch(`http://localhost:8080/api/contacts/${id}`, {
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

async function modal(contactId) {
  const response = await (
    await fetch(`http://localhost:8080/api/contacts/${contactId}`)
  ).json();

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

  Swal.fire({
    html: `
      <div class="flex items-center justify-center flex-col">
        <img
          id="picture"
          class="rounded-full border border-gray-200 dark:border-gray-600 w-40 sm:w-60 h-40 sm:h-60 mb-3"
          src="${picture}"
          alt="Profile Picture"
          onerror="'/images/Default.png'"
        />
        <div class="inline-flex">
          <h3
            id="name"
            class="text-lg sm:text-xl font-semibold text-gray-900 dark:text-white uppercase"
          >${name}</h3>
          <span class="ml-3">
            <label class="fav-container">
              <input
                type="checkbox"
                th:checked="${!!favorite}"
                id="fav"
                class="favorite-checkbox"
                disabled
              />
              <svg
                height="15px"
                viewBox="0 0 24 24"
                width="15px"
              >
                <path
                  d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"
                />
              </svg>
            </label>
          </span>
        </div>
        <p id="email">${email}</p>
      </div>
      <div class="p-4 sm:p-5 space-y-4">
        <p
          id="description"
          class="text-sm sm:text-base leading-relaxed text-gray-500 dark:text-gray-400"
        >${description}</p>
        <div class="overflow-x-auto">
          <table
            class="w-full text-sm text-left rounded-lg overflow-hidden text-gray-500 dark:text-gray-400"
          >
            <tbody>
              <tr class="bg-gray-100 dark:bg-gray-800">
                <th
                  scope="row"
                  class="px-4 sm:px-6 py-2 sm:py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                >
                  <i class="fa-solid fa-location-dot fa-lg"></i> Address
                </th>
                <td id="address" class="px-4 sm:px-6 py-2 sm:py-4">${address}</td>
              </tr>
              <tr class="bg-gray-100 dark:bg-gray-800">
                <th
                  scope="row"
                  class="px-4 sm:px-6 py-2 sm:py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                >
                  <i class="fa-solid fa-phone fa-lg"></i> Phone
                </th>
                <td id="phone" class="px-4 sm:px-6 py-2 sm:py-4">${phoneNumber}</td>
              </tr>
              <tr class="bg-gray-100 dark:bg-gray-800">
                <th
                  scope="row"
                  class="px-4 sm:px-6 py-2 sm:py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                >
                  <i class="fa-solid fa-earth-americas fa-lg"></i> Website
                </th>
                <td id="webiste-link" class="px-4 sm:px-6 py-2 sm:py-4">${websiteLink}</td>
              </tr>
              <tr th:if='${linkedInLink}' class="bg-gray-100 dark:bg-gray-800">
                <th
                  scope="row"
                  class="px-4 sm:px-6 py-2 sm:py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                >
                  <i class="fa-brands fa-linkedin fa-lg"></i> LinkedIn
                </th>
                <td id="linkedin-link" class="px-4 sm:px-6 py-2 sm:py-4">${linkedInLink}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    `,
    showCloseButton: true,
    showConfirmButton: false,
    customClass: {
      popup: "rounded-lg shadow dark:bg-gray-700 max-w-full sm:max-w-lg",
    },
  });
}

function closeModal() {
  contactModal.hide();
}
