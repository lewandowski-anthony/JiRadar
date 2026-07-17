export const fr = {
    // ---------------------------------------------------------
    // APP CONFIGURATION
    // ---------------------------------------------------------
    app: {
        loading: "Chargement...",
        subtitle: "Analyse de performance et données historiques pour les équipes",
        title: "Tableau de bord JiRadar"
    },

    // ---------------------------------------------------------
    // Common
    // ---------------------------------------------------------
    common: {
        email: "E-mail",
        checking: "Vérification",
    },

    // ---------------------------------------------------------
    // CHARTS LEGENDS & TITLES
    // ---------------------------------------------------------
    charts: {
        collaborationTitle: "Taux de réussite de collaboration & de livraison (%)",
        concurrentIssues: "Tickets simultanés",
        cycleTime: "Temps de cycle",
        deliverySuccess: "Taux de réussite de livraison (%)",
        flowTitle: "Flux d'activité (Flux de tickets)",
        issuesDone: "Tickets terminés",
        issuesStarted: "Tickets commencés",
        leadTimesTitle: "Temps d'attente moyens (Heures)",
        participationRate: "Taux de participation aux revues (%)",
        reviewHealthTitle: "Efficacité des revues & Réouvertures",
        reviewTime: "Temps de revue",
        reviewsDone: "Revues terminées",
        reviewsReopened: "Revues réouvertes",
        typesTitle: "Distribution par type de ticket (%)",
        wipTitle: "Encours moyen (WIP)"
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
            daily: "Quotidien",
            monthly: "Mensuel",
            noGranularity: "Pas de granularité",
            title: "Granularité",
            weekly: "Hebdomadaire",
            yearly: "Annuel"
        },
        projectCode: "Code projet",
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
    // CUSTOM METRICS
    // ---------------------------------------------------------
    customMetrics: {
        noCustomMetrics: "Aucune métrique personnalisée trouvée.",
        title: "Métriques personnalisées"
    },

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
        token: "Jeton",
        issueTracker: "Gestionnaire de tickets",
        selectIssueTracker: "Sélectionner un gestionnaire de tickets",
        logIn: "Se connecter",
        currentUser: "Utilisateur actuel"
    },
    // ---------------------------------------------------------
    // KEY PERFORMANCE INDICATORS (KPIs)
    // ---------------------------------------------------------
    kpi: {
        averageCycleTime: {
            description: "Le temps moyen nécessaire pour terminer un cycle de travail.",
            title: "Temps de cycle moyen"
        },
        averageReviewTime: {
            description: "Le temps moyen nécessaire pour effectuer une revue.",
            title: "Temps de revue moyen"
        },
        deliverySuccessRate: {
            description: "Le pourcentage de livraisons réussies par rapport au total.",
            title: "Taux de réussite de livraison"
        },
        numberOfIssueDone: {
            description: "Le nombre total de tickets terminés.",
            title: "Tickets terminés"
        },
        numberOfIssueStarted: {
            description: "Le nombre total de tickets commencés.",
            title: "Tickets commencés"
        },
        numberOfReviewReopened: {
            description: "Le nombre total de revues réouvertes.",
            title: "Revues réouvertes"
        },
        parallelIssuesInProgressRate: {
            description: "Le nombre moyen de tickets traités en parallèle.",
            title: "Tickets en parallèle (Moyenne)"
        },
        pingPongReviewRate: {
            description: "Le pourcentage de tickets bloqués dans une boucle de revue ping-pong (va-et-vient).",
            title: "Taux de revue ping-pong"
        }
    },

    // ---------------------------------------------------------
    // Github
    // ---------------------------------------------------------
    github: {
        pat: "Jeton d'accès personnel GitHub (PAT)",
        error: {
            invalidPat: "Le jeton GitHub (PAT) est invalide."
        }
    },

    // ---------------------------------------------------------
    // Jira
    // ---------------------------------------------------------
    jira: {
        token: "Jeton Atlassian Jira",
        invalidToken: "Jeton invalide.",
        connect: "Se connecter à Jira."
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
        periodic: "Analyse périodique",
        reviews: "Revues de code",
        customMetrics: "Métriques personnalisées",
        workHistory: "Historique de travail"
    }
};