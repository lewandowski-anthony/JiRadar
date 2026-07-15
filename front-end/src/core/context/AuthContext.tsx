/* eslint-disable react-refresh/only-export-components */
import React, { createContext, useContext, useState, useEffect, useMemo, useCallback } from 'react';
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

    const logout = useCallback(() => {
        setCookie('jiradar_token', '', -1);
        setCookie('jiradar_tracker', '', -1);
        setUser(null);
        setIsAuthenticated(false);
    }, []);

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
    }, [logout]);

    const jiraLogin = useCallback(async (email: string, token: string, tracker: string) => {
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
    }, [logout]);

    const contextValue = useMemo(() => ({
        user,
        isAuthenticated,
        loading,
        jiraLogin,
        logout
    }), [user, isAuthenticated, loading, jiraLogin, logout]);

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("Use auth must be used within a AuthProvider");
    }
    return context;
}