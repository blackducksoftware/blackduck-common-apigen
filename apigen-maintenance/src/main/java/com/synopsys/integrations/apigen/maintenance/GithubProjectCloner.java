package com.synopsys.integrations.apigen.maintenance;

import static com.synopsys.integrations.apigen.maintenance.MaintenanceRunner.GIT_PATH;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;

import com.synopsys.integrations.apigen.maintenance.model.BlackDuckGitHubRepo;

public class GithubProjectCloner {
    private Logger logger;

    public GithubProjectCloner(Logger logger) {
        this.logger = logger;
    }

    public void cloneOrUpdateProject(String projectName) throws IOException {
        //File tempDirectory = Files.createTempDirectory(); // TODO
        //cloneOrUpdateProject(projectName, tempDirectory);
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
            logger.info("You must set the environment variable " + GIT_PATH);
            return "";
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
