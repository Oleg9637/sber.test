package com.sber.test;

import org.apache.maven.plugin.MojoExecutionException;

public interface VersionProvider {

    String getVersion (String command) throws MojoExecutionException;
}
