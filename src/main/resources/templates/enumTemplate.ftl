package ${packageName};

import com.synopsys.integration.util.EnumUtils;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public enum ${enumClassName} {
${enumValues?join(",\n\t")};

public String prettyPrint() {
return EnumUtils.prettyPrint(this);
}

} 