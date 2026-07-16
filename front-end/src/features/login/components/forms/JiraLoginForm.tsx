import React, { useState } from 'react';
import { useTranslation } from "@core/hooks/useTranslation";

interface JiraLoginFormProps {
    onSuccess: () => void;
    onError: (message: string) => void;
    loginFn: (email: string, token: string, tracker: string) => Promise<void>;
    loading: boolean;
}

export function JiraLoginForm({ onSuccess, onError, loginFn, loading }: JiraLoginFormProps) {
    const [email, setEmail] = useState('');
    const [token, setToken] = useState('');
    const t = useTranslation();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await loginFn(email.trim(), token.trim(), 'jira');
            onSuccess();
        } catch (err: unknown) {
            const axiosError = err as { response?: { data?: { message?: string } } };
            onError(axiosError.response?.data?.message || t.jira.invalidToken);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <div className="flex flex-col gap-1">
                <label className="text-xs font-semibold text-text-muted uppercase tracking-wider">
                    {t.common.email}
                </label>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    data-testid="jira-email-input"
                    className="w-full px-3 py-2 text-sm rounded-xl bg-main-bg border border-border-subtle text-text-main focus:outline-none focus:accent-select-focus transition-colors"
                    disabled={loading}
                />
            </div>

            <div className="flex flex-col gap-1">
                <label className="text-xs font-semibold text-text-muted uppercase tracking-wider">
                    {t.jira.token}
                </label>
                <input
                    type="password"
                    value={token}
                    onChange={(e) => setToken(e.target.value)}
                    data-testid="jira-token-input"
                    className="w-full px-3 py-2 text-sm rounded-xl bg-main-bg border border-border-subtle text-text-main focus:outline-none focus:accent-select-focus transition-colors"
                    disabled={loading}
                />
            </div>

            <button
                type="submit"
                disabled={loading}
                data-testid="jira-submit-button"
                className="w-full mt-2 py-2.5 rounded-xl bg-btn-primary hover:bg-btn-primary-hover font-semibold text-sm text-btn-text-main transition-colors disabled:opacity-50 cursor-pointer"
            >
                {loading ? t.common.checking : t.jira.connect}
            </button>
        </form>
    );
}