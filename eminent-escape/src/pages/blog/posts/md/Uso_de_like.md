# Uso de like

**`LIKE`** es un **operador** que se utiliza con **`WHERE`** para el **filtrado de datos de tipo texto** (varchar, string)
**haciendolos coincidir con algun patron de caracteres**, utilizando los siguientes *"comodines"*.

- **`%`**: Indica que en el patron de texto puede haber 0 mas caracteres cualesquiera.
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
-- Devuelve todos los rneglones donde la columna "email" tenga un caracter
-- cualquiera entre "carlos" y "example.com"

```

## Escapar caracteres `%` y `_`

Puedes **escapar los caracteres `%`** y **`_`** si en tu patron de busqueda **necesitas dichos caracteres** como caracteres reales y no
como *"comodines"* con **`ESCAPE`** y un caracter con el que indiques que quieres *"escapar"*.

Ejemplo:

```sql

SELECT * FROM cursos WHERE titulo LIKE '_Boot\%' ESCAPE '\';
-- Aqui indicas que quieres incluir el caracter "%" como caracter real y no como
-- comodin en tu patron de busqueda ya que es el caracter que le sigue despues del
-- caracter "\", por eso es que no devuelve nada

```

## Notas

**Nota**: El uso de mayusculas y minusculas afecta al patron que quieras definir.

