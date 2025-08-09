# Filtrado complejo con operaciones lógicas: AND, OR y NOT

Si se **requiere realizar un filtrado más complejo** de las filas arrojadas por tu gestor de bases de datos, se pueden utilizar los operadores lógicos, con los cuales **es posible combinar filtros en la cláusula `WHERE`**.  
Estos operadores son los siguientes:

- **`AND`**: Filtra los datos que **cumplan todas las condiciones dadas**, sin excepción.
- **`OR`**: Filtra los datos que **cumplan al menos una de las condiciones dadas**.
- **`NOT`**: Filtra los datos que **NO cumplan** con las condiciones dadas.


## Consultas
Estos son ejemplos con los cuales puedes entender mejor estos operadores:
```sql
SELECT * FROM CATEGORIAS WHERE id = 1 AND nombre = 'Programación';
-- Aquí se están filtrando los renglones cuyo dato "ID" sea igual a 1
-- y, además, que su nombre sea igual a "Programación". Es decir,
-- si el renglón con id igual a 1 no tuviera el "nombre" de
-- "Programación", esta consulta no devolvería ningún resultado, ya
-- que se deben cumplir todas las condiciones dadas.

SELECT * FROM CURSOS WHERE id > 0 AND titulo = 'Curso de SQL Básico' AND creador_id < 3;
-- Se pueden agregar tantos filtros (condiciones) como se requieran.
-- También puedes utilizar diferentes operadores (=, <, >, !=, <>) en cada condición.

SELECT * FROM PERFILES WHERE usuario_id = 2 OR usuario_id = -1;
-- Con el operador OR se filtran los datos que cumplan al menos una
-- de las condiciones. Igual que con AND, se pueden agregar
-- tantas condiciones como se necesiten y usar cualquier operador (=, <, >, !=, <>).

SELECT * FROM PERFILES WHERE usuario_id = 2 OR usuario_id = 1;
-- El operador OR permite obtener más de un resultado que cumpla alguna de las condiciones.

SELECT * FROM INSCRIPCIONES WHERE NOT curso_id = 1;
-- El operador NOT niega la condición, es decir, filtra todos los
-- renglones que "NO" tengan el "curso_id" igual a 1. No es posible
-- usar múltiples NOT a menos que se combinen con AND u OR.
```

## Combinar operadores
Es posible la combinacion de operadores **`AND`, `OR` y `NOT`** en una misma **cláusula `WHER`** para realizar filtraos con más precisos. Sin embargo, se recomienda el uso de **paréntesis para mejorar la legibilidad** y evitar ambigüedades en la evaluación de las condiciones.

Ejemplos:
```sql
SELECT * FROM INSCRIPCIONES WHERE (curso_id = 1 OR curso_id = 2) AND (usuario_id = 3);
-- Esta consulta filtra las inscripciones para los cursos con ID 1 o 2,
-- pero solo si el usuario tiene un ID igual a 3.

SELECT * FROM CURSOS WHERE (creador_id > 0 AND creador_id < 3) AND (TITULO = 'Diseño UX/UI' OR titulo = 'Spring Boot Avanzado')
-- Esta consulta filtra los cursos que como "creador_id" este entre
-- el 1 y el 2 y que ademas el titulo del curso sea o "Diseño UX/UI"
-- o "Spring Boot Avanzado"
```

## Notas:

**Nota**: El uso de paréntesis es opcional.

**Nota**: Se pueden realizar todas las combinaciones necesarias, sin importar su nivel de complejidad.

**Nota**: El operador mas ocupado y necesario es el **`AND`**

## Ejercicios:

1. Devolver los registros de los **"usuarios"** que tengan como **"Nombre" "Luis Marquez"** ó en todo caso al usuario con **"id"** igual a 3.

2. Devolver los registros de los **"usuarios"** con los correos **"ana@example.com"** y **"ana@example.com"**.

3. Devolver los registros de las **"categorias"** con el **"Nombre"** de **"Programación"** ademas de los **"id" "3"** y **"4"**.

4. Devolver los registros de las **"insripciones"** del **"usuario"** con el **"id"** 3 y el **"curso"** con el **"id"** 1.

5. Devolver los registros de los **"curso"** con el **"titulo"** de **"Curso de SQL Básico"** y el **"curso"** con el **"creador"** del **"id"** 2.

6. Devolver los registros de los **"perfiles"** que no tengan el **"sitio web"** https://anaart.com, que tampoco tengan el **"usuario"** con el **"id"** 2 pero que si sea el **"registro"** con el **"id"** 1

## Enlaces relacionados

**GitHub**: <a class="postLinks" href="https://github.com/AlgorithmHappy/Curso-de-SQL-SELECT-Facil-y-Rapido/blob/main/Filtrado%20complejo%20con%20operaciones%20lógicas%3A%20AND%2C%20OR%20y%20NOT.md" target="_blank">Filtrado complejo con operaciones lógicas: AND, OR y NOT</a>

**Pagina WEB**: <a class="postLinks" href="http://www.gerardomarquez.dev/blog/posts/Filtrado_complejo_con_operaciones_logicas_AND_OR_y_NOT" target="_blank">Filtrado complejo con operaciones lógicas: AND, OR y NOT</a>

## APA

Marquez Mendez, L. G. (2025, 8 de agosto). *Filtrado complejo con operaciones lógicas: AND, OR y NOT*. Recuperado de <span style="word-break: break-all;">http://www.gerardomarquez.dev/blog/posts/Filtrado_complejo_con_operaciones_logicas_AND_OR_y_NOT</span>