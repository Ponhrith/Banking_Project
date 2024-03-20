<template>
     <div
        class="alert alert-success text-center"
        role="alert"
        v-if="loginSuccess"
      >
        <i class="fas fa-check-circle"></i> Login successful!
      </div>
      <div
        class="alert alert-success text-center"
        role="alert"
        v-if="registerSuccess"
      >
        <i class="fas fa-check-circle"></i> Registration successful!
      </div>
      <div class="custom-container" :class="{ 'sign-up-mode': signUpMode }">
        <div class="custom-forms-container">
          <div class="signin-signup">
            <form
              v-if="!signUpMode"
              class="sign-in-form"
              @submit.prevent="submitLoginForm"
            >
              <h2 class="title">Login</h2>
              <div class="custom-input-field">
                <i class="fas fa-envelope"></i>
                <input
                  type="text"
                  placeholder="Email *"
                  v-model="loginForm.email"
                />
              </div>
              <div class="custom-input-field">
                <i class="fas fa-lock"></i>
                <input
                  :type="loginPasswordFieldType"
                  placeholder="Password *"
                  v-model="loginForm.password"
                />
                <span
                  class="password-toggle-icon"
                  @click="togglePassword('login')"
                  ><i
                    class="fas"
                    :class="showLoginPassword ? 'fa-eye-slash' : 'fa-eye'"
                  ></i
                ></span>
              </div>
              <p class="error-message" v-if="loginError">{{ loginError }}</p>
              <!-- Display login error message -->
              <input type="submit" value="Login" class="btnn solid" />
            </form>

            <form v-else class="sign-up-form">
              <h2 class="title">Register</h2>
              <div class="custom-input-field">
                <i class="fas fa-user"></i>
                <input
                  type="text"
                  placeholder="Full Name *"
                  v-model="registerForm.fullname"
                />
              </div>
              <div class="custom-input-field">
                <i class="fas fa-address-book"></i>
                <input
                  type="text"
                  placeholder="Address *"
                  v-model="registerForm.address"
                />
              </div>
              <div class="custom-input-field">
                <i class="fas fa-envelope"></i>
                <input
                  type="email"
                  placeholder="Email *"
                  v-model="registerForm.email"
                />
              </div>
              <div class="custom-input-field">
                <i class="fas fa-lock"></i>
                <input
                  :type="registerPasswordFieldType"
                  placeholder="Password *"
                  v-model="registerForm.password"
                />
                <span
                  class="password-toggle-icon"
                  @click="togglePassword('register')"
                  ><i
                    class="fas"
                    :class="showRegisterPassword ? 'fa-eye-slash' : 'fa-eye'"
                  ></i
                ></span>
              </div>
              <div
                class="custom-input-field"
              >
                <i class="fas fa-lock"></i>
                <input
                  :type="registerConfirmPasswordFieldType"
                  placeholder="Confirm Password *"
                  v-model="registerForm.confirmPassword"
                />
                <span
                  class="password-toggle-icon"
                  @click="togglePassword('confirm')"
                  ><i
                    class="fas"
                    :class="showRegisterConfirmPassword ? 'fa-eye-slash' : 'fa-eye'"
                  ></i
                ></span>
              </div>
              <p class="error-message" v-if="registerError">
                {{ registerError }}
              </p>
              <!-- Display register error message -->
              <input
                type="submit"
                class="btnn"
                value="Register"
                @click.prevent="submitRegisterForm"
              />
              <p class="social-text">Or sign up with social platforms</p>
              <div class="social-media">
                <a href="#" class="custom-social-icon">
                  <i class="fab fa-facebook-f"></i>
                </a>
                <a href="#" class="custom-social-icon">
                  <i class="fab fa-whatsapp"></i>
                </a>
                <a href="#" class="custom-social-icon">
                  <i class="fab fa-telegram"></i>
                </a>
              </div>
            </form>
          </div>
        </div>

        <div class="custom-panels-container">
          <div class="custom-panel left-panel">
            <div class="content">
              <h3>Don't have an account?</h3>
              <p></p>
              <button class="btnn transparent" @click="toggleMode('register')">
                Register
              </button>
            </div>
            <img src="../assets/shopping-bag.png" class="image" alt="" />
          </div>
          <div class="custom-panel right-panel">
            <div class="content">
              <h3>Already have an account?</h3>
              <p></p>
              <button class="btnn transparent" @click="toggleMode('login')">
                Login
              </button>
            </div>
            <img src="../assets/bank.png" class="image" alt="" />
          </div>
        </div>
      </div>
