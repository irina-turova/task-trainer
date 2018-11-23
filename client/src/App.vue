<template>
    <v-app id="app">
        <v-toolbar app>
            <!--<router-link to="/">Home</router-link>-->
            <!--<router-link to="/about">About</router-link>-->

            <v-toolbar-title>Тренажер</v-toolbar-title>

            <v-toolbar-items class="px-5">

                <v-btn
                        v-if="user"
                        flat
                        @click="$router.push('/tasks')">Задачи
                </v-btn>

                <v-btn
                        v-if="user"
                        flat
                        @click="$router.push('/stats')">Статистика
                </v-btn>

                <v-btn
                        v-if="user && user.role !== 'student'"
                        flat
                        @click="$router.push('/newtask')">Создание задачи
                </v-btn>

            </v-toolbar-items>

            <v-spacer/>

            <auth-dialog v-if="!user"></auth-dialog>

            <registration-dialog v-if="!user"></registration-dialog>

            <v-btn v-if="user" flat>
                <!--<v-icon left
                        color="secondary"
                >far fa-user</v-icon>-->
                <v-avatar size="35"><img :src="gravatarLink"></v-avatar>
                &nbsp;&nbsp;{{ userName }}
            </v-btn>

            <v-btn  v-if="user"
                    @click="logout()"
            >Выйти</v-btn>

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
            }
        }
        
    }
</script>

<style>
</style>
