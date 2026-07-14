import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi } from 'vitest';
import { FormDashboard } from './FormDashboard';
import {LocaleProvider} from "@core/context/LocaleProvider.tsx";

describe('FormDashboard Component', () => {
    it('should update inputs and submit filters on form submit', async () => {
        const mockSubmit = vi.fn();
        const user = userEvent.setup();

        render(
            <LocaleProvider>
                <FormDashboard onSubmit={mockSubmit} isLoading={false} />
            </LocaleProvider>
        );

        const projectInput = screen.getByLabelText(/Project Code/i);
        await user.type(projectInput, 'MYPROJECT');

        const selectGranularity = screen.getByLabelText(/Granularity/i);
        await user.selectOptions(selectGranularity, 'WEEK');

        const submitButton = screen.getByRole('button', { name: /Update Dashboard/i });
        await user.click(submitButton);

        expect(mockSubmit).toHaveBeenCalledWith(
            expect.objectContaining({
                projectKey: 'MYPROJECT',
                granularity: 'WEEK'
            })
        );
    });

    it('should render an error message and block submission if project key is empty/too short', async () => {
        const mockSubmit = vi.fn();
        const user = userEvent.setup();

        render(
            <LocaleProvider>
                <FormDashboard onSubmit={mockSubmit} isLoading={false} />
            </LocaleProvider>
        );

        const projectInput = screen.getByLabelText(/Project Code/i);
        await user.type(projectInput, 'A');

        const submitButton = screen.getByRole('button', { name: /Update Dashboard/i });
        await user.click(submitButton);

        expect(screen.getByText('Project Code Required')).toBeInTheDocument();
        expect(mockSubmit).not.toHaveBeenCalled();
    });
});