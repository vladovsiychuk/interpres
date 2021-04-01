<template>
    <div>
        <i>({{ message.id }})</i> {{ message.text }}
        <span style="position: absolute; right: 0">
            <input type="button" value="Edit" @click="edit" />
            <input type="button" value="Translate" @click="translate" />
            <input type="button" value="X" @click="del" />
        </span>
    <i>({{ message.translation }})</i>
    </div>
</template>

<script>
    export default {
        props: ['message', 'editMessage', 'deleteMessage', 'messages', 'translateMethod'],
        methods: {
            edit() {
                this.editMessage(this.message)
            },
            del() {
                this.deleteMessage(this.message)
            },
            translate: function() {
                this.translateMethod(this.message);
            }
        }
    }
</script>

<style>

</style>
