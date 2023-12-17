<template>
  <div class="calendario">
    <div class="header">
      <button @click="previousMonth">Mese precedente</button>
      <h2>{{ month }}</h2>
      <button @click="nextMonth">Mese successivo</button>
    </div>
    <table>
      <thead>
        <tr>
          <th v-for="day in daysOfWeek" :key="day">{{ day }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(week, index) in calendar" :key="index">
          <td v-for="date in week" :key="date.date" :class="[getCellClasses(date), date.color]"
            @click="changeColor(date)">
            <div class="date">{{ date.date }}</div>
            <div class="color-marker"></div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { startOfMonth, endOfMonth, eachDayOfInterval, isSameMonth, addMonths, subMonths, startOfWeek } from 'date-fns';

export default {
  data() {
    return {
      daysOfWeek: ['Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab', 'Dom'],
      selectedDate: null,
      calendar: [],
      currentDate: new Date(),
      assenze: [],
    };
  },
  mounted() {
    this.caricaAssenzeDipendente();
  },
  computed: {
    month() {
      return this.currentDate.toLocaleString('default', { month: 'long', year: 'numeric' });
    },
  },
  methods: {
    generateCalendar() {
  const startOfMonthDate = startOfMonth(this.currentDate);
  const startOfWeekDate = startOfWeek(startOfMonthDate, { weekStartsOn: 1 });
  const endOfMonthDate = endOfMonth(this.currentDate);
  const days = eachDayOfInterval({
    start: startOfWeekDate,
    end: endOfMonthDate,
  });

  this.calendar = [];
  let week = [];
  let dayIndex = 0;

  days.forEach((date) => {
    const day = {
      date: date.getDate(),
      month: date.getMonth(),
      year: date.getFullYear(),
      color: '',
    };

    const hasAssenza = this.hasAssenzaForDate(day);

    if (hasAssenza) {
      day.color = 'red'; 
    } else {
      day.color = 'white'; 
    }

    week.push(day);
    dayIndex++;

    if (dayIndex === 7) {
      this.calendar.push(week);
      week = [];
      dayIndex = 0;
    }
  });

  if (week.length > 0) {
    this.calendar.push(week);
  }
},


    getCellClasses(date) {
      return {
        'selected': isSameMonth(date, this.selectedDate),
      };
    },

    changeColor(date) {
      const hasAssenza = this.hasAssenzaForDate(date);

      if (hasAssenza) {
        this.removeAssenzaForDate(date);
      } else {
        this.addAssenzaForDate(date);
      }
    },

    hasAssenzaForDate(date) {
      const assenzaDate = new Date(date.year, date.month, date.date);

      return this.assenze.some((assenza) => {
        const assenzaDateObj = new Date(assenza.data);
        return assenzaDateObj.toDateString() === assenzaDate.toDateString();
      });
    },

    addAssenzaForDate(date) {
      const token = sessionStorage.getItem('token');
      const dipendenteId = sessionStorage.getItem('id');
      const assenzaDate = new Date(date.year, date.month, date.date);

      const formattedDate = this.formatDate(assenzaDate); // Formatta la data

      fetch(`http://localhost:8080/api/v1/assenze/adddipendente/${formattedDate}/${dipendenteId}`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('Errore durante l'aggiunta dell'assenza');
          }
          this.caricaAssenzeDipendente();
        })
        .catch(error => {
          console.error("Errore durante l'aggiunta dell'assenza:", error);
        });
    },

    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    },

    removeAssenzaForDate(date) {
      const token = sessionStorage.getItem('token');
  const dipendenteId = sessionStorage.getItem('id');
  const assenzaDate = new Date(date.year, date.month, date.date);

  const formattedDate = this.formatDate(assenzaDate); 

  fetch(`http://localhost:8080/api/v1/assenze/removedipendente/${formattedDate}/${dipendenteId}`, {
    method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('Errore durante la rimozione dell'assenza');
          }
          this.caricaAssenzeDipendente();
        })
        .catch(error => {
          console.error("Errore durante la rimozione dell'assenza:", error);
        });
    },

    previousMonth() {
      this.currentDate = subMonths(this.currentDate, 1);
      this.caricaAssenzeDipendente();
    },

    nextMonth() {
      this.currentDate = addMonths(this.currentDate, 1);
      this.caricaAssenzeDipendente();
    },

    caricaAssenzeDipendente() {
      const token = sessionStorage.getItem('token');
      const dipendenteId = sessionStorage.getItem('id');

      fetch(`http://localhost:8080/api/v1/assenze/getassenzedipendente/${dipendenteId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('Errore durante la richiesta delle assenze');
          }
          return response.json();
        })
        .then(data => {
          this.assenze = data || []; 
          this.generateCalendar(); // ricrea il calendario in base alle nuove assenze
        })
        .catch(error => {
          console.error("Errore durante il recupero delle assenze:", error);
        });
    },
  },
};
</script>

<style scoped>
.calendario {
  font-family: Arial, sans-serif;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

th {
  background-color: #f2f2f2;
}

.selected {
  background-color: #ccc;
}

.date {
  margin-bottom: 5px;
  font-weight: bold;
}

.color-marker {
  width: 100%;
  height: 100%;
}

.red {
  background-color: rgba(255, 0, 0, 0.2);
}

.white {
  background-color: #fff;
}
</style>
