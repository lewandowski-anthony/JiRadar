export function snakeToCamel(str: string): string {
  return str.replace(/([-_][a-z])/g, (group) =>
    group.toUpperCase().replace('-', '').replace('_', '')
  );
}

export function mapKeysToCamel(obj: unknown): unknown {
  if (Array.isArray(obj)) {
    return obj.map((v) => mapKeysToCamel(v));
  }
    if (obj !== null && typeof obj === 'object') {
        const inspectableObj = obj as Record<string, unknown>;

        return Object.keys(inspectableObj).reduce<Record<string, unknown>>(
            (result, key) => ({
                ...result,
                [snakeToCamel(key)]: mapKeysToCamel(inspectableObj[key]),
            }),
            {}
        );
    }
  return obj;
}
