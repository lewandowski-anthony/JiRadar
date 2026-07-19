import React, { useState } from 'react';
import { type LocaleType } from '@core/constants/locales';
import { getCookie, setCookie } from '@core/utils/cookies';
import { LocaleContext } from './localContext';

export function LocaleProvider({ children }: Readonly<{ children: React.ReactNode }>) {
    const [locale, setLocale] = useState<LocaleType>(() => {
        const saved = getCookie('jiradar_locale') as LocaleType;
        if (saved) return saved;

        const browserLang = navigator.language.toLowerCase();
        if (browserLang.startsWith('fr')) return 'fr';
        if (browserLang.startsWith('es')) return 'es';
        if (browserLang.startsWith('pt')) return 'pt';
        if (browserLang.startsWith('zh')) return 'zh';
        if (browserLang.startsWith('ja')) return 'ja';

        return 'en';
    });

    const changeLocale = (newLocale: LocaleType) => {
        setLocale(newLocale);
        setCookie('jiradar_locale', newLocale, 365);
    };

    const contextValue = React.useMemo(() => ({
        locale,
        changeLocale
    }), [locale]);

    return (
        <LocaleContext.Provider value={contextValue}>
            {children}
        </LocaleContext.Provider>
    );
}