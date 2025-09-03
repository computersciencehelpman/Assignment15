document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector("form");

  const username = document.getElementById("username");
  const email = document.getElementById("email");
  const password = document.getElementById("password");
  const confirmPassword = document.getElementById("confirmPassword");
  const dateOfBirth = document.getElementById("dateOfBirth");
  const phoneInput = document.getElementById("phoneNumber");


  phoneInput.addEventListener("input", function () {
    phoneInput.value = formatPhoneNumber(phoneInput.value);
    validatePhoneNumber();
  });


  phoneInput.addEventListener("keypress", function (e) {
    const allowedChars = /[\d\s()+-]/;
    if (!allowedChars.test(e.key)) {
      e.preventDefault();
    }
  });

  const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{6,}$/;

  function validatePasswordStrength() {
    const pwd = password.value;

    const rules = [
      { id: "rule-lowercase", regex: /[a-z]/ },
      { id: "rule-uppercase", regex: /[A-Z]/ },
      { id: "rule-digit", regex: /\d/ },
      { id: "rule-special", regex: /[@$!%*?&]/ },
      { id: "rule-length", regex: /.{6,}/ }
    ];

    let isValid = true;

    rules.forEach(rule => {
      const element = document.getElementById(rule.id);
      if (rule.regex.test(pwd)) {
        element.textContent = "✅ " + element.textContent.slice(2);
        element.style.color = "green";
      } else {
        element.textContent = "❌ " + element.textContent.slice(2);
        element.style.color = "red";
        isValid = false;
      }
    });

    password.setCustomValidity(isValid ? "" : "Invalid password");
  }

  function validateConfirmPassword() {
    const ruleMatch = document.getElementById("rule-match");

    if (confirmPassword.value === "") {
      ruleMatch.textContent = "❌ Required";
      ruleMatch.style.color = "red";
      confirmPassword.setCustomValidity("Required");
    } else if (password.value !== confirmPassword.value) {
      ruleMatch.textContent = "❌ Must match the password above";
      ruleMatch.style.color = "red";
      confirmPassword.setCustomValidity("Mismatch");
    } else {
      ruleMatch.textContent = "✅ Matches password";
      ruleMatch.style.color = "green";
      confirmPassword.setCustomValidity("");
    }
  }

  function validateDOB() {
    const dob = new Date(dateOfBirth.value);
    const today = new Date();
    const age = today.getFullYear() - dob.getFullYear();
    const m = today.getMonth() - dob.getMonth();
    const validAge = age > 18 || (age === 18 && m >= 0) ? age <= 100 : false;

    const message = document.getElementById("dobStatus");

    if (dateOfBirth.value === "") {
      message.textContent = "❌ Required";
      message.style.color = "red";
      dateOfBirth.setCustomValidity("Required");
    } else if (!validAge) {
      message.textContent = "❌ You must be between 18 and 100 years old";
      message.style.color = "red";
      dateOfBirth.setCustomValidity("Invalid age");
    } else {
      message.textContent = "✅ Age is valid";
      message.style.color = "green";
      dateOfBirth.setCustomValidity("");
    }
  }

  function formatPhoneNumber(value) {
    const digits = value.replace(/\D/g, "").substring(0, 10);
    const parts = [];

    if (digits.length > 0) parts.push("(" + digits.substring(0, 3));
    if (digits.length >= 4) parts.push(") " + digits.substring(3, 6));
    if (digits.length >= 7) parts.push("-" + digits.substring(6, 10));

    return parts.join("");
  }

  function validatePhoneNumber() {
    const phoneStatus = document.getElementById("phoneStatus");
    const serverError = document.getElementById("phoneError");

    const phoneRegex = /^(\+?1\s?)?(\(?[2-9][0-9]{2}\)?)[\s.-]?[0-9]{3}[\s.-]?[0-9]{4}$/;
    const value = phoneInput.value.trim();

    if (value === "") {
      phoneStatus.textContent = "❌ Required";
      phoneStatus.style.color = "red";
      phoneInput.setCustomValidity("Required");
    } else if (!phoneRegex.test(value)) {
      phoneStatus.textContent = "❌ Invalid US phone number";
      phoneStatus.style.color = "red";
      phoneInput.setCustomValidity("Invalid phone number");
    } else {
      phoneStatus.textContent = "✅ Valid US phone number";
      phoneStatus.style.color = "green";
      phoneInput.setCustomValidity("");
      if (serverError) {
        serverError.textContent = "";
        serverError.style.display = "none";
      }
    }
  }

  async function checkUsernameExists() {
    const res = await fetch(`/api/check-username?username=${encodeURIComponent(username.value)}`);
    const exists = await res.json();
    const message = document.getElementById("usernameStatus");
    const serverError = document.getElementById("usernameError");

    if (username.value.trim() === "") {
      message.textContent = "❌ Required";
      message.style.color = "red";
      username.setCustomValidity("Required");
    } else if (exists) {
      message.textContent = "❌ Username is already taken";
      message.style.color = "red";
      username.setCustomValidity("Username taken");
    } else {
      message.textContent = "✅ Username available";
      message.style.color = "green";
      username.setCustomValidity("");
      if (serverError) {
        serverError.textContent = "";
        serverError.style.display = "none";
      }
    }
  }

  async function checkEmailExists() {
    const res = await fetch(`/api/check-email?email=${encodeURIComponent(email.value)}`);
    const exists = await res.json();
    const message = document.getElementById("emailStatus");
    const serverError = document.getElementById("emailError");

    if (email.validity.typeMismatch) {
      message.textContent = "❌ Invalid email format";
      message.style.color = "red";
      email.setCustomValidity("Invalid format");
    } else if (exists) {
      message.textContent = "❌ Email is already registered";
      message.style.color = "red";
      email.setCustomValidity("Email taken");
    } else {
      message.textContent = "✅ Email is available";
      message.style.color = "green";
      email.setCustomValidity("");
      if (serverError) {
        serverError.textContent = "";
        serverError.style.display = "none";
      }
    }
  }

  username.addEventListener("blur", checkUsernameExists);
  email.addEventListener("blur", checkEmailExists);
  password.addEventListener("input", validatePasswordStrength);
  confirmPassword.addEventListener("input", validateConfirmPassword);
  dateOfBirth.addEventListener("blur", validateDOB);
  phoneInput.addEventListener("input", validatePhoneNumber);
  phoneInput.addEventListener("blur", validatePhoneNumber);

  form.addEventListener("submit", async function (e) {
    validatePasswordStrength();
    validateConfirmPassword();
    validateDOB();
    validatePhoneNumber();
    await checkUsernameExists();
    await checkEmailExists();

    if (!form.checkValidity()) {
      e.preventDefault();
    }
  });
});
