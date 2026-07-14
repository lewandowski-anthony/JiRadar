import { render, screen } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { BasePieChart } from './BasePieChart';

vi.mock('react-chartjs-2', () => ({
    Pie: () => <div data-testid="mock-pie" />,
    Doughnut: () => <div data-testid="mock-doughnut" />
}));

describe('BasePieChart Component', () => {
    const defaultProps = {
        title: 'Issue Types Breakdown',
        labels: ['Bug', 'Task'],
        datasets: [{ label: 'Test Label', data: [10, 20] }]
    };

    it('renders Pie or Doughnut chart variants based on isDonut prop', () => {
        const { rerender } = render(<BasePieChart {...defaultProps} isDonut={false} />);
        expect(screen.getByText('Issue Types Breakdown')).toBeInTheDocument();

        rerender(<BasePieChart {...defaultProps} isDonut={true} />);
        expect(screen.getByText('Issue Types Breakdown')).toBeInTheDocument();
    });
});