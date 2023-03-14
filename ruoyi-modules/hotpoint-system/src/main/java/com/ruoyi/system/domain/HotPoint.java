package com.ruoyi.system.domain;

import lombok.Data;

/**
 * zhiyi98
 */
@Data
public class HotPoint {
    private String title;
    private String link;

    public HotPoint() {
    }

    public HotPoint(String title, String link) {
        this.title = title;
        this.link = link;
    }
}
