<template>
    <div>
        <v-card>
            <v-card-title>
                <h3 class="headline mb-0">Создание новой задачи</h3>
            </v-card-title>

            <v-layout pa-3 row wrap>
                <v-flex xs4 class="px-2">
                    <v-combobox
                            v-model="selectedTheme"
                            :items="themes"
                            item-text="description"
                            item-value="name"
                            :loading="themesLoading"
                            label="Выберите тему"
                            @change="getSubthemes"/>
                    <theme-add-dialog
                            ref="addThemeDialog"
                            genitive-name="темы"
                            accusative-name="тему"
                            name-placeholder="Математика"
                            alias-placeholder="math"
                            :error-message="errorMessage"
                            @submit="submitThemeAdd"
                    ></theme-add-dialog>
                </v-flex>

                <v-flex v-if="selectedTheme" xs4 class="px-2">
                    <v-combobox
                            v-model="selectedSubtheme"
                            :items="subthemes"
                            item-text="description"
                            item-value="name"
                            label="Выберите подтему"
                            @change="getDifficulties"/>
                    <theme-add-dialog
                            ref="addSubthemeDialog"
                            genitive-name="подтемы"
                            accusative-name="подтему"
                            name-placeholder="Арифметика"
                            alias-placeholder="arithmetic"
                            @submit="submitSubthemeAdd"
                    ></theme-add-dialog>
                </v-flex>

                <v-flex v-if="selectedSubtheme" xs4 class="px-2">
                    <v-combobox
                            v-model="selectedDifficulty"
                            :items="difficulties"
                            item-text="description"
                            item-value="name"
                            label="Выберите сложность"/>
                </v-flex>
            </v-layout>

            <v-container>
                <v-alert value="true" color="info" icon="fas fa-superscript" outline>
                    Текст задачи может содержать LaTeX-разметку. С примером использования LaTeX можно ознакомиться по
                    <a href="https://latex.js.org/playground.html">ссылке</a>
                </v-alert>
                <v-tabs>
                    <v-tab>Редактирование</v-tab>
                    <v-tab-item>
                        <v-card flat>
                            <v-card-title>
                                <v-text-field
                                        v-model="taskTitle"
                                        placeholder="Найти сумму двух чисел"
                                        label="Название задачи">
                                </v-text-field>
                            </v-card-title>
                            <v-card-text>
                                <v-textarea
                                        v-model="taskText"
                                        placeholder="Дано два числа: a и b. Нужно найти их сумму"
                                        label="Текст задачи">
                                </v-textarea>
                                <!--<v-btn>Загрузить картинку к задаче</v-btn>-->
                                <input ref="taskFileInput" type="file" name="taskImage" @change="uploadTaskImage"/>

                                <v-text-field
                                        v-model="taskSolution"
                                        placeholder="1.2"
                                        label="Ответ на задачу">
                                </v-text-field>
                                <v-textarea
                                        v-model="taskExplanation"
                                        placeholder="Нужно взять два числа и сложить их столбиком"
                                        label="Решение задачи">
                                </v-textarea>
                                <v-btn>Загрузить картинку к решению</v-btn>
                            </v-card-text>
                        </v-card>
                    </v-tab-item>

                    <v-tab>Предпросмотр</v-tab>
                    <v-tab-item>
                        <v-card>
                            <v-card-title primary-title>
                                <h3 class="headline mb-0">{{taskTitle}}</h3>
                            </v-card-title>

                            <!--style="float:right; max-width:300px; max-height:300px"-->
                            <v-img :src="taskImage"
                                   ></v-img>
                            <v-card-text>
                                <v-container v-html="renderedTaskText"></v-container>
                                <div style="clear:both;"></div>
                            </v-card-text>
                        </v-card>

                        <v-card>
                            <v-card-title><h3>Решение задачи:</h3></v-card-title>
                            <v-card-text v-html="renderedTaskExplanation">
                                <p>Квадрат гипотенузы равен сумме квадратов катетов!</p>
                            </v-card-text>
                        </v-card>
                    </v-tab-item>
                </v-tabs>
            </v-container>

            <v-card-actions>
                <v-btn flat color="orange" @click="createTask">Создать задачу</v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>

