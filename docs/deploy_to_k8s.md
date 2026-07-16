# Generic Jiradar Kubernetes Templates

This repository contains the split, parameterized Kubernetes manifests for deploying Jiradar. To customize the deployment for your environment, replace the placeholder values (formatted as
`{{ VARIABLE_NAME }}`) before applying.

---

## Configuration Variables

Before deploying, prepare values for the following placeholders:

| Placeholder               | Description                                 | Example                              |
|:--------------------------|:--------------------------------------------|:-------------------------------------|
| `{{ NAMESPACE }}`         | The target Kubernetes namespace             | `smp-tools`                          |
| `{{ REGISTRY_SECRET }}`   | Secret name containing registry credentials | `regcred-gar`                        |
| `{{ JIRA_URL }}`          | Base URL of your Jira instance              | `https://your-company.atlassian.net` |
| `{{ HOSTNAME }}`          | Domain name exposing the application        | `jiradar.your-company.com`           |
| `{{ GATEWAY_NAME }}`      | Name of the Gateway API Gateway             | `internal`                           |
| `{{ GATEWAY_NAMESPACE }}` | Namespace of the Gateway API Gateway        | `gateway`                            |
| `{{ TIMEZONE }}`          | Timezone for the scaling schedule           | `Europe/Paris`                       |

---

## Split Manifest Files

### 1. Workloads (`01-workloads.yaml`)

This manifest handles the frontend and backend deployments.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: jiradar-back
  namespace: { { NAMESPACE } }
spec:
  replicas: 1
  selector:
    matchLabels:
      app: jiradar-back
  template:
    metadata:
      labels:
        app: jiradar-back
    spec:
      imagePullSecrets:
        - name: { { REGISTRY_SECRET } }
      containers:
        - name: back
          image: europe-west4-docker.pkg.dev/smp-tools-ursb/smp-tools/jiradar-back:v1.4.4
          imagePullPolicy: IfNotPresent
          ports:
            - containerPort: 8080
          env:
            - name: JAVA_OPTS
              value: "-Xmx512m"
            - name: JIRA_ENABLED
              value: "true"
            - name: JIRA_URL
              value: "{{ JIRA_URL }}"
            - name: JIRA_STATUS_START_DEV
              value: "In Progress,En cours"
            - name: JIRA_STATUS_REQUEST_REVIEW
              value: "In Review,Peer Review"
            - name: JIRA_STATUS_DONE
              value: "Done,Terminé,Validé"
          resources:
            limits:
              memory: 768Mi
              cpu: "1"
            requests:
              memory: 512Mi
              cpu: "250m"
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: jiradar-front
  namespace: { { NAMESPACE } }
spec:
  replicas: 1
  selector:
    matchLabels:
      app: jiradar-front
  template:
    metadata:
      labels:
        app: jiradar-front
    spec:
      imagePullSecrets:
        - name: { { REGISTRY_SECRET } }
      containers:
        - name: front
          image: europe-west4-docker.pkg.dev/smp-tools-ursb/smp-tools/jiradar-front:v1.4.4
          imagePullPolicy: IfNotPresent
          ports:
            - containerPort: 80
          env:
            - name: JIRADAR_BACKEND_URL
              value: "/jiradar/backend"
          resources:
            limits:
              memory: 128Mi
              cpu: 200m
            requests:
              memory: 64Mi
              cpu: 50m
```

### 2. Networking (`02-networking.yaml`)

This manifest exposes the pods internally within the cluster.

```yaml
apiVersion: v1
kind: Service
metadata:
  name: jiradar-back-service
  namespace: { { NAMESPACE } }
spec:
  type: ClusterIP
  ports:
    - port: 80
      targetPort: 8080
      protocol: TCP
  selector:
    app: jiradar-back
---
apiVersion: v1
kind: Service
metadata:
  name: jiradar-front-service
  namespace: { { NAMESPACE } }
spec:
  type: ClusterIP
  ports:
    - port: 80
      targetPort: 80
      protocol: TCP
  selector:
    app: jiradar-front
