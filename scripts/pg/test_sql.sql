
/* Test */
DROP DATABASE IF EXISTS test1;

--SEP--

DROP DATABASE IF EXISTS test2;
/*
  * Test */DROP DATABASE IF EXISTS test3;
/*
  * Test */--SEP--
DROP DATABASE IF EXISTS test4; /* Test */

--SEP--
CREATE DATABASE test
WITH OWNER = postgres
ENCODING = 'UTF8'
TABLESPACE = pg_default
CONNECTION LIMIT = -1;
-- Test --
-- Test
--
--SEP--
/*
                Test
  -----
   *
   */
-- Test
DROP DATABASE IF EXISTS test5;
--SEP--
-- ------------- Create views -------------
CREATE OR REPLACE VIEW err.V_ERROR_SET AS
  SELECT
    ID,
    PG_EXCEPTION_CONTEXT
  FROM
    err.ERROR_SET;
--SEP--
CREATE OR REPLACE FUNCTION err.INSERT_ERROR(
  _CREATED_BY varchar(250),
  _RETURNED_SQLSTATE text
) RETURNS int AS
$BODY$
DECLARE
  ret int;
BEGIN
  INSERT INTO err.error_set(
    created_by,
    returned_sqlstate)
  VALUES (
    coalesce(_CREATED_BY, 'SYSTEM'),
    _RETURNED_SQLSTATE);

  SELECT lastval() INTO ret;
  RETURN ret;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

