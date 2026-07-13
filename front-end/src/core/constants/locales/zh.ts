export const zh = {
    // ---------------------------------------------------------
    // APP CONFIGURATION
    // ---------------------------------------------------------
    app: {
        loading: "加载中...",
        subtitle: "团队绩效分析与历史数据回溯",
        title: "JiRadar Dashboard"
    },

    // ---------------------------------------------------------
    // CHARTS LEGENDS & TITLES
    // ---------------------------------------------------------
    charts: {
        collaborationTitle: "团队协同评审与交付成功率 (%)",
        concurrentIssues: "同时进行中的任务数",
        cycleTime: "开发周期 (Cycle Time)",
        deliverySuccess: "交付成功率 (%)",
        flowTitle: "活动趋势流量图 (Issues Flow)",
        issuesDone: "已完成任务数",
        issuesStarted: "已启动任务数",
        leadTimesTitle: "平均处理周期周期 (小时)",
        participationRate: "评审参与率 (%)",
        reviewHealthTitle: "评审效率与重新打开统计",
        reviewTime: "评审周期 (Review Time)",
        reviewsDone: "已通过评审数",
        reviewsReopened: "评审被驳回重开数",
        typesTitle: "任务类型分布图 (%)",
        wipTitle: "平均在制品占用率 (WIP)"
    },

    // ---------------------------------------------------------
    // ERRORS & VALIDATIONS
    // ---------------------------------------------------------
    error: {
        projectCodeRequired: "请输入项目代码"
    },

    // ---------------------------------------------------------
    // FORMS & FILTERS
    // ---------------------------------------------------------
    dashboardForm: {
        endDate: "结束日期",
        granularity: {
            daily: "按天",
            monthly: "按月",
            noGranularity: "不按时间段",
            title: "时间粒度",
            weekly: "按周",
            yearly: "按年"
        },
        projectCode: "项目代码",
        startDate: "开始日期",
        updateDashboard: "更新仪表板数据"
    },

    // ---------------------------------------------------------
    // HISTORY TRACKING
    // ---------------------------------------------------------
    history: {
        noEvents: "历史记录中未发现任何事件。",
        title: "历史数据"
    },

    // ---------------------------------------------------------
    // ISSUE PROPERTIES
    // ---------------------------------------------------------
    issue: {
        author: "报告人",
        date: "日期",
        modificationDate: "修改时间",
        name: "任务/故障",
        title: "标题"
    },

    // ---------------------------------------------------------
    // 登录
    // ---------------------------------------------------------
    loginForm: {
        token: "Token",
        issueTracker: "问题追踪系统",
        selectIssueTracker: "选择问题追踪系统",
        logIn: "登录",
        currentUser: "当前用户"
    },

    // ---------------------------------------------------------
    // KEY PERFORMANCE INDICATORS (KPIs)
    // ---------------------------------------------------------
    kpi: {
        averageCycleTime: {
            description: "完成一个工作周期所需的平均时间。",
            title: "平均开发周期"
        },
        averageReviewTime: {
            description: "完成一次代码评审所需的平均时间。",
            title: "平均评审时间"
        },
        deliverySuccessRate: {
            description: "成功交付次数占总交付次数的的百分比。",
            title: "交付成功率"
        },
        numberOfIssueDone: {
            description: "已完成的任务总数。",
            title: "已完成任务数"
        },
        numberOfIssueStarted: {
            description: "已启动的任务总数。",
            title: "已启动任务数"
        },
        numberOfReviewReopened: {
            description: "代码评审被驳回并重新打开的总次数。",
            title: "评审重新打开数"
        },
        parallelIssuesInProgressRate: {
            description: "同时处理的并行任务平均数量。",
            title: "平均同时进行任务数"
        },
        pingPongReviewRate: {
            description: "代码评审反复修改拉扯（多轮往返）的任务百分比。",
            title: "评审反复率"
        }
    },

    // ---------------------------------------------------------
    // PAGINATION
    // ---------------------------------------------------------
    page: {
        name: "页码",
        next: "下一页",
        on: "/",
        previous: "上一页",
        total: "总计"
    },

    // ---------------------------------------------------------
    // TABS NAVIGATION
    // ---------------------------------------------------------
    tabs: {
        periodic: "周期性分析",
        reviews: "代码评审",
        wip: "在制品追踪"
    }
};