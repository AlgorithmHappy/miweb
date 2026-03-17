# Filtrado de datos con el operador `IN`

**`IN`** es un **operador** que se utiliza con **`WHERE`** para el
**filtrado de datos de cualquier tipo** (int, varchar, date, etc.),
**devolviendo coincidencias** con una lista de datos separados por
**`,`**.

## Consultas

``` sql
SELECT
    *
FROM 
    INSCRIPCIONES
WHERE 
fecha_inscripcion IN (
    TO_DATE('2025-06-01 00:00:00', 'YYYY-MM-DD HH24:mi:ss'),
    TO_DATE('2025-06-02 00:00:00', 'YYYY-MM-DD HH24:mi:ss')
);
-- Aquí "IN(...)" está filtrando los renglones que sí coincidan con las inscripciones en
-- las fechas "2025-06-01 00:00:00" y "2025-06-02 00:00:00". Poner especial atención en
-- la ","

SELECT
    *
FROM 
    INSCRIPCIONES
WHERE 
fecha_inscripcion NOT IN(
    TO_DATE('2025-06-01 00:00:00', 'YYYY-MM-DD HH24:mi:ss'),
    TO_DATE('2025-06-02 00:00:00', 'YYYY-MM-DD HH24:mi:ss')
);
-- También se puede negar el "IN", es decir, devolver los renglones que no estén dentro
-- de nuestra lista declarada en "IN"
```

## Coincidencia de conjuntos de columnas

**`IN`** también puede **filtrar renglones haciendo coincidir conjuntos
de columnas**, como en los siguientes ejemplos:

## Consultas

``` sql
SELECT * FROM PERFILES WHERE (id, usuario_id) IN ( (7, 7), (5, 5), (1, 9) );
-- Aquí "IN" indica que filtre todos los renglones cuyos valores en las columnas
-- id y usuario_id sean exactamente los 3 conjuntos (7, 7), (5, 5) y (1, 9)

SELECT
    *
FROM
    CURSOS
WHERE
(id, titulo, creador_id) IN (
    (10, 'Emprendimiento Tecnológico', 5),
    (3, 'Spring Boot Avanzado', 1),
    (0, 'Base de datos Microsoft', 1)
)
-- Este es un ejemplo de un IN con 3 conjuntos de 3 valores. Se puede hacer con la cantidad de
-- conjuntos que se requieran
```

## `IN` es mucho más poderoso de lo que crees

**`IN`** puede contener la **lista de renglones que te dé como resultado
una consulta SQL**, así como **combinar** dicho resultado con **el truco
de los conjuntos**, como en los siguientes ejemplos:

``` sql
SELECT
    *
FROM
    CURSOS
WHERE
creador_id IN(
    SELECT
        ID
    FROM
        USUARIOS
    WHERE
        nombre = 'Carlos Méndez'
    OR  nombre = 'Ana Torres'
);
-- Aquí le estamos pasando a "IN" una consulta que devuelve los renglones
-- del id de los usuarios, es decir, la lista de los usuarios con el nombre
-- Carlos Méndez y Ana Torres, por lo que la consulta completa devolverá
-- todos los cursos impartidos por estos usuarios

SELECT
    * 
FROM
    PERFILES
WHERE
(usuario_id, sitio_web) IN (
    SELECT 
        id,
        'https://ricardo.db'
    FROM
        USUARIOS
    WHERE 
        email = 'ricardo@example.com'
);
-- En esta consulta estamos devolviendo los renglones de todos los perfiles
-- que coincidan con los "id" de los usuarios y la página "https://ricardo.db"
-- de todos los usuarios que tengan el correo "ricardo@example.com". Cabe
-- resaltar que estamos ocupando IN con el resultado de una lista de un conjunto
-- de datos.
```

## Notas

**Nota**: En este capítulo se utilizó la **función "`TO_DATE(...)`"**,
la cual se utiliza para **datos de tipo fecha** y que será explicada más
adelante.

**Nota**: En la consulta
**`SELECT id, 'https://ricardo.db' FROM USUARIOS WHERE email = 'ricardo@example.com'`**
se devuelve el dato **"https://ricardo.db"**, que siempre será una
devolución constante en la consulta.

## Enlaces relacionados

**GitHub**: <a class="postLinks" href="https://github.com/AlgorithmHappy/Curso-de-SQL-SELECT-Facil-y-Rapido/blob/main/Filtrado%20de%20datos%20con%20el%20operador%20IN.md" target="_blank">Filtrado de datos con el operador IN</a>

**Pagina WEB**: <a class="postLinks" href="http://www.gerardomarquez.dev/blog/posts/Filtrado_de_datos_con_el_operador_IN" target="_blank">Filtrado de datos con el operador IN</a>

## APA

Marquez Mendez, L. G. (2025, 16 de marzo). *Filtrado de datos con el operador IN. Recuperado de <span style="word-break: break-all;">http://www.gerardomarquez.dev/blog/posts/Filtrado_de_datos_con_el_operador_IN</span>