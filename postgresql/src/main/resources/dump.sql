--
-- PostgreSQL database dump
--

-- Dumped from database version 13.6 (Ubuntu 13.6-0ubuntu0.21.10.1)
-- Dumped by pg_dump version 13.6 (Ubuntu 13.6-0ubuntu0.21.10.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: id-produto; Type: SEQUENCE; Schema: public; Owner: ti2cc
--

CREATE SEQUENCE public."codigo-usuario"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE public."codigo-usuario" OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: produto; Type: TABLE; Schema: public; Owner: ti2cc
--

CREATE TABLE public.usuario (
    codigo integer DEFAULT nextval('public."codigo-usuario"'::regclass) NOT NULL,
    nome text,
    email text,
    idade integer,
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: ti2cc
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (codigo);


--
-- PostgreSQL database dump complete
--    