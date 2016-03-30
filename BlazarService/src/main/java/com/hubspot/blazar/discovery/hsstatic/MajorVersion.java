package com.hubspot.blazar.discovery.hsstatic;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MajorVersion {
  private final int version;

  @JsonCreator
  public MajorVersion (int version) {
    this.version = version;
  }

  public int getVersion() {
    return version;
  }

  @JsonCreator
  public static MajorVersion fromString(String string) {
    String[] split = string.split(" ?\\|\\| ?");
    Integer max = 0;
    for (String s: split) {
      // Static projects can depend on Major.Minor versions, uncommon but can happen
      if (s.contains(".")) {
        s = s.split(".")[0];
      }
      int j = Integer.valueOf(s);
      if (j > max) {
        max = j;
      }
    }
    return new MajorVersion(max);
  }
}
