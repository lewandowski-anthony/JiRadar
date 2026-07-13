import Dashboard from '@features/dashboard/components/Dashboard';
import { useLocale } from '@core/hooks/useLocale';
import { CHART_LOCALES } from '@core/constants/locales';

export default function App() {
    const locale = useLocale();
    const t = CHART_LOCALES[locale];

    return (
        <div className="min-h-screen bg-slate-950 text-slate-100 p-8 w-full">
            <header className="mb-8 text-center">
                <h1 className="text-3xl font-bold tracking-tight text-white mb-2">{t.app.title}</h1>
                <p className="text-slate-400">{t.app.subtitle}</p>
            </header>

            <main className="w-full">
                <Dashboard />
            </main>
        </div>
    );
}