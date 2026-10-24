# Changelog

## Unreleased

- Added in-memory session state and displayed the authenticated username in the profile panel.
- Added typed chat messages and a local conversation service for outgoing message state.
- Replaced hardcoded demo messages with an empty-conversation state.
- Added authentication cancellation and stale-callback protection during logout and application close.
- Added Enter-to-send and explicit Shift+Enter newline insertion in the composer.
- Clear authentication fields after registration and logout to prevent stale credentials.
- Added focused service smoke tests for authentication and local conversation history.
- Made the shared event registry safely initialized and synchronized.
- Added a transport abstraction and asynchronous local transport adapter for future server integration.
- Added local development registration and login using PBKDF2 password hashing.
- Moved password hashing and authentication work off the Swing Event Dispatch Thread.
- Added project architecture, development, protocol, security, roadmap, and README documentation.
- Documented the distinction between the current local UI prototype and planned multi-user behavior.
- Defined the implementation sequence for authentication, sessions, messaging, reliability, and packaging.

## Prototype history

- Added responsive login and registration flow.
- Added remembered username and password visibility control.
- Added logout confirmation and return-to-login behavior.
- Added responsive background image and rounded authentication card.
- Added custom minimize, maximize/restore, and close controls.
- Added local chat layout and message components.
- Ensured simulated login completion updates Swing on the Event Dispatch Thread.
