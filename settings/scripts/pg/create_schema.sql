DROP SCHEMA IF EXISTS ver CASCADE;
--SEP--
CREATE SCHEMA ver AUTHORIZATION postgres;
--SEP--
CREATE TABLE ver.VERSION_SET(
  ID  SERIAL PRIMARY KEY,
  VERSION varchar(200) NOT NULL UNIQUE,
  CREATED_AT timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY varchar(250) NOT NULL,
  DELETED_AT timestamp,
  NAME text NULL,
  DESCRIPTION text NULL
);
--SEP--
CREATE OR REPLACE VIEW ver.V_VERSIONS AS
SELECT
  id,
  version,
  created_at,
  created_by,
  name,
  description
FROM
  ver.version_set
WHERE DELETED_AT IS NULL;
--SEP--
CREATE OR REPLACE FUNCTION ver.INSERT_VERSION(
  _version varchar(200),
  _created_by varchar(250),
  _name text,
  _description text
)
  RETURNS int AS
$BODY$
DECLARE
  _LAST_ID integer;
BEGIN
  INSERT INTO ver.version_set(
    version,
    created_by,
    name,
    description
  ) VALUES (
    _version,
    _created_by,
    _name,
    _description
  )
  RETURNING id INTO _LAST_ID;

  return _LAST_ID;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;
--SEP--
CREATE OR REPLACE FUNCTION ver.DELETE_VERSION(
  _version varchar(200)
)
RETURNS int AS
$BODY$
DECLARE
  _ret integer = -1;
BEGIN
  UPDATE ver.version_set SET
  DELETED_AT = CURRENT_TIMESTAMP
  WHERE version = _version;

  GET DIAGNOSTICS _ret = ROW_COUNT;

  return _ret;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;