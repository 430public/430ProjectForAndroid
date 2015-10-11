package com.projectforandroid.data;

import java.util.List;

/**
 * Created by 大灯泡 on 2015/10/11.
 * 热点新闻索引
 *
 * 服务级错误码参照(error_code)：
 *
 * 错误码	说明
 * 207701	 热点新闻不存在
 * 系统级错误码参照(error_code)：
 *
 * 错误码	说明
 * 0	 成功
 * 10001	 错误的请求KEY
 * 10002	 该KEY无请求权限
 * 10003	 KEY过期
 * 10004	 错误的SDK KEY
 * 10005	 应用未审核超时，请提交认证
 * 10007	 未知的请求源，（服务器没有获取到IP地址）
 * 10008	 被禁止的IP
 * 10009	 被禁止的KEY
 * 10011	 当前IP请求超过限制
 * 10012	 当前Key请求超过限制
 * 10013	 测试KEY超过请求限制
 * 10020	 接口维护
 * 10021	 接口停用
 * 10022	 appKey按需剩余请求次数为零
 * 10023	 请求IP无效
 * 10024	 网络错误
 * 10025	 没有查询到结果
 * 10026	 当前请求频率过高超过权限限制
 */
public class HotNewsIndexBean {
    private int status;
    private String reason;
    private List<IndexBean> indexList;

    public static class IndexBean {
        public int id;
        public String indexKey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<IndexBean> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<IndexBean> indexList) {
        this.indexList = indexList;
    }
}
