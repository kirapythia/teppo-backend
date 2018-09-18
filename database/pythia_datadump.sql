--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.10

-- Started on 2018-08-28 15:28:38 EEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 10 (class 2615 OID 16399)
-- Name: project; Type: SCHEMA; Schema: -; Owner: pythiaservice
--

CREATE SCHEMA project;


ALTER SCHEMA project OWNER TO pythiaservice;

--
-- TOC entry 580 (class 1247 OID 16939)
-- Name: status_enum; Type: TYPE; Schema: project; Owner: pythiaservice
--

CREATE TYPE project.status_enum AS ENUM (
    'WAITING_FOR_APPROVAL',
    'APPROVED',
    'REVERTED'
);


ALTER TYPE project.status_enum OWNER TO pythiaservice;

--
-- TOC entry 199 (class 1255 OID 16931)
-- Name: deleteall(); Type: FUNCTION; Schema: project; Owner: pythiaservice
--

CREATE FUNCTION project.deleteall() RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
delete from project.sister_project;
delete from project.ptext;
delete from project.plan;
delete from project.project;
END$$;


ALTER FUNCTION project.deleteall() OWNER TO pythiaservice;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 195 (class 1259 OID 16980)
-- Name: plan; Type: TABLE; Schema: project; Owner: pythiaservice
--

CREATE TABLE project.plan (
    plan_id bigint NOT NULL,
    project_id bigint,
    main_no smallint,
    sub_no smallint,
    version smallint,
    pdf_url character varying,
    xml_url character varying,
    status project.status_enum,
    created_at timestamp with time zone,
    created_by character varying,
    updated_at timestamp with time zone,
    updated_by character varying,
    deleted boolean,
    maintenance_duty boolean,
    street_management_decision timestamp with time zone
);


ALTER TABLE project.plan OWNER TO pythiaservice;

--
-- TOC entry 196 (class 1259 OID 16994)
-- Name: latest_plans; Type: VIEW; Schema: project; Owner: pythiaservice
--

CREATE VIEW project.latest_plans AS
 SELECT p1.plan_id,
    p1.project_id,
    p1.main_no,
    p1.sub_no,
    p1.version,
    p1.pdf_url,
    p1.xml_url,
    p1.status,
    p1.created_at,
    p1.created_by,
    p1.updated_at,
    p1.updated_by,
    p1.deleted,
    p1.maintenance_duty,
    p1.street_management_decision
   FROM (( SELECT max(p2.version) AS maxversion,
            p2.main_no,
            p2.sub_no
           FROM project.plan p2
          WHERE (p2.deleted = false)
          GROUP BY p2.main_no, p2.sub_no) tmp
     JOIN project.plan p1 ON (((p1.main_no = tmp.main_no) AND (p1.sub_no = tmp.sub_no) AND (p1.version = tmp.maxversion))))
UNION ALL
 SELECT p1.plan_id,
    p1.project_id,
    p1.main_no,
    p1.sub_no,
    p1.version,
    p1.pdf_url,
    p1.xml_url,
    p1.status,
    p1.created_at,
    p1.created_by,
    p1.updated_at,
    p1.updated_by,
    p1.deleted,
    p1.maintenance_duty,
    p1.street_management_decision
   FROM (( SELECT (max(p2.version) - 1) AS maxversion,
            p2.main_no,
            p2.sub_no
           FROM project.plan p2
          WHERE (p2.deleted = false)
          GROUP BY p2.main_no, p2.sub_no) tmp
     JOIN project.plan p1 ON (((p1.main_no = tmp.main_no) AND (p1.sub_no = tmp.sub_no) AND (p1.version = tmp.maxversion))));


ALTER TABLE project.latest_plans OWNER TO pythiaservice;

--
-- TOC entry 190 (class 1259 OID 16749)
-- Name: plan_serial; Type: SEQUENCE; Schema: project; Owner: pythiaservice
--

CREATE SEQUENCE project.plan_serial
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE project.plan_serial OWNER TO pythiaservice;

