import Dashboard from '@features/dashboard/Dashboard';

export default function App() {
  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 p-8 w-full">
      <header className="mb-8 text-center">
        <h1 className="text-3xl font-bold tracking-tight text-white mb-2">JiRadar Dashboard</h1>
        <p className="text-slate-400">Analyse des performances et de l'historique des équipes</p>
      </header>

      <main className="w-full">
        <Dashboard />
      </main>
    </div>
  );
}
