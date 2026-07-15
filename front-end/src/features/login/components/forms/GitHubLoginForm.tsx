import { useState } from 'react';
import {useTranslation} from "@core/hooks/useTranslation.ts";

interface GitHubLoginFormProps {
    onSuccess: () => void;
    onError: (message: string) => void;
    loginFn: (token: string, tracker: string) => Promise<void>;
    loading: boolean;
}

export function GitHubLoginForm({ onSuccess, onError, loginFn, loading }: GitHubLoginFormProps) {
    const [token, setToken] = useState('');
    const t = useTranslation();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await loginFn(token.trim(), 'github');
            onSuccess();
        } catch (err: unknown) {
            const axiosError = err as { response?: { data?: { message?: string } } };
            onError(axiosError.response?.data?.message || t.github.error.invalidPat);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <div className="flex flex-col gap-1">
                <label className="text-xs font-semibold text-slate-400 uppercase tracking-wider">{t.github.pat}</label>
                <input
                    type="password"
                    value={token}
                    onChange={(e) => setToken(e.target.value)}
                    data-testid="github-token-input"
                    className="w-full px-3 py-2 text-sm rounded-xl bg-slate-950 border border-slate-800 text-slate-100 focus:outline-none focus:border-purple-500 transition-colors"
                    disabled={loading}
                />
            </div>
            <button
                type="submit"
                disabled={loading}
                data-testid="github-submit-button"
                className="w-full mt-2 py-2.5 rounded-xl bg-purple-600 hover:bg-purple-500 font-semibold text-sm text-white transition-colors disabled:opacity-50"
            >
                {loading ? "Vérification..." : "Se connecter à GitHub"}
            </button>
        </form>
    );
}