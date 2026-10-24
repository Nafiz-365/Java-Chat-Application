# Java Chat Application

Java Swing desktop chat application prototype with a responsive authentication flow, custom window controls, chat layout, message components, image viewing, and profile/logout UI.

## Current status

This repository is currently a **desktop prototype**. Login and registration now use a local development account store with PBKDF2 password hashing, while sent messages are still rendered locally. A server, database, multi-user message transport, history, and reconnect logic are not implemented yet.

## Features

- Responsive login and registration cards.
- Remembered username and password visibility toggle.
- Rounded authentication card with bundled background image.
- Custom minimize, maximize/restore, and close buttons.
- Three-column chat shell with contacts, conversation, and profile areas.
- Local outgoing message rendering.
- Image overlay and chat UI components.
- Logout confirmation and return to authentication.

After successful local authentication, the signed-in username is shown in the profile panel and cleared during logout.

Outgoing messages now pass through a typed local conversation service with IDs, conversation IDs, timestamps, sender identity, and delivery status. Network delivery is still planned.

Empty conversations show a friendly state instead of demo messages, and the composer safely handles an unavailable chat event.

Pending authentication is cancelled during logout and application close so stale login results cannot reopen the chat.

Press Enter in the composer to send a message; use Shift+Enter to insert a visible new line in a multiline message.

Authentication forms clear password and registration fields after account creation and logout.

## Technology

- Java Swing
- FlatLaf 1.2
- MigLayout 4.0
- Apache Ant / NetBeans project layout
- Java source and target level 24

## Build and run

Requirements: JDK 24 and Apache Ant, NetBeans, or IntelliJ IDEA with Ant support.

```text
ant clean jar
java -jar dist/Java-Chat-Application.jar
```

The main class is `com.nafiz.main.Main`.

## Project structure

| Path | Responsibility |
|---|---|
| `src/com/nafiz/main` | Application frame and startup |
| `src/com/nafiz/form` | Login, home, chat, loading, profile, and image screens |
| `src/com/nafiz/component` | Chat messages, composer, contacts, and reusable UI |
| `src/com/nafiz/event` | Current UI callback contracts |
| `src/com/nafiz/swing` | Custom Swing controls and helpers |
| `src/com/nafiz/icon` | UI and background assets |
| `lib` | Repository-local third-party JARs |
| `test` | Existing demo/test sources |

See [ROADMAP.md](ROADMAP.md), [ARCHITECTURE.md](ARCHITECTURE.md), and [DEVELOPMENT.md](DEVELOPMENT.md) for the next stages.
