# GraphDB


## Running

Start the server with a Spring bean config, e.g.
`java -cp <classpath> server.Server file:src/main/java/config/test.xml`
(edit `restConfigName` in the config to point at your copy of `src/main/java/rest/configuration.yml`).

Security notes:

- The REST API (`POST /graphdb`) and the Dropwizard admin connector have **no authentication**.
  Both connectors bind to `127.0.0.1` by default (`bindHost` in `configuration*.yml`). Only change
  this behind an authenticating reverse proxy or a firewall.
- Database files are stored under `~/.graphdb/<cluster>/<db>/` (the `location` property). The
  server creates each database directory with owner-only permissions and refuses to start if it
  is a symlink or owned by another user. Do not point `location` at a shared directory like `/tmp`.
