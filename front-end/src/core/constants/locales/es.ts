export const es = {
    // ---------------------------------------------------------
    // APP CONFIGURATION
    // ---------------------------------------------------------
    app: {
        loading: "Cargando...",
        subtitle: "Análisis de rendimiento y datos históricos para equipos",
        title: "Tablero JiRadar"
    },

    // ---------------------------------------------------------
    // Common
    // ---------------------------------------------------------
    common: {
        email: "Correo electrónico",
        checking: "Comprobando",
    },

    // ---------------------------------------------------------
    // CHARTS LEGENDS & TITLES
    // ---------------------------------------------------------
    charts: {
        collaborationTitle: "Tasa de éxito de colaboración y entrega (%)",
        concurrentIssues: "Tareas simultáneas",
        cycleTime: "Tiempo de ciclo",
        deliverySuccess: "Tasa de éxito de entrega (%)",
        flowTitle: "Flujo de actividad (Flujo de tareas)",
        issuesDone: "Tareas completadas",
        issuesStarted: "Tareas iniciadas",
        leadTimesTitle: "Tiempos de entrega promedio (Horas)",
        participationRate: "Tasa de participación en revisiones (%)",
        reviewHealthTitle: "Eficiencia de revisión y reaperturas",
        reviewTime: "Tiempo de revisión",
        reviewsDone: "Revisiones completadas",
        reviewsReopened: "Revisiones reabiertas",
        typesTitle: "Distribución de tipos de tareas (%)",
        wipTitle: "Trabajo en progreso promedio (WIP)"
    },

    // ---------------------------------------------------------
    // ERRORS & VALIDATIONS
    // ---------------------------------------------------------
    error: {
        projectCodeRequired: "Código de proyecto requerido"
    },

    // ---------------------------------------------------------
    // FORMS & FILTERS
    // ---------------------------------------------------------
    dashboardForm: {
        endDate: "Fecha de finalización",
        granularity: {
            daily: "Diario",
            monthly: "Mensual",
            noGranularity: "Sin granularidad",
            title: "Granularidad",
            weekly: "Semanal",
            yearly: "Anual"
        },
        projectCode: "Código de proyecto",
        startDate: "Fecha de inicio",
        updateDashboard: "Actualizar tablero"
    },

    // ---------------------------------------------------------
    // HISTORY TRACKING
    // ---------------------------------------------------------
    history: {
        noEvents: "No se encontraron eventos en el historial.",
        title: "Historial"
    },

    // ---------------------------------------------------------
    // ---------------------------------------------------------
    // CUSTOM METRICS
    // ---------------------------------------------------------
    customMetrics: {
        noCustomMetrics: "No se encontraron métricas personalizadas.",
        title: "Métricas personalizadas"
    },

    // ISSUE PROPERTIES
    // ---------------------------------------------------------
    issue: {
        author: "Autor",
        date: "Fecha",
        modificationDate: "Fecha de modificación",
        name: "Tarea",
        title: "Título"
    },
    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    loginForm: {
        token: "Token",
        issueTracker: "Gestor de tareas",
        selectIssueTracker: "Seleccionar gestor de tareas",
        logIn: "Iniciar sesión",
        currentUser: "Usuario actual"
    },
    // ---------------------------------------------------------
    // KEY PERFORMANCE INDICATORS (KPIs)
    // ---------------------------------------------------------
    kpi: {
        averageCycleTime: {
            description: "El tiempo promedio requerido para completar un ciclo de trabajo.",
            title: "Tiempo de ciclo promedio"
        },
        averageReviewTime: {
            description: "El tiempo promedio requerido para completar una revisión.",
            title: "Tiempo de revisión promedio"
        },
        deliverySuccessRate: {
            description: "El porcentaje de entregas exitosas en comparación con el total.",
            title: "Tasa de éxito de entrega"
        },
        numberOfIssueDone: {
            description: "El número total de tareas completadas.",
            title: "Tareas completadas"
        },
        numberOfIssueStarted: {
            description: "El número total de tareas iniciadas.",
            title: "Tareas iniciadas"
        },
        numberOfReviewReopened: {
            description: "El número total de revisiones reabiertas.",
            title: "Revisiones reabiertas"
        },
        parallelIssuesInProgressRate: {
            description: "El número promedio de tareas gestionadas en paralelo.",
            title: "Tareas paralelas (Promedio)"
        },
        pingPongReviewRate: {
            description: "El porcentaje de tareas atrapadas en un bucle de revisión ping-pong (ida y vuelta).",
            title: "Tasa de revisión ping-pong"
        }
    },

    // ---------------------------------------------------------
    // Github
    // ---------------------------------------------------------
    github: {
        pat: "Token de acceso personal de GitHub",
        error: {
            invalidPat: "El token de GitHub (PAT) no es válido."
        }
    },

    // ---------------------------------------------------------
    // Jira
    // ---------------------------------------------------------
    jira: {
        token: "Token de Jira Atlassian",
        invalidToken: "Token no válido.",
        connect: "Conectarse a Jira."
    },

    // ---------------------------------------------------------
    // PAGINATION
    // ---------------------------------------------------------
    page: {
        name: "Página",
        next: "Siguiente",
        on: "de",
        previous: "Anterior",
        total: "Total"
    },

    // ---------------------------------------------------------
    // TABS NAVIGATION
    // ---------------------------------------------------------
    tabs: {
        periodic: "Análisis periódico",
        reviews: "Revisiones de código",
        customMetrics: "Métricas personalizadas",
        workHistory: "Historial de trabajo"
    }
};