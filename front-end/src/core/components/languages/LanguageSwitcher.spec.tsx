import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi } from 'vitest';
import { LanguageSwitcher } from './LanguageSwitcher';
import { LocaleContext } from '@core/context/localContext';
import type {LocaleType} from "@core/constants/locales";

describe('LanguageSwitcher Component', () => {
    const mockChangeLocale = vi.fn();

    const renderWithContext = (locale: LocaleType = 'en') => {
        return render(
            <LocaleContext.Provider value={{ locale, changeLocale: mockChangeLocale }}>
                <LanguageSwitcher />
            </LocaleContext.Provider>
        );
    };

    it('renders the active language code', () => {
        renderWithContext('fr');
        expect(screen.getByText('fr')).toBeInTheDocument();
    });

    it('opens dropdown and triggers language change', async () => {
        renderWithContext('en');
        const user = userEvent.setup();

        const triggerButton = screen.getByRole('button');
        await user.click(triggerButton);

        const frButton = screen.getByText('Français');
        await user.click(frButton);

        expect(mockChangeLocale).toHaveBeenCalledWith('fr');
    });
});