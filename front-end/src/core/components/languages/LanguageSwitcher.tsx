import { useState, useRef, useEffect } from 'react';
import { CHART_LOCALES, type LocaleType } from '@core/constants/locales';
import { useLocale } from "@core/hooks/useLocale";

const LANGUAGE_LABELS: Record<LocaleType, string> = {
    en: "English",
    fr: "Français",
    es: "Español",
    pt: "Português",
    zh: "中文",
    ja: "日本語"
};

export function LanguageSwitcher() {
    const [currentLocale, changeLocale] = useLocale();
    const [isOpen, setIsOpen] = useState(false);
    const dropdownRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
                setIsOpen(false);
            }
        }
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    return (
        <div className="relative inline-block text-left" ref={dropdownRef}>
            <button
                type="button"
                onClick={() => setIsOpen(!isOpen)}
                className="flex items-center gap-2 px-3 py-1.5 rounded-lg bg-cardbg border border-border-subtle hover:bg-cardbg-hover text-sm font-medium text-text-main transition-colors uppercase cursor-pointer"
            >
                <span>{currentLocale}</span>
                <svg className={`w-4 h-4 text-text-muted transition-transform ${isOpen ? 'rotate-180' : ''}`} fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7" />
                </svg>
            </button>

            {isOpen && (
                <div className="absolute right-0 mt-2 w-40 rounded-xl bg-cardbg border border-border-subtle shadow-xl z-50 py-1 overflow-hidden transition-colors">
                    {(Object.keys(CHART_LOCALES) as LocaleType[]).map((loc) => (
                        <button
                            type="button"
                            key={loc}
                            onClick={() => {
                                changeLocale(loc);
                                setIsOpen(false);
                            }}
                            className={`w-full text-left px-4 py-2 text-sm transition-colors block cursor-pointer ${
                                currentLocale === loc
                                    ? 'bg-btn-primary-hover text-btn-text-main font-semibold dark:bg-btn-primary dark:text-btn-primary'
                                    : 'text-text-muted hover:bg-cardbg-hover hover:text-text-main'
                            }`}
                        >
                            {LANGUAGE_LABELS[loc]}
                        </button>
                    ))}
                </div>
            )}
        </div>
    );
}