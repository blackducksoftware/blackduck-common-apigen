package ${packageName};

import com.synopsys.integration.util.EnumUtils;

<#if isDeprecated??>
@Deprecated
</#if>
/**
<#if hasNewName??>
* ${className} is now called ${newName}
</#if>
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public enum ${className} {
	${enumValues?join(",\n\t")};

	private String mediaType = "${mediaType}";

	public String getMediaType() {
	   return mediaType;
	}

	public String prettyPrint() {
	   return EnumUtils.prettyPrint(this);
	}
}
