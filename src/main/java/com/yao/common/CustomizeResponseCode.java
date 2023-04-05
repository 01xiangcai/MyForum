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
    INCREASEVIEW_SUCCESS("增加阅读数成功"),

    ARTICLE_FOUND_SUCCESS("查找文章成功"),
    ARTICLE_NOT_FOUND("你要找的文章不存在"),
    ARTICLE_INSERT_SUCCESS("新增文章成功"),
    ARTICLE_INSERT_FAIL("新增文章失败"),
    ARTICLE_UPDATE_SUCCESS("修改文章成功"),
    ARTICLE_UPDATE_FAIL("修改文章失败"),
    ARTICLE_DELETE_SUCCES("删除文章成功"),
    ARTICLE_DELETE_FAIL("删除文章失败"),



    COMMENT_IS_NULL("评论人id或内容或类型为空"),
    COMMENT_INSERT_SUCCESS("评论成功"),
    COMMENT_INSERT_FAIL("评论失败"),
    COMMENT_FOUND_SUCCESS("查找评论成功"),
    COMMENT_FOUND_FAIL("查找评论失败"),
    COMMENT_DELETE_SUCCESS("删除评论成功"),
    COMMENT_DELETE_FAIL("删除评论失败"),
    COMMENT_TYPE_ERROE("评论类型错误"),

    USER_NOT_FOUND("该用户不存在"),
    USER_UPDATE_FAIL("修改用户失败"),
    USER_UPDATE_SUCCESS("修改用户成功"),

    OLD_PASSWORD_ERROR("旧密码输入错误"),

    NOTIFITION_ERROR("通知参数错误"),
    NOTIFITION_INSERT_SUCCESS("新增通知成功"),
    NOTIFITION_INSERT_FAIL("新增通知失败"),
    NOTIFITION_FOUND_SUCCESS("查询通知成功"),
    NOTIFITION_FOUND_FAIL("查询通知失败"),
    NOTIFITION_READORNOTGOUND("该消息已读或者不存在"),
    NOTIFITION_READ_SUCCESS("消息已读成功"),
    NOTIFITION_READ_FAIL("消息已读失败"),

    CAROUSEL_FOUND_SUCCESS("轮播图查找成功"),
    CAROUSEL_FOUND_FAIL("轮播图查找失败"),


    UPLOAD_SUCCESS("上传成功"),
    UPLOAD_FAIL("上传失败"),
    PARAMETER_ERROR("参数有误");



    ;



    private String message;

    public String getMessage() {
        return message;
    }

    CustomizeResponseCode(String message) {
        this.message = message;
    }
}
