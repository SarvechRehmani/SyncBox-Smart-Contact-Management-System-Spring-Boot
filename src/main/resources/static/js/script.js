let currentTheme = getTheme();
// initial
document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

function changeTheme() {
  // Log the current theme for debugging
  console.log("Current theme:", currentTheme);

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

    // Log the new theme
    console.log("New theme:", currentTheme);

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

// VALIDATE PASSWORD
// Function to validate password match
function validatePasswords() {
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirmPassword").value;
  const confirmPasswordError = document.getElementById("confirmPasswordError");
  const signUpBtn = document.getElementById("signUpBtn");

  // Check if passwords match
  if (password !== confirmPassword) {
    confirmPasswordError.textContent = "Passwords do not match.";
    signUpBtn.disabled = true;
  } else {
    confirmPasswordError.textContent = ""; // Clear the error
    signUpBtn.disabled = false;
  }
}

// Add event listeners for dynamic validation
document
  .getElementById("password")
  .addEventListener("input", validatePasswords);
document
  .getElementById("confirmPassword")
  .addEventListener("input", validatePasswords);
