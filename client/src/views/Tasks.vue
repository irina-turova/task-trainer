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
                        :disabled="!themes"
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
                        :disabled="!subthemes"
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
                        :disabled="!difficulties"
                        :items="difficulties"
                        item-text="description"
                        item-value="name"
                        label="Выберите сложность"
                        @change="$router.push(`/tasks/${selectedTheme.name}/${selectedSubtheme.name}/${selectedDifficulty.name}`)"
                />
            </v-flex>
        </v-layout>

        <v-layout row>
            <v-flex v-if="task === undefined" xs12 ma-2>
                <v-alert
                        :value="true"
                        color="info"
                        icon="fas fa-info"
                        outline
                >
                    В этой подтеме больше нет доступных задач!
                </v-alert>
            </v-flex>
            <v-flex
                    v-if="task"
                    xs12
                    class="px-2">
                <v-card>
                    <v-card-title primary-title>
                        <h3 class="headline mb-0">{{task.name}}</h3>
                    </v-card-title>

                    <v-card-text>
                        <v-img v-if="taskImage" :src="'/' + taskImage" style="display:block; margin: 0 auto; max-width:300px; max-height:300px"
                        ></v-img>


                        <v-container v-html="renderedTaskText"></v-container>

                        <div style="clear:both;"></div>


                        <v-text-field
                                placeholder="1.2"
                                label="Ответ на задачу"
                                v-model="actualAnswer"
                                @change="onAnswerChange"
                                @click="onAnswerChange"
                                @keypress.native.enter="sendSolution"
                                >
                        </v-text-field>
                        <v-card color="success" class="white--text"
                            v-if="isRightAnswer && actualAnswerSent">
                            <v-card-text>Правильный ответ</v-card-text>
                        </v-card>
                        <v-card color="error" class="white--text"
                            v-if="!isRightAnswer && actualAnswerSent">
                            <v-card-text>Неправильный ответ</v-card-text>
                        </v-card>
                        <v-flex xs12 ma-2>
                        <v-alert
                                v-if="blankAnswer && triedToSend"
                                hidden=true
                                :value="true"
                                color="info"
                                icon="fas fa-info"
                                outline
                        >
                            Введите Ваш ответ на задачу
                        </v-alert>
            </v-flex>
                    </v-card-text>



                    <v-card-actions>
                        <v-btn
                                v-if="!gotSolution && !actualAnswerSent"
                                color="accent"
                                @click="sendSolution"
                                >
                            <v-icon left>fas fa-paper-plane</v-icon>Отправить ответ
                        </v-btn>
                        <v-btn
                                v-if="!gotSolution"
                                flat
                                color="primary"
                        @click="useHint"
                        >Узнать решение
                        </v-btn>
                    </v-card-actions>
                </v-card>

                <v-card v-if="gotSolution || actualAnswerSent">
                    <v-img v-if="solutionImage" :src="'/' + solutionImage" style="display:block; margin: 0 auto; max-width:300px; max-height:300px"
                    ></v-img>
                    <v-card-text
                        v-if="gotSolution"
                        v-html="renderedTaskExplanation">
                    </v-card-text>
                    <v-card-actions>
                        <v-btn
                                v-if="actualAnswerSent || gotSolution"
                                color="accent"
                                flat
                                @click="getNextTask"
                        >Следующая задача<v-icon right>fas fa-arrow-right</v-icon></v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import axios from 'axios'
    import { parse, HtmlGenerator } from 'latex.js'

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
                actualAnswer: null,
                actualAnswerSent : false,
                isRightAnswer: false,
                taskImage: null,
                solutionImage: null,
                triedToSend: false,
                blankAnswer: true
            }
        },

        created () {
            this.fetch()
        },

        watch: {
            '$route': 'fetch',
            ['$route.params.theme_name'](newVal, oldVal) {
                this.subthemes = null
                this.selectedSubtheme = null
                this.getSubthemes()
            },
            ['$route.params.subtheme_name'](newVal, oldVal) {
                this.difficulties = null
                this.selectedDifficulty = null
                this.getDifficulties()
            },
            ['$route.params.difficulty_name'](newVal, oldVal) {
                if (newVal)
                    this.getRandomTask()
                else
                    this.task = null
            },
            ['$route.params.task_id'](newVal, oldVal) {
                this.gotSolution = false
                this.actualAnswer = null
                this.actualAnswerSent = false
            },
        },

        computed : {
            renderedTaskText() {
                let generator = new HtmlGenerator({ hyphenate: false })

                let doc = parse(this.task.text, { generator: generator }).htmlDocument()

                let result = doc.body.childNodes[0]
                console.log(result.innerHTML)
                return result.innerHTML
            },
            renderedTaskExplanation() {
                let generator = new HtmlGenerator({ hyphenate: false })

                let doc = parse(this.task.explanation + "\n\rПравильный ответ: " + this.task.rightAnswer, { generator: generator }).htmlDocument()

                let result = doc.body.childNodes[0]
                console.log(result.innerHTML)
                return result.innerHTML
            }
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

                if (!this.task && this.$route.params.difficulty_name && !this.$route.params.task_id) {
                    console.log("will get random")
                    await this.getRandomTask()
                    if (this.task)
                        this.$router.push(`/tasks/${this.selectedTheme.name}/${this.selectedSubtheme.name}/${this.selectedDifficulty.name}/${this.task.objectId.singleValue}`)
                }

                if (!this.task && this.$route.params.task_id)
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
            },

            async getRandomTask() {
                try {
                    console.log("getting random task")
                    let res = await axios.get(`/api/tasks/${this.selectedSubtheme.name}/${this.selectedDifficulty.name}`)
                    console.log(res)
                    if (res.data === "") {
                        this.task = undefined
                        return
                    }
                    this.task = res.data;

                    if (this.task.taskImage) {
                        let taskImageRes = await axios.get(`/api/uploasds/${this.task.taskImage}`)
                        this.taskImage = "userdata/" + taskImageRes.data
                    }

                    if (this.task.answerImage) {
                        let taskImageRes = await axios.get(`/api/uploasds/${this.task.answerImage}`)
                        this.solutionImage = "userdata/" + taskImageRes.data
                    }

                    console.log(this.task)
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
                    console.log("getting current task" + `/api/tasks/${this.selectedSubtheme.name}/${this.selectedDifficulty.name}/${this.$route.params.task_id}`)
                    let res = await axios.get(`/api/tasks/${this.selectedSubtheme.name}/${this.selectedDifficulty.name}/${this.$route.params.task_id}`)
                    this.task = res.data;
                    console.log(res.data)

                    if (this.task.image1) {
                        let taskImageRes = await axios.get(`/api/tasks/taskimage/task/${this.task.objectId.singleValue}`)
                        console.log("data")
                        console.log(taskImageRes.data)
                        this.taskImage = "userdata/" + taskImageRes.data
                    }

                    if (this.task.image) {
                        let taskImageRes = await axios.get(`/api/tasks/taskimage/solution/${this.task.objectId.singleValue}`)
                        this.solutionImage = "userdata/" + taskImageRes.data
                        console.log(this.solutionImage)
                    }
                    console.log(this.taskImage)
                } catch(e) {
                    if (e.response) {
                        alert(e.response.data)
                    } else {
                        alert(e.message)
                    }
                }
            },

            async getNextTask() {
                await this.getRandomTask()
                this.$router.push(`/tasks/${this.selectedTheme.name}/${this.selectedSubtheme.name}/${this.selectedDifficulty.name}/${this.task ? this.task.objectId.singleValue : ''}`)
            },

            async sendSolution() {
                this.triedToSend = true;
                this.blankAnswer = this.actualAnswer == null;
                console.log(this.triedToSend)
                console.log(this.actualAnswer)
                if (this.blankAnswer) {
                    return;
                }

                var data = {
                    taskId: this.task.objectId.singleValue,
                    actualAnswer: this.actualAnswer,
                    }
                axios.post('/api/tasks/check', data)
                    .then((response) => {
                        console.log(response);
                        this.actualAnswerSent = true
                        this.isRightAnswer = response.data
                    })
                    .catch((error) => {
                        console.log(error);
                    }); 
            },

            async useHint() {
                this.gotSolution = true;
                
                if (this.actualAnswerSent)
                    return;

                var data = {
                    taskId: this.task.objectId.singleValue,
                    actualAnswer: "",
                    }
                axios.post('/api/tasks/check', data)
                    .then((response) => {
                        console.log(response);

                    })
                    .catch((error) => {
                        console.log(error);
                    }); 
            },

            onAnswerChange(){
                this.triedToSend = false;
                this.blankAnswer = false;
            }
        
        }
    }
</script>

<style scoped>
</style>
