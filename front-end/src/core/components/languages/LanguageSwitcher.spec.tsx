import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi } from 'vitest';
import { LanguageSwitcher } from './LanguageSwitcher';
import { LocaleContext } from '@core/context/language/localContext';

describe('LanguageSwitcher - Click Outside', () => {
    it('should close the dropdown when clicking outside', async () => {
        const mockChangeLocale = vi.fn();
        const user = userEvent.setup();

        render(
            <LocaleContext.Provider value={{ locale: 'en', changeLocale: mockChangeLocale }}>
                <div>
                    <div data-testid="outside">Outside</div>
                    <LanguageSwitcher />
                </div>
            </LocaleContext.Provider>
        );

        const triggerButton = screen.getByRole('button');
        await user.click(triggerButton);
        expect(screen.getByText('Français')).toBeInTheDocument();

        await user.click(screen.getByTestId('outside'));
        expect(screen.queryByText('Français')).not.toBeInTheDocument();
    });
});