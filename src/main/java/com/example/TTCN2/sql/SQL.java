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

    // login_check username user
    public static final String CHECK_LOGIN="select * from user where username = ?";
    // login_check username user
    public static final String CHECK_LOGIN_ADMIN="select * from admin where username = ?";
    // login_check username user
    public static final String CHECK_LOGIN_SHIPPER="select * from shipper where username = ?";

    // lay toan bo user
    public static final String ALL_USER="select * from user ";
    // lay toan bo shipper
    public static final String ALL_SHIPPER="select * from shipper ";
    // lay toan bo receiver theo ID cua User
    public static final String ALL_RECEIVER_BY_IDUSER="select * from receiver where id_user = ?";
    // lay receiver theo idRece
    public static final String RECEIVER_BY_IDRECE="select *from receiver where id=?";
    // lay user theo isCus
    public static final String USER_BY_IDUSER="select * from user where id=?";
    // dem so luong cartItem
    public static final String CART_ITEM_COUNT="select count(id) countCartItem from cart_item where id_cart=?";
    // lay trees theo id category
    public static final String ALL_TREES_TO_ID_CATEGORY="select *from trees t where t.id_category = ? and t.is_delete=0";
    // dem  so luong san  pham o category
    public static final String COUNT_TREES_TO_ID_CATEGORY="select count(id) countCategory from trees t where t.id_category = ? and t.is_delete=0";
    // lay cartItem theo idCartItem
    public static final String GET_CART_ITEM_BY_ID="select * from cart_item where id=?";
    // lay transport theo idTrans
    public static final String GET_TRANSPORT_BY_IDTRANS="select * from transport_method where id=?";
    // lay payment theo idPay
    public static final String GET_PAYMENT_BY_IDPAY="select * from payment_method where id=?";
    // order theo idCus and status
    public static final String ORDER_BY_IDCUS_AND_STATUS="select * from `order` where id_user=? and status=?";
    // order theo idCus and status
    public static final String ORDER_BY_STATUS="select * from `order` where status=?";
    // orderDetail by idOrder
    public static final String ORDERDETAIL_BY_IDORDER="select *from order_detail where id_order=?";
    // get order by idOrder
    public static final String ORDER_BY_IDORDER="select * from `order` where id=?";
    // information by detail admin
    public static final String DETAIL_ADMIN_TO_IDADMIN="select * from detail_admin where id_admin = ?";
    // count tree
    public static final String COUNT_TREES="select count(id) countTree from trees";
    // count cate
    public static final String COUNT_CATEGORY="select count(id) countCategory from category";
    // count order
    public static final String COUNT_ORDER="select count(id) countOrder from `order`";
    // count order
    public static final String COUNT_ORDER_BY_STATUS="select count(id) countOrder from `order` where status = ?";
    // count shipper
    public static final String COUNT_SHIPPER="select count(id) countShipper from shipper";
    // count user
    public static final String COUNT_USER="select count(id) countUser from user";
    // limit desc order by receiver_date
    public static final String LIMIT_ORDER_BY_RECEIVER_DATE="select  * from `order`\n" +
            "where status= 4\n" +
            "order by `order`.receiver_date desc\n" +
            "limit 5";
    // get all order join receiver ()
    public static final String GET_ALL_ORDER_BY_STATUS_JOIN_RECEIVER= "select o.id id,o.total_money totalMoney,o.total_quantity totalQuantity,o.id_receiver idRece\n" +
            "     ,r.name nameRece,r.address addressRece,o.create_date createDate,o.status status\n" +
            "from `order` o\n" +
            "    join trees.receiver r on r.id = o.id_receiver\n" +
            "where o.status = ? and o.id_shipper = ?";
    // get shipper_order by idOrder
    public static final String GET_SHIPPER_ORDER_BY_IDORDER="select * from shipper_order where id_order = ?";
    // get all shipper_order by id_shipper
    public static final String GET_ALL_SHIPPER_ORDER_BY_IDSHIPPER="select * from shipper_order where id_shipper = ?";
    // get Shipper by idShipper
    public static final String GET_SHIPPER_BY_IDSHIPPER="select * from shipper where id=?";
    // dateDiff orderStatus4
    public static final String DATEDIFF_ORDER_STATTUS_4="select datediff(receiver_date,create_date) dateOrder from `order` where id=?";

    // show chat_box
    public static final String SHOW_CHAT_BOX_BY_ID_CHAT_BOX="select * from chat_box_detail where id_chat_box =?\n" +
            "order by date";
}
