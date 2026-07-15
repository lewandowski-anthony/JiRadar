import { useState, useRef, useEffect } from 'react';
import { useTranslation } from '@core/hooks/useTranslation';
import { useAuth } from '@core/context/AuthContext';

interface LoginDropdownProps {
    isOpen: boolean;
    onClose: () => void;
}

export function LoginDropdown({ isOpen, onClose }: LoginDropdownProps) {
    const t = useTranslation();
    const { login } = useAuth();
    const dropdownRef = useRef<HTMLDivElement>(null);

    const [token, setToken] = useState('');
    const [issueTracker, setIssueTracker] = useState('jira');
    const [loading, setLoading] = useState(false);
    const [loginError, setLoginError] = useState<string | null>(null);

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

    const handleLoginSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!token.trim() || !issueTracker) {
            setLoginError("Veuillez remplir tous les champs.");
            return;
        }

        setLoading(true);
        setLoginError(null);

        try {
            await login(token.trim(), issueTracker);
            onClose();
        } catch (err: any) {
            const errMsg = err.response?.data?.message || "Token invalide ou back-end indisponible.";
            setLoginError(errMsg);
        } finally {
            setLoading(false);
        }
    };

    if (!isOpen) return null;

    return (
        <div
            ref={dropdownRef}
            className="absolute right-0 mt-2 w-72 rounded-2xl bg-slate-900 border border-slate-800 p-5 shadow-2xl z-50 animate-fadeIn"
        >
            <form onSubmit={handleLoginSubmit} className="flex flex-col gap-4">
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
                        disabled={loading}
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
                        disabled={loading}
                    >
                        <option value="">{t.loginForm.selectIssueTracker}</option>
                        <option value="jira">Jira</option>
                        <option value="trello">Trello</option>
                        <option value="github">GitHub</option>
                    </select>
                </div>

                {loginError && (
                    <div className="text-xs text-red-400 bg-red-950/40 p-2 border border-red-900/50 rounded-lg">
                        {loginError}
                    </div>
                )}

                <button
                    type="submit"
                    disabled={loading}
                    className="w-full mt-2 py-2.5 rounded-xl bg-purple-600 hover:bg-purple-500 font-semibold text-sm text-white transition-colors shadow-lg shadow-purple-600/10 disabled:opacity-50"
                >
                    {loading ? "Vérification..." : t.loginForm.logIn}
                </button>
            </form>
        </div>
    );
}