import Vue from 'vue'
import VueRouter from  'vue-router'

// Layouts
import TopNavBar from '../layouts/TopNavBar.vue'
// import DashboardLayout from '../layouts/DashboardLayout'

// General Views

// User pages
import Notifications from '../views/Notifications.vue'
import Booking from '../views/Booking.vue'
import Analytics from '../views/Analytics.vue'
import AdminCorpPass from '../views/AdminCorpPass.vue'
import AdminCorpPassManagement from '../views/AdminCorpPassManagement.vue'
import AdminCorpPassDisplay from '../views/AdminCorpPassDisplay.vue'
import Login from '../views/Login.vue'

// import NotFound from '../views/NotFound.vue'
// import Overview from '../views/Overview.vue'

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    component: TopNavBar,
    redirect: '/booking'
  },
  { path: '/notifications', component: Notifications },
  { path: '/analytics', component: Analytics},
  { path: '/booking', component: Booking },
  { path: '/login', component: Login },
  { path: '/corporate-pass-creation', component: AdminCorpPass},
  { path: '/corporate-pass-management', component: AdminCorpPassManagement},
  { path: '/corporate-pass-management/passes', component: AdminCorpPassDisplay}, 
]

const router = new VueRouter({
  mode: 'history',
  routes, // short for routes: routes
  linkActiveClass: 'nav-item active',
  scrollBehavior: (to) => {
    if (to.hash) {
      return {selector: to.hash}
    } else {
      return { x: 0, y: 0 }
    }
  }
})

export default router