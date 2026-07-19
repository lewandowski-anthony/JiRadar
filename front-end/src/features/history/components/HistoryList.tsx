import { format, parseISO } from 'date-fns';
import type { UserHistoryEventDto } from '@core/models/history';
import type { Page } from '@core/models/pages';
import { useTranslation } from "@core/hooks/useTranslation";

interface HistoryListProps {
    historyPage: Page<UserHistoryEventDto>;
    onPageChange: (newPage: number, newSize: number) => void;
    historyLoading: boolean;
}

export function HistoryList({
                                historyPage,
                                onPageChange,
                                historyLoading,
                            }: Readonly<HistoryListProps>) {
    const content = historyPage.content || [];
    const t = useTranslation();

    const pageMetadata = historyPage?.page || { totalPages: 0, number: 0, totalElements: 0, size: 10 };
    const { totalPages, number, totalElements, size } = pageMetadata;

    return (
        <div className="bg-cardbg p-6 rounded-xl border border-border-subtle mx-auto space-y-4 transition-colors">
            <div className="flex items-center justify-between">
                <h2 className="text-xl font-bold text-text-main">{t.history.title}</h2>

                {/* Sélecteur de taille de page */}
                <div className="flex items-center gap-2 text-xs text-text-muted">
                    <label htmlFor="pageSizeSelect">Afficher</label>
                    <select
                        id="pageSizeSelect"
                        value={size}
                        disabled={historyLoading}
                        onChange={(e) => onPageChange(0, Number(e.target.value))}
                        className="bg-main-bg border border-border-subtle rounded px-2 py-1 text-text-main focus:outline-none focus:border-btn-primary transition-colors cursor-pointer"
                    >
                        <option value={5}>5</option>
                        <option value={10}>10</option>
                        <option value={20}>20</option>
                        <option value={50}>50</option>
                    </select>
                </div>
            </div>

            {content.length === 0 ? (
                <div className="text-center py-8 text-text-muted">
                    <p>{t.history.noEvents}</p>
                </div>
            ) : (
                <div className="overflow-x-auto">
                    <table className="w-full text-left border-collapse text-sm text-text-muted">
                        <thead>
                            <tr className="border-b border-border-subtle text-text-muted font-medium">
                                <th className="py-3 px-4">{t.issue.name}</th>
                                <th className="py-3 px-4">{t.issue.title}</th>
                                <th className="py-3 px-4">{t.issue.author}</th>
                                <th className="py-3 px-4">{t.issue.modificationDate}</th>
                                <th className="py-3 px-4">{t.issue.date}</th>
                            </tr>
                        </thead>
                        <tbody className={`divide-y divide-border-subtle/30 transition-opacity duration-200 ${historyLoading ? 'opacity-40 pointer-events-none' : 'opacity-100'}`}>
                            {content.map((event, index) => (
                                <tr key={`${event.issueKey}-${event.date}-${index}`} className="hover:bg-cardbg-hover transition-colors">
                                    <td className="py-3 px-4 font-mono text-btn-primary font-bold">{event.issueKey}</td>
                                    <td className="py-3 px-4 font-mono text-text-muted">{event.issueSummary}</td>
                                    <td className="py-3 px-4 flex items-center gap-3">
                                        <img
                                            src={event.issueAssignee?.avatarUrl}
                                            alt={event.issueAssignee?.name || 'None'}
                                            className="w-9 h-9 rounded-full border border-border-subtle object-cover shrink-0"
                                        />
                                        <div className="flex flex-col">
                                            <div className="font-medium text-text-main">{event.issueAssignee?.name || 'None'}</div>
                                            <div className="text-xs text-text-muted">{event.issueAssignee?.email || 'None'}</div>
                                        </div>
                                    </td>
                                    <td className="py-3 px-4">
                                        <span className="text-text-muted font-medium">{event.transitionType}</span>
                                    </td>
                                    <td className="py-3 px-4 text-xs text-text-muted">
                                        {event.date ? format(parseISO(event.date), 'dd/MM/yyyy HH:mm') : '—'}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            {totalPages > 1 && (
                <div className="flex items-center justify-between border-t border-border-subtle pt-4 text-xs text-text-muted">
                    <div>
                        {t.page.name} {' '}
                        <span className="font-bold text-text-main">{number + 1}</span>
                        {' '}{t.page.on} {' '}
                        <span className="font-bold text-text-main">{totalPages}</span>
                        <span className="mx-2 text-border-subtle">|</span>
                        {t.page.total} : {totalElements}
                    </div>
                    <div className="flex space-x-2">
                        <button
                            type="button"
                            onClick={() => onPageChange(number - 1, size)}
                            disabled={number === 0 || historyLoading}
                            data-testid="history-prev-btn"
                            className="bg-main-bg border border-border-subtle rounded px-3 py-1.5 text-text-main hover:bg-cardbg-hover transition-colors disabled:opacity-30 disabled:hover:bg-main-bg cursor-pointer disabled:cursor-not-allowed"
                        >
                            {t.page.previous}
                        </button>
                        <button
                            type="button"
                            onClick={() => onPageChange(number + 1, size)}
                            disabled={number === totalPages - 1 || historyLoading}
                            data-testid="history-next-btn"
                            className="bg-main-bg border border-border-subtle rounded px-3 py-1.5 text-text-main hover:bg-cardbg-hover transition-colors disabled:opacity-30 disabled:hover:bg-main-bg cursor-pointer disabled:cursor-not-allowed"
                        >
                            {t.page.next}
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}