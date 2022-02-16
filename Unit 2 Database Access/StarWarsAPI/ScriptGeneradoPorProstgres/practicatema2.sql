--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

-- Started on 2022-01-04 20:50:54

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

DROP DATABASE swapi;
--
-- TOC entry 3414 (class 1262 OID 24638)
-- Name: swapi; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE swapi WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';


ALTER DATABASE swapi OWNER TO postgres;

\connect swapi

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
-- TOC entry 222 (class 1255 OID 40655)
-- Name: getlastcodigooffilms(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.getlastcodigooffilms() RETURNS integer
    LANGUAGE plpgsql
    AS $$
	BEGIN
		RETURN (SELECT MAX(codigo) FROM films);
	END
	$$;


ALTER FUNCTION public.getlastcodigooffilms() OWNER TO postgres;

--
-- TOC entry 223 (class 1255 OID 36169)
-- Name: peoplewithoutstarships(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.peoplewithoutstarships() RETURNS TABLE(name character varying)
    LANGUAGE sql
    AS $$SELECT people.name 
 FROM people
 LEFT JOIN starships_people 
 ON starships_people.codigo_people = people.codigo
 WHERE starships_people.codigo_people IS NULL;$$;


ALTER FUNCTION public.peoplewithoutstarships() OWNER TO postgres;

--
-- TOC entry 236 (class 1255 OID 37598)
-- Name: search_test(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.search_test(title_search character varying) RETURNS SETOF refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE c1 refcursor;
DECLARE c2 refcursor;
BEGIN
	OPEN c1 FOR
	SELECT DISTINCT ON (films.title , films.director) films.title , films.director FROM films
		INNER JOIN films_people ON films_people.codigo_film = films.codigo
		WHERE UPPER(films.title) LIKE UPPER(title_search);
		RETURN NEXT c1;
	OPEN c2 FOR
	SELECT DISTINCT ON (people.name) people.name FROM people
		INNER JOIN films_people ON films_people.codigo_people = people.codigo
		INNER JOIN films on films.codigo = films_people.codigo_film
		WHERE UPPER(films.title) LIKE UPPER(title_search);
		RETURN NEXT c2;
	RETURN;
END;
$$;


ALTER FUNCTION public.search_test(title_search character varying) OWNER TO postgres;

--
-- TOC entry 235 (class 1255 OID 37596)
-- Name: searchfilmbytitle(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.searchfilmbytitle(filmtosearch character varying) RETURNS TABLE(title character varying, director character varying, characters character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN 
	    RETURN QUERY SELECT films.title , films.director, people.name FROM films
		INNER JOIN films_people ON films_people.codigo_film = films.codigo
		INNER JOIN people ON people.codigo = films_people.codigo_people
		WHERE UPPER(films.title) LIKE UPPER(filmToSearch);
END;
$$;


ALTER FUNCTION public.searchfilmbytitle(filmtosearch character varying) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 40656)
-- Name: films; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.films (
    codigo integer NOT NULL,
    title character varying(100),
    episode_id character varying(100),
    opening_crawl character varying(10485760),
    director character varying(100),
    producer character varying(100),
    release_date character varying(100),
    created character varying(100),
    edited character varying(100)
);


ALTER TABLE public.films OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 40726)
-- Name: films_people; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.films_people (
    codigo_film integer NOT NULL,
    codigo_people integer NOT NULL
);


ALTER TABLE public.films_people OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 40798)
-- Name: films_planets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.films_planets (
    codigo_film integer NOT NULL,
    codigo_planet integer NOT NULL
);


ALTER TABLE public.films_planets OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 40714)
-- Name: people; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.people (
    codigo integer NOT NULL,
    name character varying(100),
    birth_year character varying(100),
    eye_color character varying(100),
    gender character varying(100),
    hair_color character varying(100),
    height character varying(100),
    mass character varying(100),
    skin_color character varying(100),
    homeworld integer,
    created character varying(100),
    edited character varying(100)
);


ALTER TABLE public.people OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 40707)
-- Name: planets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.planets (
    codigo integer NOT NULL,
    name character varying(100),
    diameter character varying(100),
    rotation_period character varying(100),
    orbital_period character varying(100),
    gravity character varying(100),
    population character varying(100),
    climate character varying(100),
    terrain character varying(100),
    surface_water character varying(100),
    created character varying(100),
    edited character varying(100)
);


ALTER TABLE public.planets OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 40771)
-- Name: species; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.species (
    codigo integer NOT NULL,
    name character varying(100),
    classification character varying(100),
    designation character varying(100),
    average_height character varying(100),
    average_lifespan character varying(100),
    eye_color character varying(100),
    hair_color character varying(100),
    skin_color character varying(100),
    language character varying(100),
    homeworld integer,
    created character varying(100),
    edited character varying(100)
);


ALTER TABLE public.species OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 40783)
-- Name: species_people; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.species_people (
    codigo_specie integer NOT NULL,
    codigo_people integer NOT NULL
);


ALTER TABLE public.species_people OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 40685)
-- Name: starships; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.starships (
    codigo integer NOT NULL,
    name character varying(100),
    model character varying(100),
    starship_class character varying(100),
    manufacturer character varying(100),
    cost_in_credits character varying(100),
    length character varying(100),
    crew character varying(100),
    passengers character varying(100),
    max_atmosphering_speed character varying(100),
    hyperdrive_rating character varying(100),
    mglt character varying(100),
    cargo_capacity character varying(100),
    consumables character varying(100),
    created character varying(100),
    edited character varying(100)
);


ALTER TABLE public.starships OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 40692)
-- Name: starships_films; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.starships_films (
    codigo_starship integer NOT NULL,
    codigo_film integer NOT NULL
);


ALTER TABLE public.starships_films OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 40756)
-- Name: starships_people; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.starships_people (
    codigo_starship integer NOT NULL,
    codigo_people integer NOT NULL
);


ALTER TABLE public.starships_people OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 40663)
-- Name: vehicles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicles (
    codigo integer NOT NULL,
    name character varying(100),
    model character varying(100),
    vehicle_class character varying(100),
    manufacturer character varying(100),
    length character varying(100),
    cost_in_credits character varying(100),
    crew character varying(100),
    passengers character varying(100),
    max_atmosphering_speed character varying(100),
    cargo_capacity character varying(100),
    consumables character varying(100),
    created character varying(100),
    edited character varying(100)
);


ALTER TABLE public.vehicles OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 40670)
-- Name: vehicles_films; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicles_films (
    codigo_vehicle integer NOT NULL,
    codigo_film integer NOT NULL
);


ALTER TABLE public.vehicles_films OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 40741)
-- Name: vehicles_people; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicles_people (
    codigo_vehicle integer NOT NULL,
    codigo_people integer NOT NULL
);


ALTER TABLE public.vehicles_people OWNER TO postgres;

--
-- TOC entry 3396 (class 0 OID 40656)
-- Dependencies: 209
-- Data for Name: films; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.films (codigo, title, episode_id, opening_crawl, director, producer, release_date, created, edited) VALUES (1, 'A New Hope', '4', 'It is a period of civil war.
Rebel spaceships, striking
from a hidden base, have won
their first victory against
the evil Galactic Empire.

During the battle, Rebel
spies managed to steal secret
plans to the Empire''s
ultimate weapon, the DEATH
STAR, an armored space
station with enough power
to destroy an entire planet.

Pursued by the Empire''s
sinister agents, Princess
Leia races home aboard her
starship, custodian of the
stolen plans that can save her
people and restore
freedom to the galaxy....', 'George Lucas', 'Gary Kurtz, Rick McCallum', '1977-05-25', '2014-12-10T14:23:31.880000Z', '2014-12-20T19:49:45.256000Z');
INSERT INTO public.films (codigo, title, episode_id, opening_crawl, director, producer, release_date, created, edited) VALUES (2, 'The Empire Strikes Back', '5', 'It is a dark time for the
Rebellion. Although the Death
Star has been destroyed,
Imperial troops have driven the
Rebel forces from their hidden
base and pursued them across
the galaxy.

Evading the dreaded Imperial
Starfleet, a group of freedom
fighters led by Luke Skywalker
has established a new secret
base on the remote ice world
of Hoth.

The evil lord Darth Vader,
obsessed with finding young
Skywalker, has dispatched
thousands of remote probes into
the far reaches of space....', 'Irvin Kershner', 'Gary Kurtz, Rick McCallum', '1980-05-17', '2014-12-12T11:26:24.656000Z', '2014-12-15T13:07:53.386000Z');
INSERT INTO public.films (codigo, title, episode_id, opening_crawl, director, producer, release_date, created, edited) VALUES (3, 'Return of the Jedi', '6', 'Luke Skywalker has returned to
his home planet of Tatooine in
an attempt to rescue his
friend Han Solo from the
clutches of the vile gangster
Jabba the Hutt.

Little does Luke know that the
GALACTIC EMPIRE has secretly
begun construction on a new
armored space station even
more powerful than the first
dreaded Death Star.

When completed, this ultimate
weapon will spell certain doom
for the small band of rebels
struggling to restore freedom
to the galaxy...', 'Richard Marquand', 'Howard G. Kazanjian, George Lucas, Rick McCallum', '1983-05-25', '2014-12-18T10:39:33.255000Z', '2014-12-20T09:48:37.462000Z');
INSERT INTO public.films (codigo, title, episode_id, opening_crawl, director, producer, release_date, created, edited) VALUES (4, 'The Phantom Menace', '1', 'Turmoil has engulfed the
Galactic Republic. The taxation
of trade routes to outlying star
systems is in dispute.

Hoping to resolve the matter
with a blockade of deadly
battleships, the greedy Trade
Federation has stopped all
shipping to the small planet
of Naboo.

While the Congress of the
Republic endlessly debates
this alarming chain of events,
the Supreme Chancellor has
secretly dispatched two Jedi
Knights, the guardians of
peace and justice in the
galaxy, to settle the conflict....', 'George Lucas', 'Rick McCallum', '1999-05-19', '2014-12-19T16:52:55.740000Z', '2014-12-20T10:54:07.216000Z');
INSERT INTO public.films (codigo, title, episode_id, opening_crawl, director, producer, release_date, created, edited) VALUES (5, 'Attack of the Clones', '2', 'There is unrest in the Galactic
Senate. Several thousand solar
systems have declared their
intentions to leave the Republic.

This separatist movement,
under the leadership of the
mysterious Count Dooku, has
made it difficult for the limited
number of Jedi Knights to maintain 
peace and order in the galaxy.

Senator Amidala, the former
Queen of Naboo, is returning
to the Galactic Senate to vote
on the critical issue of creating
an ARMY OF THE REPUBLIC
to assist the overwhelmed
Jedi....', 'George Lucas', 'Rick McCallum', '2002-05-16', '2014-12-20T10:57:57.886000Z', '2014-12-20T20:18:48.516000Z');
INSERT INTO public.films (codigo, title, episode_id, opening_crawl, director, producer, release_date, created, edited) VALUES (6, 'Revenge of the Sith', '3', 'War! The Republic is crumbling
under attacks by the ruthless
Sith Lord, Count Dooku.
There are heroes on both sides.
Evil is everywhere.

In a stunning move, the
fiendish droid leader, General
Grievous, has swept into the
Republic capital and kidnapped
Chancellor Palpatine, leader of
the Galactic Senate.

As the Separatist Droid Army
attempts to flee the besieged
capital with their valuable
hostage, two Jedi Knights lead a
desperate mission to rescue the
captive Chancellor....', 'George Lucas', 'Rick McCallum', '2005-05-19', '2014-12-20T18:49:38.403000Z', '2014-12-20T20:47:52.073000Z');


--
-- TOC entry 3403 (class 0 OID 40726)
-- Dependencies: 216
-- Data for Name: films_people; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 1);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 2);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 3);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 4);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 5);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 6);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 7);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 8);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 9);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 10);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 12);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 13);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 14);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 15);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 16);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 18);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 19);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (1, 81);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 1);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 2);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 3);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 4);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 5);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 10);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 13);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 14);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 18);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 20);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 21);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 22);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 23);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 24);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 25);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (2, 26);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 1);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 2);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 3);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 4);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 5);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 10);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 13);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 14);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 16);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 18);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 20);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 21);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 22);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 25);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 27);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 28);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 29);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 30);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 31);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (3, 45);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 2);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 3);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 10);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 11);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 16);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 20);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 21);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 32);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 33);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 34);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 35);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 36);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 37);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 38);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 39);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 40);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 41);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 42);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 43);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 44);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 46);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 47);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 48);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 49);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 50);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 51);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 52);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 53);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 54);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 55);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 56);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 57);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 58);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (4, 59);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 2);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 3);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 6);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 7);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 10);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 11);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 20);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 21);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 22);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 33);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 35);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 36);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 40);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 43);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 46);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 51);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 52);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 53);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 58);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 59);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 60);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 61);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 62);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 63);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 64);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 65);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 66);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 67);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 68);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 69);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 70);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 71);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 72);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 73);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 74);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 75);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 76);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 77);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 78);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (5, 82);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 1);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 2);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 3);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 4);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 5);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 6);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 7);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 10);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 11);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 12);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 13);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 20);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 21);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 33);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 35);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 46);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 51);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 52);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 53);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 54);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 55);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 56);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 58);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 63);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 64);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 67);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 68);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 75);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 78);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 79);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 80);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 81);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 82);
INSERT INTO public.films_people (codigo_film, codigo_people) VALUES (6, 83);


