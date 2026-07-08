# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Chirp** is a Kotlin Multiplatform (KMP) chat app targeting **Android and iOS** (no Desktop target despite the README boilerplate). The shared UI is built with Compose Multiplatform.

Package root: `com.plcoding.chirp`  
API base URL: `https://chirp.pl-coding.com/api`  
API key: sourced from `local.properties` → `API_KEY` (injected via BuildKonfig convention plugin at build time)

## Build Commands

```bash
# Android
./gradlew :androidApp:assembleDebug
./gradlew :androidApp:assembleRelease
./gradlew :androidApp:bundleRelease          # AAB

# Run Android unit tests
./gradlew :androidApp:testDebugUnitTest

# Build any single module (e.g.)
./gradlew :core:data:build
./gradlew :feature:chat:database:build
./gradlew :composeApp:build                  # builds Android + iOS klibs/framework

# iOS framework (consumed by Xcode)
./gradlew :composeApp:iosSimulatorArm64Binaries
```

No lint/detekt/ktlint setup exists in this repo.

## Module Graph

```
androidApp           ← thin Android Application shell (ChirpApplication, launcher Activity)
composeApp           ← KMP app entry point; wires all modules, hosts NavigationRoot + initKoin()
  ├── core:domain          ← pure KMP: domain models, Result types, repository interfaces
  ├── core:data            ← Ktor HTTP client, DataStore, auth token/refresh logic
  ├── core:designsystem    ← shared Compose theme, reusable UI components
  ├── core:presentation    ← BaseScreenViewModel, ScreenStatePm, Event<T>, nav extensions
  ├── feature:auth:domain
  ├── feature:auth:presentation
  ├── feature:chat:domain
  ├── feature:chat:database  ← Room KMP: entities, DAOs, expect/actual DB builder
  ├── feature:chat:data      ← repository impls, Koin DI wiring for chat
  └── feature:chat:presentation
```

Each feature is split into `domain` (interfaces/models) and `presentation` (ViewModels/screens/nav); `feature:chat:data` and `feature:chat:database` are separate modules for the data layer.

## Convention Plugins (build-logic)

Convention plugins live in `build-logic/convention/src/main/kotlin/` and define the module "tiers":

| Plugin alias | What it configures |
|---|---|
| `kmp.library.convention` | KMP + Android/iOS targets, serialization, coroutines, Koin, Ktor core |
| `cmp.library.convention` | Extends `kmp` + Compose MP runtime/UI/Material3, Coil |
| `cmp.feature.convention` | Extends `cmp.library` + adds core:designsystem/domain/presentation deps + full Koin VM bundle |
| `targets.convention` | Used by `composeApp`: Android + iOS static framework named `"ComposeApp"` |
| `room.convention` | KSP + Room plugin, schema export to `$projectDir/schemas`, BundledSQLiteDriver |
| `build.konfig.convention` | BuildKonfig plugin, reads `API_KEY` from `local.properties` |

When creating a new module, pick the right convention plugin tier rather than configuring targets/deps manually.

## Dependency Injection (Koin)

Each module declares its own top-level `val xModule = module { ... }`. Modules that need platform-specific bindings use `expect val platformXDiModule: Module` with `actual` implementations in `androidMain`/`iosMain`.

All modules are assembled in `composeApp/src/commonMain/.../di/DiRoot.kt`:

```kotlin
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        modules(appModule, coreDataDiModule, authPresentationDiModule, chatDataDiModule, chatPresentationDiModule)
    }
}
```

`initKoin` is called from `ChirpApplication.onCreate()` (Android) and `MainViewController.kt` (iOS).

## Networking

`core/data/.../tools/HttpClientFactory.kt` builds a Ktor `HttpClient` with:
- Platform engine: OkHttp (Android) / Darwin (iOS) — injected via DI
- `x-api-key` header set from `BuildKonfig.API_KEY`
- Bearer auth with auto-refresh: reads/writes `AuthInfo` via DataStore; calls `POST /auth/refresh`; clears tokens on failure
- 20s timeouts, WebSocket support (20s ping), JSON content negotiation

## Database (Room KMP)

`feature/chat/database` — `ChirpDatabase` with entities: `ChatEntity`, `ChatMemberEntity`, `ChatMessageEntity`, `ChatAndMemberEntity`, plus a `ChatLastMessageView`.

Cross-platform builder: `expect class ChirpDatabaseBuilderFactory` with `actual` implementations per platform that resolve the DB file path (Android `getDatabasePath`, iOS `NSHomeDirectory/documents`).

KSP Room compiler runs for android + all three iOS targets (iosArm64/iosX64/iosSimulatorArm64). Room schemas are exported to `feature/chat/database/schemas/`.

## Navigation

Jetpack Compose Navigation (multiplatform) with `@Serializable` type-safe routes.

- `composeApp/.../navigation/NavigationRoot.kt` — root `NavHost` composing `authGraph()` and `chatGraph()`
- Each feature owns its route sealed interface (e.g. `AuthRoute`, `ChatRoute`) + a `NavGraphBuilder` extension function (`authGraph`, `chatGraph`)
- Auth graph supports deep links: `https://chirp.pl-coding.com/api/auth/reset-password`, `https://chirp.pl-coding.com/api/auth/verify`, and `chirp://` scheme mirrors
- Shared nav helpers in `core/presentation/.../utils/NavControllerExt.kt`: `navigateNewRoot`, `navigateFresh`, `navigateWithPopUpTo`

## Presentation Pattern

MVVM + unidirectional state (MVI-flavored). Key base classes in `core:presentation`:

- **`BaseScreenViewModel<ContentPm>`** — exposes a single `StateFlow<ScreenStatePm<ContentPm>>`. First collection triggers `onInitialize()`. Helper methods: `updateContentPm { copy(...) }`, `launchLoadable { }` (wraps suspend call with Blocker/Loader overlays), `showSnackbar(stringRes)`.
- **`ScreenStatePm<ContentPm>`** — wraps cross-cutting overlays (Blocker, Loader, Snackbar) + feature-specific `ContentPm`.
- **`Event<T>`** — single-consumption wrapper for one-shot UI events (navigation triggers, snackbar messages).

Typical file set for a screen:
```
XScreen.kt                 ← Composable
XScreenViewModel.kt        ← extends BaseScreenViewModel<XScreenContentPm>
XScreenContentPm.kt        ← immutable data class (UI state)
XScreenContentPmMapper.kt  ← domain model → ContentPm mapping
```

Actions are plain public ViewModel methods called directly from Composables (no separate sealed `UiAction` class). Repository results use a custom `Result` type from `core.domain.result` with `onSuccess`/`onFailure` callbacks.
