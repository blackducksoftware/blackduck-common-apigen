/*
 * apigen-maintenance
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integrations.apigen.maintenance.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;

import com.synopsys.integrations.apigen.maintenance.model.BlackDuckGitHubRepo;

public class GithubProjectCloner {
    private static final String GIT_PATH = "GIT_PATH";
    private Logger logger;

    public GithubProjectCloner(Logger logger) {
        this.logger = logger;
    }

    // If a destination is not specified, the project will be cloned to a temporary directory which will then be returned to the caller.
    public File cloneOrUpdateProject(String projectName) throws IOException {
        File projectDirectory = new File(String.format("./build/%s", projectName)); // TODO - is build folder an acceptable place for this?
        cloneOrUpdateProject(projectName, projectDirectory);
        return projectDirectory;
    }

    public void cloneOrUpdateProject(String projectName, File destination) throws IOException {
        BlackDuckGitHubRepo projectRepo = new BlackDuckGitHubRepo(projectName);
        File clonedProjectFolder = new File(destination, projectName);
        clonedProjectFolder.mkdirs();

        Runtime runtime = Runtime.getRuntime();
        String gitPath = getGitPath();
        if (new File(projectName).exists()) {
            updateProject(runtime, projectName, gitPath);
        } else {
            cloneProject(runtime, gitPath, projectRepo.getProjectUrl(), clonedProjectFolder.getAbsolutePath());
        }
    }

    private String getGitPath() {
        String gitPath = System.getenv(GIT_PATH);
        if (gitPath == null) {
            logger.info(String.format("Could not find git executable.  You must set the environment variable %s", GIT_PATH));
            System.exit(0);
        }
        return gitPath;
    }

    private void updateProject(Runtime runtime, String gitPath, String projectDestination) throws IOException {
        String gitPullCommand = String.format("%s pull origin master %s", gitPath, projectDestination);
        runtime.exec(gitPullCommand);
    }

    private void cloneProject(Runtime runtime, String gitPath, String url, String projectDestination) throws IOException {
        String cloneCommand = String.format("%s clone %s %s", gitPath, url + ".git", projectDestination);
        runtime.exec(cloneCommand);
    }
}
