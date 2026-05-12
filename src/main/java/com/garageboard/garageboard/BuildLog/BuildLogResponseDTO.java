package com.garageboard.garageboard.BuildLog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildLogResponseDTO {
    private long id;
    private String content;

    public BuildLogResponseDTO(BuildLog buildLog) {
        this.id = buildLog.getId(); // useful while in dev, might remove in production
        this.content = buildLog.getContent();
    }
}
