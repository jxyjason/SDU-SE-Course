import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Dijkstra from "@/views/Dijkstra";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/dijkstra',
    name: 'Dijkstra',
    component: Dijkstra
  },


]

const router = new VueRouter({
  routes
})

export default router
