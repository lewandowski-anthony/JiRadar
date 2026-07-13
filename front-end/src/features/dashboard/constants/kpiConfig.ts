export interface KpiConfig {
  key: string;
  title: string;
  description: string;
  color: string;
  borderColor: string;
  format?: (value: any) => string;
}

export const KPI_CONFIGS: KpiConfig[] = [
  {
    key: 'averageCycleTime',
    title: 'Temps de Cycle Moyen',
    description: 'Le temps moyen nécessaire pour compléter un cycle de travail.',
    color: 'text-blue-400',
    borderColor: 'border-blue-500/20',
  },
  {
    key: 'averageReviewTime',
    title: 'Temps de Revue Moyen',
    description: 'Le temps moyen nécessaire pour effectuer une revue.',
    color: 'text-purple-400',
    borderColor: 'border-purple-500/20',
  },
  {
    key: 'deliverySuccessRate',
    title: 'Taux de Réussite de Livraison',
    description: 'Le pourcentage de livraisons réussies par rapport au total.',
    color: 'text-emerald-400',
    borderColor: 'border-emerald-500/20',
    format: (val) => `${val}%`,
  },
  {
    key: 'numberOfIssueDone',
    title: 'Tickets Terminés',
    description: 'Le nombre total de tickets terminés.',
    color: 'text-emerald-400',
    borderColor: 'border-emerald-500/20',
  },
  {
    key: 'numberOfIssueStarted',
    title: 'Tickets Commencés',
    description: 'Le nombre total de tickets commencés.',
    color: 'text-emerald-400',
    borderColor: 'border-emerald-500/20',
  },
  {
    key: 'numberOfReviewReopened',
    title: 'Retours Review',
    description: 'Le nombre total de retours en revue.',
    color: 'text-red-400',
    borderColor: 'border-red-500/20',
  },
  {
    key: 'parallelIssuesInProgressRate',
    title: 'Tickets en Parallèle (Moyenne)',
    description: 'La moyenne de tickets en parallèle.',
    color: 'text-amber-400',
    borderColor: 'border-amber-500/20',
  },
  {
    key: 'pingPongReviewRate',
    title: 'Taux de Review Ping-Pong',
    description: 'Le pourcentage de tickets en revue ping-pong (allers-retours).',
    color: 'text-red-400',
    borderColor: 'border-red-500/20',
    format: (val) => `${val}%`,
  },
];
