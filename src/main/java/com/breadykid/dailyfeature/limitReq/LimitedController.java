package com.breadykid.dailyfeature.limitReq;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @description: 限流样例接口
 * @author: Joyce Liu
 */
@RestController
@RequestMapping("limited")
public class LimitedController {
    //每秒创建100个令牌
    private final RateLimiter rateLimiter = RateLimiter.create(400) ;

    @GetMapping("demo")
    public ResponseEntity<String> demo() {
        boolean accepted = rateLimiter.tryAcquire();
        String time = LocalDateTime.now().toString();
        if (accepted) {
            System.out.println(String.format("%s---%s",time,"拿到令牌"));
            return ResponseEntity.ok("成功");
        }
        System.err.println(String.format("%s---%s",time,"降级拒绝"));
        return ResponseEntity.internalServerError().body("降级拒绝");
    }

}
