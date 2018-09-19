CREATE SCHEMA project;
-- ALTER SCHEMA project OWNER TO tepposervice;

CREATE TYPE project.status_enum AS ENUM (
    'WAITING_FOR_REVIEW',
    'WAITING_FOR_REVISION',
    'WAITING_FOR_APPROVAL',
    'APPROVED',
    'REVERTED'
);

-- ALTER TYPE project.status_enum OWNER TO tepposervice;

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


-- ALTER TABLE project.project OWNER TO tepposervice;

CREATE SEQUENCE project.proj_serial
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


-- ALTER TABLE project.proj_serial OWNER TO tepposervice;


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


-- ALTER TABLE project.plan OWNER TO tepposervice

CREATE SEQUENCE project.plan_serial
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


-- ALTER TABLE project.plan_serial OWNER TO tepposervice;



CREATE TABLE project.ptext (
    text_id bigint NOT NULL,
    plan_id bigint,
    url character varying,
    approved boolean,
    created_at timestamp with time zone,
    created_by character varying,
    updated_at timestamp with time zone,
    updated_by character varying,
    ptext character varying,
    xcoord decimal,
    ycoord decimal,
    xheight smallint,
    ywidth smallint

);


-- ALTER TABLE project.ptext OWNER TO tepposervice;

CREATE SEQUENCE project.ptex_serial
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


-- ALTER TABLE project.ptex_serial OWNER TO tepposervice;

CREATE TABLE project.sister_project (
    id bigint NOT NULL,
    project_id bigint,
    sister_project_id bigint
);


-- ALTER TABLE project.sister_project OWNER TO tepposervice;