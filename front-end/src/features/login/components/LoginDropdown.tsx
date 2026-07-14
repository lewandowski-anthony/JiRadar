import { useState, useRef, useEffect } from 'react';
import { useTranslation } from '@core/hooks/useTranslation';

interface LoginDropdownProps {
    isOpen: boolean;
    onClose: () => void;
}

export function LoginDropdown({ isOpen, onClose }: LoginDropdownProps) {
    const t = useTranslation();
    const dropdownRef = useRef<HTMLDivElement>(null);
    const [token, setToken] = useState('');
    const [issueTracker, setIssueTracker] = useState('');

    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
                onClose();
            }
        }
        if (isOpen) {
            document.addEventListener('mousedown', handleClickOutside);
        }
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, [isOpen, onClose]);

    if (!isOpen) return null;

    return (
        <div
            ref={dropdownRef}
            className="absolute right-0 mt-2 w-72 rounded-2xl bg-slate-900 border border-slate-800 p-5 shadow-2xl z-50 animate-fadeIn"
        >
            <form onSubmit={(e) => e.preventDefault()} className="flex flex-col gap-4">
                <div className="flex flex-col gap-1">
                    <label className="text-xs font-semibold text-slate-400 uppercase tracking-wider">
                        {t.loginForm.token}
                    </label>
                    <input
                        type="text"
                        value={token}
                        onChange={(e) => setToken(e.target.value)}
                        className="w-full px-3 py-2 text-sm rounded-xl bg-slate-950 border border-slate-800 text-slate-100 focus:outline-none focus:border-purple-500 transition-colors"
                        placeholder={t.loginForm.token}
                    />
                </div>

                <div className="flex flex-col gap-1">
                    <label className="text-xs font-semibold text-slate-400 uppercase tracking-wider">
                        {t.loginForm.issueTracker}
                    </label>
                    <select
                        value={issueTracker}
                        onChange={(e) => setIssueTracker(e.target.value)}
                        className="w-full px-3 py-2 text-sm rounded-xl bg-slate-950 border border-slate-800 text-slate-100 focus:outline-none focus:border-purple-500 transition-colors"
                    >
                        <option value="">{t.loginForm.selectIssueTracker}</option>
                        <option value="jira">Jira</option>
                        <option value="trello">Trello</option>
                        <option value="github">GitHub</option>
                    </select>
                </div>

                <button
                    type="submit"
                    className="w-full mt-2 py-2.5 rounded-xl bg-purple-600 hover:bg-purple-500 font-semibold text-sm text-white transition-colors shadow-lg shadow-purple-600/10"
                >
                    {t.loginForm.logIn}
                </button>
            </form>
        </div>
    );
}