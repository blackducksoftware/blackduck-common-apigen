<#include "licenseHeader.ftl">
package ${discoveryPackage};

import java.util.HashMap;
import java.util.Map;

<#list imports as import>
import ${import};
</#list>

<#include "doNotEdit.ftl">
public class ApiDiscovery {
    public static final Map<BlackDuckPath, BlackDuckPathResponse> links = new HashMap<>();

<#list apiPathData as data>
    public static final BlackDuckPath ${data.getJavaConstant()} = new BlackDuckPath("${data.getPath()}");
</#list>

<#list apiPathData as data>
    public static final ${data.getLinkType()} ${data.getJavaConstant()}_RESPONSE = new ${data.getLinkType()}(${data.getJavaConstant()}, ${data.getResultClass()}.class);
</#list>

    static {
<#list apiPathData as data>
        links.put(${data.getJavaConstant()}, ${data.getJavaConstant()}_RESPONSE);
</#list>
    }

}
