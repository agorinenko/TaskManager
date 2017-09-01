IF EXISTS (SELECT name FROM sys.schemas WHERE name = N'ver')
  BEGIN
    DROP SCHEMA ver
  END