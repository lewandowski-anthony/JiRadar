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

Jiradar is a full-stack platform designed to extract, process, and analyze project delivery metrics and activity loops from issue-tracking ecosystems like Jira. By evaluating issue transitions and raw
changelog streams, the system provides real-time developer productivity engineering analytics, including flow predictability metrics, cycle times, review behavior metrics, and work-in-progress
indexes.

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

A global validation mechanism is enforced on code pushes via Git hooks. The pre-push hook acts as a quality gate ensuring that both modules pass strict criteria before code can be published to remote
servers:

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

## Custom Metrics Configuration Guide

This guide explains how to configure and extend user performance metrics within the application using **Spring Expression Language (SpEL)**.

---

### 🚀 How It Works

The application dynamically evaluates formulas defined as environment variables against the `UserMetricCalculationService` context.

To prevent runtime failures, a **Fail-Fast validation mechanism** runs during the application bootstrap[cite: 2]. If any formula contains a syntax error or references a non-existent property/method,
the application will refuse to start and throw an explicit initialization error[cite: 2].

---

### 🛠️ Configuration Format

Custom metrics are loaded via the `issue-tracker.metrics.custom-metrics` configuration prefix[cite: 2]. You can inject them directly into your IntelliJ IDEA Run/Debug Configuration using the *
*Environment variables** field.

#### IntelliJ Environment Variables String

Copy and paste the following single-line string into your IntelliJ configuration to load **10 distinct custom metrics**:

```text
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_0_NAME=total-assigned;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_0_FORMULA=userIssues.size();
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_1_NAME=bugs-count;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_1_FORMULA=userIssues.?[type.name == 'Bug'].size();
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_2_NAME=stories-count;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_2_FORMULA=userIssues.?[type.name == 'Story'].size();
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_3_NAME=completion-rate;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_3_FORMULA=numberOfIssueStarted > 0 ? (numberOfIssueDone * 100.0 / numberOfIssueStarted) : 0.0;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_4_NAME=review-reopen-rate;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_4_FORMULA=numberOfReviewDone > 0 ? (numberOfReviewReopened * 100.0 / numberOfReviewDone) : 0.0;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_5_NAME=wip-alert;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_5_FORMULA=calculateParallelJiraInProgressRate() > 4.0;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_6_NAME=clean-delivery-rate;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_6_FORMULA=numberOfIssueDone > 0 ? ((numberOfIssueDone - numberOfReviewReopened) * 100.0 / numberOfIssueDone) : 0.0;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_7_NAME=bugs-done-count;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_7_FORMULA=userIssues.?[type.name == 'Bug' && status.name == 'Done'].size();
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_8_NAME=has-done-issues;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_8_FORMULA=numberOfIssueDone > 0;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_9_NAME=review-ratio;
ISSUE_TRACKER_METRICS_CUSTOM_METRICS_9_FORMULA=numberOfIssueDone > 0 ? (numberOfReviewDone * 1.0 / numberOfIssueDone) : 0.0
```

### SpEl Writing skills

#### 1. Global Variables and Pre-Computed Counters

These fields return direct numerical values or core objects. You can combine them using mathematical operators or ternary conditions.

| SpEL Field / Property    | Java Type   | Description                                                | Usage Example              |
|:-------------------------|:------------|:-----------------------------------------------------------|:---------------------------|
| `startedCount`           | `long`      | Number of issues started during the selected timeframe.    | `startedCount`             |
| `numberOfIssueStarted`   | `long`      | Shortcut getter for the number of started issues.          | `numberOfIssueStarted > 0` |
| `doneCount`              | `long`      | Number of issues moved to 'Done' during the timeframe.     | `doneCount`                |
| `numberOfIssueDone`      | `long`      | Shortcut getter for the number of completed issues.        | `numberOfIssueDone`        |
| `reopenedCount`          | `long`      | Total number of issue reopens following a code review.     | `reopenedCount`            |
| `numberOfReviewReopened` | `long`      | Shortcut getter for the number of reopened reviews.        | `numberOfReviewReopened`   |
| `startedAndDoneCount`    | `long`      | Issues both started AND completed within the same period.  | `startedAndDoneCount`      |
| `numberOfReviewDone`     | `long`      | Total number of code reviews completed by the user.        | `numberOfReviewDone`       |
| `range`                  | `DateRange` | The object representing the selected evaluation timeframe. | `range.from()`             |