--
-- TOC entry 192 (class 1259 OID 16764)
-- Name: pmap_serial; Type: SEQUENCE; Schema: project; Owner: pythiaservice
--

CREATE SEQUENCE project.pmap_serial
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE project.pmap_serial OWNER TO pythiaservice;

--
-- TOC entry 189 (class 1259 OID 16747)
-- Name: proj_serial; Type: SEQUENCE; Schema: project; Owner: pythiaservice
--

CREATE SEQUENCE project.proj_serial
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE project.proj_serial OWNER TO pythiaservice;

--
-- TOC entry 188 (class 1259 OID 16729)
-- Name: project; Type: TABLE; Schema: project; Owner: pythiaservice
--

CREATE TABLE project.project (
    project_id bigint NOT NULL,
    hansu_project_id character varying,
    main_no smallint,
    name character varying,
    description character varying,
    completed boolean,
    created_at timestamp with time zone,
    created_by character varying,
    updated_at timestamp with time zone,
    updated_by character varying
);


ALTER TABLE project.project OWNER TO pythiaservice;

--
-- TOC entry 193 (class 1259 OID 16912)
-- Name: ptex_serial; Type: SEQUENCE; Schema: project; Owner: pythiaservice
--

CREATE SEQUENCE project.ptex_serial
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE project.ptex_serial OWNER TO pythiaservice;

--
-- TOC entry 194 (class 1259 OID 16914)
-- Name: ptext; Type: TABLE; Schema: project; Owner: pythiaservice
--

CREATE TABLE project.ptext (
    text_id bigint NOT NULL,
    plan_id bigint,
    url character varying,
    approved boolean,
    created_at timestamp with time zone,
    created_by character varying,
    updated_at timestamp with time zone,
    updated_by character varying,
    ptext character varying
);


ALTER TABLE project.ptext OWNER TO pythiaservice;

--
-- TOC entry 191 (class 1259 OID 16759)
-- Name: sister_project; Type: TABLE; Schema: project; Owner: pythiaservice
--

CREATE TABLE project.sister_project (
    id bigint NOT NULL,
    project_id bigint,
    sister_project_id bigint
);


ALTER TABLE project.sister_project OWNER TO pythiaservice;

--
-- TOC entry 3092 (class 0 OID 16980)
-- Dependencies: 195
-- Data for Name: plan; Type: TABLE DATA; Schema: project; Owner: pythiaservice
--

