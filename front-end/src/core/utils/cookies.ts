export function getCookie(name: string): string | null {
    if (typeof document === 'undefined') return null;

    const cookieString = document.cookie
        .split('; ')
        .find((row) => row.startsWith(`${name}=`));

    if (!cookieString) return null;

    const value = decodeURIComponent(cookieString.split('=')[1]);

    return value.replace(/^"|"$/g, '');
}

export function setCookie(name: string, value: string, days: number): void {
    if (typeof document === 'undefined') return;

    let expires = '';
    if (days) {
        const date = new Date();
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        expires = `; expires=${date.toUTCString()}`;
    }

    const cleanValue = value.replace(/^"|"$/g, '');
    document.cookie = `${name}=${encodeURIComponent(cleanValue)}${expires}; path=/; SameSite=Lax; Secure`;
}