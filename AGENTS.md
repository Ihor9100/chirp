# AGENTS.md

Repository guidance for coding agents working on **Chirp**.

## Core Rules

-   Inspect relevant code before making changes.
-   Follow existing architecture, naming, and module boundaries.
-   Prefer the smallest change that satisfies the task.
-   Reuse existing abstractions and patterns instead of introducing
    parallel solutions.
-   Do not perform unrelated refactoring or fix unrelated issues unless
    they block the requested task.
-   Do not silently invent product or backend behavior. If an important
    decision cannot be determined from the task or repository, ask the
    user.
-   Keep platform behavior consistent across Android and iOS when
    changing shared KMP code.
-   Never commit secrets or machine-specific configuration such as
    `local.properties`.
-   Unless explicitly requested, do not commit, push, or create a pull
    request.

## Project Overview

**Chirp** is a Kotlin Multiplatform (KMP) chat application targeting
**Android and iOS**. There is no Desktop target despite README
boilerplate. Shared UI is built with Compose Multiplatform.

-   Package root: `com.plcoding.chirp`
-   API base URL: `https://chirp.pl-coding.com/api`
-   API key: `local.properties` -\> `API_KEY`
-   `API_KEY` is injected at build time through the BuildKonfig
    convention plugin.

## Module Graph

``` text
androidApp
└── thin Android application shell
    (ChirpApplication, launcher Activity)

composeApp
└── KMP application entry point
    ├── NavigationRoot
    └── initKoin()

core:domain
└── domain models, Result types, repository interfaces

core:data
└── Ktor HTTP client, DataStore, authentication/token refresh

core:designsystem
└── shared Compose theme and reusable UI components

core:presentation
└── BaseScreenViewModel, ScreenStatePm, Event<T>, navigation helpers

feature:auth:domain
feature:auth:presentation

feature:chat:domain
feature:chat:database
feature:chat:data
feature:chat:presentation
```

Features are split into `domain` and `presentation` modules. Chat
additionally has separate `data` and `database` modules.

Respect these boundaries. Do not move responsibilities between layers
without a task-specific reason.

## Source Sets and KMP

Prefer shared implementations in `commonMain` when behavior is
platform-independent.

Use platform source sets only when platform APIs are required:

``` text
commonMain
androidMain
iosMain
```

When changing shared APIs, inspect relevant Android and iOS
implementations/call sites before considering the change complete.

Do not introduce Android-only APIs into `commonMain`.

## Convention Plugins

Convention plugins live in:

``` text
build-logic/convention/src/main/kotlin/
```

They define the module tiers:

  -----------------------------------------------------------------------
  Plugin alias                        Purpose
  ----------------------------------- -----------------------------------
  `kmp.library.convention`            KMP + Android/iOS targets,
                                      serialization, coroutines, Koin,
                                      Ktor core

  `cmp.library.convention`            Extends KMP library with Compose
                                      Multiplatform runtime/UI/Material3
                                      and Coil

  `cmp.feature.convention`            Extends CMP library with core
                                      design/domain/presentation
                                      dependencies and Koin ViewModel
                                      bundle

  `targets.convention`                Android + iOS static framework for
                                      `composeApp`, framework name
                                      `ComposeApp`

  `room.convention`                   KSP + Room, schema export,
                                      BundledSQLiteDriver

  `build.konfig.convention`           BuildKonfig; reads `API_KEY` from
                                      `local.properties`
  -----------------------------------------------------------------------

When creating a module, use the appropriate existing convention plugin
rather than manually duplicating target or dependency configuration.

Do not modify convention plugins merely to work around an agent
environment or verification problem.

## Dependency Injection

Dependency injection uses **Koin**.

Each module declares its own top-level module, for example:

``` kotlin
val xModule = module {
    // bindings
}
```

Platform-specific bindings use `expect`/`actual`, for example:

``` kotlin
expect val platformXDiModule: Module
```

with implementations in `androidMain` and `iosMain`.

Application modules are assembled in:

``` text
composeApp/src/commonMain/.../di/DiRoot.kt
```

`initKoin()` is called from:

-   `ChirpApplication.onCreate()` on Android
-   `MainViewController.kt` on iOS

Follow existing module ownership when adding bindings.

## Networking

`core/data/.../tools/HttpClientFactory.kt` creates the Ktor
`HttpClient`.

Current networking conventions:

