import { format, parseISO } from 'date-fns';
import type { UserHistoryEventDto } from '@core/models/history';
import type { Page } from '@core/models/pages';
import {useTranslation} from "@core/hooks/useTranslation";

export function HistoryList({
                              historyPage,
                              onPageChange,
                            }: {
  historyPage: Page<UserHistoryEventDto>;
  onPageChange: (newPage: number) => void;
}) {
  const content = historyPage.content || [];
  const t = useTranslation();

  const pageMetadata = historyPage?.page || { totalPages: 0, number: 0, totalElements: 0, size: 10 };
  const { totalPages, number, totalElements } = pageMetadata;

  return (
    <div className="bg-slate-900 p-6 rounded-xl border border-slate-800 mx-auto space-y-4">
      <h2 className="text-xl font-bold text-white">{t.history.title}</h2>

      {content.length === 0 ? (
        <div className="text-center py-8 text-slate-500">
          <p>{t.history.noEvents}</p>
        </div>
      ) : (
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse text-sm text-slate-300">
            <thead>
              <tr className="border-b border-slate-800 text-slate-400 font-medium">
                <th className="py-3 px-4">{t.issue.name}</th>
                <th className="py-3 px-4">{t.issue.title}</th>
                <th className="py-3 px-4">{t.issue.author}</th>
                <th className="py-3 px-4">{t.issue.modificationDate}</th>
                <th className="py-3 px-4">{t.issue.date}</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-500/50">
              {content.map((event) => (
                <tr key={event.issueKey} className="hover:bg-slate-950/40 transition-colors">
                  <td className="py-3 px-4 font-mono text-blue-400">{event.issueKey}</td>
                  <td className="py-3 px-4 font-mono text-blue-200">{event.issueSummary}</td>
                  <td className="py-3 px-4">
                    <div className="font-medium text-slate-200">{event.issueAssignee?.name}</div>
                    <div className="text-xs text-slate-500">{event.issueAssignee?.email || 'Système'}</div>
                  </td>
                  <td className="py-3 px-4">
                    <span className="text-slate-400 font-medium">{event.transitionType}</span>
                  </td>
                  <td className="py-3 px-4 text-xs text-slate-400">
                    {event.date ? format(parseISO(event.date), 'dd/MM/yyyy HH:mm') : '—'}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {totalPages > 1 && (
        <div className="flex items-center justify-between border-t border-slate-800 pt-4 text-xs text-slate-400">
          <div>
              {t.page.name} <span className="font-bold text-slate-200">{number + 1}</span>
              {t.page.on} {' '}
            <span className="font-bold text-slate-200">{totalPages}</span>
            <span className="mx-2 text-slate-700">|</span> {t.page.total} : {totalElements}
          </div>
          <div className="flex space-x-2">
            <button
              onClick={() => onPageChange(number - 1)}
              disabled={number === 0}
              className="bg-slate-950 border border-slate-800 rounded px-3 py-1.5 hover:bg-slate-800 transition-colors disabled:opacity-30 disabled:hover:bg-slate-950"
            >
                {t.page.previous}
            </button>
            <button
              onClick={() => onPageChange(number + 1)}
              disabled={number === totalPages - 1}
              className="bg-slate-950 border border-slate-800 rounded px-3 py-1.5 hover:bg-slate-800 transition-colors disabled:opacity-30 disabled:hover:bg-slate-950"
            >
              {t.page.next}
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
