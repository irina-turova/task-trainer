<template>
    <div>
        <v-layout
                row
                wrap>
            <v-flex xs6 pa-2>
                <v-menu
                        ref="menuDateStart"
                        :close-on-content-click="false"
                        v-model="startDateMenu"
                        :nudge-right="40"
                        :return-value.sync="date"
                        lazy
                        transition="scale-transition"
                        offset-y
                        full-width
                        min-width="290px"
                >
                    <v-text-field
                            slot="activator"
                            v-model="startDate"
                            label="Выберите дату начала"
                            readonly
                    ></v-text-field>
                    <v-date-picker v-model="startDate" @change="validateDates" no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn flat color="primary" @click="startDateMenu = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="$refs.menu.save(date)">OK</v-btn>
                    </v-date-picker>
                </v-menu>
            </v-flex>

            <v-flex xs6 pa-2>
                <v-menu
                        ref="menuDateFinish"
                        :close-on-content-click="false"
                        v-model="endDateMenu"
                        :nudge-right="40"
                        :return-value.sync="date"
                        lazy
                        transition="scale-transition"
                        offset-y
                        full-width
                        min-width="290px"
                >
                    <v-text-field
                            slot="activator"
                            v-model="endDate"
                            label="Выберите дату окончания"
                            readonly
                    ></v-text-field>
                    <v-date-picker v-model="endDate" @change="validateDates" no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn flat color="primary" @click="endDateMenu = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="$refs.menu.save(date)">OK</v-btn>
                    </v-date-picker>
                </v-menu>
            </v-flex>


        </v-layout>

        <v-flex px-2>
            <v-combobox
                    v-model="selectedTheme"
                    :items="themes"
                    item-text="description"
                    item-value="name"
                    :loading="themesLoading"
                    label="Выберите темы"
                    multiple
                    chips
                    @change="getChartData"
            />
        </v-flex>

        <v-flex v-if="selectedTheme && selectedTheme.length > 0">
            <GChart
                type="ColumnChart"
                :data="chartData"
                :options="chartOptions"
            />
        </v-flex>
    </div>
</template>

<script>

    import axios from 'axios'
    import { GChart } from 'vue-google-charts'

    export default {
        name: "Stats",
        components: {
            GChart
        },

        data() {
            return {
                // Array will be automatically processed with visualization.arrayToDataTable function
                chartData: [
                    ["Year", "Математика", "Физика", "Химия"],
                    ["Пн", 1000, 400, 200],
                    ["Вт", 1170, 460, 250],
                    ["Ср", 660, 1120, 300],
                    ["Чт", 1030, 540, 350]
                ],
                chartOptions: {
                    chart: {
                        title: "Company Performance",
                        subtitle: "Sales, Expenses, and Profit: 2014-2017"
                    }
                },

                startDate : new Date().toISOString().substr(0, 10),
                endDate : new Date().toISOString().substr(0, 10),
                startDateMenu: false,
                endDateMenu: false,
                selectedTheme: null,
                themes: null,
                themesLoading: false,
            };
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

            async getChartData(){
                if (!(this.selectedTheme && this.selectedTheme.length > 0)) return;

                const params = {
                    start: this.startDate,
                    end: this.endDate,
                    theme: this.themes
                }

                try {
                    let res = await axios.get('/api/stats', params)
                    this.chartData = res.data;
                } catch(e) {
                    if (e.response) {
                        alert(e.response.data)
                    } else {
                        alert(e.message)
                    }
                }

            },

            validateDates(){
                if (this.startDate > this.endDate) { 
                    this.endDate = this.startDate 
                }
            }
        }
    };
</script>

<style scoped>
</style>
