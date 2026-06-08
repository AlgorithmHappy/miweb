import { c as createComponent, m as maybeRenderHead, d as renderScript, b as addAttribute, r as renderTemplate } from './astro/server_BiJ_XDgM.mjs';
import 'kleur/colors';
import 'clsx';
/* empty css                         */

const $$SideBar = createComponent(($$result, $$props, $$slots) => {
  const navItems = [
    { customHref: "/", icon: "home", navText: "Inicio" },
    { customHref: "/sobre-mi", icon: "person", navText: "Sobre m\xED" },
    { customHref: "/blog?page=0&size=6&sort=date,desc", icon: "article", navText: "Blog" },
    { customHref: "/portafolio", icon: "work", navText: "Portafolio" },
    { customHref: "/contacto", icon: "mail", navText: "Contacto" }
  ];
  return renderTemplate`${maybeRenderHead()}<aside class="sidebar" id="sidebar" data-astro-cid-f5u63ayj> <div class="sidebar-header" id="sidebar-header" data-astro-cid-f5u63ayj> <div class="sidebar-logo" data-astro-cid-f5u63ayj>&lt;/&gt;</div> <span class="sidebar-brand" data-astro-cid-f5u63ayj>Gerardo Marquez Dev</span> <button id="toggle-btn" class="toggle-btn" title="Toggle sidebar" data-astro-cid-f5u63ayj> <span class="material-icons-round" id="toggle-icon" data-astro-cid-f5u63ayj>close</span> </button> </div> <ul class="nav-list" data-astro-cid-f5u63ayj> ${navItems.map(
    (item) => renderTemplate`<li class="nav-item" data-astro-cid-f5u63ayj> <a${addAttribute(item.customHref, "href")} class="nav-link" data-astro-cid-f5u63ayj> <span class="material-icons-round" data-astro-cid-f5u63ayj>${item.icon}</span> <span class="nav-text" data-astro-cid-f5u63ayj>${item.navText}</span> </a> </li>`
  )} </ul> </aside>  ${renderScript($$result, "C:/Users/Marquez Mendez Luis/Documents/Proyectos/miweb/eminent-escape/src/components/SideBar.astro?astro&type=script&index=0&lang.ts")}`;
}, "C:/Users/Marquez Mendez Luis/Documents/Proyectos/miweb/eminent-escape/src/components/SideBar.astro", void 0);

export { $$SideBar as $ };
