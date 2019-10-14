package ${package};

import java.util.HashMap;
import java.util.Map;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
<#list imports as import>
import ${import};
</#list>

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class MediaTypeDiscovery {
	private final Map<Class<? extends BlackDuckComponent>, String> mediaTypeMap = new HashMap<>();

	public MediaTypeDiscovery() {
		<#list mostRecentClassVersions as class>
    			mediaTypeMap.put(${class.getNonVersionedClassName()}.class, "${class.getMediaType()}");
		</#list>
	}

	public<T extends BlackDuckComponent> String determineMediaType(T bdClass) {
    		return mediaTypeMap.get(bdClass);
    	}

}