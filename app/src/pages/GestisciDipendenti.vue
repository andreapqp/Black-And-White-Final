<template>
  <div>
    <div class="container">
      <div class="title">
        <h2 style="font-weight: bold">INSERISCI DATI DEL DIPENDENTE</h2>
      </div>
      <div class="form-group">
        <div class="form form-inline">
          <label for="username" class="label">Username:</label>
          <div class="input-wrapper">
            <input type="text" id="username" class="input" v-model="username" />
          </div>
          <label for="password" class="label">Password:</label>
          <div class="input-wrapper">
            <input type="password" id="password" class="input" v-model="password" />
          </div>
          <label for="name" class="label">Nome:</label>
          <div class="input-wrapper">
            <input type="text" id="name" class="input" v-model="nome" />
          </div>
          <label for="surname" class="label">Cognome:</label>
          <div class="input-wrapper">
            <input type="text" id="surname" class="input" v-model="cognome" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="column">
          <div class="checkbox-group">
            <label class="label">Qualifiche:</label>
            <div class="checkbox">
              <input type="checkbox" id="BAR" v-model="qualifiche.BAR" />
              <label for="BAR">Bar</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="SALA" v-model="qualifiche.SALA" />
              <label for="SALA">Sala</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="CUCINA" v-model="qualifiche.CUCINA" />
              <label for="CUCINA">Cucina</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="AUTOMUNITO" v-model="qualifiche.AUTOMUNITO" />
              <label for="AUTOMUNITO">Automunito</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="REFERENTE" v-model="referente" />
              <label for="REFERENTE">Referente</label>
            </div>
          </div>
        </div>
        <div class="column">
          <!-- Lingue -->
          <div class="checkbox-group">
            <label class="label">Lingue:</label>
            <div class="checkbox">
              <input type="checkbox" id="inglese" v-model="lingue.INGLESE" />
              <label for="inglese">Inglese</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="francese" v-model="lingue.FRANCESE" />
              <label for="francese">Francese</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="spagnolo" v-model="lingue.SPAGNOLO" />
              <label for="spagnolo">Spagnolo</label>
            </div>
            <div class="checkbox">
              <input type="checkbox" id="tedesco" v-model="lingue.TEDESCO" />
              <label for="tedesco">Tedesco</label>
            </div>
          </div>
        </div>
      </div>

      <!-- Pulsante di conferma -->
      <button class="button" @click="inviaDati">Conferma</button>
      <span v-if="mostraConferma" class="conferma">Dati inviati correttamente!</span>
    </div>


    <!-- Tabella dei dipendenti -->
    

    <div class="table-container">
        <table class="dipendenti-table">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Cognome</th>
              <th>Azioni</th> <!-- Aggiunta della colonna per le azioni -->
            </tr>
          </thead>
          <tbody>
            <tr v-for="dipendente in dipendenti" :key="dipendente.id">
              <td>{{ dipendente.nome }}</td>
              <td>{{ dipendente.cognome }}</td>
              <td>
                <button class="button-delete" @click="eliminaDipendente(dipendente.id)">
                  <img src="@/assets/img/trash.png" alt="Icona Trash" class="icon-trash" />
                  Elimina
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

    <!-- clearfix per pulire il float -->
    <div class="clearfix"></div>
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
  flex-direction: column;
  justify-content: center;
  align-items: baseline;
  margin-bottom: 20px;
}

.row {
  display: flex;
}

.column {
  flex: 1;
  margin-right: 100px;
}

.column:last-child {
  margin-right: 0;
}

.form-group {
  padding: 10px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
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
  margin-bottom: 5px;
}

.input {
  width: 2000px;
  height: 40px;
  padding: 5px 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.checkbox-group {
  width: 150px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-top: 20px;
}

.checkbox {
  margin-bottom: 5px;
}

.button {
  position: relative;
  margin-bottom: 20px;
}

.conferma {
  display: inline-block;
  margin-left: 100px;
  font-size: 14px;
  color: green;
  margin-top: 10px;
  position: absolute;
  top: 90%;
}

.clearfix::after {
  content: "";
  display: table;
  clear: both;
}

.table-container {
  width: 100%;
  padding: 10px;
  overflow: auto;
}

.dipendenti-table {
  width: 100%;
  border-collapse: collapse;
}

.dipendenti-table th,
.dipendenti-table td {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: left;
}

.dipendenti-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.dipendenti-table td {
  font-size: 14px;
}



</style>

<script>

export default {
  data() {
  return {
    username: "",
    password: "",
    nome: "",
    cognome: "",
    referente: false, 
    mostraConferma: false,
    qualifiche: {
      AUTOMUNITO: false,
      BAR: false,
      SALA: false,
      CUCINA: false,
    },
    lingue: {
      INGLESE: false,
      FRANCESE: false,
      SPAGNOLO: false,
      TEDESCO: false,
    },

    dipendenti: [], 


  };
},

  methods: {
    inviaDati() {
      const role = this.referente ? "REFERENTE" : "DIPENDENTE";
      const lingueSelezionate = Object.keys(this.lingue).filter(
        (l) => this.lingue[l]
      );
      const qualificheSelezionate = Object.keys(this.qualifiche).filter(
        (l) => this.qualifiche[l]
      );

      const dipendente = {
        username: this.username,
        password: this.password,
        nome: this.nome,
        cognome: this.cognome,
        role: role,
        qualifiche: qualificheSelezionate,
        lingue: lingueSelezionate,
      };

      
      const token = sessionStorage.getItem("token");

      const requestOptions = {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(dipendente),
      };

      fetch("http://localhost:8080/api/v1/dipendenti/adddipendente", requestOptions)
        .then((response) => {
          if (!response.ok) {
            throw new Error("Errore durante la richiesta");
          }
          return response.json();
        })
        .then((data) => {
          console.log("Dipendente aggiunto:", data);
          // Gestisci la risposta del server se necessario
        })
        .catch((error) => {
          console.error("Errore durante la richiesta:", error);
        });
      this.username = "";
      this.password = "";
      this.nome = "";
      this.cognome = "";
      this.mostraConferma = true;

      this.caricaDipendenti();
    },
  
    eliminaDipendente(dipendenteId) {
      const token = sessionStorage.getItem("token");
      fetch(`http://localhost:8080/api/v1/dipendenti/deletedipendente/${dipendenteId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      }).then(response => {
        if (response.ok) {
          this.mostraEliminaConferma = true;
          this.caricaDipendenti();
        } else {
          console.log("errore nell'eliminazione");
        }
      });
      this.dipendenteId = "";
    },

    caricaDipendenti() {
      const token = sessionStorage.getItem("token");

      fetch("http://localhost:8080/api/v1/dipendenti/getalldipendenti", {
        headers: {
          "Authorization": `Bearer ${token}`,
        },
      })
        .then(response => response.json())
        .then(data => {
          this.dipendenti = data;
        })
        .catch(error => {
          console.error("Errore durante il recupero dei dipendenti:", error);
        });
    },
  
  
  
  },

  mounted() {
    this.caricaDipendenti();
  },

};

</script>
