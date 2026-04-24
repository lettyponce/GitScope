# GitScope

## Development Approach (Step-by-step Evolution)

This project was built using an incremental approach, focusing on delivering a working user experience early and progressively integrating data, architecture, and quality improvements.

### Step 1 — Foundation

- Project setup with Jetpack Compose, Material 3, Navigation and Hilt
- App successfully builds and runs

#### 🤖 AI Usage
- Assisted in resolving Java/Gradle compatibility issues
- Suggested stable library versions and dependency setup

---

### Step 2 — UI with Mock Data

- Create package structure: `presentation`
- Initial NavHost configuration
- Implemented `RepositoriesScreen` with a 2-column grid
- Created reusable `RepositoryItem` composable
- Added Top App Bar: **"Github Repositories"**
- Displayed repository information
- Introduced mock data (`RepoPreview`) to simulate real content
- Added Coil for image loading

#### 🤖 AI Usage
- Generated mock data structure for repositories
- Assisted in creating initial Compose UI components
- Suggested preview configurations for faster UI iteration

---

### Step 3 — Navigation & Details Screen

- Implemented navigation using Navigation Compose
- Added `RepositoryDetailsScreen`
- Enabled navigation from repository list to details screen
- Passed repository identifier through navigation arguments
- Retrieved selected repository from mock data source
- Added ellipsized title in Top App Bar

#### 🤖 AI Usage
- Assisted in structuring navigation routes and arguments
- Helped define the initial structure of the details screen
- Suggested preview configurations for faster UI iteration

---

### Step 4 — ViewModel & UI State with Mock Data

- Added domain model `Repository`
- Moved mock repositories to a mock data source
- Introduced `RepositoriesViewModel`
- Introduced `RepositoryDetailsViewModel`
- Added UI state classes for list and details screens
- Updated screens to render state instead of owning mock data directly
- Added Route composables to connect ViewModels with UI screens

#### 🤖 AI Usage
- Assisted in defining the initial ViewModel structure
- Update preview configurations after refactoring

---

### Step 5 — Initial ViewModel Unit Tests

- Added unit tests for `RepositoriesViewModel`
- Added unit tests for `RepositoryDetailsViewModel`
- Created `MainDispatcherRule` to replace `Dispatchers.Main` during JVM tests

#### 🤖 AI Usage
- Suggested using `MainDispatcherRule` for coroutine-based ViewModel tests

---

### Next Steps

- Integrate GitHub API
- Add filtering/sorting features
- Improve UX with loading/error/empty states
- Add unit and UI tests