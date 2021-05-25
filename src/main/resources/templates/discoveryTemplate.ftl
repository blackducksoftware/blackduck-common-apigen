<#include "licenseHeader.ftl">
package ${discoveryPackage};

<#list imports as import>
import ${import};
</#list>

<#include "doNotEdit.ftl">
public class ApiDiscovery {
<#list apiPathData as data>
    public static final BlackDuckPath<${data.getResultClass()}> ${data.getJavaConstant()} = new BlackDuckPath("${data.getPath()}", ${data.getResultClass()}.class, ${data.hasMultipleResults()?c});
</#list>

    private final HttpUrl blackDuckUrl;

    public ApiDiscovery(HttpUrl blackDuckUrl) {
        this.blackDuckUrl = blackDuckUrl;
    }

<#list apiPathData as data>
    public ${data.getUrlResponseClass()}<${data.getResultClass()}> meta${data.getJavaLabel()}Link() {
        return ${data.getUrlResponseMethod()}(${data.getJavaConstant()});
    }

</#list>
    public <T extends BlackDuckResponse> UrlSingleResponse<T> metaSingleResponse(BlackDuckPath<T> blackDuckPath) {
        HttpUrl url = getUrl(blackDuckPath);
        return new UrlSingleResponse<>(url, blackDuckPath.getResponseClass());
    }

    public <T extends BlackDuckResponse> UrlMultipleResponses<T> metaMultipleResponses(BlackDuckPath<T> blackDuckPath) {
        HttpUrl url = getUrl(blackDuckPath);
        return new UrlMultipleResponses<>(url, blackDuckPath.getResponseClass());
    }

    public HttpUrl getUrl(BlackDuckPath blackDuckPath) {
        return blackDuckPath.getFullBlackDuckUrl(blackDuckUrl);
    }

}
