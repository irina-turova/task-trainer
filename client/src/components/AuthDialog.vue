<template>
    <v-dialog
            v-model="dialog"
            width="500">
        <v-btn slot="activator">Войти</v-btn>

        <v-card>
            <v-card-title
                    class="headline"
                    primary-title>Авторизация
            </v-card-title>

            <v-container>
                <v-form v-model="valid">
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
        name: "AuthDialog"
    }
</script>

<style scoped>

</style>