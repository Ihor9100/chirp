# Fix Firebase Initialization Error

The application is failing with `java.lang.IllegalStateException: Default FirebaseApp is not initialized`. This is because the `google-services` plugin is not applied to the main application module (`androidApp`), and the `google-services.json` file is in the wrong location (`composeApp/`).

## User Review Required

> [!IMPORTANT]
> I am moving `google-services.json` from `composeApp/` to `androidApp/` and applying the Google Services plugin to the `androidApp` module. This is the standard way to initialize Firebase on Android.

## Proposed Changes

### Build Configuration

#### [MODIFY] [androidApp/build.gradle.kts](file:///C:/Users/ihorb/StudioProjects/chirp/androidApp/build.gradle.kts)
- Apply the `google-services` plugin.

#### [MODIFY] [feature/chat/data/build.gradle.kts](file:///C:/Users/ihorb/StudioProjects/chirp/feature/chat/data/build.gradle.kts)
- Remove the `google-services` plugin as it should only be applied to the application module.

### File Relocation

#### [NEW] [androidApp/google-services.json](file:///C:/Users/ihorb/StudioProjects/chirp/androidApp/google-services.json)
- Copy the content from `composeApp/google-services.json`.

#### [DELETE] [composeApp/google-services.json](file:///C:/Users/ihorb/StudioProjects/chirp/composeApp/google-services.json)
- Remove the misplaced file.

### Application Initialization

#### [MODIFY] [ChirpApplication.kt](file:///C:/Users/ihorb/StudioProjects/chirp/androidApp/src/main/java/com/plcoding/chirp/ChirpApplication.kt)
- Explicitly call `FirebaseApp.initializeApp(this)` to ensure Firebase is ready before Koin or other components try to use it.

## Verification Plan

### Automated Tests
- Run the application and verify that the `IllegalStateException` no longer occurs when `FirebaseTokenProvider` is accessed.

### Manual Verification
- Deploy the app to a device/emulator.
- Check logs for "FirebaseTokenProvider | New token: ..." which indicates successful token retrieval.
