package hu.javalife.heroesofempires.hero.rest;

import hu.javalife.heroesofempires.hero.datamodel.Hero;
import hu.javalife.heroesofempires.hero.servicemodel.BusinessException;
import hu.javalife.heroesofempires.hero.servicemodel.HeroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author krisztian
 */
@Path("hero")
@ApplicationScoped
@Api(value = "/hero", consumes = "application/json")
public class HeroResource {
    @Inject
    HeroService service;
    

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @ApiOperation(value = "New Hero",
        notes = "Create new hero",
        consumes = "application/x-www-form-urlencoded",
        response = Hero.class)
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Name of Hero not available"),
        @ApiResponse(code = 500, message = "some exception")})
    public Response add(
            @ApiParam(value = "Name of Hero", required = true) @FormParam("name") String pName, 
            @ApiParam(value = "Description of Hero", required = true) @FormParam("desc") String pDesc){

        Hero hero = new Hero();
        hero.setName(pName);
        hero.setDescription(pDesc);
        try{    
            return Response.ok(service.add(hero)).build();
        }
        catch(BusinessException e){return Response.status(404).entity(e).build();}                    
        catch(Throwable e){return Response.status(500).build();}
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get all heroes",
        notes = "List of heroes",
        response = Hero.class,
        responseContainer = "List")    
    public List<Hero> getAll(){ return service.getAll();}
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Hero by Id",
        notes = "Hero by Id",
        response = Hero.class)
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Hero not found"),
        @ApiResponse(code = 500, message = "some exception")})
    public Response getById(@ApiParam(value = "ID of Hero", required = true) @PathParam("id") @DefaultValue("0") long pId){
        try{return Response.ok(service.getById(pId)).build();}
        catch(BusinessException e){return Response.status(404).entity(e).build();}
        catch(Throwable t){return Response.status(500).build();}
    }
    
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Heroes by name",
        notes = "Heroes by name",
        response = Hero.class,
        responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Hero not found"),
        @ApiResponse(code = 500, message = "some exception")})
    public Response getByName(
            @ApiParam(value = "Name of Hero", required = true) @QueryParam("name") @DefaultValue("") String pName){
        try{
            return Response.ok(service.getByName(pName)).build();
        }
        catch(BusinessException e){
            return Response.status(404).entity(e).build();
        }
        catch(Throwable e){
            return Response.status(500).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @ApiOperation(value = "Hero by Id",
        notes = "Hero by Id",
        consumes = "application/x-www-form-urlencoded",
        response = Hero.class)
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Name not available"),
        @ApiResponse(code = 404, message = "Hero not found"),
        @ApiResponse(code = 500, message = "some exception")})
    public Response modifyById(
            @ApiParam(value = "ID of Hero", required = true) @PathParam("id") @DefaultValue("0") long pId,
            @ApiParam(value = "New name of Hero", required = true) @FormParam("name") String pName, 
            @ApiParam(value = "New description of Hero", required = true) @FormParam("desc") String pDesc){
        
        try{
            Hero hero = new Hero();
            hero.setName(pName);
            hero.setDescription(pDesc);
            return Response.ok(service.modify(pId, hero)).build();
        }
        catch(BusinessException e){
            if(e.getCode()==1) return Response.status(400).build();
            else return Response.status(404).build();
        }
        catch(Throwable e){
            return Response.status(500).build();
        }         
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete Hero by Id",
        notes = "Delte Hero by Id")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Hero not found"),
        @ApiResponse(code = 500, message = "some exception")})
    public Response deleteById(@ApiParam(value = "ID of Hero", required = true) @PathParam("id") @DefaultValue("0") long pId){
        try{
            service.delete(pId);
            return Response.ok().build();
        }
        catch(BusinessException e){
            return Response.status(404).entity(e).build();
        }
        catch(Throwable e){
            return Response.status(500).build();
        }
    }


    @POST
    @Path("/part")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Part of Heroes",
        notes = "part of heroes and ordering",
        response = PagerViewModel.class)
    @ApiResponses(value = {})
    public Response getPart(
            @ApiParam(value = "Name of Sort propery ", required = true) @QueryParam("sort") @DefaultValue("name") String pSort,
            @ApiParam(value = "Sort direction ", required = true) @QueryParam("direction") @DefaultValue("ASC") String pDiection,
            @ApiParam(value = "Initial index", required = true) @QueryParam("start") @DefaultValue("0") int pStart,
            @ApiParam(value = "Count of elements", required = true) @QueryParam("count") @DefaultValue("0") int pCount,
            @ApiParam(value = "Count of elements", required = false) Hero hero){
        PagerViewModel res = new PagerViewModel(
                service.getNumberOfHeroes(),
                pStart,pCount,
                service.search(pStart, pCount, hero, pSort, pDiection));
        return Response.ok(res).build();
    }            
}