--
-- TOC entry 3408 (class 0 OID 40798)
-- Dependencies: 221
-- Data for Name: films_planets; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (1, 1);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (1, 2);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (1, 3);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (2, 4);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (2, 5);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (2, 6);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (2, 27);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (3, 1);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (3, 5);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (3, 7);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (3, 8);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (3, 9);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (4, 1);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (4, 8);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (4, 9);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (5, 1);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (5, 8);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (5, 9);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (5, 10);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (5, 11);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 1);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 2);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 5);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 8);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 9);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 12);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 13);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 14);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 15);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 16);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 17);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 18);
INSERT INTO public.films_planets (codigo_film, codigo_planet) VALUES (6, 19);


--
-- TOC entry 3402 (class 0 OID 40714)
-- Dependencies: 215
-- Data for Name: people; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (1, 'Luke Skywalker', '19BBY', 'blue', 'male', 'blond', '172', '77', 'fair', 1, '2014-12-09T13:50:51.644000Z', '2014-12-20T21:17:56.891000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (2, 'C-3PO', '112BBY', 'yellow', 'n/a', 'n/a', '167', '75', 'gold', 1, '2014-12-10T15:10:51.357000Z', '2014-12-20T21:17:50.309000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (3, 'R2-D2', '33BBY', 'red', 'n/a', 'n/a', '96', '32', 'white, blue', 8, '2014-12-10T15:11:50.376000Z', '2014-12-20T21:17:50.311000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (4, 'Darth Vader', '41.9BBY', 'yellow', 'male', 'none', '202', '136', 'white', 1, '2014-12-10T15:18:20.704000Z', '2014-12-20T21:17:50.313000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (5, 'Leia Organa', '19BBY', 'brown', 'female', 'brown', '150', '49', 'light', 2, '2014-12-10T15:20:09.791000Z', '2014-12-20T21:17:50.315000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (6, 'Owen Lars', '52BBY', 'blue', 'male', 'brown, grey', '178', '120', 'light', 1, '2014-12-10T15:52:14.024000Z', '2014-12-20T21:17:50.317000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (7, 'Beru Whitesun lars', '47BBY', 'blue', 'female', 'brown', '165', '75', 'light', 1, '2014-12-10T15:53:41.121000Z', '2014-12-20T21:17:50.319000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (8, 'R5-D4', 'unknown', 'red', 'n/a', 'n/a', '97', '32', 'white, red', 1, '2014-12-10T15:57:50.959000Z', '2014-12-20T21:17:50.321000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (9, 'Biggs Darklighter', '24BBY', 'brown', 'male', 'black', '183', '84', 'light', 1, '2014-12-10T15:59:50.509000Z', '2014-12-20T21:17:50.323000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (10, 'Obi-Wan Kenobi', '57BBY', 'blue-gray', 'male', 'auburn, white', '182', '77', 'fair', 20, '2014-12-10T16:16:29.192000Z', '2014-12-20T21:17:50.325000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (11, 'Anakin Skywalker', '41.9BBY', 'blue', 'male', 'blond', '188', '84', 'fair', 1, '2014-12-10T16:20:44.310000Z', '2014-12-20T21:17:50.327000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (12, 'Wilhuff Tarkin', '64BBY', 'blue', 'male', 'auburn, grey', '180', 'unknown', 'fair', 21, '2014-12-10T16:26:56.138000Z', '2014-12-20T21:17:50.330000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (13, 'Chewbacca', '200BBY', 'blue', 'male', 'brown', '228', '112', 'unknown', 14, '2014-12-10T16:42:45.066000Z', '2014-12-20T21:17:50.332000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (14, 'Han Solo', '29BBY', 'brown', 'male', 'brown', '180', '80', 'fair', 22, '2014-12-10T16:49:14.582000Z', '2014-12-20T21:17:50.334000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (15, 'Greedo', '44BBY', 'black', 'male', 'n/a', '173', '74', 'green', 23, '2014-12-10T17:03:30.334000Z', '2014-12-20T21:17:50.336000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (16, 'Jabba Desilijic Tiure', '600BBY', 'orange', 'hermaphrodite', 'n/a', '175', '1,358', 'green-tan, brown', 24, '2014-12-10T17:11:31.638000Z', '2014-12-20T21:17:50.338000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (18, 'Wedge Antilles', '21BBY', 'hazel', 'male', 'brown', '170', '77', 'fair', 22, '2014-12-12T11:08:06.469000Z', '2014-12-20T21:17:50.341000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (19, 'Jek Tono Porkins', 'unknown', 'blue', 'male', 'brown', '180', '110', 'fair', 26, '2014-12-12T11:16:56.569000Z', '2014-12-20T21:17:50.343000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (20, 'Yoda', '896BBY', 'brown', 'male', 'white', '66', '17', 'green', 28, '2014-12-15T12:26:01.042000Z', '2014-12-20T21:17:50.345000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (21, 'Palpatine', '82BBY', 'yellow', 'male', 'grey', '170', '75', 'pale', 8, '2014-12-15T12:48:05.971000Z', '2014-12-20T21:17:50.347000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (22, 'Boba Fett', '31.5BBY', 'brown', 'male', 'black', '183', '78.2', 'fair', 10, '2014-12-15T12:49:32.457000Z', '2014-12-20T21:17:50.349000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (23, 'IG-88', '15BBY', 'red', 'none', 'none', '200', '140', 'metal', 28, '2014-12-15T12:51:10.076000Z', '2014-12-20T21:17:50.351000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (24, 'Bossk', '53BBY', 'red', 'male', 'none', '190', '113', 'green', 29, '2014-12-15T12:53:49.297000Z', '2014-12-20T21:17:50.355000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (25, 'Lando Calrissian', '31BBY', 'brown', 'male', 'black', '177', '79', 'dark', 30, '2014-12-15T12:56:32.683000Z', '2014-12-20T21:17:50.357000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (26, 'Lobot', '37BBY', 'blue', 'male', 'none', '175', '79', 'light', 6, '2014-12-15T13:01:57.178000Z', '2014-12-20T21:17:50.359000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (27, 'Ackbar', '41BBY', 'orange', 'male', 'none', '180', '83', 'brown mottle', 31, '2014-12-18T11:07:50.584000Z', '2014-12-20T21:17:50.362000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (28, 'Mon Mothma', '48BBY', 'blue', 'female', 'auburn', '150', 'unknown', 'fair', 32, '2014-12-18T11:12:38.895000Z', '2014-12-20T21:17:50.364000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (29, 'Arvel Crynyd', 'unknown', 'brown', 'male', 'brown', 'unknown', 'unknown', 'fair', 28, '2014-12-18T11:16:33.020000Z', '2014-12-20T21:17:50.367000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (30, 'Wicket Systri Warrick', '8BBY', 'brown', 'male', 'brown', '88', '20', 'brown', 7, '2014-12-18T11:21:58.954000Z', '2014-12-20T21:17:50.369000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (31, 'Nien Nunb', 'unknown', 'black', 'male', 'none', '160', '68', 'grey', 33, '2014-12-18T11:26:18.541000Z', '2014-12-20T21:17:50.371000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (32, 'Qui-Gon Jinn', '92BBY', 'blue', 'male', 'brown', '193', '89', 'fair', 28, '2014-12-19T16:54:53.618000Z', '2014-12-20T21:17:50.375000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (33, 'Nute Gunray', 'unknown', 'red', 'male', 'none', '191', '90', 'mottled green', 18, '2014-12-19T17:05:57.357000Z', '2014-12-20T21:17:50.377000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (34, 'Finis Valorum', '91BBY', 'blue', 'male', 'blond', '170', 'unknown', 'fair', 9, '2014-12-19T17:21:45.915000Z', '2014-12-20T21:17:50.379000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (35, 'Padmé Amidala', '46BBY', 'brown', 'female', 'brown', '185', '45', 'light', 8, '2014-12-19T17:28:26.926000Z', '2014-12-20T21:17:50.381000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (36, 'Jar Jar Binks', '52BBY', 'orange', 'male', 'none', '196', '66', 'orange', 8, '2014-12-19T17:29:32.489000Z', '2014-12-20T21:17:50.383000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (37, 'Roos Tarpals', 'unknown', 'orange', 'male', 'none', '224', '82', 'grey', 8, '2014-12-19T17:32:56.741000Z', '2014-12-20T21:17:50.385000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (38, 'Rugor Nass', 'unknown', 'orange', 'male', 'none', '206', 'unknown', 'green', 8, '2014-12-19T17:33:38.909000Z', '2014-12-20T21:17:50.388000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (39, 'Ric Olié', 'unknown', 'blue', 'male', 'brown', '183', 'unknown', 'fair', 8, '2014-12-19T17:45:01.522000Z', '2014-12-20T21:17:50.392000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (40, 'Watto', 'unknown', 'yellow', 'male', 'black', '137', 'unknown', 'blue, grey', 34, '2014-12-19T17:48:54.647000Z', '2014-12-20T21:17:50.395000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (41, 'Sebulba', 'unknown', 'orange', 'male', 'none', '112', '40', 'grey, red', 35, '2014-12-19T17:53:02.586000Z', '2014-12-20T21:17:50.397000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (42, 'Quarsh Panaka', '62BBY', 'brown', 'male', 'black', '183', 'unknown', 'dark', 8, '2014-12-19T17:55:43.348000Z', '2014-12-20T21:17:50.399000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (43, 'Shmi Skywalker', '72BBY', 'brown', 'female', 'black', '163', 'unknown', 'fair', 1, '2014-12-19T17:57:41.191000Z', '2014-12-20T21:17:50.401000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (44, 'Darth Maul', '54BBY', 'yellow', 'male', 'none', '175', '80', 'red', 36, '2014-12-19T18:00:41.929000Z', '2014-12-20T21:17:50.403000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (45, 'Bib Fortuna', 'unknown', 'pink', 'male', 'none', '180', 'unknown', 'pale', 37, '2014-12-20T09:47:02.512000Z', '2014-12-20T21:17:50.407000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (46, 'Ayla Secura', '48BBY', 'hazel', 'female', 'none', '178', '55', 'blue', 37, '2014-12-20T09:48:01.172000Z', '2014-12-20T21:17:50.409000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (47, 'Ratts Tyerel', 'unknown', 'unknown', 'male', 'none', '79', '15', 'grey, blue', 38, '2014-12-20T09:53:15.086000Z', '2014-12-20T21:17:50.410000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (48, 'Dud Bolt', 'unknown', 'yellow', 'male', 'none', '94', '45', 'blue, grey', 39, '2014-12-20T09:57:31.858000Z', '2014-12-20T21:17:50.414000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (49, 'Gasgano', 'unknown', 'black', 'male', 'none', '122', 'unknown', 'white, blue', 40, '2014-12-20T10:02:12.223000Z', '2014-12-20T21:17:50.416000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (50, 'Ben Quadinaros', 'unknown', 'orange', 'male', 'none', '163', '65', 'grey, green, yellow', 41, '2014-12-20T10:08:33.777000Z', '2014-12-20T21:17:50.417000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (51, 'Mace Windu', '72BBY', 'brown', 'male', 'none', '188', '84', 'dark', 42, '2014-12-20T10:12:30.846000Z', '2014-12-20T21:17:50.420000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (52, 'Ki-Adi-Mundi', '92BBY', 'yellow', 'male', 'white', '198', '82', 'pale', 43, '2014-12-20T10:15:32.293000Z', '2014-12-20T21:17:50.422000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (53, 'Kit Fisto', 'unknown', 'black', 'male', 'none', '196', '87', 'green', 44, '2014-12-20T10:18:57.202000Z', '2014-12-20T21:17:50.424000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (54, 'Eeth Koth', 'unknown', 'brown', 'male', 'black', '171', 'unknown', 'brown', 45, '2014-12-20T10:26:47.902000Z', '2014-12-20T21:17:50.427000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (55, 'Adi Gallia', 'unknown', 'blue', 'female', 'none', '184', '50', 'dark', 9, '2014-12-20T10:29:11.661000Z', '2014-12-20T21:17:50.432000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (56, 'Saesee Tiin', 'unknown', 'orange', 'male', 'none', '188', 'unknown', 'pale', 47, '2014-12-20T10:32:11.669000Z', '2014-12-20T21:17:50.434000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (57, 'Yarael Poof', 'unknown', 'yellow', 'male', 'none', '264', 'unknown', 'white', 48, '2014-12-20T10:34:48.725000Z', '2014-12-20T21:17:50.437000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (58, 'Plo Koon', '22BBY', 'black', 'male', 'none', '188', '80', 'orange', 49, '2014-12-20T10:49:19.859000Z', '2014-12-20T21:17:50.439000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (59, 'Mas Amedda', 'unknown', 'blue', 'male', 'none', '196', 'unknown', 'blue', 50, '2014-12-20T10:53:26.457000Z', '2014-12-20T21:17:50.442000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (60, 'Gregar Typho', 'unknown', 'brown', 'male', 'black', '185', '85', 'dark', 8, '2014-12-20T11:10:10.381000Z', '2014-12-20T21:17:50.445000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (61, 'Cordé', 'unknown', 'brown', 'female', 'brown', '157', 'unknown', 'light', 8, '2014-12-20T11:11:39.630000Z', '2014-12-20T21:17:50.449000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (62, 'Cliegg Lars', '82BBY', 'blue', 'male', 'brown', '183', 'unknown', 'fair', 1, '2014-12-20T15:59:03.958000Z', '2014-12-20T21:17:50.451000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (63, 'Poggle the Lesser', 'unknown', 'yellow', 'male', 'none', '183', '80', 'green', 11, '2014-12-20T16:40:43.977000Z', '2014-12-20T21:17:50.453000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (64, 'Luminara Unduli', '58BBY', 'blue', 'female', 'black', '170', '56.2', 'yellow', 51, '2014-12-20T16:45:53.668000Z', '2014-12-20T21:17:50.455000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (65, 'Barriss Offee', '40BBY', 'blue', 'female', 'black', '166', '50', 'yellow', 51, '2014-12-20T16:46:40.440000Z', '2014-12-20T21:17:50.457000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (66, 'Dormé', 'unknown', 'brown', 'female', 'brown', '165', 'unknown', 'light', 8, '2014-12-20T16:49:14.640000Z', '2014-12-20T21:17:50.460000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (67, 'Dooku', '102BBY', 'brown', 'male', 'white', '193', '80', 'fair', 52, '2014-12-20T16:52:14.726000Z', '2014-12-20T21:17:50.462000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (68, 'Bail Prestor Organa', '67BBY', 'brown', 'male', 'black', '191', 'unknown', 'tan', 2, '2014-12-20T16:53:08.575000Z', '2014-12-20T21:17:50.463000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (69, 'Jango Fett', '66BBY', 'brown', 'male', 'black', '183', '79', 'tan', 53, '2014-12-20T16:54:41.620000Z', '2014-12-20T21:17:50.465000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (70, 'Zam Wesell', 'unknown', 'yellow', 'female', 'blonde', '168', '55', 'fair, green, yellow', 54, '2014-12-20T16:57:44.471000Z', '2014-12-20T21:17:50.468000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (71, 'Dexter Jettster', 'unknown', 'yellow', 'male', 'none', '198', '102', 'brown', 55, '2014-12-20T17:28:27.248000Z', '2014-12-20T21:17:50.470000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (72, 'Lama Su', 'unknown', 'black', 'male', 'none', '229', '88', 'grey', 10, '2014-12-20T17:30:50.416000Z', '2014-12-20T21:17:50.473000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (73, 'Taun We', 'unknown', 'black', 'female', 'none', '213', 'unknown', 'grey', 10, '2014-12-20T17:31:21.195000Z', '2014-12-20T21:17:50.474000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (74, 'Jocasta Nu', 'unknown', 'blue', 'female', 'white', '167', 'unknown', 'fair', 9, '2014-12-20T17:32:51.996000Z', '2014-12-20T21:17:50.476000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (75, 'R4-P17', 'unknown', 'red, blue', 'female', 'none', '96', 'unknown', 'silver, red', 28, '2014-12-20T17:43:36.409000Z', '2014-12-20T21:17:50.478000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (76, 'Wat Tambor', 'unknown', 'unknown', 'male', 'none', '193', '48', 'green, grey', 56, '2014-12-20T17:53:52.607000Z', '2014-12-20T21:17:50.481000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (77, 'San Hill', 'unknown', 'gold', 'male', 'none', '191', 'unknown', 'grey', 57, '2014-12-20T17:58:17.049000Z', '2014-12-20T21:17:50.484000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (78, 'Shaak Ti', 'unknown', 'black', 'female', 'none', '178', '57', 'red, blue, white', 58, '2014-12-20T18:44:01.103000Z', '2014-12-20T21:17:50.486000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (79, 'Grievous', 'unknown', 'green, yellow', 'male', 'none', '216', '159', 'brown, white', 59, '2014-12-20T19:43:53.348000Z', '2014-12-20T21:17:50.488000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (80, 'Tarfful', 'unknown', 'blue', 'male', 'brown', '234', '136', 'brown', 14, '2014-12-20T19:46:34.209000Z', '2014-12-20T21:17:50.491000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (81, 'Raymus Antilles', 'unknown', 'brown', 'male', 'brown', '188', '79', 'light', 2, '2014-12-20T19:49:35.583000Z', '2014-12-20T21:17:50.493000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (82, 'Sly Moore', 'unknown', 'white', 'female', 'none', '178', '48', 'pale', 60, '2014-12-20T20:18:37.619000Z', '2014-12-20T21:17:50.496000Z');
INSERT INTO public.people (codigo, name, birth_year, eye_color, gender, hair_color, height, mass, skin_color, homeworld, created, edited) VALUES (83, 'Tion Medon', 'unknown', 'black', 'male', 'none', '206', '80', 'grey', 12, '2014-12-20T20:35:04.260000Z', '2014-12-20T21:17:50.498000Z');


--
-- TOC entry 3401 (class 0 OID 40707)
-- Dependencies: 214
-- Data for Name: planets; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (1, 'Tatooine', '10465', '23', '304', '1 standard', '200000', 'arid', 'desert', '1', '2014-12-09T13:50:49.641000Z', '2014-12-20T20:58:18.411000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (2, 'Alderaan', '12500', '24', '364', '1 standard', '2000000000', 'temperate', 'grasslands, mountains', '40', '2014-12-10T11:35:48.479000Z', '2014-12-20T20:58:18.420000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (3, 'Yavin IV', '10200', '24', '4818', '1 standard', '1000', 'temperate, tropical', 'jungle, rainforests', '8', '2014-12-10T11:37:19.144000Z', '2014-12-20T20:58:18.421000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (4, 'Hoth', '7200', '23', '549', '1.1 standard', 'unknown', 'frozen', 'tundra, ice caves, mountain ranges', '100', '2014-12-10T11:39:13.934000Z', '2014-12-20T20:58:18.423000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (5, 'Dagobah', '8900', '23', '341', 'N/A', 'unknown', 'murky', 'swamp, jungles', '8', '2014-12-10T11:42:22.590000Z', '2014-12-20T20:58:18.425000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (6, 'Bespin', '118000', '12', '5110', '1.5 (surface), 1 standard (Cloud City)', '6000000', 'temperate', 'gas giant', '0', '2014-12-10T11:43:55.240000Z', '2014-12-20T20:58:18.427000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (7, 'Endor', '4900', '18', '402', '0.85 standard', '30000000', 'temperate', 'forests, mountains, lakes', '8', '2014-12-10T11:50:29.349000Z', '2014-12-20T20:58:18.429000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (8, 'Naboo', '12120', '26', '312', '1 standard', '4500000000', 'temperate', 'grassy hills, swamps, forests, mountains', '12', '2014-12-10T11:52:31.066000Z', '2014-12-20T20:58:18.430000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (9, 'Coruscant', '12240', '24', '368', '1 standard', '1000000000000', 'temperate', 'cityscape, mountains', 'unknown', '2014-12-10T11:54:13.921000Z', '2014-12-20T20:58:18.432000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (10, 'Kamino', '19720', '27', '463', '1 standard', '1000000000', 'temperate', 'ocean', '100', '2014-12-10T12:45:06.577000Z', '2014-12-20T20:58:18.434000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (11, 'Geonosis', '11370', '30', '256', '0.9 standard', '100000000000', 'temperate, arid', 'rock, desert, mountain, barren', '5', '2014-12-10T12:47:22.350000Z', '2014-12-20T20:58:18.437000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (12, 'Utapau', '12900', '27', '351', '1 standard', '95000000', 'temperate, arid, windy', 'scrublands, savanna, canyons, sinkholes', '0.9', '2014-12-10T12:49:01.491000Z', '2014-12-20T20:58:18.439000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (13, 'Mustafar', '4200', '36', '412', '1 standard', '20000', 'hot', 'volcanoes, lava rivers, mountains, caves', '0', '2014-12-10T12:50:16.526000Z', '2014-12-20T20:58:18.440000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (14, 'Kashyyyk', '12765', '26', '381', '1 standard', '45000000', 'tropical', 'jungle, forests, lakes, rivers', '60', '2014-12-10T13:32:00.124000Z', '2014-12-20T20:58:18.442000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (15, 'Polis Massa', '0', '24', '590', '0.56 standard', '1000000', 'artificial temperate ', 'airless asteroid', '0', '2014-12-10T13:33:46.405000Z', '2014-12-20T20:58:18.444000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (16, 'Mygeeto', '10088', '12', '167', '1 standard', '19000000', 'frigid', 'glaciers, mountains, ice canyons', 'unknown', '2014-12-10T13:43:39.139000Z', '2014-12-20T20:58:18.446000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (17, 'Felucia', '9100', '34', '231', '0.75 standard', '8500000', 'hot, humid', 'fungus forests', 'unknown', '2014-12-10T13:44:50.397000Z', '2014-12-20T20:58:18.447000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (18, 'Cato Neimoidia', '0', '25', '278', '1 standard', '10000000', 'temperate, moist', 'mountains, fields, forests, rock arches', 'unknown', '2014-12-10T13:46:28.704000Z', '2014-12-20T20:58:18.449000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (19, 'Saleucami', '14920', '26', '392', 'unknown', '1400000000', 'hot', 'caves, desert, mountains, volcanoes', 'unknown', '2014-12-10T13:47:46.874000Z', '2014-12-20T20:58:18.450000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (20, 'Stewjon', '0', 'unknown', 'unknown', '1 standard', 'unknown', 'temperate', 'grass', 'unknown', '2014-12-10T16:16:26.566000Z', '2014-12-20T20:58:18.452000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (21, 'Eriadu', '13490', '24', '360', '1 standard', '22000000000', 'polluted', 'cityscape', 'unknown', '2014-12-10T16:26:54.384000Z', '2014-12-20T20:58:18.454000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (22, 'Corellia', '11000', '25', '329', '1 standard', '3000000000', 'temperate', 'plains, urban, hills, forests', '70', '2014-12-10T16:49:12.453000Z', '2014-12-20T20:58:18.456000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (23, 'Rodia', '7549', '29', '305', '1 standard', '1300000000', 'hot', 'jungles, oceans, urban, swamps', '60', '2014-12-10T17:03:28.110000Z', '2014-12-20T20:58:18.458000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (24, 'Nal Hutta', '12150', '87', '413', '1 standard', '7000000000', 'temperate', 'urban, oceans, swamps, bogs', 'unknown', '2014-12-10T17:11:29.452000Z', '2014-12-20T20:58:18.460000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (25, 'Dantooine', '9830', '25', '378', '1 standard', '1000', 'temperate', 'oceans, savannas, mountains, grasslands', 'unknown', '2014-12-10T17:23:29.896000Z', '2014-12-20T20:58:18.461000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (26, 'Bestine IV', '6400', '26', '680', 'unknown', '62000000', 'temperate', 'rocky islands, oceans', '98', '2014-12-12T11:16:55.078000Z', '2014-12-20T20:58:18.463000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (27, 'Ord Mantell', '14050', '26', '334', '1 standard', '4000000000', 'temperate', 'plains, seas, mesas', '10', '2014-12-15T12:23:41.661000Z', '2014-12-20T20:58:18.464000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (28, 'unknown', '0', '0', '0', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', '2014-12-15T12:25:59.569000Z', '2014-12-20T20:58:18.466000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (29, 'Trandosha', '0', '25', '371', '0.62 standard', '42000000', 'arid', 'mountains, seas, grasslands, deserts', 'unknown', '2014-12-15T12:53:47.695000Z', '2014-12-20T20:58:18.468000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (30, 'Socorro', '0', '20', '326', '1 standard', '300000000', 'arid', 'deserts, mountains', 'unknown', '2014-12-15T12:56:31.121000Z', '2014-12-20T20:58:18.469000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (31, 'Mon Cala', '11030', '21', '398', '1', '27000000000', 'temperate', 'oceans, reefs, islands', '100', '2014-12-18T11:07:01.792000Z', '2014-12-20T20:58:18.471000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (32, 'Chandrila', '13500', '20', '368', '1', '1200000000', 'temperate', 'plains, forests', '40', '2014-12-18T11:11:51.872000Z', '2014-12-20T20:58:18.472000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (33, 'Sullust', '12780', '20', '263', '1', '18500000000', 'superheated', 'mountains, volcanoes, rocky deserts', '5', '2014-12-18T11:25:40.243000Z', '2014-12-20T20:58:18.474000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (34, 'Toydaria', '7900', '21', '184', '1', '11000000', 'temperate', 'swamps, lakes', 'unknown', '2014-12-19T17:47:54.403000Z', '2014-12-20T20:58:18.476000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (35, 'Malastare', '18880', '26', '201', '1.56', '2000000000', 'arid, temperate, tropical', 'swamps, deserts, jungles, mountains', 'unknown', '2014-12-19T17:52:13.106000Z', '2014-12-20T20:58:18.478000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (36, 'Dathomir', '10480', '24', '491', '0.9', '5200', 'temperate', 'forests, deserts, savannas', 'unknown', '2014-12-19T18:00:40.142000Z', '2014-12-20T20:58:18.480000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (37, 'Ryloth', '10600', '30', '305', '1', '1500000000', 'temperate, arid, subartic', 'mountains, valleys, deserts, tundra', '5', '2014-12-20T09:46:25.740000Z', '2014-12-20T20:58:18.481000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (38, 'Aleen Minor', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', '2014-12-20T09:52:23.452000Z', '2014-12-20T20:58:18.483000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (39, 'Vulpter', '14900', '22', '391', '1', '421000000', 'temperate, artic', 'urban, barren', 'unknown', '2014-12-20T09:56:58.874000Z', '2014-12-20T20:58:18.485000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (40, 'Troiken', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'desert, tundra, rainforests, mountains', 'unknown', '2014-12-20T10:01:37.395000Z', '2014-12-20T20:58:18.487000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (41, 'Tund', '12190', '48', '1770', 'unknown', '0', 'unknown', 'barren, ash', 'unknown', '2014-12-20T10:07:29.578000Z', '2014-12-20T20:58:18.489000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (42, 'Haruun Kal', '10120', '25', '383', '0.98', '705300', 'temperate', 'toxic cloudsea, plateaus, volcanoes', 'unknown', '2014-12-20T10:12:28.980000Z', '2014-12-20T20:58:18.491000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (43, 'Cerea', 'unknown', '27', '386', '1', '450000000', 'temperate', 'verdant', '20', '2014-12-20T10:14:48.178000Z', '2014-12-20T20:58:18.493000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (44, 'Glee Anselm', '15600', '33', '206', '1', '500000000', 'tropical, temperate', 'lakes, islands, swamps, seas', '80', '2014-12-20T10:18:26.110000Z', '2014-12-20T20:58:18.495000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (45, 'Iridonia', 'unknown', '29', '413', 'unknown', 'unknown', 'unknown', 'rocky canyons, acid pools', 'unknown', '2014-12-20T10:26:05.788000Z', '2014-12-20T20:58:18.497000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (46, 'Tholoth', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', '2014-12-20T10:28:31.117000Z', '2014-12-20T20:58:18.498000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (47, 'Iktotch', 'unknown', '22', '481', '1', 'unknown', 'arid, rocky, windy', 'rocky', 'unknown', '2014-12-20T10:31:32.413000Z', '2014-12-20T20:58:18.500000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (48, 'Quermia', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', '2014-12-20T10:34:08.249000Z', '2014-12-20T20:58:18.502000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (49, 'Dorin', '13400', '22', '409', '1', 'unknown', 'temperate', 'unknown', 'unknown', '2014-12-20T10:48:36.141000Z', '2014-12-20T20:58:18.504000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (50, 'Champala', 'unknown', '27', '318', '1', '3500000000', 'temperate', 'oceans, rainforests, plateaus', 'unknown', '2014-12-20T10:52:51.524000Z', '2014-12-20T20:58:18.506000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (51, 'Mirial', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'deserts', 'unknown', '2014-12-20T16:44:46.318000Z', '2014-12-20T20:58:18.508000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (52, 'Serenno', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'rainforests, rivers, mountains', 'unknown', '2014-12-20T16:52:13.357000Z', '2014-12-20T20:58:18.510000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (53, 'Concord Dawn', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'jungles, forests, deserts', 'unknown', '2014-12-20T16:54:39.909000Z', '2014-12-20T20:58:18.512000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (54, 'Zolan', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', '2014-12-20T16:56:37.250000Z', '2014-12-20T20:58:18.514000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (55, 'Ojom', 'unknown', 'unknown', 'unknown', 'unknown', '500000000', 'frigid', 'oceans, glaciers', '100', '2014-12-20T17:27:41.286000Z', '2014-12-20T20:58:18.516000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (56, 'Skako', 'unknown', '27', '384', '1', '500000000000', 'temperate', 'urban, vines', 'unknown', '2014-12-20T17:50:47.864000Z', '2014-12-20T20:58:18.517000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (57, 'Muunilinst', '13800', '28', '412', '1', '5000000000', 'temperate', 'plains, forests, hills, mountains', '25', '2014-12-20T17:57:47.420000Z', '2014-12-20T20:58:18.519000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (58, 'Shili', 'unknown', 'unknown', 'unknown', '1', 'unknown', 'temperate', 'cities, savannahs, seas, plains', 'unknown', '2014-12-20T18:43:14.049000Z', '2014-12-20T20:58:18.521000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (59, 'Kalee', '13850', '23', '378', '1', '4000000000', 'arid, temperate, tropical', 'rainforests, cliffs, canyons, seas', 'unknown', '2014-12-20T19:43:51.278000Z', '2014-12-20T20:58:18.523000Z');
INSERT INTO public.planets (codigo, name, diameter, rotation_period, orbital_period, gravity, population, climate, terrain, surface_water, created, edited) VALUES (60, 'Umbara', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', '2014-12-20T20:18:36.256000Z', '2014-12-20T20:58:18.525000Z');


--
-- TOC entry 3406 (class 0 OID 40771)
-- Dependencies: 219
-- Data for Name: species; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (1, 'Human', 'mammal', 'sentient', '180', '120', 'brown, blue, green, hazel, grey, amber', 'blonde, brown, black, red', 'caucasian, black, asian, hispanic', 'Galactic Basic', 9, '2014-12-10T13:52:11.567000Z', '2014-12-20T21:36:42.136000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (2, 'Droid', 'artificial', 'sentient', 'n/a', 'indefinite', 'n/a', 'n/a', 'n/a', 'n/a', NULL, '2014-12-10T15:16:16.259000Z', '2014-12-20T21:36:42.139000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (3, 'Wookie', 'mammal', 'sentient', '210', '400', 'blue, green, yellow, brown, golden, red', 'black, brown', 'gray', 'Shyriiwook', 14, '2014-12-10T16:44:31.486000Z', '2014-12-20T21:36:42.142000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (4, 'Rodian', 'sentient', 'reptilian', '170', 'unknown', 'black', 'n/a', 'green, blue', 'Galatic Basic', 23, '2014-12-10T17:05:26.471000Z', '2014-12-20T21:36:42.144000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (5, 'Hutt', 'gastropod', 'sentient', '300', '1000', 'yellow, red', 'n/a', 'green, brown, tan', 'Huttese', 24, '2014-12-10T17:12:50.410000Z', '2014-12-20T21:36:42.146000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (6, 'Yoda''s species', 'mammal', 'sentient', '66', '900', 'brown, green, yellow', 'brown, white', 'green, yellow', 'Galactic basic', 28, '2014-12-15T12:27:22.877000Z', '2014-12-20T21:36:42.148000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (7, 'Trandoshan', 'reptile', 'sentient', '200', 'unknown', 'yellow, orange', 'none', 'brown, green', 'Dosh', 29, '2014-12-15T13:07:47.704000Z', '2014-12-20T21:36:42.151000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (8, 'Mon Calamari', 'amphibian', 'sentient', '160', 'unknown', 'yellow', 'none', 'red, blue, brown, magenta', 'Mon Calamarian', 31, '2014-12-18T11:09:52.263000Z', '2014-12-20T21:36:42.153000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (9, 'Ewok', 'mammal', 'sentient', '100', 'unknown', 'orange, brown', 'white, brown, black', 'brown', 'Ewokese', 7, '2014-12-18T11:22:00.285000Z', '2014-12-20T21:36:42.155000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (10, 'Sullustan', 'mammal', 'sentient', '180', 'unknown', 'black', 'none', 'pale', 'Sullutese', 33, '2014-12-18T11:26:20.103000Z', '2014-12-20T21:36:42.157000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (11, 'Neimodian', 'unknown', 'sentient', '180', 'unknown', 'red, pink', 'none', 'grey, green', 'Neimoidia', 18, '2014-12-19T17:07:31.319000Z', '2014-12-20T21:36:42.160000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (12, 'Gungan', 'amphibian', 'sentient', '190', 'unknown', 'orange', 'none', 'brown, green', 'Gungan basic', 8, '2014-12-19T17:30:37.341000Z', '2014-12-20T21:36:42.163000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (13, 'Toydarian', 'mammal', 'sentient', '120', '91', 'yellow', 'none', 'blue, green, grey', 'Toydarian', 34, '2014-12-19T17:48:56.893000Z', '2014-12-20T21:36:42.165000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (14, 'Dug', 'mammal', 'sentient', '100', 'unknown', 'yellow, blue', 'none', 'brown, purple, grey, red', 'Dugese', 35, '2014-12-19T17:53:11.214000Z', '2014-12-20T21:36:42.167000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (15, 'Twi''lek', 'mammals', 'sentient', '200', 'unknown', 'blue, brown, orange, pink', 'none', 'orange, yellow, blue, green, pink, purple, tan', 'Twi''leki', 37, '2014-12-20T09:48:02.406000Z', '2014-12-20T21:36:42.169000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (16, 'Aleena', 'reptile', 'sentient', '80', '79', 'unknown', 'none', 'blue, gray', 'Aleena', 38, '2014-12-20T09:53:16.481000Z', '2014-12-20T21:36:42.171000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (17, 'Vulptereen', 'unknown', 'sentient', '100', 'unknown', 'yellow', 'none', 'grey', 'vulpterish', 39, '2014-12-20T09:57:33.128000Z', '2014-12-20T21:36:42.173000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (18, 'Xexto', 'unknown', 'sentient', '125', 'unknown', 'black', 'none', 'grey, yellow, purple', 'Xextese', 40, '2014-12-20T10:02:13.915000Z', '2014-12-20T21:36:42.175000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (19, 'Toong', 'unknown', 'sentient', '200', 'unknown', 'orange', 'none', 'grey, green, yellow', 'Tundan', 41, '2014-12-20T10:08:36.795000Z', '2014-12-20T21:36:42.177000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (20, 'Cerean', 'mammal', 'sentient', '200', 'unknown', 'hazel', 'red, blond, black, white', 'pale pink', 'Cerean', 43, '2014-12-20T10:15:33.765000Z', '2014-12-20T21:36:42.179000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (21, 'Nautolan', 'amphibian', 'sentient', '180', '70', 'black', 'none', 'green, blue, brown, red', 'Nautila', 44, '2014-12-20T10:18:58.610000Z', '2014-12-20T21:36:42.181000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (22, 'Zabrak', 'mammal', 'sentient', '180', 'unknown', 'brown, orange', 'black', 'pale, brown, red, orange, yellow', 'Zabraki', 45, '2014-12-20T10:26:59.894000Z', '2014-12-20T21:36:42.183000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (23, 'Tholothian', 'mammal', 'sentient', 'unknown', 'unknown', 'blue, indigo', 'unknown', 'dark', 'unknown', 46, '2014-12-20T10:29:13.798000Z', '2014-12-20T21:36:42.186000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (24, 'Iktotchi', 'unknown', 'sentient', '180', 'unknown', 'orange', 'none', 'pink', 'Iktotchese', 47, '2014-12-20T10:32:13.046000Z', '2014-12-20T21:36:42.188000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (25, 'Quermian', 'mammal', 'sentient', '240', '86', 'yellow', 'none', 'white', 'Quermian', 48, '2014-12-20T10:34:50.827000Z', '2014-12-20T21:36:42.189000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (26, 'Kel Dor', 'unknown', 'sentient', '180', '70', 'black, silver', 'none', 'peach, orange, red', 'Kel Dor', 49, '2014-12-20T10:49:21.692000Z', '2014-12-20T21:36:42.191000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (27, 'Chagrian', 'amphibian', 'sentient', '190', 'unknown', 'blue', 'none', 'blue', 'Chagria', 50, '2014-12-20T10:53:28.795000Z', '2014-12-20T21:36:42.193000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (28, 'Geonosian', 'insectoid', 'sentient', '178', 'unknown', 'green, hazel', 'none', 'green, brown', 'Geonosian', 11, '2014-12-20T16:40:45.618000Z', '2014-12-20T21:36:42.195000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (29, 'Mirialan', 'mammal', 'sentient', '180', 'unknown', 'blue, green, red, yellow, brown, orange', 'black, brown', 'yellow, green', 'Mirialan', 51, '2014-12-20T16:46:48.290000Z', '2014-12-20T21:36:42.197000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (30, 'Clawdite', 'reptilian', 'sentient', '180', '70', 'yellow', 'none', 'green, yellow', 'Clawdite', 54, '2014-12-20T16:57:46.171000Z', '2014-12-20T21:36:42.199000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (31, 'Besalisk', 'amphibian', 'sentient', '178', '75', 'yellow', 'none', 'brown', 'besalisk', 55, '2014-12-20T17:28:28.821000Z', '2014-12-20T21:36:42.200000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (32, 'Kaminoan', 'amphibian', 'sentient', '220', '80', 'black', 'none', 'grey, blue', 'Kaminoan', 10, '2014-12-20T17:31:24.838000Z', '2014-12-20T21:36:42.202000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (33, 'Skakoan', 'mammal', 'sentient', 'unknown', 'unknown', 'unknown', 'none', 'grey, green', 'Skakoan', 56, '2014-12-20T17:53:54.515000Z', '2014-12-20T21:36:42.204000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (34, 'Muun', 'mammal', 'sentient', '190', '100', 'black', 'none', 'grey, white', 'Muun', 57, '2014-12-20T17:58:19.088000Z', '2014-12-20T21:36:42.207000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (35, 'Togruta', 'mammal', 'sentient', '180', '94', 'red, orange, yellow, green, blue, black', 'none', 'red, white, orange, yellow, green, blue', 'Togruti', 58, '2014-12-20T18:44:03.246000Z', '2014-12-20T21:36:42.209000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (36, 'Kaleesh', 'reptile', 'sentient', '170', '80', 'yellow', 'none', 'brown, orange, tan', 'Kaleesh', 59, '2014-12-20T19:45:42.537000Z', '2014-12-20T21:36:42.210000Z');
INSERT INTO public.species (codigo, name, classification, designation, average_height, average_lifespan, eye_color, hair_color, skin_color, language, homeworld, created, edited) VALUES (37, 'Pau''an', 'mammal', 'sentient', '190', '700', 'black', 'none', 'grey', 'Utapese', 12, '2014-12-20T20:35:06.777000Z', '2014-12-20T21:36:42.212000Z');


--
-- TOC entry 3407 (class 0 OID 40783)
-- Dependencies: 220
-- Data for Name: species_people; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (1, 66);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (1, 67);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (1, 68);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (1, 74);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (2, 2);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (2, 3);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (2, 8);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (2, 23);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (3, 13);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (3, 80);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (4, 15);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (5, 16);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (6, 20);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (7, 24);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (8, 27);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (9, 30);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (10, 31);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (11, 33);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (12, 36);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (12, 37);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (12, 38);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (13, 40);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (14, 41);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (15, 45);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (15, 46);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (16, 47);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (17, 48);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (18, 49);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (19, 50);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (20, 52);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (21, 53);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (22, 44);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (22, 54);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (23, 55);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (24, 56);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (25, 57);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (26, 58);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (27, 59);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (28, 63);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (29, 64);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (29, 65);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (30, 70);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (31, 71);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (32, 72);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (32, 73);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (33, 76);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (34, 77);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (35, 78);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (36, 79);
INSERT INTO public.species_people (codigo_specie, codigo_people) VALUES (37, 83);


--
-- TOC entry 3399 (class 0 OID 40685)
-- Dependencies: 212
-- Data for Name: starships; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (2, 'CR90 corvette', 'CR90 corvette', 'corvette', 'Corellian Engineering Corporation', '3500000', '150', '30-165', '600', '950', '2.0', '60', '3000000', '1 year', '2014-12-10T14:20:33.369000Z', '2014-12-20T21:23:49.867000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (3, 'Star Destroyer', 'Imperial I-class Star Destroyer', 'Star Destroyer', 'Kuat Drive Yards', '150000000', '1,600', '47,060', 'n/a', '975', '2.0', '60', '36000000', '2 years', '2014-12-10T15:08:19.848000Z', '2014-12-20T21:23:49.870000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (5, 'Sentinel-class landing craft', 'Sentinel-class landing craft', 'landing craft', 'Sienar Fleet Systems, Cyngus Spaceworks', '240000', '38', '5', '75', '1000', '1.0', '70', '180000', '1 month', '2014-12-10T15:48:00.586000Z', '2014-12-20T21:23:49.873000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (9, 'Death Star', 'DS-1 Orbital Battle Station', 'Deep Space Mobile Battlestation', 'Imperial Department of Military Research, Sienar Fleet Systems', '1000000000000', '120000', '342,953', '843,342', 'n/a', '4.0', '10', '1000000000000', '3 years', '2014-12-10T16:36:50.509000Z', '2014-12-20T21:26:24.783000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (10, 'Millennium Falcon', 'YT-1300 light freighter', 'Light freighter', 'Corellian Engineering Corporation', '100000', '34.37', '4', '6', '1050', '0.5', '75', '100000', '2 months', '2014-12-10T16:59:45.094000Z', '2014-12-20T21:23:49.880000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (11, 'Y-wing', 'BTL Y-wing', 'assault starfighter', 'Koensayr Manufacturing', '134999', '14', '2', '0', '1000km', '1.0', '80', '110', '1 week', '2014-12-12T11:00:39.817000Z', '2014-12-20T21:23:49.883000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (12, 'X-wing', 'T-65 X-wing', 'Starfighter', 'Incom Corporation', '149999', '12.5', '1', '0', '1050', '1.0', '100', '110', '1 week', '2014-12-12T11:19:05.340000Z', '2014-12-20T21:23:49.886000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (13, 'TIE Advanced x1', 'Twin Ion Engine Advanced x1', 'Starfighter', 'Sienar Fleet Systems', 'unknown', '9.2', '1', '0', '1200', '1.0', '105', '150', '5 days', '2014-12-12T11:21:32.991000Z', '2014-12-20T21:23:49.889000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (15, 'Executor', 'Executor-class star dreadnought', 'Star dreadnought', 'Kuat Drive Yards, Fondor Shipyards', '1143350000', '19000', '279,144', '38000', 'n/a', '2.0', '40', '250000000', '6 years', '2014-12-15T12:31:42.547000Z', '2014-12-20T21:23:49.893000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (17, 'Rebel transport', 'GR-75 medium transport', 'Medium transport', 'Gallofree Yards, Inc.', 'unknown', '90', '6', '90', '650', '4.0', '20', '19000000', '6 months', '2014-12-15T12:34:52.264000Z', '2014-12-20T21:23:49.895000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (21, 'Slave 1', 'Firespray-31-class patrol and attack', 'Patrol craft', 'Kuat Systems Engineering', 'unknown', '21.5', '1', '6', '1000', '3.0', '70', '70000', '1 month', '2014-12-15T13:00:56.332000Z', '2014-12-20T21:23:49.897000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (22, 'Imperial shuttle', 'Lambda-class T-4a shuttle', 'Armed government transport', 'Sienar Fleet Systems', '240000', '20', '6', '20', '850', '1.0', '50', '80000', '2 months', '2014-12-15T13:04:47.235000Z', '2014-12-20T21:23:49.900000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (23, 'EF76 Nebulon-B escort frigate', 'EF76 Nebulon-B escort frigate', 'Escort ship', 'Kuat Drive Yards', '8500000', '300', '854', '75', '800', '2.0', '40', '6000000', '2 years', '2014-12-15T13:06:30.813000Z', '2014-12-20T21:23:49.902000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (27, 'Calamari Cruiser', 'MC80 Liberty type Star Cruiser', 'Star Cruiser', 'Mon Calamari shipyards', '104000000', '1200', '5400', '1200', 'n/a', '1.0', '60', 'unknown', '2 years', '2014-12-18T10:54:57.804000Z', '2014-12-20T21:23:49.904000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (28, 'A-wing', 'RZ-1 A-wing Interceptor', 'Starfighter', 'Alliance Underground Engineering, Incom Corporation', '175000', '9.6', '1', '0', '1300', '1.0', '120', '40', '1 week', '2014-12-18T11:16:34.542000Z', '2014-12-20T21:23:49.907000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (29, 'B-wing', 'A/SF-01 B-wing starfighter', 'Assault Starfighter', 'Slayn & Korpil', '220000', '16.9', '1', '0', '950', '2.0', '91', '45', '1 week', '2014-12-18T11:18:04.763000Z', '2014-12-20T21:23:49.909000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (31, 'Republic Cruiser', 'Consular-class cruiser', 'Space cruiser', 'Corellian Engineering Corporation', 'unknown', '115', '9', '16', '900', '2.0', 'unknown', 'unknown', 'unknown', '2014-12-19T17:01:31.488000Z', '2014-12-20T21:23:49.912000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (32, 'Droid control ship', 'Lucrehulk-class Droid Control Ship', 'Droid control ship', 'Hoersch-Kessel Drive, Inc.', 'unknown', '3170', '175', '139000', 'n/a', '2.0', 'unknown', '4000000000', '500 days', '2014-12-19T17:04:06.323000Z', '2014-12-20T21:23:49.915000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (39, 'Naboo fighter', 'N-1 starfighter', 'Starfighter', 'Theed Palace Space Vessel Engineering Corps', '200000', '11', '1', '0', '1100', '1.0', 'unknown', '65', '7 days', '2014-12-19T17:39:17.582000Z', '2014-12-20T21:23:49.917000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (40, 'Naboo Royal Starship', 'J-type 327 Nubian royal starship', 'yacht', 'Theed Palace Space Vessel Engineering Corps, Nubia Star Drives', 'unknown', '76', '8', 'unknown', '920', '1.8', 'unknown', 'unknown', 'unknown', '2014-12-19T17:45:03.506000Z', '2014-12-20T21:23:49.919000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (41, 'Scimitar', 'Star Courier', 'Space Transport', 'Republic Sienar Systems', '55000000', '26.5', '1', '6', '1180', '1.5', 'unknown', '2500000', '30 days', '2014-12-20T09:39:56.116000Z', '2014-12-20T21:23:49.922000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (43, 'J-type diplomatic barge', 'J-type diplomatic barge', 'Diplomatic barge', 'Theed Palace Space Vessel Engineering Corps, Nubia Star Drives', '2000000', '39', '5', '10', '2000', '0.7', 'unknown', 'unknown', '1 year', '2014-12-20T11:05:51.237000Z', '2014-12-20T21:23:49.925000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (47, 'AA-9 Coruscant freighter', 'Botajef AA-9 Freighter-Liner', 'freighter', 'Botajef Shipyards', 'unknown', '390', 'unknown', '30000', 'unknown', 'unknown', 'unknown', 'unknown', 'unknown', '2014-12-20T17:24:23.509000Z', '2014-12-20T21:23:49.928000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (48, 'Jedi starfighter', 'Delta-7 Aethersprite-class interceptor', 'Starfighter', 'Kuat Systems Engineering', '180000', '8', '1', '0', '1150', '1.0', 'unknown', '60', '7 days', '2014-12-20T17:35:23.906000Z', '2014-12-20T21:23:49.930000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (49, 'H-type Nubian yacht', 'H-type Nubian yacht', 'yacht', 'Theed Palace Space Vessel Engineering Corps', 'unknown', '47.9', '4', 'unknown', '8000', '0.9', 'unknown', 'unknown', 'unknown', '2014-12-20T17:46:46.847000Z', '2014-12-20T21:23:49.932000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (52, 'Republic Assault ship', 'Acclamator I-class assault ship', 'assault ship', 'Rothana Heavy Engineering', 'unknown', '752', '700', '16000', 'unknown', '0.6', 'unknown', '11250000', '2 years', '2014-12-20T18:08:42.926000Z', '2014-12-20T21:23:49.935000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (58, 'Solar Sailer', 'Punworcca 116-class interstellar sloop', 'yacht', 'Huppla Pasa Tisc Shipwrights Collective', '35700', '15.2', '3', '11', '1600', '1.5', 'unknown', '240', '7 days', '2014-12-20T18:37:56.969000Z', '2014-12-20T21:23:49.937000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (59, 'Trade Federation cruiser', 'Providence-class carrier/destroyer', 'capital ship', 'Rendili StarDrive, Free Dac Volunteers Engineering corps.', '125000000', '1088', '600', '48247', '1050', '1.5', 'unknown', '50000000', '4 years', '2014-12-20T19:40:21.902000Z', '2014-12-20T21:23:49.941000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (61, 'Theta-class T-2c shuttle', 'Theta-class T-2c shuttle', 'transport', 'Cygnus Spaceworks', '1000000', '18.5', '5', '16', '2000', '1.0', 'unknown', '50000', '56 days', '2014-12-20T19:48:40.409000Z', '2014-12-20T21:23:49.944000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (63, 'Republic attack cruiser', 'Senator-class Star Destroyer', 'star destroyer', 'Kuat Drive Yards, Allanteen Six shipyards', '59000000', '1137', '7400', '2000', '975', '1.0', 'unknown', '20000000', '2 years', '2014-12-20T19:52:56.232000Z', '2014-12-20T21:23:49.946000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (64, 'Naboo star skiff', 'J-type star skiff', 'yacht', 'Theed Palace Space Vessel Engineering Corps/Nubia Star Drives, Incorporated', 'unknown', '29.2', '3', '3', '1050', '0.5', 'unknown', 'unknown', 'unknown', '2014-12-20T19:55:15.396000Z', '2014-12-20T21:23:49.948000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (65, 'Jedi Interceptor', 'Eta-2 Actis-class light interceptor', 'starfighter', 'Kuat Systems Engineering', '320000', '5.47', '1', '0', '1500', '1.0', 'unknown', '60', '2 days', '2014-12-20T19:56:57.468000Z', '2014-12-20T21:23:49.951000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (66, 'arc-170', 'Aggressive Reconnaissance-170 starfighte', 'starfighter', 'Incom Corporation, Subpro Corporation', '155000', '14.5', '3', '0', '1000', '1.0', '100', '110', '5 days', '2014-12-20T20:03:48.603000Z', '2014-12-20T21:23:49.953000Z');
INSERT INTO public.starships (codigo, name, model, starship_class, manufacturer, cost_in_credits, length, crew, passengers, max_atmosphering_speed, hyperdrive_rating, mglt, cargo_capacity, consumables, created, edited) VALUES (68, 'Banking clan frigte', 'Munificent-class star frigate', 'cruiser', 'Hoersch-Kessel Drive, Inc, Gwori Revolutionary Industries', '57000000', '825', '200', 'unknown', 'unknown', '1.0', 'unknown', '40000000', '2 years', '2014-12-20T20:07:11.538000Z', '2014-12-20T21:23:49.956000Z');


--
-- TOC entry 3400 (class 0 OID 40692)
-- Dependencies: 213
-- Data for Name: starships_films; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (64, 6);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (65, 6);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (2, 1);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (2, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (2, 6);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (66, 6);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (3, 1);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (3, 2);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (3, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (68, 6);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (5, 1);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (9, 1);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (10, 1);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (10, 2);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (10, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (11, 1);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (11, 2);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (11, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (12, 1);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (12, 2);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (12, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (13, 1);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (15, 2);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (15, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (17, 2);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (17, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (21, 2);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (21, 5);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (22, 2);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (22, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (23, 2);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (23, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (27, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (28, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (29, 3);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (31, 4);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (32, 4);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (32, 5);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (32, 6);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (39, 4);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (39, 5);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (40, 4);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (41, 4);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (43, 5);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (47, 5);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (48, 5);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (48, 6);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (49, 5);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (52, 5);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (58, 5);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (59, 6);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (61, 6);
INSERT INTO public.starships_films (codigo_starship, codigo_film) VALUES (63, 6);


--
-- TOC entry 3405 (class 0 OID 40756)
-- Dependencies: 218
-- Data for Name: starships_people; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (64, 10);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (64, 35);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (65, 10);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (65, 11);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (10, 13);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (10, 14);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (10, 25);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (10, 31);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (12, 1);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (12, 9);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (12, 18);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (12, 19);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (13, 4);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (21, 22);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (22, 1);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (22, 13);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (22, 14);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (28, 29);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (39, 11);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (39, 35);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (39, 60);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (40, 39);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (41, 44);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (48, 10);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (48, 58);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (49, 35);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (59, 10);
INSERT INTO public.starships_people (codigo_starship, codigo_people) VALUES (59, 11);


--
-- TOC entry 3397 (class 0 OID 40663)
-- Dependencies: 210
-- Data for Name: vehicles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (4, 'Sand Crawler', 'Digger Crawler', 'wheeled', 'Corellia Mining Corporation', '36.8 ', '150000', '46', '30', '30', '50000', '2 months', '2014-12-10T15:36:25.724000Z', '2014-12-20T21:30:21.661000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (6, 'T-16 skyhopper', 'T-16 skyhopper', 'repulsorcraft', 'Incom Corporation', '10.4 ', '14500', '1', '1', '1200', '50', '0', '2014-12-10T16:01:52.434000Z', '2014-12-20T21:30:21.665000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (7, 'X-34 landspeeder', 'X-34 landspeeder', 'repulsorcraft', 'SoroSuub Corporation', '3.4 ', '10550', '1', '1', '250', '5', 'unknown', '2014-12-10T16:13:52.586000Z', '2014-12-20T21:30:21.668000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (8, 'TIE/LN starfighter', 'Twin Ion Engine/Ln Starfighter', 'starfighter', 'Sienar Fleet Systems', '6.4', 'unknown', '1', '0', '1200', '65', '2 days', '2014-12-10T16:33:52.860000Z', '2014-12-20T21:30:21.670000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (14, 'Snowspeeder', 't-47 airspeeder', 'airspeeder', 'Incom corporation', '4.5', 'unknown', '2', '0', '650', '10', 'none', '2014-12-15T12:22:12Z', '2014-12-20T21:30:21.672000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (16, 'TIE bomber', 'TIE/sa bomber', 'space/planetary bomber', 'Sienar Fleet Systems', '7.8', 'unknown', '1', '0', '850', 'none', '2 days', '2014-12-15T12:33:15.838000Z', '2014-12-20T21:30:21.675000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (18, 'AT-AT', 'All Terrain Armored Transport', 'assault walker', 'Kuat Drive Yards, Imperial Department of Military Research', '20', 'unknown', '5', '40', '60', '1000', 'unknown', '2014-12-15T12:38:25.937000Z', '2014-12-20T21:30:21.677000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (19, 'AT-ST', 'All Terrain Scout Transport', 'walker', 'Kuat Drive Yards, Imperial Department of Military Research', '2', 'unknown', '2', '0', '90', '200', 'none', '2014-12-15T12:46:42.384000Z', '2014-12-20T21:30:21.679000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (20, 'Storm IV Twin-Pod cloud car', 'Storm IV Twin-Pod', 'repulsorcraft', 'Bespin Motors', '7', '75000', '2', '0', '1500', '10', '1 day', '2014-12-15T12:58:50.530000Z', '2014-12-20T21:30:21.681000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (24, 'Sail barge', 'Modified Luxury Sail Barge', 'sail barge', 'Ubrikkian Industries Custom Vehicle Division', '30', '285000', '26', '500', '100', '2000000', 'Live food tanks', '2014-12-18T10:44:14.217000Z', '2014-12-20T21:30:21.684000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (25, 'Bantha-II cargo skiff', 'Bantha-II', 'repulsorcraft cargo skiff', 'Ubrikkian Industries', '9.5', '8000', '5', '16', '250', '135000', '1 day', '2014-12-18T10:48:03.208000Z', '2014-12-20T21:30:21.688000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (26, 'TIE/IN interceptor', 'Twin Ion Engine Interceptor', 'starfighter', 'Sienar Fleet Systems', '9.6', 'unknown', '1', '0', '1250', '75', '2 days', '2014-12-18T10:50:28.225000Z', '2014-12-20T21:30:21.691000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (30, 'Imperial Speeder Bike', '74-Z speeder bike', 'speeder', 'Aratech Repulsor Company', '3', '8000', '1', '1', '360', '4', '1 day', '2014-12-18T11:20:04.625000Z', '2014-12-20T21:30:21.693000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (33, 'Vulture Droid', 'Vulture-class droid starfighter', 'starfighter', 'Haor Chall Engineering, Baktoid Armor Workshop', '3.5', 'unknown', '0', '0', '1200', '0', 'none', '2014-12-19T17:09:53.584000Z', '2014-12-20T21:30:21.697000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (34, 'Multi-Troop Transport', 'Multi-Troop Transport', 'repulsorcraft', 'Baktoid Armor Workshop', '31', '138000', '4', '112', '35', '12000', 'unknown', '2014-12-19T17:12:04.400000Z', '2014-12-20T21:30:21.700000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (35, 'Armored Assault Tank', 'Armoured Assault Tank', 'repulsorcraft', 'Baktoid Armor Workshop', '9.75', 'unknown', '4', '6', '55', 'unknown', 'unknown', '2014-12-19T17:13:29.799000Z', '2014-12-20T21:30:21.703000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (36, 'Single Trooper Aerial Platform', 'Single Trooper Aerial Platform', 'repulsorcraft', 'Baktoid Armor Workshop', '2', '2500', '1', '0', '400', 'none', 'none', '2014-12-19T17:15:09.511000Z', '2014-12-20T21:30:21.705000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (37, 'C-9979 landing craft', 'C-9979 landing craft', 'landing craft', 'Haor Chall Engineering', '210', '200000', '140', '284', '587', '1800000', '1 day', '2014-12-19T17:20:36.373000Z', '2014-12-20T21:30:21.707000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (38, 'Tribubble bongo', 'Tribubble bongo', 'submarine', 'Otoh Gunga Bongameken Cooperative', '15', 'unknown', '1', '2', '85', '1600', 'unknown', '2014-12-19T17:37:37.924000Z', '2014-12-20T21:30:21.710000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (42, 'Sith speeder', 'FC-20 speeder bike', 'speeder', 'Razalon', '1.5', '4000', '1', '0', '180', '2', 'unknown', '2014-12-20T10:09:56.095000Z', '2014-12-20T21:30:21.712000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (44, 'Zephyr-G swoop bike', 'Zephyr-G swoop bike', 'repulsorcraft', 'Mobquet Swoops and Speeders', '3.68', '5750', '1', '1', '350', '200', 'none', '2014-12-20T16:24:16.026000Z', '2014-12-20T21:30:21.714000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (45, 'Koro-2 Exodrive airspeeder', 'Koro-2 Exodrive airspeeder', 'airspeeder', 'Desler Gizh Outworld Mobility Corporation', '6.6', 'unknown', '1', '1', '800', '80', 'unknown', '2014-12-20T17:17:33.526000Z', '2014-12-20T21:30:21.716000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (46, 'XJ-6 airspeeder', 'XJ-6 airspeeder', 'airspeeder', 'Narglatch AirTech prefabricated kit', '6.23', 'unknown', '1', '1', '720', 'unknown', 'unknown', '2014-12-20T17:19:19.991000Z', '2014-12-20T21:30:21.719000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (50, 'LAAT/i', 'Low Altitude Assault Transport/infrantry', 'gunship', 'Rothana Heavy Engineering', '17.4', 'unknown', '6', '30', '620', '170', 'unknown', '2014-12-20T18:01:21.014000Z', '2014-12-20T21:30:21.723000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (51, 'LAAT/c', 'Low Altitude Assault Transport/carrier', 'gunship', 'Rothana Heavy Engineering', '28.82', 'unknown', '1', '0', '620', '40000', 'unknown', '2014-12-20T18:02:46.802000Z', '2014-12-20T21:30:21.725000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (53, 'AT-TE', 'All Terrain Tactical Enforcer', 'walker', 'Rothana Heavy Engineering, Kuat Drive Yards', '13.2', 'unknown', '6', '36', '60', '10000', '21 days', '2014-12-20T18:10:07.560000Z', '2014-12-20T21:30:21.728000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (54, 'SPHA', 'Self-Propelled Heavy Artillery', 'walker', 'Rothana Heavy Engineering', '140', 'unknown', '25', '30', '35', '500', '7 days', '2014-12-20T18:12:32.315000Z', '2014-12-20T21:30:21.731000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (55, 'Flitknot speeder', 'Flitknot speeder', 'speeder', 'Huppla Pasa Tisc Shipwrights Collective', '2', '8000', '1', '0', '634', 'unknown', 'unknown', '2014-12-20T18:15:20.312000Z', '2014-12-20T21:30:21.735000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (56, 'Neimoidian shuttle', 'Sheathipede-class transport shuttle', 'transport', 'Haor Chall Engineering', '20', 'unknown', '2', '6', '880', '1000', '7 days', '2014-12-20T18:25:44.912000Z', '2014-12-20T21:30:21.739000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (57, 'Geonosian starfighter', 'Nantex-class territorial defense', 'starfighter', 'Huppla Pasa Tisc Shipwrights Collective', '9.8', 'unknown', '1', '0', '20000', 'unknown', 'unknown', '2014-12-20T18:34:12.541000Z', '2014-12-20T21:30:21.742000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (60, 'Tsmeu-6 personal wheel bike', 'Tsmeu-6 personal wheel bike', 'wheeled walker', 'Z-Gomot Ternbuell Guppat Corporation', '3.5', '15000', '1', '1', '330', '10', 'none', '2014-12-20T19:43:54.870000Z', '2014-12-20T21:30:21.745000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (62, 'Emergency Firespeeder', 'Fire suppression speeder', 'fire suppression ship', 'unknown', 'unknown', 'unknown', '2', 'unknown', 'unknown', 'unknown', 'unknown', '2014-12-20T19:50:58.559000Z', '2014-12-20T21:30:21.749000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (67, 'Droid tri-fighter', 'tri-fighter', 'droid starfighter', 'Colla Designs, Phlac-Arphocc Automata Industries', '5.4', '20000', '1', '0', '1180', '0', 'none', '2014-12-20T20:05:19.992000Z', '2014-12-20T21:30:21.752000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (69, 'Oevvaor jet catamaran', 'Oevvaor jet catamaran', 'airspeeder', 'Appazanna Engineering Works', '15.1', '12125', '2', '2', '420', '50', '3 days', '2014-12-20T20:20:53.931000Z', '2014-12-20T21:30:21.756000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (70, 'Raddaugh Gnasp fluttercraft', 'Raddaugh Gnasp fluttercraft', 'air speeder', 'Appazanna Engineering Works', '7', '14750', '2', '0', '310', '20', 'none', '2014-12-20T20:21:55.648000Z', '2014-12-20T21:30:21.759000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (71, 'Clone turbo tank', 'HAVw A6 Juggernaut', 'wheeled walker', 'Kuat Drive Yards', '49.4', '350000', '20', '300', '160', '30000', '20 days', '2014-12-20T20:24:45.587000Z', '2014-12-20T21:30:21.762000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (72, 'Corporate Alliance tank droid', 'NR-N99 Persuader-class droid enforcer', 'droid tank', 'Techno Union', '10.96', '49000', '0', '4', '100', 'none', 'none', '2014-12-20T20:26:55.522000Z', '2014-12-20T21:30:21.765000Z');
INSERT INTO public.vehicles (codigo, name, model, vehicle_class, manufacturer, length, cost_in_credits, crew, passengers, max_atmosphering_speed, cargo_capacity, consumables, created, edited) VALUES (73, 'Droid gunship', 'HMP droid gunship', 'airspeeder', 'Baktoid Fleet Ordnance, Haor Chall Engineering', '12.3', '60000', '0', '0', '820', '0', 'none', '2014-12-20T20:32:05.687000Z', '2014-12-20T21:30:21.768000Z');


--
-- TOC entry 3398 (class 0 OID 40670)
-- Dependencies: 211
-- Data for Name: vehicles_films; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (67, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (4, 1);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (4, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (69, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (6, 1);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (70, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (7, 1);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (71, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (8, 1);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (8, 2);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (8, 3);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (72, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (73, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (14, 2);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (16, 2);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (16, 3);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (18, 2);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (18, 3);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (19, 2);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (19, 3);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (20, 2);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (24, 3);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (25, 3);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (26, 3);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (30, 3);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (33, 4);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (33, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (34, 4);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (35, 4);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (36, 4);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (37, 4);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (38, 4);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (42, 4);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (44, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (45, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (46, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (50, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (50, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (51, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (53, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (53, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (54, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (55, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (56, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (56, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (57, 5);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (60, 6);
INSERT INTO public.vehicles_films (codigo_vehicle, codigo_film) VALUES (62, 6);


--
-- TOC entry 3404 (class 0 OID 40741)
-- Dependencies: 217
-- Data for Name: vehicles_people; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (14, 1);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (14, 18);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (19, 13);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (30, 1);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (30, 5);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (38, 10);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (38, 32);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (42, 44);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (44, 11);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (45, 70);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (46, 11);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (55, 67);
INSERT INTO public.vehicles_people (codigo_vehicle, codigo_people) VALUES (60, 79);


--
-- TOC entry 3230 (class 2606 OID 40730)
-- Name: films_people films_people_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.films_people
    ADD CONSTRAINT films_people_pkey PRIMARY KEY (codigo_film, codigo_people);


--
-- TOC entry 3216 (class 2606 OID 40662)
-- Name: films films_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.films
    ADD CONSTRAINT films_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3240 (class 2606 OID 40802)
-- Name: films_planets films_planets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.films_planets
    ADD CONSTRAINT films_planets_pkey PRIMARY KEY (codigo_film, codigo_planet);


--
-- TOC entry 3228 (class 2606 OID 40720)
-- Name: people people_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.people
    ADD CONSTRAINT people_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3226 (class 2606 OID 40713)
-- Name: planets planets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.planets
    ADD CONSTRAINT planets_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3238 (class 2606 OID 40787)
-- Name: species_people species_people_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.species_people
    ADD CONSTRAINT species_people_pkey PRIMARY KEY (codigo_specie, codigo_people);


--
-- TOC entry 3236 (class 2606 OID 40777)
-- Name: species species_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.species
    ADD CONSTRAINT species_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3224 (class 2606 OID 40696)
-- Name: starships_films starships_films_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starships_films
    ADD CONSTRAINT starships_films_pkey PRIMARY KEY (codigo_starship, codigo_film);


--
-- TOC entry 3234 (class 2606 OID 40760)
-- Name: starships_people starships_people_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starships_people
    ADD CONSTRAINT starships_people_pkey PRIMARY KEY (codigo_starship, codigo_people);


--
-- TOC entry 3222 (class 2606 OID 40691)
-- Name: starships starships_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starships
    ADD CONSTRAINT starships_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3220 (class 2606 OID 40674)
-- Name: vehicles_films vehicles_films_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles_films
    ADD CONSTRAINT vehicles_films_pkey PRIMARY KEY (codigo_vehicle, codigo_film);


--
-- TOC entry 3232 (class 2606 OID 40745)
-- Name: vehicles_people vehicles_people_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles_people
    ADD CONSTRAINT vehicles_people_pkey PRIMARY KEY (codigo_vehicle, codigo_people);


--
-- TOC entry 3218 (class 2606 OID 40669)
-- Name: vehicles vehicles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles
    ADD CONSTRAINT vehicles_pkey PRIMARY KEY (codigo);


--
-- TOC entry 3246 (class 2606 OID 40731)
-- Name: films_people films_people_codigo_film_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.films_people
    ADD CONSTRAINT films_people_codigo_film_fkey FOREIGN KEY (codigo_film) REFERENCES public.films(codigo);


--
-- TOC entry 3247 (class 2606 OID 40736)
-- Name: films_people films_people_codigo_people_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.films_people
    ADD CONSTRAINT films_people_codigo_people_fkey FOREIGN KEY (codigo_people) REFERENCES public.people(codigo);


--
-- TOC entry 3255 (class 2606 OID 40803)
-- Name: films_planets films_planets_codigo_film_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.films_planets
    ADD CONSTRAINT films_planets_codigo_film_fkey FOREIGN KEY (codigo_film) REFERENCES public.films(codigo);


--
-- TOC entry 3256 (class 2606 OID 40808)
-- Name: films_planets films_planets_codigo_planet_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.films_planets
    ADD CONSTRAINT films_planets_codigo_planet_fkey FOREIGN KEY (codigo_planet) REFERENCES public.planets(codigo);


--
-- TOC entry 3245 (class 2606 OID 40721)
-- Name: people people_homeworld_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.people
    ADD CONSTRAINT people_homeworld_fkey FOREIGN KEY (homeworld) REFERENCES public.planets(codigo);


--
-- TOC entry 3252 (class 2606 OID 40778)
-- Name: species species_homeworld_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.species
    ADD CONSTRAINT species_homeworld_fkey FOREIGN KEY (homeworld) REFERENCES public.planets(codigo);


--
-- TOC entry 3253 (class 2606 OID 40793)
-- Name: species_people species_people_codigo_people_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.species_people
    ADD CONSTRAINT species_people_codigo_people_fkey FOREIGN KEY (codigo_people) REFERENCES public.people(codigo);


--
-- TOC entry 3254 (class 2606 OID 40788)
-- Name: species_people species_people_codigo_specie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.species_people
    ADD CONSTRAINT species_people_codigo_specie_fkey FOREIGN KEY (codigo_specie) REFERENCES public.species(codigo);


--
-- TOC entry 3243 (class 2606 OID 40702)
-- Name: starships_films starships_films_codigo_film_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starships_films
    ADD CONSTRAINT starships_films_codigo_film_fkey FOREIGN KEY (codigo_film) REFERENCES public.films(codigo);


--
-- TOC entry 3244 (class 2606 OID 40697)
-- Name: starships_films starships_films_codigo_starship_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starships_films
    ADD CONSTRAINT starships_films_codigo_starship_fkey FOREIGN KEY (codigo_starship) REFERENCES public.starships(codigo);


--
-- TOC entry 3250 (class 2606 OID 40766)
-- Name: starships_people starships_people_codigo_people_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starships_people
    ADD CONSTRAINT starships_people_codigo_people_fkey FOREIGN KEY (codigo_people) REFERENCES public.people(codigo);


--
-- TOC entry 3251 (class 2606 OID 40761)
-- Name: starships_people starships_people_codigo_starship_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.starships_people
    ADD CONSTRAINT starships_people_codigo_starship_fkey FOREIGN KEY (codigo_starship) REFERENCES public.starships(codigo);


--
-- TOC entry 3241 (class 2606 OID 40680)
-- Name: vehicles_films vehicles_films_codigo_film_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles_films
    ADD CONSTRAINT vehicles_films_codigo_film_fkey FOREIGN KEY (codigo_film) REFERENCES public.films(codigo);


--
-- TOC entry 3242 (class 2606 OID 40675)
-- Name: vehicles_films vehicles_films_codigo_vehicle_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles_films
    ADD CONSTRAINT vehicles_films_codigo_vehicle_fkey FOREIGN KEY (codigo_vehicle) REFERENCES public.vehicles(codigo);


--
-- TOC entry 3248 (class 2606 OID 40751)
-- Name: vehicles_people vehicles_people_codigo_people_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles_people
    ADD CONSTRAINT vehicles_people_codigo_people_fkey FOREIGN KEY (codigo_people) REFERENCES public.people(codigo);


--
-- TOC entry 3249 (class 2606 OID 40746)
-- Name: vehicles_people vehicles_people_codigo_vehicle_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles_people
    ADD CONSTRAINT vehicles_people_codigo_vehicle_fkey FOREIGN KEY (codigo_vehicle) REFERENCES public.vehicles(codigo);


-- Completed on 2022-01-04 20:50:54

--
-- PostgreSQL database dump complete
--

