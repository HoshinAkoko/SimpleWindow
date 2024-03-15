//package moe.moti.simplewindow.util;
//
//import nuonuo.open.sdk.NNOpenSDK;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//public class InvoiceUtil {
//    private static final Logger log = LogManager.getLogger();
//
//    /** 参数配置
//     * @param taxnum "23***789" 授权企业税号, 商户填""
//     * @param appKey "填写应用的appKey"
//     * @param appSecret "填写应用的appSecret"
//     * @param method "填写API方法名"
//     *
//     * @param token "填写访问令牌,可通过OpenToken.java获取"
//     * @param content "填写API私有请求参数, 标准JSON格式"
//     * @param url 开票地址https://sdk.nuonuo.com/open/v1/services 票据识别请使用https://sdk.nuonuo.com/open/v2/ocr
//     * @param senid UUID.randomUUID().toString().replace("-", "") 唯一标识，由企业自己生成32位随机码
//     */
//    public static String requestApi(String taxnum, String appKey, String appSecret, String method, String token, String content, String url, String senid) {
//
//        // 接口调用
//        NNOpenSDK sdk = NNOpenSDK.getIntance();
//        String json = sdk.sendPostSyncRequest(url, senid, appKey, appSecret, token, taxnum, method, content);
//
//        // 响应报文解析
//        log.info("-----------> nuonuo invoiceRequestService requestApi:{}", json);
//        return json;
//    }
//
//    /**
//     * 参数配置
//     * @param appKey 填写应用的appKey
//     * @param appSecret 填写应用的appSecret
//     */
//    public static String getMerchantToken(String appKey, String appSecret) {
//        // 接口调用
//        NNOpenSDK sdk = NNOpenSDK.getIntance();
//        String json = sdk.getMerchantToken(appKey, appSecret);
//
//        // 响应报文解析
//        log.info("-----------> nuonuo invoiceRequestService getMerchantToken:{}", json);
//        return json;
//    }
//}
