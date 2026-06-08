import { renderers } from './renderers.mjs';
import { c as createExports } from './chunks/entrypoint_Cs7PkI3c.mjs';
import { manifest } from './manifest_BegFBJ3p.mjs';

const serverIslandMap = new Map();;

const _page0 = () => import('./pages/_image.astro.mjs');
const _page1 = () => import('./pages/blog/posts/curso_de_sql_select_facil_y_rapido.astro.mjs');
const _page2 = () => import('./pages/blog/posts/filtra_datos_con_la_clausula_where.astro.mjs');
const _page3 = () => import('./pages/blog/posts/filtrado_complejo_con_operaciones_logicas_and_or_y_not.astro.mjs');
const _page4 = () => import('./pages/blog/posts/filtrado_de_datos_con_el_operador_in.astro.mjs');
const _page5 = () => import('./pages/blog/posts/filtrado_de_datos_con_el_operador_like.astro.mjs');
const _page6 = () => import('./pages/blog/posts/md/curso_de_sql_select_facil_y_rapido.astro.mjs');
const _page7 = () => import('./pages/blog/posts/md/filtra_datos_con_la_clausula_where.astro.mjs');
const _page8 = () => import('./pages/blog/posts/md/filtrado_complejo_con_operaciones_logicas_and_or_y_not.astro.mjs');
const _page9 = () => import('./pages/blog/posts/md/filtrado_de_datos_con_el_operador_in.astro.mjs');
const _page10 = () => import('./pages/blog/posts/md/filtrado_de_datos_con_el_operador_like.astro.mjs');
const _page11 = () => import('./pages/blog/posts/md/que_es_sql_y_dml_estructura_de_una_consulta_con_select.astro.mjs');
const _page12 = () => import('./pages/blog/posts/que_es_sql_y_dml_estructura_de_una_consulta_con_select.astro.mjs');
const _page13 = () => import('./pages/blog.astro.mjs');
const _page14 = () => import('./pages/contacto.astro.mjs');
const _page15 = () => import('./pages/portafolio.astro.mjs');
const _page16 = () => import('./pages/privacidad/politicas.astro.mjs');
const _page17 = () => import('./pages/prueba.astro.mjs');
const _page18 = () => import('./pages/sobre-mi.astro.mjs');
const _page19 = () => import('./pages/index.astro.mjs');
const pageMap = new Map([
    ["node_modules/astro/dist/assets/endpoint/generic.js", _page0],
    ["src/pages/blog/posts/Curso_de_SQL_SELECT_Facil_y_Rapido.astro", _page1],
    ["src/pages/blog/posts/Filtra_datos_con_la_clausula_Where.astro", _page2],
    ["src/pages/blog/posts/Filtrado_complejo_con_operaciones_logicas_AND_OR_y_NOT.astro", _page3],
    ["src/pages/blog/posts/Filtrado_de_datos_con_el_operador_IN.astro", _page4],
    ["src/pages/blog/posts/Filtrado_de_datos_con_el_operador_LIKE.astro", _page5],
    ["src/pages/blog/posts/md/Curso_de_SQL_SELECT_Facil_y_Rapido.md", _page6],
    ["src/pages/blog/posts/md/Filtra_datos_con_la_clausula_Where.md", _page7],
    ["src/pages/blog/posts/md/Filtrado_complejo_con_operaciones_logicas_AND_OR_y_NOT.md", _page8],
    ["src/pages/blog/posts/md/Filtrado_de_datos_con_el_operador_IN.md", _page9],
    ["src/pages/blog/posts/md/Filtrado_de_datos_con_el_operador_LIKE.md", _page10],
    ["src/pages/blog/posts/md/Que_es_SQL_y_DML_Estructura_de_una_consulta_con_Select.md", _page11],
    ["src/pages/blog/posts/Que_es_SQL_y_DML_Estructura_de_una_consulta_con_Select.astro", _page12],
    ["src/pages/blog/index.astro", _page13],
    ["src/pages/contacto/index.astro", _page14],
    ["src/pages/portafolio/index.astro", _page15],
    ["src/pages/privacidad/politicas.astro", _page16],
    ["src/pages/prueba/index.astro", _page17],
    ["src/pages/sobre-mi/index.astro", _page18],
    ["src/pages/index.astro", _page19]
]);

const _manifest = Object.assign(manifest, {
    pageMap,
    serverIslandMap,
    renderers,
    actions: () => import('./_noop-actions.mjs'),
    middleware: () => import('./_noop-middleware.mjs')
});
const _args = {
    "middlewareSecret": "3f6c4b07-57b2-49fe-976b-0d44473b2a61",
    "skewProtection": false
};
const _exports = createExports(_manifest, _args);
const __astrojsSsrVirtualEntry = _exports.default;

export { __astrojsSsrVirtualEntry as default, pageMap };
