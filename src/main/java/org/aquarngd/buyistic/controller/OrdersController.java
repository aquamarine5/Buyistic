package org.aquarngd.buyistic.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.UUID;

@RestController
public class OrdersController {
    final
    JdbcTemplate jdbcTemplate;

    public OrdersController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/get_order")
    @CrossOrigin(origins = "*")
    public String GetOrder(@RequestParam String orderid) {
        CheckDatabase();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from orders where orderid = ?", orderid);
        JSONObject jsonObject = new JSONObject();
        if (rowSet.next()) {
            jsonObject.put("orderid", rowSet.getString("orderid"));
            jsonObject.put("status", rowSet.getInt("status"));
            jsonObject.put("createTime", rowSet.getTimestamp("createTime"));
            jsonObject.put("itemid", rowSet.getInt("itemid"));
            jsonObject.put("price", rowSet.getDouble("price"));
        }
        return jsonObject.toJSONString();
    }

    @GetMapping("/orders")
    @CrossOrigin(origins = "*")
    public String GetOrders(@RequestParam String userid) {
        CheckDatabase();
        SqlRowSet sqlRowSet=jdbcTemplate.queryForRowSet("select * from buyistic.orders where userid = ?", userid);
        JSONObject response=new JSONObject();
        JSONArray jsonArray=new JSONArray();

        while (sqlRowSet.next()) {
            jsonArray.add(new JSONObject(Map.ofEntries(
                    Map.entry("orderid", sqlRowSet.getString("orderid")),
                    Map.entry("itemid",sqlRowSet.getInt("itemid")),
                    Map.entry("createTime",sqlRowSet.getTimestamp("createTime")),
                    Map.entry("status",sqlRowSet.getInt("status")),
                    Map.entry("price",sqlRowSet.getDouble("price"))
            )));
        }
        response.put("result",jsonArray);
        return response.toJSONString();
    }

    @GetMapping("/buyit")
    @CrossOrigin(origins = "*")
    public String BuyItem(@RequestParam String userid, @RequestParam String itemid,@RequestParam double price) {
        CheckDatabase();
        UUID uuid = UUID.randomUUID();
        String sql = "INSERT INTO buyistic.orders (orderid, itemid, createTime, status, price, userid) VALUES (?, ?, NOW(), ?, ?, ?)";
        jdbcTemplate.update(sql, uuid.toString(), itemid, 1, price, userid);
        JSONObject response=new JSONObject();
        response.put("orderid",uuid.toString());
        return response.toJSONString();
    }

    private void CheckDatabase() {
        if(!IsDatabaseExisted())
            jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS buyistic.orders (
                orderid TEXT NOT NULL,
                itemid INT NOT NULL,
                createTime TIMESTAMP NOT NULL,
                status INT NOT NULL,
                price DOUBLE NOT NULL,
                userid TEXT NOT NULL
                    ) CHARACTER SET utf8mb4""");
    }
    private boolean IsDatabaseExisted() {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            DatabaseMetaData data = connection.getMetaData();
            String[] types = {"TABLE"};
            rs = data.getTables(null, null, "orders", types);
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
