# Louisville Youth Group Conference App (KMP)

A Kotlin Multiplatform (KMP) conference application inspired by the KotlinConf app, designed specifically for Louisville Youth Group events. This app supports both attendee and organizer roles within a single application.

## 🏗️ Architecture

This project follows a clean KMP architecture with shared business logic and platform-specific UI implementations:

```
📦 LYGConferenceApp/
├── shared/                    # KMP shared module
│   ├── commonMain/           # Shared business logic
│   │   ├── models/          # Data models (User, Session, Event, etc.)
│   │   ├── repository/      # Repository interfaces
│   │   ├── usecases/        # Business use cases
│   │   ├── viewmodels/      # Shared ViewModels
│   │   └── di/              # Dependency injection
│   ├── androidMain/         # Android-specific implementations
│   └── iosMain/             # iOS-specific implementations
├── app/                     # Android app module
│   └── src/main/java/       # Android UI with Jetpack Compose
└── iosApp/                  # iOS app module (future)
```

## 🚀 Features

### For Attendees
- **Schedule Viewing**: Browse conference sessions and workshops
- **Speaker Information**: Learn about conference speakers
- **Venue Maps**: Navigate conference locations
- **Session Registration**: Register for workshops and sessions

### For Organizers
- **Event Management**: Create and manage conferences
- **Session Management**: Add, edit, and schedule sessions
- **Speaker Management**: Manage speaker profiles and assignments
- **Analytics**: View attendance and engagement metrics
- **Push Notifications**: Send updates to attendees

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| **Shared Logic** | Kotlin Multiplatform |
| **Android UI** | Jetpack Compose + Material 3 |
| **iOS UI** | SwiftUI (planned) |
| **Networking** | Ktor Client |
| **Database** | SQLDelight |
| **DI** | Koin |
| **Authentication** | Firebase Auth |
| **Backend** | Firebase Firestore |

## 🏃‍♂️ Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Xcode 13+ (for iOS development)
- JDK 8 or later
- Kotlin 2.0.21+

### Setup

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd LYGConferenceApp
   ```

2. **Open in Android Studio**
   - Open the project in Android Studio
   - Let Gradle sync complete

3. **Run Android App**
   ```bash
   ./gradlew :app:installDebug
   ```

4. **Build iOS Framework** (Optional)
   ```bash
   ./gradlew :shared:assembleXCFramework
   ```

## 📱 User Roles

The app supports two distinct user roles:

### Attendee Flow
1. Login with attendee credentials
2. View attendee dashboard with quick actions
3. Browse schedule, speakers, and venue information
4. Register for sessions and workshops

### Organizer Flow
1. Login with organizer credentials
2. Access organizer dashboard with management tools
3. Create and manage events, sessions, and speakers
4. View analytics and send notifications

## 🔧 Configuration

### Firebase Setup
1. Create a Firebase project at [Firebase Console](https://console.firebase.google.com)
2. Add your Android app with package name: `com.lyg.conference.android`
3. Download `google-services.json` and place it in `app/` directory
4. Enable Authentication and Firestore in Firebase Console

### Environment Variables
Create a `local.properties` file in the root directory:
```properties
# Firebase configuration
firebase.api.key=your_api_key_here
firebase.project.id=your_project_id_here
```

## 🏗️ Development

### Adding New Features

1. **Shared Logic**: Add models, repositories, and use cases in `shared/src/commonMain/`
2. **Android UI**: Create Compose screens in `app/src/main/java/com/lyg/conference/android/ui/`
3. **Navigation**: Update navigation in `Navigation.kt`
4. **DI**: Register dependencies in appropriate modules

### Code Structure

```kotlin
// Example: Adding a new feature
shared/src/commonMain/kotlin/com/lyg/conference/
├── models/NewFeature.kt
├── repository/NewFeatureRepository.kt
├── usecases/GetNewFeatureUseCase.kt
└── viewmodels/NewFeatureViewModel.kt

app/src/main/java/com/lyg/conference/android/
└── ui/screens/NewFeatureScreen.kt
```

## 🧪 Testing

```bash
# Run shared module tests
./gradlew :shared:testDebugUnitTest

# Run Android tests
./gradlew :app:testDebugUnitTest

# Run instrumented tests
./gradlew :app:connectedAndroidTest
```

## 📦 Building

### Android APK
```bash
./gradlew :app:assembleDebug
```

### iOS Framework
```bash
./gradlew :shared:assembleXCFramework
```

## 🎨 Design System

The app uses Material 3 design system with Louisville Youth Group branding:
- **Primary Color**: Purple (#6750A4)
- **Typography**: Default Material 3 typography
- **Components**: Material 3 components with custom theming

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Inspired by the [KotlinConf app](https://github.com/JetBrains/kotlinconf-app)
- Built for Louisville Youth Group community
- Powered by Kotlin Multiplatform

---

**Louisville Youth Group Conference App** - Bringing communities together through technology 🌟
