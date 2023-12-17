<template>
  <div class="report-generator">
    <h2>Genera report</h2>
    <div class="form-group">
      <label for="employee">Dipendente:</label>
      <select id="employee" v-model="selectedEmployee">
        <option v-for="employee in employees" :key="employee.id" :value="employee">{{ employee.name }}</option>
      </select>
    </div>
    <div class="form-group">
      <label for="year">Anno:</label>
      <select id="year" v-model="selectedYear" @change="generateMonthList">
        <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
      </select>
    </div>
    <div class="form-group">
      <label for="month">Mese:</label>
      <select id="month" v-model="selectedMonth">
        <option v-for="month in months" :key="month.value" :value="month">{{ month.label }}</option>
      </select>
    </div>
    <button class="generate-button" @click="generateReport">Genera report</button>
    <div v-if="report" class="report">
      <h3>Report</h3>
      <ul>
        <li v-for="entry in report" :key="entry.date">
          {{ entry.date }} - {{ entry.hours }} ore
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { format, startOfMonth, endOfMonth, eachMonthOfInterval } from 'date-fns';
import { it } from 'date-fns/locale';

export default {
  data() {
    return {
      employees: [],
      selectedEmployee: null,
      years: [],
      selectedYear: null,
      months: [],
      selectedMonth: null,
      report: null,
    };
  },
  mounted() {
    this.generateYearList();
    this.generateMonthList();
  },
  methods: {
    generateYearList() {
      const currentYear = new Date().getFullYear();
      this.years = [currentYear - 1, currentYear, currentYear + 1];
      this.selectedYear = currentYear;
    },
    generateMonthList() {
      const startOfYear = startOfMonth(new Date(this.selectedYear, 0, 1));
      const endOfYear = endOfMonth(new Date(this.selectedYear, 11, 31));

      this.months = eachMonthOfInterval(
        {
          start: startOfYear,
          end: endOfYear,
        },
        { locale: it }
      ).map((month) => ({
        value: month.getMonth() + 1,
        label: format(month, 'LLLL', { locale: it }),
      }));
      this.selectedMonth = null;
    },
    generateReport() {
      // TODO Logica per generare il report
    },
  },
};
</script>

<style scoped>
.report-generator {
  max-width: 400px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  font-size: 16px;
  margin-bottom: 5px;
}

select {
  width: 100%;
  padding: 8px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 4px;
  transition: border-color 0.3s ease;
}

button.generate-button {
  display: block;
  width: 100%;
  padding: 10px;
  font-size: 16px;
  font-weight: bold;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button.generate-button:hover {
  background-color: #0056b3;
}

.report {
  margin-top: 20px;
}

.report h3 {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.report ul {
  list-style: none;
  padding: 0;
}

.report li {
  font-size: 14px;
  margin-bottom: 5px;
}

</style>
