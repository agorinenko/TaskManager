IF OBJECT_ID ('ver.DELETE_VERSION', 'P' ) IS NOT NULL
  DROP PROCEDURE ver.DELETE_VERSION;
--SEP--
IF OBJECT_ID('ver.VERSION_SET', 'U') IS NOT NULL
 DROP TABLE ver.VERSION_SET;
--SEP--
IF OBJECT_ID ('ver.INSERT_VERSION', 'P' ) IS NOT NULL
  DROP PROCEDURE ver.INSERT_VERSION;
--SEP--
IF OBJECT_ID ('ver.V_VERSIONS',  'V') IS NOT NULL
  DROP VIEW ver.V_VERSIONS;
--SEP--
IF EXISTS (SELECT name FROM sys.schemas WHERE name = N'ver')
  BEGIN
    DROP SCHEMA ver
  END
--SEP--
CREATE SCHEMA [ver] AUTHORIZATION [dbo]
--SEP--
IF EXISTS (SELECT name FROM sys.schemas WHERE name = N'test')
  BEGIN
    DROP SCHEMA test;
  END
--SEP--
CREATE SCHEMA [test] AUTHORIZATION [dbo]
--SEP--
CREATE TABLE ver.VERSION_SET(
  ID INT NOT NULL IDENTITY,
  VERSION varchar(200) NOT NULL,
  CREATED_AT DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY varchar(250) NOT NULL,
  DELETED_AT DATETIME,
  NAME text NULL,
  DESCRIPTION text NULL,
  CONSTRAINT PK_VERSION_SET_ID PRIMARY KEY (ID),
  CONSTRAINT VERSION_DELETED_AT_CONSTRAINT UNIQUE (VERSION, DELETED_AT)
);
--SEP--
CREATE VIEW ver.V_VERSIONS AS
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
CREATE PROCEDURE ver.INSERT_VERSION(
  @_version varchar(200),
  @_created_by varchar(250),
  @_name text,
  @_description text
)
AS
  BEGIN
    SET NOCOUNT ON;
    DECLARE @_LAST_ID int;
    INSERT INTO ver.version_set(
      version,
      created_by,
      name,
      description
    ) VALUES (
      @_version,
      @_created_by,
      @_name,
      @_description
    )

    SET @_LAST_ID = @@IDENTITY;

    select @_LAST_ID as id;
  END;
--SEP--
CREATE PROCEDURE ver.DELETE_VERSION(
  @_version varchar(200)
) AS
  BEGIN
    SET NOCOUNT ON;
    DECLARE @_ret int = -1;

    UPDATE ver.version_set SET
      DELETED_AT = CURRENT_TIMESTAMP
    WHERE version = @_version;

    SET @_ret = @@ROWCOUNT;

    select @_ret as res;
  END;
--SEP--