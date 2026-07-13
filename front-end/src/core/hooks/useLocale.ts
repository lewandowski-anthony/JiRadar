import { useState } from 'react';
import { type LocaleType } from '@core/constants/locales';
import { getCookie, setCookie } from '@core/utils/cookies';

export function useLocale(): [LocaleType, (locale: LocaleType) => void] {
    const [locale] = useState<LocaleType>(() => {
        if (typeof window !== 'undefined') {
            const saved = getCookie('jiradar_locale') as LocaleType;
            if (saved) return saved;

            const browserLanguage = navigator.language.toLowerCase();
            if (browserLanguage.startsWith('fr')) return 'fr';
            if (browserLanguage.startsWith('es')) return 'es';
            if (browserLanguage.startsWith('pt')) return 'pt';
            if (browserLanguage.startsWith('zh')) return 'zh';
            if (browserLanguage.startsWith('ja')) return 'ja';
        }
        return 'en';
    });

    const changeLocale = (newLocale: LocaleType) => {
        setCookie('jiradar_locale', newLocale, 365);
        window.location.reload();
    };

    return [locale, changeLocale];
}