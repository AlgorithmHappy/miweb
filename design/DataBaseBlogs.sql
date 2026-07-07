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


CREATE TABLE repositories (
    id SERIAL PRIMARY KEY,                                  -- Identificador interno del repositorio
    name VARCHAR(100) NOT NULL UNIQUE,                      -- Nombre del repositorio en GitHub (ej. 'curso-java')
    owner VARCHAR(100) NOT NULL,                            -- Usuario u organización propietaria del repo (ej. 'AlgorithmHappy')
    github_token VARCHAR(255) NOT NULL,                     -- Personal Access Token con scope 'repo' para hacer push/pull
    default_branch VARCHAR(100) NOT NULL DEFAULT 'main',    -- Rama por defecto a usar (ej. 'main' o 'master')
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP          -- Fecha de registro del repositorio
);

COMMENT ON TABLE repositories IS 'Repositorios de GitHub usados para sincronizar posts con HedgeDoc';
COMMENT ON COLUMN repositories.name IS 'Nombre del repositorio en GitHub';
COMMENT ON COLUMN repositories.owner IS 'Usuario u organización propietaria del repositorio (user/repo)';
COMMENT ON COLUMN repositories.github_token IS 'Personal Access Token de GitHub con permisos de lectura/escritura sobre el repo';
COMMENT ON COLUMN repositories.default_branch IS 'Rama por defecto usada para pull/push';

CREATE TABLE posts_sync (
    id_post INTEGER PRIMARY KEY,            -- FK a posts.id; también es PK porque la relación es 1 a 1
    id_repository INTEGER NOT NULL,         -- FK al repositorio de GitHub donde vive el archivo
    file_path VARCHAR(500) NOT NULL,        -- Ruta del archivo .md dentro del repo (ej. 'docs/intro.md')
    hedgedoc_note_id VARCHAR(255) NOT NULL, -- ID de la nota correspondiente en HedgeDoc
    github_sha VARCHAR(100),                -- SHA actual del archivo en GitHub, requerido para hacer push sin conflicto
    last_synced_at TIMESTAMP,               -- Fecha/hora de la última sincronización exitosa (pull o push)

    CONSTRAINT fk_posts_sync_post
        FOREIGN KEY (id_post) REFERENCES posts(id)
        ON DELETE CASCADE,                  -- Si se borra el post, se borra su info de sync

    CONSTRAINT fk_posts_sync_repo
        FOREIGN KEY (id_repository) REFERENCES repositories(id)
        ON DELETE RESTRICT                  -- No permite borrar un repo si tiene posts sincronizados
);

COMMENT ON TABLE posts_sync IS 'Relaciona cada post con su archivo en GitHub y su nota en HedgeDoc para sincronización bidireccional';
COMMENT ON COLUMN posts_sync.id_post IS 'Referencia al post sincronizado (1 a 1)';
COMMENT ON COLUMN posts_sync.id_repository IS 'Repositorio de GitHub donde vive el archivo del post';
COMMENT ON COLUMN posts_sync.file_path IS 'Ruta relativa del archivo .md dentro del repositorio';
COMMENT ON COLUMN posts_sync.hedgedoc_note_id IS 'ID de la nota en HedgeDoc asociada a este post';
COMMENT ON COLUMN posts_sync.github_sha IS 'SHA del archivo en GitHub, necesario para hacer push (PUT) sin conflicto 409';
COMMENT ON COLUMN posts_sync.last_synced_at IS 'Marca de tiempo de la última sincronización exitosa (pull o push)';






