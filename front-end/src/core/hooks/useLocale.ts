import { useContext } from 'react';
import { LocaleContext } from '@core/context/language/localContext';

export function useLocale() {
    const context = useContext(LocaleContext);
    if (!context) {
        throw new Error('useLocale must be used within a LocaleProvider');
    }
    return [context.locale, context.changeLocale] as const;
}