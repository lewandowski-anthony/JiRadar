# Jiradar - Frontend Client Application

The Jiradar frontend is a reactive, highly modular browser interface designed to consume Jira workspace delivery telemetry streams and render analytical metrics dashboard profiles. The client engine relies on React 19, Tailwind CSS 4, and Vite to deliver high-performance visual representations of engineering cycle flows.

---

## Technical Stack

* **Core View Engine:** React 19 (Functional Components with Hooks architecture)
* **Build System & Local Engine:** Vite 6+
* **Styling Framework:** Tailwind CSS 4
* **Metrics & Charts Engine:** Chart.js wrapped via `react-chartjs-2`
* **Test Architecture:** Vitest running within virtualized `jsdom` environments
* **Package Manager:** npm v10.x+
* **Runtime Target:** Node.js v20.x+

---

## Directory Architecture

The module utilizes feature-driven domain isolation to ensure scaling predictability:

```text
front-end/
├── docker/                  # Containment scripts and configuration entrypoints
├── public/                  # Static assets and vectors (logos, icons)
├── src/
│   ├── core/                # Core architecture elements (API clients, Contexts, Providers)
│   │   ├── api/             # Base Axios instance and centralized inter-service routes
│   │   ├── context/         # AuthContext and state boundary containers
│   │   ├── i18n/            # LocaleProvider and multilingual dictionaries
│   │   └── utils/           # Shared snake_case formatting filters and helper utilities
│   ├── features/            # Feature-centric application domain logic
│   │   ├── dashboard/       # Query filters, form validations, and time periodic grouping
│   │   ├── devcharts/       # ChartJS component definitions for rendering metrics
│   │   └── history/         # Paginated historical tracking ledger data views
│   ├── shared/              # Presentation components (buttons, loaders, navigation templates)
│   ├── App.tsx              # Application layout root configuration
│   └── main.tsx             # Standalone client runtime bootstrapping hook
├── Dockerfile               # Multi-stage production container manifest
└── vite.config.ts           # Bundler config, aliases, and Vitest configurations
```

---

## Setup & Running Locally

### Prerequisites
Ensure your global environment satisfies node prerequisites (`node >= 20.x` and `npm >= 10.x`).

### 1. Install Dependencies
Initialize package allocations and link client-side execution triggers:
```bash
npm install
```

### 2. Configure Environment Variables
Create a local tracking environment configuration block or export properties directly to your terminal environment:
```bash
# Variable targeting the API context pathway
VITE_API_BASE_URL=http://localhost:8080
```

### 3. Launch Development Server
Boot up the fast-refresh active bundle loop:
```bash
npm run dev
```
The console will expose the presentation layer location link (typically defaults to `http://localhost:5173/`).

### 4. Build for Production
To optimize, compress, and dump deployment assets into the `/dist` directory structure, run:
```bash
npm run build
```

---

## Client Test Suite Framework

The application maintains a strict quality gate managed through Vitest to assert logic integrity within data regions.

### Quality Matrix Thresholds
A rigid 80% baseline requirement boundary is strictly mapped across the feature space:

| Indicator Type | Coverage Target Threshold Constraint |
|:---|:---|
| **Statements** | Minimum 80% |
| **Lines** | Minimum 80% |
| **Branches** | Minimum 80% |
| **Functions** | Minimum 80% |

### Execution Directives

```bash
# Launch interactive assertion test-suite checks
npm run test

# Run validation checks and output detailed coverage tables
npm run test:coverage
```
Coverage records are stored inside `front-end/coverage` as searchable structural formats.

---

## Docker Container Deployment

The frontend application uses a decoupled multi-stage container deployment cycle to separate asset generation from high-performance delivery vectors.

### Production Execution Loop

1. **Compilation Phase:** Spawns a lightweight `node:alpine` base context to fetch clean package dependencies and build production static files via `npm run build`.
2. **Distribution Phase:** Moves the compiled visual distribution directory (`dist/`) into an isolated `nginx:alpine` engine block.
3. **Environment Injection (`docker/entrypoint.sh`):** At image boot-up, a shell controller converts standard external runtime parameters (such as `BACKEND_URL`) into static client injection parameters (`config.js`) to provide dynamic endpoint target routing without requiring rebuilding workflows.

### Building and Running the Container Standalone

```bash
# Build the image from the workspace context
docker build -t jiradar-front .

# Run the container mapping web traffic boundaries
docker run -d -p 80:80 -e BACKEND_URL=http://localhost:8080 jiradar-front
```
```