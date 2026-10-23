/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.imagegen.practical;

import static cn.zhuatech.imagegen.practical.PracticalInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class PracticalService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "width", "height", "count", "maxBatchPixels", "maxBatchImages", "baseSeed");
    int width = integer(r, "width", 64, 8192),
        height = integer(r, "height", 64, 8192),
        count = integer(r, "count", 1, 100),
        limit = integer(r, "maxBatchPixels", 1, 2000000000),
        max = integer(r, "maxBatchImages", 1, 16),
        seed = integer(r, "baseSeed", 0, 2000000000);
    if (width % 8 != 0 || height % 8 != 0) throw new IllegalArgumentException("宽高须为8的倍数");
    long pixels = (long) width * height;
    int capacity = (int) Math.min(max, limit / pixels);
    List<Map<String, Object>> batches = new ArrayList<>();
    if (capacity > 0)
      for (int index = 0; index < count; index += capacity) {
        int size = Math.min(capacity, count - index);
        List<Long> seeds = new ArrayList<>();
        for (int i = 0; i < size; i++) seeds.add((long) seed + index + i);
        batches.add(
            Map.of(
                "batch",
                batches.size() + 1,
                "imageCount",
                size,
                "pixels",
                pixels * size,
                "seeds",
                seeds));
      }
    return Map.of(
        "fitsSingleImage",
        capacity > 0,
        "batchCapacity",
        capacity,
        "batches",
        batches,
        "requestedImages",
        count,
        "totalPixels",
        pixels * count,
        "generationPerformed",
        false);
  }
}
