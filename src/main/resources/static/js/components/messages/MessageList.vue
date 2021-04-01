<template>
    <div style="position: relative; width: 300px;">
        <message-form :messages="messages" :messageAttr="message" :action="action" />
        <message-row v-for="message in messages"
                     :key="message.id"
                     :message="message"
                     :editMessage="editMessage"
                     :deleteMessage="deleteMessage"
                     :messages="messages"
                     :translateMethod="translateMethod"/>
    </div>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'

    export default {
        props: ['messages'],
        components: {
            MessageRow,
            MessageForm
        },
        data() {
            return {
                message: null,
                action: ''
            }
        },
        methods: {
            editMessage(message) {
                this.message = message;
                this.action = 'edit'
            },
            deleteMessage(message) {
                this.$resource('/message{/id}').remove({id: message.id}).then(result => {
                    if (result.ok) {
                        this.messages.splice(this.messages.indexOf(message), 1)
                    }
                })
            },
            translateMethod: function(message) {
                this.message = message;
                this.action = 'translate'
            }
        }
    }
</script>

<style>

</style>
