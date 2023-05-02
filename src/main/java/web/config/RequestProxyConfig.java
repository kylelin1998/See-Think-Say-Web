package web.config;

import kong.unirest.HttpRequest;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.HttpHost;

public class RequestProxyConfig {

    private ProxyTypeEnum type;
    private String hostName;
    private Integer port;

    public static RequestProxyConfig create(AppConfig appConfig) {
        RequestProxyConfig config = new RequestProxyConfig();
        if (null != appConfig && null != appConfig.getProxy() && appConfig.getProxy().getOnProxy()) {
            config.type = ProxyTypeEnum.HttpProxy;
            config.hostName = appConfig.getProxy().getProxyHost();
            config.port = appConfig.getProxy().getProxyPort();
        } else {
            config.type = ProxyTypeEnum.getDefault();
        }
        return config;
    }

    public void viaProxy(HttpRequest request) {
        switch (this.type) {
            case HttpProxy:
                request.proxy(this.hostName, this.port);
                break;
        }
    }

    public void viaProxy(Request request) {
        switch (this.type) {
            case HttpProxy:
                request.viaProxy(new HttpHost(this.hostName, this.port));
                break;
        }
    }

}
