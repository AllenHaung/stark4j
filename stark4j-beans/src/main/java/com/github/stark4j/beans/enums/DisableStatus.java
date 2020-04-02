package com.github.stark4j.beans.enums;

/**
 * @author Allen Created 2020/3/30
 */
public enum DisableStatus implements Stark4jEnums<DisableStatus, Integer> {
    /**
     * 正常
     */
    ENABLE(0),
    /**
     * 禁用
     */
    DISABLE(1);

    DisableStatus(Integer value) {
        this.value = value;
    }

    private Integer value;

    @Override
    public DisableStatus getInstance(Integer value) {
        for (DisableStatus disableStatus : DisableStatus.values()) {
            if (disableStatus.getValue().equals(value)) {
                return disableStatus;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
