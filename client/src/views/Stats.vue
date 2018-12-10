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
                        :return-value.sync="startDateMenu"
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
                            @change="getChartData"
                            readonly
                    ></v-text-field>
                    <v-date-picker v-model="startDate" @change="onDatePickerChange" no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn flat color="primary" @click="startDateMenu = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="$refs.menuDateStart.save(startDateMenu)">OK</v-btn>
                    </v-date-picker>
                </v-menu>
            </v-flex>

            <v-flex xs6 pa-2>
                <v-menu
                        ref="menuDateFinish"
                        :close-on-content-click="false"
                        v-model="endDateMenu"
                        :nudge-right="40"
                        :return-value.sync="endDateMenu"
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
                    <v-date-picker v-model="endDate" @change="onDatePickerChange" no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn flat color="primary" @click="endDateMenu = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="$refs.menuDateFinish.save(endDateMenu)">OK</v-btn>
                    </v-date-picker>
                </v-menu>
            </v-flex>


        </v-layout>

        <v-flex px-2>
            <v-combobox
                    v-model="selectedThemes"
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

        <v-flex v-if="selectedThemes && selectedThemes.length > 0">
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
                chartData: [],
                chartOptions: {
                    chart: {
                        title: "Task statistics",
                        subtitle: "Solved tasks, right solved tasks",
                        colors: ['0000ff', '00ff00'],
                        is3D: true
                    }
                },

                startDate : new Date().toISOString().substr(0, 10),
                endDate : new Date().toISOString().substr(0, 10),
                startDateMenu: false,
                endDateMenu: false,
                selectedThemes: null,
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
                    await this.getThemes();

            },

            async getThemes() {
                this.themesLoading = true;
                try {
                    let res = await axios.get('/api/themes');
                    this.themes = res.data;
                } catch(e) {
                    if (e.response) {
                        alert(e.response.data);
                    } else {
                        alert(e.message);
                    }
                }
                this.themesLoading = false;
            },

            async getChartData(){
                if (!(this.selectedThemes && this.selectedThemes.length > 0)) return;
                
                console.log("Getting chart data...");

                try {
                    let res = await axios.get(`/api/stats/${this.startDate}/${this.endDate}/${this.selectedThemes.map((x) => x.name)}`);
                    console.log(res);
                    console.log(res.data);
                    this.chartData = res.data;
                } catch(e) {
                    if (e.response) {
                        console.log(e.response.data);
                    } else {
                        console.log(e.message);
                    }
                }

            },

            validateDates() {
                if (this.startDate > this.endDate) { 
                    this.endDate = this.startDate;
                }
            },

            onDatePickerChange() {
                this.validateDates();
                this.getChartData();
            }
        }
    };
</script>

<style scoped>
</style>
