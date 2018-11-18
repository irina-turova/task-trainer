<template>
    <div>
        <v-layout
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
                        @change="$router.push(`/tasks/${selectedTheme.name}`)"
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
                        @change="$router.push(`/tasks/${selectedTheme.name}/${selectedSubtheme.name}`)"
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
                        @change="$router.push(`/tasks/${selectedTheme.name}/${selectedSubtheme.name}/${selectedDifficulty.name}`)"
                />
            </v-flex>
        </v-layout>

        <v-layout>
            <v-flex
                    v-if="task"
                    xs12
                    class="px-2">
                <v-card>
                    <v-card-title primary-title>
                        <h3 class="headline mb-0">{{task.name}}</h3>
                    </v-card-title>

                    <v-card-text>
                        <img src="http://docs.likenul.com/pars_docs/refs/19/18704/18704_html_538f9f8f.png"
                               style="float:right; max-width:300px; max-height:300px"
                        >


                        <p style="text-align: justify">
                            {{task.text}}
                        </p>

                        <div style="clear:both;"></div>


                        <v-text-field
                                placeholder="1.2"
                                label="Ответ на задачу"
                                >
                        </v-text-field>
                    </v-card-text>


                    <v-card-actions>
                        <v-btn
                                v-if="!gotSolution"
                                flat
                                color="orange">Отправить ответ
                        </v-btn>
                        <v-btn
                                flat
                                color="orange"
                        @click="gotSolution = true"
                        >Узнать решение
                        </v-btn>
                    </v-card-actions>
                </v-card>

                <v-card v-if="gotSolution">
                    <v-card-text>
                        <p>
                            Квадрат гипотенузы равен сумме квадратов катетов!
                        </p>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn
                                color="orange"
                                flat
                        >Следующая задача</v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>

    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: 'Tasks',

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
            ['$route.params.theme_name'](newVal, oldVal) {
                // this.subthemes = null
                // this.selectedSubtheme = null
                this.getSubthemes()
            },
            ['$route.params.subtheme_name'](newVal, oldVal) {
                this.getDifficulties()
            },
            ['$route.params.difficulty_name'](newVal, oldVal) {
                this.getRandomTask()
            },
        },

        methods: {
            async fetch() {
                if (!this.themes)
                    await this.getThemes()

                if (!this.selectedTheme && this.$route.params.theme_name)
                    this.selectedTheme = this.themes.find(value => value.name === this.$route.params.theme_name)

                if (this.selectedTheme && !this.subthemes)
                    await this.getSubthemes()

                if (!this.selectedSubtheme && this.$route.params.subtheme_name)
                    this.selectedSubtheme = this.subthemes.find(value => value.name === this.$route.params.subtheme_name)

                if (this.selectedSubtheme && !this.difficulties)
                    await this.getDifficulties()

                if (!this.selectedDifficulty && this.$route.params.difficulty_name)
                    this.selectedDifficulty = this.difficulties.find(value => value.name === this.$route.params.difficulty_name)

                if (!this.task && this.$route.params.difficulty_name && !this.$route.query.task_id) {
                    await this.getRandomTask()
                    this.$router.push(`/tasks/${this.selectedTheme.name}/${this.selectedSubtheme.name}/${this.selectedDifficulty.name}/${this.task.objectId.singleValue}`)
                }

                if (!this.task && this.$route.query.task_id)
                    await this.getCurrentTask()
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
                this.difficultiesLoading = true
                try {
                    let res = await axios.get(`/api/difficulties/${this.selectedSubtheme.name}`)
                    this.difficulties = res.data;
                } catch(e) {
                    if (e.response) {
                        alert(e.response.data)
                    } else {
                        alert(e.message)
                    }
                }
                this.difficultiesLoading = false
            },

            async getRandomTask() {
                try {
                    let res = await axios.get(`/api/tasks/random`)
                    this.task = res.data;
                } catch(e) {
                    if (e.response) {
                        alert(e.response.data)
                    } else {
                        alert(e.message)
                    }
                }
            },

            async getCurrentTask() {
                try {
                    let res = await axios.get(`/api/tasks/${this.selectedSubtheme.name}/${this.selectedDifficulty.name}/${this.task.objectId.singleValue}`)
                    this.task = res.data;
                } catch(e) {
                    if (e.response) {
                        alert(e.response.data)
                    } else {
                        alert(e.message)
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
