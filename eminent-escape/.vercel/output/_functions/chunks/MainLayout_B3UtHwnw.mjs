import { c as createComponent, a as createAstro, b as addAttribute, f as renderHead, g as renderSlot, r as renderTemplate } from './astro/server_BiJ_XDgM.mjs';
import 'kleur/colors';
import 'clsx';
/* empty css                         */
/* empty css                         */

const $$Astro = createAstro();
const $$MainLayout = createComponent(($$result, $$props, $$slots) => {
  const Astro2 = $$result.createAstro($$Astro, $$props, $$slots);
  Astro2.self = $$MainLayout;
  const { title, description } = Astro2.props;
  return renderTemplate`<html lang="es-Mx" data-astro-cid-ouamjn2i> <head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><link rel="icon" type="image/x-icon" href="/favicon.ico"><link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"><link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet"><meta name="generator"${addAttribute(Astro2.generator, "content")}><meta name="author" content="Luis Gerardo Márquez Méndez"><title>${title}</title><meta name="description"${addAttribute(description, "content")}>${renderHead()}</head> <body class="bg-primary text-secondary" data-astro-cid-ouamjn2i> ${renderSlot($$result, $$slots["nav"])} <main class="main" data-astro-cid-ouamjn2i> ${renderSlot($$result, $$slots["main"])} </main> ${renderSlot($$result, $$slots["footer"])} </body></html>`;
}, "C:/Users/Marquez Mendez Luis/Documents/Proyectos/miweb/eminent-escape/src/layouts/MainLayout.astro", void 0);

export { $$MainLayout as $ };
