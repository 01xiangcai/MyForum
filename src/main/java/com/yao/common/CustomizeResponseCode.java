package com.yao.common;

/**
 * @className: CustomizeResponseCode
 * @Description: TODO
 * @author: long
 * @date: 2023/3/15 13:41
 */
public enum  CustomizeResponseCode {

    QUESTION_FOUND_SUCCESS("查找问题成功"),
    QUESTION_NOT_FOUND("你要找的问题不存在"),
    QUESTION_INSERT_SUCCESS("新增问题成功"),
    QUESTION_INSERT_FAIL("新增问题失败"),
    QUESTION_UPDATE_SUCCESS("修改问题成功"),
    QUESTION_UPDATE_FAIL("修改问题失败"),
    QUESTION_DELETE_SUCCES("删除问题成功"),
    QUESTION_DELETE_FAIL("删除问题失败"),
    QUESTION_NOT_NULL("标题或内容或标签不能为空"),

    COMMENT_IS_NULL("评论人id或内容或类型为空"),
    COMMENT_INSERT_SUCCESS("评论成功"),
    COMMENT_INSERT_FAIL("评论失败"),
    COMMENT_FOUND_SUCCESS("查找评论成功"),
    COMMENT_FOUND_FAIL("查找评论失败"),
    COMMENT_DELETE_SUCCESS("删除评论成功"),
    COMMENT_DELETE_FAIL("删除评论失败"),

    USER_NOT_FOUND("该用户不存在"),



    ;



    private String message;

    public String getMessage() {
        return message;
    }

    CustomizeResponseCode(String message) {
        this.message = message;
    }
}
