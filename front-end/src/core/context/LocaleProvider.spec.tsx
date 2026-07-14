import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { LocaleProvider } from './LocaleProvider';
import { useLocale } from '@core/hooks/useLocale';
import { getCookie, setCookie } from '@core/utils/cookies';

vi.mock('@core/utils/cookies', () => ({
    getCookie: vi.fn(),
    setCookie: vi.fn(),
}));

function TestingComponent() {
    const [locale, changeLocale] = useLocale();
    return (
        <div>
            <span data-testid="current-lang">{locale}</span>
            <button onClick={() => changeLocale('ja')}>Change to JA</button>
        </div>
    );
}

describe('LocaleProvider Component', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('initialise la langue depuis le cookie s\'il existe', () => {
        vi.mocked(getCookie).mockReturnValue('pt');
        render(
            <LocaleProvider>
                <TestingComponent />
            </LocaleProvider>
        );
        expect(screen.getByTestId('current-lang')).toHaveTextContent('pt');
    });

    it('initialise via le navigateur si aucun cookie n\'est présent', () => {
        vi.mocked(getCookie).mockReturnValue(null);
        const languageSpy = vi.spyOn(navigator, 'language', 'get').mockReturnValue('zh-CN');

        render(
            <LocaleProvider>
                <TestingComponent />
            </LocaleProvider>
        );

        expect(screen.getByTestId('current-lang')).toHaveTextContent('zh');
        languageSpy.mockRestore();
    });

    it('applique un changement de langue et met à jour le cookie', async () => {
        vi.mocked(getCookie).mockReturnValue('en');
        const user = userEvent.setup();

        render(
            <LocaleProvider>
                <TestingComponent />
            </LocaleProvider>
        );

        const button = screen.getByRole('button', { name: 'Change to JA' });
        await user.click(button);

        expect(screen.getByTestId('current-lang')).toHaveTextContent('ja');
        expect(setCookie).toHaveBeenCalledWith('jiradar_locale', 'ja', 365);
    });
});