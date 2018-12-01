<template>
    <v-dialog
            style="display: block"
            v-model="dialog"
            width="500"
            persistent
    >
        <v-btn :block="true" slot="activator" class="accent"><v-icon left>fas fa-plus</v-icon>Добавить {{accusativeName}}</v-btn>

        <v-form ref="form" v-model="valid" @keypress.native.enter="submit($event)">
            <v-card>
                <v-card-title
                        class="headline"
                        primary-title
                >
                    Добавление {{genitiveName}}
                </v-card-title>

                <v-container>
                    <v-alert
                            v-if="errorMessage"
                            :value="true"
                            color="error"
                            icon="fas fa-exclamation-triangle"
                            outline
                            class="mb-3"
                    >
                        {{ errorMessage }}
                    </v-alert>

                    <v-text-field
                            v-model="name"
                            :rules="nameRules"
                            :placeholder="namePlaceholder"
                            :label="'Название ' + genitiveName "
                            required
                    />
                    <v-text-field
                            v-model="alias"
                            :rules="aliasRules"
                            :placeholder="aliasPlaceholder"
                            label="Служебное название"
                            required
                    />
                </v-container>

                <v-divider/>

                <v-card-actions>
                    <v-spacer/>
                    <v-btn class="accent" @click="submit($event)">Добавить</v-btn>
                    <v-btn color="primary" flat @click="clear">Отмена</v-btn>
                </v-card-actions>
            </v-card>
        </v-form>
    </v-dialog>
</template>

<script>
export default {
    name: 'ThemeAddDialog',

    props: ['genitiveName', 'accusativeName', 'namePlaceholder', 'aliasPlaceholder', 'errorMessage'],

    data () {
        return {
            dialog: false,
            valid: true,

            name: '',
            nameRules: [
                v => !!(v.trim()) || 'Обязательное поле',
                v => /^[А-Яа-яЁё ]+$/.test(v) || 'Название может содержать только русские буквы',
                v => v.trim().length >= 3 || 'Название должно содержать как минимум 3 буквы'
            ],

            alias: '',
            aliasRules: [
                v => !!(v.trim()) || 'Обязательное поле',
                v => /^\w+$/.test(v) || 'Служебное название может содержать только маленькие латинские буквы',
                v => v.trim().length >= 3 || 'Название должно содержать как минимум 3 буквы'
            ]
        }
    },

    methods: {
        submit (event) {
            if (this.$refs.form.validate()) {
                console.log(this.name, this.alias)
                this.$emit('submit', event, { name: this.name, alias: this.alias })
            }
        },

        clear () {
            this.dialog = false
            this.$refs.form.reset()
            this.errorMessage = ''
        }
    }
}
</script>

<style scoped>

</style>
