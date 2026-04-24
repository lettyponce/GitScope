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

### Next Steps

- Add navigation and repository details screen
- Introduce ViewModels with state management
- Integrate GitHub API
- Add filtering/sorting features
- Improve UX with loading/error/empty states
- Add unit and UI tests