# Security

The current application is an offline desktop prototype. It stores local
development accounts in Java Preferences using PBKDF2-derived hashes. The
remembered username is the only login preference; passwords must never be stored
there.

## Required controls

- Keep using a slow password-hashing algorithm with a random salt. The local
  service uses PBKDF2-HMAC-SHA256 with 210,000 iterations.
- Validate usernames and message content before storing or displaying them.
- Never log passwords, tokens, message contents, or sensitive attachment paths.
- Keep local preferences limited to non-sensitive settings.
- Do not render untrusted message content as executable HTML.
- Rate-limit repeated authentication failures.
- Clear local session state on logout.

This local-only edition has no network trust boundary. If synchronization is
added later, authentication, authorization, transport encryption, and
remote input validation must be designed before enabling it.
