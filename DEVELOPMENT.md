# Development Guide

## Requirements

- JDK 24
- Apache Ant, NetBeans, or IntelliJ IDEA

## Build commands

```text
ant clean
ant compile
ant jar
ant test
```

The focused service smoke test can also be run from the IDE as `testing.ServiceTest`. It verifies local password authentication, wrong-password rejection, conversation scoping, message IDs, and asynchronous local transport delivery.

The backend foundation is in `server/`. It currently exposes only
`GET /api/health` and contains a disabled-by-default Flyway PostgreSQL
migration. Authentication, repositories, and WebSocket messaging are not
enabled yet. See `server/README.md` and `server/.env.example` for setup.

Run the packaged application:

```text
java -jar dist/Java-Chat-Application.jar
```

## Source changes

The project contains NetBeans-generated `.form` files and generated initialization methods. Prefer adding behavior in dedicated methods outside generated regions. If a form layout changes, update the `.form` file through NetBeans where possible and verify generated Java output.

## Swing rules

- Keep blocking work off the Event Dispatch Thread.
- Use `SwingUtilities.invokeLater` for worker-to-UI callbacks.
- Close executors, sockets, and dialogs during logout and shutdown.
- Keep validation in forms, but keep authentication and transport logic outside forms.

## Dependency rules

Third-party libraries must be repository-local or introduced through a reproducible build mechanism. Never add developer-specific absolute paths. Keep generated `build`, `bin`, `out`, and `dist` output out of version control.

## Verification checklist

- Compile after every coherent change.
- Test login and registration validation.
- Test title-bar buttons and dragging.
- Resize at 640x480, normal, and maximized sizes.
- Verify logout resets visible session state.
- Verify no network or database work runs on the Event Dispatch Thread.
