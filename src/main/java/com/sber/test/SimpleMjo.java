package com.sber.test;

import org.apache.maven.Maven;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

/*
* an example Maven Mojo that resolves the current project's git revision and add that a new property to the current project
* */


@Mojo(name = "version", defaultPhase = LifecyclePhase.INITIALIZE)
public class SimpleMjo extends AbstractMojo {

    /*
    this command use to get the current
    */

    @Parameter(property = "git.command", defaultValue = "git rev-parse --short HEAD")
    private String command;

    @Parameter(property = "project", readonly = true)
    private MavenProject project;

    @Inject
    VersionProvider versionProvider;

    @Override
    public void execute() throws MojoExecutionException {

        String version = versionProvider.getVersion(command);

        project.getProperties().put("example version", version);

        getLog().info("Git hash " + version);
    }


}
