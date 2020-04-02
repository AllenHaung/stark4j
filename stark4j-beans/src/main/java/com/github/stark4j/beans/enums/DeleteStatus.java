package com.github.stark4j.beans.enums;

/**
 * @author Allen Created 2020/3/30
 */
public enum DeleteStatus implements Stark4jEnums<DeleteStatus, Integer> {
    /**
     * 正常状态
     */
    NORMAL(0),
    /**
     * 删除状态
     */
    DELETE(1);

    private Integer value;

    DeleteStatus(Integer value) {
        this.value = value;
    }

    @Override
    public DeleteStatus getInstance(Integer value) {
        for (DeleteStatus deleteStatus : DeleteStatus.values()) {
            if (deleteStatus.getValue().equals(value)) {
                return deleteStatus;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
