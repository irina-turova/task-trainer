<template>
    <div>
        <v-card>
            <v-card-title>
                <h3 class="headline mb-0">Создание новой задачи</h3>
            </v-card-title>

            <v-layout
                    pa-3
                    row
                    wrap>
                <v-flex
                        xs4
                        class="px-2">
                    <v-combobox
                            v-model="selectedTheme"
                            :items="themes"
                            item-text="description"
                            item-value="name"
                            :loading="themesLoading"
                            label="Выберите тему"
                            @change="getSubthemes"
                    />
                </v-flex>

                <v-flex
                        xs4
                        class="px-2">
                    <v-combobox
                            v-if="selectedTheme"
                            v-model="selectedSubtheme"
                            :items="subthemes"
                            item-text="description"
                            item-value="name"
                            label="Выберите подтему"
                            @change="getDifficulties"
                    />
                </v-flex>

                <v-flex
                        v-if="selectedSubtheme"
                        xs4
                        class="px-2">
                    <v-combobox
                            v-model="selectedDifficulty"
                            :items="difficulties"
                            item-text="description"
                            item-value="name"
                            label="Выберите сложность"
                    />
                </v-flex>
            </v-layout>

            <v-container>
                <v-tabs
                        v-model="active"
                >
                    <v-tab>
                        Редактирование
                    </v-tab>
                    <v-tab-item>
                        <v-card flat>
                            <v-card-title>
                                <v-text-field
                                        placeholder="Найти сумму двух чисел"
                                        label="Название задачи"
                                >
                                </v-text-field>
                            </v-card-title>
                            <v-card-text>
                                <v-textarea
                                        placeholder="Дано два числа: a и b. Нужно найти их сумму"
                                        label="Текст задачи"
                                >
                                </v-textarea>
                                <v-btn>Загрузить картинку к решению</v-btn>
                                <v-text-field
                                        placeholder="1.2"
                                        label="Ответ на задачу"
                                >
                                </v-text-field>
                                <v-textarea
                                        placeholder="Нужно взять два числа и сложить их столбиком"
                                        label="Решение задачи"
                                >
                                </v-textarea>
                                <v-btn>Загрузить картинку к решению</v-btn>
                            </v-card-text>
                        </v-card>
                    </v-tab-item>

                    <v-tab>
                        Предпросмотр
                    </v-tab>
                    <v-tab-item>
                        <v-card>
                            <v-card-title primary-title>
                                <h3 class="headline mb-0">Нахождение гипотенузы треугольника</h3>
                            </v-card-title>

                            <v-card-text>
                                <img src="http://docs.likenul.com/pars_docs/refs/19/18704/18704_html_538f9f8f.png"
                                     style="float:right; max-width:300px; max-height:300px"
                                >

                                <p style="text-align: justify">
                                    Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab commodi ex natus quos ratione suscipit. Consequuntur facere labore magnam officia! Aliquid ea eligendi enim et eveniet fugiat qui vero, voluptas? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab commodi ex natus quos ratione suscipit. Consequuntur facere labore magnam officia! Aliquid ea eligendi enim et eveniet fugiat qui vero, voluptas? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab commodi ex natus quos ratione suscipit. Consequuntur facere labore magnam officia! Aliquid ea eligendi enim et eveniet fugiat qui vero, voluptas?Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab commodi ex natus quos ratione suscipit. Consequuntur facere labore magnam officia! Aliquid ea eligendi enim et eveniet fugiat qui vero, voluptas? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab commodi ex natus quos ratione suscipit. Consequuntur facere labore magnam officia! Aliquid ea eligendi enim et eveniet fugiat qui vero, voluptas? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab commodi ex natus quos ratione suscipit. Consequuntur facere labore magnam officia! Aliquid ea eligendi enim et eveniet fugiat qui vero, voluptas?
                                </p>

                                <div style="clear:both;"></div>


                                <v-text-field
                                        placeholder="1.2"
                                        label="Ответ на задачу"
                                >
                                </v-text-field>
                            </v-card-text>

                        </v-card>

                        <v-card>
                            <v-card-title><h3>Решение задачи:</h3></v-card-title>
                            <v-card-text>
                                <p>
                                    Квадрат гипотенузы равен сумме квадратов катетов!
                                </p>
                            </v-card-text>
                        </v-card>
                    </v-tab-item>
                </v-tabs>
            </v-container>

            <v-card-actions>
                <v-btn
                        flat
                        color="orange"
                >Создать задачу
                </v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>

<script>

    import axios from 'axios'

    export default {
        name: 'NewTask',

        data() {
            return {
                selectedTheme: null,
                selectedSubtheme: null,
                selectedDifficulty: null,
                themes: null,
                subthemes: null,
                difficulties: null,
                task: null,
                themesLoading: false,
                subthemesLoading: false,
                difficultiesLoading: false,
                gotSolution: false,
            }
        },

        created () {
            this.fetch()
        },

        watch: {
            '$route': 'fetch',
        },

        methods: {
            async fetch() {
                if (!this.themes)
                    await this.getThemes()

                if (this.selectedTheme && !this.subthemes)
                    await this.getSubthemes()

                if (this.selectedSubtheme && !this.difficulties)
                    await this.getDifficulties()
            },

            async getThemes() {
                this.themesLoading = true
                try {
                    let res = await axios.get('/api/themes')
                    this.themes = res.data;
                } catch(e) {
                    if (e.response) {
                        alert(e.response.data)
                    } else {
                        alert(e.message)
                    }
                }
                this.themesLoading = false
            },

            async getSubthemes() {
                this.subthemesLoading = true
                try {
                    console.log(this.selectedTheme)
                    let res = await axios.get(`/api/subthemes/${this.selectedTheme.name}`)
                    this.subthemes = res.data;
                } catch(e) {
                    if (e.response) {
                        alert(e.response.data)
                    } else {
                        alert(e.message)
                    }
                }
                this.subthemesLoading = false
            },

            async getDifficulties() {
                if (!this.selectedSubtheme)
                    return

                this.difficultiesLoading = true
                try {
                    let res = await axios.get(`/api/difficulties/${this.selectedSubtheme.name}`)
                    this.difficulties = res.data;
                } catch(e) {
                    alert('ТУТ')
                    if (e.response) {
                        alert(e.response.data)
                    } else {
                        alert(e.message)
                    }
                }
                this.difficultiesLoading = false
            }
        }
    }
</script>

<style scoped>

</style>
