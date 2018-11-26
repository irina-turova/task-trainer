<template>
    <div>
        <v-layout
                column
        >
            <v-data-table
                    :items="messages"
                    class="elevation-1 mb-3"
                    hide-actions
                    hide-headers
            >
                <template
                        slot="items"
                        slot-scope="props"
                >
                    <v-layout
                            column
                            class="elevation-1"
                            pa-3
                    >
                        <v-label>{{props.item.user.firstName + " " + props.item.user.secondName}}</v-label>
                        <v-label>{{props.item.message}}</v-label>
                    </v-layout>
                </template>
            </v-data-table>

            <v-textarea v-model="message" outline></v-textarea>

            <v-btn
                    @click="sendMessage"
            >Отправить</v-btn>
        </v-layout>
    </div>
</template>

<script>
export default {
    name: 'Chat',

    data () {
        return {
            messages: [],
            message: ''
        }
    },

    created () {
        this.$options.sockets.onmessage = (data) => this.messages.push(JSON.parse(data.data))
    },

    methods: {
        sendMessage () {
            if (this.message.trim().length > 0) { this.$socket.send(this.message) }
            this.message = ''
        }
    }
}
</script>

<style scoped>

</style>
