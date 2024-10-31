package org.aquarngd.buyistic.controller.background;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.aquarngd.buyistic.UnifiedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/background/orders")
public class OrdersBackgroundController {
    final
    JdbcTemplate jdbcTemplate;

    public OrdersBackgroundController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @GetMapping("/get_all")
    @CrossOrigin(origins = "*")
    public String getAll() {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from orders order by createTime desc");
        JSONObject result=new JSONObject();
        JSONArray orders=new JSONArray();
        while (rs.next()) {
            JSONObject order=new JSONObject();
            order.put("orderid", rs.getString("orderid"));
            order.put("status", rs.getInt("status"));
            order.put("createTime", rs.getTimestamp("createTime"));
            order.put("itemid", rs.getInt("itemid"));
            order.put("price", rs.getDouble("price"));
            order.put("userid",rs.getString("userid"));
            orders.add(order);
        }
        result.put("orders", orders);
        return UnifiedResponse.Success(result).toJSONString();
    }
    @GetMapping("/change_status")
    @CrossOrigin(origins = "*")
    public String changeStatus(@RequestParam String orderid, @RequestParam int status) {
        jdbcTemplate.update("update orders set status = ? where orderid = ?", status, orderid);
        return UnifiedResponse.SuccessSignal().toJSONString();
    }

    @GetMapping("/remove")
    @CrossOrigin(origins = "*")
    public String remove(@RequestParam String orderid) {
        jdbcTemplate.update("delete from orders where orderid = ?", orderid);
        return UnifiedResponse.SuccessSignal().toJSONString();
    }
}
