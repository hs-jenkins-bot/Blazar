package com.hubspot.blazar.discovery.hsstatic;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class StaticConfig {

  private final String name;
  private final Version majorVersion;
  private final boolean isCurrentVersion;
  private Map<String, Version> runtimeDeps;
  private final Map<String, Version> deps;

  @JsonCreator
  public StaticConfig(@JsonProperty("name") String name,
                      @JsonProperty("majorVersion") Version majorVersion,
                      @JsonProperty("isCurrentVersion") boolean isCurrentVersion,
                      @JsonProperty("runtimeDeps") Map<String, Version> runtimeDeps,
                      @JsonProperty("deps") Map<String, Version> deps) {
    this.name = name;
    this.majorVersion = majorVersion;
    this.isCurrentVersion = isCurrentVersion;
    this.runtimeDeps = Objects.firstNonNull(runtimeDeps, Collections.<String, Version>emptyMap());
    this.deps = Objects.firstNonNull(deps, Collections.<String, Version>emptyMap());
  }

  public String getName() {
    return name;
  }

  public Version getMajorVersion() {
    return majorVersion;
  }

  public boolean isCurrentVersion() {
    return isCurrentVersion;
  }

  public Map<String, Version> getRuntimeDeps() {
    return runtimeDeps;
  }

  public Map<String, Version> getDeps() {
    return deps;
  }
}
