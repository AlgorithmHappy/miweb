// @ts-check
import { defineConfig } from 'astro/config';
import tailWind from '@astrojs/tailwind'
import vercel from '@astrojs/vercel/static';

// https://astro.build/config
export default defineConfig({
    integrations: [tailWind()],
    output: 'static',
    adapter: vercel({
        webAnalytics: {
            enabled: true,
        },
    }),
});
