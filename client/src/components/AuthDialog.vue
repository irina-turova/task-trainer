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
                    <v-alert
                            v-if="succeded"
                            :value="true"
                            color="success"
                            icon="fas fa-check"
                            outline
                            class="mb-3"
                    >
                        Аутентификация прошла успешно
                    </v-alert>

                    <v-alert
                            v-if="succeded === false"
                            :value="true"
                            color="error"
                            icon="fas fa-exclamation-triangle"
                            outline
                            class="mb-3"
                    >
                        {{errorMessage}}
                    </v-alert>

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
    name: 'AuthDialog',

    data () {
        return {
            dialog: false,

            valid: true,
            succeded: null,
            errorMessage: null,

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
        async submit () {
            if (this.$refs.form.validate()) {
                const data = new FormData()
                data.set('user', this.email)
                data.set('password', this.password)

                try {
                    let response = await axios.post('/api/users/login', data, {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    })
                    this.succeded = true
                    await new Promise(resolve => setTimeout(resolve, 500))

                    localStorage.setItem('user', JSON.stringify(response.data))
                    this.$root.user = response.data

                    this.dialog = false
                    this.$refs.form.reset()
                    this.succeded = null
                } catch (e) {
                    this.succeded = false
                    if (e.response) {
                        if (e.response.status === 401) { this.errorMessage = 'Неверный логин или пароль' } else { this.errorMessage = 'Возникла ошибка: ' + e.response.statusText }
                    } else {
                        this.errorMessage = 'Возникла ошибка, попробуйте позже'
                    }
                }
            }
        },
        clear () {
            this.dialog = false
            this.$refs.form.reset()
            this.succeded = null
        }
    }
}
</script>

<style scoped>

</style>
