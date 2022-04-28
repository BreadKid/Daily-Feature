package com.breadykid.dailyfeature.verifyCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @description: 图验接口
 * @author: Joyce Liu
 */
@RestController
@RequestMapping("captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService service;

    @GetMapping("base64")
    public ResponseEntity<String> getBase64Str(@RequestParam String randomId) {
        return ResponseEntity.ok(service.imageToBase64(randomId));
    }

    @GetMapping("image")
    @ResponseBody
    public void getImageResponse(@RequestParam String randomId, HttpServletResponse response) {
        service.image(randomId, response);
    }

    @GetMapping("verify")
    public ResponseEntity<Boolean> verify(@RequestParam String value,@RequestParam String randomId) {
        return ResponseEntity.ok(service.existVerifyMapping(value, randomId));
    }
}
