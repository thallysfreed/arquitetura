package com.poc;

import com.poc.config.DefaultProfileUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * This is a helper Java class that provides an alternative to creating a {@code web.xml}.
 * This will be invoked only when the application is deployed to a Servlet container like Tomcat, JBoss etc.
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        /**
         * set a default to use when no profile is configured.
         */
        DefaultProfileUtil.addDefaultProfile(application.application());
        return application.sources(PocApp.class);
    }
}
curl 'http://localhost:8080/api/norma-tecnicas'
  -H 'Connection: keep-alive'
  -H 'Pragma: no-cache'
  -H 'Cache-Control: no-cache'
  -H 'Accept: application/json, text/plain, */*'
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYxODI2NzExN30.OpA-e4yLYQrUxE-g1uC7Zxlr9homwBrpKfvOTJKnFblmg2I0yQswosj6kDc33O-_QNe0YMHIUP84jJnaol5YDA'
  -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36 OPR/75.0.3969.149' \
  -H 'Sec-Fetch-Site: same-origin'
  -H 'Sec-Fetch-Mode: cors'
  -H 'Sec-Fetch-Dest: empty'
  -H 'Referer: http://localhost:8080/norma-tecnica'
  -H 'Accept-Language: pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7'
  --compressed