package info.biyesheji.sheji.mapper;

import info.biyesheji.sheji.entity.Product;
import info.biyesheji.sheji.entity.ProductParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * ProductDao.java
 *
 * @version 1.0.0
 */
public interface ProductMapper {

    String fields = " tp.id id, tp.name name, tp.remark remark, tp.download_num downloadNum, tp.start_num startNum, tp.create_time createTime, tp.url url, tp.source_url sourceUrl, tp.price price, tp.size size, tp.type type, tp.status status ";
    String publicFields = " tp.id id, tp.name name, tp.remark remark, tp.download_num downloadNum, tp.start_num startNum, tp.create_time createTime, tp.price price, tp.size size, tp.status status ";
    String pageQurey = "<where>" +
            "<if test='param.startPrice != null'>and tp.price &gt;= #{param.startPrice} </if>" +
            "<if test='param.endPrice != null'>and tp.price &lt;= #{param.endPrice} </if>" +
            "<if test='param.name != null and param.name !=\"\" '>and tp.name like '%${param.name}%' </if>" +
            "<if test='param.type != null and param.type != 0'>and tp.type = #{param.type} </if>" +
            "</where>" +
            " order by " +
            "  <choose>" +
            "  <when test=\"param.sortType = 2\">" + " tp.price  </when>" +
            "  <when test=\"param.sortType = 3\">" + " tp.start_num  </when>" +
            "  <otherwise>" + " tp.download_num  </otherwise>" +
            "  </choose> " +
            " desc limit #{limitStart},#{param.pageSize}";

    /**
     * 根据ID查询 Product 实体
     */
    @Select(" select " + fields + " from t_product tp where tp.id = #{id}")
    Product getProductByPrimaryId(@Param(value = "id") Integer id);

    /**
     * 根据ID修改 Product 实体
     */
    @Update("<script> update t_product" +
            "<set>" +
            "<if test='name !=null '>name = #{name},</if><if test='remark !=null '>remark = #{remark},</if><if test='downloadNum !=null '>download_num = #{downloadNum},</if><if test='startNum !=null '>start_num = #{startNum},</if><if test='createTime !=null '>create_time = #{createTime},</if><if test='url !=null '>url = #{url},</if><if test='sourceUrl !=null '>source_url = #{sourceUrl},</if><if test='price !=null '>price = #{price},</if><if test='size !=null '>size = #{size},</if><if test='type !=null '>type = #{type},</if><if test='status !=null '>status = #{status}</if>" +
            "</set>" +
            "where id = #{id} </script>")
    Integer updateProductByPrimaryId(Product Product);

    /**
     * 根据ID删除 Product 实体
     */
    @Delete("delete from t_product where id = #{id}")
    Integer delProductByPrimaryId(@Param(value = "id") Integer id);

    /**
     * 保存 Product 实体
     */
    @Insert("<script> insert into t_product " +
            "<trim prefix=' ( ' suffix=' ) ' suffixOverrides=' , '>" +
            "<if test='id !=null '>id,</if><if test='name !=null '>name,</if><if test='remark !=null '>remark,</if><if test='downloadNum !=null '>download_num,</if><if test='startNum !=null '>start_num,</if><if test='createTime !=null '>create_time,</if><if test='url !=null '>url,</if><if test='sourceUrl !=null '>source_url,</if><if test='price !=null '>price,</if><if test='size !=null '>size,</if><if test='type !=null '>type,</if><if test='status !=null '>status</if>" +
            "</trim>" +
            "<trim prefix=' values( ' suffix=' ) ' suffixOverrides=','><if test='id !=null '>#{id},</if><if test='name !=null '>#{name},</if><if test='remark !=null '>#{remark},</if><if test='downloadNum !=null '>#{downloadNum},</if><if test='startNum !=null '>#{startNum},</if><if test='createTime !=null '>#{createTime},</if><if test='url !=null '>#{url},</if><if test='sourceUrl !=null '>#{sourceUrl},</if><if test='price !=null '>#{price},</if><if test='size !=null '>#{size},</if><if test='type !=null '>#{type},</if><if test='status !=null '>#{status}</if>" +
            "</trim></script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addProduct(Product Product);


    @Select("<script>" +
            "select count(1) from t_product tp " + pageQurey +
            "</script>")
    Integer countListProduct(@Param("param") ProductParam param, @Param("limitStart") Integer limitStart);

    @Select("<script>" +
            "select " + fields +
            " from t_product tp " + pageQurey +
            "</script>")
    List<Product> listProduct(@Param("param") ProductParam param, @Param("limitStart") Integer limitStart);
}