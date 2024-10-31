package org.aquarngd.buyistic.controller;

import com.alibaba.fastjson2.JSONObject;
import org.aquarngd.buyistic.UnifiedResponse;
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
public class AccountsController {
    final
    JdbcTemplate jdbcTemplate;

    public AccountsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public String Login(@RequestParam String username, @RequestParam String password) {
        CheckDatabase();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from buyistic.accounts where username=?", username);
        JSONObject response = new JSONObject();
        if (sqlRowSet.next()) {
            if (password.equals(sqlRowSet.getString("password"))) {
                response.put("status", "login");
                response.put("result", new JSONObject(Map.ofEntries(
                        Map.entry("username", username),
                        Map.entry("userid", sqlRowSet.getString("uuid")))));
            } else {
                response.put("status", "wrongpassword");
            }
        } else {
            UUID uuid = UUID.randomUUID();
            jdbcTemplate.update("insert into `accounts` (`uuid`,`username`,`password`) values (?, ?, ?)",
                    uuid.toString(), username, password);
            response.put("status", "newaccount");
            response.put("result", new JSONObject(Map.ofEntries(
                    Map.entry("username", username),
                    Map.entry("userid", uuid.toString()))));
        }

        return UnifiedResponse.Success(response).toJSONString();
    }
    @GetMapping("/get_user")
    @CrossOrigin(origins = "*")
    public String GetUser(@RequestParam String userid) {
        SqlRowSet sqlRowSet=jdbcTemplate.queryForRowSet("select * from buyistic.accounts where accounts.uuid=?", userid);
        JSONObject response = new JSONObject();
        if (sqlRowSet.next()) {
            response.put("result", new JSONObject(Map.ofEntries(
                    Map.entry("name",sqlRowSet.getString("username"))
            )));
        }
        return UnifiedResponse.Success(response).toJSONString();
    }
    private void CheckDatabase() {
        if (!IsDatabaseExisted())
            jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS buyistic.accounts (
                    uuid TEXT NOT NULL,
                    username TEXT NOT NULL,
                    password TEXT NOT NULL
                    ) CHARACTER SET utf8mb4""");
    }

    private boolean IsDatabaseExisted() {
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            DatabaseMetaData data = connection.getMetaData();
            String[] types = {"TABLE"};
            rs = data.getTables(null, null, "accounts", types);
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
