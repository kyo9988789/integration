package com.qunjie.crm.utils;

import com.google.gson.Gson;
import com.qunjie.crm.attendance.args.AttendanceQueryArg;
import com.qunjie.crm.attendance.result.AttendanceQueryResult;
import com.qunjie.crm.beans.AppReqParmVO;
import com.qunjie.crm.beans.HttpResponseMessageVO;
import com.qunjie.crm.beans.MsgReceiveParamVO;
import com.qunjie.crm.beans.args.*;
import com.qunjie.crm.beans.results.*;
import com.qunjie.crm.exception.BaseException;
import com.qunjie.crm.manager.args.ModifyCustomArg;
import com.qunjie.crm.query.args.QueryArg;
import com.qunjie.crm.query.results.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 开放平台Api调用的工具类
 * 
 * @author whs
 * @date 2021/1/18  16:04
 */
public class OpenAPIUtils {

    private static final Logger LOG = LoggerFactory.getLogger(OpenAPIUtils.class);

    /**
     * 环境为：https://open.fxiaoke.com
     */
    private static final String prefix = "https://open.fxiaoke.com";

    /**
     * 获取AppToken 实现
     * 
     * @param appTokenArg @see AppTokenArg
     * @return
     * @throws Exception
     */
    public static AppTokenResult getAppToken(AppTokenArg appTokenArg) {
        String url = prefix + "/cgi/appAccessToken/get";
        return doPost(url, appTokenArg, AppTokenResult.class);
    }

    /**
     * 获取corpAccessToken 实现
     * 
     * @param corpAccessTokenArg @see CorpAccessTokenArg
     * @return
     * @throws Exception
     */
    public static CorpAccessTokenResult getCorpToken(CorpAccessTokenArg corpAccessTokenArg) {
        String url = prefix + "/cgi/corpAccessToken/get/V2";
        return doPost(url, corpAccessTokenArg, CorpAccessTokenResult.class);
    }

    /**
     * 获取OpenUserId 实现
     * 
     * @param openUserIdArg @see OpenUserIdArg
     * @return
     * @throws Exception
     */
    public static OpenUserIdResult getOpenUserId(OpenUserIdArg openUserIdArg) {
        String url = prefix + "/oauth2/openUserId/get";
        return doPost(url, openUserIdArg, OpenUserIdResult.class);
    }

    /**
     * accountBind 实现
     * 
     * @param bindAccountArg @see BindAccountArg
     * @return
     * @throws Exception
     */
    public static BindAccountResult bindAccount(BindAccountArg bindAccountArg) {
        String url = prefix + "/oauth2/accountBind";
        return doPost(url, bindAccountArg, BindAccountResult.class);
    }

    /**
     * 获取用户详细信息 实现
     * 
     * @see UserInfoArg @see UserInfoArg
     * @return
     * @throws Exception
     */
    public static UserResult getUserInfo(UserInfoArg userInfoArg) {
        String url = prefix + "/cgi/user/get";
        return doPost(url, userInfoArg, UserResult.class);
    }

    /**
     * 获取部门列表 实现
     * 
     * @param deptListArg @see DeptListArg
     * @return
     * @throws Exception
     */
    public static DeptListResult getDeptList(DeptListArg deptListArg) {
        String url = prefix + "/cgi/department/list";
        return doPost(url, deptListArg, DeptListResult.class);
    }

    /**
     * 获取部门人员列表 实现
     * 
     * @param deptUserListArg @see DeptUserListArg
     * @return
     * @throws Exception
     */
    public static DeptUserListResult getDeptUserList(DeptUserListArg deptUserListArg) {
        String url = prefix + "/cgi/user/list";
        return doPost(url, deptUserListArg, DeptUserListResult.class);
    }

    /**
     * 发送文本消息 实现
     * 
     * @param textMsgArg @see TextMsgArg
     * @return
     * @throws Exception
     */
    public static TextMsgResult sendTextMsg(TextMsgArg textMsgArg) {
        String url = prefix + "/cgi/message/send";
        return doPost(url, textMsgArg, TextMsgResult.class);
    }
    
    /**
     * CRM 字段描述  元数据接口
     */
    public static CrmDescResult getCrmDesc(CrmDescArg arg) {
        String url = prefix + "/cgi/crm/object/describe";
        return doPost(url, arg, CrmDescResult.class);
    }
    
