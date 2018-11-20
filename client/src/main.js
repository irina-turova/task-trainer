import Vue from 'vue'
import Vuetify from 'vuetify/lib'
// import 'vuetify/src/stylus/app.styl'
import 'vuetify/dist/vuetify.min.css'
// import 'vuetify/src/stylus/main.styl'
import ru from 'vuetify/es5/locale/ru'
import App from './App.vue'
import router from './router'
import VueGoogleCharts from 'vue-google-charts'
import '@fortawesome/fontawesome-free/css/all.css'

Vue.use(Vuetify, {
    customProperties: true,
    iconfont: 'fa',
    lang: {
        locales: {ru},
        current: 'ru'
    }
});

Vue.use(VueGoogleCharts)

Vue.config.productionTip = false;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
