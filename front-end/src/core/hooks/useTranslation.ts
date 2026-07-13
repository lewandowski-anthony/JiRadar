import { useLocale } from './useLocale';
import { CHART_LOCALES, type TranslationKeys } from '@core/constants/locales';

export function useTranslation(): TranslationKeys {
    const locale = useLocale();
    return CHART_LOCALES[locale];
}