COPY project.plan (plan_id, project_id, main_no, sub_no, version, pdf_url, xml_url, status, created_at, created_by, updated_at, updated_by, deleted, maintenance_duty, street_management_decision) FROM stdin;
540	50	6034	2	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6034_002_0.pdf		APPROVED	2017-11-03 08:55:36.281+00	root	\N	\N	f	f	\N
541	50	6034	1	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6034_001_0.pdf		APPROVED	2017-11-03 09:17:53.101+00	root	\N	\N	f	f	\N
542	50	6034	110	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6034_110_0.pdf		APPROVED	2017-11-06 10:31:31.337+00	root	\N	\N	f	f	\N
543	52	5003	1	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_0.pdf		APPROVED	2017-11-22 09:25:52.175+00	root	\N	\N	f	f	\N
544	52	5003	602	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_602_0.pdf		APPROVED	2017-11-22 09:25:52.354+00	root	\N	\N	f	f	\N
545	52	5003	1	1	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_1.pdf	\N	APPROVED	2017-11-22 09:28:30.916+00	root	2017-11-22 09:29:53.986+00	\N	f	f	\N
546	52	5003	1	2	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_2.pdf	\N	APPROVED	2017-11-22 09:30:13.725+00	root	2017-11-22 09:40:00.434+00	\N	f	f	\N
547	53	5003	1	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_0.pdf		APPROVED	2017-11-22 09:47:48.007+00	root	\N	\N	f	f	\N
569	60	7000	10	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/7000_010_0.pdf		APPROVED	2018-05-03 12:04:29.303+00	root	\N	\N	f	f	\N
549	53	5003	1	1	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_1.pdf	\N	APPROVED	2017-11-22 09:52:14.648+00	root	2017-11-22 09:59:52.899+00	\N	f	t	\N
548	53	5003	602	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_602_0.pdf		APPROVED	2017-11-22 09:47:48.169+00	root	2017-11-22 10:00:08.707+00	\N	f	t	\N
550	54	5003	1	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_0.pdf		APPROVED	2017-11-22 12:11:25.821+00	root	\N	\N	f	f	\N
551	54	5003	602	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_602_0.pdf		APPROVED	2017-11-22 12:11:25.994+00	root	\N	\N	f	f	\N
570	61	7000	10	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/7000_010_0.pdf		APPROVED	2018-05-23 09:17:14.913+00	root	\N	\N	f	f	\N
552	54	5003	1	1	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_1.pdf	\N	APPROVED	2017-11-22 12:15:21.222+00	root	2017-11-22 12:17:11.367+00	\N	f	t	\N
554	56	6998	10	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6998_010C_0.pdf		APPROVED	2017-12-04 09:55:10.378+00	root	\N	\N	f	f	\N
555	56	6998	20	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6998_020D_0.pdf		APPROVED	2017-12-04 09:55:10.804+00	root	\N	\N	f	f	\N
556	56	6998	30	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6998_030A_0.pdf		APPROVED	2017-12-04 09:55:11.058+00	root	\N	\N	f	f	\N
557	56	6998	300	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6998_300B_0.pdf		APPROVED	2017-12-04 09:55:11.442+00	root	\N	\N	f	f	\N
558	56	6998	700	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6998_700B_0.pdf		APPROVED	2017-12-04 09:55:11.7+00	root	\N	\N	f	f	\N
560	56	6998	20	1	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6998_020D_1.pdf	\N	APPROVED	2018-01-10 10:36:17.932+00	root	2018-01-16 13:35:42.737+00	\N	f	f	\N
559	56	6998	10	1	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6998_010C_1.pdf	\N	APPROVED	2018-01-10 10:35:45.292+00	root	2018-01-16 13:50:12.883+00	\N	f	f	\N
561	56	6998	700	1	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/6998_700B_1.pdf	\N	WAITING_FOR_APPROVAL	2018-01-17 06:02:19.31+00	root	\N	\N	f	f	\N
562	57	5003	1	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_0.pdf		APPROVED	2018-02-13 06:31:02.203+00	root	\N	\N	f	f	\N
563	57	5003	602	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_602_0.pdf		APPROVED	2018-02-13 06:31:02.355+00	root	\N	\N	f	f	\N
564	57	5003	602	1	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_602_1.pdf	\N	APPROVED	2018-02-13 06:35:48.477+00	root	2018-02-13 06:36:23.164+00	\N	f	f	\N
565	58	5003	1	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_0.pdf	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_0.xml	APPROVED	2018-03-08 08:13:43.013+00	root	2018-03-08 08:13:43.768+00	\N	f	f	\N
553	55	5003	1	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_0.pdf	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_0.xml	APPROVED	2017-11-27 11:24:15.743+00	root	2017-11-27 11:24:16.393+00	\N	t	f	\N
566	59	5003	1	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_0.pdf	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_001_0.xml	APPROVED	2018-04-26 07:10:56.259+00	root	2018-04-26 07:10:56.681+00	\N	f	f	\N
567	59	5003	602	0	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_602_0.pdf		APPROVED	2018-04-26 07:10:56.924+00	root	\N	\N	f	f	\N
568	59	5003	602	1	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/5003_602_1.pdf	\N	APPROVED	2018-04-26 07:25:48.281+00	root	2018-04-26 07:29:32.206+00	\N	f	f	\N
571	61	7000	10	1	https://kirapythia-plans-bucket.s3-eu-west-1.amazonaws.com/7000_010_1.pdf	\N	APPROVED	2018-05-23 09:19:49.583+00	root	2018-05-23 09:20:38.148+00	\N	f	f	\N
\.