```

### 3. Routing (`03-routing.yaml`)

This manifest configures the ingress path routing through the Kubernetes Gateway API.

```yaml
apiVersion: gateway.networking.k8s.io/v1
kind: HTTPRoute
metadata:
  name: smptools-route
  namespace: { { NAMESPACE } }
spec:
  parentRefs:
    - group: gateway.networking.k8s.io
      kind: Gateway
      name: { { GATEWAY_NAME } }
      namespace: { { GATEWAY_NAMESPACE } }
  hostnames:
    - "{{ HOSTNAME }}"
  rules:
    # Path routing for backend endpoints
    - matches:
        - path:
            type: PathPrefix
            value: /jiradar/backend
      filters:
        - type: URLRewrite
          urlRewrite:
            path:
              type: ReplacePrefixMatch
              replacePrefixMatch: /
    # This is optional but useful if you want to expose Swagger-UI
        - type: RequestHeaderModifier
          requestHeaderModifier:
            set:
              - name: X-Forwarded-Prefix
                value: /jiradar/backend
      backendRefs:
        - name: jiradar-back-service
          port: 80
    # Path routing for frontend static files
    - matches:
        - path:
            type: PathPrefix
            value: /jiradar
      filters:
        - type: URLRewrite
          urlRewrite:
            path:
              type: ReplacePrefixMatch
              replacePrefixMatch: /
      backendRefs:
        - name: jiradar-front-service
          port: 80
```

### 4. Autoscaling (`04-autoscaling.yaml`)

This manifest applies KEDA cron triggers to manage active uptime windows.

```yaml
apiVersion: keda.sh/v1alpha1
kind: ScaledObject
metadata:
  name: jiradar-back-scaler
  namespace: { { NAMESPACE } }
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: jiradar-back
  minReplicaCount: 0
  maxReplicaCount: 1
  triggers:
    - type: cron
      metadata:
        timezone: { { TIMEZONE } }
        start: 0 7 * * *
        end: 0 19 * * *
        desiredReplicas: "1"
---
apiVersion: keda.sh/v1alpha1
kind: ScaledObject
metadata:
  name: jiradar-front-scaler
  namespace: { { NAMESPACE } }
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: jiradar-front
  minReplicaCount: 0
  maxReplicaCount: 1
  triggers:
    - type: cron
      metadata:
        timezone: { { TIMEZONE } }
        start: 0 7 * * *
        end: 0 19 * * *
        desiredReplicas: "1"
```

---

## Quick Deployment Script

You can use standard command-line tools like `sed` or `envsubst` to replace placeholders and deploy immediately.

### Option: Deploy with `sed`

Set your configuration environment variables and pipe the transformed outputs to `kubectl`:

```bash
# 1. Define configuration
export NAMESPACE="my-custom-namespace"
export REGISTRY_SECRET="my-registry-secret"
export JIRA_URL="[https://my-company.atlassian.net](https://my-company.atlassian.net)"
export HOSTNAME="jiradar.my-domain.com"
export GATEWAY_NAME="internal"
export GATEWAY_NAMESPACE="gateway"
export TIMEZONE="Europe/Paris"

# 2. Create target namespace if needed
kubectl create namespace ${NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -

# 3. Templatize and Apply
for file in 01-workloads.yaml 02-networking.yaml 03-routing.yaml 04-autoscaling.yaml; do
  sed -e "s|{{ NAMESPACE }}|${NAMESPACE}|g" \
      -e "s|{{ REGISTRY_SECRET }}|${REGISTRY_SECRET}|g" \
      -e "s|{{ JIRA_URL }}|${JIRA_URL}|g" \
      -e "s|{{ HOSTNAME }}|${HOSTNAME}|g" \
      -e "s|{{ GATEWAY_NAME }}|${GATEWAY_NAME}|g" \
      -e "s|{{ GATEWAY_NAMESPACE }}|${GATEWAY_NAMESPACE}|g" \
      -e "s|{{ TIMEZONE }}|${TIMEZONE}|g" \
      $file | kubectl apply -f -
done
```