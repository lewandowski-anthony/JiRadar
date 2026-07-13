export function snakeToCamel(str: string): string {
  return str.replace(/([-_][a-z])/g, (group) =>
    group.toUpperCase().replace('-', '').replace('_', '')
  );
}

export function mapKeysToCamel(obj: any): any {
  if (Array.isArray(obj)) {
    return obj.map((v) => mapKeysToCamel(v));
  } else if (obj !== null && obj.constructor === Object) {
    return Object.keys(obj).reduce(
      (result, key) => ({
        ...result,
        [snakeToCamel(key)]: mapKeysToCamel(obj[key]),
      }),
      {}
    );
  }
  return obj;
}
