# Jiradar - Frontend Client

This repository houses the frontend application for Jiradar, an analytics and visualization dashboard built to track Jira metrics, issue history, and team performance[cite: 1]. The application is built using Angular and leverages Tailwind CSS for styling along with Chart.js for data visualization.

---

## Technical Specifications and Prerequisites

The application runs on a modern frontend stack configured for strict code quality, automated build optimizations, and high test coverage.

* Framework: Angular v22.0.0
* Data Visualization: chart.js v4.5.1 paired with ng2-charts v10.0.0
* Utilities: date-fns v4.4.0 and rxjs ~7.8.0
* Build System: @angular/build v22.0.5 and @angular/cli v22.0.5
* Compilation: TypeScript ~6.0.2
* Styling Engine: Tailwind CSS v4.1.12 processed through PostCSS v8.5.3 and @tailwindcss/postcss v4.1.12
* Quality Assurance: ESLint v10.3.0, TypeScript ESLint v8.60.1, and Angular ESLint v22.0.0
* Automation: Husky v8.0.3 manages automated pre-commit lifecycle actions
* Package Manager: npm v11.17.0
* Testing Framework: Vitest with JSDOM and V8 Coverage

Ensure you have Node.js installed matching the recommended npm environment requirements.

---

## Complete CLI Command Reference

The package.json file exposes a structured set of scripts for standard lifecycle tasks:

| Command | Action | Description |
| :--- | :--- | :--- |
| npm run ng | ng | Direct entry point to invoke the Angular CLI executable. |
| npm run start | ng serve | Starts the local dev server using @angular/build:dev-server with live-reloading. |
| npm run build | ng build | Compiles production assets with strict performance budgets and cache busting. |
| npm run watch | ng build --watch --configuration development | Continually recompiles assets in development mode upon file modifications. |
| npm run test | ng test | Runs the full Vitest test execution cycle. |
| npm run test:coverage | ng test --coverage --watch=false | Executes a single-run test pass that dumps a coverage report to disk. |
| npm run lint | ng lint | Evaluates all TypeScript and HTML source files against defined style lint constraints. |
| npm run prepare | husky install | Registers project-specific git hooks locally during the installation process. |

---

## Installation and Deployment

### 1. Installation
Clone the repository and install the project dependencies:
```bash
npm install
```

### 2. Development Server
Run the local development server:
```bash
npm run start
```
The application will be accessible at http://localhost:4200/. The application automatically reloads if you change any of the source files.

### 3. Production Build
To build the project for production, execute:
```bash
npm run build
```
The build assets are saved in the output directory specified by the Angular configuration, with optimized bundle budgets and cache-busting hashing enabled.

---

## Testing and Quality Control

### Unit Tests
Execute the test suite using Vitest:
```bash
npm run test
```

### Code Coverage
Generate a detailed test coverage report:
```bash
npm run test:coverage
```
The test suite enforces an 80% coverage threshold for statements, lines, branches, and functions across the core application logic. Reports are exported in both terminal text and HTML format within the ./coverage/front-end directory[cite: 1].

### Linting and Formatting
To run static code analysis using ESLint and the Angular ESLint builder, execute:
```bash
npm run lint
```
Code formatting rules are managed via Prettier configuration definitions[cite: 1].

---

## Architecture and Project Structure

The codebase adheres to a modular, feature-driven structure under the src/app directory[cite: 1]:

```text
src/
├── main.ts                     # Application bootstrap entry point[cite: 1]
├── styles.css                  # Global styles configuration[cite: 1]
├── assets/                     # Managed static public assets[cite: 1]
└── app/
    ├── core/                   # Singleton services, interceptors, and helpers[cite: 1]
    │   ├── constants/          # Static API parameter mappings[cite: 1]
    │   ├── interceptors/       # HTTP Interceptors for API requests[cite: 1]
    │   ├── models/             # Shared data models (User, Issue, Changelog)[cite: 1]
    │   └── utils/              # Base HTTP utilities and text casing mappers[cite: 1]
    └── features/               # Domain-specific modules[cite: 1]
        ├── dashboard/          # Centralized tracking landing view[cite: 1]
        ├── history/            # Audit logs and issue progression listings[cite: 1]
        └── metrics/            # Periodic charts and analytical performance displays[cite: 1]
```

