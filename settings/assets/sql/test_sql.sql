
/* Test */
IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'test1')
  BEGIN
    ALTER DATABASE [test1] SET SINGLE_USER WITH ROLLBACK IMMEDIATE
    DROP DATABASE [test1]
  END
--SEP--
CREATE DATABASE [test1] COLLATE Cyrillic_General_CI_AS;

--SEP--

IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'test2')
  BEGIN
    ALTER DATABASE [test2] SET SINGLE_USER WITH ROLLBACK IMMEDIATE
    DROP DATABASE [test2]
  END
--SEP--
CREATE DATABASE [test2] COLLATE Cyrillic_General_CI_AS;
/*
  * Test */IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'test3')
  BEGIN
    ALTER DATABASE [test3] SET SINGLE_USER WITH ROLLBACK IMMEDIATE
    DROP DATABASE [test3]
  END
--SEP--
CREATE DATABASE [test3] COLLATE Cyrillic_General_CI_AS;
/*
  * Test */--SEP--
IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'test4')
  BEGIN
    ALTER DATABASE [test4] SET SINGLE_USER WITH ROLLBACK IMMEDIATE
    DROP DATABASE [test4]
  END
--SEP--
CREATE DATABASE [test4] COLLATE Cyrillic_General_CI_AS; /* Test */

--SEP--
CREATE DATABASE [test] COLLATE Cyrillic_General_CI_AS;
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
IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'test5')
  BEGIN
    ALTER DATABASE [test5] SET SINGLE_USER WITH ROLLBACK IMMEDIATE
    DROP DATABASE [test5]
  END
--SEP--
CREATE DATABASE [test5] COLLATE Cyrillic_General_CI_AS;
--SEP--
-- ------------- Create views -------------
IF OBJECT_ID ('err.V_ERROR_SET',  'V') IS NOT NULL
  DROP VIEW err.V_ERROR_SET;
--SEP--
CREATE VIEW err.V_ERROR_SET AS
  SELECT
    ID,
    PG_EXCEPTION_CONTEXT
  FROM
    err.ERROR_SET;
--SEP--
IF OBJECT_ID ('err.INSERT_ERROR', 'P' ) IS NOT NULL
  DROP PROCEDURE err.INSERT_ERROR;
--SEP--
CREATE PROCEDURE err.INSERT_ERROR(
  @_CREATED_BY varchar(250),
  @_RETURNED_SQLSTATE text
) AS
BEGIN
  DECLARE @ret int;
  INSERT INTO err.error_set(
    created_by,
    returned_sqlstate)
  VALUES (
    coalesce(@_CREATED_BY, 'SYSTEM'),
    @_RETURNED_SQLSTATE);

  SELECT lastval() INTO ret;
  RETURN ret;
END;

