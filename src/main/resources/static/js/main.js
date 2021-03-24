
function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}


var messageApi = Vue.resource('/message{/id}');
var messageTranslationApi = Vue.resource('/message/translation{/id}');

Vue.component('message-form', {
    props: ['messages', 'messageAttr', 'action'],
    data: function () {
        return {
            text: '',
            id: '',
            translation: ''
        }
    },
    watch: {
        messageAttr: function (newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
            this.translation = newVal.translation;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Write something" v-model="text" />' +
        '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function () {

            if (this.action == 'edit') {
                var message = {text: this.text}

                messageApi.update({id: this.id}, message).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.messages, data.id);
                        this.messages.splice(index, 1, data);
                        this.text = ''
                        this.id = ''
                    })
                )

            } else if (this.action == 'translate') { //this.action == 'translate'
                var translationMessage = {translation: this.text};
                messageTranslationApi.update({id: this.id}, translationMessage).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.messages, data.id);
                        this.messages.splice(index, 1, data);
                        this.text = ''
                        this.id = ''
                    })
                )
            } else {
                var message = {text: this.text};
                messageApi.save({}, message).then(result =>
                    result.json().then(data => {
                        this.messages.push(data);
                        this.text = ''
                    })
                )
            }

        }
    }
});

Vue.component('message-row', {
    props: ['message', 'editMethod', 'translateMethod', 'messages'],
    template: '<div>' +
        '<i>({{ message.id }})</i> {{ message.text }}' +
        '<span style="position: absolute; right: 0">' +
            '<input type="button" value="Edit" @click="edit" />' +
            '<input type="button" value="Translate" @click="translate" />' +
            '<input type="button" value="X" @click="del" />' +
        '</span>' +
        '<i>({{ message.translation }})</i>' +
        '</div>',
    methods: {
        edit: function() {
            this.editMethod(this.message);
        },
        translate: function() {
            this.translateMethod(this.message);
        },
        del: function() {
            messageApi.remove({id: this.message.id}).then(result => {
                if (result.ok) {
                    this.messages.splice(this.messages.indexOf(this.message), 1)
                }
            })
        }
    }
});

Vue.component('messages-list', {
  props: ['messages'],
  data: function() {
    return {
        message: null,
        action: ''
    }
  },
  template:
    '<div style="position: relative; width: 300px;">' +
        '<message-form :messages="messages" :messageAttr="message" :action="action" />' +
        '<message-row v-for="message in messages" :key="message.id" :message="message" ' +
            ':editMethod="editMethod" :translateMethod="translateMethod" :messages="messages" />' +
    '</div>',
  methods: {
    editMethod: function(message) {
        this.message = message;
        this.action = 'edit'
    },
    translateMethod: function(message) {
        this.message = message;
        this.action = 'translate'
    }
  }
});

var app = new Vue({
  el: '#app',
  template:
    '<div>' +
        '<div v-if="!profile">You need to log in through <a href="/login">Google</a></div>' +
        '<div v-else>' +
            '<div>{{profile.name}}&nbsp;<a href="/logout">Exit</a></div>' +
            '<messages-list :messages="messages" />' +
        '</div>' +
    '</div>',
  data: {
    messages: frontendData.messages,
    profile: frontendData.profile
  },
  created: function() {
//    messageApi.get().then(result =>
//        result.json().then(data =>
//            data.forEach(message => this.messages.push(message))
//        )
//    )
  },
});
