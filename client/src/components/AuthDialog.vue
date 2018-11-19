<template>
    <v-dialog
            v-model="dialog"
            width="500">
        <v-btn slot="activator">Войти</v-btn>


        <v-form ref="form" v-model="valid">
            <v-card>
                <v-card-title
                        class="headline"
                        primary-title>Авторизация
                </v-card-title>

                <v-container>
                        <v-text-field
                                v-model="email"
                                :rules="emailRules"
                                placeholder="example@example.com"
                                label="E-mail"
                                type="email"
                                required
                        />
                        <v-text-field
                                v-model="password"
                                :rules="passwordRules"
                                placeholder="Ваш пароль"
                                label="Пароль"
                                type="password"
                                required
                        />
                </v-container>

                <v-divider/>

                <v-card-actions>
                    <v-spacer/>
                    <v-btn
                            color="primary"
                            @click="submit"
                    >
                        Войти
                    </v-btn>
                    <v-btn
                            color="primary"
                            flat
                            @click="clear"
                    >
                        Отмена
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-form>
    </v-dialog>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "AuthDialog",

        data() {
            return {
                dialog: false,

                valid: true,

                email: '',
                emailRules: [
                    v => !!v || 'Обязательное поле',
                    v => /.+@.+/.test(v) || 'E-mail должен быть корректным'
                ],

                password: '',
                passwordRules: [
                    v => !!v || 'Обязательное поле',
                    v => v.length >= 10 || 'Пароль должен содержать не менее 10 символов'
                ]
            }
        },

        methods: {
            submit() {
                if (this.$refs.form.validate()) {
                    axios.post('/api/login', {
                        email: this.email,
                        password: this.password
                    })
                    this.$refs.form.reset()
                    this.dialog = false
                }
            },
            clear() {
                this.$refs.form.reset()
                this.dialog = false
            }
        }
    }
</script>

<style scoped>

</style>