--
-- TOC entry 3098 (class 0 OID 0)
-- Dependencies: 190
-- Name: plan_serial; Type: SEQUENCE SET; Schema: project; Owner: pythiaservice
--

SELECT pg_catalog.setval('project.plan_serial', 571, true);


--
-- TOC entry 3099 (class 0 OID 0)
-- Dependencies: 192
-- Name: pmap_serial; Type: SEQUENCE SET; Schema: project; Owner: pythiaservice
--

SELECT pg_catalog.setval('project.pmap_serial', 67, true);


--
-- TOC entry 3100 (class 0 OID 0)
-- Dependencies: 189
-- Name: proj_serial; Type: SEQUENCE SET; Schema: project; Owner: pythiaservice
--

SELECT pg_catalog.setval('project.proj_serial', 62, true);


--
-- TOC entry 3085 (class 0 OID 16729)
-- Dependencies: 188
-- Data for Name: project; Type: TABLE DATA; Schema: project; Owner: pythiaservice
--

COPY project.project (project_id, hansu_project_id, main_no, name, description, completed, created_at, created_by, updated_at, updated_by) FROM stdin;
51	B108	6652	Testi	\N	f	2017-11-06 07:47:24.889+00	\N	2017-11-06 07:47:24.889+00	\N
50	H3456	6034	Saara-Maijan Malliprojekti	Projektia luotaessa sille annetaan päänumero. Tämän jälkeen projekti hyväksyy suunnitelmia, jotka on nimetty Espoon kaupungin ohjeen mukaisesti päänumero_alanumero. Ensimmäinen portaaliin tuotu suunnitelma on automaattisesti hyväksytty ja sen revisiokirjain on A. Järjestelmä kerää jokaisesta suunnitelmasta versiohistorian. -Saara	f	2017-11-03 08:55:15.832+00	\N	2017-11-06 10:04:26.024+00	\N
53	UP50493	5003	Demo	\N	t	2017-11-22 09:47:04.312+00	\N	2017-11-22 09:59:00.837+00	\N
54	UP928123	5003	Iltapäiväprojekti	\N	t	2017-11-22 12:10:33.324+00	\N	2017-11-22 12:16:38.631+00	\N
52	UP1234	5003	Uusi projekti 22112017	testi	t	2017-11-22 09:24:07.953+00	\N	2017-11-22 13:21:56.302+00	\N
56	B43V	6998	Piilopolku	Piilopoluiden revisioiden jakaminen , testi	f	2017-11-28 07:35:40.787+00	\N	2017-11-28 07:36:08.55+00	\N
57	H4567	5003	Testiprojekti1	Testataan projektin ja suunnitelmien luontia.	t	2018-02-13 06:29:49.021+00	\N	2018-02-15 09:04:27.967+00	\N
58	0987	5003	Testi0803	Testaan maaliskuus	f	2018-03-08 08:12:33.218+00	\N	2018-03-08 08:12:33.218+00	\N
55	HP7654	5003	Testiprojekti maanantaina	Lisätietoja	f	2017-11-27 11:23:01.224+00	\N	2017-11-27 11:23:01.224+00	\N
59	UP9999	5003	Projektin testi2604	projektin kuvaus	f	2018-04-26 07:04:57.437+00	\N	2018-04-26 07:04:57.437+00	\N
60	UP12345	7000	Projekti testausta varten	Testataan tarkastajan roolia.	f	2018-05-03 12:01:21.659+00	\N	2018-05-03 12:01:21.659+00	\N
61	PO1234	7000	Testiprojekti	Testataan projektin tekoa	t	2018-05-23 09:16:28.267+00	\N	2018-05-23 09:21:21.436+00	\N
62	234	2344	asdf	asdf	f	2018-08-22 07:32:42.762+00	\N	2018-08-22 07:32:42.762+00	\N
\.


