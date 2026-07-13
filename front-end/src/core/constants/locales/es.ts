export const es = {
    // ---------------------------------------------------------
    // APP CONFIGURATION
    // ---------------------------------------------------------
    app: {
        loading: "Cargando...",
        subtitle: "Análisis de rendimiento e histórico de datos para equipos",
        title: "JiRadar Dashboard"
    },

    // ---------------------------------------------------------
    // CHARTS LEGENDS & TITLES
    // ---------------------------------------------------------
    charts: {
        collaborationTitle: "Colaboración y Tasa de Éxito de Entrega (%)",
        concurrentIssues: "Tareas Simultáneas",
        cycleTime: "Tiempo de Ciclo",
        deliverySuccess: "Tasa de Éxito de Entrega (%)",
        flowTitle: "Flujo de Actividad (Issues Flow)",
        issuesDone: "Tareas Completadas",
        issuesStarted: "Tareas Iniciadas",
        leadTimesTitle: "Tiempos Medios de Ejecución (Horas)",
        participationRate: "Tasa de Participación en Revisiones (%)",
        reviewHealthTitle: "Eficiencia de Revisión y Reaperturas",
        reviewTime: "Tiempo de Revisión",
        reviewsDone: "Revisiones Completadas",
        reviewsReopened: "Revisiones Reabiertas",
        typesTitle: "Distribución por Tipo de Tarea (%)",
        wipTitle: "Trabajo en Progreso Medio (WIP)"
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
        projectCode: "Código de Proyecto",
        startDate: "Fecha de inicio",
        updateDashboard: "Actualizar panel de control"
    },

    // ---------------------------------------------------------
    // HISTORY TRACKING
    // ---------------------------------------------------------
    history: {
        noEvents: "No se encontraron eventos en el historial.",
        title: "Historial"
    },

    // ---------------------------------------------------------
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
        issueTracker: "Rastreador de problemas",
        selectIssueTracker: "Seleccionar gestor de incidencias",
        logIn: "Iniciar sesión",
        currentUser: "Usuario actual"
    },

    // ---------------------------------------------------------
    // KEY PERFORMANCE INDICATORS (KPIs)
    // ---------------------------------------------------------
    kpi: {
        averageCycleTime: {
            description: "El tiempo promedio necesario para completar un ciclo de trabajo.",
            title: "Tiempo de Ciclo Medio"
        },
        averageReviewTime: {
            description: "El tiempo promedio necesario para realizar una revisión.",
            title: "Tiempo de Revisión Medio"
        },
        deliverySuccessRate: {
            description: "El porcentaje de entregas exitosas en comparación con el total.",
            title: "Tasa de Éxito de Entrega"
        },
        numberOfIssueDone: {
            description: "El número total de tareas completadas.",
            title: "Tareas Completadas"
        },
        numberOfIssueStarted: {
            description: "El número total de tareas iniciadas.",
            title: "Tareas Iniciadas"
        },
        numberOfReviewReopened: {
            description: "El número total de revisiones reabiertas.",
            title: "Revisiones Reabiertas"
        },
        parallelIssuesInProgressRate: {
            description: "El promedio de tareas manejadas en paralelo.",
            title: "Tareas Simultáneas (Promedio)"
        },
        pingPongReviewRate: {
            description: "El porcentaje de tareas atrapadas en un bucle de revisión ping-pong (ida y vuelta).",
            title: "Tasa de Revisión Ping-Pong"
        }
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
        periodic: "Análisis Periódico",
        reviews: "Revisiones de Código",
        wip: "Trabajo En Progreso"
    }
};