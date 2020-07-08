package edu.netcracker.custom.swagger.starter;


import edu.netcracker.custom.swagger.starter.endpoint.Endpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class CustomSwagger {
    private static final Set<Class<?>> SIMPLE_CLS = new HashSet<>(Arrays.asList(Integer.class, int.class, Long.class, long.class,
            String.class, Double.class, double.class, Float.class, float.class, Instant.class, Boolean.class, boolean.class));
    private static final Set<String> SIMPLE_CLS_STR = SIMPLE_CLS.stream()
            .map(Class::getSimpleName).collect(Collectors.toSet());

    public List<Endpoint> allEndpoints(ApplicationContext context) {
        List<Endpoint> endpoints = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = context.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        for (RequestMappingInfo key : handlerMethods.keySet()) {
            Endpoint endpoint = new Endpoint();
            HandlerMethod method = handlerMethods.get(key);

            if (key.getMethodsCondition().isEmpty()) {
                endpoint.setMethod("ALL_METHODS");
            } else {
                endpoint.setMethod(key.getMethodsCondition().getMethods().iterator().next().name());
            }

            endpoint.setHref(key.getPatternsCondition().getPatterns().iterator().next());

            Annotation[][] annotations = method.getMethod().getParameterAnnotations();

            for (int i = 0; i < annotations.length; i++) {
                for (Annotation annotation : annotations[i]) {
                    if (annotation.annotationType().equals(RequestBody.class)) {
                        endpoint.setBody(processMethodParam(method, i));
                    } else if (annotation.annotationType().equals(RequestParam.class)) {
                        endpoint.setRequestParam(processMethodParam(method, i));
                    }
                }
            }

            String returnType = "void";
            if (!method.getMethod().getReturnType().getSimpleName().equals("void")) {
                if (SIMPLE_CLS.contains(method.getMethod().getReturnType())) {
                    returnType = method.getReturnType().getNestedParameterType().getSimpleName();
                } else if (Collection.class.isAssignableFrom(method.getMethod().getReturnType())) {
                    returnType = buildCollectionRepresentation(method.getReturnType().getGenericParameterType());
                } else if (Map.class.isAssignableFrom(method.getMethod().getReturnType())) {
                    returnType = method.getReturnType().getGenericParameterType().getTypeName();
                } else {
                    returnType = buildClassRepresentation(method.getMethod().getReturnType(), null);
                }
            }
            endpoint.setReturnType(returnType);

            endpoints.add(endpoint);
        }
        return endpoints;
    }

    private String processMethodParam(HandlerMethod method, int i) {
        if (SIMPLE_CLS.contains(method.getMethodParameters()[i].getNestedParameterType())) {
            return method.getMethodParameters()[i].getNestedParameterType().getSimpleName() + ": " +
                    method.getMethodParameters()[i].getParameterName();
        } else if (Collection.class.isAssignableFrom(method.getMethodParameters()[i].getNestedParameterType())) {
            return buildCollectionRepresentation(method.getMethodParameters()[i].getGenericParameterType());
        } else {
            return buildClassRepresentation(method.getMethodParameters()[i].getNestedParameterType(),
                    method.getMethodParameters()[i].getParameterName());
        }
    }

    private String buildCollectionRepresentation(Type type) {
        String typeStr = type.getTypeName();
        String innerType = typeStr.substring(typeStr.lastIndexOf(".") + 1, typeStr.indexOf(">"));
        String tempStr = typeStr.substring(0, typeStr.indexOf("<"));
        String outerType = tempStr.substring(tempStr.lastIndexOf(".") + 1);
        StringJoiner collectionRepresentation = new StringJoiner("", outerType + "<", ">");
        collectionRepresentation.add(innerType);
//        if (SIMPLE_CLS_STR.contains(innerType)) {
//            collectionRepresentation.add(innerType);
//        } else {
//            collectionRepresentation.add(buildClassRepresentation(, ""));
//        }
        return collectionRepresentation.toString();
    }

    private String buildClassRepresentation(Class<?> nestedParameterType, String nestedParameterName) {
        StringJoiner parameters = new StringJoiner(", ", Objects.nonNull(nestedParameterName) ?
                nestedParameterName + ": " : "", "");
//        Field[] fields = nestedParameterType.getDeclaredFields();
//        for (Field field : fields) {
//            if (SIMPLE_CLS.contains(field.getType())) {
//                parameters.add(field.getType().getSimpleName() + ": " + field.getName());
//            } else if (Collection.class.isAssignableFrom(field.getType())) {
//                parameters.add(buildCollectionRepresentation(field.getGenericType()));
//            } else {
//                parameters.add(buildClassRepresentation(field.getType(), field.getName()));
//            }
//        }
        return parameters.toString();
    }

}
