import { useState } from 'react';
import { type LocaleType } from '@core/constants/locales';

export function useLocale(): LocaleType {
    const [locale] = useState<LocaleType>(() => {
        if (typeof navigator !== 'undefined') {
            const browserLanguage = navigator.language.toLowerCase();

            if (browserLanguage.startsWith('fr')) return 'fr';
            if (browserLanguage.startsWith('pt')) return 'pt';
            if (browserLanguage.startsWith('es')) return 'es';
            if (browserLanguage.startsWith('zh')) return 'zh';
            if (browserLanguage.startsWith('ja')) return 'ja';
        }
        return 'en';
    });

    return locale;
}