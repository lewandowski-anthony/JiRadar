import { en } from './en';
import { fr } from './fr';
import { pt } from './pt';
import { es } from './es';
import { zh } from './zh';
import { ja } from './ja';

export const CHART_LOCALES = { en, fr, pt, es, zh, ja };

export type LocaleType = keyof typeof CHART_LOCALES;
export type TranslationKeys = typeof en;