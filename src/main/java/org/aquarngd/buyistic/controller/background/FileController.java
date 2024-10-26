package org.aquarngd.buyistic.controller.background;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/background/file")
public class FileController {

    @CrossOrigin(origins = "*")
    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        JSONObject json = new JSONObject();
        try {
            String filename = file.getOriginalFilename();
            String filepath = "/root/imgs" + UUID.randomUUID() + filename.substring(filename.lastIndexOf(".") + 1);
            file.transferTo(new File(filepath));
            json.put("filename", filename);
            json.put("status", "success");
            return json.toJSONString();
        } catch (IOException e) {
            json.put("status", "error");
            json.put("errmsg", e.getMessage());
            return json.toJSONString();
        }
    }
}
