package com.yao.common.myEnum;

/**
 * @className: uploadImageType
 * @Description: 图片上传的类型，方便按不同文件建立不同的文件夹保存
 * @author: long
 * @date: 2023/4/4 16:28
 */
public enum UploadImageType {

    CAROUSEL(0 ,"轮播图"),
    ARTICLE_TITLE_IMAGE(1,"文章标题图片"),
    ARTICLE_CONTENT_PICTURE(11,"文章内容图片"),
    QUESTION_TITLE_IMAGE(2,"问题标题图片"),
    QUESTION_CONTENT_PICTURE(22,"问题内容图片"),
    ORTHER(200,"其它")
    ;


    private Integer imageType;
    private String imageTypeName;





    UploadImageType(Integer imageType, String imageTypeName) {
        this.imageType = imageType;
        this.imageTypeName = imageTypeName;
    }

    public Integer getImageType() {
        return imageType;
    }
    public String getImageTypeName() {
        return imageTypeName;
    }
}
