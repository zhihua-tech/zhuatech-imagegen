/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.imagegen.controller;

import cn.zhuatech.imagegen.service.ImageGenService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/imagegen")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class ImageGenController {
    private final ImageGenService service;
    public ImageGenController(ImageGenService service) { this.service = service; }
    @PostMapping("/plan") public ImageGenService.Result plan(@Valid @RequestBody ImageGenService.Request request) { return service.plan(request); }
}
