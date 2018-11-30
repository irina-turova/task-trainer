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
import VueNativeSock from 'vue-native-websocket'
import colors from 'vuetify/es5/util/colors'

Vue.use(Vuetify, {
    customProperties: true,
    iconfont: 'fa',
    lang: {
        locales: {ru},
        current: 'ru'
    },
    theme: {
        primary: colors.indigo.darken1,
        secondary: colors.indigo.accent1,
        accent: colors.teal,
    }
});

Vue.use(VueGoogleCharts)

Vue.use(VueNativeSock, 'ws://192.168.1.2:8075/api/chat', {
    // reconnection: true, // (Boolean) whether to reconnect automatically (false)
    reconnectionAttempts: 5, // (Number) number of reconnection attempts before giving up (Infinity),
    reconnectionDelay: 3000, // (Number) how long to initially wait before attempting a new (1000)
})

Vue.config.productionTip = false;

new Vue({
    router,

    data() {
        return {
            user: JSON.parse(localStorage.getItem("user"))
        }
    },

    render: h => h(App)
}).$mount('#app');
