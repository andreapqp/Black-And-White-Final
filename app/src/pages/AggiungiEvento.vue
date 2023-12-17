<template>
  <div class="container">
    <!-- Titolo -->
    <div class="title">
      <h2 style="font-weight: bold">DATI EVENTO</h2>
    </div>

    <!-- Lista clienti -->
    <div class="form-group">
      <label for="cliente" class="label">Cliente:</label>
      <div>
        <select id="cliente" class="input" v-model="cliente">
          <option value="" disabled selected>&lt;seleziona il cliente&gt;</option>
          <option v-for="cliente in clienti" :key="cliente.id" :value="cliente.id">{{ cliente.nome }}</option>
        </select>
      </div>
    </div>

    <!-- Data -->
    <div class="form-group">
      <label for="date" class="label">Data:</label>
      <div class="input-wrapper">
        <input type="date" id="date" class="input" v-model="date"  @change="fetchEventiByDate(); fetchDipendentiOccupati()" />
      </div>
    </div>

    <!-- Luogo -->
    <div class="form-group">
      <label for="luogo" class="label">Luogo:</label>
      <div class="input-wrapper">
        <input type="text" id="luogo" class="input" v-model="luogo" />
      </div>
    </div>

    <!-- Descrizione -->
    <div class="form-group">
      <label for="descrizione" class="label">Descrizione:</label>
      <div class="input-wrapper">
        <textarea id="descrizione" class="textarea" v-model="descrizione"></textarea>
      </div>
    </div>

    <!-- Tabella delle qualifiche necessarie -->
    <div class="form-group">
      <label class="label">Qualifiche necessarie:</label>
      <div class="qualifiche-table">
        <table>
          <thead>
            <tr>
              <th>Qualifica</th>
              <th>Numero Persone</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(quantity, qualifica) in qualificheNecessarie" :key="qualifica">
              <td>{{ qualifica }}</td>
              <td>
                <div class="numero-persone">
                  <button class="decrease-button" @click="decreaseQuantity(qualifica)">-</button>
                  {{ quantity }}
                  <button class="increase-button" @click="increaseQuantity(qualifica)">+</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="form-group">
  <label for="dipendentiSelezionati" class="label">Dipendenti:</label>
  <div class="input-wrapper" style="height: 400px; width: 300px; overflow-y: auto;">
    <select id="dipendentiSelezionati" class="input" multiple v-model="dipendentiSelezionati" style="height: 100%; width: 100%;">
      <option v-for="dipendente in allDipendenti" :key="dipendente.id" :value="dipendente.id">{{ dipendente.nome }}</option>
    </select>
  </div>
</div>

<div class="form-group">
  <label for="dipendentiDisponibiliSelezionati" class="label">Dipendenti Disponibili:</label>
  <div class="input-wrapper" style="height: 400px; width: 300px; overflow-y: auto;">
    <select id="dipendentiDisponibiliSelezionati" class="input" multiple v-model="dipendentiDisponibiliSelezionati" style="height: 100%; width: 100%;">
      <!-- Mostra prima i dipendenti assenti -->
      <option v-for="dipendente in dipendentiAssenti" :key="dipendente.id" :value="dipendente.id" style="color: red;">
        {{ dipendente.nome }}
      </option>
      <!-- Poi mostra i dipendenti occupati -->
      <option v-for="dipendente in dipendentiOccupati" :key="dipendente.id" :value="dipendente.id" style="color: green;">
        {{ dipendente.nome }}
      </option>
      <!-- Poi mostra i dipendenti disponibili -->
      <option v-for="dipendente in dipendentiDisponibili" :key="dipendente.id" :value="dipendente.id">
        {{ dipendente.nome }}
      </option>
    </select>
  </div>
</div>

    <!-- Pulsante di conferma -->
    <button class="button" @click="Algoritmo()">Conferma</button>
    <span v-if="mostraConferma" class="conferma">Evento aggiunto correttamente!</span>

    <!-- Area per visualizzare il messaggio di errore -->
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

    <!-- Area per visualizzare il messaggio di errore -->
    <div v-if="successMessage" class="success-message">{{ successMessage }}</div>
    
    <!-- Spazio per scorrere -->
    <div style="height: 1000px;"></div>
  </div>
