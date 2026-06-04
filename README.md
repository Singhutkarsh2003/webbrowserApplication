# Web Browser Application

A modern Android application built with Kotlin and Jetpack Compose that allows users to sign in with Google, browse websites inside a WebView, track browsing history, receive welcome notifications, and switch between dark and light themes.

## Features

* Google Sign-In using Firebase Authentication
* Open websites inside WebView
* Browsing History using Room Database
* Welcome Notification
* Dark/Light Theme Support
* Logout Functionality
* Modern UI with Jetpack Compose
* MVVM Architecture

## Tech Stack

* Kotlin
* Jetpack Compose
* Firebase Authentication
* Google Credential Manager
* Room Database
* Navigation Compose
* Coroutines
* Material 3

## Project Structure

```text
ui/
├── signin/
├── home/
├── webview/
├── history/
├── navigation/
└── theme/

data/
├── local/
└── repository/
```

## Firebase Setup

1. Create a Firebase Project.
2. Enable Google Authentication.
3. Download `google-services.json`.
4. Place it inside the `app/` folder.
5. Add SHA-1 and SHA-256 fingerprints.

## Notification

Welcome notification:

Title:
Welcome Back

Message:
Thanks for opening the app

Rules:

* Shows only once per day
* Not shown immediately after login
* Supports Android 13+ notification permission

## Architecture

The project follows MVVM Architecture:

* UI Layer (Compose Screens)
* ViewModel Layer
* Repository Layer
* Room Database Layer



Android Developer
