import { useState } from 'react';

interface KpiCardProps {
    title: string;
    value: string | number;
    description?: string;
    color: string;
    borderColor: string;
}

export function KpiCard({ title, value, description, color, borderColor }: Readonly<KpiCardProps>) {
    const [showDesc, setShowDesc] = useState(false);

    return (
        <div className={`bg-cardbg p-6 rounded-xl border ${borderColor} space-y-2 flex flex-col justify-between transition-all duration-200`}>
            <div>
                <h3 className="text-sm font-medium text-text-muted">
                    {title}
                </h3>
                <p className={`text-2xl font-bold tracking-tight ${color} mt-1`}>
                    {value ?? '—'}
                </p>
            </div>

            {description && description.trim() !== "" && (
                <div className="pt-2 border-t border-slate-800/60 mt-2">

                    <button
                        type="button"
                        onClick={() => setShowDesc(!showDesc)}
                        className="w-full flex items-center justify-between text-xs font-medium text-text-muted hover:text-text-main transition-colors focus:outline-none cursor-pointer"
                    >
                        <span className="text-xs font-medium text-text-muted"> Description </span>

                        <svg
                            className={`w-3 h-3 transform transition-transform duration-200 ${showDesc ? 'rotate-180' : ''}`}
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                        >
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M19 9l-7 7-7-7" />
                        </svg>
                    </button>

                    <div
                        className={`grid transition-all duration-200 ease-in-out ${
                            showDesc ? 'grid-rows-[1fr] opacity-100 mt-2' : 'grid-rows-[0fr] opacity-0'
                        }`}
                    >
                        <div className="overflow-hidden">
                            <p className="text-xs italic text-text-muted leading-relaxed bg-main-bg p-2 rounded border border-border-subtle">
                                {description}
                            </p>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}