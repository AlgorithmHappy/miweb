# Filtrado de datos con el operador `LIKE`

**`LIKE`** es un **operador** que se utiliza con **`WHERE`** para el **filtrado de datos de tipo texto** (varchar, string)
**haciendolos coincidir con algun patron de caracteres**, utilizando los siguientes *"comodines"*.

- **`%`**: Indica que en el patron de texto puede haber 0 ó mas caracteres cualesquiera.
- **`_`**: Indica que en el patron de texto puede haber solo un caracter cualquiera.

## Consultas

```sql

SELECT * FROM CATEGORIAS WHERE nombre LIKE '%end';
-- Aqui filtramos todos los datos de la columna "nombre" que terminen en "end" ya
-- que con el "%" indicamos que antes de "end" puede haber 0 o mas caracteres
-- cualesquiera

SELECT * FROM PERFILES WHERE sitio_web LIKE '%tps://%';
-- Esta consulta devuelve todos los renglones donde la columna "sitio_web" contenga
-- "tps://" en medio del texto

SELECT * FROM USUARIOS WHERE email LIKE 'carlos_example.com'; 
-- Devuelve todos los renglones donde la columna "email" tenga un caracter
-- cualquiera entre "carlos" y "example.com"

```

## Escapar caracteres `%` y `_`

Puedes **escapar los caracteres "`%`"** y **"`_`"** si en tu patron de busqueda **necesitas dichos caracteres** como caracteres reales y no
como *"comodines"* con **`ESCAPE`** y un caracter con el que indiques que quieres *"escapar"*.

Ejemplo:

```sql

SELECT * FROM cursos WHERE titulo LIKE '_Boot\%' ESCAPE '\';
-- Aqui indicas que quieres incluir el caracter "%" como caracter real y no como
-- comodin en tu patron de busqueda ya que es el caracter que le sigue despues del
-- caracter "\", por eso es que la consulta no devuelve nada

```

## Notas

**Nota**: El uso de mayusculas y minusculas afecta al patron que quieras definir.

**Nota**: Con este operador tambien se pueden utilizar tipos de datos **`CLOB`** como por ejemplo en la columna **"`BIOGRAFIA`"** de la tabla **"`PERFILES`"**.

## Ejercicios:

1. Devolver los **usuarios** que almenos tengan un acento en su nombre.

2. Devolver todos los **usuarios** que tengan **"ia"** en su correo electronico.

3. Devolver todos los **cursos** que tengan **"UX/UI"**.

4. Devolver todos los **perfiles** que en su sitio web solo tengan su ".dominio" con 2 letras.

5. Devolver todos los **cursos** que en la descripcion tengan **"spring"** no importando si este en mayusculas o minusculas.

## Enlaces relacionados

**GitHub**: <a class="postLinks" href="https://github.com/AlgorithmHappy/Curso-de-SQL-SELECT-Facil-y-Rapido/blob/main/Filtrado%20de%20datos%20con%20el%20operador%20LIKE.md" target="_blank">Filtrado de datos con el operador LIKE</a>

**Pagina WEB**: <a class="postLinks" href="http://www.gerardomarquez.dev/blog/posts/Filtrado_de_datos_con_el_operador_LIKE" target="_blank">Filtrado de datos con el operador LIKE</a>

## APA

Marquez Mendez, L. G. (2025, 8 de agosto). *Filtrado de datos con el operador LIKE. Recuperado de <span style="word-break: break-all;">http://www.gerardomarquez.dev/blog/posts/Filtrado_de_datos_con_el_operador_LIKE</span>