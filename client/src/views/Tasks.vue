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
                        @change="$router.push('/tasks/4/6/5')"
                />
            </v-flex>
        </v-layout>

        <v-layout>
            <v-flex
                    v-if="$route.params.difficulty"
                    xs12
                    class="px-2">
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
                ],
                gotSolution: false,
            }
        }
    }

</script>

<style scoped>

</style>
