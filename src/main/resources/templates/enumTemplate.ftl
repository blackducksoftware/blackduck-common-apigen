<#include "licenseHeader.ftl">
package ${packageName};

import com.synopsys.integration.util.EnumUtils;

<#if hasNewName??>
// ${className} is now called ${newName}
</#if>
<#if isLatestMediaVersionEnum??>
// ${className} is equivalent to ${className}V${latestEnumMediaVersion}
</#if>
<#include "doNotEdit.ftl">
<#if isDeprecated??>
@Deprecated
</#if>
public enum ${className} {
    ${enumValues?join(",\n\t")};

    public String prettyPrint() {
        return EnumUtils.prettyPrint(this);
    }

}
