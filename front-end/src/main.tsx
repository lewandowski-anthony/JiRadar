import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import {LocaleProvider} from "@core/context/LocaleProvider";
import {AuthProvider} from "@core/context/AuthContext";

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <LocaleProvider>
            <AuthProvider>
                <App />
            </AuthProvider>
        </LocaleProvider>
    </StrictMode>,
)
