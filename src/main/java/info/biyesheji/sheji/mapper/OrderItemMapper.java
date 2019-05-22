package info.biyesheji.sheji.mapper;

import info.biyesheji.sheji.entity.OrderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Options;
/**
 * OrderItemDao.java
 * @version 1.0.0
 */
public interface OrderItemMapper {

    String fields = " toi.id id, toi.product_id productId, toi.product_type productType, toi.pirce pirce, toi.final_pirce finalPirce, toi.create_time createTime ";

    /**
     * 根据ID查询 OrderItem 实体
     */
    @Select(" select " + fields + " from t_order_item toi where toi.id = #{id}")
    OrderItem getOrderItemByPrimaryId(@Param(value = "id") Integer id);

    /**
     * 根据ID修改 OrderItem 实体
     */
    @Update("<script> update t_order_item" +
        "<set>" +
            "<if test='productId !=null '>product_id = #{productId},</if><if test='productType !=null '>product_type = #{productType},</if><if test='pirce !=null '>pirce = #{pirce},</if><if test='finalPirce !=null '>final_pirce = #{finalPirce},</if><if test='createTime !=null '>create_time = #{createTime}</if>" +
        "</set>" +
        "where id = #{id} </script>")
    Integer updateOrderItemByPrimaryId(OrderItem OrderItem);

    /**
     * 根据ID删除 OrderItem 实体
     */
    @Delete("delete from t_order_item where id = #{id}")
    Integer delOrderItemByPrimaryId(@Param(value = "id") Integer id);

    /**
     * 保存 OrderItem 实体
     */
    @Insert("<script> insert into t_order_item " +
        "<trim prefix=' ( ' suffix=' ) ' suffixOverrides=' , '>" +
            "<if test='id !=null '>id,</if><if test='productId !=null '>product_id,</if><if test='productType !=null '>product_type,</if><if test='pirce !=null '>pirce,</if><if test='finalPirce !=null '>final_pirce,</if><if test='createTime !=null '>create_time</if>" +
        "</trim>" +
        "<trim prefix=' values( ' suffix=' ) ' suffixOverrides=','><if test='id !=null '>#{id},</if><if test='productId !=null '>#{productId},</if><if test='productType !=null '>#{productType},</if><if test='pirce !=null '>#{pirce},</if><if test='finalPirce !=null '>#{finalPirce},</if><if test='createTime !=null '>#{createTime}</if>"+
        "</trim></script>")
    @Options(useGeneratedKeys=true,keyProperty = "id")
    Integer addOrderItem(OrderItem OrderItem);

}