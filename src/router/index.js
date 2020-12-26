import Vue from 'vue'
import Router from 'vue-router'

// Login
//const Login = () => import('@/views/Login');
const HelloWorld = () => import('@/components/HelloWorld');
const Home = () => import('@/components/Home');
const Start = () => import('@/components/Start');
const HastaHome = () => import('@/components/HastaHome');
const DoktorHome = () => import('@/components/DoktorHome');
const AdminHome = () => import('@/components/AdminHome');
const TetkikHome = () => import('@/components/TetkikHome');
const HastaKayit = () => import('@/components/HastaKayit');


// Search User
//const SearchUser = () => import('@/views/pages/UserSearchPage');

//const Page404 = () => import('@/views/pages/Page404');

//const User = {
//  props: ['id', 'name'],
//  template: '<div>User {{ id }{{ name }}}</div>'
//}


Vue.use(Router)

const router = new Router({
  mode: 'history',
  //linkActiveClass: 'open active',
  //scrollBehavior: () => ({
  //  y: 0
  //}),
  routes: [
    {
      path: '/',
      redirect: '',
      name: 'Start',
      component: Start
    },
    {
      path: '/home',
      redirect: '',
      name: 'Home',
      component: Home
    },
    {
      path: '/hastaHome',
      redirect: '',
      name: 'hastaHome',
      component: HastaHome
    },
    {
      path: '/doktorHome',
      redirect: '',
      name: 'DoktorHome',
      component: DoktorHome
    },
    {
      path: '/adminHome',
      redirect: '',
      name: 'AdminHome',
      component: AdminHome
    },
    {
      path: '/tetkikHome',
      redirect: '',
      name: 'TetkikHome',
      component: TetkikHome
    },
    {
      path: '/hastaKayit',
      redirect: '',
      name: 'HastaKayit',
      component: HastaKayit
    }
    //{
    //  path: '/searchuser',
    //  name: 'kullaniciArama',
    //  component: SearchUser,
    //},
    
    //{
    //  path: '*',
    //  component: Page404
    //}
  ]
})

export default router;