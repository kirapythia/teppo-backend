-- Make default grants to application role for objects created by flyway
CREATE SCHEMA project;
ALTER SCHEMA project OWNER TO "${application_user}";

-- GRANT CONNECT, CREATE ON DATABASE teppo TO "${application_user}";
-- GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA project TO "${application_user}";

ALTER DEFAULT PRIVILEGES IN SCHEMA project GRANT SELECT, TRIGGER, INSERT, UPDATE, DELETE ON TABLES TO "${application_user}";
ALTER DEFAULT PRIVILEGES IN SCHEMA project GRANT USAGE, SELECT ON SEQUENCES TO "${application_user}";
ALTER DEFAULT PRIVILEGES IN SCHEMA project GRANT EXECUTE ON FUNCTIONS TO "${application_user}";