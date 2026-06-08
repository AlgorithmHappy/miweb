CREATE TABLE "posts" (
  "id" integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  "title" varchar(70) NOT NULL,
  "created_at" timestamp DEFAULT CURRENT_TIMESTAMP,
  "average_read_duration" integer not null,
  "description" varchar(110) not null,
  "link_raw_markdown" varchar(250) not null,
  "link_image" varchar(250),
  "alt_image" varchar(70)
);

CREATE TABLE "relations_posts" (
  "id" integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  "id_origin_post" integer not null,
  "id_previous_post" integer,
  "id_next_post" integer,
  "is_in_post_list" boolean
);

CREATE TABLE "posts_tags" (
  "id_post" integer not null,
  "id_tag" integer not null,
  PRIMARY KEY ("id_post", "id_tag")
);

CREATE TABLE "tags" (
  "id" integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  "name" varchar(15) not null
);

CREATE TABLE messages_from_contact_form (
    id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(70) NOT NULL,
    email VARCHAR(70) NOT NULL,
    message VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE "posts_tags" ADD CONSTRAINT "posts_tags_post" FOREIGN KEY ("id_post") REFERENCES "posts" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "posts_tags" ADD CONSTRAINT "posts_tags_tag" FOREIGN KEY ("id_tag") REFERENCES "tags" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "relations_posts" ADD CONSTRAINT "relations_posts_origin" FOREIGN KEY ("id_origin_post") REFERENCES "posts" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "relations_posts" ADD CONSTRAINT "relations_posts_previous" FOREIGN KEY ("id_previous_post") REFERENCES "posts" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "relations_posts" ADD CONSTRAINT "relations_posts_next" FOREIGN KEY ("id_next_post") REFERENCES "posts" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE public.posts ADD CONSTRAINT posts_unique UNIQUE (title);

COMMENT ON TABLE posts IS 'Tabla que almacena los metadatos de una entrada de un blog (post)';

COMMENT ON TABLE relations_posts IS 'Tabla que almacena cual es la entrada de blog (post) que ira antes o despues, ya que puede ser ordenada por fecha o por lista de entradas, por ejemplo un curso que tiene varios capitulos';

COMMENT ON TABLE posts_tags IS 'Tabla de cruce para relacionar las entradas de blogs (post) con las tags, ya que la relacion es de muchos a muchos';

COMMENT ON TABLE tags IS 'Palabras clave (tags) que indican de que es la entrada del blog (post)';

COMMENT ON COLUMN posts.link_raw_markdown IS 'Link de github donde se subio el post en markdown en raw';

COMMENT ON COLUMN posts.link_image IS 'Url de la imagen que todavia no se en donde la vamos a guardar, se ien public del front end, en file browser, o en minio o en github';

COMMENT ON COLUMN posts.alt_image IS 'Alt de la imagen del atributo alt de la etiqueta img de html';

COMMENT ON COLUMN relations_posts.is_in_post_list IS 'Indica si es el la informacion del post siguiente o previo con base al origen es de una lista de post o es por fecha, false = por fecha y true = por lista';









