package cn.sangedon.redis.controller;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/put")
    public String put(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value, 20, TimeUnit.SECONDS);
        return "SUC";
    }

    @GetMapping("/get")
    public String put(@RequestParam String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
