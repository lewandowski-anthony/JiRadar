import { render, screen } from '@testing-library/react';
import { describe, test, expect, vi } from 'vitest';
import { CustomMetricsPanel } from './CustomMetricsPanel';

vi.mock('@core/hooks/useTranslation', () => ({
    useTranslation: () => ({
        customMetrics: {
            noCustomMetrics: "No custom metrics found."
        }
    })
}));

vi.mock('@features/kpis/components/KpiCard', () => ({
    KpiCard: ({ title, value, color, borderColor }: any) => {
        const displayValue = typeof value === 'string' && value.startsWith('ERROR:') ? '-' : value;
        return (
            <div data-testid="kpi-card" data-color={color} data-border={borderColor}>
                <span data-testid="kpi-title">{title}</span>
                <span data-testid="kpi-value">{displayValue}</span>
            </div>
        );
    }
}));

describe('CustomMetricsPanel - Component Test Suite', () => {

    test('should display default empty state message when customMetrics array is missing or empty', () => {
        const { rerender } = render(<CustomMetricsPanel customMetrics={[]} />);
        expect(screen.getByText("No custom metrics found.")).toBeInTheDocument();

        rerender(<CustomMetricsPanel customMetrics={undefined} />);
        expect(screen.getByText("No custom metrics found.")).toBeInTheDocument();
    });

    test('should properly map names and pass nominal values to KpiCard components', () => {
        const mockMetrics = [
            { name: 'bugs-count', value: 14 },
            { name: 'is-pipeline-green', value: true },
            { name: 'is-wip-alert', value: false }
        ];

        render(<CustomMetricsPanel customMetrics={mockMetrics} />);

        expect(screen.getByText('bugs count')).toBeInTheDocument();
        expect(screen.getByText('14')).toBeInTheDocument();

        expect(screen.getByText('TRUE')).toBeInTheDocument();
        expect(screen.getByText('FALSE')).toBeInTheDocument();

        const cards = screen.getAllByTestId('kpi-card');
        expect(cards[0]).toHaveAttribute('data-color', 'text-text-main');
        expect(cards[0]).toHaveAttribute('data-border', 'border-border-subtle');
    });

    test('should securely handle SpEL runtime evaluation errors and output a dash safely', () => {
        const consoleWarnSpy = vi.spyOn(console, 'warn').mockImplementation(() => {});
        const mockMetricsWithErrors = [
            { name: 'failed-formula', value: 'ERROR: SpelEvaluationException: Division by zero' }
        ];

        render(<CustomMetricsPanel customMetrics={mockMetricsWithErrors} />);

        expect(screen.getByText('failed formula')).toBeInTheDocument();
        expect(screen.getByText('-')).toBeInTheDocument();

        consoleWarnSpy.mockRestore();
    });
});