### Metrics Module (src/app/features/metrics)
Handles data tracking and plotting operations[cite: 1].
* components/metrics-display.component.ts: Hosts the top-level container structure for visual dashboards[cite: 1].
* components/periodic-charts.component.ts: Feeds historical timeline datasets directly into Chart.js elements[cite: 1].
* services/metrics.service.ts: Pulls back analytics telemetry from external endpoints[cite: 1].
* models/: Models data shapes including userMetrics.model.ts and issueTypeRate.model.ts[cite: 1].

### History Module (src/app/features/history)
Tracks change verification logs and issue events over time[cite: 1].
* components/history-list.component.ts: Structures a detailed log view of audit histories and ticket modifications[cite: 1].
* services/history.service.ts: Performs querying logic to retrieve changelog streams[cite: 1].
* models/history.model.ts: Dictates domain properties for items displayed in historical data feeds[cite: 1].

### Core Architecture Infrastructure (src/app/core)
Maintains non-feature-specific structural elements shared application-wide[cite: 1]:
* interceptors/api.interceptor.ts: Attaches required parameters and handles errors on outgoing HTTP traffic[cite: 1].
* utils/case-mapper.ts: Converts incoming API payloads automatically between snake_case and camelCase formats[cite: 1].
* utils/jiradar-http.util.ts: Standardizes structural wrappers for HTTP network operations[cite: 1].
* constants/api-params.constant.ts: Centralizes structural parameter declarations for network transactions[cite: 1].

---

## Workspace and Build Architecture

The workspace configuration detailed in angular.json sets explicit limits and pathways for the build pipeline:

### Bundle Size Budgets
To keep production initial load speeds fast, the framework enforces boundaries during the ng build phase:
* Initial Bundles: Generates a warning if the aggregate size crosses 500kB, and throws a hard build failure at 1MB.
* Component Styles: Generates a warning if an individual component style bundle reaches 4kB, failing outright at 8kB.

### Optimization Matrix
The behavior of the compiler adapts strictly according to the active configuration target:
* Production Mode: Dispatches full minification, dead code elimination, and appends unique tokens to filenames via outputHashing: "all".
* Development Mode: Sets optimization: false, leaves third-party license extractions disabled (extractLicenses: false), and enables source maps (sourceMap: true) to ease debugging. It also triggers a file replacement rule that overrides the production environment definition (src/environments/environment.ts) with development variables (src/environments/environment.development.ts).

---

## Test Automation and Coverage Isolation

The unit testing setup uses @angular/build:unit-test alongside a Vitest engine configured to check rigorous validation rules:

### Enforcement Thresholds
The project configuration requires a minimum score across all assessed categories:
* Statements: 80%
* Lines: 80%
* Branches: 80%
* Functions: 80%

### Inclusions and Exclusions
To ensure metrics accurately represent functional logic, specific file signatures are excluded from the coverage denominator:

```text
Included Focus Areas:
└── src/app/**/*.ts                 # All functional TypeScript implementation logic

Explicit Exclusions:
├── src/app/**/*.spec.ts            # Unit test files themselves
├── src/app/**/*.model.ts           # Pure interface definitions and structural types
├── src/app/**/*.routes.ts          # Angular routing path maps
└── src/app/app.config.ts           # Bootstrapping providers and global config definitions
```

---

## Configuration Notes

* Environment Swapping: The build system swaps src/environments/environment.ts with src/environments/environment.development.ts automatically when running under development configurations.
* Internationalization: Static translation and localization reference templates are maintained within ./src/locale/messages.xlf[cite: 1].
