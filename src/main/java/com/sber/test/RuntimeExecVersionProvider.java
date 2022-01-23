package com.sber.test;

import org.apache.maven.plugin.MojoExecutionException;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

@Named
@Singleton
public class RuntimeExecVersionProvider implements VersionProvider{
    public String getVersion (String command) throws MojoExecutionException{
        try {
            StringBuilder builder = new StringBuilder();

            Process process = Runtime.getRuntime().exec(command);
            Executors.newSingleThreadExecutor().submit(() ->
                    new BufferedReader(new InputStreamReader(process.getInputStream()))
                            .lines()
                            .forEach(builder::append)
            );
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new MojoExecutionException("Execution of command " + command + " failed whith the exit code: " + exitCode);
            }
            // return the output
            return  builder.toString();
        } catch (IOException | InterruptedException e) {
            throw  new MojoExecutionException("Execution of command " + command + " failed", e);
        }
    }
}
