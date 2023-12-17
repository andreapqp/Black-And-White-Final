<template>
  <div class="container">
    <div class="left-column">
      <div class="title">
        <h2 style="font-weight: bold">DATI CLIENTE</h2>
      </div>
      <div class="form-container">
        <div class="form-group">
          <label for="nome" class="label">Nome:</label>
          <div class="input-wrapper">
            <input type="text" id="nome" class="input" v-model="nome" />
          </div>
        </div>

        <div class="form-group">
          <label for="descrizione" class="label">Descrizione:</label>
          <div class="input-wrapper">
            <textarea id="descrizione" class="textarea" v-model="descrizione"></textarea>
          </div>
        </div>

        <button class="button" @click="inviaDati">Conferma</button>
        <span v-if="mostraConferma" class="conferma">Cliente aggiunto correttamente!</span>
      </div>
    </div>

    <div class="right-column">
      <div class="table-container">
        <table class="clienti-table">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Descrizione</th>
              <th>Azioni</th> 
            </tr>
          </thead>
          <tbody>
            <tr v-for="cliente in clienti" :key="cliente.id">
              <td>{{ cliente.nome }}</td>
              <td>{{ cliente.descrizione }}</td>
              <td>
                <button class="button-delete" @click="eliminaCliente(cliente.id)">
                  <img src="@/assets/img/trash.png" alt="Icona Trash" class="icon-trash" />
                  Elimina
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>



  </div>
       <!-- Spazio per scorrere -->
  <div style="height: 1000px;"></div>
  </div>
</template>

<style>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 5px;
  background-color: #f9f9f9;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.left-column,
.right-column {
  flex: 1;
  padding: 10px;
}

.title {
  margin-bottom: 10px;
}

.form-container {
  padding: 10px;
  margin-bottom: 20px;
}

.form-group {
  padding: 10px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.icon-trash {
  width: 16px;
  height: 16px;
  margin-right: 5px;
}

.label {
  display: block;
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 5px;
}

.input-wrapper {
  display: flex;
  align-items: center;
}

.button-delete {
  background-color: red;
}

.input {
  width: 100%;
  height: 40px;
  padding: 5px 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.textarea {
  width: 100%;
  min-height: 100px;
  padding: 5px 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.button {
  padding: 10px 50px;
  background-color: #4caf50;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.conferma {
  display: inline-block;
  margin-left: 10px;
  font-size: 14px;
  color: green;
  margin-top: 10px;
}

.table-container {
  width: 100%;
  padding: 10px;
  overflow: auto;
}

.clienti-table {
  width: 100%;
  border-collapse: collapse;
}

.clienti-table th,
.clienti-table td {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: left;
}

.clienti-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.clienti-table td {
  font-size: 14px;
}
</style>


<script>
import Cliente from "@/models/Cliente.js";

export default {
  data() {
    return {
      nome: "",
      descrizione: "",
      mostraConferma: false,
      clienteId: "",
      mostraEliminaConferma: false,
      clienti: [], 
    };
  },
  methods: {
    inviaDati() {
      const cliente = new Cliente(this.nome, this.descrizione);
      const token = sessionStorage.getItem("token");
      fetch("http://localhost:8080/api/v1/clienti/addcliente", {
        method: "POST",
        body: JSON.stringify(cliente),
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      });
      this.nome = "";
      this.descrizione = "";
      this.mostraConferma = true;
      this.caricaClienti();
    },

    eliminaCliente(clienteId) {
      const token = sessionStorage.getItem("token");
      fetch(`http://localhost:8080/api/v1/clienti/deletecliente/${clienteId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      }).then(response => {
        if (response.ok) {
          this.mostraEliminaConferma = true;
          this.caricaClienti();
        } else {
          console.log("errore nell'eliminazione");
        }
      });
      this.clienteId = "";
    },

    caricaClienti() {
      const token = sessionStorage.getItem("token");

      fetch("http://localhost:8080/api/v1/clienti/getallclienti", {
        headers: {
          "Authorization": `Bearer ${token}`,
        },
      })
        .then(response => response.json())
        .then(data => {
          this.clienti = data;
        })
        .catch(error => {
          console.error("Errore durante il recupero dei clienti:", error);
        });
    },
  },

  mounted() {
    this.caricaClienti();
  },
};
</script>