--
-- TOC entry 3101 (class 0 OID 0)
-- Dependencies: 193
-- Name: ptex_serial; Type: SEQUENCE SET; Schema: project; Owner: pythiaservice
--

SELECT pg_catalog.setval('project.ptex_serial', 134, true);


--
-- TOC entry 3091 (class 0 OID 16914)
-- Dependencies: 194
-- Data for Name: ptext; Type: TABLE DATA; Schema: project; Owner: pythiaservice
--

COPY project.ptext (text_id, plan_id, url, approved, created_at, created_by, updated_at, updated_by, ptext) FROM stdin;
129	555	\N	t	\N	Helvi Hyväksyjä	2017-12-18 09:22:56.595+00	\N	Tämäkin revisio, 6998_020D, puolestani ok, mietin että pitääkö myös esittää tyyppi kuvassa tuö kivituhkakaulus?
128	554	\N	t	\N	Helvi Hyväksyjä	2018-01-09 09:14:25.486+00	\N	Muistaa laittaa kaikille uusille valaisinpylvälle kivituhkakaulus. Muuten revisiokuva 6998_010C, minun puolelsta ok
130	557	\N	f	2018-01-09 11:19:54.835+00	Kalle Katselija	2018-01-09 11:19:54.835+00	\N	Onko kaukolämpöputken sijoitus riittävällä etäisyydellä uusista puu istutuksista?
131	562	\N	f	2018-02-13 06:31:56.001+00	Sirpa Suunnittelija	2018-02-13 06:31:56.001+00	\N	Tämän suunnitelman ensimmäinen kommentti.
132	563	https://kirapythia-comments-bucket.s3-eu-west-1.amazonaws.com/3075_032_0.tif	t	\N	Kalle Katselija	2018-02-13 06:34:49.33+00	\N	Tämä on katselijan kommentti.
133	567	https://kirapythia-comments-bucket.s3-eu-west-1.amazonaws.com/dataflow_0.PNG	f	\N	Kalle Katselija	2018-04-26 07:17:30.601+00	\N	\N
134	570	\N	t	\N	Kalle Katselija	2018-05-23 09:19:02.287+00	\N	Puuttuu valaisin
117	541	\N	t	\N	Pekka Pääkäyttäjä	2018-08-21 09:53:30.452+00	\N	Pekan kommentti
114	541	\N	f	2017-11-03 09:22:50.174+00	\N	2017-11-03 09:22:50.174+00	\N	Puut on säilytettävä! Niillä on merkittävä vaikutus ylläpidon työhön. 
115	541	\N	f	2017-11-03 10:09:38.341+00	Sirpa Suunnittelija	2017-11-03 10:09:38.341+00	\N	Testikommentti
116	541	\N	f	2017-11-03 10:10:17.104+00	Helvi Hyväksyjä	2017-11-03 10:10:17.104+00	\N	Tämä on Helvin kommentti
119	542	\N	f	2017-11-06 10:31:56.756+00	Helvi Hyväksyjä	2017-11-06 10:31:56.756+00	\N	Voisiko tuo olla vähän pyöreämpi?
120	541	\N	t	\N	Kaija Kunnossapito	2017-11-07 13:38:47.616+00	\N	Puut pois, varjostavat liikaa kaunista katukivetystä.
113	541	https://kirapythia-comments-bucket.s3-eu-west-1.amazonaws.com/puut_pois_0.PNG	f	\N	\N	2017-11-07 13:38:58.011+00	\N	Puut pois tästä kohdasta. 
118	541	\N	f	\N	Pekka Pääkäyttäjä	2017-11-07 13:38:58.638+00	\N	Kaikki voivat kommentoida, mutta suunnnittelun tilaaja hyväksyy ne muutokset, joiden perusteella tehdään uusi revisio. 
121	543	https://kirapythia-comments-bucket.s3-eu-west-1.amazonaws.com/EspooRGBN_0.jpg	t	\N	Sirpa Suunnittelija	2017-11-22 09:26:58.769+00	\N	Kommentti ok
122	543	\N	f	2017-11-22 09:27:10.056+00	Helvi Hyväksyjä	2017-11-22 09:27:10.056+00	\N	Ei ok kommentti
124	547	\N	f	2017-11-22 09:49:52.318+00	Kalle Katselija	2017-11-22 09:49:52.318+00	\N	Kommetntiit 2
123	547	https://kirapythia-comments-bucket.s3-eu-west-1.amazonaws.com/EspooRGBN_0.jpg	t	\N	Kalle Katselija	2017-11-22 09:50:47.892+00	\N	Kommentti 1
126	550	\N	f	2017-11-22 12:13:21.887+00	Kalle Katselija	2017-11-22 12:13:21.887+00	\N	Kommentti
125	550	https://kirapythia-comments-bucket.s3-eu-west-1.amazonaws.com/EspooRGBN_0.jpg	t	\N	Kalle Katselija	2017-11-22 12:13:59.769+00	\N	Valopylväät väärässä paikassa
127	553	\N	f	\N	Kalle Katselija	2017-11-27 11:35:12.157+00	\N	Kaivonkansi väärässä paikassa
\.


