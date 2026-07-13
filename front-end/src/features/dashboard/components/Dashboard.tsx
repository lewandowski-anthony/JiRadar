import { FormDashboard } from '../components/FormDashboard';
import { KpiCard } from '@features/kpis/components/KpiCard.tsx';
import { PeriodicCharts } from '@features/devCharts/components/PeriodicCharts';
import { HistoryList } from '@features/history/components/HistoryList';
import { useDashboard } from '../hooks/useDashboard';
import { KPI_CONFIGS } from '@core/constants/kpiConfig';
import { Tabs, Tab } from "@core/components/Tab.tsx";
import {type TranslationKeys} from '@core/constants/locales';
import {useTranslation} from "@core/hooks/useTranslation.ts";

export default function Dashboard() {
    const { userMetrics, history, loading, error, fetchDashboardData } = useDashboard();
    const t: TranslationKeys = useTranslation();

    const handlePageChange = (newPage: number) => {
        console.log("Page change :", newPage);
    };

    return (
        <div className="space-y-8 w-full px-4 md:px-8">
            <FormDashboard onSubmit={fetchDashboardData} isLoading={loading} />

            {error && (
                <div className="bg-red-950/50 border border-red-900 text-red-400 p-4 rounded-xl">
                    <p className="text-sm">{error}</p>
                </div>
            )}

            {loading && (
                <div className="text-center py-12 text-slate-400 animate-pulse">
                    {t.app.loading}
                </div>
            )}

            {!loading && (userMetrics || history) && (
                <Tabs>
                    {userMetrics && (
                        <Tab id="metrics" label={t.tabs.periodic} icon="fa-solid fa-chart-simple">
                            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 w-full animate-fadeIn">
                                {KPI_CONFIGS.map((metricConfig) => {
                                    const metric = (userMetrics as any)[metricConfig.key];
                                    const displayValue = metricConfig.format ? metricConfig.format(metric) : String(metric ?? '');

                                    const kpiText = t.kpi[metricConfig.key];

                                    return (
                                        <KpiCard
                                            key={metricConfig.key}
                                            title={kpiText.title}
                                            value={displayValue}
                                            color={metricConfig.color}
                                            description={kpiText.description}
                                            borderColor={metricConfig.borderColor}
                                        />
                                    );
                                })}
                            </div>
                        </Tab>
                    )}

                    {history && (
                        <Tab id="history" label={t.tabs.wip} icon="fa-solid fa-clock-rotate-left">
                            <div className="animate-fadeIn">
                                <HistoryList historyPage={history} onPageChange={handlePageChange} />
                            </div>
                        </Tab>
                    )}

                    {userMetrics?.userMetricsByGranularity && (
                        <Tab id="charts" label={t.tabs.reviews} icon="fa-solid fa-chart-bar">
                            <div className="animate-fadeIn">
                                <PeriodicCharts granularityData={userMetrics.userMetricsByGranularity} />
                            </div>
                        </Tab>
                    )}
                </Tabs>
            )}
        </div>
    );
}