<template>
    <div>
        <div v-if="!profile">You need to log in through
            <a href="/login">Google</a>
        </div>
        <div v-else>
            <div>{{profile.name}}&nbsp;<a href="/logout">Exit</a></div>
            <messages-list :messages="messages" />
        </div>
    </div>
</template>

<script>
    import MessagesList from 'components/messages/MessageList.vue'

    export default {
        components: {
            MessagesList
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        }
    }
</script>

<style>

</style>
