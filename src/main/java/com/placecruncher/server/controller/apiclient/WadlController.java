package com.placecruncher.server.controller.apiclient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.java.dev.wadl.WadlApplication;
import net.java.dev.wadl.WadlDoc;
import net.java.dev.wadl.WadlMethod;
import net.java.dev.wadl.WadlParam;
import net.java.dev.wadl.WadlParamStyle;
import net.java.dev.wadl.WadlRepresentation;
import net.java.dev.wadl.WadlRequest;
import net.java.dev.wadl.WadlResource;
import net.java.dev.wadl.WadlResources;
import net.java.dev.wadl.WadlResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
@RequestMapping("/application.wadl")
public class WadlController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @RequestMapping(method=RequestMethod.GET, produces={"application/xml"} )
    public @ResponseBody WadlApplication generateWadl(HttpServletRequest request) {
        WadlApplication result = new WadlApplication();
        WadlDoc doc = new WadlDoc();
        doc.setTitle("REST Service WADL");
        result.getDoc().add(doc);
        WadlResources wadResources = new WadlResources();
        wadResources.setBase(getBaseUrl(request));

        Map<RequestMappingInfo, HandlerMethod> handletMethods = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handletMethods.entrySet()) {
            WadlResource wadlResource = new WadlResource();

            HandlerMethod handlerMethod = entry.getValue();
            RequestMappingInfo mappingInfo = entry.getKey();

            Set<String> pattern =  mappingInfo.getPatternsCondition().getPatterns();
            Set<RequestMethod> httpMethods =  mappingInfo.getMethodsCondition().getMethods();
            ProducesRequestCondition producesRequestCondition = mappingInfo.getProducesCondition();
            Set<MediaType> mediaTypes = producesRequestCondition.getProducibleMediaTypes();

            for (RequestMethod httpMethod : httpMethods) {
                WadlMethod wadlMethod = new WadlMethod();

                for (String uri : pattern) {
                    wadlResource.setPath(uri);
                }

                wadlMethod.setName(httpMethod.name());
                Method javaMethod = handlerMethod.getMethod();
                wadlMethod.setId(javaMethod.getName());
                WadlDoc wadlDocMethod = new WadlDoc();
                wadlDocMethod.setTitle(javaMethod.getDeclaringClass().getName()+"."+javaMethod.getName());
                wadlMethod.getDoc().add(wadlDocMethod);

                // Request
                WadlRequest wadlRequest = new WadlRequest();

                Annotation[][] annotations = javaMethod.getParameterAnnotations();
                for (Annotation[] annotation : annotations) {
                    for (Annotation annotation2 : annotation) {
                        if (annotation2 instanceof RequestParam ) {
                            RequestParam param2 = (RequestParam)annotation2;
                            WadlParam waldParam = new WadlParam();
                            waldParam.setName(param2.value());
                            waldParam.setStyle(WadlParamStyle.QUERY);
                            waldParam.setRequired(param2.required());
                            String defaultValue = cleanDefault(param2.defaultValue());
                            if ( !defaultValue.equals("") ) {
                                waldParam.setDefault(defaultValue);
                            }
                            wadlRequest.getParam().add(waldParam);
                        } else if ( annotation2 instanceof PathVariable ) {
                            PathVariable param2 = (PathVariable)annotation2;
                            WadlParam waldParam = new WadlParam();
                            waldParam.setName(param2.value());
                            waldParam.setStyle(WadlParamStyle.TEMPLATE);
                            waldParam.setRequired(true);
                            wadlRequest.getParam().add(waldParam);
                        }
                    }
                }
                if ( ! wadlRequest.getParam().isEmpty() ) {
                    wadlMethod.setRequest(wadlRequest);
                }

                // Response
                if ( !mediaTypes.isEmpty() ) {
                    WadlResponse wadlResponse = new WadlResponse();
                    wadlResponse.getStatus().add(200l);
                    for (MediaType mediaType : mediaTypes) {
                        WadlRepresentation wadlRepresentation = new WadlRepresentation();
                        wadlRepresentation.setMediaType(mediaType.toString());
                        wadlResponse.getRepresentation().add(wadlRepresentation);
                    }
                    wadlMethod.getResponse().add(wadlResponse);
                }

                wadlResource.getMethodOrResource().add(wadlMethod);

            }

            wadResources.getResource().add(wadlResource);

        }
        result.getResources().add(wadResources);

        return result;
    }

    private String getBaseUrl(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + requestUri;
    }

    private String cleanDefault(String value) {
        return StringEscapeUtils.escapeXml(value);
    }

}
