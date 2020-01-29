package ${discoveryPackage};

import java.util.HashMap;
import java.util.Map;

<#list imports as import>
    import ${import};
</#list>

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ApiDiscovery {
	public static final Map<BlackDuckPath, LinkResponse> links = new HashMap<>();

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
