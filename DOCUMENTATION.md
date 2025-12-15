# Proxy Finder - Development Documentation

## Project Overview

This is a complete, production-ready Android application built with Kotlin following modern Android development best practices.

## Architecture

### MVVM Pattern
The app uses the Model-View-ViewModel architecture:

- **Model**: Data classes in `model/` package
- **View**: Fragments and XML layouts
- **ViewModel**: `ProxyViewModel`, `CheckerViewModel` for managing UI state

### Key Components

#### 1. Data Layer
- `ProxyRepository`: Singleton repository for fetching proxies from multiple sources
- `ProxyApiService`: Retrofit interface for Geonode API
- `ProxyScanApiService`: Retrofit interface for ProxyScan API
- `RawProxyApiService`: Retrofit interface for GitHub raw text lists

#### 2. Domain Layer
- `Proxy`: Main data model representing a proxy server
- `Resource`: Sealed class for handling network states (Loading, Success, Error)
- `GeonodeResponse`, `ProxyScanProxy`: API-specific response models

#### 3. UI Layer

**MainActivity**
- Hosts fragments using BottomNavigationView
- Initializes shared ProxyViewModel

**SearchFragment**
- Allows user to select filters
- Triggers proxy fetching via ProxyViewModel
- Observes loading states and results

**ListFragment**
- Displays proxies in RecyclerView
- Uses ProxyAdapter with DiffUtil for efficiency
- Shares data with SearchFragment via ProxyViewModel
- Allows copying proxies to clipboard

**CheckerFragment**
- Accepts pasted proxy list
- Uses CheckerViewModel to check each proxy
- Shows real-time progress and results
- Uses CheckerResultAdapter to display results

#### 4. Utilities
- `ProxyChecker`: Validates proxy by attempting connection through it
  - Uses OkHttp with custom proxy settings
  - Measures response time
  - Handles different proxy types (HTTP, SOCKS)

## Threading Model

All network operations run on `Dispatchers.IO`:
- Retrofit API calls
- Proxy validation checks

UI updates happen on `Dispatchers.Main`:
- LiveData observations
- RecyclerView updates

## Data Flow

### Search Flow
1. User selects filters and taps "Get Proxies"
2. SearchFragment calls `viewModel.fetchProxies()`
3. ProxyViewModel updates `_proxies` LiveData to Loading state
4. Repository fetches from all sources in parallel using `async`
5. Results are filtered and deduplicated
6. ViewModel updates `_proxies` to Success/Error
7. ViewModel updates `_proxyList` for List fragment
8. SearchFragment observes and shows result

### List Flow
1. ListFragment observes `viewModel.proxyList`
2. When data changes, RecyclerView is updated via adapter
3. User can copy individual or all proxies

### Checker Flow
1. User pastes proxies in CheckerFragment
2. ProxyChecker.parseProxyList() creates Proxy objects
3. CheckerViewModel.startChecking() launches coroutine
4. Each proxy is checked via `ProxyChecker.checkProxy()`
5. Results are posted to `_checkedProxies` LiveData
6. Progress is updated after each check
7. CheckerFragment observes and updates UI

## API Integration

### Geonode API
- URL: `https://proxylist.geonode.com/api/proxy-list`
- Returns JSON with array of proxies
- Fields: ip, port, country, protocols, anonymityLevel

### ProxyScan API
- URL: `https://www.proxyscan.io/api/proxy`
- Returns JSON array of proxies
- Fields: Ip, Port, Type, Location

### GitHub Raw Lists
- URLs: `https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/{http,socks4,socks5}.txt`
- Returns plain text, one proxy per line (ip:port format)
- Parsed line by line

## Performance Optimizations

1. **Parallel API Calls**: All sources fetched simultaneously using coroutines `async`
2. **DiffUtil**: Efficient RecyclerView updates
3. **ViewBinding**: Type-safe view access, faster than findViewById
4. **Singleton Repository**: Reuses Retrofit instances
5. **Connection Pooling**: OkHttp automatically manages connections

## Error Handling

- Network errors caught in Repository, wrapped in Resource.Error
- UI displays error messages via Toast
- Proxy check failures set `isValid = false` without crashing
- Empty results handled gracefully with empty state messages

## Testing Considerations

To test the app:
1. Ensure device has internet connection
2. Use Search tab to fetch proxies
3. Verify results appear in List tab
4. Copy some proxies from List
5. Paste in Checker tab
6. Verify checking works and shows valid/invalid status

Note: Some proxies may be down, which is expected.

## Future Enhancements

Possible improvements:
- Save proxies to local database (Room)
- Export proxies to file
- Import proxies from file
- More proxy sources
- Custom test URL for checker
- Proxy authentication support
- Dark theme
- Settings screen
