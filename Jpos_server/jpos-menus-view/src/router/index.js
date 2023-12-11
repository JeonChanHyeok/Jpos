import { createRouter, createWebHistory } from 'vue-router'
import menusView from "@/views/MenusView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/menus/:storeName/:seatName',
      name: 'menusView',
      component: menusView,
    },
  ]
})

export default router