    /**
     * CRM 字段描述  修改负责人
     */
    public static BaseResult changeCrmOwner(CrmChangeOwnerArg arg) {
        String url = prefix + "/cgi/crm/data/changeOwner";
        return doPost(url, arg, BaseResult.class);
    }
    
    /**
     * CRM添加 元数据接口
     */
    public static CrmAddResult createCrmData(CrmAddArg arg) {
        String url = prefix + "/cgi/crm/data/create";
        return doPost(url, arg, CrmAddResult.class);
    }

    /**
     * CRM修改 元数据接口
     */
    public static BaseResult updateCrmData(CrmUpdateArg arg) {
        String url = prefix + "/cgi/crm/data/update";
        return doPost(url, arg, BaseResult.class);
    }

    /**
     * CRM作废 元数据接口
     */
    public static BaseResult dropCrmData(CrmDeleteArg arg) {
        String url = prefix + "/cgi/crm/data/drop";
        return doPost(url, arg, BaseResult.class);
    }

    /**
     * CRM查询列表 元数据接口
     */
    public static CrmQueryResult queryCrmData(CrmQueryArg arg) {
        String url = prefix + "/cgi/crm/data/query";
        return doPost(url, arg, CrmQueryResult.class);
    }

    /**
     * CRM获取详情 元数据接口
     */
    public static CrmGetResult getData(CrmGetArg arg) {
        String url = prefix + "/cgi/crm/data/get";
        return doPost(url, arg, CrmGetResult.class);
    }

    /**
     * 验证消息推送请求合法性
     * 
     * @param msgReceiveParamVO @see MsgReceiveParamVO
     * @param token
     * @return true 表示验证成功,其它表示失败
     */
    public static boolean verifyMsgReq(MsgReceiveParamVO msgReceiveParamVO, String token) {
        boolean verifyResult = false;

        try {
            String sha1Str = SigUtils.getSHA1(token, msgReceiveParamVO.getTimeStamp(), msgReceiveParamVO.getNonce(),
                            msgReceiveParamVO.getContent());
            verifyResult = sha1Str.equals(msgReceiveParamVO.getSig()) ? true : false;
        } catch (Exception e) {
            verifyResult = false;
            LOG.error(" verify signature error, details:", e);
        }

        return verifyResult;
    }

    /**
     * 验证应用跳转来源合法性
     * 
     * @param appReqParmVO @see AppReqParmVO
     * @param token
     * @return true 表示验证成功,其它表示失败
     */
    public static boolean verifyAppReq(AppReqParmVO appReqParmVO, String token) {
        boolean verifyResult = false;

        try {
            String signResult = SigUtils.getSHA1(token, appReqParmVO.getCode(), appReqParmVO.getTimestamp(),
                            appReqParmVO.getNonce());
            verifyResult = appReqParmVO.getCodeSig().equals(signResult) ? true : false;
        } catch (Exception e) {
            verifyResult = false;
            LOG.error(" verify signature error, details:", e);
        }

        return verifyResult;
    }

    /**
     * 增加部门实现 返回部门的ID和ORDER
     * 
     * @param deptAddModifyArg @see DeptAddModifyArg
     * @return
     * @throws Exception
     */
    public static DeptAddResult addDept(DeptAddModifyArg deptAddModifyArg) {
        String url = prefix + "/cgi/department/add";
        return doPost(url, deptAddModifyArg, DeptAddResult.class);
    }

    /**
     * 修改部门实现
     * 
     * @param deptAddModifyArg 修改部门信息的参数 @see DeptAddModifyArg
     * @return 错误码和错误信息
     * @throws Exception
     */
    public static DeptUpdateResult modifyDept(DeptAddModifyArg deptAddModifyArg) {
        String url = prefix + "/cgi/department/update";
        return doPost(url, deptAddModifyArg, DeptUpdateResult.class);
    }

    public static DeptUpdateResult CanceledDept(DeptStatusArg arg){
        String url = prefix + "/cgi/department/setStatus";
        return doPost(url, arg, DeptUpdateResult.class);
    }

    /**
     * 查询部门详情
     * @param deptArg
     * @return
     */
    public static DeptDetailResult deptDetail(DeptArg deptArg){
        String url = prefix + "/cgi/department/detail";
        return doPost(url,deptArg,DeptDetailResult.class);
    }


