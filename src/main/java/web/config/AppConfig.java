package web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private Proxy proxy;

    @Getter
    @Setter
    public static class Proxy {
        private Boolean onProxy;
        private String proxyHost;
        private Integer proxyPort;
    }

    public RequestProxyConfig buildProxy() {
        return RequestProxyConfig.create(this);
    }

}
