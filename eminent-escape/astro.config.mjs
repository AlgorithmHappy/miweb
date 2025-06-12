// @ts-check
import { defineConfig } from 'astro/config';
import tailWind from '@astrojs/tailwind'

// https://astro.build/config
export default defineConfig({
    integrations: [tailWind()]
});
