package ${packageName};

<#if hasLinksWithResults??>
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
</#if>
<#list imports as import>
import ${import};
</#list>

<#if isDeprecated??>
@Deprecated
</#if>
/**
<#if hasNewName??>
* ${className} is now called ${newName}
</#if>
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class ${className} extends ${parentClass} <#if buildable??>implements Buildable </#if>{

<#if hasLinksWithResults??>
	public static final Map<String, LinkResponse> links = new HashMap<>();
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
}
