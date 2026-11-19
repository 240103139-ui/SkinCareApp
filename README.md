# SkinCareApp

## Project Description

SkinCareApp is an Android application developed using Kotlin and Jetpack Compose. It helps users explore cosmetic products, review ingredient information, manage skin preferences, and save product reviews.

This project was developed for the Week 12 Android application assignment.

## Developer

- Asset Kozhan

## Technologies

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design 3
- MVVM Architecture
- ViewModel
- StateFlow
- Navigation Compose
- Git and GitHub

## Main Features

### Home Screen
Displays skincare information, popular products, and shortcuts to the application's main features.

### Products Screen
Shows a catalog of cosmetic products with images and a search function.

### Scanner Screen
Allows users to take a photo, select an image from the gallery, or enter ingredients manually. Automatic text and barcode recognition are not yet implemented.

### Scan Results
Displays product information and available ingredient descriptions.

### Ingredient Details
Shows the function and description of a selected ingredient.

### Skin Profile
Allows users to choose their skin type and ingredients they prefer to avoid.

### Scan History
Allows users to save, search, view, and remove product review records during the current app session.

## Project Architecture

The application follows the MVVM architecture:

- **Model:** Data classes for products, ingredients, skin profiles, and scan history.
- **View:** User interface screens created using Jetpack Compose.
- **ViewModel:** Manages application state and business logic using StateFlow.

## Navigation

Navigation Compose connects the application's screens.

The application passes a product ID from the product list to the scan results screen and an ingredient name to the ingredient details screen.

## Current Limitations

- Ingredient information uses local demonstration data.
- Automatic camera OCR and barcode recognition are not implemented.
- Scan history and skin profile preferences are not permanently stored.
- Some planned features are not yet available.

## How to Run

1. Clone the GitHub repository.
2. Open the project in Android Studio.
3. Wait for Gradle Sync to complete.
4. Start an Android emulator or connect an Android device.
5. Click Run.

## Future Improvements

- OCR ingredient recognition
- Barcode scanning
- Persistent storage using Room or DataStore
- Expanded cosmetic ingredient database
- Personalized ingredient guidance

## GitHub

This project uses Git for version control, with development progress recorded through meaningful commits.