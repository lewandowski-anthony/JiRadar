import { FormDashboard } from './components/FormDashboard';
import { KpiCard } from '@features/kpis/components/KpiCard.tsx';
import { PeriodicCharts } from '@features/devCharts/components/PeriodicCharts';
import { HistoryList } from '@features/history/components/HistoryList';
import { useDashboard } from './hooks/useDashboard';

export default function Dashboard() {
  const { userMetrics, history, loading, error, fetchDashboardData } = useDashboard();

  const handlePageChange = (newPage: number) => {
    console.log("Changement de page :", newPage);
  };

  return (
    <div className="space-y-8 w-full px-4 md:px-8">
      <FormDashboard onSubmit={fetchDashboardData} isLoading={loading} />

      {error && (
        <div className="bg-red-950/50 border border-red-900 text-red-400 p-4 rounded-xl">
          <p className="text-sm">{error}</p>
        </div>
      )}

      {loading && (
        <div className="text-center py-12 text-slate-400 animate-pulse">
          Chargement...
        </div>
      )}

      {!loading && (
        <>
          {userMetrics && (
            <>
              <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 w-full">
                <KpiCard
                  title="Temps de Cycle Moyen"
                  value={userMetrics.averageCycleTime}
                  color="text-blue-400"
                  description="Le temps moyen nécessaire pour compléter un cycle de travail."
                  borderColor="border-blue-500/20"
                />
                <KpiCard
                  title="Temps de Revue Moyen"
                  value={userMetrics.averageReviewTime}
                  color="text-purple-400"
                  description="Le temps moyen nécessaire pour effectuer une revue."
                  borderColor="border-purple-500/20"
                />
                <KpiCard
                  title="Taux de Réussite de Livraison"
                  value={`${userMetrics.deliverySuccessRate}%`}
                  color="text-emerald-400"
                  description="Le pourcentage de livraisons réussies par rapport au total des livraisons."
                  borderColor="border-emerald-500/20"
                />
                <KpiCard
                  title="Nombre de Tickets Terminés"
                  value={`${userMetrics.numberOfIssueDone}`}
                  color="text-emerald-400"
                  description="Le nombre total de tickets terminés."
                  borderColor="border-emerald-500/20"
                />
                <KpiCard
                  title="Nombre de Tickets Commencés"
                  value={`${userMetrics.numberOfIssueStarted}`}
                  color="text-emerald-400"
                  description="Le nombre total de tickets commencés."
                  borderColor="border-emerald-500/20"
                />
                <KpiCard
                  title="Nombre de Retour Review"
                  value={`${userMetrics.numberOfReviewReopened}`}
                  color="text-red-400"
                  description="Le nombre total de retours en revue."
                  borderColor="border-red-500/20"
                />
                <KpiCard
                  title="Moyenne de ticket en parallele"
                  value={`${userMetrics.parallelIssuesInProgressRate}`}
                  color="text-red-400"
                  description="La moyenne de tickets en parallèle."
                  borderColor="border-red-500/20"
                />
                <KpiCard
                  title="Moyenne de ticket en parallele"
                  value={`${userMetrics.pingPongReviewRate}%`}
                  color="text-red-400"
                  description="Le pourcentage de tickets en revue ping-pong."
                  borderColor="border-red-500/20"
                />
              </div>

              {userMetrics.userMetricsByGranularity && (
                <PeriodicCharts granularityData={userMetrics.userMetricsByGranularity} />
              )}
            </>
          )}

          {history && (
            <HistoryList historyPage={history} onPageChange={handlePageChange} />
          )}
        </>
      )}
    </div>
  );
}