</template>

<script>
import Evento from "@/models/Evento.js";

export default {
  data() {
    return {
      cliente: "",
      date: "",
      luogo: "",
      descrizione: "",
      isFesta: false,
      dipendenti: [],
      errorMessage: '', 
      successMessage: '', 
      mostraConferma: false,
      eventi: [],
      DipendentiAllocati: [], // Aggiungi l'array DipendentiAllocati
      clienti: [],
      dipendentiDisponibili: [],
      dipendentiOccupati: [],
      allDipendenti: [],
      dipendentiSelezionati: [],
      dipendentiDisponibiliSelezionati: [],
      dipendentiAssenti: [],
      clientiDisponibili: [],
      numeroDipendenti: 0,
      qualificheNecessarie: {
        BAR: 0,
        SALA: 0,
        CUCINA: 0,
      },
    };
  },
  created() {
    this.fetchClienti();
    this.fetchDipendenti();
    // this.fetchEventi(); // Manca
  },
  methods: {
    fetchClienti() {
      const token = sessionStorage.getItem("token");
      fetch("http://localhost:8080/api/v1/clienti/getallclienti", {
        headers: {
          Authorization: `Bearer ${token}`,
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
    fetchDipendenti() {
      console.log('Chiamata a fetchDipendenti()');
      const token = sessionStorage.getItem("token");
      fetch("http://localhost:8080/api/v1/dipendenti/getalldipendenti", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => response.json())
      .then((data) => {
        this.allDipendenti = data;
      })
      .catch((error) => {
        console.error("Errore durante il recupero dei dipendenti:", error);
      });

    },
    increaseQuantity(qualifica) {
      this.qualificheNecessarie = {
        ...this.qualificheNecessarie,
        [qualifica]: this.qualificheNecessarie[qualifica] + 1,
      };
    },
    decreaseQuantity(qualifica) {
      if (this.qualificheNecessarie[qualifica] > 0) {
        this.qualificheNecessarie = {
          ...this.qualificheNecessarie,
          [qualifica]: this.qualificheNecessarie[qualifica] - 1,
        };
      }
    },
    fetchDipendentiOccupati() {
  console.log('Chiamata a fetchDipendentiOccupati()');

  const token = sessionStorage.getItem('token');
  const formattedDate = this.formatDateForApi(this.date);

  fetch(`http://localhost:8080/api/v1/eventi/geteventibydate/${formattedDate}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
  .then((response) => {
    if (!response.ok) {
      // throw new Error('Errore durante il recupero degli eventi');
      console.log('Nessun evento trovato per la data specificata');
      this.fetchDipendentiDisponibili();
      return []; // Restituisce un array vuoto per continuare il flusso
    }
    return response.json();
  })
  .then((eventi) => {
    if (eventi.length === 0) {
      console.log('Nessun evento trovato per la data specificata');
      // Puoi gestire la situazione senza eventi qui se necessario
      // Esempio: esegui un'azione predefinita o restituisci un valore vuoto
    } else {
      const idDipendentiOccupati = [];

      eventi.forEach((evento) => {
        evento.dipendenti.forEach((idDipendente) => {
          idDipendentiOccupati.push(idDipendente);
        });
      });

      const dipendentiPromises = idDipendentiOccupati.map((idDipendente) => {
        return fetch(`http://localhost:8080/api/v1/dipendenti/getdipendente/${idDipendente}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Dipendente non trovato');
          }
          return response.json();
        })
        .catch((error) => {
          console.error("Errore durante il recupero dei dettagli del dipendente:", error);
          return null;
        });
      });

      Promise.all(dipendentiPromises)
        .then((dipendentiOccupati) => {
          this.dipendentiOccupati = dipendentiOccupati.filter((dipendente) => dipendente !== null);
          // Chiamata a fetchDipendentiDisponibili dopo aver recuperato i dipendenti occupati
          this.fetchDipendentiDisponibili();
        });
    }
  })
  .catch((error) => {
    console.error("Errore durante il recupero degli eventi:", error);
  });
    },

    fetchEventiByDate() {
  console.log('Chiamata a fetchEventiByDate()');

  const token = sessionStorage.getItem('token');
  const formattedDate = this.formatDateForApi(this.date);

  fetch(`http://localhost:8080/api/v1/eventi/geteventibydate/${formattedDate}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
  .then((response) => {
    if (!response.ok) {
      this.eventi = []; // Imposta this.eventi come un array vuoto
      console.log('Nessun evento trovato per questa data.');
    }
    return response.json();
  })
  .then((eventi) => {
    if (eventi && eventi.length > 0) {
      this.eventi = eventi;
      console.log('Eventi ottenuti:', JSON.stringify(this.eventi, null, 2));
    } else {
      this.eventi = []; // Imposta this.eventi come un array vuoto
      console.log('Nessun evento trovato per questa data.');
    }
  })
  .catch((error) => {
    console.error("Errore durante il recupero degli eventi:", error);
  });
},


    inviaDatiEvento() {
  console.log('Chiamata a inviaDatiEvento()');

  // Calcola il numero di dipendenti necessari basandoti sulla somma delle qualifiche richieste
const qualificheNecessarie = this.qualificheNecessarie;
let numDipendentiDaSelezionare = 0;

for (const qualifica in qualificheNecessarie) {
  numDipendentiDaSelezionare += qualificheNecessarie[qualifica];
}

console.log('Numero dipendenti basato sulle qualifiche necessarie:', numDipendentiDaSelezionare);


 // Estrai gli ID dai dipendenti allocati
const dipendentiIDs = this.DipendentiAllocati.map(dipendente => dipendente.id);

  // Crea l'oggetto 'evento' con i dati raccolti dal form, incluso solo gli ID dei dipendenti
  const evento = {
    cliente: this.cliente,
    data: this.date,
    luogo: this.luogo,
    descrizione: this.descrizione,
    isFesta: this.isFesta,
    dipendenti: dipendentiIDs, // Passa solo gli ID dei dipendenti
    numeroDipendenti: numDipendentiDaSelezionare,
    qualificheNecessarie: this.qualificheNecessarie,
  };

 // console.log('Dati dell\'evento da inviare:', JSON.stringify(evento));

  const token = sessionStorage.getItem("token");

  fetch("http://localhost:8080/api/v1/eventi/addevento", {
    method: "POST",
    body: JSON.stringify(evento),
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  })
  .then((response) => {
    if (response.ok) {
      this.mostraConferma = true;
    } else {
      // TODO Gestione dell'errore
    }
  })
  .catch((error) => {
    console.error("Errore durante l'invio dei dati evento:", error);
  });

  // Resetta i campi del form
  this.cliente = "";
  this.date = "";
  this.luogo = "";
  this.descrizione = "";
  this.isFesta = false;
  this.dipendenti = [];
  this.numeroDipendenti = 0;
  this.qualificheNecessarie = {
    BAR: 0,
    SALA: 0,
    CUCINA: 0,
  };
    },
   formatDateForApi(date) {
      console.log('Chiamata a formatDateForApi');
      const formattedDate = new Date(date);
      const year = formattedDate.getFullYear();
      let month = (formattedDate.getMonth() + 1).toString();
      let day = formattedDate.getDate().toString();

      if (month.length === 1) {
        month = `0${month}`;
      }
      if (day.length === 1) {
        day = `0${day}`;
      }
      console.log('Data:', `${year}-${month}-${day}` );

      return `${year}-${month}-${day}`;
      
   },
   fetchDipendentiDisponibili() {
  console.log('Chiamata a fetchDipendentiDisponibili()');

  const token = sessionStorage.getItem('token');
  const formattedDate = this.formatDateForApi(this.date);

  fetch(`http://localhost:8080/api/v1/assenze/getassenzabydata/${formattedDate}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
  .then((response) => {
    if (!response.ok) {
      throw new Error('Assenza non trovata');
    }
    return response.json();
  })
  .then((assenza) => {
    // Estrai gli ID dei dipendenti assenti dall'oggetto assenza
    const idDipendentiAssenti = assenza.dipendenti;

    // Filtra gli allDipendenti escludendo quelli presenti tra i dipendenti assenti
    this.dipendentiAssenti = this.allDipendenti.filter((dipendente) => idDipendentiAssenti.includes(dipendente.id));

    // Rimuovi i dipendenti occupati dai dipendenti diponibili
    this.dipendentiDisponibili = this.allDipendenti.filter(
      (dipendente) => {
        const isDipendenteOccupato = this.dipendentiOccupati.map((d) => d.id).includes(dipendente.id);
        const hasQualifiche = dipendente.qualifiche && dipendente.qualifiche.length > 0;
        const isDipendenteAssente = idDipendentiAssenti.includes(dipendente.id);

        return !isDipendenteAssente && !isDipendenteOccupato && hasQualifiche;
      }
    );
    
    // console.log('Dipendenti Assenti:', JSON.stringify(this.dipendentiAssenti, null, 2));
    // console.log('Dipendenti Disponibili:', JSON.stringify(this.dipendentiDisponibili, null, 2));
  })
  .catch((error) => {
    console.error("Errore durante il recupero dei dipendenti assenti:", error);
  });
},

   Algoritmo() {
   // Recupera le chiavi (qualifiche) dall'oggetto qualificheNecessarie
  const qualifiche = Object.keys(this.qualificheNecessarie);

  // Crea un array di oggetti con qualifica e numero di dipendenti richiesti
  const qualificheConNumeroPersone = qualifiche.map(qualifica => ({
    qualifica,
    numeroDipendenti: this.qualificheNecessarie[qualifica]
  }));

   console.log('Qualifiche con numero di persone:', qualificheConNumeroPersone);


  // Ordina l'array in base al numero di persone richieste per qualifica in ordine decrescente
  qualificheConNumeroPersone.sort((a, b) => b.numeroDipendenti - a.numeroDipendenti);

  // Genera un array di qualifiche ripetute il numero di volte corrispondente al numero di dipendenti richiesti
  const arrayQualifiche = [];
  qualificheConNumeroPersone.forEach(qualifica => {
    for (let i = 0; i < qualifica.numeroDipendenti; i++) {
      arrayQualifiche.push(qualifica.qualifica);
    }
  });
 
 console.log('Array di qualifiche:', arrayQualifiche);


  // Chiamata per recuperare i dipendenti disponibili
  //this.fetchDipendentiDisponibili(); 

// Array con le qualifiche da considerare per il conteggio
const qualificheConsiderate = ['SALA', 'CUCINA', 'BAR'];

// Array per contare le qualifiche tra i dipendenti disponibili
let qualificheCount = {};
console.log('Dipendenti Disponibili:', JSON.stringify(this.dipendentiDisponibili, null, 2));
console.log('sto per for etchare');

if (this.dipendentiDisponibili && Array.isArray(this.dipendentiDisponibili)) {
  this.dipendentiDisponibili.forEach(dipendente => {
    if (dipendente && dipendente.qualifiche && Array.isArray(dipendente.qualifiche)) {
      dipendente.qualifiche.forEach(qualifica => {
        if (qualificheConsiderate && qualificheConsiderate.includes && qualificheConsiderate.includes(qualifica)) {
          if (!qualificheCount[qualifica]) {
            qualificheCount[qualifica] = 1;
          } else {
            qualificheCount[qualifica]++;
          }
        }
      });
    }
  });
}
// Conteggio delle qualifiche selezionate tra i dipendenti disponibili
this.dipendentiDisponibili.forEach(dipendente => {
  dipendente.qualifiche.forEach(qualifica => {
    if (qualificheConsiderate.includes(qualifica)) {
      if (!qualificheCount[qualifica]) {
        qualificheCount[qualifica] = 1;
      } else {
        qualificheCount[qualifica]++;
      }
    }
  });
});

  console.log('Conteggio delle qualifiche:', qualificheCount);

// Ora 'qualificheCount' contiene il numero di dipendenti disponibili per SALA, CUCINA e BAR
console.log('Numero di dipendenti disponibili per SALA, CUCINA e BAR:', qualificheCount);

 // Calcolo degli scarti tra le qualifiche richieste e i dipendenti disponibili
 const Scarti = {};
  for (const qualifica in this.qualificheNecessarie) {
    if (qualificheCount.hasOwnProperty(qualifica)) {
      Scarti[qualifica] = qualificheCount[qualifica] - this.qualificheNecessarie[qualifica];
    } else {
      Scarti[qualifica] = -this.qualificheNecessarie[qualifica];
    }
  }

   console.log('Scarti:', Scarti);

  // Ora 'Scarti' contiene gli scarti tra le qualifiche richieste e i dipendenti disponibili
  console.log('Scarti tra qualifiche richieste e dipendenti disponibili:', Scarti);

  const hasNegativeScarti = Object.values(Scarti).some(scarto => scarto < 0);

if (hasNegativeScarti) {
  this.errorMessage = 'Impossibile allocare questa configurazione';
  return; // Esci dalla funzione Algoritmo() se ci sono scarti negativi
}

// Ordinamento dell'array dei dipendenti disponibili
this.dipendentiDisponibili.sort((a, b) => {
    // Calcolo del numero di qualifiche per i dipendenti
    const numQualificheA = a.qualifiche.filter(qualifica => qualificheConsiderate.includes(qualifica)).length;
    const numQualificheB = b.qualifiche.filter(qualifica => qualificheConsiderate.includes(qualifica)).length;

    // Se i dipendenti hanno un numero diverso di qualifiche, ordina in base al numero di qualifiche
    if (numQualificheA !== numQualificheB) {
      return numQualificheA - numQualificheB;
    } else {
      // Altrimenti, se hanno lo stesso numero di qualifiche, calcola la somma degli scarti per ciascun dipendente
      const sommaScartiA = a.qualifiche.reduce((acc, qualifica) => acc + Scarti[qualifica], 0);
      const sommaScartiB = b.qualifiche.reduce((acc, qualifica) => acc + Scarti[qualifica], 0);

      // Ordina in base alla somma degli scarti
      return sommaScartiB - sommaScartiA;
    }
  });

   // Mostra l'array dei dipendenti disponibili ordinato in formato JSON
  console.log('Array dei dipendenti disponibili ordinato:');

   for (let i = 0; i < this.dipendentiDisponibili.length; i++) {
    const dipendente = this.dipendentiDisponibili[i];
    
    // Ciclo su tutte le qualifiche necessarie
    for (let j = 0; j < arrayQualifiche.length; j++) {
      const qualifica = arrayQualifiche[j];
      
      // Verifica se il dipendente contiene la qualifica richiesta
      if (dipendente.qualifiche.includes(qualifica)) {
        // Aggiungi il dipendente agli allocati
        this.DipendentiAllocati.push(dipendente);
        
        // Rimuovi il dipendente dal array dei dipendenti disponibili
        this.dipendentiDisponibili.splice(i, 1);
        
        // Rimuovi la qualifica dal arrayQualifiche
        arrayQualifiche.splice(j, 1);
        
        // Decrementa l'indice per gestire la rimozione corretta dell'elemento
        i--;
        break; // Esci dal ciclo delle qualifiche una volta trovata una corrispondenza
      }
    }
  }

    
    console.log('DipendentiAllocati:', JSON.stringify(this.DipendentiAllocati));
    // Ora 'arrayQualifiche' contiene le qualifiche ripetute in base al numero di dipendenti necessari
    console.log('Array di qualifiche ripetute in base al numero di dipendenti richiesti:', arrayQualifiche);

  if (arrayQualifiche.length === 0) {

    this.mostraConferma = true;
    this.successMessage = 'Dipendenti allocati con successo!!!';
    this.inviaDatiEvento();
    
    this.errorMessage = ''; // Resetta eventuali errori precedenti
  } else {
 
    this.mostraConferma = false;
    this.errorMessage = 'Allocazione non riuscita';
  }



}






  },
};
</script>

<style>
/* Stili precedenti */

.qualifiche-table table {
  margin-top: 20px;
  width: 100%;
  border-collapse: collapse;
}

.error-message {
  margin-top: 10px;
  color: red;
  font-weight: bold;
}

.success-message {
  margin-top: 10px;
  color: green;
  font-weight: bold;
}


.qualifiche-table th,
.qualifiche-table td {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: center;
}

.numero-persone {
  display: flex;
  align-items: center;
}

.decrease-button,
.increase-button {
  padding: 5px 10px;
  font-size: 14px;
  background-color: #f2f2f2;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}


.increase-button {
  margin-left: 5px;
}
</style>
