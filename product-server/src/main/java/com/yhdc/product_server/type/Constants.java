package com.yhdc.product_server.type;

import org.springframework.util.MimeType;

public class Constants {

    // PAGE
    public static final int DEFAULT_PAGE_NO = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_SORT_ORDER = "DESC";
    public static final String DEFAULT_SORT_BY = "createdAt";

    // STORE
    public static final String STORE_ACTIVE = "ACTIVE";
    public static final String STORE_INACTIVE = "INACTIVE";
    public static final String STORE_SUSPENDED = "SUSPENDED";

    // PRODUCT
    public static final String PRODUCT_ACTIVE = "FOR_SALE";
    public static final String PRODUCT_INACTIVE = "SOLD_OUT";
    public static final String PRODUCT_SUSPENDED = "SUSPENDED";

    // FILE
    public static final String IMAGE_BASE_DIR = "/stackcore/etc/file/images/";

    // MIME
    public static final MimeType DOC_PDF = MimeType.valueOf("application/pdf");
    public static final MimeType DOC_CSV = MimeType.valueOf("text/csv");
    public static final MimeType DOC_DOC = MimeType.valueOf("application/msword");
    public static final MimeType DOC_DOCX = MimeType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    public static final MimeType DOC_XLS = MimeType.valueOf("application/vnd.ms-excel");
    public static final MimeType DOC_XLSX = MimeType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final MimeType DOC_HTML = MimeType.valueOf("text/html");
    public static final MimeType DOC_TXT = MimeType.valueOf("text/plain");
    public static final MimeType DOC_MD = MimeType.valueOf("text/markdown");
    public static final MimeType VIDEO_MKV = MimeType.valueOf("video/x-matros");
    public static final MimeType VIDEO_MOV = MimeType.valueOf("video/quicktime");
    public static final MimeType VIDEO_MP4 = MimeType.valueOf("video/mp4");
    public static final MimeType VIDEO_WEBM = MimeType.valueOf("video/webm");
    public static final MimeType VIDEO_FLV = MimeType.valueOf("video/x-flv");
    public static final MimeType VIDEO_MPEG = MimeType.valueOf("video/mpeg");
    public static final MimeType VIDEO_MPG = MimeType.valueOf("video/mpeg");
    public static final MimeType VIDEO_WMV = MimeType.valueOf("video/x-ms-wmv");
    public static final MimeType VIDEO_THREE_GP = MimeType.valueOf("video/3gpp");
    public static final MimeType IMAGE_PNG = MimeType.valueOf("image/png");
    public static final MimeType IMAGE_JPEG = MimeType.valueOf("image/jpeg");
    public static final MimeType IMAGE_GIF = MimeType.valueOf("image/gif");
    public static final MimeType IMAGE_WEBP = MimeType.valueOf("image/webp");

}
