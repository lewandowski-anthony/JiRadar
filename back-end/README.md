# Jiradar Backend

Jiradar Backend is a Spring Boot application designed to extract, process, and analyze project metrics from issue trackers like Jira. By integrating directly with tracker APIs and processing issue changelogs, the platform generates developer performance analytics, including cycle times, review rates, and work-in-progress statistics.
---

## Technical Architecture

The application is structured following clean architecture principles, separating the core domain logic from infrastructure frameworks and HTTP presentation layers.

```text
com.jiradar.jiradarback
├── controller          # REST Endpoints, DTOs, and Request Mapping Configuration
│   ├── config          # Argument resolvers, web filters, and MVC setups
│   ├── dto             # Data transfer objects for frontend communication
│   └── mapper          # MapStruct mappers for model-to-DTO conversion
├── core                # Core Business Logic and Domain Entities
│   └── model           # Rich domain models (Issue, User, ChangeLog, UserMetrics)
└── infrastructure      # External Frameworks and Database/API Adapters
    ├── cache           # Caffeine cache registration and retention setup
    ├── common          # Conditional bean configuration and module management
    └── jira            # Jira API clients, configuration properties, and adapters
```

### Core Components

* **UserMetricCalculationService**: The central algorithmic engine that evaluates issue histories within a requested `DateRange` to compile software delivery KPIs.
* **IssueTrackerService**: An interface layer defining data retrieval methods for user tracking information, implemented as a vendor adapter.
* **JiraIssueTrackerAdapter**: The infrastructure implementation that translates Jira-specific data schemas into pure domain objects.
* **ModuleDisablerPostProcessor**: A dynamic bean post-processor that reads environment configuration properties (`issue-tracker.<module-name>.config.enabled`) to remove disabled integration modules from the Spring application context on startup.

---

## Features & Analytics Generated

The backend computes engineering performance statistics over explicit time intervals:

| Metric | Calculation Logic |
| :--- | :--- |
| **Average Cycle Time** | The average elapsed duration between an issue's first `START_DEVELOPMENT` transition and its first `DONE` transition. |
| **Average Review Time** | The time spanning from a `REQUEST_REVIEW` log entry until the review action completes (`END_REVIEW`) by the target developer. |
| **Delivery Success Rate** | The percentage of tickets marked as `DONE` relative to total tickets `STARTED` inside the time frame. |
| **Ping-Pong Review Rate** | Represents code volatility by calculating the frequency at which peer reviews are reopened against total completed issues. |
| **Parallel Issues In Progress** | Daily Work-In-Progress (WIP) index assessing concurrent open tickets active on a developer's plate over the date range. |
| **Issue Type Distribution** | Percentage breakdown profiling the categories of work (e.g., Bug, Feature, Task) finished during the window. |

---

## REST API Specifications

The application exposes standard REST resources under `/api/v1/tracker/{issueTracker}/...`. The path variable `{issueTracker}` expects a registered provider name, such as `jira`.

### 1. Get Logged-In User Profile
* **URL:** `/api/v1/tracker/{issueTracker}/users/myself`
* **Method:** `GET`
* **Response Summary:** Profile metadata matching the authenticating API credentials.

### 2. Fetch Aggregated Performance Metrics
* **URL:** `/api/v1/tracker/{issueTracker}/users/myself/metrics`
* **Method:** `GET`
* **Query Parameters:**
    * `projectsKey` (Required): Comma-separated or multi-value list of project keys to analyze.
    * `startDate` (Optional): ISO local date format (`YYYY-MM-DD`). Defaults to 30 days ago.
    * `endDate` (Optional): ISO local date format (`YYYY-MM-DD`). Defaults to current date.
* **Constraints:** The date window between `startDate` and `endDate` cannot exceed 1 year.

### 3. Fetch Single Issue Detail
* **URL:** `/api/v1/tracker/{issueTracker}/issues/{issueKey}`
* **Method:** `GET`

---

## Configuration & Environment Variables

System parameters are managed within `application.properties` or `application.yml`. Core external integration properties map to the prefix `issue-tracker.jira.config`.

### Properties Reference

```properties
# Module Lifecycle Controls
issue-tracker.jira.config.enabled=true

# Remote Connection Details
issue-tracker.jira.config.url=[https://your-company.atlassian.net](https://your-company.atlassian.net)
issue-tracker.jira.config.user=automation-account@company.com
issue-tracker.jira.config.token=ATATT3xFf...

# Status Mapping Assignments (Matches workflow labels in issue tracker)
issue-tracker.jira.config.statuses.startDevelopment=In Progress,Selected for Development
issue-tracker.jira.config.statuses.requestReview=In Review,Peer Review
issue-tracker.jira.config.statuses.done=Done,Closed,Resolved
```

---

## Optimization and Performance Strategies

### Multi-Level Intelligent Caching
The application deploys an in-memory `CaffeineCacheManager` to eliminate repetitive API round-trips:
* **User Cache (`JIRA_USER_CACHE`)**: Persists profile configurations for up to 1 day.
* **Metrics Cache (`JIRA_METRICS_CACHE`)**: Configured with a 5-minute time-to-live (TTL) to store intermediate search results partitioned by projects and requested month boundaries.

### Smart API Query Partitioning
When querying for analytics ranges that span broad timeframes, the execution layer shifts from querying exact, narrow date windows to grouping fetch calls into complete calendar-month blocks (`YearMonth`). This maximizes caching efficiency for historic data chunks while keeping remote pagination payloads uniform.

### Automatic Parameter Interception
Incoming snake_case URL query strings are normalized to camelCase parameters transparently using a customized servlet filter wrapper (`OncePerRequestFilter`), allowing seamless compatibility between frontend naming strategies and Java object bindings.