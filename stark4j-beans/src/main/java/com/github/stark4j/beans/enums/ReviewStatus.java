package com.github.stark4j.beans.enums;

/**
 * @author Allen Created 2020/4/2
 */
public enum  ReviewStatus implements Stark4jEnums<ReviewStatus, Integer>  {
    /**
     * 尚未审核。
     */
    UN_REVIEWED(1),
    /**
     * 同意，审核通过。
     */
    APPROVE(2),
    /**
     * 拒绝，审批不通过。
     */
    REFUSE(3);

    private int value;

    ReviewStatus(int value){
        this.value = value;
    }

    @Override
    public ReviewStatus getInstance(Integer value) {
        for (ReviewStatus reviewStatus : ReviewStatus.values()) {
            if (reviewStatus.getValue().equals(value)) {
                return reviewStatus;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

}
