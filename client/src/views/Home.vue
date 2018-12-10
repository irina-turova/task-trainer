<template>
    <div>
        <v-layout>
            <v-flex xs12 sm6 offset-sm3>
                <v-card>
                    <v-card-title primary-title>
                    <div>
                        <h3 class="headline mb-0">Добро пожаловать!</h3>
                        <div><br>Данный сайт предназначен для решения задач по различным предметам<br>Нажмите кнопку "Начать", чтобы попробовать</div>
                    </div>
                    </v-card-title>

                    <v-card-actions>
                    <v-btn flat color="blue" @click="$router.push('/tasks')">Начать</v-btn>
                    </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
      </div>
</template>

<script>

import VContent from 'vuetify/lib/components/VGrid/VContent'

export default {
    name: 'Home',
    components: { VContent }
}
</script>
