import react from '@vitejs/plugin-react';
import tsconfigPaths from 'vite-tsconfig-paths';
import tailwindcss from '@tailwindcss/vite';
import { defineConfig } from 'vitest/config';

export default defineConfig({
    plugins: [
        tailwindcss(),
        react(),
        tsconfigPaths()
    ],
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
            exclude: [
                'node_modules/**',
                'dist/**',
                'src/main.tsx',
                'src/test/**',
                'eslint.config.js',
                'vite.config.ts',
                'src/core/models/**',
                'src/core/constants/locales/**',
            ]
        }
    }
});