-   OkHttp engine on Android
-   Darwin engine on iOS
-   `x-api-key` from `BuildKonfig.API_KEY`
-   Bearer authentication
-   auth state persisted through DataStore
-   token refresh through `POST /auth/refresh`
-   tokens cleared when refresh fails
-   20-second timeouts
-   WebSocket support with 20-second ping
-   JSON content negotiation

Follow existing Ktor configuration, DTO, serialization, and repository
patterns when adding networking behavior.

Do not invent backend contracts that cannot be determined from the task
or repository. Ask the user when a contract decision is required.

## Database

Chat persistence uses Room KMP in:

``` text
feature/chat/database
```

`ChirpDatabase` currently contains chat-related entities and
`ChatLastMessageView`.

The database builder is cross-platform:

``` kotlin
expect class ChirpDatabaseBuilderFactory
```

with Android and iOS `actual` implementations.

Room/KSP runs for Android and the iOS targets. Room schemas are exported
to:

``` text
feature/chat/database/schemas/
```

When changing persisted models:

-   inspect entities, relations, DAOs, views, mappers, and schema
    output;
-   verify DTO/domain/database/domain mapping where relevant;
-   keep persistence behavior consistent across sync/restart paths;
-   do not add a production migration unless the task/release state
    requires one.

## Navigation

Navigation uses multiplatform Jetpack Compose Navigation with
`@Serializable` type-safe routes.

Root navigation:

``` text
composeApp/.../navigation/NavigationRoot.kt
```

Each feature owns its routes and `NavGraphBuilder` extension.

Examples include:

-   `AuthRoute`
-   `ChatRoute`
-   `authGraph()`
-   `chatGraph()`

Shared navigation helpers live in:

``` text
core/presentation/.../utils/NavControllerExt.kt
```

including `navigateNewRoot`, `navigateFresh`, and `navigateWithPopUpTo`.

Follow the existing type-safe navigation approach rather than
introducing ad-hoc route strings.

## Presentation Pattern

Presentation uses MVVM with unidirectional state (MVI-flavored).

Important primitives in `core:presentation`:

### `BaseScreenViewModel<ContentPm>`

Exposes:

``` kotlin
StateFlow<ScreenStatePm<ContentPm>>
```

The first collection triggers `onInitialize()`.

Common helpers include:

``` kotlin
updateContentPm { copy(...) }
launchLoadable { ... }
showSnackbar(...)
```

### `ScreenStatePm<ContentPm>`

Wraps cross-cutting UI state such as:

-   Blocker
-   Loader
-   Snackbar
-   feature-specific content state

### `Event<T>`

Used for single-consumption UI events such as navigation and snackbar
triggers.

Typical screen structure:

``` text
XScreen.kt
XScreenViewModel.kt
XScreenContentPm.kt
XScreenContentPmMapper.kt
```

Actions are public ViewModel methods called directly from Composables.
Do not introduce a sealed `UiAction` hierarchy unless the task
specifically requires an architectural change.

Repository operations use the project's custom `Result` type from
`core.domain.result` with `onSuccess` / `onFailure`.

## Build and Verification

### General Strategy

Use the **smallest relevant verification** for the files/modules
changed.

Do not start with a full application build when compiling the affected
modules is sufficient.

After implementation:

1.  inspect the diff;
2.  compile affected modules;
3.  run focused relevant tests when they exist;
4.  run broader checks only when justified by the scope;
5.  distinguish failures caused by the task from
    pre-existing/environment failures.

No lint, detekt, or ktlint setup currently exists in this repository.

### Android Module Compilation

For KMP modules, Android compilation tasks use `compileAndroidMain`.

Examples:

``` bash
./gradlew :core:designsystem:compileAndroidMain
./gradlew :feature:chat:domain:compileAndroidMain
./gradlew :feature:chat:presentation:compileAndroidMain
```

When several affected modules need verification, prefer one Gradle
invocation:

``` bash
./gradlew \
  :core:designsystem:compileAndroidMain \
  :feature:chat:domain:compileAndroidMain \
  :feature:chat:presentation:compileAndroidMain
```

On Windows/PowerShell use:

``` powershell
.\gradlew.bat :core:designsystem:compileAndroidMain :feature:chat:domain:compileAndroidMain :feature:chat:presentation:compileAndroidMain
```

Do not guess task names. If the required task is unclear, inspect the
relevant module's available Gradle tasks first.

### Application Builds

Use broader builds when the task requires application-level
verification:

``` bash
./gradlew :androidApp:assembleDebug
./gradlew :androidApp:assembleRelease
./gradlew :androidApp:bundleRelease
```

