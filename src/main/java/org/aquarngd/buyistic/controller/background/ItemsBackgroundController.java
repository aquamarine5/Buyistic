package org.aquarngd.buyistic.controller.background;

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
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/background/items")
public class ItemsBackgroundController {

    final
    JdbcTemplate jdbcTemplate;

    public ItemsBackgroundController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/change_property")
    @CrossOrigin(origins = "*")
    public String ChangeProperty(@RequestParam String property, @RequestParam String value, @RequestParam int id) {
        Map<String, String> allowedProperties = Map.of(
                "title", "title",
                "detail", "detail",
                "type", "type",
                "nowprice", "nowprice",
                "rawprice", "rawprice"
        );
        if (!allowedProperties.containsKey(property)) {
            return "error: invalid property";
        }
        String column = allowedProperties.get(property);
        if (Objects.equals(property, "title") || Objects.equals(property, "detail"))
            jdbcTemplate.update("update items set " + column + " = ? where id = ?", value, id);
        else if (Objects.equals(property, "type"))
            jdbcTemplate.update("update items set type = ? where id = ?", Integer.parseInt(value), id);
        else
            jdbcTemplate.update("update items set " + column + " = ? where id = ?", Double.parseDouble(value), id);
        return UnifiedResponse.SuccessSignal().toJSONString();
    }

    @GetMapping("/get_all")
    @CrossOrigin(origins = "*")
    public String GetItems() {
        CheckDatabaseStatus();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM `items`");
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

    @PostMapping("/add")
    @CrossOrigin(origins = "*")
    public String AddItem(
            @RequestParam("imgurl") String imgurl,
            @RequestParam("title") String title,
            @RequestParam("detail") String detail,
            @RequestParam("nowprice") String nowprice,
            @RequestParam("rawprice") String rawprice,
            @RequestParam("categories") String categories,
            @RequestParam("type") int type,
            @RequestParam("introductions") String introductions) {
        CheckDatabaseStatus();
        JSONObject result = new JSONObject();
        try {
            String sql = """
                    INSERT INTO `items` (imgurl, title, detail, nowprice, rawprice, categories, type, introductions) VALUES
                    (?, ?, ?, ?, ?, ?, ?, ?)""";
            jdbcTemplate.update(sql, imgurl, title, detail, Float.parseFloat(nowprice), Float.parseFloat(rawprice), categories, type, introductions);
            result.put("result", "OK");
            return result.toJSONString();
        } catch (Exception e) {
            result.put("result", "ERR");
            result.put("error_msg", e.getMessage());
            return result.toJSONString();
        }

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
