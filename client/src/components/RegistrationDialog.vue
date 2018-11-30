<template>
    <v-dialog
            v-model="dialog"
            width="500"
    >
        <v-btn slot="activator" class="accent">
            Зарегистрироваться
        </v-btn>

        <v-form ref="form" v-model="valid">
            <v-card>
                <v-card-title
                        class="headline"
                        primary-title
                >
                    Регистрация
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
                        Регистрация прошла успешно
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
                                v-model="lastName"
                                :rules="lastNameRules"
                                placeholder="Иванов"
                                label="Фамилия *"
                                required
                        />
                        <v-text-field
                                v-model="firstName"
                                :rules="firstNameRules"
                                placeholder="Иван"
                                label="Имя *"
                                required
                        />
                        <v-text-field
                                v-model="middleName"
                                :rules="middleNameRules"
                                placeholder="Иванович"
                                label="Отчество"
                        />
                        <v-text-field
                                v-model="email"
                                :rules="emailRules"
                                placeholder="example@example.com"
                                label="E-mail *"
                                required
                        />
                        <v-text-field
                                v-model="password"
                                :rules="passwordRules"
                                placeholder="Ваш пароль"
                                label="Пароль *"
                                type="password"
                                required
                        />
                </v-container>

                <v-divider/>

                <v-card-actions>
                    <v-spacer/>
                    <v-btn
                            @click="submit"
                            class="accent"
                    >
                        Зарегистрироваться
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
    name: 'RegistrationDialog',

    data () {
        return {
            dialog: false,

            valid: true,
            succeded: null,
            errorMessage: null,

            lastName: '',
            lastNameRules: [
                v => !!v || 'Обязательное поле',
                v => /^[a-zа-яё]+$/i.test(v) || 'Поле должно содержать только буквы',
                v => v.length <= 100 || 'Длина должна быть не больше 100 символов'
            ],

            firstName: '',
            firstNameRules: [
                v => !!v || 'Обязательное поле',
                v => /^[a-zа-яё]+$/i.test(v) || 'Поле должно содержать только буквы',
                v => v.length <= 100 || 'Длина должна быть не больше 100 символов'
            ],

            middleName: null,
            middleNameRules: [
                v => v == null || v.length === 0 || /^[a-zа-яё]+$/i.test(v) || 'Поле должно содержать только буквы',
                v => v == null || v.length === 0 || v.length <= 100 || 'Длина должна быть не больше 100 символов'
            ],

            email: '',
            emailRules: [
                v => !!v || 'Обязательное поле',
                v => /[a-zA-Zа-яА-ЯёЁ]+@[a-zA-Zа-яА-ЯёЁ]+/.test(v) || 'E-mail должен быть корректным',
                v => v.length <= 100 || 'Длина должна быть не больше 100 символов'
            ],

            password: '',
            passwordRules: [
                v => !!v || 'Обязательное поле',
                v => v.length >= 10 || 'Пароль должен содержать не менее 10 символов'
            ]
        }
    },

    watch: {
        dialog: function (val) {
            if (!val) { this.clear() }
        }
    },

    methods: {
        async submit () {
            this.succeded = null
            if (this.$refs.form.validate()) {
                const data = {
                    lastName: this.lastName,
                    firstName: this.firstName,
                    middleName: this.middleName,
                    login: this.email,
                    password: this.password
                }

                try {
                    let response = await axios.post('/api/users/register', data)
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
                        this.errorMessage = 'Возникла ошибка: ' + e.response.data
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