---

#### 2. Advanced Business Calculation Methods

These methods execute internal algorithms and can be invoked directly using parentheses `()`.

| SpEL Method                              | Return Type           | Description                                             | Usage Example                                |
|:-----------------------------------------|:----------------------|:--------------------------------------------------------|:---------------------------------------------|
| `calculateAverageCycleTime()`            | `Duration`            | Average time taken to resolve an issue (Cycle Time).    | `calculateAverageCycleTime().toDays()`       |
| `calculateAverageReviewTime()`           | `Duration`            | Average time spent by the developer reviewing an issue. | `calculateAverageReviewTime().toHours()`     |
| `calculateTeamReviewParticipationRate()` | `double`              | User participation rate in team reviews (0.0 to 100.0). | `calculateTeamReviewParticipationRate()`     |
| `calculateDeliverySuccessRate()`         | `double`              | Delivery success rate (started vs completed issues).    | `calculateDeliverySuccessRate() < 80.0`      |
| `calculatePingPongReviewRate()`          | `double`              | Code review back-and-forth rate (reopened vs done).     | `calculatePingPongReviewRate()`              |
| `calculateParallelJiraInProgressRate()`  | `double`              | Average number of parallel active tasks (WIP per day).  | `calculateParallelJiraInProgressRate()`      |
| `getDoneIssuesTypeDistribution()`        | `Map<String, Double>` | Percentage distribution of completed issue types.       | `getDoneIssuesTypeDistribution().get('Bug')` |

---

#### 3. Raw Data Structures (Lists for Deep Filtering)

These collections allow you to leverage SpEL's projection (`.![...]`) and selection (`.?[...]`) mechanisms to build highly customized filters.

| SpEL List             | Element Type     | Collection Content                                               |
|:----------------------|:-----------------|:-----------------------------------------------------------------|
| `userIssues`          | `List<Issue>`    | All issues currently or previously assigned to the active user.  |
| `devReviewDurations`  | `List<Duration>` | List of all review durations performed specifically by the user. |
| `teamReviewDurations` | `List<Duration>` | List of all review durations performed across the entire team.   |

##### 💡 Nested Properties Accessible Inside `Issue` Filters

When filtering the `userIssues` list (e.g., `userIssues.?[...]`), you can query the following fields from the underlying `Issue` domain model:

* `key` (`String`): The unique key of the issue (e.g., `PROJ-123`)
* `type.name` (`String`): The type designation of the issue (e.g., `Bug`, `Story`, `Task`)
* `status.name` (`String`): The current state of the issue (e.g., `To Do`, `In Progress`, `Done`)

