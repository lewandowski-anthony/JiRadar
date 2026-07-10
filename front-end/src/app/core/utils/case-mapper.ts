export function snakeToCamel(str: string): string {
  return str.replace(/_([a-z])/g, (_, letter) => letter.toUpperCase());
}

function mapArray(arr: unknown[]): unknown[] {
  return arr.map(autoMapSnakeToCamel);
}

function mapObject(obj: Record<string, unknown>): Record<string, unknown> {
  const result: Record<string, unknown> = {};
  for (const [key, value] of Object.entries(obj)) {
    result[snakeToCamel(key)] = autoMapSnakeToCamel(value);
  }
  return result;
}

export function autoMapSnakeToCamel<T>(data: T): T {
  if (Array.isArray(data)) {
    return mapArray(data) as T;
  }

  if (data !== null && typeof data === 'object') {
    return mapObject(data as Record<string, unknown>) as T;
  }

  return data;
}
