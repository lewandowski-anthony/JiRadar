import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi } from 'vitest';
import { Tabs, Tab } from './Tab';

describe('Tabs Component System', () => {
    it('affiche l\'onglet par défaut ou le premier onglet disponible', () => {
        render(
            <Tabs>
                <Tab id="first" label="Tab 1">Content One</Tab>
                <Tab id="second" label="Tab 2">Content Two</Tab>
            </Tabs>
        );

        expect(screen.getByText('Content One')).toBeInTheDocument();
        expect(screen.queryByText('Content Two')).not.toBeInTheDocument();
    });

    it('permet de basculer d\'onglet lors du clic utilisateur', async () => {
        const user = userEvent.setup();
        render(
            <Tabs defaultActiveId="first">
                <Tab id="first" label="Tab 1">Content One</Tab>
                <Tab id="second" label="Tab 2">Content Two</Tab>
            </Tabs>
        );

        const targetTabButton = screen.getByRole('button', { name: 'Tab 2' });
        await user.click(targetTabButton);

        expect(screen.getByText('Content Two')).toBeInTheDocument();
        expect(screen.queryByText('Content One')).not.toBeInTheDocument();
    });

    it('lève une exception si <Tab /> est instancié sans contexte parent', () => {
        const spy = vi.spyOn(console, 'error').mockImplementation(() => {});

        expect(() => render(<Tab id="orphan" label="Orphan">Fail</Tab>)).toThrow(
            '<Tab /> doit être utilisé dans <Tabs />'
        );

        spy.mockRestore();
    });
});