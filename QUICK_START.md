# Quick Start Guide

## Opening the Project in Android Studio

1. **Download/Clone the Repository**
   ```bash
   git clone https://github.com/dreyes148/Proxy_Finder.git
   cd Proxy_Finder
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Click "Open an Existing Project" or "File > Open"
   - Navigate to the `Proxy_Finder` folder
   - Click "OK"

3. **Wait for Gradle Sync**
   - Android Studio will automatically sync the project
   - Dependencies will be downloaded (this may take a few minutes)
   - Wait for "Gradle sync finished" message

4. **Run the App**
   - Connect an Android device or start an emulator
   - Click the green "Run" button (▶) or press Shift+F10
   - Select your device/emulator
   - Wait for the app to build and install

## System Requirements

- **Android Studio**: Arctic Fox (2020.3.1) or newer
- **Java**: JDK 8 or newer
- **Android SDK**: API 34 (Android 14)
- **Minimum Device**: Android 7.0 (API 24) or higher

## First Time Setup

If this is your first Android project:

1. **Install Android Studio**: Download from https://developer.android.com/studio
2. **During installation, ensure these are selected**:
   - Android SDK
   - Android SDK Platform
   - Android Virtual Device (AVD)
3. **Open SDK Manager** (Tools > SDK Manager):
   - Install "Android 14.0 (API 34)" if not present
   - Install "Android SDK Build-Tools 34"

## Using the App

### 1. Search for Proxies
- Open the app
- You'll start on the "Search" tab
- (Optional) Enter a country name or leave as "All Countries"
- Select protocols you want (HTTP, SOCKS4, SOCKS5)
- (Optional) Select anonymity levels
- Tap "Get Proxies"
- Wait for proxies to load (this fetches from 5+ sources)

### 2. View Proxy List
- Switch to the "List" tab
- See all fetched proxies
- Tap the "Copy" button on any proxy to copy it to clipboard
- Tap "Copy All" to copy all proxies at once

### 3. Check Proxy Validity
- Copy some proxies from the List tab
- Switch to the "Checker" tab
- Paste proxies into the text area (one per line, format: ip:port)
- Tap "Start Check"
- Watch as each proxy is tested
- Valid proxies show in green with response time
- Invalid proxies show in red
- Tap "Copy Valid" to copy only working proxies

## Common Issues

### Gradle Sync Failed
- **Solution**: Click "Try Again" or "File > Sync Project with Gradle Files"
- Check your internet connection
- Ensure you have the correct SDK installed

### App Won't Run
- **Check**: Do you have an emulator or connected device?
- **Check**: Is USB Debugging enabled on your device?
- **Solution**: Tools > Device Manager > Create Virtual Device

### Build Errors
- **Solution**: Build > Clean Project, then Build > Rebuild Project
- **Solution**: File > Invalidate Caches / Restart

### No Proxies Found
- **Check**: Internet connection required
- **Note**: Some API sources may be temporarily down
- **Note**: Filters may be too restrictive

## Project Structure at a Glance

```
Proxy_Finder/
├── app/
│   ├── build.gradle              # App dependencies
│   └── src/main/
│       ├── AndroidManifest.xml   # Permissions & app config
│       ├── java/com/dreyes148/proxyfinder/
│       │   ├── model/            # Data classes
│       │   ├── data/             # API & Repository
│       │   ├── ui/               # UI components
│       │   └── utils/            # Helper classes
│       └── res/
│           ├── layout/           # XML layouts
│           ├── values/           # Strings, colors, themes
│           └── menu/             # Navigation menu
├── build.gradle                  # Project-level config
└── settings.gradle               # Module config
```

## Next Steps

- **Customize**: Edit colors in `res/values/colors.xml`
- **Add Features**: Implement database storage with Room
- **Improve UI**: Customize layouts in `res/layout/`
- **Add Sources**: Add more proxy API sources in `ProxyRepository.kt`

## Support

For issues or questions:
- Check `DOCUMENTATION.md` for technical details
- Review `PROJECT_SUMMARY.md` for complete feature list
- Check Android Studio's Logcat for error messages

## Happy Coding! 🚀
