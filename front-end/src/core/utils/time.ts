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
    const daysMatch = durationStr.match(/(\d+)d/);
    const hoursMatch = durationStr.match(/(\d+)h/);
    const minutesMatch = durationStr.match(/(\d+)m/);

    if (daysMatch) totalHours += parseInt(daysMatch[1], 10) * 24;
    if (hoursMatch) totalHours += parseInt(hoursMatch[1], 10);
    if (minutesMatch) totalHours += parseInt(minutesMatch[1], 10) / 60;

    return parseFloat(totalHours.toFixed(1));
}