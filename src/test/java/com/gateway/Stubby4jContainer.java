package com.gateway;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.MountableFile;

public class Stubby4jContainer extends GenericContainer<Stubby4jContainer> {

  public Stubby4jContainer() {
    super("azagniotov/stubby4j:latest-jre21");
    this.withExposedPorts(8882);
    this.withEnv("YAML_CONFIG", "stubby4j.yaml");
    this.withCopyFileToContainer(
        MountableFile.forClasspathResource("/stubby4j.yaml/"), "/home/stubby4j/data/stubby4j.yaml");
  }
}
