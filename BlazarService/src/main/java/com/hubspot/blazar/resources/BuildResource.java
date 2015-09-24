package com.hubspot.blazar.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.hubspot.blazar.base.BuildDefinition;
import com.hubspot.blazar.base.BuildState;
import com.hubspot.blazar.base.LogChunk;
import com.hubspot.blazar.base.ModuleBuild;
import com.hubspot.blazar.data.service.BuildDefinitionService;
import com.hubspot.blazar.data.service.BuildService;
import com.hubspot.blazar.data.service.BuildStateService;
import com.hubspot.horizon.AsyncHttpClient;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.jackson.jaxrs.PropertyFiltering;
import com.sun.jersey.api.NotFoundException;

import java.util.Set;

@Path("/build")
@Produces(MediaType.APPLICATION_JSON)
public class BuildResource {
  private final BuildDefinitionService buildDefinitionService;
  private final BuildStateService buildStateService;
  private final BuildService buildService;
  private final AsyncHttpClient asyncHttpClient;

  @Inject
  public BuildResource(BuildDefinitionService buildDefinitionService,
                       BuildStateService buildStateService,
                       BuildService buildService,
                       AsyncHttpClient asyncHttpClient) {
    this.buildDefinitionService = buildDefinitionService;
    this.buildStateService = buildStateService;
    this.buildService = buildService;
    this.asyncHttpClient = asyncHttpClient;
  }

  @GET
  @Path("/definitions")
  @PropertyFiltering
  public Set<BuildDefinition> getAllBuildDefinitions(@QueryParam("since") @DefaultValue("0") long since) {
    return buildDefinitionService.getAll(since);
  }

  @GET
  @Path("/states")
  @PropertyFiltering
  public Set<BuildState> getAllBuildStates(@QueryParam("since") @DefaultValue("0") long since) {
    return buildStateService.getAll(since);
  }

  @GET
  @Path("/{id}")
  public Optional<ModuleBuild> get(@PathParam("id") long id) {
    return buildService.get(id);
  }

  @GET
  @Path("/{id}/log")
  public LogChunk getLog(@PathParam("id") long id,
                         @QueryParam("offset") @DefaultValue("0") long offset,
                         @QueryParam("length") @DefaultValue("90000") long length) throws Exception {
    Optional<ModuleBuild> build = get(id);
    if (!build.isPresent()) {
      throw new NotFoundException("No build found for ID " + id);
    }

    Optional<String> logUrl = build.get().getBuild().getLog();
    if (!logUrl.isPresent()) {
      throw new NotFoundException("No build log URL found for ID " + id);
    }

    return getLog(replaceHost(logUrl.get()), offset, length);
  }

  @POST
  @Path("/module/{moduleId}")
  public ModuleBuild trigger(@PathParam("moduleId") int moduleId) {
    return trigger(buildDefinitionService.getByModule(moduleId).get());
  }

  @POST
  public ModuleBuild trigger(BuildDefinition buildDefinition) {
    BuildState buildState = buildService.enqueue(buildDefinition);

    return new ModuleBuild(buildState.getGitInfo(), buildState.getModule(), buildState.getPendingBuild().get());
  }

  @PUT
  public ModuleBuild update(ModuleBuild moduleBuild) {
    buildService.update(moduleBuild.getBuild());
    return moduleBuild;
  }

  private LogChunk getLog(String url, long offset, long length) throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
        .setUrl(url)
        .addHeader("X-HubSpot-User", "jhaber")
        .setQueryParam("offset").to(offset)
        .setQueryParam("length").to(length)
        .build();

    HttpResponse response = asyncHttpClient.execute(request).get();
    if (response.isSuccess()) {
      return response.getAs(LogChunk.class).withNextOffset(offset + length);
    } else if (response.getStatusCode() == 404 && url.endsWith("/tail_of_finished_service.log")) {
      return getLog(url.replace("/tail_of_finished_service.log", "/service.log"), offset, length);
    } else if (response.getStatusCode() == 404) {
      throw new NotFoundException("No build log found at URL " + url);
    } else {
      String message = String.format("Error hitting Singularity, status code %d, response %s", response.getStatusCode(), response.getAsString());
      throw new WebApplicationException(Response.serverError().entity(message).type(MediaType.TEXT_PLAIN_TYPE).build());
    }
  }

  private static String replaceHost(String url) {
    return UriBuilder.fromUri(url).host(System.getenv("SINGULARITY_HOST")).build().toString();
  }
}
