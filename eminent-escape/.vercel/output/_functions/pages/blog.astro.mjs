import { c as createComponent, a as createAstro, r as renderTemplate, b as addAttribute, m as maybeRenderHead, g as renderSlot, e as renderComponent } from '../chunks/astro/server_BiJ_XDgM.mjs';
import 'kleur/colors';
import { $ as $$MainLayout } from '../chunks/MainLayout_B3UtHwnw.mjs';
import { $ as $$SideBar } from '../chunks/SideBar_C7TcFIyF.mjs';
import { $ as $$Footer } from '../chunks/Footer_CCLR_XZ3.mjs';
import 'clsx';
/* empty css                                 */
import { $ as $$MainHeaderCenter } from '../chunks/MainHeaderCenter_C0Lvq0e7.mjs';
export { renderers } from '../renderers.mjs';

var __freeze = Object.freeze;
var __defProp = Object.defineProperty;
var __template = (cooked, raw) => __freeze(__defProp(cooked, "raw", { value: __freeze(cooked.slice()) }));
var _a;
const $$Astro$3 = createAstro();
const $$BlogCard = createComponent(($$result, $$props, $$slots) => {
  const Astro2 = $$result.createAstro($$Astro$3, $$props, $$slots);
  Astro2.self = $$BlogCard;
  const { title, date, duration, description, tags, link, imageSrc, imageAlt } = Astro2.props;
  return renderTemplate(_a || (_a = __template(["", '<div class="blog-card" data-cat="web" data-astro-cid-e3grugc2> <div class="blog-img" data-astro-cid-e3grugc2> <img', "", ' data-astro-cid-e3grugc2> </div> <div class="blog-body" data-astro-cid-e3grugc2> <div class="blog-meta" data-astro-cid-e3grugc2> <div class="container-tags" data-astro-cid-e3grugc2> ', " </div> <span data-astro-cid-e3grugc2>", "</span> <span data-astro-cid-e3grugc2>\u23F1 ", ' min</span> </div> <h3 class="blog-title" data-astro-cid-e3grugc2>', '</h3> <p class="blog-desc" data-astro-cid-e3grugc2>', '</p> <a class="btn-text"', ` data-astro-cid-e3grugc2>
Leer m\xE1s <span class="material-icons-round" style="font-size:16px" data-astro-cid-e3grugc2>arrow_forward</span> </a> </div> </div>  <script>
  window.addEventListener('load', () => {
    document.querySelectorAll('.container-tags').forEach((container) => {
      let speed = 0.3;
      let position = 0;

      function autoScroll() {
        position += speed;
        container.scrollLeft = position;

        if (position >= container.scrollWidth - container.clientWidth) {
          position = 0;
        }

        requestAnimationFrame(autoScroll);
      }

      autoScroll();
    });
  });
<\/script>`])), maybeRenderHead(), addAttribute(imageSrc, "src"), addAttribute(imageAlt, "alt"), tags.map(
    (tag) => renderTemplate`<span class="blog-tag" data-astro-cid-e3grugc2>${tag}</span>`
  ), date, duration, title, description, addAttribute(link, "href"));
}, "C:/Users/Marquez Mendez Luis/Documents/Proyectos/miweb/eminent-escape/src/components/BlogCard.astro", void 0);

const $$Astro$2 = createAstro();
const $$PaginationArrowButton = createComponent(($$result, $$props, $$slots) => {
  const Astro2 = $$result.createAstro($$Astro$2, $$props, $$slots);
  Astro2.self = $$PaginationArrowButton;
  const { class: className, customHref, customTitle } = Astro2.props;
  return renderTemplate`${maybeRenderHead()}<a${addAttribute(`${className} fab`, "class")}${addAttribute(customHref, "href")}${addAttribute(customTitle, "title")} data-astro-cid-idiw7vcs> ${renderSlot($$result, $$slots["arrowIcon"])} <!--<span class="material-icons-round">mail</span> --> </a> `;
}, "C:/Users/Marquez Mendez Luis/Documents/Proyectos/miweb/eminent-escape/src/components/PaginationArrowButton.astro", void 0);

