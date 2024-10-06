package com.example.TTCN2.sql;

public class SQL {
    // lấy toàn bộ cây
    public final static String ALL_TREES="select * from trees t\n" +
            "    where  t.is_delete = 0";

    // lấy ảnh chính của cây theo idtree
    public final static String MAIN_IMAGE_OF_TREE="select * from trees_image\n" +
            "    where main_image = 1 and id_tree =?";

    // xem chi tiet cay
    public static final String DETAIL_TREE="select * from trees t where t.id = ?";

    //ảnh cua cay theo idTree
    public static final String IDTREE_IMAGE="select * from trees_image t where t.id_tree = ?";

    // lay san pham cung category (loai tru san pham dang xem chi tiet)
    public static final String TREES_CATEGORY_EXCLUDED_DETAIL_TREE="select * from trees t where t.id_category=? and t.id!=?\n" +
            "    limit 4";

    // login_check username
    public static final String CHECK_LOGIN="select * from user where user.username = ?";

    // lay toan bo user
    public static final String ALL_USER="select * from user ";
    // lay toan bo receiver theo ID cua User
    public static final String ALL_RECEIVER_BY_IDUSER="select * from receiver where id_user = ?";
    // lay receiver theo idRece
    public static final String RECEIVER_BY_IDRECE="select *from receiver where id=?";
    // lay user theo isCus
    public static final String USER_BY_IDUSER="select * from user where id=?";
}
