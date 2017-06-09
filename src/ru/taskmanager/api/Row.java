package ru.taskmanager.api;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Row {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public void init(ResultSet rs) throws SQLException {
//        setId(rs.getInt("id"));
//        mapFields(rs);
//    }

    //protected abstract void mapFields(ResultSet rs) throws SQLException;

    //public abstract void update();
}
