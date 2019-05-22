package info.biyesheji.sheji.mapper;

import info.biyesheji.sheji.entity.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Options;
/**
 * OrderDao.java
 * @version 1.0.0
 */
public interface OrderMapper {

    String fields = " t.id id, t.req_ip reqIp, t.mac_ip macIp, t.remark remark, t.alipay_account alipayAccount, t.qq_account qqAccount, t.weixin_account weixinAccount, t.create_time createTime ";

    /**
     * 根据ID查询 Order 实体
     */
    @Select(" select " + fields + " from t_order t where t.id = #{id}")
    Order getOrderByPrimaryId(@Param(value = "id") Integer id);

    /**
     * 根据ID修改 Order 实体
     */
    @Update("<script> update t_order" +
        "<set>" +
            "<if test='reqIp !=null '>req_ip = #{reqIp},</if><if test='macIp !=null '>mac_ip = #{macIp},</if><if test='remark !=null '>remark = #{remark},</if><if test='alipayAccount !=null '>alipay_account = #{alipayAccount},</if><if test='qqAccount !=null '>qq_account = #{qqAccount},</if><if test='weixinAccount !=null '>weixin_account = #{weixinAccount},</if><if test='createTime !=null '>create_time = #{createTime}</if>" +
        "</set>" +
        "where id = #{id} </script>")
    Integer updateOrderByPrimaryId(Order Order);

    /**
     * 根据ID删除 Order 实体
     */
    @Delete("delete from t_order where id = #{id}")
    Integer delOrderByPrimaryId(@Param(value = "id") Integer id);

    /**
     * 保存 Order 实体
     */
    @Insert("<script> insert into t_order " +
        "<trim prefix=' ( ' suffix=' ) ' suffixOverrides=' , '>" +
            "<if test='id !=null '>id,</if><if test='reqIp !=null '>req_ip,</if><if test='macIp !=null '>mac_ip,</if><if test='remark !=null '>remark,</if><if test='alipayAccount !=null '>alipay_account,</if><if test='qqAccount !=null '>qq_account,</if><if test='weixinAccount !=null '>weixin_account,</if><if test='createTime !=null '>create_time</if>" +
        "</trim>" +
        "<trim prefix=' values( ' suffix=' ) ' suffixOverrides=','><if test='id !=null '>#{id},</if><if test='reqIp !=null '>#{reqIp},</if><if test='macIp !=null '>#{macIp},</if><if test='remark !=null '>#{remark},</if><if test='alipayAccount !=null '>#{alipayAccount},</if><if test='qqAccount !=null '>#{qqAccount},</if><if test='weixinAccount !=null '>#{weixinAccount},</if><if test='createTime !=null '>#{createTime}</if>"+
        "</trim></script>")
    @Options(useGeneratedKeys=true,keyProperty = "id")
    Integer addOrder(Order Order);

}