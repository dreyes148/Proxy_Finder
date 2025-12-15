# Proxy Finder

A complete Android application for finding, listing, and checking proxy servers.

## Features

### 🔍 Search Module
- Fetch proxies from multiple sources:
  - Geonode API (JSON)
  - ProxyScan API (JSON)
  - GitHub raw proxy lists (HTTP, SOCKS4, SOCKS5)
- Filter by:
  - Country
  - Protocol (HTTP, SOCKS4, SOCKS5)
  - Anonymity level (Transparent, Anonymous, Elite)

### 📋 List Module
- Display fetched proxies in a RecyclerView
- Shows IP, Port, Country, Protocol, and Anonymity
- Copy individual proxy (ip:port format)
- Copy all proxies at once
- Efficient list updates using DiffUtil

### ✅ Checker Module
- Paste multiple proxies (ip:port format)
- Check proxy validity by testing connection to Google
- Display response time in milliseconds
- Show valid/invalid status with color coding
- Copy only valid proxies
- Start/Stop checking process
- Progress indicator

## Technical Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI**: ViewBinding
- **Networking**: Retrofit 2 + OkHttp
- **Async**: Kotlin Coroutines + LiveData
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Project Structure

```
app/src/main/java/com/dreyes148/proxyfinder/
├── model/          # Data classes
│   ├── Proxy.kt
│   ├── ApiModels.kt
│   └── Resource.kt
├── data/           # Repository and API services
│   ├── ProxyApiService.kt
│   └── ProxyRepository.kt
├── ui/             # Activities, Fragments, ViewModels, Adapters
│   ├── MainActivity.kt
│   ├── SearchFragment.kt
│   ├── ListFragment.kt
│   ├── CheckerFragment.kt
│   ├── ProxyViewModel.kt
│   ├── CheckerViewModel.kt
│   ├── ProxyAdapter.kt
│   └── CheckerResultAdapter.kt
└── utils/          # Helper classes
    └── ProxyChecker.kt
```

## Dependencies

- AndroidX Core, AppCompat, Material Components
- Fragment KTX
- Lifecycle & ViewModel
- Kotlin Coroutines
- Retrofit 2 with Gson converter
- OkHttp with logging interceptor

## Building the Project

1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Build and run on an emulator or device

```bash
./gradlew assembleDebug
```

## Usage

1. **Search Tab**: Select filters and tap "Get Proxies" to fetch from all sources
2. **List Tab**: View fetched proxies, tap copy icons to copy to clipboard
3. **Checker Tab**: Paste proxies, tap "Start Check" to verify them

## API Sources

- [Geonode Proxy List](https://proxylist.geonode.com/api/proxy-list)
- [ProxyScan](https://www.proxyscan.io/api/proxy)
- [TheSpeedX PROXY-List GitHub](https://github.com/TheSpeedX/PROXY-List)

## License

This project is for educational purposes.