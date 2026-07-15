export const zh = {
    // ---------------------------------------------------------
    // APP CONFIGURATION
    // ---------------------------------------------------------
    app: {
        loading: "加载中...",
        subtitle: "团队绩效分析与历史数据",
        title: "JiRadar 仪表板"
    },

    // ---------------------------------------------------------
    // Common
    // ---------------------------------------------------------
    common: {
        email: "电子邮件",
        checking: "正在检查",
    },

    // ---------------------------------------------------------
    // CHARTS LEGENDS & TITLES
    // ---------------------------------------------------------
    charts: {
        collaborationTitle: "协作与交付成功率 (%)",
        concurrentIssues: "并发问题数",
        cycleTime: "周期时间",
        deliverySuccess: "交付成功率 (%)",
        flowTitle: "活动流（问题流）",
        issuesDone: "已完成问题",
        issuesStarted: "已启动问题",
        leadTimesTitle: "平均前置时间（小时）",
        participationRate: "评审参与率 (%)",
        reviewHealthTitle: "评审效率与重新打开",
        reviewTime: "评审时间",
        reviewsDone: "已完成评审",
        reviewsReopened: "重新打开的评审",
        typesTitle: "问题类型分布 (%)",
        wipTitle: "平均在制品数量 (WIP)"
    },

    // ---------------------------------------------------------
    // ERRORS & VALIDATIONS
    // ---------------------------------------------------------
    error: {
        projectCodeRequired: "需要项目代码"
    },

    // ---------------------------------------------------------
    // FORMS & FILTERS
    // ---------------------------------------------------------
    dashboardForm: {
        endDate: "结束日期",
        granularity: {
            daily: "每日",
            monthly: "每月",
            noGranularity: "无粒度",
            title: "粒度",
            weekly: "每周",
            yearly: "每年"
        },
        projectCode: "项目代码",
        startDate: "开始日期",
        updateDashboard: "更新仪表板"
    },

    // ---------------------------------------------------------
    // HISTORY TRACKING
    // ---------------------------------------------------------
    history: {
        noEvents: "历史记录中未找到任何事件。",
        title: "历史记录"
    },

    // ---------------------------------------------------------
    // ISSUE PROPERTIES
    // ---------------------------------------------------------
    issue: {
        author: "作者",
        date: "日期",
        modificationDate: "修改日期",
        name: "问题",
        title: "标题"
    },
    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    loginForm: {
        token: "令牌",
        issueTracker: "问题跟踪工具",
        selectIssueTracker: "选择问题跟踪工具",
        logIn: "登录",
        currentUser: "当前用户"
    },
    // ---------------------------------------------------------
    // KEY PERFORMANCE INDICATORS (KPIs)
    // ---------------------------------------------------------
    kpi: {
        averageCycleTime: {
            description: "完成一个工作周期所需的平均时间。",
            title: "平均周期时间"
        },
        averageReviewTime: {
            description: "完成一次评审所需的平均时间。",
            title: "平均评审时间"
        },
        deliverySuccessRate: {
            description: "成功交付占总交付量的百分比。",
            title: "交付成功率"
        },
        numberOfIssueDone: {
            description: "已完成问题的总数。",
            title: "已完成问题"
        },
        numberOfIssueStarted: {
            description: "已启动问题的总数。",
            title: "已启动问题"
        },
        numberOfReviewReopened: {
            description: "重新打开的评审总数。",
            title: "重新打开的评审"
        },
        parallelIssuesInProgressRate: {
            description: "并行处理的平均问题数。",
            title: "并行问题数（平均）"
        },
        pingPongReviewRate: {
            description: "陷入乒乓评审循环（来回往复）的问题百分比。",
            title: "乒乓评审率"
        }
    },

    // ---------------------------------------------------------
    // Github
    // ---------------------------------------------------------
    github: {
        pat: "GitHub 个人访问令牌 (PAT)",
        error: {
            invalidPat: "GitHub 令牌 (PAT) 无效。"
        }
    },

    // ---------------------------------------------------------
    // Jira
    // ---------------------------------------------------------
    jira: {
        token: "Jira Atlassian 令牌",
        invalidToken: "无效的令牌。",
        connect: "连接到 Jira。"
    },

    // ---------------------------------------------------------
    // PAGINATION
    // ---------------------------------------------------------
    page: {
        name: "页",
        next: "下一页",
        on: "/",
        previous: "上一页",
        total: "总计"
    },

    // ---------------------------------------------------------
    // TABS NAVIGATION
    // ---------------------------------------------------------
    tabs: {
        periodic: "定期分析",
        reviews: "代码评审",
        workHistory: "工作历史"
    }
};