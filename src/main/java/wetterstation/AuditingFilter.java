package wetterstation;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 * Diese Klasse dient zur Protokollierung von Aktionen in der MongoDB
 * 
 * @author Michael Inden
 * 
 * Copyright 2016 by Michael Inden 
 */
@Provider
public class AuditingFilter implements ContainerResponseFilter
{
    private static final Logger LOGGER = Logger.getLogger("AuditingFilter");

    @Context
    ResourceInfo                resourceInfo;

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
                    throws IOException
    {
        final Class<?> resourceClass = resourceInfo.getResourceClass();
        final Method method = resourceInfo.getResourceMethod();

        LOGGER.info("called: method '" + method.getName() + "' of class " + resourceClass.getSimpleName());

        final String className = resourceClass.getSimpleName();
        MongoDbWriter.getInstance().writeAuditLogEntry(className, method.getName(), LocalDateTime.now());
    }
}
