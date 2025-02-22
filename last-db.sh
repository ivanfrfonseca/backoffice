#!/bin/bash
set -e  # Faz o script parar se houver erro

# Verifica se a variável DATABASE_NAME está definida
if [ -z "$DATABASE_NAME" ]; then
  echo "Erro: A variável DATABASE_NAME não está definida!"
  exit 1
fi

# Conectando ao PostgreSQL e aplicando o schema
psql -v ON_ERROR_STOP=1 --username "$DDL_USERNAME" --dbname "$DATABASE_NAME" <<EOSQL

-- Criação das tabelas
CREATE TABLE public.artigo (
    id integer NOT NULL,
    titulo character varying(1000),
    texto text,
    data character varying(20),
    fk_id_empresa integer NOT NULL
);

COMMENT ON COLUMN public.artigo.data IS 'Data da última atualização do artigo';

CREATE TABLE public.empresa (
    id integer NOT NULL,
    nome character varying(255) NOT NULL
);

CREATE TABLE public.mural (
    id integer NOT NULL,
    titulo character varying(255),
    texto text,
    cor character varying(255),
    tipo character varying(255),
    data character varying(255),
    usuario character varying(255),
    fk_id_empresa integer NOT NULL
);

COMMENT ON COLUMN public.mural.tipo IS '1 = Recorrente
2 = Outras';

CREATE TABLE public.script (
    id integer NOT NULL,
    titulo character varying(255),
    categoria character varying(255),
    caminho character varying(255),
    fk_id_empresa integer NOT NULL
);

CREATE TABLE public.usuario (
    id integer NOT NULL,
    nome character varying(255),
    usuario character varying(255),
    senha character varying(255),
    ativo integer
);

COMMENT ON COLUMN public.usuario.ativo IS '0 = Inativo / 1 = Ativo';

-- Criação das sequências e definição das constraints
CREATE SEQUENCE public.artigos_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.artigos_id_seq OWNED BY public.artigo.id;
ALTER TABLE ONLY public.artigo ALTER COLUMN id SET DEFAULT nextval('public.artigos_id_seq'::regclass);

CREATE SEQUENCE public.empresa_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.empresa_id_seq OWNED BY public.empresa.id;
ALTER TABLE ONLY public.empresa ALTER COLUMN id SET DEFAULT nextval('public.empresa_id_seq'::regclass);

CREATE SEQUENCE public.mural_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.mural_id_seq OWNED BY public.mural.id;
ALTER TABLE ONLY public.mural ALTER COLUMN id SET DEFAULT nextval('public.mural_id_seq'::regclass);

CREATE SEQUENCE public.scripts_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER SEQUENCE public.scripts_id_seq OWNED BY public.script.id;
ALTER TABLE ONLY public.script ALTER COLUMN id SET DEFAULT nextval('public.scripts_id_seq'::regclass);

-- Definição das constraints
ALTER TABLE ONLY public.artigo ADD CONSTRAINT artigo_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.empresa ADD CONSTRAINT empresa_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.mural ADD CONSTRAINT mural_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.script ADD CONSTRAINT script_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.usuario ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);

-- Definição das foreign keys
ALTER TABLE ONLY public.mural ADD CONSTRAINT fk_id_empresa FOREIGN KEY (fk_id_empresa) REFERENCES public.empresa(id) NOT VALID;
ALTER TABLE ONLY public.script ADD CONSTRAINT fk_id_empresa FOREIGN KEY (fk_id_empresa) REFERENCES public.empresa(id) NOT VALID;
ALTER TABLE ONLY public.artigo ADD CONSTRAINT fk_id_empresa FOREIGN KEY (fk_id_empresa) REFERENCES public.empresa(id) NOT VALID;

EOSQL

echo "Schema aplicado com sucesso no banco: $DATABASE_NAME"