--
-- TOC entry 3088 (class 0 OID 16759)
-- Dependencies: 191
-- Data for Name: sister_project; Type: TABLE DATA; Schema: project; Owner: pythiaservice
--

COPY project.sister_project (id, project_id, sister_project_id) FROM stdin;
58	50	51
60	54	53
62	57	56
63	58	57
64	59	58
65	59	51
67	61	56
\.


--
-- TOC entry 2965 (class 2606 OID 16987)
-- Name: plan planid_pri; Type: CONSTRAINT; Schema: project; Owner: pythiaservice
--

ALTER TABLE ONLY project.plan
    ADD CONSTRAINT planid_pri PRIMARY KEY (plan_id);


--
-- TOC entry 2959 (class 2606 OID 16763)
-- Name: sister_project project_pri; Type: CONSTRAINT; Schema: project; Owner: pythiaservice
--

ALTER TABLE ONLY project.sister_project
    ADD CONSTRAINT project_pri PRIMARY KEY (id);


--
-- TOC entry 2957 (class 2606 OID 16736)
-- Name: project projectid_pri; Type: CONSTRAINT; Schema: project; Owner: pythiaservice
--

ALTER TABLE ONLY project.project
    ADD CONSTRAINT projectid_pri PRIMARY KEY (project_id);


--
-- TOC entry 2962 (class 2606 OID 16921)
-- Name: ptext ptext_pri; Type: CONSTRAINT; Schema: project; Owner: pythiaservice
--

ALTER TABLE ONLY project.ptext
    ADD CONSTRAINT ptext_pri PRIMARY KEY (text_id);


--
-- TOC entry 2963 (class 1259 OID 16988)
-- Name: plan_id; Type: INDEX; Schema: project; Owner: pythiaservice
--

CREATE INDEX plan_id ON project.plan USING btree (plan_id);


--
-- TOC entry 2955 (class 1259 OID 16745)
-- Name: project_index; Type: INDEX; Schema: project; Owner: pythiaservice
--

CREATE INDEX project_index ON project.project USING btree (project_id);


--
-- TOC entry 2960 (class 1259 OID 16927)
-- Name: ptext_ind; Type: INDEX; Schema: project; Owner: pythiaservice
--

CREATE INDEX ptext_ind ON project.ptext USING btree (text_id);


--
-- TOC entry 2966 (class 2606 OID 16778)
-- Name: sister_project project_fkey; Type: FK CONSTRAINT; Schema: project; Owner: pythiaservice
--

ALTER TABLE ONLY project.sister_project
    ADD CONSTRAINT project_fkey FOREIGN KEY (project_id) REFERENCES project.project(project_id) MATCH FULL;


-- Completed on 2018-08-28 15:28:45 EEST

--
-- PostgreSQL database dump complete
--