    /**
     * 添加用户
     * @param arg
     * @return
     */
    public static UserAddResult addUser(UserAddModifyArg arg){
        String url = prefix + "/cgi/user/add";
        return doPost(url,arg,UserAddResult.class);
    }

    public static UserUpdateResult canceledUser(UserStatusArg arg){
        String url = prefix + "/cgi/user/setStatus";
        return doPost(url,arg,UserUpdateResult.class);
    }
    /**
     * 修改用户
     * @param arg
     * @return
     */
    public static UserUpdateResult modifyUser(UserAddModifyArg arg){
        String url = prefix + "/cgi/user/update";
        return doPost(url,arg,UserUpdateResult.class);
    }

    private static <T extends BaseResult> T doPost(String url, Arg arg, Class<T> clazz) {
        T t = null;
        Result<String> result = doPost(url, arg);
        if (result.getCode() == 0) {
            t = new Gson().fromJson(result.getData(), clazz);
        }

        if (t != null) {
            return t;
        }

        try {
            t = clazz.newInstance();
            t.setErrorCode(result.getCode());
            t.setErrorMessage(result.getMsg());
        } catch (Exception e) {
            LOG.error("doPost error, details:", e);
        }
        return t;
    }

    private static Result<String> doPost(String url, Arg arg) {
        Result<String> result = new Result<String>();

        try {
            HttpResponseMessageVO resp = HttpTookit.sendPostByJson(url, new Gson().toJson(arg));

            if ("200".equals(resp.getHttpCode())) {
                result.setData(resp.getContent());
            } else {
                result.setCode(Constants.interfaceException.INTERFACE_EXCEPTION.code);
                result.setMsg(Constants.interfaceException.INTERFACE_EXCEPTION.msg.concat(",HTTP Status Code:").concat(
                        resp.getHttpCode()));
            }
        } catch (BaseException e) {
            LOG.error("doPost error, details:", e);
            result.setMsg(e.getMessage());
            result.setCode(e.getCode());
        }

        return result;
    }


    public static <T extends BaseResult> T saveData(BaseArg baseArg ,Class<T> clazz){
        String url = prefix + "/cgi/crm/v2/data/create";
        return  doPost(url, baseArg, clazz);
    }

    public static <T extends BaseResult> T saveCustomData(BaseArg baseArg ,Class<T> clazz){
        String url = prefix + "/cgi/crm/custom/data/create";
        return  doPost(url, baseArg, clazz);
    }

    public static QueryResult queryData(QueryArg queryArg){
        String url = prefix + "/cgi/crm/v2/data/query";
        return doPost(url,queryArg,QueryResult.class);
    }

    public static QueryResult queryCustomData(QueryArg queryArg){
        String url = prefix + "/cgi/crm/custom/data/query";
        return doPost(url,queryArg,QueryResult.class);
    }

    public static BaseResult modifyCustomData(ModifyCustomArg arg){
        String url = prefix + "/cgi/crm/custom/data/update";
        return doPost(url,arg,BaseResult.class);
    }

    public static <T extends BaseResult> T deleteData(BaseArg arg,Class<T> clazz){
        String url = prefix + "/cgi/crm/v2/data/delete";
        return  doPost(url, arg, clazz);
    }

    public static <T extends BaseResult> T deleteCustomData(BaseArg arg,Class<T> clazz){
        String url = prefix + "/cgi/crm/custom/data/delete";
        return  doPost(url, arg, clazz);
    }

    public static <T extends BaseResult> T invalidData(BaseArg arg,Class<T> clazz){
        String url = prefix + "/cgi/crm/v2/data/invalid";
        return  doPost(url, arg, clazz);
    }
    public static <T extends BaseResult> T invalidCustomData(BaseArg arg,Class<T> clazz){
        String url = prefix + "/cgi/crm/custom/data/invalid";
        return  doPost(url, arg, clazz);
    }

    /**
     * 考勤信息查询
     * @param arg
     * @return
     */
    public static AttendanceQueryResult findAttendance(AttendanceQueryArg arg){
        String url = prefix + "/cgi/attendance/find";
        return doPost(url, arg,AttendanceQueryResult.class);
    }

    public static AttendanceQueryResult findAttendance(AttendanceQueryArg arg,String uri){
        String url = prefix + uri;
        return doPost(url, arg,AttendanceQueryResult.class);
    }
}
