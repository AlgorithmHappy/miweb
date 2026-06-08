import { c as createComponent, a as createAstro, m as maybeRenderHead, b as addAttribute, r as renderTemplate } from './astro/server_BiJ_XDgM.mjs';
import 'kleur/colors';
import 'clsx';
/* empty css                         */

const $$Astro = createAstro();
const $$MainHeaderCenter = createComponent(($$result, $$props, $$slots) => {
  const Astro2 = $$result.createAstro($$Astro, $$props, $$slots);
  Astro2.self = $$MainHeaderCenter;
  const { class: className, firstText, secondText } = Astro2.props;
  return renderTemplate`${maybeRenderHead()}<h1${addAttribute(`hero-name ${className}`, "class")} data-astro-cid-6h76ypju> ${firstText} <br data-astro-cid-6h76ypju> <span data-astro-cid-6h76ypju> ${secondText} </span> </h1> `;
}, "C:/Users/Marquez Mendez Luis/Documents/Proyectos/miweb/eminent-escape/src/components/MainHeaderCenter.astro", void 0);

export { $$MainHeaderCenter as $ };
