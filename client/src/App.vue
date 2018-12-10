<template>
    <v-app id="app">
        <v-toolbar app class="primary" dark>
            <v-toolbar-title @click="$router.push('/')" style="cursor: pointer"><v-icon>fas fa-user-graduate</v-icon> Тренажер</v-toolbar-title>

            <v-toolbar-items class="px-5">

                <v-btn
                        v-if="user"
                        flat
                        @click="$router.push('/tasks')"><v-icon>fas fa-graduation-cap</v-icon>&nbsp;&nbsp;Задачи
                </v-btn>

                <v-btn
                        v-if="user"
                        flat
                        @click="$router.push('/stats')"><v-icon>fas fa-chart-bar</v-icon>&nbsp;&nbsp;Статистика
                </v-btn>

                <v-btn
                        v-if="user && user.role !== 'student'"
                        flat
                        @click="$router.push('/newtask')"><v-icon>fas fa-hammer</v-icon>&nbsp;&nbsp;Создание задачи
                </v-btn>

            </v-toolbar-items>

            <v-spacer/>

            <auth-dialog v-if="!user"></auth-dialog>

            <registration-dialog v-if="!user"></registration-dialog>

            <v-btn v-if="user" flat>
                <v-avatar size="35"><img :src="gravatarLink"></v-avatar>
                &nbsp;&nbsp;{{ userName }}
            </v-btn>

            <v-btn  v-if="user"
                    @click="logout()"
                    class="accent"
            >Выйти<v-icon right>fas fa-sign-out-alt</v-icon></v-btn>

        </v-toolbar>

        <v-content class="light-blue lighten-5">
            <v-container fill-height>
                <v-container
                        align-start
                        fill-height
                        class="elevation-2 white">
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
                class="primary" dark>
            <v-container px-4 py-1 class="text-xs-center">
                Тренажёр для решения задач онлайн. All rights reserved &copy; {{ new Date().getFullYear() }}
            </v-container>
        </v-footer>

    </v-app>
</template>

<script>
    import AuthDialog from "./components/AuthDialog";
    import RegistrationDialog from "./components/RegistrationDialog";
    import axios from 'axios'
    import md5 from 'md5'

    export default {
        name: 'app',
        components: {RegistrationDialog, AuthDialog},
        data() {
            return {}
        },

        computed: {
            user() {
                return this.$root.user
            },

            userName() {
                return this.user.firstName + ' ' + this.user.secondName
            },

            gravatarLink() {
                return `https://www.gravatar.com/avatar/${md5(this.user.email)}?d=identicon`
            }
        },

        methods: {
            logout() {
                this.$root.user = undefined
                localStorage.removeItem("user")
                axios.post("/api/users/logout")
                this.$router.push('/')
            }
        }
        
    }
</script>

<style>
</style>
