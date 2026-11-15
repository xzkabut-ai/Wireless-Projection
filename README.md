# Wireless Projection

A lightweight Android app that opens the device's native Cast or Wireless Display settings. Wireless display support and connection status are controlled by Android and vary by phone and TV. The app does not simulate a connection.

## Requirements

- JDK 17
- Android SDK Platform 35 (installed automatically by the GitHub Actions workflow)
- Android Gradle Plugin 8.7.3 / Gradle 8.9
- Minimum Android version: API 26

## Build locally

Run the Gradle wrapper from the repository root:

```shell
./gradlew assembleDebug
```

On Windows, use `gradlew.bat assembleDebug`. The debug APK is written to `app/build/outputs/apk/debug/`.

## GitHub Actions APKs

The workflow runs on pushes and pull requests targeting `main`, and can also be started with **Actions > Android Build > Run workflow**. It builds both debug and release APKs and uploads them as separate workflow artifacts. Download an APK from the completed workflow run's **Artifacts** section. The release APK is unsigned; install the debug APK for testing or configure signing before distributing a release.

## Device behavior

Press **CONNECT TV** to open Android's Wireless Display or Cast settings, when provided by the device. Use **Settings** to open system settings directly. Android does not expose a reliable public API for this app to confirm a wireless display connection across devices, so the app does not claim that a connection succeeded. Miracast and related features are not supported on every Android phone or television.
