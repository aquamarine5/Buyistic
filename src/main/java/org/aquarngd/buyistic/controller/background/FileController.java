package org.aquarngd.buyistic.controller.background;

import com.alibaba.fastjson2.JSONObject;
import org.aquarngd.buyistic.UnifiedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/background/file")
public class FileController {

    @CrossOrigin(origins = "*")
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) {
        Logger logger = Logger.getLogger("FileController");
        logger.info("uploading file");
        JSONObject json = new JSONObject();
        try {
            String filename = UUID.randomUUID()+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String filepath = "/root/imgs/" + filename;
            file.transferTo(new File(filepath));
            json.put("filename", filename);
            return UnifiedResponse.Success(json).toJSONString();
        } catch (IOException e) {
            return UnifiedResponse.Failed(e.getMessage()).toJSONString();
        }
    }
}
