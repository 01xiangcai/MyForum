package com.yao.common;

//评论类型
public enum CommentTypeEnum {

    QUESTION(1),
    COMMENT(2),

    ;


    private Integer type;

    public static boolean exist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (type==commentTypeEnum.getType()){
                return true;
            }
        }
        return false;
    }


    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
