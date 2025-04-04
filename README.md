# SpeedyEats

## Project Description

**SpeedyEats** is an e-commerce mobile app designed for online food sales, specifically fast food. The app includes essential features like login/register, product search, product categorization, homepage banners, online payment functionality, product favorites, and profile management.

This project serves as the **final project** for the **Software Architecture - Service-Oriented** course at the University of Economics. The app is built using **MVP (Model-View-Presenter)** architecture and is developed in **Java** using **Android Studio**, with **Firebase** for data management.

## Architecture

- **Software Architecture**: MVP (Model-View-Presenter)
- **Backend**: Firebase (Firestore, Authentication, Storage)
- **Languages**: Java
- **Development Environment**: Android Studio

## Project Structure

- **app/**: Main Android app source files.
- **gradle/**: Gradle configuration files.
- **.gitignore**: Specifies files to ignore in version control.
- **README.md**: Project documentation.

## Setup Instructions

Follow these steps to get started with the project:

### 1. Clone the repository:
   git clone https://github.com/botrn10/speedyeats.git
### 2. Open the project in Android Studio.
### 3. Install dependencies:
Android Studio will prompt you to sync the Gradle files. Ensure all dependencies are downloaded and synced properly.

### 4. Configure Firebase:

Create a Firebase project at Firebase Console.

Download the google-services.json file from your Firebase project.

Place the google-services.json file in the app/ directory.

### 5. Install required SDKs and libraries:

Android SDK version 31

Google Play Services (e.g., com.google.android.gms:play-services-maps)

Firebase dependencies (Authentication, Firestore, Storage)

Kotlin Standard Library (if not already available)

### 6. Run the app:
Select the target device (either a physical Android device or an emulator), and click the "Run" button in Android Studio.

## Requirements
- **Android Studio**: Download from https://developer.android.com/studio.

- **JDK**: Ensure the Java Development Kit is installed.

- **Google Play Services**: Required for Maps and Firebase features.

- **Firebase Project**: Configure Firebase services like Authentication, Firestore, and Storage as described above.

## Dependencies
- com.android.application
- com.google.gms:google-services
- firebase-auth
- firebase-firestore
- firebase-storage
- picasso
- glide
- retrofit
- androidx.appcompat:appcompat
- androidx.constraintlayout:constraintlayout

Check build.gradle for the full list of dependencies.

## License
This project is licensed under [the MIT License](LICENSE).

