<#include "licenseHeader.ftl">
package ${packageName};

<#list imports as import>
import ${import};
</#list>

<#if hasNewName??>
// ${className} from the previous API is now called ${newName}
</#if>
<#include "doNotEdit.ftl">
<#if isDeprecated??>
@Deprecated
</#if>
public class ${className} extends ${baseClass} <#if buildable??>implements Buildable </#if>{
<#if hasLinksWithResults??>
    public static final Map<String, LinkBlackDuckResponse> links = new HashMap<>();

</#if>
<#if hasLinks??>
    <#list links as link>
    public static final String ${link.javaConstant()} = "${link.label}";
    </#list>

    <#list links as link>
        <#if link.resultClass??>
    public static final ${link.linkType()} ${link.javaConstant()}_RESPONSE = new ${link.linkType()}(${link.javaConstant()}, ${link.resultClass()}.class);
        </#if>
    </#list>

</#if>
<#if hasLinksWithResults??>
    static {
    <#list links as link>
        <#if link.resultClass??>
        links.put(${link.javaConstant()}, ${link.javaConstant()}_RESPONSE);
        </#if>
    </#list>
    }

</#if>
<#list classFields as field>
    private ${field.type} ${field.path};
</#list>

<#if buildable??>
    public static ${className}Builder newBuilder() {
        return new ${className}Builder();
    }

</#if>
<#list classFields as field>
    public ${field.type} get${field.path?cap_first}() {
        return ${field.path};
    }

    public void set${field.path?cap_first}(${field.type} ${field.path}) {
        this.${field.path} = ${field.path};
    }

</#list>
<#if hasLinks??>
    <#list links as link>
        <#if link.resultClass??>
    public ${link.urlResponseType()} meta${link.javaLabel()}Link() {
        return ${link.metaResponseMethod()}(${link.javaConstant()}_RESPONSE);
    }

    public Optional<${link.urlResponseType()}> meta${link.javaLabel()}LinkSafely() {
        return ${link.metaResponseMethod()}Safely(${link.javaConstant()}_RESPONSE);
    }

        </#if>
    </#list>
</#if>
}
