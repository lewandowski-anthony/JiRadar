export const ja = {
    // ---------------------------------------------------------
    // APP CONFIGURATION
    // ---------------------------------------------------------
    app: {
        loading: "読み込み中...",
        subtitle: "チームのパフォーマンス分析および履歴データ追跡",
        title: "JiRadar Dashboard"
    },

    // ---------------------------------------------------------
    // CHARTS LEGENDS & TITLES
    // ---------------------------------------------------------
    charts: {
        collaborationTitle: "コラボレーションおよびリリース成功率 (%)",
        concurrentIssues: "同時進行中のタスク数",
        cycleTime: "サイクルタイム",
        deliverySuccess: "リリース成功率 (%)",
        flowTitle: "アクティビティフロー (Issues Flow)",
        issuesDone: "完了したタスク数",
        issuesStarted: "開始されたタスク数",
        leadTimesTitle: "平均リードタイム (時間)",
        participationRate: "レビュー参加率 (%)",
        reviewHealthTitle: "レビュー効率および再オープン数",
        reviewTime: "レビュータイム",
        reviewsDone: "承認済みレビュー数",
        reviewsReopened: "再オープンされたレビュー数",
        typesTitle: "タスクタイプ分布比率 (%)",
        wipTitle: "平均仕掛品数 (WIP)"
    },

    // ---------------------------------------------------------
    // ERRORS & VALIDATIONS
    // ---------------------------------------------------------
    error: {
        projectCodeRequired: "プロジェクトコードが必要です"
    },

    // ---------------------------------------------------------
    // FORMS & FILTERS
    // ---------------------------------------------------------
    form: {
        endDate: "終了日",
        granularity: {
            daily: "日次",
            monthly: "月次",
            noGranularity: "期間指定なし",
            title: "时间粒度",
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
        noEvents: "履歴にイベントが見つかりませんでした。",
        title: "履歴"
    },

    // ---------------------------------------------------------
    // ISSUE PROPERTIES
    // ---------------------------------------------------------
    issue: {
        author: "作成者",
        date: "日付",
        modificationDate: "更新日",
        name: "タスク",
        title: "タイトル"
    },

    // ---------------------------------------------------------
    // KEY PERFORMANCE INDICATORS (KPIs)
    // ---------------------------------------------------------
    kpi: {
        averageCycleTime: {
            description: "1つのワークサイクルを完了するのに必要な平均時間。",
            title: "平均サイクルタイム"
        },
        averageReviewTime: {
            description: "1つのレビューを完了するのに必要な平均時間。",
            title: "平均レビュータイム"
        },
        deliverySuccessRate: {
            description: "総リリース数に対する成功したリリースの割合。",
            title: "リリース成功率"
        },
        numberOfIssueDone: {
            description: "完了したタスクの総数。",
            title: "完了タスク数"
        },
        numberOfIssueStarted: {
            description: "開始されたタスクの総数。",
            title: "開始タスク数"
        },
        numberOfReviewReopened: {
            description: "再オープンされたレビューの総数。",
            title: "レビュー再オープン数"
        },
        parallelIssuesInProgressRate: {
            description: "並行して処理されたタスクの平均数。",
            title: "平均並行タスク数"
        },
        pingPongReviewRate: {
            description: "レビューの差し戻しが複数回発生した（往復を繰り返した）タスクの割合。",
            title: "レビューピンポン率"
        }
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
        wip: "仕掛品タスク"
    }
};