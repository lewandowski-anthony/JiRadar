import { CHART_LOCALES, type TranslationKeys } from '@core/constants/locales';
import { en } from "@core/constants/locales/en";
import {useLocale} from "@core/hooks/useLocale.ts";

export function useTranslation(): TranslationKeys {
    const [locale] = useLocale();
    const currentTranslations = CHART_LOCALES[locale];

    if (!currentTranslations) return en;

    return {
        ...en,
        ...currentTranslations,
        app: { ...en.app, ...currentTranslations.app },
        charts: { ...en.charts, ...currentTranslations.charts },
        error: { ...en.error, ...currentTranslations.error },
        dashboardForm: {
            ...en.dashboardForm,
            ...currentTranslations.dashboardForm,
            granularity: { ...en.dashboardForm?.granularity, ...currentTranslations.dashboardForm?.granularity }
        },
        history: { ...en.history, ...currentTranslations.history },
        issue: { ...en.issue, ...currentTranslations.issue },
        kpi: { ...en.kpi, ...currentTranslations.kpi },
        loginForm: { ...en.loginForm, ...currentTranslations.loginForm },
        page: { ...en.page, ...currentTranslations.page },
        tabs: { ...en.tabs, ...currentTranslations.tabs }
    } as TranslationKeys;
}