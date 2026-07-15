# Jiradar - Analytics & Visualization Workspace
[![Java Version](https://img.shields.io/badge/Java-25-orange.svg?style=flat-square&logo=openjdk)](https://openjdk.org/projects/jdk/25/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.0-brightgreen.svg?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Node Target](https://img.shields.io/badge/Node.js-v20.x-blue.svg?style=flat-square&logo=nodedotjs)](https://nodejs.org/)
[![Vite Engine](https://img.shields.io/badge/Vite-6.x-purple.svg?style=flat-square&logo=vite)](https://vite.dev/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-v4-38bdf8.svg?style=flat-square&logo=tailwindcss)](https://tailwindcss.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](https://opensource.org/licenses/MIT)
[![CI/CD Pipeline](https://github.com/lewandowski-anthony/JiRadar/actions/workflows/build-jiradar.yml/badge.svg)](https://github.com/lewandowski-anthony/JiRadar/actions/workflows/build-jiradar.yml)
[![Release Version](https://img.shields.io/github/v/release/lewandowski-anthony/jiradar?style=flat-square&color=blue)](https://github.com/lewandowski-anthony/jiradar/releases)
![JiRadar Logo](front-end/public/jiradar_logo.svg)

Jiradar is a full-stack platform designed to extract, process, and analyze project delivery metrics and activity loops from issue-tracking ecosystems like Jira. By evaluating issue transitions and raw changelog streams, the system provides real-time developer productivity engineering analytics, including flow predictability metrics, cycle times, review behavior metrics, and work-in-progress indexes.

The repository is managed as a unified multi-module structure comprising a Java-based Spring Boot backend engine, a React frontend client application, and global automated verification workflows.

---

## Architecture Overview

The system is split into two isolated, micro-architectured layers that collaborate via a structured REST API layer:

```text
.
├── .husky/                  # Workspace validation lifecycle automation triggers
├── back-end/                # Spring Boot REST engine using Clean Architecture
└── front-end/               # React client application utilizing feature modules
```

A global validation mechanism is enforced on code pushes via Git hooks. The pre-push hook acts as a quality gate ensuring that both modules pass strict criteria before code can be published to remote servers:

1. Compiles the Spring Boot backend environment and runs integration testing cycles.
2. Triggers the frontend unit testing coverage engine to ensure strict thresholds are met.

---

## Global Environment Prerequisites

To set up, build, or develop within this workspace, ensure your workstation satisfies the following version constraints:

* **Java Development Kit:** Version 25
* **Build Automation Tool:** Apache Maven 3.x+
* **Runtime Environment:** Node.js v20.x or higher
* **Frontend Node Package Manager:** npm v10.x or higher

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
   npm run dev
   ```
4. Access the presentation console by opening a browser tab navigated to: `http://localhost:5173/` (or the port specified by Vite)

---

## Continuous Integration & Continuous Deployment (CI/CD)

The project leverages automated delivery practices distributed between localized Git quality locks, automated release workflows, and containerized deployment profiles.

### Release-Please Automation
Project releases, version bumps, and `CHANGELOG.md` compilation are fully automated using Google's **Release-Please** utility.
* **Conventional Commits:** The pipeline parses commit prefixes (e.g., `feat:` for minor updates, `fix:` for patches, and `BREAKING CHANGE:` for major updates).
* **Release Pull Request:** It dynamically compiles pending adjustments into a persistent Release PR branch containing updated manifests (`pom.xml`, `package.json`) and release note configurations.
* **Automated Publishing:** Merging the Release PR automatically triggers Git tags and registers structured release summaries directly on the remote hosting platform.

### Local Quality Gate (Husky Pre-Push Pipeline)
Before any commit hits the remote origin, compliance constraints run locally inside the engineer's workspace via Husky lifecycle triggers.

* **Backend Loop:** Automatically kicks off `mvn clean verify` to validate clean code compilation, check for code style regressions, run Cucumber behavioral matrices, and assert backend test coverage.
* **Frontend Loop:** Triggers automated testing setups via Vitest across feature bundles to guarantee no breaks slip through.

### Containerization Strategy (Docker Specifications)
Both frontend and backend modules feature multi-stage production-ready container profiles designed to scale smoothly under automated infrastructure workflows.

#### 1. Backend Service Layer (`back-end/Dockerfile`)
The backend relies on an optimized multi-stage build structure utilizing a layer extraction mechanism to achieve rapid startup overhead and lean image footprints:
* **Build Phase:** Extracts compiled archive artifacts (`target/jiradar-back-*.jar`) into standard operational layered segments (`lib`, standalone application dependencies, class resources) using Spring Boot's internal tool index properties.
* **Runtime Environment:** Runs on an isolated, non-root system configuration context (`spring:spring` safe user space) using an Eclipse Temurin Java 25 Alpine foundation. This layer loads raw dependencies externally via the decoupled layer manager, optimizing system caches.

#### 2. Frontend App UI Layer (`front-end/Dockerfile`)
The client UI workspace builds on a split Node environment mapping directly into high-availability distribution endpoints:
* **Compilation Phase:** Spins up a Node Alpine framework layer to install pristine project dependencies and execute code compilation blocks (`npm run build`).
* **Distribution Phase:** Moves built visual bundles (`dist/`) directly into a high-performance Nginx web server layout container. An injection runtime hook (`docker/entrypoint.sh`) dynamically builds configuration property matrices (`config.js`) from target environment variables (`JIRADAR_BACKEND_URL`), keeping compiled code separate from backend locations.

### Complete Ecosystem Automation (Docker Compose)
The global container stack can be fully orchestrated locally or through automated provisioning clusters via the workspace compose layer:

```yaml
version: '3.8'

services:
  back:
    build:
      context: ../back-end
      dockerfile: Dockerfile
    container_name: jiradar-back
    ports:
      - "8080:8080"
    environment:
      - JAVA_OPTS=-Xmx512m
      - JIRA_ENABLED=true
      - JIRA_URL=[https://decathlon.atlassian.net](https://decathlon.atlassian.net)
      - JIRA_STATUS_START_DEV=In Progress,En cours
      - JIRA_STATUS_REQUEST_REVIEW=In Review,Peer Review
      - JIRA_STATUS_DONE=Done,Terminé,Validé

  front:
    build:
      context: ../front-end
      dockerfile: Dockerfile
    container_name: jiradar-front
    ports:
      - "80:80"
    environment:
      - BACKEND_URL=http://localhost:8080
    depends_on:
      - back
```

---

## Deep-Dive System Specifications

### 1. Backend Engine (Java 25 & Spring Boot 4.1.0)

The server is structured following Clean Architecture domains to isolate infrastructural adjustments from core metrics logic:

* **Presentation Layer (`controller`)**: Exposes versioned REST components under `/api/v1/tracker/{issueTracker}/...`. It handles structural payload casing transparently using an automatic servlet interception filter (`OncePerRequestFilter`) to unify naming conversions between frontend interfaces and Java models.
* **Domain Layer (`core`)**: Formulates rich calculation models such as `UserMetricCalculationService` to compute specific metrics:
    * **Average Cycle Time:** The duration separating an issue's initialization in development from its transition into a finalized status state.
    * **Average Review Time:** Elapsed duration spent strictly inside peer review workflow labels.
    * **Delivery Success Rate:** The ratio of issues started that successfully transitioned to finished within the selected timeframe.
    * **WIP Parallel Index:** A daily work-in-progress assessment factor measuring concurrent open tickets allocated to an active engineer profile.
* **Infrastructure Layer (`infrastructure`)**: Standardizes vendor connection adapters like `JiraIssueTrackerAdapter` and routes performance operations through an in-memory `CaffeineCacheManager`. Telemetry tracking caches use short 5-minute boundaries grouped into monthly calendars (`YearMonth`) to balance API query roundtrips with up-to-date data changes.

### 2. Frontend Client (React 19 & Tailwind CSS 4)

The application handles visual rendering dynamically through reactive components, hooks, and context structures built on Vite:

* **Core Framework Elements (`core`)**: Context systems such as `AuthContext` manage credentials and sessions, while `LocaleProvider` implements multilingual switches. Shared utilities handle API clients (`apiClient`) and normalized naming conversions.
* **Dashboard Engine (`features/dashboard`)**: Orchestrates unified filter inputs (`FormDashboard`) and orchestrates layout widgets allowing users to query project data, restrict date fields, and configure timeline periodic groupings (Daily, Weekly, Monthly, Yearly).
* **Metrics Visualization (`features/devcharts`)**: Integrates Chart.js wrapped structures via `react-chartjs-2` to render multi-dimensional performance representations for timeline indicators and workload allocation charts.
* **Audit Trails List (`features/history`)**: Implements asynchronous paginated views, processing metadata headers mapped directly from server pagination boundaries (Page Number, Size, Total Elements, and Total Pages).

---

## Quality Control & Test Coverage Enforcement

Both layers run strict test-suite validations to control software standards before deployment.

### Backend Testing Matrix

Backend coverage profiles are generated using the `jacoco-maven-plugin` configuration sequence. This tracks execution data paths directly during packaging loops to verify code reliability.

### Frontend Testing Matrix

The client framework delegates automated testing workflows to Vitest executing inside virtualized `jsdom` document spaces. It relies on a structural statement filter constraint enforcing an absolute 80% coverage requirement across functional code regions, explicitly omitting structural types or configuration modules:

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

The compiled metrics are stored locally inside the `./front-end/coverage` tree as interactive structural files.