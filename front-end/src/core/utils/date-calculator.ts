export function getPastMonthDateStr(): string {
    const date = new Date();
    date.setMonth(date.getMonth() - 1);
    return date.toISOString().split('T')[0];
}

export function getCurrentDateStr(): string {
    return new Date().toISOString().split('T')[0];
}