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

<style>
</style>
