export const pt = {
    // ---------------------------------------------------------
    // APP CONFIGURATION
    // ---------------------------------------------------------
    app: {
        loading: "Carregando...",
        subtitle: "Análise de desempenho e dados históricos para equipes",
        title: "Painel JiRadar"
    },

    // ---------------------------------------------------------
    // Common
    // ---------------------------------------------------------
    common: {
        email: "E-mail",
        checking: "Verificando",
    },

    // ---------------------------------------------------------
    // CHARTS LEGENDS & TITLES
    // ---------------------------------------------------------
    charts: {
        collaborationTitle: "Taxa de Sucesso de Colaboração e Entrega (%)",
        concurrentIssues: "Tarefas Simultâneas",
        cycleTime: "Tempo de Ciclo",
        deliverySuccess: "Taxa de Sucesso de Entrega (%)",
        flowTitle: "Fluxo de Atividade (Fluxo de Tarefas)",
        issuesDone: "Tarefas Concluídas",
        issuesStarted: "Tarefas Iniciadas",
        leadTimesTitle: "Tempo Médio de Atendimento (Horas)",
        participationRate: "Taxa de Participação em Revisões (%)",
        reviewHealthTitle: "Eficiência de Revisão e Reaberturas",
        reviewTime: "Tempo de Revisão",
        reviewsDone: "Revisões Concluídas",
        reviewsReopened: "Revisões Reabertas",
        typesTitle: "Distribuição por Tipo de Tarefa (%)",
        wipTitle: "Trabalho em Progresso Médio (WIP)"
    },

    // ---------------------------------------------------------
    // ERRORS & VALIDATIONS
    // ---------------------------------------------------------
    error: {
        projectCodeRequired: "Código do Projeto Obrigatório"
    },

    // ---------------------------------------------------------
    // FORMS & FILTERS
    // ---------------------------------------------------------
    dashboardForm: {
        endDate: "Data Final",
        granularity: {
            daily: "Diário",
            monthly: "Mensal",
            noGranularity: "Sem Granularidade",
            title: "Granularidade",
            weekly: "Semanal",
            yearly: "Anual"
        },
        projectCode: "Código do Projeto",
        startDate: "Data Inicial",
        updateDashboard: "Atualizar Painel"
    },

    // ---------------------------------------------------------
    // HISTORY TRACKING
    // ---------------------------------------------------------
    history: {
        noEvents: "Nenhum evento encontrado no histórico.",
        title: "Histórico"
    },

    // ---------------------------------------------------------
    // CUSTOM METRICS
    // ---------------------------------------------------------
    customMetrics: {
        noCustomMetrics: "Nenhuma métrica personalizada encontrada.",
        title: "Métricas personalizadas"
    },

    // ISSUE PROPERTIES
    // ---------------------------------------------------------
    issue: {
        author: "Autor",
        date: "Data",
        modificationDate: "Data de modificação",
        name: "Tarefa",
        title: "Título"
    },
    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    loginForm: {
        token: "Token",
        issueTracker: "Gerenciador de Tarefas",
        selectIssueTracker: "Selecionar Gerenciador de Tarefas",
        logIn: "Entrar",
        currentUser: "Usuário Atual"
    },
    // ---------------------------------------------------------
    // KEY PERFORMANCE INDICATORS (KPIs)
    // ---------------------------------------------------------
    kpi: {
        averageCycleTime: {
            description: "O tempo médio necessário para concluir um ciclo de trabalho.",
            title: "Tempo de Ciclo Médio"
        },
        averageReviewTime: {
            description: "O tempo médio necessário para concluir uma revisão.",
            title: "Tempo de Revisão Médio"
        },
        deliverySuccessRate: {
            description: "A percentagem de entregas bem-sucedidas em relação ao total.",
            title: "Taxa de Sucesso de Entrega"
        },
        numberOfIssueDone: {
            description: "O número total de tarefas concluídas.",
            title: "Tarefas Concluídas"
        },
        numberOfIssueStarted: {
            description: "O número total de tarefas iniciadas.",
            title: "Tarefas Iniciadas"
        },
        numberOfReviewReopened: {
            description: "O número total de revisões reabertas.",
            title: "Reaberturas de Revisão"
        },
        parallelIssuesInProgressRate: {
            description: "O número médio de tarefas tratadas em paralelo.",
            title: "Tarefas Paralelas (Média)"
        },
        pingPongReviewRate: {
            description: "A percentagem de tarefas presas em um ciclo de revisão ping-pong (ida e volta).",
            title: "Taxa de Revisão Ping-Pong"
        }
    },

    // ---------------------------------------------------------
    // Github
    // ---------------------------------------------------------
    github: {
        pat: "Token de Acesso Pessoal do GitHub (PAT)",
        error: {
            invalidPat: "O Token do GitHub (PAT) é inválido."
        }
    },

    // ---------------------------------------------------------
    // Jira
    // ---------------------------------------------------------
    jira: {
        token: "Token do Jira Atlassian",
        invalidToken: "Token inválido.",
        connect: "Conectar ao Jira."
    },

    // ---------------------------------------------------------
    // PAGINATION
    // ---------------------------------------------------------
    page: {
        name: "Página",
        next: "Próxima",
        on: "de",
        previous: "Anterior",
        total: "Total"
    },

    // ---------------------------------------------------------
    // TABS NAVIGATION
    // ---------------------------------------------------------
    tabs: {
        periodic: "Análise Periódica",
        reviews: "Revisões de Código",
        customMetrics: "Métricas personalizadas",
        workHistory: "Histórico de Trabalho"
    }
};