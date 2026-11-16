# Wireless Projection

Wireless Projection is a native Android app built with Kotlin, Jetpack Compose, and Material 3. Its dark navy and blue home screen opens the phone's Cast or Wireless Display settings, shows presentation displays reported by Android, and includes About, Version, Help, and Privacy pages.

## Requirements

- JDK 17
- Android SDK Platform 35
- Android Gradle Plugin 8.7.3 and Gradle 8.9
- Android 8.0 (API 26) or newer

## Build

From the repository root:

```shell
./gradlew assembleDebug
```

On Windows, run `gradlew.bat assembleDebug`. The debug APK is written to `app/build/outputs/apk/debug/`.

## GitHub Actions

The workflow builds Debug and Release APKs on pushes and pull requests targeting `main`, and supports manual runs through **Actions > Wireless Projection Android Build > Run workflow**. Android SDK Platform 35 and Build Tools are installed in the workflow. Both APKs are uploaded as separate artifacts on a successful run. Download them from that run's **Artifacts** section. The Release APK is unsigned.

## Device behavior and limitations

- **Connect to TV** opens Android's Cast or Wireless Display settings when the device provides a matching system activity. The system handles discovery and connection.
- The app lists presentation displays reported by Android's public `DisplayManager` API. A reported display can be wired or wireless; Android does not expose the connection type consistently.
- Android does not provide a universal public API for third-party apps to scan and list nearby Miracast/Wireless Display receivers. If no external display is reported, the app directs users to system settings rather than showing fabricated devices.
- Connection status is based on the presentation displays currently reported by Android. Wireless display support varies by phone and TV.
- The app does not request special permissions and does not use a server, account, database, or internet access.
