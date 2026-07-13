# Jiradar - Analytics & Visualization Workspace

![JiRadar Logo](front-end-angular/public/jiradar_logo.svg)

Jiradar is a full-stack platform designed to extract, process, and visualize software delivery lifecycle data from issue tracking ecosystems like Jira. By evaluating issue transitions and raw
changelog streams, the system provides real-time developer productivity engineering analytics, including flow predictability metrics, cycle times, review behavior metrics, and work-in-progress
indexes.

The repository is managed as a unified multi-module structure comprising a Java-based Spring Boot backend engine, an Angular frontend client application, and global automated verification workflows.

---

## Architecture Overview

The system is split into two isolated, micro-architectured layers that collaborate via a structured REST API layer:

```text
.
├── .husky/                  # Workspace validation lifecycle automation triggers
├── back-end/                # Spring Boot REST engine using Clean Architecture
└── front-end/               # Angular client application utilizing feature modules
```

A global validation mechanism is enforced on code pushes via Git hooks. The pre-push hook acts as a quality gate ensuring that both modules pass strict criteria before code can be published to remote
servers:

1. Compiles the Spring Boot backend environment and runs integration testing cycles.
2. Triggers the frontend unit testing coverage engine to ensure strict thresholds are met.

---

## Global Environment Prerequisites

To set up, build, or develop within this workspace, ensure your workstation satisfies the following version constraints:

* **Java Development Kit:** Version 25
* **Build Automation Tool:** Apache Maven 3.x+
* **Runtime Environment:** Node.js v22.x or higher
* **Frontend Node Package Manager:** npm v11.17.0

---

## Module Setup and Execution

### Backend Engine Deployment

The backend module handles calculation logic, query optimization partitions, and third-party tracking vendor adapters.

1. Navigate to the backend directory:
   ```bash
   cd back-end
   ```
2. Build the executable packaging and run tests locally via Maven:
   ```bash
   mvn clean verify
   ```
3. Run the Spring Boot application locally:
   ```bash
   mvn spring-boot:run
   ```

### Frontend Client Deployment

The frontend provides user dashboards, telemetry visualization feeds, and paginated event streaming trackers.

1. Navigate to the frontend directory:
   ```bash
   cd front-end
   ```
2. Install the target node modules and register client-side development tooling hooks:
   ```bash
   npm install
   ```
3. Start the local live-reload development server:
   ```bash
   npm run start
   ```
4. Access the presentation console by opening a browser tab navigated to: `http://localhost:4200/`

---

## Deep-Dive System Specifications

### 1. Backend Engine (Java 25 & Spring Boot 4.1.0)

The server is structured following Clean Architecture domains to isolate infrastructural adjustments from core metrics logic:

* **Presentation Layer (`controller`)**: Exposes versioned REST components under `/api/v1/tracker/{issueTracker}/...`. It handles structural payload casing transparently using an automatic servlet
  interception filter (`OncePerRequestFilter`) to unify naming conversions between frontend interfaces and Java models.
* **Domain Layer (`core`)**: Formulates rich calculation models such as `UserMetricCalculationService` to compute specific metrics:
    * **Average Cycle Time:** The duration separating an issue's initialization in development from its transition into a finalized status state.
    * **Average Review Time:** Elapsed duration spent strictly inside peer review workflow labels.
    * **Delivery Success Rate:** The ratio of issues started that successfully transitioned to finished within the selected timeframe.
    * **WIP Parallel Index:** A daily work-in-progress assessment factor measuring concurrent open tickets allocated to an active engineer profile.
* **Infrastructure Layer (`infrastructure`)**: Standardizes vendor connection adapters like `JiraIssueTrackerAdapter` and routes performance operations through an in-memory `CaffeineCacheManager`.
  Telemetry tracking caches use short 5-minute boundaries grouped into monthly calendars (`YearMonth`) to balance API query roundtrips with up-to-date data changes.

### 2. Frontend Client (Angular 22 & Tailwind CSS 4)

The application handles visual rendering dynamically through reactive components, standalone directives, and isolated layout structures:

* **Core Core Framework Elements (`core`)**: Shared utilities like `apiInterceptor` attach contextual base paths and normalize payloads. Shared model graphs dictate structures for Issues, Changelogs,
  Pages, and User objects.
* **Dashboard Engine (`features/dashboard`)**: Orchestrates unified layout widgets allowing users to select Project Keys, restrict Date Ranges (under a maximum 1-year boundary constraint), and
  alternate time grouping granularties (Daily, Weekly, Monthly, Yearly).
* **Metrics Visualization (`features/metrics`)**: Harnesses Chart.js implementations and specialized wrappers (`ng2-charts`) to render multi-dimensional performance representations for timeline
  indicators and workload allocation charts.
* **Audit Trails List (`features/history`)**: Implements asynchronous paginated views, processing metadata headers mapped directly from server pagination bounds (Page Number, Size, Total Elements, and
  Total Pages).

---

## Quality Control & Test Coverage Enforcement

Both layers run strict test-suite validations to control software standards before deployment.

### Backend Testing Matrix

Backend coverage profiles are generated using the `jacoco-maven-plugin` configuration sequence. This tracks execution data paths directly during packaging loops to verify code reliability.

### Frontend Testing Matrix

The client framework delegates automated testing workflows to Vitest executing inside virtualized `jsdom` document spaces. It relies on a structural statement filter constraint enforcing an absolute
80% coverage requirement across functional code regions, explicitly omitting structural types or configuration modules:

| Target Category | Code Coverage Threshold Constraint |
|:----------------|:-----------------------------------|
| **Statements**  | Minimum 80%                        |
| **Lines**       | Minimum 80%                        |
| **Branches**    | Minimum 80%                        |
| **Functions**   | Minimum 80%                        |

To launch automated unit test runs or extract file coverage documents manually within the client environment, deploy the designated commands:

```bash
# Execute standalone validation suites
npm run test

# Compile coverage telemetry trees to disk
npm run test:coverage
```

The compiled metrics are stored locally inside the `./front-end/coverage/front-end` tree as interactive structural files.