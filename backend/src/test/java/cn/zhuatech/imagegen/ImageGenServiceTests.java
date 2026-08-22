/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.imagegen;

import cn.zhuatech.imagegen.service.ImageGenService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageGenServiceTests {
    private final ImageGenService service = new ImageGenService();

    @Test void createsFourPromptVariations() {
        var result = service.plan(new ImageGenService.Request("现代制造工厂内，工程师查看设备健康看板，画面自然专业，保留标题空间。", "夸张科幻、文字水印", "企业纪实", "16:9", "#187d75", 4, true, true));
        assertThat(result.status()).isEqualTo("READY");
        assertThat(result.variations()).hasSize(4);
    }

    @Test void blocksUnlicensedBrandReference() {
        var result = service.plan(new ImageGenService.Request("为企业产品生成一张专业宣传主视觉图片。", "", "摄影", "1:1", "#1e3955", 1, false, true));
        assertThat(result.status()).isEqualTo("BLOCKED");
    }
}
