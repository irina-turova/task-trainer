<template>
    <div>
        <v-tabs
                slot="extension"
                v-model="model"
                slider-color="yellow"
        >
            <v-tab>Неделя</v-tab>
            <v-tab-item>
                <GChart
                        type="ColumnChart"
                        :data="chartData"
                        :options="chartOptions"
                />
            </v-tab-item>

            <v-tab>Месяц</v-tab>
            <v-tab-item>
                <GChart
                        type="ColumnChart"
                        :data="chartData1"
                        :options="chartOptions"
                />
            </v-tab-item>

            <v-tab>Год</v-tab>
            <v-tab-item>
                <GChart
                        type="ColumnChart"
                        :data="chartData2"
                        :options="chartOptions"
                />
            </v-tab-item>
        </v-tabs>
    </div>
</template>

<script>
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
                }
            };
        }
    };
</script>

<style scoped>
</style>
