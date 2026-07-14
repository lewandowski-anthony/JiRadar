import {useState, useRef, useEffect } from 'react';
import { CHART_LOCALES, type LocaleType } from '@core/constants/locales';
import {useLocale} from "@core/hooks/useLocale";

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
                onClick={() => setIsOpen(!isOpen)}
                className="flex items-center gap-2 px-3 py-1.5 rounded-lg bg-slate-800 border border-slate-700 hover:bg-slate-700 text-sm font-medium text-slate-200 transition-colors uppercase"
            >
                <span>{currentLocale}</span>
                <svg className={`w-4 height-4 transition-transform ${isOpen ? 'rotate-180' : ''}`} fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7" />
                </svg>
            </button>

            {isOpen && (
                <div className="absolute right-0 mt-2 w-40 rounded-xl bg-slate-900 border border-slate-800 shadow-xl z-50 py-1 overflow-hidden">
                    {(Object.keys(CHART_LOCALES) as LocaleType[]).map((loc) => (
                        <button
                            key={loc}
                            onClick={() => {
                                changeLocale(loc);
                                setIsOpen(false);
                            }}
                            className={`w-full text-left px-4 py-2 text-sm transition-colors block ${
                                currentLocale === loc
                                    ? 'bg-purple-950/40 text-purple-400 font-semibold'
                                    : 'text-slate-300 hover:bg-slate-800'
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