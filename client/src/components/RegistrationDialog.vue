<template>
    <v-dialog
            v-model="dialog"
            width="500"
    >
        <v-btn
                slot="activator"
        >
            Зарегистрироваться
        </v-btn>

        <v-card>
            <v-card-title
                    class="headline"
                    primary-title
            >
                Регистрация
            </v-card-title>

            <v-container>
                <v-form v-model="valid">
                    <v-text-field
                            v-model="surname"
                            :rules="surnameRules"
                            placeholder="Иванов"
                            label="Фамилия"
                            required
                    />
                    <v-text-field
                            v-model="name"
                            :rules="nameRules"
                            placeholder="Иван"
                            label="Имя"
                            required
                    />
                    <v-text-field
                            v-model="fathersname"
                            :rules="fathersnameRules"
                            placeholder="Иванович"
                            label="Отчество"
                            required
                    />
                    <v-text-field
                            v-model="email"
                            :rules="emailRules"
                            placeholder="example@example.com"
                            label="E-mail"
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
                </v-form>
            </v-container>

            <v-divider/>

            <v-card-actions>
                <v-spacer/>
                <v-btn
                        color="primary"
                        @click="dialog = false"
                >
                    Войти
                </v-btn>
                <v-btn
                        color="primary"
                        flat
                        @click="dialog = false"
                >
                    Отмена
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        name: "RegistrationDialog"
    }
</script>

<style scoped>

</style>