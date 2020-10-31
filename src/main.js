import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'
Vue.use(BootstrapVue)
import router from './router/index'
import Toasted from 'vue-toasted';
//VUELİDATE
import Vuelidate from 'vuelidate'
Vue.use(Vuelidate)

Vue.use(Toasted)
Vue.config.productionTip = false

new Vue({
 router,
 render: h => h(App),
}).$mount('#app')