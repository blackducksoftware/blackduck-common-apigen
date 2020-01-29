package ${componentPackage};

<#list imports as import>
import ${import};
</#list>

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ${className} extends ${baseClass} <#if buildable??>implements Buildable </#if>{

<#list classFields as field>
    private ${field.type} ${field.path};
</#list>
    private String mediaType = "${mediaType}";

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
	public String getMediaType() {
	   return mediaType;
	}
}
