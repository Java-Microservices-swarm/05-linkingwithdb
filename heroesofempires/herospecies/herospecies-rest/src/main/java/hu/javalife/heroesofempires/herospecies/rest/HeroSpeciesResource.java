package hu.javalife.heroesofempires.herospecies.rest;

import hu.javalife.heroesofempires.hero.datamodel.Hero;
import hu.javalife.heroesofempires.herospecies.daomodel.HeroSpecies;
import hu.javalife.heroesofempires.herospecies.servicemodel.BusinessException;
import hu.javalife.heroesofempires.herospecies.servicemodel.HeroSpeciesService;
import hu.javalife.heroesofempires.species.daomodel.Species;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author krisztian
 */
@Path("/hs")
@Api(value = "Link of Hero & Species")
public class HeroSpeciesResource {
    @Inject
    HeroSpeciesService service;
    
    @GET
    public List<HeroSpecies> getHeroesBySpecies(@ApiParam(value = "Species ID") @QueryParam("speciesid") long pID){
        return service.getAllBySpecies(pID);
    }
    
    @GET
    public List<HeroSpecies> getSpeciesByHero(@ApiParam(value = "Hero ID") @QueryParam("heroid") long pID){
        return service.getAllBySpecies(pID);
    }
    
    
    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete Hero Species Link")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "id not available"),
        @ApiResponse(code = 500, message = "some exception")})    
    public Response delete(@ApiParam(value = "HeroSpecies Link id") long pID){
        try{
            service.delete(pID);
            return Response.ok().build();
        }
        catch(BusinessException e){return Response.status(404).entity(e).build();}
        catch(Throwable e){return Response.status(500).build();}
    }
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @ApiOperation(value = "New Link of HeroSpecies ",
        notes = "Create new Link of HeroSpecies ",
        consumes = "application/x-www-form-urlencoded",
        response = HeroSpecies.class)
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Hero or Species not available"),
        @ApiResponse(code = 400, message = "Percent is too large"),
        @ApiResponse(code = 500, message = "some exception")})    
    public Response add(
            @FormParam("percent") byte pPercent,
            @FormParam("heroid") long pHeroID,
            @FormParam("speciesid") long pSpeciesID){
        try{
            return Response.ok(service.add(pHeroID, pSpeciesID, pPercent)).build();
        }
        catch(BusinessException e){return Response.status(404).entity(e).build();}
        catch(Throwable e){return Response.status(500).build();}
    }    
}
