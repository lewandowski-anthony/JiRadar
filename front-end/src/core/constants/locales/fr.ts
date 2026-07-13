export const fr = {
    // ---------------------------------------------------------
    // APP CONFIGURATION
    // ---------------------------------------------------------
    app: {
        loading: "Chargement...",
        subtitle: "Analyse des performances et de l'historique des équipes",
        title: "JiRadar Dashboard"
    },

    // ---------------------------------------------------------
    // CHARTS LEGENDS & TITLES
    // ---------------------------------------------------------
    charts: {
        collaborationTitle: "Collaboration & Succès des Livraisons (%)",
        concurrentIssues: "Tickets en Cours Simultanément",
        cycleTime: "Temps de Cycle",
        deliverySuccess: "Succès des Livraisons (%)",
        flowTitle: "Flux d'Activité (Issues Flow)",
        issuesDone: "Tickets Terminés",
        issuesStarted: "Tickets Commencés",
        leadTimesTitle: "Délais de Traitement Moyens (en heures)",
        participationRate: "Participation aux Reviews (%)",
        reviewHealthTitle: "Efficacité & Retours de Revue",
        reviewTime: "Temps de Revue",
        reviewsDone: "Revues Validées",
        reviewsReopened: "Retours / Réouvertures",
        typesTitle: "Répartition des Types de Tickets (%)",
        wipTitle: "Tickets en Parallèle Moyen (WIP)"
    },

    // ---------------------------------------------------------
    // ERRORS & VALIDATIONS
    // ---------------------------------------------------------
    error: {
        projectCodeRequired: "Code projet requis"
    },

    // ---------------------------------------------------------
    // FORMS & FILTERS
    // ---------------------------------------------------------
    dashboardForm: {
        endDate: "Date de fin",
        granularity: {
            daily: "Journalier",
            monthly: "Mensuel",
            noGranularity: "Pas de granularité",
            title: "Granularité",
            weekly: "Hebdomadaire",
            yearly: "Annuel"
        },
        projectCode: "Code Projet",
        startDate: "Date de début",
        updateDashboard: "Mettre à jour le tableau de bord"
    },

    // ---------------------------------------------------------
    // HISTORY TRACKING
    // ---------------------------------------------------------
    history: {
        noEvents: "Aucun événement trouvé dans l'historique.",
        title: "Historique"
    },

    // ---------------------------------------------------------
    // ISSUE PROPERTIES
    // ---------------------------------------------------------
    issue: {
        author: "Auteur",
        date: "Date",
        modificationDate: "Date de modification",
        name: "Ticket",
        title: "Titre"
    },

    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    loginForm: {
        token: "Token",
        issueTracker: "Outil de gestion des tickets",
        selectIssueTracker: "Gestionnaire de tickets",
        logIn: "Se connecter",
        currentUser: "Utilisateur actuel"
    },

    // ---------------------------------------------------------
    // KEY PERFORMANCE INDICATORS (KPIs)
    // ---------------------------------------------------------
    kpi: {
        averageCycleTime: {
            description: "Le temps moyen nécessaire pour compléter un cycle de travail.",
            title: "Temps de Cycle Moyen"
        },
        averageReviewTime: {
            description: "Le temps moyen nécessaire pour effectuer une revue.",
            title: "Temps de Revue Moyen"
        },
        deliverySuccessRate: {
            description: "Le pourcentage de livraisons réussies par rapport au total.",
            title: "Taux de Réussite de Livraison"
        },
        numberOfIssueDone: {
            description: "Le nombre total de tickets terminés.",
            title: "Tickets Terminés"
        },
        numberOfIssueStarted: {
            description: "Le nombre total de tickets commencés.",
            title: "Tickets Commencés"
        },
        numberOfReviewReopened: {
            description: "Le nombre total de retours en revue.",
            title: "Retours Review"
        },
        parallelIssuesInProgressRate: {
            description: "La moyenne de tickets en parallèle.",
            title: "Tickets en Parallèle (Moyenne)"
        },
        pingPongReviewRate: {
            description: "Le pourcentage de tickets en revue ping-pong (allers-retours).",
            title: "Taux de Review Ping-Pong"
        }
    },

    // ---------------------------------------------------------
    // PAGINATION
    // ---------------------------------------------------------
    page: {
        name: "Page",
        next: "Suivant",
        on: "sur",
        previous: "Précédent",
        total: "Total"
    },

    // ---------------------------------------------------------
    // TABS NAVIGATION
    // ---------------------------------------------------------
    tabs: {
        periodic: "Analyse Périodique",
        reviews: "Revues de Code",
        wip: "Travail En Cours"
    }
};