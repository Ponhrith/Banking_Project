// app.js
const app = Vue.createApp({
  data() {
    return {
      signUpMode: false,
      loginForm: {
        accountNumber: '',
        password: ''
      },
      registerForm: {
        fullName: '',
        address: '',
        email: '',
        password: '',
        confirmPassword: ''
      },
      showPassword: false,
      showPasswordIcon: 'fa-eye'
    };
  },
  methods: {
    toggleMode(mode) {
      this.signUpMode = (mode === 'register');
    },
    togglePassword() {
      this.showPassword = !this.showPassword;
      this.showPasswordIcon = this.showPassword ? 'fa-eye-slash' : 'fa-eye';
      const passwordFields = document.querySelectorAll('input[type="password"]');
      passwordFields.forEach(field => {
        field.type = this.showPassword ? 'text' : 'password';
      });
    },
    submitLoginForm() {
      // Handle login form submission
      console.log('Login form submitted:', this.loginForm);
    },
    submitRegisterForm() {
      // Handle register form submission
      console.log('Register form submitted:', this.registerForm);
    }
  }
});

app.mount('#app');
