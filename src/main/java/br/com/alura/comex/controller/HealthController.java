package br.com.alura.comex.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthController {
    // do this - keys get ordered alfabetically
    @RequestMapping("/health")
    @ResponseBody
    public Map<String, Object> index() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "ok");
        map.put("time", new Date());
        map.put("c", "ok");
        map.put("b", "ok");
        map.put("a", "ok");
        return map;
    }

    // or that? - keys ordered as defined by attributes and then methods
    class Dto {
        public Date time;
        public String message;
        public String getMessage() {
            return message;
        }
        public Date getTime() {
            return time;
        }
        public String getC() {
            return "ok";
        }
        public String getB() {
            return "ok";
        }
        public String getA() {
            return "ok";
        }
    }
    @RequestMapping("/health_dto")
    @ResponseBody
    public Dto indexDto() {
        Dto dto = new Dto();
        dto.message = "ok";
        dto.time = new Date();
        return dto;
    }
}
