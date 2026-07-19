export function getPastMonthDateStr(): string {
    const date = new Date();
    date.setMonth(date.getMonth() - 1);
    return date.toISOString().split('T')[0];
}

export function getCurrentDateStr(): string {
    return new Date().toISOString().split('T')[0];
}

export function parseDurationToHours(durationStr: string): number {
    if (!durationStr || durationStr === '0m') return 0;

    let totalHours = 0;

    const daysMatch = /[0-9]+d/.exec(durationStr);
    const hoursMatch = /[0-9]+h/.exec(durationStr);
    const minutesMatch = /[0-9]+m/.exec(durationStr);

    if (daysMatch) totalHours += Number.parseInt(daysMatch[0], 10) * 24;
    if (hoursMatch) totalHours += Number.parseInt(hoursMatch[0], 10);
    if (minutesMatch) totalHours += Number.parseInt(minutesMatch[0], 10) / 60;

    return Number.parseFloat(totalHours.toFixed(1));
}