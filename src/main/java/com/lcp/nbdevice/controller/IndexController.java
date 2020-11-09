package com.lcp.nbdevice.controller;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Map;

import javax.validation.constraints.Null;

@RequestMapping
@RestController
public class IndexController {

    private static String path = "";

    private static String fileName = "AppData";

    static {
        try {
            path = new ApplicationHome(IndexController.class).getSource().getParentFile().toString() + "/";
        }catch (Throwable e) {
        }
    }

    @PostMapping({ "/", "" })
    public Object index(@RequestBody Map<String, Object> map) {
        // 解密
        File file = new File(path + fileName);
        try (FileOutputStream fos = new FileOutputStream(file, true);) {
            Map<String, String> payload = (Map) map.get("payload");
            String appData = payload.get("APPdata");
            byte[] bytes = Base64.getDecoder().decode(appData);
            if (!file.isFile()) {
                file.createNewFile();
            }
            fos.write(bytes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "{\"msg\":\"OK\"}";
    }

    @GetMapping({ "/", "" })
    public Object getBb() {
        int i = 0;
        String ret = "";
        StringBuilder s = new StringBuilder();
        int lenth = 0;
        try {
            FileInputStream is = new FileInputStream(new File(path + fileName));
            byte[] bytes = new byte[1024];
            int n = 0;
            while ((n = is.read(bytes)) != -1) {
                s.append(new String(bytes));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        for(;i<s.toString().length();i++)
        {
            if(s.charAt(i) == 0x68)
            {
                lenth = s.charAt(i+30);
                i+=31;
                for(int j=0;j<lenth && (i<toString().length()-6);j++)
                {
                   ret =  String.format("%d 时间:%hhu-%hhu-%hhu %hhu:%hhu:%hhu", s.charAt(i + 1), s.charAt(i + 2),
                            s.charAt(i + 3), s.charAt(i + 4), s.charAt(i + 5), s.charAt(i + 6)) + "\n";
                            i+=8;
                } 
            }
        }
        return ret;
    }
}
