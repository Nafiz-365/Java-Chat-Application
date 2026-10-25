# Architecture

## Current desktop client

`Main` owns the top-level undecorated frame and switches between login, loading, home, and image layers. `Home` lays out the left navigation, central `Chat`, and right profile panels. `Chat` combines the title, message body, and composer.

`PublicEvent` stores the small set of callbacks needed by the Swing screens. The
client is deliberately offline-first: local authentication and local conversation
services keep the UI responsive without a server.

## Target architecture

```text
Swing UI -> local session/conversation services
```

- **UI:** renders state and collects input.
- **Controllers:** coordinate user actions and loading/error state.
- **Session service:** connect, authenticate, logout, and reconnect.
- **Conversation service:** contacts, history, selection, send, and receive.
- **Local transport:** asynchronous in-memory message delivery for the prototype.

The current implementation includes `ChatTransport` and `LocalChatTransport`. The local adapter preserves the UI behavior while exposing connection, history, send, receive, error, and disconnect events. A real WebSocket adapter can replace it without changing the chat form.

## Threading rules

Long-running work must never run on the Swing Event Dispatch Thread. Background
callbacks must update Swing components through `SwingUtilities.invokeLater`.
Shutdown must cancel local transport workers.

## Data ownership

The local services own users, message IDs, timestamps, delivery state, and history
for this desktop prototype.

## Migration

1. Keep the current UI while introducing typed application state.
2. Replace direct `PublicEvent` calls at service boundaries.
3. Add persistence only when the desktop experience is ready.
