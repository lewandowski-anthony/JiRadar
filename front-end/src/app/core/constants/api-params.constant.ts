export const ApiParams = {
  TRACKER: {
    PROJECT_KEYS: 'project_keys',
    START_DATE: 'start_date',
    END_DATE: 'end_date',
    GRANULARITY: 'history_granularity',
  },
  PAGINATION: {
    PAGE: 'page',
    SIZE: 'size',
  },
  HEADERS: {
    PAGE_NUMBER: 'page-number',
    PAGE_SIZE: 'page-size',
    TOTAL_ELEMENTS: 'total-elements',
    TOTAL_PAGES: 'total-pages',
  }
} as const;
