import { useState } from 'react';

export function GitHubLoginForm({ onSuccess, onError, loginFn, loading }: any) {
    const [token, setToken] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await loginFn(token.trim(), 'github');
            onSuccess();
        } catch (err: any) {
            onError(err.response?.data?.message || "Jeton GitHub (PAT) invalide.");
        }
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <div className="flex flex-col gap-1">
                <label className="text-xs font-semibold text-slate-400 uppercase tracking-wider">GitHub Personal Access Token</label>
                <input
                    type="password"
                    value={token}
                    onChange={(e) => setToken(e.target.value)}
                    className="w-full px-3 py-2 text-sm rounded-xl bg-slate-950 border border-slate-800 text-slate-100 focus:outline-none focus:border-purple-500 transition-colors"
                    disabled={loading}
                />
            </div>
            <button type="submit" disabled={loading} className="w-full mt-2 py-2.5 rounded-xl bg-purple-600 hover:bg-purple-500 font-semibold text-sm text-white transition-colors disabled:opacity-50">
                {loading ? "Vérification..." : "Se connecter à GitHub"}
            </button>
        </form>
    );
}