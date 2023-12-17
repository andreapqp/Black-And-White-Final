<template>
  <div class="login-container">
    <h1>Login</h1>
    <div class="form-group">
      <input type="text" v-model="username" placeholder="Username" class="input-field">
    </div>
    <div class="form-group">
      <input type="password" v-model="password" placeholder="Password" class="input-field">
    </div>
    <button @click="login" class="login-button">Login</button>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: "",
      password: "",
      errorMessage: "",
      token: sessionStorage.getItem("token") || "", // recupera il token dal sessionStorage se presente
    };
  },
  methods: {
    login() {
      fetch("http://localhost:8080/api/v1/auth/authenticate", {
        method: "POST",
        body: JSON.stringify({ username: this.username, password: this.password }),
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => response.json())
        .then((data) => {
          if (data.token) {
            // salva il token, l'ID, il nome e il cognome nel sessionStorage
            sessionStorage.setItem("token", data.token);
            sessionStorage.setItem("id", data.id);
            sessionStorage.setItem("nome", data.nome);
            sessionStorage.setItem("cognome", data.cognome);

            this.token = data.token; 

            this.$router.replace('/dashboard');
          } else {
            this.errorMessage = "Autenticazione fallita. Verifica le tue credenziali.";
          }
        })
        .catch((error) => {
          this.errorMessage = "Si è verificato un errore durante l'autenticazione. Riprova più tardi.";
        });
    },
  },
};
</script>

<style>
.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 5px;
  text-align: center;
}

.form-group {
  margin-bottom: 10px;
}

.input-field {
  width: 100%;
  height: 40px;
  padding: 5px 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.login-button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #4caf50;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.error-message {
  color: red;
}
</style>
