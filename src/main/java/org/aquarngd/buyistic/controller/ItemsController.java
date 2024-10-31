package org.aquarngd.buyistic.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.aquarngd.buyistic.UnifiedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@RestController
public class ItemsController {
    final
    JdbcTemplate jdbcTemplate;

    public ItemsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/get_item")
    @CrossOrigin(origins = "*")
    public String GetItem(@RequestParam("id") String id) {
        CheckDatabaseStatus();
        String query = "SELECT * FROM `items` WHERE id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(query, id);
        JSONObject result = new JSONObject();
        if (sqlRowSet.next()) {
            result.put("result", new JSONObject(Map.ofEntries(
                    Map.entry("imgurl", sqlRowSet.getString("imgurl")),
                    Map.entry("nowprice", sqlRowSet.getFloat("nowprice")),
                    Map.entry("rawprice", sqlRowSet.getFloat("rawprice")),
                    Map.entry("title", sqlRowSet.getString("title")),
                    Map.entry("detail", sqlRowSet.getString("detail")),
                    Map.entry("id", sqlRowSet.getInt("id")),
                    Map.entry("categories", sqlRowSet.getString("categories")),
                    Map.entry("type", sqlRowSet.getInt("type")),
                    Map.entry("introductions", sqlRowSet.getString("introductions"))
            )));
            return UnifiedResponse.Success(result).toJSONString();
        } else {
            return UnifiedResponse.Failed("ITEM_NOT_EXIST").toJSONString();
        }
    }

    @GetMapping("/get_items")
    @CrossOrigin(origins = "*")
    public String GetItems() {
        CheckDatabaseStatus();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM `items` WHERE type = 1");
        JSONObject result = new JSONObject();
        JSONArray resultSet = new JSONArray();
        while (sqlRowSet.next()) {
            resultSet.add(new JSONObject(Map.ofEntries(
                    Map.entry("imgurl", sqlRowSet.getString("imgurl")),
                    Map.entry("nowprice", sqlRowSet.getFloat("nowprice")),
                    Map.entry("rawprice", sqlRowSet.getFloat("rawprice")),
                    Map.entry("title", sqlRowSet.getString("title")),
                    Map.entry("detail", sqlRowSet.getString("detail")),
                    Map.entry("id", sqlRowSet.getInt("id")),
                    Map.entry("type", sqlRowSet.getInt("type"))
            )));
        }
        result.put("data", resultSet);
        return UnifiedResponse.Success(result).toJSONString();
    }

    private void CheckDatabaseStatus() {
        if (!IsDatabaseExisted()) {
            jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS buyistic.items (
                    imgurl TEXT NOT NULL,
                    nowprice FLOAT NOT NULL,
                    rawprice FLOAT NOT NULL,
                    title TEXT NOT NULL,
                    detail TEXT NOT NULL,
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    categories TEXT NOT NULL,
                    type INT NOT NULL,
                    introductions TEXT NOT NULL
                    ) CHARACTER SET utf8mb4""");
        }
    }

    private boolean IsDatabaseExisted() {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            DatabaseMetaData data = connection.getMetaData();
            String[] types = {"TABLE"};
            rs = data.getTables(null, null, "items", types);
            if (rs.next()) return true;
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) rs.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
            }
        }
        return false;
    }
}
