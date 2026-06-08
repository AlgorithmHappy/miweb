import { c as createComponent, a as createAstro, m as maybeRenderHead, b as addAttribute, g as renderSlot, r as renderTemplate } from './astro/server_BiJ_XDgM.mjs';
import 'kleur/colors';
import 'clsx';
/* empty css                                                      */

const $$Astro = createAstro();
const $$SocialButton = createComponent(($$result, $$props, $$slots) => {
  const Astro2 = $$result.createAstro($$Astro, $$props, $$slots);
  Astro2.self = $$SocialButton;
  const { customHref, customTitle } = Astro2.props;
  return renderTemplate`${maybeRenderHead()}<a${addAttribute(customHref, "href")} class="social-btn"${addAttribute(customTitle, "title")} target="_blank" data-astro-cid-a4n7sfki> ${renderSlot($$result, $$slots["svgIcon"])} </a> `;
}, "C:/Users/Marquez Mendez Luis/Documents/Proyectos/miweb/eminent-escape/src/components/SocialButton.astro", void 0);

export { $$SocialButton as $ };
