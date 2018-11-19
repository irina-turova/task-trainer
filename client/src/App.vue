<template>
    <v-app id="app">
        <v-toolbar app>
            <!--<router-link to="/">Home</router-link>-->
            <!--<router-link to="/about">About</router-link>-->

            <v-toolbar-title>Тренажер</v-toolbar-title>

            <v-toolbar-items class="px-5">

                <v-btn
                        flat
                        @click="$router.push('/tasks')">Задачи
                </v-btn>

                <v-btn
                        flat
                        @click="$router.push('/stats')">Статистика
                </v-btn>

                <v-btn
                        flat
                        @click="$router.push('/newtask')">Создание задачи
                </v-btn>

            </v-toolbar-items>

            <v-spacer/>

            <auth-dialog></auth-dialog>

            <registration-dialog></registration-dialog>

        </v-toolbar>

        <v-content>
            <v-container fill-height>
                <v-container
                        align-start
                        fill-height
                        class="elevation-2">
                    <v-layout>
                        <v-flex>
                            <router-view/>
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-container>
        </v-content>

        <v-footer
                app
                class="pa-3">
            <v-spacer/>
            <div>&copy; {{ new Date().getFullYear() }}</div>
        </v-footer>

    </v-app>
</template>

<script>
    import AuthDialog from "./components/AuthDialog";
    import RegistrationDialog from "./components/RegistrationDialog";
    export default {
        name: 'app',
        components: {RegistrationDialog, AuthDialog},
        data() {
            return {}
        }
        
    }
</script>

<style>
</style>
