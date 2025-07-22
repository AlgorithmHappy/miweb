# ¿Que es SQL y DML?. Estructura de una consulta con "Select"
## Definicion de SQL y DML
**SQL** son las siglas en inglés de *Structured Query Language*, que se traduce al español como **Lenguaje de Consulta Estructurado**. Es un lenguaje de programación *declarativo* que permite manipular grandes volúmenes de datos almacenados en una base de datos, mediante instrucciones específicas.
**SQL** está compuesto por cuatro subconjuntos de comandos: **DML, DDL, DCL y TCL**. El subconjunto más utilizado es el **DML**.
**DML** significa *Data Manipulation Language (Lenguaje de Manipulación de Datos)*. Este conjunto de comandos se utiliza para consultar y modificar los datos almacenados en las tablas de una base de datos, lo que lo convierte en el más usado en el día a día del trabajo con bases de datos.
Los principales comandos de **DML** son:
- **SELECT**: *para consultar datos de una tabla*
- **INSERT**: *para insertar nuevos registros*
- **UPDATE**: *para actualizar datos existentes*
- **DELETE**: *para eliminar registros*
En este curso nos enfocaremos exclusivamente en el comando **SELECT** y en todo lo que se puede lograr con él.

### Glosario
- **Lenguaje de programacion**: Un lenguaje de programación es una herramienta que permite a los seres humanos "comunicarse" con una computadora para darle instrucciones. Estas instrucciones pueden utilizarse para crear software (aplicaciones) que pueden ser usados por personas a través de distintos dispositivos.
- **Declarativo**: Es un tipo de lenguaje de programación cuyo enfoque está en describir lo que se quiere lograr, en lugar de explicar cómo hacerlo paso a paso. En otras palabras, su semántica se basa en el *"qué" se desea obtener, no en el "cómo" se debe ejecutar*.
- **Comando**: Es la instrucción más básica que se le puede dar a una computadora para que realice una acción específica.
- **Base de datos**: Es un software (aplicacion) que permite la gestion de una gran cantidad de datos.

## Primera consulta
La consulta mas basica que se puede realizar es:
```sql
SELECT email FROM usuarios;
-- La palabra "SELECT" indica las columnas que quieres de la tabla, con el "FROM" indicas que tabla de la base de datos quieres y con el ";" indicas que hasta ahi acaba la instruccion.

SELECT nombre, email FROM usuarios;
-- Si deseas consultar varias columnas puedes separar por coma cada una de las columnas que quieras.

-- Si quisieramos traducir estas linea a lenguaje humano yo las traduciria asi "Selecciona la columna email desde la tabla usuarios"
```

### Variante
```sql
SELECT * FROM usuarios;
-- Con el "*" indicas que quieres mostrar todas las columnas de dicha tabla, en este caso "usuarios"
```

## Notas:
**Nota:** Las sentencias utilizadas pueden ser en mayusculas o en minusculas

**Nota:** Es importante contar con los permisos adecuados sobre las tablas a las que se les aplica una sentencia SELECT, ya que podrían estar restringidas y provocar el siguiente error: "
ORA-00942: table or view does not exist"

## Ejercicios:
1. *Consulta todos los datos de la tabla "CURSOS"*
2. *Consulta las columnas "BIOGRAFIA" y "SITIO_WEB" de la tabla "PERFILES"*
3. *¿Que columnas tiene la tabla "INSCRIPCIONES"?*
4. *¿Que cursos existen en la base de datos?*