Android unit tests:

``` bash
./gradlew :androidApp:testDebugUnitTest
```

Module builds when appropriate:

``` bash
./gradlew :core:data:build
./gradlew :feature:chat:database:build
```

A broad KMP build is available through:

``` bash
./gradlew :composeApp:build
```

but it builds Android and iOS artifacts and should not be the default
verification for a small Android/shared-code change.

iOS framework:

``` bash
./gradlew :composeApp:iosSimulatorArm64Binaries
```

### `local.properties` and API Key

The BuildKonfig convention plugin reads:

``` text
local.properties
└── API_KEY
```

A Codex-managed Git worktree may not contain `local.properties` because
it is machine-local and not tracked by Git.

Rules:

-   never commit `local.properties`;
-   never commit a real API key;
-   do not change production/build logic just to bypass the missing
    property;
-   prefer the machine's existing local configuration when accessible;
-   if a temporary worktree-local configuration is necessary only for
    Gradle configuration, keep it temporary and remove it before
    completion;
-   never claim runtime/network behavior was verified using a dummy key.

Before completing the task, confirm that temporary verification files
were removed.

### Android SDK and Gradle Cache in Managed Worktrees

Managed/sandboxed agent environments may not automatically inherit the
normal Android SDK or Gradle cache paths.

If required for verification:

-   use the machine's existing Android SDK configuration;
-   use the existing user Gradle cache;
-   do not relocate or modify project build configuration to compensate
    for sandbox restrictions;
-   request the required execution permission when the environment
    requires access outside the sandbox.

On the current Windows development environment, prefer the existing
user/machine configuration rather than creating repository-specific SDK
or Gradle installations.

Environment setup is not a product-code change.

### Verification Failures

If verification fails:

1.  determine whether the failure was introduced by the task;
2.  fix it only if it is caused by the implementation;
3.  report unrelated pre-existing failures separately;
4.  report environment/sandbox failures separately;
5.  do not modify unrelated code just to make checks green.

A failed unrelated module or unavailable iOS toolchain does not justify
unrelated repository changes.

## Tests

Add or update focused tests when behavior changes and the repository has
an appropriate test location/pattern.

Prioritize tests for:

-   business logic;
-   mapping;
-   persistence;
-   validation;
-   state transitions;
-   failure handling;
-   regressions introduced by the task.

Do not add low-value tests merely to increase test count.

When a change is purely visual and the repository has no established
UI-test pattern for it, compilation plus focused logic tests may be more
appropriate than introducing a new testing framework.

## Git and Worktrees

Codex may run tasks inside managed Git worktrees.

Treat the current worktree as the task's isolated workspace.

Before editing:

-   inspect `git status`;
-   preserve unrelated pre-existing changes;
-   do not overwrite or clean files you did not create;
-   do not modify another worktree.

Before completion:

-   inspect the final diff;
-   ensure temporary files are removed;
-   report unrelated dirty files separately.

Unless explicitly requested:

-   do not commit;
-   do not push;
-   do not create a branch manually when Codex already provided a
    managed worktree;
-   do not create a pull request.

## Scope and Decision Making

Separate repository facts from unresolved decisions.

Agents may determine from the repository:

-   architecture;
-   existing implementation patterns;
-   naming;
-   module ownership;
-   existing API behavior;
-   established validation rules;
-   test conventions.

Agents must ask the user when an important decision cannot be
determined, for example:

-   new product behavior;
-   unspecified limits;
-   ambiguous UX behavior;
-   undefined backend/API contracts;
-   compatibility requirements not represented in the repository.

Do not silently choose a "reasonable" product value when the task
requires a decision.

If an unrelated bug or improvement is discovered:

-   mention it separately;
-   do not include it in the task unless it blocks implementation or the
    user expands the scope.

## Completion Checklist

Before reporting completion:

-   requirements are satisfied;
-   relevant diff was inspected;
-   affected modules compile as far as the environment permits;
-   relevant focused tests were run where appropriate;
-   reviewer findings, if any, were addressed or explicitly rejected
    with reasoning;
-   temporary files/configuration were removed;
-   unrelated changes were left untouched;
-   unresolved verification limitations are reported;
-   no commit/push/PR was performed unless requested.

Keep the final report concise and include:

-   what changed;
-   important implementation decisions;
-   tests/checks run and their results;
-   anything that could not be verified;
-   relevant out-of-scope issues.
