import type { Plugin } from 'vite';
import os from 'os';

const clr = {
    green: (text: string) => `\x1b[92m${text}\x1b[0m`,
    gray: (text: string) => `\x1b[90m${text}\x1b[0m`,
    white: (text: string) => `\x1b[37m${text}\x1b[0m`,
    cyan: (text: string) => `\x1b[36m${text}\x1b[0m`,
    yellow: (text: string) => `\x1b[33m${text}\x1b[0m`,
    purple: (text: string) => `\x1b[95m${text}\x1b[0m`
};

export function jiradarBannerPlugin(): Plugin {
    return {
        name: 'vite-plugin-jiradar-banner',
        configureServer(server) {
            const asciiTitle = `
     ██╗██╗██████╗  █████╗ ██████╗  █████╗ ██████╗ 
     ██║██║██╔══██╗██╔══██╗██╔══██╗██╔══██╗██╔══██╗
     ██║██║██████╔╝███████║██║  ██║███████║██████╔╝
██╗  ██║██║██╔══██╗██╔══██║██║  ██║██╔══██║██╔══██╗
╚█████╔╝██║██║  ██║██║  ██║██████╔╝██║  ██║██║  ██║
 ╚════╝ ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝`;

            const appVersion = process.env.VITE_APP_VERSION || '0.0.1-SNAPSHOT';
            const apiBaseUrl = process.env.VITE_API_BASE_URL || 'http://localhost:8080';
            const environment = server.config.mode || 'development';
            const viteVersion = server.config.define?.['__VITE_VERSION__'] || '8.1.4';

            const nodeVersion = process.version;
            const osPlatform = os.platform();
            const osRelease = os.release();
            const cpuArchitecture = os.arch();
            const totalMemoryGB = Math.round(os.totalmem() / (1024 * 1024 * 1024));

            const protocol = server.config.server.https ? 'https' : 'http';
            const port = server.config.server.port || 5173;
            const localUrl = `${protocol}://localhost:${port}/`;

            console.clear();
            console.log(clr.green(asciiTitle));
            console.log(clr.gray(' ───  ENGINEERING PERFORMANCE & RADAR DASHBOARD  ───\n'));

            console.log(clr.purple(' [ Application Context ]'));
            console.log(`  ├── ${clr.gray('Core System     :')} ${clr.white(`JiRadar Front v${appVersion}`)}`);
            console.log(`  ├── ${clr.gray('Framework       :')} ${clr.white('React 19')} ${clr.gray(`+ Vite v${viteVersion}`)}`);
            console.log(`  ├── ${clr.gray('Environment     :')} ${clr.yellow(environment.toUpperCase())}`);
            console.log(`  └── ${clr.gray('API Endpoint    :')} ${clr.cyan(apiBaseUrl)}`);
            console.log();

            console.log(clr.yellow(' [ System & Engine Landscape ]'));
            console.log(`  ├── ${clr.gray('Node.js Engine  :')} ${clr.white(nodeVersion)}`);
            console.log(`  ├── ${clr.gray('Platform/Arch   :')} ${clr.white(`${osPlatform} (${cpuArchitecture})`)}`);
            console.log(`  ├── ${clr.gray('OS Kernel Rel.  :')} ${clr.white(osRelease)}`);
            console.log(`  └── ${clr.gray('System Memory   :')} ${clr.white(`${totalMemoryGB} GB RAM`)}`);
            console.log();

            console.log(clr.cyan(' [ Runtime Network Bounds ]'));
            console.log(`  └── ${clr.gray('Local URL Access:')} ${clr.green(localUrl)}`);
            console.log();

            console.log(` ${clr.green('✔')} ${clr.white('JiRadar Web Dashboard is operational and listening for user interactions.')}`);
            console.log(clr.gray(' ─────────────────────────────────────────────────────────'));
            console.log();
        }
    };
}