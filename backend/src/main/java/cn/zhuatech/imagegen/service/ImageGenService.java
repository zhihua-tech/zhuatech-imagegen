/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.imagegen.service;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class ImageGenService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result plan(Request request) {
        List<String> checks = new ArrayList<>();
        checks.add(request.brandAuthorized() ? "品牌名称与参考素材授权已确认" : "品牌或参考素材授权未确认");
        checks.add(request.prompt().length() >= 20 ? "画面描述信息充足" : "建议补充主体、场景、光线与构图");
        checks.add(request.disclosureEnabled() ? "AI 生成内容标识已开启" : "建议开启 AI 生成内容标识");
        boolean sensitive = request.prompt().matches(".*(身份证|银行卡|真实客户隐私|冒充).*" );
        checks.add(sensitive ? "发现需要人工复核的敏感内容" : "未发现演示规则中的敏感内容");

        String status = !request.brandAuthorized() ? "BLOCKED" : sensitive ? "REVIEW_REQUIRED" : request.prompt().length() < 20 ? "NEEDS_PROMPT" : "READY";
        List<Variation> variations = new ArrayList<>();
        String[] compositions = {"主体居左，右侧保留标题空间", "中心稳定构图，前后景分明", "低视角纵深构图，强调规模", "三分法构图，环境信息充分"};
        for (int i = 0; i < request.imageCount(); i++) {
            variations.add(new Variation(i + 1, compositions[i], request.style(), request.aspectRatio(),
                request.prompt() + "；" + compositions[i] + "；企业级视觉；主色 " + request.brandColor()));
        }
        return new Result(status, variations.size(), List.copyOf(variations), List.copyOf(checks),
            Map.of("prompt", request.prompt(), "negativePrompt", request.negativePrompt(), "style", request.style(),
                "aspectRatio", request.aspectRatio(), "count", request.imageCount(), "disclosureEnabled", request.disclosureEnabled()),
            "LOCAL_PROMPT_PLANNER");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank @Size(max = 3000) String prompt,
                          @Size(max = 1000) String negativePrompt,
                          @NotBlank String style,
                          @NotBlank String aspectRatio,
                          @Pattern(regexp = "^#[0-9a-fA-F]{6}$") String brandColor,
                          @Min(1) @Max(4) int imageCount,
                          boolean brandAuthorized,
                          boolean disclosureEnabled) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Variation(int sequence, String composition, String style, String aspectRatio, String providerPrompt) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(String status, int variationCount, List<Variation> variations,
                         List<String> checks, Map<String, Object> providerPayload, String executionMode) {}
}