const $$Astro$1 = createAstro();
const $$ListBlogCards = createComponent(async ($$result, $$props, $$slots) => {
  const Astro2 = $$result.createAstro($$Astro$1, $$props, $$slots);
  Astro2.self = $$ListBlogCards;
  const { request } = Astro2.props;
  const res = await fetch(
    `http://localhost:1690/api/blog-cards?page=${request.page}&size=${request.size}&sort=${request.sort}`
  );
  const data = await res.json();
  const cards = data.content;
  const nextPage = `/blog?page=${request.page + 1}&size=${request.size}&sort=${request.sort}`;
  const previusPage = `/blog?page=${request.page - 1 < 0 ? 0 : request.page - 1}&size=${request.size}&sort=${request.sort}`;
  return renderTemplate`${maybeRenderHead()}<section class="grid-container" data-astro-cid-br5kh34f> ${cards.map(
    (card) => renderTemplate`${renderComponent($$result, "BlogCard", $$BlogCard, { "title": card.title, "date": card.date, "duration": card.duration, "description": card.description, "tags": card.tags, "link": card.link, "imageSrc": card.imageSrc, "imageAlt": card.imageAlt, "data-astro-cid-br5kh34f": true })}`
  )} </section> <div class="arrow-container" data-astro-cid-br5kh34f> ${renderComponent($$result, "PaginationArrowButton", $$PaginationArrowButton, { "customHref": previusPage, "customTitle": "P\xE1gina anterior", "data-astro-cid-br5kh34f": true }, { "arrowIcon": async ($$result2) => renderTemplate`<span class="material-icons-round" data-astro-cid-br5kh34f>arrow_back</span>` })} ${renderComponent($$result, "PaginationArrowButton", $$PaginationArrowButton, { "customHref": nextPage, "customTitle": "P\xE1gina siguiente", "data-astro-cid-br5kh34f": true }, { "arrowIcon": async ($$result2) => renderTemplate`<span class="material-icons-round" data-astro-cid-br5kh34f>arrow_forward</span>` })} </div> `;
}, "C:/Users/Marquez Mendez Luis/Documents/Proyectos/miweb/eminent-escape/src/components/ListBlogCards.astro", void 0);

const $$Astro = createAstro();
const prerender = false;
const $$Index = createComponent(($$result, $$props, $$slots) => {
  const Astro2 = $$result.createAstro($$Astro, $$props, $$slots);
  Astro2.self = $$Index;
  const url = new URL(Astro2.request.url);
  const page = Number(url.searchParams.get("page")) || 0;
  const size = Number(url.searchParams.get("size")) || 6;
  const sort = url.searchParams.get("sort") || "date,desc";
  const search = url.searchParams.get("search");
  const request = {
    page,
    size,
    sort,
    search: {
      tag: search,
      title: search
    }
  };
  return renderTemplate`${renderComponent($$result, "MainLayout", $$MainLayout, { "title": "Gerardo M\xE1rquez Dev - Blog", "description": `Bienvenido a mi Blog. En esta secci\xF3n encontrar\xE1s diversos articulos escritos por mi, aportaciones para los desarrolladores, temas interesantes, cursos de programacion
    y demas cosas que te haran mejorar como desarrollador.` }, { "footer": ($$result2) => renderTemplate`${renderComponent($$result2, "Footer", $$Footer, { "slot": "footer" })}`, "main": ($$result2) => renderTemplate`${maybeRenderHead()}<section class="fade-in"> ${renderComponent($$result2, "MainHeaderCenter", $$MainHeaderCenter, { "firstText": "Mi", "secondText": "Blog" })} ${renderComponent($$result2, "ListBlogCards", $$ListBlogCards, { "request": request })} </section>`, "nav": ($$result2) => renderTemplate`${renderComponent($$result2, "SideBar", $$SideBar, { "slot": "nav" })}` })}`;
}, "C:/Users/Marquez Mendez Luis/Documents/Proyectos/miweb/eminent-escape/src/pages/blog/index.astro", void 0);

const $$file = "C:/Users/Marquez Mendez Luis/Documents/Proyectos/miweb/eminent-escape/src/pages/blog/index.astro";
const $$url = "/blog";

const _page = /*#__PURE__*/Object.freeze(/*#__PURE__*/Object.defineProperty({
  __proto__: null,
  default: $$Index,
  file: $$file,
  prerender,
  url: $$url
}, Symbol.toStringTag, { value: 'Module' }));

const page = () => _page;

export { page };
