import Vue from 'vue'
import Router from 'vue-router'

// Login
//const Login = () => import('@/views/Login');
const HelloWorld = () => import('@/components/HelloWorld');
const Home = () => import('@/components/Home');

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
      name: 'Login',
      component: HelloWorld
    },
    {
      path: '/home',
      redirect: '',
      name: 'Home',
      component: Home
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