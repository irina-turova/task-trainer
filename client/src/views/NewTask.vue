<template>
    <div>
        <v-card>
            <v-card-title>
                <h3 class="headline mb-0">Создание новой задачи</h3>
            </v-card-title>

            <v-container>
                <v-tabs
                        v-model="active"
                >
                    <v-tab>
                        Редактирование
                    </v-tab>
                    <v-tab-item>
                        <v-card flat>
                            <v-card-title>
                                <v-text-field
                                        placeholder="Найти сумму двух чисел"
                                        label="Название задачи"
                                >
                                </v-text-field>
                            </v-card-title>
                            <v-card-text>
                                <v-textarea
                                        placeholder="Дано два числа: a и b. Нужно найти их сумму"
                                        label="Текст задачи"
                                >
                                </v-textarea>
                                <v-btn>Загрузить картинку к решению</v-btn>
                                <v-text-field
                                        placeholder="1.2"
                                        label="Ответ на задачу"
                                >
                                </v-text-field>
                                <v-textarea
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

                        </v-card>

                        <v-card>
                            <v-card-title><h3>Решение задачи:</h3></v-card-title>
                            <v-card-text>
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
    export default {
        name: 'NewTask'
    }
</script>

<style scoped>

</style>
