import { useState, useRef, useEffect } from 'react';
import { useTranslation } from '@core/hooks/useTranslation';
import { useAuth } from '@core/context/AuthContext';
import { JiraLoginForm } from './forms/JiraLoginForm';
import { GitHubLoginForm } from './forms/GitHubLoginForm.tsx';

interface LoginDropdownProps {
    isOpen: boolean;
    onClose: () => void;
}

export function LoginDropdown({ isOpen, onClose }: LoginDropdownProps) {
    const t = useTranslation();
    const { jiraLogin } = useAuth();
    const dropdownRef = useRef<HTMLDivElement>(null);

    const [issueTracker, setIssueTracker] = useState('jira');
    const [loading] = useState(false);
    const [loginError, setLoginError] = useState<string | null>(null);

    useEffect(() => {
        if (isOpen) setLoginError(null);
    }, [isOpen, issueTracker]);

    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
                onClose();
            }
        }
        if (isOpen) document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, [isOpen, onClose]);

    if (!isOpen) return null;

    return (
        <div ref={dropdownRef} className="absolute right-0 top-full mt-2 w-72 rounded-2xl bg-slate-900 border border-slate-800 p-5 shadow-2xl z-50 animate-fadeIn flex flex-col gap-4">
            <div className="flex flex-col gap-1">
                <label className="text-xs font-semibold text-slate-400 uppercase tracking-wider">
                    {t.loginForm.issueTracker}
                </label>
                <select
                    value={issueTracker}
                    onChange={(e) => setIssueTracker(e.target.value)}
                    className="w-full px-3 py-2 text-sm rounded-xl bg-slate-950 border border-slate-800 text-slate-100 focus:outline-none focus:border-purple-500 transition-colors"
                    disabled={loading}
                >
                    <option value="jira">Jira</option>
                    <option value="github">GitHub</option>
                </select>
            </div>

            {loginError && (
                <div className="text-xs text-red-400 bg-red-950/40 p-2 border border-red-900/50 rounded-lg">
                    {loginError}
                </div>
            )}

            {issueTracker === 'jira' && (
                <JiraLoginForm
                    loginFn={jiraLogin}
                    loading={loading}
                    onSuccess={onClose}
                    onError={setLoginError}
                />
            )}

            {issueTracker === 'github' && (
                <GitHubLoginForm
                    loginFn={jiraLogin}
                    loading={loading}
                    onSuccess={onClose}
                    onError={setLoginError}
                />
            )}
        </div>
    );
}