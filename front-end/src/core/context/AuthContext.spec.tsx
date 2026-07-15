import React, { createContext, useContext, useState, useEffect } from 'react';
import { getCookie, setCookie } from '@core/utils/cookies';
import { JiradarService } from '@core/services/JiradarService';
import type { User } from "@core/models/user";

interface AuthContextType {
    user: User | null;
    isAuthenticated: boolean;
    loading: boolean;
    jiraLogin: (email: string, token: string, tracker: string) => Promise<void>;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
    const [user, setUser] = useState<User | null>(null);
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        async function initAuth() {
            const token = getCookie('jiradar_token');
            const tracker = getCookie('jiradar_tracker') || 'jira';

            if (token) {
                try {
                    const profile = await JiradarService.getMyAccount(tracker);
                    setUser(profile);
                    setIsAuthenticated(true);
                } catch (err) {
                    console.error(err);
                    logout();
                }
            }
            setLoading(false);
        }
        initAuth();
    }, []);

    const jiraLogin = async (email: string, token: string, tracker: string) => {
        setLoading(true);
        const credentials = `${email}:${token}`;
        const base64Token = btoa(credentials);
        setCookie('jiradar_token', base64Token, 1);

        try {
            const profile = await JiradarService.getMyAccount(tracker);
            setCookie('jiradar_token', base64Token, 7);
            setCookie('jiradar_tracker', tracker, 7);
            setUser(profile);
            setIsAuthenticated(true);
        } catch (error) {
            logout();
            throw error;
        } finally {
            setLoading(false);
        }
    };

    // Déclarée en "function" pour profiter du hoisting automatique
    function logout() {
        setCookie('jiradar_token', '', -1);
        setCookie('jiradar_tracker', '', -1);
        setUser(null);
        setIsAuthenticated(false);
    }

    return (
        <AuthContext.Provider value={{ user, isAuthenticated, loading, jiraLogin, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth doit être utilisé au sein d\'un AuthProvider');
    }
    return context;
}