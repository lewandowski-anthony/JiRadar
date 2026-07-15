export const ja = {
    // ---------------------------------------------------------
    // APP CONFIGURATION
    // ---------------------------------------------------------
    app: {
        loading: "読み込み中...",
        subtitle: "チームのパフォーマンス分析と履歴データ",
        title: "JiRadar ダッシュボード"
    },

    // ---------------------------------------------------------
    // Common
    // ---------------------------------------------------------
    common: {
        email: "メールアドレス",
        checking: "確認中",
    },

    // ---------------------------------------------------------
    // CHARTS LEGENDS & TITLES
    // ---------------------------------------------------------
    charts: {
        collaborationTitle: "コラボレーション＆デリバリー成功率 (%)",
        concurrentIssues: "並行課題",
        cycleTime: "サイクルタイム",
        deliverySuccess: "デリバリー成功率 (%)",
        flowTitle: "アクティビティフロー (課題のフロー)",
        issuesDone: "完了した課題",
        issuesStarted: "開始した課題",
        leadTimesTitle: "平均リードタイム (時間)",
        participationRate: "レビュー参加率 (%)",
        reviewHealthTitle: "レビュー効率と再オープン",
        reviewTime: "レビュー時間",
        reviewsDone: "完了したレビュー",
        reviewsReopened: "再オープンされたレビュー",
        typesTitle: "課題タイプの分布 (%)",
        wipTitle: "平均仕掛品 (WIP)"
    },

    // ---------------------------------------------------------
    // ERRORS & VALIDATIONS
    // ---------------------------------------------------------
    error: {
        projectCodeRequired: "プロジェクトコードは必須です"
    },

    // ---------------------------------------------------------
    // FORMS & FILTERS
    // ---------------------------------------------------------
    dashboardForm: {
        endDate: "終了日",
        granularity: {
            daily: "日次",
            monthly: "月次",
            noGranularity: "粒度なし",
            title: "粒度",
            weekly: "週次",
            yearly: "年次"
        },
        projectCode: "プロジェクトコード",
        startDate: "開始日",
        updateDashboard: "ダッシュボードを更新"
    },

    // ---------------------------------------------------------
    // HISTORY TRACKING
    // ---------------------------------------------------------
    history: {
        noEvents: "履歴にイベントが見つかりません。",
        title: "履歴"
    },

    // ---------------------------------------------------------
    // ISSUE PROPERTIES
    // ---------------------------------------------------------
    issue: {
        author: "作成者",
        date: "日付",
        modificationDate: "更新日",
        name: "課題",
        title: "タイトル"
    },
    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    loginForm: {
        token: "トークン",
        issueTracker: "課題トラッカー",
        selectIssueTracker: "課題トラッカーを選択",
        logIn: "ログイン",
        currentUser: "現在のユーザー"
    },
    // ---------------------------------------------------------
    // KEY PERFORMANCE INDICATORS (KPIs)
    // ---------------------------------------------------------
    kpi: {
        averageCycleTime: {
            description: "作業サイクルを完了するまでに必要な平均時間。",
            title: "平均サイクルタイム"
        },
        averageReviewTime: {
            description: "レビューを完了するまでに必要な平均時間。",
            title: "平均レビュー時間"
        },
        deliverySuccessRate: {
            description: "全体の配信に対する成功したデリバリーの割合。",
            title: "デリバリー成功率"
        },
        numberOfIssueDone: {
            description: "完了した課題の総数。",
            title: "完了した課題"
        },
        numberOfIssueStarted: {
            description: "開始した課題の総数。",
            title: "開始した課題"
        },
        numberOfReviewReopened: {
            description: "再オープンされたレビューの総数。",
            title: "レビューの再オープン"
        },
        parallelIssuesInProgressRate: {
            description: "並行して処理された課題の平均数。",
            title: "並行課題 (平均)"
        },
        pingPongReviewRate: {
            description: "ピンポンレビュー（やり取りの繰り返し）のループに陥った課題の割合。",
            title: "ピンポンレビュー率"
        }
    },

    // ---------------------------------------------------------
    // Github
    // ---------------------------------------------------------
    github: {
        pat: "GitHub 個人アクセストークン (PAT)",
        error: {
            invalidPat: "Github トークン (PAT) が無効です。"
        }
    },

    // ---------------------------------------------------------
    // Jira
    // ---------------------------------------------------------
    jira: {
        token: "Jira Atlassian トークン",
        invalidToken: "無効なトークン。",
        connect: "Jira に接続。"
    },

    // ---------------------------------------------------------
    // PAGINATION
    // ---------------------------------------------------------
    page: {
        name: "ページ",
        next: "次へ",
        on: "/",
        previous: "前へ",
        total: "合計"
    },

    // ---------------------------------------------------------
    // TABS NAVIGATION
    // ---------------------------------------------------------
    tabs: {
        periodic: "定期分析",
        reviews: "コードレビュー",
        workHistory: "作業履歴"
    }
};