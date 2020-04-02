package com.github.stark4j.beans.pojo;

import com.github.stark4j.beans.Stark4jVersion;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Allen Created 2020/4/2
 */
@Data
public class StringBaseEntity implements Serializable {

    private static final long serialVersionUID = Stark4jVersion.STARK4J_SERIAL_VERSION_ID;

    /**
     * 创建时间
     */
    private LocalDateTime createdDate;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 最后更新时间
     */
    private LocalDateTime updatedDate;
    /**
     * 最后更新人
     */
    private String updatedBy;

}
