# Security

The current application is a desktop prototype. It stores local development accounts in Java Preferences using PBKDF2-derived hashes, but it does not yet authenticate against a server, protect message transport, or provide authorization. The remembered username is the only login preference; passwords must never be stored there.

## Required controls

- Hash passwords on the server with Argon2id, bcrypt, or an equivalent slow password-hashing algorithm. The current server foundation uses PBKDF2-HMAC-SHA256 with 210,000 iterations and a random salt.
- Use TLS for authentication, messages, and attachments.
- Use short-lived access tokens with rotation, expiration, and revocation.
- Validate usernames, messages, filenames, and attachment size/type on the server.
- Authorize every conversation and attachment operation server-side.
- Never log passwords, tokens, message contents, or sensitive attachment paths.
- Keep local preferences limited to non-sensitive settings.
- Do not render untrusted message content as executable HTML.
- Rate-limit repeated authentication failures.
- Clear session state and close network resources on logout.

The Swing client is untrusted. The server must not trust client timestamps, delivery states, user IDs, permissions, or attachment metadata.
