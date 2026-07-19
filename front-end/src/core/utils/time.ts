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

    const daysMatch = /\b\d+d\b/.exec(durationStr);
    const hoursMatch = /\b\d+h\b/.exec(durationStr);
    const minutesMatch = /\b\d+m\b/.exec(durationStr);

    if (daysMatch) totalHours += Number.parseInt(daysMatch[0], 10) * 24;
    if (hoursMatch) totalHours += Number.parseInt(hoursMatch[0], 10);
    if (minutesMatch) totalHours += Number.parseInt(minutesMatch[0], 10) / 60;

    return Number.parseFloat(totalHours.toFixed(1));
}