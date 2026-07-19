import { useState } from 'react';
import { FormDashboard } from '../components/FormDashboard';
import { KpiCard } from '@features/kpis/components/KpiCard';
import { PeriodicCharts } from '@features/devcharts/components/PeriodicCharts';
import { HistoryList } from '@features/history/components/HistoryList';
import { useFetchDashboardDatas } from '../hooks/useFetchDashboardDatas';
import { KPI_CONFIGS } from '@core/constants/kpiConfig';
import { Tabs, Tab } from "@core/components/menus/Tab";
import { type TranslationKeys } from '@core/constants/locales';
import { useTranslation } from "@core/hooks/useTranslation";
import { type DashboardFilters } from '@core/models/dashboard';
import { CustomMetricsPanel } from '@features/customMetrics/components/CustomMetricsPanel';

export default function Dashboard() {
    const {
        userMetrics,
        history,
        loading,
        historyLoading,
        error,
        fetchDashboardData,
        fetchHistoryPage
    } = useFetchDashboardDatas();

    const t: TranslationKeys = useTranslation();
    const [currentFilters, setCurrentFilters] = useState<DashboardFilters | null>(null);

    const handleFormSubmit = (filters: DashboardFilters) => {
        setCurrentFilters(filters);
        fetchDashboardData(filters);
    };

    const handlePageChange = (newPage: number, newSize: number) => {
        if (!currentFilters) return;
        fetchHistoryPage(currentFilters, newPage, newSize);
    };

    return (
        <div className="space-y-8 w-full px-4 md:px-8">
            <FormDashboard onSubmit={handleFormSubmit} isLoading={loading} />

            {error && (
                <div className="text-xs text-red-600 dark:text-red-400 bg-red-500/10 dark:bg-red-950/40 p-4 border border-red-300 dark:border-red-900/50 rounded-xl transition-colors">
                    <p className="text-sm">{error}</p>
                </div>
            )}

            {loading && (
                <div className="text-center py-12 text-text-muted animate-pulse">
                    {t.app.loading}
                </div>
            )}

            {!loading && (userMetrics || history) && (
                <Tabs>
                    {userMetrics && (
                        <Tab id="metrics" label={t.tabs.periodic} icon="fa-solid fa-chart-simple">
                            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 w-full animate-fadeIn">
                                {KPI_CONFIGS.map((metricConfig) => {
                                    const metricKey = metricConfig.key;
                                    const metric = userMetrics ? userMetrics[metricKey] : undefined;
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

                    {(userMetrics?.customMetrics?.length ?? 0) > 0 && (
                        <Tab id="custom-metrics" label={t.tabs.customMetrics} icon="i-custom-icon">
                            <CustomMetricsPanel customMetrics={userMetrics?.customMetrics ?? []} />
                        </Tab>
                    )}

                    {history && (
                        <Tab id="history" label={t.tabs.workHistory} icon="fa-solid fa-clock-rotate-left">
                            <div className={`animate-fadeIn transition-opacity ${historyLoading ? 'opacity-50 pointer-events-none' : ''}`}>
                                <HistoryList historyPage={history} onPageChange={handlePageChange} historyLoading={historyLoading} />
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