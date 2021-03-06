package com.hubspot.blazar;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase;
import com.fasterxml.jackson.jaxrs.cfg.ObjectReaderInjector;
import com.fasterxml.jackson.jaxrs.cfg.ObjectReaderModifier;
import com.google.inject.Inject;
import com.hubspot.blazar.github.GitHubProtos;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GitHubNamingFilter implements ContainerRequestFilter {
  private final Set<Class<?>> gitHubClasses;

  @Inject
  public GitHubNamingFilter() {
    this.gitHubClasses = new HashSet<>(Arrays.asList(GitHubProtos.class.getClasses()));
  }

  @Override
  public ContainerRequest filter(ContainerRequest request) {
    ObjectReaderInjector.set(new ObjectReaderModifier() {

      @Override
      public ObjectReader modify(EndpointConfigBase<?> endpoint,
                                 MultivaluedMap<String, String> httpHeaders,
                                 JavaType resultType,
                                 ObjectReader r,
                                 JsonParser p) throws IOException {
        if (gitHubClasses.contains(resultType.getRawClass())) {
          return r.with(r.getConfig().with(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES));
        } else {
          return r;
        }
      }
    });

    return request;
  }
}
