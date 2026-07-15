import { useState } from 'react';
import { useTranslation } from '@core/hooks/useTranslation';
import { LanguageSwitcher } from "@core/components/languages/LanguageSwitcher";
import { LoginDropdown } from '@features/login/components/LoginDropdown';
import { useAuth } from '@core/context/authentication/AuthContext';
import { useTheme } from '@core/context/theme/ThemeContext';

export function Navbar() {
    const t = useTranslation();
    const { isAuthenticated, user, logout } = useAuth();
    const { theme, toggleTheme } = useTheme();
    const [isLoginOpen, setIsLoginOpen] = useState(false);

    return (
        <nav className="w-full bg-cardbg border-b border-border-subtle px-6 py-4 flex items-center justify-between transition-colors">
            <div className="flex items-center gap-3">
                <img
                    src="/jiradar_logo.svg"
                    alt="JiRadar Logo"
                    style={{ filter: 'invert(var(--logo-invert))' }}
                    className="w-8 h-8 object-contain transition-all"
                />
                <span className="text-xl font-bold tracking-tight text-text-main">
                    {t.app.title}
                </span>
            </div>

            <div className="text-sm text-text-muted hidden sm:block">
                {t.app.subtitle}
            </div>

            <div className="flex items-center gap-4">
                <button
                    onClick={toggleTheme}
                    className="p-2 rounded-lg bg-main-bg border border-border-subtle text-text-muted hover:text-text-main transition-colors cursor-pointer"
                    aria-label="Toggle theme"
                >
                    {theme === 'dark' ? (
                        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <circle cx="12" cy="12" r="4" strokeWidth="2" />
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 2v2m0 16v2M4.22 4.22l1.42 1.41m12.72 12.72l1.42 1.41M2 12h2m16 0h2M5.64 18.36l1.42-1.42m11.32-11.32l1.42-1.42" />
                        </svg>
                    ) : (
                        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
                        </svg>
                    )}
                </button>

                <LanguageSwitcher />

                <div className="relative flex items-center gap-3">
                    {isAuthenticated && user ? (
                        <div className="flex items-center gap-3">
                            <img
                                src={user.avatarUrl}
                                alt={user.displayName}
                                className="w-9 h-9 rounded-full border bg-border-subtle object-cover"
                            />
                            <button
                                onClick={logout}
                                className="text-xs font-semibold text-text-muted hover:text-red-400 transition-colors cursor-pointer"
                            >
                                Deconnexion
                            </button>
                        </div>
                    ) : (
                        <div className="relative">
                            <button
                                onClick={() => setIsLoginOpen(!isLoginOpen)}
                                className={`flex items-center justify-center w-9 h-9 rounded-full border transition-colors cursor-pointer ${
                                    isLoginOpen
                                        ? 'bg-btn-primary border-btn-text-main text-btn-text-main'
                                        : 'bg-btn-primary-hover border-btn-text-main text-btn-text-main'
                                }`}
                            >
                                <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                                    <path fillRule="evenodd" d="M7.5 6a4.5 4.5 0 1 1 9 0 4.5 4.5 0 0 1-9 0ZM3.751 20.105a8.25 8.25 0 0 1 16.498 0 .75.75 0 0 1-.437.695A18.683 18.683 0 0 1 12 22.5c-2.786 0-5.433-.608-7.812-1.7a.75.75 0 0 1-.437-.695Z" clipRule="evenodd" />
                                </svg>
                            </button>
                            <LoginDropdown isOpen={isLoginOpen} onClose={() => setIsLoginOpen(false)} />
                        </div>
                    )}
                </div>
            </div>
        </nav>
    );
}