</template>

<script>


export default {
  name: 'LoginRegister',
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
      passwordMatchError: false,
      token: [],
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
            const responseData = await response.json();
            this.token = responseData.token; // Store the JWT token
            this.loginSuccess = true; // Set login success to true
            this.loginForm = { email: '', password: '' }; // Reset login form
            setTimeout(() => {
                this.loginSuccess = false; // Reset login success after 3 seconds
                // Redirect to the sidebar route
                this.$router.push({path: '/sidebar'});
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
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700;800&display=swap");

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body,
input {
  font-family: "Poppins", sans-serif;
}

.custom-container {
  position: relative;
  width: 100%;
  background-color: #fff;
  min-height: 100vh;
  overflow: hidden;
}

.custom-forms-container {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
}

.signin-signup {
  position: absolute;
  top: 50%;
  transform: translate(-50%, -50%);
  left: 75%;
  width: 50%;
  transition: 1s 0.7s ease-in-out;
  display: grid;
  grid-template-columns: 1fr;
  z-index: 5;
}

form {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0rem 5rem;
  transition: all 0.2s 0.7s;
  overflow: hidden;
  grid-column: 1 / 2;
  grid-row: 1 / 2;
}

form.sign-up-form {
  opacity: 0;
  z-index: 1;
}

form.sign-in-form {
  z-index: 2;
}

.title {
  font-size: 2.2rem;
  color: #444;
  margin-bottom: 10px;
}

.custom-input-field {
  max-width: 380px;
  width: 100%;
  background-color: #f0f0f0;
  margin: 10px 0;
  height: 55px;
  border-radius: 55px;
  display: grid;
  grid-template-columns: 15% 85%;
  padding: 0 0.4rem;
  position: relative;
}

.custom-input-field i {
  text-align: center;
  line-height: 55px;
  color: #acacac;
  transition: 0.5s;
  font-size: 1.1rem;
}

.custom-input-field input {
  background: none;
  outline: none;
  border: none;
  line-height: 1;
  font-weight: 600;
  font-size: 1.1rem;
  color: #333;
}

.custom-input-field input::placeholder {
  color: #aaa;
  font-weight: 500;
}

.social-text {
  padding: 0.7rem 0;
  font-size: 1rem;
}

.social-media {
  display: flex;
  justify-content: center;
}

.custom-social-icon {
  height: 46px;
  width: 46px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 0.45rem;
  color: #333;
  border-radius: 50%;
  border: 1px solid #333;
  text-decoration: none;
  font-size: 1.1rem;
  transition: 0.3s;
}

.custom-social-icon:hover {
  color: #B780FA;
  border-color: #B780FA;
}

.btnn {
  width: 150px;
  background-color: #B881FA  ;
  border: none;
  outline: none;
  height: 49px;
  border-radius: 49px;
  color: #fff;
  text-transform: uppercase;
  font-weight: 600;
  margin: 10px 0;
  cursor: pointer;
  transition: 0.5s;
}

.btnn:hover {
  background-color: #DBA1F5;
}
.custom-panels-container {
  position: absolute;
  height: 100%;
  width: 100%;
  top: 0;
  left: 0;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
}

.custom-container:before {
  content: "";
  position: absolute;
  height: 2000px;
  width: 2000px;
  top: -10%;
  right: 48%;
  transform: translateY(-50%);
  background-image: linear-gradient(-45deg, #CCABF3  0%, #AC73F1  100%);
  transition: 1.8s ease-in-out;
  border-radius: 50%;
  z-index: 6;
}

.image {
  width: 100%;
  transition: transform 1.1s ease-in-out;
  transition-delay: 0.4s;
}

.custom-panel {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: space-around;
  text-align: center;
  z-index: 6;
}

.left-panel {
  pointer-events: all;
  padding: 3rem 17% 2rem 12%;
}

.right-panel {
  pointer-events: none;
  padding: 3rem 12% 2rem 17%;
}

.custom-panel .content {
  color: #fff;
  transition: transform 0.9s ease-in-out;
  transition-delay: 0.6s;
}

.custom-panel h3 {
  font-weight: 600;
  line-height: 1;
  font-size: 1.5rem;
}

.custom-panel p {
  font-size: 0.95rem;
  padding: 0.7rem 0;
}

.btnn.transparent {
  margin: 0;
  background: none;
  border: 2px solid #fff;
  width: 130px;
  height: 41px;
  font-weight: 600;
  font-size: 0.8rem;
}

.right-panel .image,
.right-panel .content {
  transform: translateX(800px);
}

/* ANIMATION */

.custom-container.sign-up-mode:before {
  transform: translate(100%, -50%);
  right: 52%;
}

.custom-container.sign-up-mode .left-panel .image,
.custom-container.sign-up-mode .left-panel .content {
  transform: translateX(-800px);
}

.custom-container.sign-up-mode .signin-signup {
  left: 25%;
}

.custom-container.sign-up-mode form.sign-up-form {
  opacity: 1;
  z-index: 2;
}

.custom-container.sign-up-mode form.sign-in-form {
  opacity: 0;
  z-index: 1;
}

.custom-container.sign-up-mode .right-panel .image,
.custom-container.sign-up-mode .right-panel .content {
  transform: translateX(0%);
}

.custom-container.sign-up-mode .left-panel {
  pointer-events: none;
}

.custom-container.sign-up-mode .right-panel {
  pointer-events: all;
}

@media (max-width: 870px) {
  .custom-container {
    min-height: 800px;
    height: 100vh;
  }
  .signin-signup {
    width: 100%;
    top: 95%;
    transform: translate(-50%, -100%);
    transition: 1s 0.8s ease-in-out;
  }

  .signin-signup,
  .custom-container.sign-up-mode .signin-signup {
    left: 50%;
  }

  .custom-panels-container {
    grid-template-columns: 1fr;
    grid-template-rows: 1fr 2fr 1fr;
  }

  .custom-panel {
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    padding: 2.5rem 8%;
    grid-column: 1 / 2;
  }

  .right-panel {
    grid-row: 3 / 4;
  }

  .left-panel {
    grid-row: 1 / 2;
  }

  .image {
    width: 200px;
    transition: transform 0.9s ease-in-out;
    transition-delay: 0.6s;
  }

  .custom-panel .content {
    padding-right: 15%;
    transition: transform 0.9s ease-in-out;
    transition-delay: 0.8s;
  }

  .custom-panel h3 {
    font-size: 1.2rem;
  }

  .custom-panel p {
    font-size: 0.7rem;
    padding: 0.5rem 0;
  }

  .btnn.transparent {
    width: 110px;
    height: 35px;
    font-size: 0.7rem;
  }

  .custom-container:before {
    width: 1500px;
    height: 1500px;
    transform: translateX(-50%);
    left: 30%;
    bottom: 68%;
    right: initial;
    top: initial;
    transition: 2s ease-in-out;
  }

  .custom-container.sign-up-mode:before {
    transform: translate(-50%, 100%);
    bottom: 32%;
    right: initial;
  }

  .custom-container.sign-up-mode .left-panel .image,
  .custom-container.sign-up-mode .left-panel .content {
    transform: translateY(-300px);
  }

  .custom-container.sign-up-mode .right-panel .image,
  .custom-container.sign-up-mode .right-panel .content {
    transform: translateY(0px);
  }

  .right-panel .image,
  .right-panel .content {
    transform: translateY(300px);
  }

  .custom-container.sign-up-mode .signin-signup {
    top: 5%;
    transform: translate(-50%, 0);
  }
}

.password-toggle-icon {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  cursor: pointer;
}

.password-toggle-icon i {
  font-size: 18px;
  line-height: 1;
  color: #333;
  transition: color 0.3s ease-in-out;
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
}


.password-toggle-icon i:hover {
  color: #000;
}


@media (max-width: 570px) {
  form {
    padding: 0 1.5rem;
  }

  .image {
    display: none;
  }
  .custom-panel .content {
    padding: 0.5rem 1rem;
  }
  .custom-container {
    padding: 1.5rem;
  }

  .custom-container:before {
    bottom: 72%;
    left: 50%;
  }

  .custom-container.sign-up-mode:before {
    bottom: 28%;
    left: 50%;
  }
}


/* Added */
.error {
  border-color: red;
}

.error-message {
  color: red;
}

.alert {
  position: fixed;
  top: 0;
  left: 50%;
  transform: translate3d(-50%, 0, 0); /* Center the alert horizontally */
  z-index: 1000; /* Ensure the alert is above other elements */
  animation: slideDown 0.5s ease;
}

@keyframes slideDown {
  0% {
    transform: translate3d(-50%, -100%, 0); /* Start from above the viewport */
  }
  100% {
    transform: translate3d(-50%, 0, 0); /* End at the top of the viewport */
  }
}
</style>