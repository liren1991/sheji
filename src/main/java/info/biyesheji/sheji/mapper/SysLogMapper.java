package info.biyesheji.sheji.mapper;

import info.biyesheji.sheji.entity.SysLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Options;
/**
 * SysLogDao.java
 * @version 1.0.0
 */
public interface SysLogMapper {

    String fields = " tsl.id id, tsl.action action, tsl.parameters parameters, tsl.res res, tsl.account account, tsl.ip ip, tsl.create_time createTime ";

    /**
     * 根据ID查询 SysLog 实体
     */
    @Select(" select " + fields + " from t_sys_log tsl where tsl.id = #{id}")
    SysLog getSysLogByPrimaryId(@Param(value = "id") Integer id);

    /**
     * 根据ID修改 SysLog 实体
     */
    @Update("<script> update t_sys_log" +
        "<set>" +
            "<if test='action !=null '>action = #{action},</if><if test='parameters !=null '>parameters = #{parameters},</if><if test='res !=null '>res = #{res},</if><if test='account !=null '>account = #{account},</if><if test='ip !=null '>ip = #{ip},</if><if test='createTime !=null '>create_time = #{createTime}</if>" +
        "</set>" +
        "where id = #{id} </script>")
    Integer updateSysLogByPrimaryId(SysLog SysLog);

    /**
     * 根据ID删除 SysLog 实体
     */
    @Delete("delete from t_sys_log where id = #{id}")
    Integer delSysLogByPrimaryId(@Param(value = "id") Integer id);

    /**
     * 保存 SysLog 实体
     */
    @Insert("<script> insert into t_sys_log " +
        "<trim prefix=' ( ' suffix=' ) ' suffixOverrides=' , '>" +
            "<if test='id !=null '>id,</if><if test='action !=null '>action,</if><if test='parameters !=null '>parameters,</if><if test='res !=null '>res,</if><if test='account !=null '>account,</if><if test='ip !=null '>ip,</if><if test='createTime !=null '>create_time</if>" +
        "</trim>" +
        "<trim prefix=' values( ' suffix=' ) ' suffixOverrides=','><if test='id !=null '>#{id},</if><if test='action !=null '>#{action},</if><if test='parameters !=null '>#{parameters},</if><if test='res !=null '>#{res},</if><if test='account !=null '>#{account},</if><if test='ip !=null '>#{ip},</if><if test='createTime !=null '>#{createTime}</if>"+
        "</trim></script>")
    @Options(useGeneratedKeys=true,keyProperty = "id")
    Integer addSysLog(SysLog SysLog);

}