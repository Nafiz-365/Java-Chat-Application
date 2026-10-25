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

## 3. Local authentication

- Keep local registration and login fast, clear, and safe.
- Improve password feedback and session cleanup.
- Display useful credential and validation errors.

## 4. Local messaging

- Load contacts and conversations.
- Send and receive messages through the local transport.
- Add IDs, timestamps, acknowledgements, failures, retries, and history pagination.
- Reconcile reconnects without duplicate messages.

## 5. Chat experience

- Add search, unread counts, presence, notifications, empty states, and active conversation navigation.
- Implement attachment upload/download with size and type limits.
- Implement image saving and progress/error states.

## 6. Desktop readiness

- Keep sensitive values out of preferences and logs.
- Add packaging and release documentation.
- Test narrow, normal, maximized, and high-DPI layouts.
