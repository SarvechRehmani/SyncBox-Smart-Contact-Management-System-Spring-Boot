// set the modal menu element
const $viewContactModal = document.getElementById("viewContactModal");

// options with default values
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  closable: true,
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {
    console.log("modal is hidden");
  },
  onShow: () => {
    console.log("modal is shown");
  },
  onToggle: () => {
    console.log("modal has been toggled");
  },
};

// instance options object
const instanceOptions = {
  id: "viewContactModal",
  override: true,
};

const contactModal = new Modal($viewContactModal, options, instanceOptions);

async function fetchContact(contactId) {
  try {
    const response = await (
      await fetch(`http://localhost:8080/api/contacts/${contactId}`)
    ).json();

    const name = response.name;
    const email = response.email;
    const phone = response.phoneNumber;
    const address = response.address;
    const profile = response.picture;
    const description = response.description;
    const fav = response.favorite;
    const websiteLink = response.websiteLink;
    const linkedInLink = response.linkedInLink;

    document.getElementById("name").innerHTML = name;
    document.getElementById("email").innerHTML = email;
    document.getElementById("phone").innerHTML = phone;
    document.getElementById("address").innerHTML = address;
    document.getElementById("picture").src = profile;
    document.getElementById("description").innerHTML = description;
    document.getElementById("fav").checked = !!fav;
    document.getElementById("webiste-link").innerHTML = websiteLink;
    document.getElementById("linkedin-link").innerHTML = linkedInLink;

    // Check for availability and populate the table
    if (phone) {
      document.getElementById("phone").innerHTML = phone;
    } else {
      document.getElementById("phone").closest("tr").style.display = "none";
    }

    if (address) {
      document.getElementById("address").innerHTML = address;
    } else {
      document.getElementById("address").closest("tr").style.display = "none";
    }

    if (websiteLink) {
      document.getElementById(
        "webiste-link"
      ).innerHTML = `<a href="${websiteLink}" target="_blank">${websiteLink}</a>`;
    } else {
      document.getElementById("webiste-link").closest("tr").style.display =
        "none";
    }

    if (linkedInLink) {
      document.getElementById(
        "linkedin-link"
      ).innerHTML = `<a href="${linkedInLink}" target="_blank">${linkedInLink}</a>`;
    } else {
      document.getElementById("linkedin-link").closest("tr").style.display =
        "none";
    }

    // Optionally handle description
    if (description) {
      document.getElementById("description").innerHTML = description;
    } else {
      document.getElementById("description").style.display = "none";
    }

    contactModal.show();
  } catch (error) {
    console.log("Error ", error);
  }
}

async function deleteContact(id) {
  const data = await await fetch(`http://localhost:8080/api/contacts/${id}`, {
    method: "DELETE",
  }).json();
  console.log(data);
}

function closeModal() {
  contactModal.hide();
}
