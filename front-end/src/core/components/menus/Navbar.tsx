import { useState } from 'react';
import { useTranslation } from '@core/hooks/useTranslation';
import { LanguageSwitcher } from "@core/components/languages/LanguageSwitcher";
import { LoginDropdown } from '@features/login/components/LoginDropdown';

export function Navbar() {
    const t = useTranslation();
    const [isLoginOpen, setIsLoginOpen] = useState(false);

    return (
        <nav className="w-full bg-slate-900 border-b border-slate-800 px-6 py-4 flex items-center justify-between">
            <div className="flex items-center gap-3">
                <img
                    src="/jiradar_logo.svg"
                    alt="JiRadar Logo"
                    className="w-8 h-8 object-contain"
                />
                <span className="text-xl font-bold tracking-tight text-white">
                    {t.app.title}
                </span>
            </div>

            <div className="text-sm text-slate-400 hidden sm:block">
                {t.app.subtitle}
            </div>

            <div className="flex items-center gap-4">
                <LanguageSwitcher />

                <div className="relative">
                    <button
                        onClick={() => setIsLoginOpen(!isLoginOpen)}
                        className={`flex items-center justify-center w-9 h-9 rounded-full border transition-colors ${
                            isLoginOpen
                                ? 'bg-slate-700 border-purple-500 text-purple-400'
                                : 'bg-slate-800 border-slate-700 text-slate-300 hover:text-white hover:bg-slate-700'
                        }`}
                    >
                        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                        </svg>
                    </button>

                    <LoginDropdown isOpen={isLoginOpen} onClose={() => setIsLoginOpen(false)} />
                </div>
            </div>
        </nav>
    );
}