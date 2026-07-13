export const pt = {
    // ---------------------------------------------------------
    // APP CONFIGURATION
    // ---------------------------------------------------------
    app: {
        loading: "Carregando...",
        subtitle: "Análise de desempenho e histórico de dados para equipas",
        title: "JiRadar Dashboard"
    },

    // ---------------------------------------------------------
    // CHARTS LEGENDS & TITLES
    // ---------------------------------------------------------
    charts: {
        collaborationTitle: "Colaboração e Taxa de Sucesso de Entrega (%)",
        concurrentIssues: "Tarefas Simultâneas",
        cycleTime: "Tempo de Ciclo",
        deliverySuccess: "Taxa de Sucesso de Entrega (%)",
        flowTitle: "Fluxo de Atividade (Issues Flow)",
        issuesDone: "Tarefas Concluídas",
        issuesStarted: "Tarefas Iniciadas",
        leadTimesTitle: "Tempos Médios de Execução (Horas)",
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
        projectCodeRequired: "Código do projeto obrigatório"
    },

    // ---------------------------------------------------------
    // FORMS & FILTERS
    // ---------------------------------------------------------
    dashboardForm: {
        endDate: "Data de término",
        granularity: {
            daily: "Diário",
            monthly: "Mensal",
            noGranularity: "Sem granularidade",
            title: "Granularidade",
            weekly: "Semanal",
            yearly: "Anual"
        },
        projectCode: "Código do Projeto",
        startDate: "Data de início",
        updateDashboard: "Atualizar painel de controlo"
    },

    // ---------------------------------------------------------
    // HISTORY TRACKING
    // ---------------------------------------------------------
    history: {
        noEvents: "Nenhum evento encontrado no histórico.",
        title: "Histórico"
    },

    // ---------------------------------------------------------
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
        issueTracker: "Gerenciador de tarefas",
        selectIssueTracker: "Selecionar gerenciador de tarefas",
        logIn: "Entrar",
        currentUser: "Usuário atual"
    },

    // ---------------------------------------------------------
    // KEY PERFORMANCE INDICATORS (KPIs)
    // ---------------------------------------------------------
    kpi: {
        averageCycleTime: {
            description: "O tempo médio necessário para concluir um cycle de trabalho.",
            title: "Tempo de Ciclo Médio"
        },
        averageReviewTime: {
            description: "O tempo médio necessário para realizar uma revisão.",
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
            title: "Revisões Reabertas"
        },
        parallelIssuesInProgressRate: {
            description: "A média de tarefas tratadas em paralelo.",
            title: "Tarefas Simultâneas (Média)"
        },
        pingPongReviewRate: {
            description: "A percentagem de tarefas presas num ciclo de revisão ping-pong (ida e volta).",
            title: "Taxa de Revisão Ping-Pong"
        }
    },

    // ---------------------------------------------------------
    // PAGINATION
    // ---------------------------------------------------------
    page: {
        name: "Página",
        next: "Seguinte",
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
        wip: "Trabalho Em Progresso"
    }
};