<script>
import axios from 'axios'
import { parse, HtmlGenerator } from 'latex.js'
import ThemeAddDialog from '../components/ThemeAddDialog'

export default {
    name: 'NewTask',

    components: { ThemeAddDialog },

    data () {
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
            taskImage: 'http://docs.likenul.com/pars_docs/refs/19/18704/18704_html_538f9f8f.png',
            taskSolution: null,
            taskExplanation: '',

            errorMessage: ''
        }
    },

    computed: {
        renderedTaskText () {
            let generator = new HtmlGenerator({ hyphenate: false })

            let doc = parse(this.taskText, { generator: generator }).htmlDocument()

            let result = doc.body.childNodes[0]
            console.log(result.innerHTML)
            return result.innerHTML
        },
        renderedTaskExplanation () {
            let generator = new HtmlGenerator({ hyphenate: false })

            let doc = parse(this.taskExplanation, { generator: generator }).htmlDocument()

            let result = doc.body.childNodes[0]
            console.log(result.innerHTML)
            return result.innerHTML
        }
    },

    created () {
        this.fetch()
    },

    watch: {
        '$route': 'fetch'
    },

    methods: {
        async fetch () {
            if (!this.themes) {
                await this.getThemes()
            }
            if (this.selectedTheme && !this.subthemes) {
                await this.getSubthemes()
            }
            if (this.selectedSubtheme && !this.difficulties) {
                await this.getDifficulties()
            }
        },

        async getThemes () {
            this.themesLoading = true
            try {
                let res = await axios.get('/api/themes')
                this.themes = res.data
            } catch (e) {
                if (e.response) {
                    alert(e.response.data)
                } else {
                    alert(e.message)
                }
            }
            this.themesLoading = false
        },

        async getSubthemes () {
            this.subthemesLoading = true
            this.selectedSubtheme = null
            try {
                console.log(this.selectedTheme)
                let res = await axios.get(`/api/subthemes/${this.selectedTheme.name}`)
                this.subthemes = res.data
            } catch (e) {
                if (e.response) {
                    alert(e.response.data)
                } else {
                    alert(e.message)
                }
            }
            this.subthemesLoading = false
        },

        async getDifficulties () {
            if (!this.selectedSubtheme) { return }

            this.difficultiesLoading = true
            try {
                let res = await axios.get(`/api/difficulties/`)
                this.difficulties = res.data
            } catch (e) {
                if (e.response) {
                    alert(e.response.data)
                } else {
                    alert(e.message)
                }
            }
            this.difficultiesLoading = false
        },

        async submitThemeAdd (event, { name, alias }) {
            console.log({ name, alias })
            try {
                let response = await axios.post('api/themes', { name, alias })
                await this.getThemes()
                this.selectedTheme = this.themes.find(value => value.name === response.data)
                this.$refs.addThemeDialog.clear()
            } catch (e) {
                if (e.response) {
                    if (e.response.status === 401) { this.errorMessage = e.response.data } else { this.errorMessage = 'Возникла ошибка: ' + e.response.statusText }
                } else {
                    this.errorMessage = 'Возникла ошибка, попробуйте позже'
                }
            }
        },

        async submitSubthemeAdd (event, { name, alias }) {
            try {
                let data = {
                    theme: this.selectedTheme.name,
                    name,
                    alias
                }
                let response = await axios.post('api/subthemes', data)
                await this.getSubthemes()
                this.selectedSubtheme = this.subthemes.find(value => value.name === response.data)
                this.$refs.addSubthemeDialog.clear()
            } catch (e) {
                if (e.response) {
                    if (e.response.status === 401) { this.errorMessage = e.response.data } else { this.errorMessage = 'Возникла ошибка: ' + e.response.statusText }
                } else {
                    this.errorMessage = 'Возникла ошибка, попробуйте позже'
                }
            }
        },

        async uploadImage(input) {
            let formData = new FormData();
            formData.append("image", input.files[0]);

            return await axios.post("api/uploads", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            })
        },

        async uploadTaskImage() {
            let response = await this.uploadImage(this.$refs.taskFileInput)
            this.taskImage = response.data.name
            console.log(this.taskImage)
        },

        async createTask() {

        }
    }
}
</script>

<style scoped>

</style>
