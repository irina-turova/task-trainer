<template>
    <div>
        <v-layout
                row
                wrap>
            <v-flex
                    xs4
                    class="px-2">
                <v-combobox
                        v-model="selectCat"
                        :items="items"
                        label="Выберите категорию"
                        @change="$router.push('/tasks/4')"
                />
            </v-flex>

            <v-flex
                    xs4
                    class="px-2">
                <v-combobox
                        v-if="$route.params.category_id"
                        v-model="selectSubcat"
                        :items="items"
                        label="Выберите подкатегорию"
                        @change="$router.push('/tasks/4/6')"
                />
            </v-flex>

            <v-flex
                    v-if="$route.params.subcategory_id"
                    xs4
                    class="px-2">
                <v-combobox
                        v-model="selectDiff"
                        :items="items"
                        label="Выберите сложность"
                />
            </v-flex>
        </v-layout>

        <v-layout>
            <v-flex
                    xs12
                    class="px-2">
                <v-card>
                    <v-img
                            src="https://cdn.vuetifyjs.com/images/cards/desert.jpg"
                            aspect-ratio="2.75"
                    />

                    <v-card-title primary-title>
                        <div>
                            <h3 class="headline mb-0">Kangaroo Valley Safari</h3>
                            <div>Located two hours south of Sydney in the <br>Southern Highlands of New South Wales, ...
                            </div>
                            <div>{{ $route.params.category_id }}</div>
                        </div>
                    </v-card-title>

                    <v-card-actions>
                        <v-btn
                                flat
                                color="orange">Share
                        </v-btn>
                        <v-btn
                                flat
                                color="orange">Explore
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>

    </div>
</template>

<script>
    export default {
        name: 'Tasks',

        data() {
            return {
                selectCat: null,
                selectSubcat: null,
                selectDiff: null,
                items: [
                    'Programming',
                    'Design',
                    'Vue',
                    'Vuetify'
                ]
            }
        }
    }

</script>

<style scoped>

</style>
