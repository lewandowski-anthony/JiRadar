import react from '@vitejs/plugin-react';
import tailwindcss from '@tailwindcss/vite';
import { defineConfig } from 'vitest/config';
import {jiradarBannerPlugin} from "./vite-banner.plugin.js";

export default defineConfig({
    base: './',
    plugins: [
        tailwindcss(),
        react(),
        jiradarBannerPlugin()
    ],
    resolve: {
        tsconfigPaths: true
    },
    test: {
        globals: true,
        environment: 'jsdom',
        setupFiles: './src/test/setup.ts',
        coverage: {
            provider: 'v8',
            reporter: ['text', 'json', 'html'],
            thresholds: {
                statements: 80,
                branches: 80,
                functions: 80,
                lines: 80,
            },
            include: ['src/**/*.{ts,tsx}'],
            exclude: [
                'node_modules/**',
                'dist/**',
                'src/main.tsx',
                'src/test/**',
                'eslint.config.js',
                'vite.config.ts',
                'src/core/models/**',
                '**/*.spec.{ts,tsx}',
                'src/core/constants/locales/**',
            ]
        }
    }
});
