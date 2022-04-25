# Spring Security - SecurityFilterChain bean instead of WebSecurityConfigurerAdapter

Demonstrate the differences in behavior between a `SecurityFilterChain` bean and
extending `WebSecurityConfigurerAdapter`.

There are two implementation of the same app, protected by HTTP basic. Users can authenticate with  `user:password`
through a provided `UserDetailsService` bean, that is wired into a `DaoAuthenticationProvider` and the global
authentication manager. That authentication manager has a `DefaultAuthenticationEventPublisher` wired in, and
authentication events are emitted in both implementation.

Users can also authenticate with a custom `FooUserAuthenticationProvider`, which allows username `foo` with any
password, e.g. `foo:bar` or `foo:baz`. In the case of `WebSecurityConfigurerAdapter`, authentication events are emitted.
In the case of the `SecurityFilterChain` bean, no events are emitted, because the local authentication manager has
a `NullEventPublisher`.

Both cases are illustrated in a unit test,
in [AuthenticationEventTests](src/test/java/wf/garnier/springsecurityeventpublisher/AuthenticationEventTests.java).

Tested with Java 11.

## Running app

### With `WebSecurityConfigurerAdapter` (profile = `adapter`)

- Run the application with `SPRING_PROFILES_ACTIVE=adapter ./gradlew bootRun`
- Run `curl -v localhost:8080 -u "user:password"`, this should print something in the console,
  e.g.
    ```console
    ~~~~~~~> SUCCESSFUL AUTH, type: UsernamePasswordAuthenticationToken, name: user
    ```
- Run `curl -v localhost:8080 -u "foo:bar"`, this should print something in the console,
  e.g.
    ```console
    ~~~~~~~> SUCCESSFUL AUTH, type: UsernamePasswordAuthenticationToken, name: foo
    ```

### With `@Bean public SecurityFilterChain ...()` (profile = `filterchain`)

- Run the application with `SPRING_PROFILES_ACTIVE=filterchain ./gradlew bootRun`
- Run `curl -v localhost:8080 -u "user:password"`, this should print something in the console,
  e.g.
    ```console
    ~~~~~~~> SUCCESSFUL AUTH, type: UsernamePasswordAuthenticationToken, name: user
    ```
- Run `curl -v localhost:8080 -u "foo:bar"`, this does not print anything in the console.
