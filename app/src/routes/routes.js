import DashboardLayout from "@/pages/Layout/DashboardLayout.vue";
import Dashboard from "@/pages/Dashboard.vue";
import UserProfile from "@/pages/UserProfile.vue";
import GestisciDipendenti from "@/pages/GestisciDipendenti.vue";
import AggiungiEvento from "@/pages/AggiungiEvento.vue";
import GestisciClienti from "@/pages/GestisciClienti.vue";

import Ore from "@/pages/Ore.vue";
import Calendario from "@/pages/Calendario.vue";
import Icons from "@/pages/Icons.vue";


import LoginPage from "@/pages/LoginPage.vue"

const routes = [
  {
    path: "/login",
    name: "Login",
    component: LoginPage,
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: "/",
    component: DashboardLayout,
    redirect: "dashboard",
    children: [
      {
        path: "dashboard",
        name: "Dashboard",
        component: Dashboard,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "user",
        name: "User Profile",
        component: UserProfile,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "gestiscidipendenti",
        name: "Gestisci Dipendenti",
        component: GestisciDipendenti,
        meta: {
          requiresAuth: true,
        },
      },
      
      {
        path: "aggiungievento",
        name: "Aggiungi Evento",
        component: AggiungiEvento,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "ore",
        name: "Ore",
        component: Ore,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "calendario",
        name: "Calendario",
        component: Calendario,
        meta: {
          requiresAuth: true,
        },
      },
      {
        path: "gestisciclienti",
        name: "Gestisci Clienti",
        component: GestisciClienti,
        meta: {
          requiresAuth: true,
        },
      },
      
     
      
    ],
  },
];

export default routes;
