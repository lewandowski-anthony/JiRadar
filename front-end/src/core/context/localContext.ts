import { createContext } from 'react';
import { type LocaleType } from '@core/constants/locales';

export interface LocaleContextType {
    locale: LocaleType;
    changeLocale: (newLocale: LocaleType) => void;
}

export const LocaleContext = createContext<LocaleContextType | undefined>(undefined);