package com.hubspot.blazar.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;

import java.util.List;
import java.util.Objects;

public class GitInfo {
  private final Optional<Integer> id;
  private final String host;
  private final String organization;
  private final String repository;
  private final int repositoryId;
  private final String branch;
  private final boolean active;
  private final long createdTimestamp;
  private final long updatedTimestamp;

  @JsonCreator
  public GitInfo(@JsonProperty("id") Optional<Integer> id,
                 @JsonProperty("host") String host,
                 @JsonProperty("organization") String organization,
                 @JsonProperty("repository") String repository,
                 @JsonProperty("repositoryId") int repositoryId,
                 @JsonProperty("branch") String branch,
                 @JsonProperty("active") boolean active,
                 @JsonProperty("createdTimestamp") long createdTimestamp,
                 @JsonProperty("updatedTimestamp") long updatedTimestamp) {
    this.id = id;
    this.host = host;
    this.organization = organization;
    this.repository = repository;
    this.repositoryId = repositoryId;
    this.branch = branch;
    this.active = active;
    this.createdTimestamp = createdTimestamp;
    this.updatedTimestamp = updatedTimestamp;
  }

  public Optional<Integer> getId() {
    return id;
  }

  public String getHost() {
    return host;
  }

  public String getOrganization() {
    return organization;
  }

  public String getRepository() {
    return repository;
  }

  public int getRepositoryId() {
    return repositoryId;
  }

  public String getBranch() {
    return branch;
  }

  public boolean isActive() {
    return active;
  }

  public long getCreatedTimestamp() {
    return createdTimestamp;
  }

  public long getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  @JsonIgnore
  public String getFullRepositoryName() {
    return getOrganization() + '/' + getRepository();
  }

  public GitInfo withId(int id) {
    return new GitInfo(Optional.of(id), host, organization, repository, repositoryId, branch, active, createdTimestamp, updatedTimestamp);
  }

  public GitInfo withBranch(String branch) {
    return new GitInfo(id, host, organization, repository, repositoryId, branch, active, createdTimestamp, updatedTimestamp);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    GitInfo gitInfo = (GitInfo) o;
    return Objects.equals(repositoryId, gitInfo.repositoryId) &&
        Objects.equals(active, gitInfo.active) &&
        Objects.equals(id, gitInfo.id) &&
        Objects.equals(host, gitInfo.host) &&
        Objects.equals(organization, gitInfo.organization) &&
        Objects.equals(repository, gitInfo.repository) &&
        Objects.equals(branch, gitInfo.branch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, host, organization, repository, repositoryId, branch, active);
  }

  @JsonCreator
  public static GitInfo fromString(String uri) {
    List<String> parts = Splitter.on('#').splitToList(uri);
    String ref = parts.get(1);
    parts = Splitter.on('/').splitToList(parts.get(0));
    String host = parts.get(0);
    String organization = parts.get(1);
    String repository = parts.get(2);

    return new GitInfo(Optional.<Integer>absent(), host, organization, repository, 0, ref, false, System.currentTimeMillis(), System.currentTimeMillis());
  }
}
