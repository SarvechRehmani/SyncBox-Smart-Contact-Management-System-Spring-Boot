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
// document
//   .getElementById("password")
//   .addEventListener("input", validatePasswords);
document
  .getElementById("confirmPassword")
  .addEventListener("input", validatePasswords);

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
