import React, {useState} from 'react';
import type {DashboardFilters} from '@core/models/dashboard.ts';
import {getCurrentDateStr, getPastMonthDateStr} from "@core/utils/time.ts";
import type {TranslationKeys} from "@core/constants/locales";
import {useTranslation} from "@core/hooks/useTranslation.ts";

interface DashboardFormProps {
    onSubmit: (filters: DashboardFilters) => void;
    isLoading: boolean;
}

export function FormDashboard({onSubmit, isLoading}: DashboardFormProps) {

    const t: TranslationKeys = useTranslation();
    const [filters, setFilters] = useState<DashboardFilters>({
        projectKey: '',
        startDate: getPastMonthDateStr(),
        endDate: getCurrentDateStr(),
        granularity: 'NONE',
    });

    const [error, setError] = useState<string | null>(null);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {name, value} = e.target;
        setFilters((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (!filters.projectKey || filters.projectKey.trim().length < 2) {
            setError(t.error?.projectCodeRequired || "Project code required");
            return;
        }
        setError(null);
        onSubmit(filters);
    };

    return (
        <form onSubmit={handleSubmit} className="bg-slate-900 p-6 rounded-xl border border-slate-800 space-y-4 mx-auto">
            <div className="grid grid-cols-1 md:grid-cols-4 gap-4">

                <div className="flex flex-col">
                    <label className="text-slate-400 text-sm mb-1" htmlFor="projectKey">{t.dashboardForm.projectCode}</label>
                    <input
                        type="text"
                        id="projectKey"
                        name="projectKey"
                        placeholder={t.dashboardForm.projectCode}
                        value={filters.projectKey}
                        onChange={handleChange}
                        className="bg-slate-950 border border-slate-800 rounded p-2 text-slate-200 focus:outline-none focus:border-blue-500"
                    />
                </div>

                <div className="flex flex-col">
                    <label className="text-slate-400 text-sm mb-1" htmlFor="startDate">{t.dashboardForm.startDate}</label>
                    <input
                        type="date"
                        id="startDate"
                        name="startDate"
                        value={filters.startDate}
                        onChange={handleChange}
                        className="bg-slate-950 border border-slate-800 rounded p-2 text-slate-200 focus:outline-none focus:border-blue-500"
                    />
                </div>

                <div className="flex flex-col">
                    <label className="text-slate-400 text-sm mb-1" htmlFor="endDate">{t.dashboardForm.endDate}</label>
                    <input
                        type="date"
                        id="endDate"
                        name="endDate"
                        value={filters.endDate}
                        onChange={handleChange}
                        className="bg-slate-950 border border-slate-800 rounded p-2 text-slate-200 focus:outline-none focus:border-blue-500"
                    />
                </div>

                <div className="flex flex-col">
                    <label className="text-slate-400 text-sm mb-1" htmlFor="granularity">{t.dashboardForm.granularity.title}</label>
                    <select
                        id="granularity"
                        name="granularity"
                        value={filters.granularity}
                        onChange={handleChange}
                        className="bg-slate-950 border border-slate-800 rounded p-2 text-slate-200 focus:outline-none focus:border-blue-500"
                    >
                        <option value="NONE">{t.dashboardForm.granularity.noGranularity}</option>
                        <option value="DAY">{t.dashboardForm.granularity.daily}</option>
                        <option value="WEEK">{t.dashboardForm.granularity.weekly}</option>
                        <option value="MONTH">{t.dashboardForm.granularity.monthly}</option>
                        <option value="YEAR">{t.dashboardForm.granularity.yearly}</option>
                    </select>
                </div>
            </div>

            {error && <p className="text-red-500 text-sm">{error}</p>}

            <div className="flex justify-end">
                <button
                    type="submit"
                    disabled={isLoading}
                    className="bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-6 rounded transition-colors disabled:opacity-50"
                >
                    {isLoading ? t.app.loading : t.dashboardForm.updateDashboard}
                </button>
            </div>
        </form>
    );
}
