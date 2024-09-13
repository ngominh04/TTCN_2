package com.example.TTCN2.sql;

public class SQL {
    // lấy toàn bộ cây
    public final static String ALL_TREES="select * from trees t\n" +
            "    where t.is_active = 0 and t.is_delete = 0";

    // lấy ảnh chính của cây theo idtree
    public final static String MAIN_IMAGE_OF_TREE="select * from trees_image\n" +
            "    where main_image = 1 and id_tree =?";
}
