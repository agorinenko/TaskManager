DROP SCHEMA IF EXISTS ver CASCADE;
--SEP--
CREATE SCHEMA ver AUTHORIZATION postgres;
--SEP--
CREATE TABLE ver.VERSION_SET(
  VERSION varchar(200) NOT NULL PRIMARY KEY,
  CREATED_AT timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY varchar(250) NOT NULL,
  DESCRIPTION text NULL
);
--SEP--
CREATE OR REPLACE FUNCTION ver.INSERT_VERSION(
  _version varchar(200),
  _created_by varchar(250),
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
    description
  ) VALUES (
    _version,
    _created_by,
    _description
  )
  RETURNING id INTO _LAST_ID;

  return _LAST_ID;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;
