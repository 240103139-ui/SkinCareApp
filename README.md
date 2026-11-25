
# SkinCareApp

## Project Description

SkinCareApp is an Android application built with Kotlin and Jetpack Compose.

The app helps users explore skincare products, view ingredients, save products, and manage scan history.

This project implements the requirements of Assignments 8 and 9.

## Developer

- Asset Kozhan

## Technologies

- Kotlin
- Jetpack Compose
- Material Design 3
- MVVM Architecture
- Navigation Compose
- Retrofit and Gson
- Kotlin Coroutines
- StateFlow
- Coil
- Room Database
- Preferences DataStore
- Git and GitHub

## Assignment 8: REST API Integration

### API

The application uses the Open Beauty Facts API.

API URL:
https://world.openbeautyfacts.org/

### Architecture

Retrofit API Service
→ Repository
→ ViewModel
→ StateFlow
→ Jetpack Compose UI

### Features

- Fetch skincare products from a public API
- Display product names and brands
- Show ingredient information when available
- Load product images using Coil
- Show loading indicator during API requests
- Display results after successful requests
- Handle network and server errors
- Display an empty state when no products are returned
- Retry failed requests

### API UI States

1. Loading
2. Success
3. Error
4. Empty

## Assignment 9: Room Database and DataStore

### Room Database

The app uses Room to store scan history locally.

Database name: saved_scans_database

Entity: SavedScanEntity

DAO: SavedScanDao

Repository: SavedScanRepository

ViewModel: ScanHistoryViewModel

### Room CRUD Operations

Create:
Save a product to scan history.

Read:
Display saved products in Scan History.

Update:
Edit and save a note for a product.

Delete:
Delete one saved product or clear the entire history.

Room stores saved scans locally, so records remain available after restarting the app.

### Preferences DataStore

The app uses DataStore to save the preferred scan history order.

Available options:

- Newest first
- Oldest first

The selected preference is saved locally and restored after restarting the app.

## Main Screens

### Home Screen

Shows navigation to skincare features.

### Products Screen

Displays a local demonstration product catalog.

### Explore Beauty Products

Fetches products from the Open Beauty Facts API.

### Scanner Screen

Supports image selection and manual ingredient entry.

Automatic OCR and barcode recognition are not implemented.

### Scan Results

Shows product details and ingredient information.

### Ingredient Details

Displays ingredient descriptions.

### Skin Profile

Allows users to select skin-related preferences.

### Scan History

Allows users to:

- View saved products
- Search saved products
- Add and edit notes
- Delete saved products
- Clear scan history
- Change history sorting

## Project Architecture

The application follows MVVM.

Model:
Product models, Room entities, API data models.

View:
Jetpack Compose screens.

ViewModel:
Manages UI state using StateFlow.

Repository:
Handles API requests and local database operations.

## How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle.
4. Start an Android emulator or connect a device.
5. Run the app.

Internet access is required for API requests.

## Manual Testing

### API Test

1. Open Explore Beauty Products.
2. Check the loading indicator.
3. Check that product data appears.
4. Disable internet access and retry.
5. Check the error message.
6. Enable internet access and retry again.

### Room Test

1. Open a local product.
2. Save it.
3. Open Scan History.
4. Add a note using Edit.
5. Close and reopen the app.
6. Check that the product and note remain.
7. Delete the product.
8. Check that it disappears.

### DataStore Test

1. Save at least two products.
2. Open Scan History.
3. Select Oldest first.
4. Close and reopen the app.
5. Check that Oldest first remains selected.
6. Switch back to Newest first.

## Current Limitations

- Some product and ingredient data is local demonstration data.
- Automatic OCR and barcode scanning are not implemented.
- Skin profile settings are not permanently stored.
- API results depend on network access and available API data.
- The API product list and local scan history use separate product sources.

## Future Improvements

- OCR ingredient recognition
- Barcode scanning
- More detailed ingredient analysis
- Persistent skin profile
- Integration of API products with scan history