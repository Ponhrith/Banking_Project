const app = Vue.createApp({
  data() {
    return {
      signUpMode: false,
      loginForm: {
        email: '',
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
    async submitLoginForm() {
      try {
        const response = await fetch('http://localhost:8080/api/v1/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.loginForm)
        });
        const data = await response.json();
        if (response.ok) {
          alert('Login successful!'); // Show success message
          // Optionally, you can redirect the user to another page
        } else {
          alert('Login failed: ' + data.message); // Show error message
        }
      } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while logging in.'); // Show generic error message
      }
    },
    async submitRegisterForm() {
      try {
        const response = await fetch('http://localhost:8080/api/v1/profile', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.registerForm)
        });
        const data = await response.json();
        if (response.ok) {
          alert('Registration successful!'); // Show success message
          // Optionally, you can redirect the user to another page
        } else {
          alert('Registration failed: ' + data.message); // Show error message
        }
      } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while registering.'); // Show generic error message
      }
    }
  }
});

app.mount('#app');
