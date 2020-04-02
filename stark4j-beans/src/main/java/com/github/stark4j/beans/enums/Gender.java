package com.github.stark4j.beans.enums;

/**
 * @author Allen Created 2020/4/2
 */
public enum Gender implements Stark4jEnums<Gender,Integer>  {
    /**
     * 男性
     */
    MALE(0),
    /**
     * 女性
     */
    FEMALE(1);

    private int value;

    Gender(int value) {
        this.value = value;
    }

    @Override
    public Gender getInstance(Integer value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
