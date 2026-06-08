// @ts-check
import { defineConfig } from 'astro/config';
import vercel from '@astrojs/vercel/static';
import node from '@astrojs/node';

// https://astro.build/config
export default defineConfig({
    integrations: [],
    output: 'static',
    adapter: node({
        mode: 'standalone',
    }),
    /*adapter: vercel({
        webAnalytics: {
            enabled: true,
        },
    }),*/
});
