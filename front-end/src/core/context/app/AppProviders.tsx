import React from 'react';
import { LocaleProvider } from '@core/context/language/LocaleProvider';
import { ThemeProvider } from '@core/context/theme/ThemeContext';
import { AuthProvider } from '@core/context/authentication/AuthContext';

export function AppProviders({ children }: Readonly<{ children: React.ReactNode }>) {
    return (
        <LocaleProvider>
            <ThemeProvider>
                <AuthProvider>
                    {children}
                </AuthProvider>
            </ThemeProvider>
        </LocaleProvider>
    );
}