```

---

### 📊 Detailed Metrics Breakdown

| Metric Name               | Description                                                         | SpEL Formula Template                                                                                      | Expected Output Type |
|:--------------------------|:--------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------|:---------------------|
| **`total-assigned`**      | Total volume of tickets assigned to the user.                       | `userIssues.size()`                                                                                        | `Integer`            |
| **`bugs-count`**          | Total number of assigned bugs across all statuses.                  | `userIssues.?[type.name == 'Bug'].size()`                                                                  | `Integer`            |
| **`stories-count`**       | Total number of assigned user stories.                              | `userIssues.?[type.name == 'Story'].size()`                                                                | `Integer`            |
| **`completion-rate`**     | Percentage of completed issues relative to started ones.            | `numberOfIssueStarted > 0 ? (numberOfIssueDone * 100.0 / numberOfIssueStarted) : 0.0`                      | `Double`             |
| **`review-reopen-rate`**  | Reopen rate of code reviews (stability/quality indicator).          | `numberOfReviewDone > 0 ? (numberOfReviewReopened * 100.0 / numberOfReviewDone) : 0.0`                     | `Double`             |
| **`wip-alert`**           | Boolean flag that turns `true` if parallel active tasks exceed 4.0. | `calculateParallelJiraInProgressRate() > 4.0`                                                              | `Boolean`            |
| **`clean-delivery-rate`** | Percentage of closed issues delivered without ever being reopened.  | `numberOfIssueDone > 0 ? ((numberOfIssueDone - numberOfReviewReopened) * 100.0 / numberOfIssueDone) : 0.0` | `Double`             |
| **`bugs-done-count`**     | Specific number of Bugs transitioned to the 'Done' status.          | `userIssues.?[type.name == 'Bug' && status.name == 'Done'].size()`                                         | `Integer`            |
| **`has-done-issues`**     | Simple boolean flag to check if the user closed any issue.          | `numberOfIssueDone > 0`                                                                                    | `Boolean`            |
| **`review-ratio`**        | Ratio of performed reviews compared to completed tickets.           | `numberOfIssueDone > 0 ? (numberOfReviewDone * 1.0 / numberOfIssueDone) : 0.0`                             | `Double`             |

---

### 🔒 Security & Performance Features

#### 1. Read-Only Sandbox

The custom evaluation engine leverages SpEL's `SimpleEvaluationContext` configured for read-only data binding[cite: 2]. This strictly prevents malicious formulas from modifying application states,
accessing unauthorized Java classes, or manipulating system resources.

#### 2. Pre-Compiled Expressions

Formulas are parsed and compiled into memory **once** during the initialization phase or at the root of a query[cite: 2]. When generating periodic historical datasets (e.g., weekly or monthly
metrics), the pre-compiled expressions are reused over the time-granularity intervals, eliminating high CPU overhead[cite: 2].

#### 3. Graceful Runtime Error Handling

If a formula throws an unexpected exception at runtime (such as an unhandled division by zero on a specific sub-range), the application isolates the failure[cite: 2]. Instead of breaking the entire
API response, it catches the error locally and returns a structured error message within the response payload[cite: 2]:

```json
{
  "name": "faulty-metric",
  "value": "ERROR: SpelEvaluationException: ..."
}
```

---

## Caching Strategy & Configuration

The application uses a pluggable caching infrastructure managed by `CacheProvider` implementations. This allows swapping telemetry tracking and query-optimization caches between an internal in-memory
solution and a distributed external cluster.

### Cache Providers

The system switches providers dynamically via environment variables or configuration setups:

* **Caffeine (In-Memory):** The default local strategy (`cache.provider=caffeine`), using high-performance local caches with short default lifecycles.
* **Redis/Valkey (Distributed):** An externalized distributed caching option (`cache.provider=redis`), routing JSON-serialized payloads through a dedicated key-value infrastructure layer.

| Environment Variable Overrides | Default value | For mode |
|:-------------------------------|:--------------|:---------|
| `CACHE_PROVIDER`               | `caffeine`    | /        |
| `SPRING_DATA_REDIS_HOST`       | `localhost`   | redis    |
| `SPRING_DATA_REDIS_PORT`       | `6379`        | redis    |

### System Specifications & Tuning Properties

Custom retention times (TTL) and maximum entry bounds can be overwritten via `application.yaml` configurations or explicit environment injects:

| Cache Context Key    | Default Fallback TTL | Default Max Size | Environment Variable Overrides                           |
|:---------------------|:---------------------|:-----------------|:---------------------------------------------------------|
| `JIRA_METRICS_CACHE` | 15 Minutes           | 1000             | `JIRA_METRICS_CACHE_TTL` / `JIRA_METRICS_CACHE_MAX_SIZE` |
| `JIRA_USER_CACHE`    | 2 Hours              | 200              | `JIRA_USER_CACHE_TTL` / `JIRA_USER_CACHE_MAX_SIZE`       |

### Redis Deployment Matrix

To run the application environment utilizing the decoupled distributed caching architecture, launch the workspace using the extended compose manifest:

```bash
docker compose -f docker/docker-compose-redis.yaml up --build
```

This bundle provisions a Valkey 8.0 core service instance mapping to port `6379`, attaches a RedisInsight telemetry dashboard console mapping to port `8001`, and reconfigures the backend layer
variables smoothly.

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

* **Build Phase:** Extracts compiled archive artifacts (`target/jiradar-back-*.jar`) into standard operational layered segments (`lib`, standalone application dependencies, class resources) using
  Spring Boot's internal tool index properties.
* **Runtime Environment:** Runs on an isolated, non-root system configuration context (`spring:spring` safe user space) using an Eclipse Temurin Java 25 Alpine foundation. This layer loads raw
  dependencies externally via the decoupled layer manager, optimizing system caches.

#### 2. Frontend App UI Layer (`front-end/Dockerfile`)

The client UI workspace builds on a split Node environment mapping directly into high-availability distribution endpoints:

* **Compilation Phase:** Spins up a Node Alpine framework layer to install pristine project dependencies and execute code compilation blocks (`npm run build`).
* **Distribution Phase:** Moves built visual bundles (`dist/`) directly into a high-performance Nginx web server layout container. An injection runtime hook (`docker/entrypoint.sh`) dynamically builds
  configuration property matrices (`config.js`) from target environment variables (`JIRADAR_BACKEND_URL`), keeping compiled code separate from backend locations.

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
      - JIRA_URL=[https://company.atlassian.net](https://company.atlassian.net)
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

* **Presentation Layer (`controller`)**: Exposes versioned REST components under `/api/v1/tracker/{issueTracker}/...`. It handles structural payload casing transparently using an automatic servlet
  interception filter (`OncePerRequestFilter`) to unify naming conversions between frontend interfaces and Java models.
* **Domain Layer (`core`)**: Formulates rich calculation models such as `UserMetricCalculationService` to compute specific metrics:
    * **Average Cycle Time:** The duration separating an issue's initialization in development from its transition into a finalized status state.
    * **Average Review Time:** Elapsed duration spent strictly inside peer review workflow labels.
    * **Delivery Success Rate:** The ratio of issues started that successfully transitioned to finished within the selected timeframe.
    * **WIP Parallel Index:** A daily work-in-progress assessment factor measuring concurrent open tickets allocated to an active engineer profile.
* **Infrastructure Layer (`infrastructure`)**: Standardizes vendor connection adapters like `JiraIssueTrackerAdapter` and abstracts performance optimization structures into decoupled caching modules
  via the `CacheProvider` interface contract.

### 2. Frontend Client (React 19 & Tailwind CSS 4)

The application handles visual rendering dynamically through reactive components, hooks, and context structures built on Vite:

* **Core Framework Elements (`core`)**: Context systems such as `AuthContext` manage credentials and sessions, while `LocaleProvider` implements multilingual switches. Shared utilities handle API
  clients (`apiClient`) and normalized naming conversions.
* **Dashboard Engine (`features/dashboard`)**: Orchestrates unified filter inputs (`FormDashboard`) and orchestrates layout widgets allowing users to query project data, restrict date fields, and
  configure timeline periodic groupings (Daily, Weekly, Monthly, Yearly).
* **Metrics Visualization (`features/devcharts`)**: Integrates Chart.js wrapped structures via `react-chartjs-2` to render multi-dimensional performance representations for timeline indicators and
  workload allocation charts.
* **Audit Trails List (`features/history`)**: Implements asynchronous paginated views, processing metadata headers mapped directly from server pagination boundaries (Page Number, Size, Total Elements,
  and Total Pages).

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

The compiled metrics are stored locally inside the `./front-end/coverage` tree as interactive structural files.
