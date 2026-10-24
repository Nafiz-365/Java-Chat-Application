package com.nafiz.protocol;

public enum ProtocolMessageType {
    AUTH_REGISTER("auth.register"),
    AUTH_LOGIN("auth.login"),
    SESSION_REFRESH("session.refresh"),
    SESSION_LOGOUT("session.logout"),
    CONVERSATION_LIST("conversation.list"),
    CONVERSATION_HISTORY("conversation.history"),
    MESSAGE_SEND("message.send"),
    MESSAGE_ACCEPTED("message.accepted"),
    MESSAGE_RECEIVED("message.received"),
    MESSAGE_READ("message.read"),
    MESSAGE_FAILED("message.failed"),
    PRESENCE_CHANGED("presence.changed");

    private final String wireName;

    ProtocolMessageType(String wireName) {
        this.wireName = wireName;
    }

    public String wireName() {
        return wireName;
    }
}
