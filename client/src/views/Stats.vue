<template>
    <div>
        <v-layout
                row
                wrap>
            <v-flex xs6 pa-2>
                <v-menu
                        ref="menuDateStart"
                        :close-on-content-click="false"
                        v-model="menu"
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
                            v-model="date"
                            label="Выберите дату начала"
                            readonly
                    ></v-text-field>
                    <v-date-picker v-model="date" no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn flat color="primary" @click="menu = false">Cancel</v-btn>
                        <v-btn flat color="primary" @click="$refs.menu.save(date)">OK</v-btn>
                    </v-date-picker>
                </v-menu>
            </v-flex>

            <v-flex xs6 pa-2>
                <v-menu
                        ref="menuDateFinish"
                        :close-on-content-click="false"
                        v-model="menu"
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
                            v-model="date"
                            label="Выберите дату окончания"
                            readonly
                    ></v-text-field>
                    <v-date-picker v-model="date" no-title scrollable>
                        <v-spacer></v-spacer>
                        <v-btn flat color="primary" @click="menu = false">Cancel</v-btn>
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
            />
        </v-flex>

        <v-flex v-if="selectedTheme && selectedTheme.length > 0">
            <GChart
                type="ColumnChart"
                :data="chartData"
                :options="chartOptions"
            />
        </v-flex>

        <!--<v-tabs-->
                <!--slot="extension"-->
                <!--v-model="model"-->
                <!--slider-color="yellow"-->
        <!--&gt;-->
            <!--<v-tab>Неделя</v-tab>-->
            <!--<v-tab-item>-->
                <!--<GChart-->
                        <!--type="ColumnChart"-->
                        <!--:data="chartData"-->
                        <!--:options="chartOptions"-->
                <!--/>-->
            <!--</v-tab-item>-->

            <!--<v-tab>Месяц</v-tab>-->
            <!--<v-tab-item>-->
                <!--<GChart-->
                        <!--type="ColumnChart"-->
                        <!--:data="chartData1"-->
                        <!--:options="chartOptions"-->
                <!--/>-->
            <!--</v-tab-item>-->

            <!--<v-tab>Год</v-tab>-->
            <!--<v-tab-item>-->
                <!--<GChart-->
                        <!--type="ColumnChart"-->
                        <!--:data="chartData2"-->
                        <!--:options="chartOptions"-->
                <!--/>-->
            <!--</v-tab-item>-->
        <!--</v-tabs>-->
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
                chartData1: [
                    ["Year", "Математика", "Физика", "Химия"],
                    ["1", 1000, 435, 200],
                    ["2", 1120, 460, 250],
                    ["3", 660, 1030, 300],
                    ["4", 1030, 540, 350]
                ],
                chartData2: [
                    ["Year", "Математика", "Физика", "Химия"],
                    ["9", 1000, 344, 200],
                    ["10", 1170, 460, 250],
                    ["11", 660, 1120, 300],
                    ["12", 1030, 540, 656]
                ],
                chartOptions: {
                    chart: {
                        title: "Company Performance",
                        subtitle: "Sales, Expenses, and Profit: 2014-2017"
                    }
                },
                date: new Date().toISOString().substr(0, 10),
                menu: false,
                modal: false,
                menu2: false,
                selectedTheme: null,
                themes: null,
                themesLoading: false
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

        }
    };
</script>

<style scoped>
</style>
