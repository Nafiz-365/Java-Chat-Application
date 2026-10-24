# Chat Protocol Proposal

The production client/server protocol should use TLS-protected JSON messages over a persistent WebSocket-compatible transport. The protocol is versioned so incompatible clients can reject messages safely.

## Envelope

```json
{
  "version": 1,
  "type": "message.send",
  "requestId": "client-generated-id",
  "payload": {}
}
```

Responses include `type`, the original `requestId` when applicable, and a structured `error` object on failure.

## Authentication

- `auth.register`
- `auth.login`
- `session.refresh`
- `session.logout`

Successful login returns a short-lived access token and user profile. Passwords are never returned or logged.

## Conversations and messages

- `conversation.list`
- `conversation.history`
- `message.send`
- `message.accepted`
- `message.received`
- `message.read`
- `message.failed`
- `presence.changed`

Each message has a server ID, client request ID, sender, conversation ID, content, timestamp, and delivery state. Client request IDs make retries idempotent.

## Errors

```json
{
  "code": "AUTH_INVALID_CREDENTIALS",
  "message": "The username or password is incorrect.",
  "retryable": false
}
```

Clients show safe user-facing messages and keep diagnostic details in secure logs only.

## Client contract

The client-side contract is represented by `com.nafiz.protocol.ProtocolEnvelope`.
`ProtocolMessageType` keeps wire names centralized, while `ProtocolError` provides
stable error codes and retryability. Envelope version `1` is currently supported.
Payload objects are intentionally transport-neutral; JSON encoding belongs in the
future network adapter rather than in Swing forms or chat services.
