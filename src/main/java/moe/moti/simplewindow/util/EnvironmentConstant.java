package moe.moti.simplewindow.util;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class EnvironmentConstant {
    @Value("${environment.database.url}")
    public String SQL_DATA_URL_ENVIRONMENT;
    public static String SQL_DATA_URL;

    @Value("${environment.database.username}")
    public String SQL_DATA_USERNAME_ENVIRONMENT;
    public static String SQL_DATA_USERNAME;

    @Value("${environment.database.password}")
    public String SQL_DATA_PASSWORD_ENVIRONMENT;
    public static String SQL_DATA_PASSWORD;

    @Value("${environment.invoHelper.key}")
    public String INVO_HELPER_ALL_ELEC_INVOICE_KEY_ENVIRONMENT;
    public static String INVO_HELPER_ALL_ELEC_INVOICE_KEY;

    @Value("${environment.invoHelper.checkUrl}")
    public String INVO_HELPER_ALL_ELEC_INVOICE_CHECK_URL_ENVIRONMENT;
    public static String INVO_HELPER_ALL_ELEC_INVOICE_CHECK_URL;

    @Value("${environment.invoHelper.renewUrl}")
    public String INVO_HELPER_ALL_ELEC_INVOICE_RENEW_URL_ENVIRONMENT;
    public static String INVO_HELPER_ALL_ELEC_INVOICE_RENEW_URL;

    @Value("${environment.invoHelper.delUrl}")
    public String INVO_HELPER_ALL_ELEC_INVOICE_DEL_URL_ENVIRONMENT;
    public static String INVO_HELPER_ALL_ELEC_INVOICE_DEL_URL;

    @PostConstruct
    public void initEnvironmentConstant() {
        SQL_DATA_URL = SQL_DATA_URL_ENVIRONMENT;
        SQL_DATA_USERNAME = SQL_DATA_USERNAME_ENVIRONMENT;
        SQL_DATA_PASSWORD = SQL_DATA_PASSWORD_ENVIRONMENT;
        INVO_HELPER_ALL_ELEC_INVOICE_KEY = INVO_HELPER_ALL_ELEC_INVOICE_KEY_ENVIRONMENT;
        INVO_HELPER_ALL_ELEC_INVOICE_CHECK_URL = INVO_HELPER_ALL_ELEC_INVOICE_CHECK_URL_ENVIRONMENT;
        INVO_HELPER_ALL_ELEC_INVOICE_RENEW_URL = INVO_HELPER_ALL_ELEC_INVOICE_RENEW_URL_ENVIRONMENT;
        INVO_HELPER_ALL_ELEC_INVOICE_DEL_URL = INVO_HELPER_ALL_ELEC_INVOICE_DEL_URL_ENVIRONMENT;
    }

}
