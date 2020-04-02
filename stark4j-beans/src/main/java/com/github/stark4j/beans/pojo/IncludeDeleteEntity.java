package com.github.stark4j.beans.pojo;

import com.github.stark4j.beans.Stark4jVersion;
import com.github.stark4j.beans.enums.DeleteStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Allen Created 2020/3/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IncludeDeleteEntity extends BaseEntity {
    /**
     * 序列化id
     */
    private static final long serialVersionUID = Stark4jVersion.STARK4J_SERIAL_VERSION_ID;
    /**
     * 逻辑删除状态
     */
    private DeleteStatus isDelete;

}
