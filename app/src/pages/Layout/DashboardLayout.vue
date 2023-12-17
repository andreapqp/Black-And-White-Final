<template>
  <div class="wrapper" :class="{ 'nav-open': $sidebar.showSidebar }">
    <!-- Componente per le notifiche -->
    <notifications></notifications>

    <!-- Barra laterale -->
    <side-bar
      :sidebar-item-color="sidebarBackground"
      :sidebar-background-image="sidebarBackgroundImage"
    >
      
      <!-- Link alla dashboard -->
      <sidebar-link to="/dashboard">
        <md-icon>home</md-icon>
        <p>Dashboard</p>
      </sidebar-link>


      <!-- Link per aggiungere un dipendente -->
      <sidebar-link to="/gestiscidipendenti">
        <md-icon>person_add</md-icon>
        <p>Gestisci Dipendenti</p>
      </sidebar-link>

      <!-- Link per aggiungere un cliente -->
      <sidebar-link to="/gestisciclienti">
        <md-icon>food_bank</md-icon>
        <p>Gestisci Clienti</p>
      </sidebar-link>

       <!-- Link per aggiungere un cliente -->
       <sidebar-link to="/aggiungievento">
        <md-icon>food_bank</md-icon>
        <p>Aggiungi Evento</p>
      </sidebar-link>

      <!-- Link alla sezione delle ore -->
      <sidebar-link to="/ore">
        <md-icon>access_time</md-icon>
        <p>Ore</p>
      </sidebar-link>

      <!-- Link al calendario -->
      <sidebar-link to="/calendario">
        <md-icon>calendar_today</md-icon>
        <p>Calendario</p>
      </sidebar-link>
    </side-bar>

    <!-- Pannello principale -->
    <div class="main-panel">
      <!-- Contenuto della dashboard -->
      <router-view></router-view>

    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      sidebarBackground: "green",
      sidebarBackgroundImage: require("@/assets/img/sidebar-2.jpg"),
    };
  },
};
</script>
