/*
 * apigen-maintenance
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integrations.apigen.maintenance.utility;

import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

import java.util.*;
import java.util.regex.Pattern;

public class EquivalentClassIdentifier {

    public boolean hasAnEquivalent(JavaClass clazz1, List<JavaClass> classes) {
        for (JavaClass clazz2 : classes) {
            if (areEqual(clazz1, clazz2)) {
                return true;
            }
        }
        return false;
    }

    public boolean areEqual(JavaClass clazz1, JavaClass clazz2) {
        if (!clazz1.getClassName().equals(clazz2.getClassName())) {
            return false;
        }

        for (Field field1 : clazz1.getFields()) {
            boolean foundEqualField = false;
            for (Field field2 : clazz2.getFields()) {
                if (field1.getName().equals(field2.getName()) && field1.getType().equals(field2.getType())) {
                    foundEqualField = true;
                }
            }
            if (!foundEqualField) {
                return false;
            }
        }
        return true;
    }

    public Map<String, Set<String>> checkForPotentiallyEquivalentClasses(List<JavaClass> classes1, List<JavaClass> classes2, Pattern namesToIgnore) {
        Map<String, Set<String>> classesAndPotentialEquivalents = new HashMap<>();
        for (JavaClass clazz : classes2) {
            Set<String> potentialEquivalents = findPotentialEquivalents(clazz, classes1, namesToIgnore);
            if (!potentialEquivalents.isEmpty()) {
                classesAndPotentialEquivalents.put(clazz.getClassName(), potentialEquivalents);
            }
        }
        return classesAndPotentialEquivalents;
    }

    public Set<String> findPotentialEquivalents(JavaClass class1, List<JavaClass> classes, Pattern namesToIgnore) {
        Set<String> potentialEquivalents = new HashSet<>();
        for (JavaClass class2 : classes) {
            int class2FieldCount = class2.getFields().length;
            if (class2FieldCount == 0) {
                continue;
            }
            int fieldNamesInCommon = 0;
            for (Field generatedField : class2.getFields()) {
                for (Field temporaryField : class1.getFields()) {
                    if (generatedField.getName().equals(temporaryField.getName())) {
                        fieldNamesInCommon++;
                    }
                }
            }
            if (fieldNamesInCommon/class2FieldCount > .8 && fieldNamesInCommon/class1.getFields().length > .8 && namesToIgnore != null && !namesToIgnore.matcher(class2.getClassName()).matches()) {
                potentialEquivalents.add(class2.getClassName());
            }
        }
        return potentialEquivalents;
    }
}
