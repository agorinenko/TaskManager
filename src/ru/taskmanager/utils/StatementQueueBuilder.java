package ru.taskmanager.utils;

import ru.taskmanager.args.params.KeyValueParam;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StatementQueueBuilder {
    private Path filePath;
    private String separator;
    private List<String> statements;
    /*
    * START             начало
    * APPENDED          добавлено
    * COMMENT_OPEN      комментарий открыт
    * COMMENT_CLOSE     комментарий закрыт
    * */
    private String state;
    private boolean appendStatement;


    public StatementQueueBuilder(String file, String separator) throws FileNotFoundException {

        this.filePath = Paths.get(file).normalize();
        if(!Files.exists(filePath)) throw new FileNotFoundException(file);
        this.separator = separator;
    }

    public void build(List<KeyValueParam> params) {
        statements = new ArrayList<>();
        setState("START");
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(filePath);

            try {
                LineNumberReader lineReader = new LineNumberReader(reader);
                StringBuilder builder =  new StringBuilder();
                String line;
                while ((line = lineReader.readLine()) != null) {
                    line = StringUtils.trim(line, " ");
                    line = step(line);

                    if (!StringUtils.isNullOrEmpty(line)) {
                        builder.append(line);
                        builder.append(" ");
                    }

                    if(appendStatement){
                        appendStatement(params, builder);
                        builder = new StringBuilder();
                    }
                }

                if(!appendStatement) {
                    appendStatement(params, builder);
                }

            }finally {
                if(null != reader) reader.close();
            }
        } catch (IOException e) {

        }
    }

    private String step(String str){
        if(StringUtils.isNullOrEmpty(str)){
            return "";
        }

        if(StringUtils.containsIgnoreCase(str, separator)){
            appendStatement = true;
        }

        if(str.contains("/*")) {
            setState("COMMENT_OPEN");
        }

        if(str.contains("*/")) {
            setState("COMMENT_CLOSE");
        }

        if(state.equalsIgnoreCase("COMMENT_OPEN")){
            return "";
        }

        int i = str.indexOf("--");
        int b = str.indexOf("/*");
        int c = str.indexOf("*/");

        if(i >= 0) {
            str = str.substring(0, i);
        }

        if(b >= 0 && c > 0) {
            str = String.format("%1$s%2$s", str.substring(0, b), str.substring(c+2, str.length()));
        } else if(b >= 0) {
            str = str.substring(0, b);
        } else if(c >= 0) {
            str = str.substring(c + 2, str.length());
        }

        return str;
    }

    private void setState(String state){
        this.state = state;
    }

    public List<String> getStatements() {
        return statements;
    }

    public void appendStatement(List<KeyValueParam> params, StringBuilder builder) {
        String sql = builder.toString();
        sql = StringUtils.trim(sql, " ");
        int i = sql.lastIndexOf(this.separator);
        if(i >= 0){
            sql = sql.substring(0, i);
        }
        //sql = StringUtils.trim(sql, Pattern.quote(this.separator));

        if (!StringUtils.isNullOrEmpty(sql)) {
            sql = StringUtils.replaceAllDbConstants(params, sql);
            statements.add(sql);

            appendStatement = false;
            setState("APPENDED");
        }
    }
}
