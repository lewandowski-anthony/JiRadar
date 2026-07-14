import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect } from 'vitest';
import { KpiCard } from './KpiCard';

describe('KpiCard Component', () => {
    const props = {
        title: 'Test KPI',
        value: '84%',
        description: 'This is a description used for performance tracking.',
        color: 'text-emerald-400',
        borderColor: 'border-emerald-500/20'
    };

    it('renders title and value', () => {
        render(<KpiCard {...props} />);
        expect(screen.getByText('Test KPI')).toBeInTheDocument();
        expect(screen.getByText('84%')).toBeInTheDocument();
    });

    it('toggles description on click', async () => {
        render(<KpiCard {...props} />);
        const user = userEvent.setup();
        const button = screen.getByRole('button');

        const gridContainer = screen.getByText('This is a description used for performance tracking.').closest('.grid');
        expect(gridContainer).toHaveClass('grid-rows-[0fr]');

        await user.click(button);
        expect(gridContainer).toHaveClass('grid-rows-[1fr]');

        await user.click(button);
        expect(gridContainer).toHaveClass('grid-rows-[0fr]');
    });
});