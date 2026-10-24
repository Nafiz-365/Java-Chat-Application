# Roadmap

## 1. Stable prototype

- Document current UI and behavior.
- Keep dependencies repository-local.
- Verify authentication navigation, logout, window controls, and resizing.
- Add focused validation and model tests.

## 2. Architecture foundation

- Define typed application state and service interfaces.
- Define authentication, session, conversation, message, and error models.
- Add an executor/threading policy for Swing.
- Replace silent placeholders with explicit results.

## 3. Authentication

- Add server repositories on top of the versioned database schema.
- Register users with password hashing.
- Implement login, token expiration, logout, and authorization.
- Display connection and credential errors.

## 4. Real messaging

- Load contacts and conversations.
- Send and receive messages through the server.
- Add IDs, timestamps, acknowledgements, failures, retries, and history pagination.
- Reconcile reconnects without duplicate messages.

## 5. Chat experience

- Add search, unread counts, presence, notifications, empty states, and active conversation navigation.
- Implement attachment upload/download with size and type limits.
- Implement image saving and progress/error states.

## 6. Production readiness

- Use TLS, secure session storage, rate limits, audit logging, and dependency scanning.
- Add automated tests, packaging, and release documentation.
- Test narrow, normal, maximized, high-DPI, offline, reconnect, and server-error scenarios.
