package com.placecruncher.server.controller.apiclient;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.core.Documentation;
import com.wordnik.swagger.core.DocumentationEndPoint;
import com.wordnik.swagger.core.DocumentationOperation;
import com.wordnik.swagger.core.DocumentationParameter;

@Controller
@RequestMapping("/api")
public class SwaggerController {
    private static final String API_VERSION = "1.0";
    private static final String SWAGGER_VERSION = "1.1";
    private static final String BASE_URL = "http://localhost:8080/placecruncher/api";

    @Autowired
    private ResourceLoader resourceLoader;

    public String getResource(String name) {
        try {
            Resource r = resourceLoader.getResource(name);

            StringWriter writer = new StringWriter();
            IOUtils.copy(r.getInputStream(), writer, "UTF-8");
            return writer.toString();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value="resources.json", method=RequestMethod.GET, produces={"application/json"} )
    public @ResponseBody String resources(HttpServletRequest request) {
        return getResource("file:/Users/ddieckma/play/placecruncher-java/tmp/resource.json");
//        return "{\"apiVersion\":\"0.2\",\"swaggerVersion\":\"1.1-SNAPSHOT\",\"basePath\":\"http://localhost:8080/placecruncher/api\",\"apis\":[{\"path\":\"/pets.{format}\",\"description\":\"Operations about pets\"}]}";
    }

    @RequestMapping(value="pets.json", method=RequestMethod.GET, produces={"application/json"} )
    public @ResponseBody String getPets() {
        return getResource("file:/Users/ddieckma/play/placecruncher-java/tmp/pets.json");
//        return "{\"apiVersion\":\"0.2\",\"swaggerVersion\":\"1.1-SNAPSHOT\",\"basePath\":\"http://localhost:8080/placecruncher/api\",\"resourcePath\":\"/pets\",\"apis\":[{\"path\":\"/pets.{format}/{petId}\",\"description\":\"Operations about pets\",\"operations\":[{\"httpMethod\":\"GET\",\"summary\":\"Find pet by ID\",\"notes\":\"Returns a pet based on ID\",\"responseClass\":\"Pet\",\"nickname\":\"getPetById\",\"parameters\":[{\"name\":\"petId\",\"description\":\"ID of pet that needs to be fetched\",\"paramType\":\"path\",\"required\":true,\"allowMultiple\":false,\"dataType\":\"string\"}],\"errorResponses\":[{\"code\":400,\"reason\":\"Invalid ID supplied\"},{\"code\":404,\"reason\":\"Pet not found\"}]}]},{\"path\":\"/pet.{format}\",\"description\":\"Operations about pets\",\"operations\":[{\"httpMethod\":\"POST\",\"summary\":\"Add a new pet to the store\",\"responseClass\":\"void\",\"nickname\":\"addPet\",\"parameters\":[{\"description\":\"Pet object that needs to be added to the store\",\"paramType\":\"body\",\"required\":true,\"allowMultiple\":false,\"dataType\":\"Pet\"}],\"errorResponses\":[{\"code\":405,\"reason\":\"Invalid input\"}]},{\"httpMethod\":\"PUT\",\"summary\":\"Update an existing pet\",\"responseClass\":\"void\",\"nickname\":\"updatePet\",\"parameters\":[{\"description\":\"Pet object that needs to be updated in the store\",\"paramType\":\"body\",\"required\":true,\"allowMultiple\":false,\"dataType\":\"Pet\"}],\"errorResponses\":[{\"code\":400,\"reason\":\"Invalid ID supplied\"},{\"code\":404,\"reason\":\"Pet not found\"},{\"code\":405,\"reason\":\"Validation exception\"}]}]},{\"path\":\"/pet.{format}/findByStatus\",\"description\":\"Operations about pets\",\"operations\":[{\"httpMethod\":\"GET\",\"summary\":\"Finds Pets by status\",\"notes\":\"Multiple status values can be provided with comma seperated strings\",\"responseClass\":\"List[Pet]\",\"nickname\":\"findPetsByStatus\",\"parameters\":[{\"name\":\"status\",\"description\":\"Status values that need to be considered for filter\",\"paramType\":\"query\",\"defaultValue\":\"available\",\"allowableValues\":{\"valueType\":\"LIST\",\"values\":[\"available\",\"pending\",\"sold\"],\"valueType\":\"LIST\"},\"required\":true,\"allowMultiple\":true,\"dataType\":\"string\"}],\"errorResponses\":[{\"code\":400,\"reason\":\"Invalid status value\"}]}]},{\"path\":\"/pet.{format}/findByTags\",\"description\":\"Operations about pets\",\"operations\":[{\"httpMethod\":\"GET\",\"summary\":\"Finds Pets by tags\",\"notes\":\"Muliple tags can be provided with comma seperated strings. Use tag1, tag2, tag3 for testing.\",\"deprecated\":true,\"responseClass\":\"List[Pet]\",\"nickname\":\"findPetsByTags\",\"parameters\":[{\"name\":\"tags\",\"description\":\"Tags to filter by\",\"paramType\":\"query\",\"required\":true,\"allowMultiple\":true,\"dataType\":\"string\"}],\"errorResponses\":[{\"code\":400,\"reason\":\"Invalid tag value\"}]}]}],\"models\":{\"Category\":{\"id\":\"Category\",\"properties\":{\"id\":{\"type\":\"long\"},\"name\":{\"type\":\"string\"}}},\"Pet\":{\"id\":\"Pet\",\"properties\":{\"tags\":{\"items\":{\"$ref\":\"Tag\"},\"type\":\"Array\"},\"id\":{\"type\":\"long\"},\"category\":{\"type\":\"Category\"},\"status\":{\"allowableValues\":{\"valueType\":\"LIST\",\"values\":[\"available\",\"pending\",\"sold\"],\"valueType\":\"LIST\"},\"description\":\"pet status in the store\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"photoUrls\":{\"items\":{\"type\":\"string\"},\"type\":\"Array\"}}},\"Tag\":{\"id\":\"Tag\",\"properties\":{\"id\":{\"type\":\"long\"},\"name\":{\"type\":\"string\"}}}}}";
    }

    @RequestMapping(value="store.json", method=RequestMethod.GET, produces={"application/json"} )
    public @ResponseBody String getStore() {
        return getResource("file:/Users/ddieckma/play/placecruncher-java/tmp/store.json");
    }

    @RequestMapping(value="swagger/resources.json", method=RequestMethod.GET,  produces={"application/json"} )
    public @ResponseBody Documentation swaggerResources(HttpServletRequest request) {
        Documentation documentation = new Documentation(API_VERSION, SWAGGER_VERSION, BASE_URL,null);
        // the {format} is REQUIRED
        DocumentationEndPoint endpoint = new DocumentationEndPoint("/sources.{format}", "Operations on sources");
        documentation.addApi(endpoint);
        return documentation;
    }

    @RequestMapping(value="sources.json", method=RequestMethod.GET, produces={"application/json"} )
    public @ResponseBody Documentation getSources() {
        Documentation documentation = new Documentation(API_VERSION, SWAGGER_VERSION, BASE_URL, "/sources");
        DocumentationEndPoint endpoint = new DocumentationEndPoint("/private/v1/sources/{id}", "Operations on sources");
        DocumentationOperation getSourcesOp = new DocumentationOperation(HttpMethod.GET.toString(), "Gets the sources", "Notes about getting sources");
        getSourcesOp.setNickname("The nickname");
        DocumentationParameter idParam = new DocumentationParameter("id", "The source ID", "", "int", "", null, true, false);
        idParam.setParamType("path");
        idParam.setDataType("int");
        getSourcesOp.addParameter(idParam);
//        DocumentationParameter urlParam = new DocumentationParameter("url", "The url associated with the source", "Notes about the URL associated with the source", "string", "", null, false, false);
//        urlParam.setParamType("query");
//        getSourcesOp.addParameter(urlParam);
        getSourcesOp.setResponseClass("string");
        endpoint.addOperation(getSourcesOp);
        documentation.addApi(endpoint);
        return documentation;
    }


}
