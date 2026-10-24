# Architecture

## Current client

`Main` owns the top-level undecorated frame and switches between login, loading, home, and image layers. `Home` lays out the left navigation, central `Chat`, and right profile panels. `Chat` combines the title, message body, and composer.

`PublicEvent` currently stores mutable callbacks for main-window, login, chat, and image-view actions. Its singleton initialization and callback access are synchronized, but it should still be replaced or isolated behind application services before networking is added.

## Target architecture

```text
Swing UI -> controllers -> session/conversation services
         -> transport client -> chat server -> persistence
```

- **UI:** renders state and collects input.
- **Controllers:** coordinate user actions and loading/error state.
- **Session service:** connect, authenticate, logout, and reconnect.
- **Conversation service:** contacts, history, selection, send, and receive.
- **Transport client:** protocol encoding, decoding, and socket lifecycle.
- **Server:** authentication, authorization, routing, presence, and history.
- **Persistence:** users, password hashes, conversations, messages, and attachments.

The current implementation includes `ChatTransport` and `LocalChatTransport`. The local adapter preserves the UI behavior while exposing connection, history, send, receive, error, and disconnect events. A real WebSocket adapter can replace it without changing the chat form.

## Threading rules

Blocking network and database work must never run on the Swing Event Dispatch Thread. Background callbacks must update Swing components through `SwingUtilities.invokeLater`. Shutdown must cancel workers and close transports.

## Data ownership

The server is authoritative for users, permissions, message IDs, timestamps, delivery state, and history. The client may cache display data but must reconcile it with server events.

## Migration

1. Keep the current UI while introducing typed application state.
2. Add transport-neutral session and conversation services.
3. Replace direct `PublicEvent` calls at service boundaries.
4. Add a concrete transport adapter and server.
5. Remove simulated login after the real session flow is verified.
