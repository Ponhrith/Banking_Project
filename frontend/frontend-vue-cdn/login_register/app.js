const app = Vue.createApp({
  data() {
    return {
      signUpMode: false,
      loginForm: {
        email: "",
        password: "",
      },
      registerForm: {
        fullname: "",
        address: "", 
        email: "",
        password: "",
        confirmPassword: "",
      },
      showLoginPassword: false,
      showRegisterPassword: false,
      showRegisterConfirmPassword: false,
      loginPasswordFieldType: "password",
      registerPasswordFieldType: "password",
      registerConfirmPasswordFieldType: "password",
      loginError: "",
      registerError: "",
      loginSuccess: false,
      registerSuccess: false,
    };
  },
  methods: {
    toggleMode(mode) {
      this.signUpMode = mode === "register";
    },
    togglePassword(form) {
      if (form === "login") {
        this.showLoginPassword = !this.showLoginPassword;
        this.loginPasswordFieldType = this.showLoginPassword
          ? "text"
          : "password";
      } else if (form === "register") {
        this.showRegisterPassword = !this.showRegisterPassword;
        this.registerPasswordFieldType = this.showRegisterPassword
          ? "text"
          : "password";
      } else if (form === "confirm") {
        this.showRegisterConfirmPassword = !this.showRegisterConfirmPassword;
        this.registerConfirmPasswordFieldType = this.showRegisterConfirmPassword
          ? "text"
          : "password";
      }
    },
    isValidEmail(email) {
      const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      return regex.test(email);
    },
    isValidPassword(password) {
      const regex =
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
      return regex.test(password);
    },
    isValidFullName(fullname) {
      const regex = /^[a-zA-Z]+[\s][a-zA-Z]+$/;
      return regex.test(fullname);
    },

    async submitLoginForm() {
      // Reset login error message
      this.loginError = "";

      // Check if email and password fields are empty
      if (!this.loginForm.email || !this.loginForm.password) {
        this.loginError = "Fields cannot be empty!"; // Set login error message
        setTimeout(() => {
          this.loginError = "";
        }, 3000);
        return;
      }

      try {
        const response = await fetch(
          "http://localhost:8080/api/v1/auth/login",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(this.loginForm),
          }
        );

        if (response.ok) {
          this.loginSuccess = true; // Set login success to true
          this.loginForm = { email: '', password: '' }; // Reset login form
          setTimeout(() => {
            this.loginSuccess = false; // Reset login success after 3 seconds
            window.location.href = "/dashboard/dashboard.html"; // Navigate to another page after 3 seconds
          }, 2000);
        } else {
          this.loginError = "Invalid email or password!"; // Set login error message
          setTimeout(() => {
            this.loginError = "";
          }, 3000);
        }
      } catch (error) {
        console.error("Error:", error);
        alert("An error occurred while logging in."); // Show generic error message
      }
    },

    async submitRegisterForm() {
      // Check if passwords match
      this.registerError = "";

      // Check if any fields are empty
      if (
        !this.registerForm.fullname ||
        !this.registerForm.address ||
        !this.registerForm.email ||
        !this.registerForm.password ||
        !this.registerForm.confirmPassword
      ) {
        this.registerError = "Fields cannot be empty!"; // Set register error message
        setTimeout(() => {
          this.registerError = "";
        }, 3000);
        return;
      }

      if (this.registerForm.password !== this.registerForm.confirmPassword) {
        this.registerError = "Passwords do not match!"; // Set register error message
        setTimeout(() => {
          this.registerError = "";
        }, 3000);
        return;
      }

      // Check if full name is valid
      if (!this.isValidFullName(this.registerForm.fullname)) {
        this.registerError = "Invalid full name format!"; // Set register error message
        setTimeout(() => {
          this.registerError = "";
        }, 3000);
        return;
      }

      // Check if email is valid
      if (!this.isValidEmail(this.registerForm.email)) {
        this.registerError = "Invalid email address"; // Set register error message
        setTimeout(() => {
          this.registerError = "";
        }, 3000);
        return;
      }

      // Check if password is valid
      if (!this.isValidPassword(this.registerForm.password)) {
        this.registerError = "Invalid password format!"; // Set register error message
        setTimeout(() => {
          this.registerError = "";
        }, 3000);
        return;
      }

      // If passwords match, proceed with registration
      try {
        const emailCheckResponse = await fetch(
          `http://localhost:8080/api/v1/profile/check-email?email=${encodeURIComponent(
            this.registerForm.email
          )}`
        );
        if (emailCheckResponse.ok) {
          const emailExists = await emailCheckResponse.json();
          if (emailExists) {
            this.registerError = "Email already exists";
            setTimeout(() => {
              this.registerError = "";
            }, 3000);
            return;
          }
          // Proceed with registration if email does not exist
          const registerResponse = await fetch(
            "http://localhost:8080/api/v1/profile",
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify(this.registerForm),
            }
          );
          const registerData = await registerResponse.json();
          if (registerResponse.ok) {
            this.registerSuccess = true; // Set register success to true
            this.registerForm = { // Clear the form
              fullname: '',
              address: '',
              email: '',
              password: '',
              confirmPassword: ''
            };
            setTimeout(() => {
              this.registerSuccess = false; // Reset register success after 3 seconds
              window.location.href = "/dashboard/dashboard.html"; // Navigate to another page after 3 seconds
            }, 2000);
          } else {
            alert("Registration failed: " + registerData.message); // Show error message
          }
        } else {
          alert("Failed to check email existence"); // Show error message
        }
      } catch (error) {
        console.error("Error:", error);
        alert("An error occurred while registering."); // Show generic error message
      }
      
    },
  },
});

app.mount("#app");
