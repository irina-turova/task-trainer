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
                <v-alert
                        value="true"
                        color="info"
                        icon="fas fa-superscript"
                        outline
                >

                        Текст задачи может содержать LaTeX-разметку. С примером использования LaTeX можно ознакомиться по
                        <a href="https://latex.js.org/playground.html">ссылке</a>

                </v-alert>
                <v-tabs
                >
                    <v-tab>
                        Редактирование
                    </v-tab>
                    <v-tab-item>
                        <v-card flat>
                            <v-card-title>
                                <v-text-field
                                        v-model="taskTitle"
                                        placeholder="Найти сумму двух чисел"
                                        label="Название задачи"
                                >
                                </v-text-field>
                            </v-card-title>
                            <v-card-text>
                                <v-textarea
                                        v-model="taskText"
                                        placeholder="Дано два числа: a и b. Нужно найти их сумму"
                                        label="Текст задачи"
                                >
                                </v-textarea>
                                <v-btn>Загрузить картинку к задаче</v-btn>
                                <v-text-field
                                        v-model="taskSolution"
                                        placeholder="1.2"
                                        label="Ответ на задачу"
                                >
                                </v-text-field>
                                <v-textarea
                                        v-model="taskExplanation"
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
                                <h3 class="headline mb-0">{{taskTitle}}</h3>
                            </v-card-title>

                            <img src="http://docs.likenul.com/pars_docs/refs/19/18704/18704_html_538f9f8f.png"
                                 style="float:right; max-width:300px; max-height:300px"
                            >
                            <v-card-text>

                                <v-container v-html="renderedTaskText">                               >
                                </v-container>

                                <div style="clear:both;"></div>

                            </v-card-text>

                        </v-card>

                        <v-card>
                            <v-card-title><h3>Решение задачи:</h3></v-card-title>
                            <v-card-text v-html="renderedTaskExplanation">
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
    import { parse, HtmlGenerator } from 'latex.js'

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
                themesLoading: false,
                subthemesLoading: false,
                difficultiesLoading: false,

                taskTitle: '',
                taskText: '',
                taskSolution: null,
                taskExplanation: '',
            }
        },

        computed: {
            renderedTaskText() {
                let generator = new HtmlGenerator({ hyphenate: false })

                let doc = parse(this.taskText, { generator: generator }).htmlDocument()

                let result = doc.body.childNodes[0]
                console.log(result.innerHTML)
                return result.innerHTML
            },
            renderedTaskExplanation() {
                let generator = new HtmlGenerator({ hyphenate: false })

                let doc = parse(this.taskExplanation, { generator: generator }).htmlDocument()

                let result = doc.body.childNodes[0]
                console.log(result.innerHTML)
                return result.innerHTML
            },
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
