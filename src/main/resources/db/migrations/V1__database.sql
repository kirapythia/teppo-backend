CREATE TYPE project.status_enum AS ENUM (
    'WAITING_FOR_REVIEW',
    'WAITING_FOR_REVISION',
    'WAITING_FOR_APPROVAL',
    'APPROVED',
    'REVERTED'
);

CREATE CAST (character varying AS project.status_enum) WITH INOUT AS IMPLICIT;

-- ALTER TYPE project.status_enum OWNER TO "${application_user}";;

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
    dwg_url character varying,
    dxf_url character varying,
    svg_url character varying,
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


-- ALTER TABLE project.plan OWNER TO tepposervice;

CREATE SEQUENCE project.plan_serial
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


-- ALTER TABLE project.plan_serial OWNER TO tepposervice;



CREATE TABLE project.ptext (
    ptext_id bigint NOT NULL,
    plan_id bigint,
    url character varying,
    approved boolean,
    approved_by character varying,
    created_at timestamp with time zone,
    created_by character varying,
    updated_at timestamp with time zone,
    updated_by character varying,
    ptext character varying,
    xcoord double precision,
    ycoord double precision,
    xwidth double precision,
    yheight double precision

);


-- ALTER TABLE project.ptext OWNER TO tepposervice;

CREATE SEQUENCE project.ptext_serial
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 2147483647
    CACHE 1;


-- ALTER TABLE project.ptext_serial OWNER TO tepposervice;

CREATE TABLE project.sister_project (
    id bigint NOT NULL,
    project_id bigint,
    sister_project_id bigint
);


-- ALTER TABLE project.sister_project OWNER TO tepposervice;

CREATE VIEW project.latest_plans AS
 SELECT p1.plan_id,
    p1.project_id,
    p1.main_no,
    p1.sub_no,
    p1.version,
    p1.pdf_url,
    p1.xml_url,
    p1.dwg_url,
    p1.dxf_url,
    p1.svg_url,
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
    p1.dwg_url,
    p1.dxf_url,
    p1.svg_url,
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

-- ALTER TABLE latest_plans OWNER TO pythiaservice;