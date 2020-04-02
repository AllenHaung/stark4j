package com.github.stark4j.beans.pojo;

import com.github.stark4j.beans.enums.DeleteStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Allen Created 2020/4/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StringIncludeDeleteEntity extends IncludeDeleteEntity {

    private DeleteStatus isDelete;

}
