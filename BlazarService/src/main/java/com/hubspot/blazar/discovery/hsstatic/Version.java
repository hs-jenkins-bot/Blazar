package com.hubspot.blazar.discovery.hsstatic;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Version {
  private final int version;

  @JsonCreator
  public Version(int version) {
    this.version = version;
  }

  public int getVersion() {
    return version;
  }

  @JsonCreator
  public static Version fromString(String string) {
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
    return new Version(max);
  }

  @Override
  public String toString()  {
    return String.valueOf(version);
  }
}
