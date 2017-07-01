package ru.taskmanager.tests.args.utils;

import org.junit.Test;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StatementQueueBuilder;
import ru.taskmanager.utils.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StatementQueueBuilderTest {
    @Test
    public void Test() throws IOException {
        String file = SettingsUtils.getScriptPath("test_sql.sql");

        StatementQueueBuilder builder = new StatementQueueBuilder(file, "--SEP--");
        builder.build();
        List<String> statements = builder.getStatements();
        assertEquals(statements.stream().count(), 7);
        assertEquals(statements.get(0), "DROP DATABASE IF EXISTS test1;");
        assertEquals(statements.get(1), "DROP DATABASE IF EXISTS test2; DROP DATABASE IF EXISTS test3;");
        assertEquals(statements.get(2), "DROP DATABASE IF EXISTS test4;");
        assertEquals(statements.get(3), "CREATE DATABASE test WITH OWNER = postgres ENCODING = 'UTF8' TABLESPACE = pg_default CONNECTION LIMIT = -1;");
        assertEquals(statements.get(4), "DROP DATABASE IF EXISTS test5;");
        assertEquals(statements.get(5), "CREATE OR REPLACE VIEW err.V_ERROR_SET AS SELECT ID, PG_EXCEPTION_CONTEXT FROM err.ERROR_SET;");
        assertEquals(statements.get(6), "CREATE OR REPLACE FUNCTION err.INSERT_ERROR( _CREATED_BY varchar(250), _RETURNED_SQLSTATE text ) RETURNS int AS $BODY$ DECLARE ret int; BEGIN INSERT INTO err.error_set( created_by, returned_sqlstate) VALUES ( coalesce(_CREATED_BY, 'SYSTEM'), _RETURNED_SQLSTATE); SELECT lastval() INTO ret; RETURN ret; END; $BODY$ LANGUAGE plpgsql VOLATILE;");
    }
}
