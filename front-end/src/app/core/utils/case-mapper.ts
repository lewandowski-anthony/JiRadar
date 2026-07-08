export function snakeToCamel(str: string): string {
  return str.replace(/_([a-z])/g, (_, letter) => letter.toUpperCase());
}

export function autoMapSnakeToCamel<T>(obj: any): T {
  if (Array.isArray(obj)) {
    return obj.map(v => autoMapSnakeToCamel(v)) as unknown as T;
  } else if (obj !== null && obj.constructor === Object) {
    return Object.keys(obj).reduce((result, key) => {
      const camelKey = snakeToCamel(key);
      result[camelKey] = autoMapSnakeToCamel(obj[key]);
      return result;
    }, {} as any) as T;
  }
  return obj;
}
