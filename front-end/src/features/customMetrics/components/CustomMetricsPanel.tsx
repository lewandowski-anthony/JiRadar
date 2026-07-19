import { useTranslation } from '@core/hooks/useTranslation';
import type { CustomMetricElement } from '@core/models/dashboard';
import { KpiCard } from '@features/kpis/components/KpiCard';

interface CustomMetricsPanelProps {
    customMetrics?: CustomMetricElement[];
}

export function CustomMetricsPanel({ customMetrics }: Readonly<CustomMetricsPanelProps>) {
    const t = useTranslation();

    if (!customMetrics || customMetrics.length === 0) {
        return (
            <div className="bg-cardbg p-8 rounded-xl border border-border-subtle text-center text-text-muted transition-colors">
                <p>{t.customMetrics.noCustomMetrics}</p>
            </div>
        );
    }

    return (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 w-full">
            {customMetrics.map((metric) => {
                const { name, value } = metric;

                const formattedTitle = name.replaceAll('-', ' ');

                let displayValue: string | number = '-';
                if (value !== undefined && value !== null) {
                    if (typeof value === 'boolean') {
                        displayValue = value ? 'TRUE' : 'FALSE';
                    } else {
                        displayValue = value;
                    }
                }

                return (
                    <KpiCard
                        key={name}
                        title={formattedTitle}
                        value={displayValue}
                        color="text-text-main"
                        borderColor="border-border-subtle"
                    />
                );
            })}
        </div>
    );
}