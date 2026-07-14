import Dashboard from '@features/dashboard/components/Dashboard';
import { Navbar } from '@core/components/menus/Navbar';

export default function App() {
    return (
        <div className="min-h-screen bg-slate-950 text-slate-100 w-full flex flex-col">
            <Navbar />
            <main className="w-full p-6 md:p-8 flex-1">
                <Dashboard />
            </main>
        </div>
    );
}