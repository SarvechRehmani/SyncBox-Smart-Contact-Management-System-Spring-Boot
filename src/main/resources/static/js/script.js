// initial
document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

function changeTheme() {
  // Log the current theme for debugging
  let currentTheme = getTheme();
  // Validate currentTheme before adding to the DOM
  if (currentTheme === "dark" || currentTheme === "light") {
    // set to web page
    document.querySelector("html").classList.add(currentTheme);
  } else {
    console.error("Invalid theme:", currentTheme);
    currentTheme = "light"; // Default to light if the theme is invalid
    document.querySelector("html").classList.add(currentTheme);
  }

  // set the listener to change theme button
  const changeThemeBtn = document.querySelector("#changeThemeBtn");
  if (currentTheme == "light") {
    changeThemeBtn.querySelector("i").classList.remove("fa-sun");
    changeThemeBtn.querySelector("i").classList.add("fa-moon");
    changeThemeBtn.querySelector("span").textContent = "Dark";
  } else {
    changeThemeBtn.querySelector("i").classList.remove("fa-moon");
    changeThemeBtn.querySelector("i").classList.add("fa-sun");
    changeThemeBtn.querySelector("span").textContent = "Light";
  }

  changeThemeBtn.addEventListener("click", (event) => {
    const oldTheme = currentTheme;

    if (currentTheme === "dark") {
      currentTheme = "light";
    } else {
      currentTheme = "dark";
    }

    // set theme to localstorage
    setTheme(currentTheme);

    // remove old theme
    document.querySelector("html").classList.remove(oldTheme);

    // set new theme
    document.querySelector("html").classList.add(currentTheme);

    // Chnage Text and icon
    if (currentTheme == "light") {
      changeThemeBtn.querySelector("i").classList.remove("fa-sun");
      changeThemeBtn.querySelector("i").classList.add("fa-moon");
      changeThemeBtn.querySelector("span").textContent = "Dark";
    } else {
      changeThemeBtn.querySelector("i").classList.remove("fa-moon");
      changeThemeBtn.querySelector("i").classList.add("fa-sun");
      changeThemeBtn.querySelector("span").textContent = "Light";
    }
  });
}

// set theme to localstorage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

// get theme from localstorage
function getTheme() {
  let theme = localStorage.getItem("theme");
  // Return 'light' if theme is null or invalid
  if (theme === "dark" || theme === "light") {
    return theme;
  }
  return "light"; // Default to light
}

// Display iamge in image container in add contact form and update contact form
let fileInput = document.getElementById("contact-image");
let imagePreview = document.getElementById("image-preview");
let imgContainer = document.getElementById("img-container");
fileInput.addEventListener("change", function (event) {
  var file = event.target.files[0];
  let reader = new FileReader();
  reader.onload = function () {
    imagePreview.src = reader.result;
  };
  reader.readAsDataURL(file);
  imgContainer.classList.remove("hidden");
  imgContainer.classList.add("flex");
});

// Show loading icon on submit button in add contact form and update contact form
function showLoadingIcon(buttonId) {
  const submitBtn = document.getElementById(buttonId);

  submitBtn.innerHTML = `<i class="fa-solid fa-spinner fa-spin fa-xl"></i> Loading...`;
  submitBtn.disabled = true; // Disable the button to prevent multiple clicks
  submitBtn.style.backgroundColor = "rgb(192 132 252)";

  // Allow the form to submit
  const form = event.target.closest("form");
  if (form) {
    form.submit();
  }
}

// Password visibility toggle
function togglePasswordVisibility(fieldId, eyeIcon) {
  const passwordField = document.getElementById(fieldId);
  if (passwordField.type === "password") {
    passwordField.type = "text";
    eyeIcon.classList.remove("fa-eye");
    eyeIcon.classList.add("fa-eye-slash");
  } else {
    passwordField.type = "password";
    eyeIcon.classList.remove("fa-eye-slash");
    eyeIcon.classList.add("fa-eye");
  }
}
