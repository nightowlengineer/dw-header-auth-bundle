package engineer.nightowl.dwheaderbundle.internals;

import engineer.nightowl.dwheaderbundle.HeaderAuthConfiguration;
import io.dropwizard.auth.AuthFilter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class HeaderAuthenticationFilter<P extends Principal, S extends PrincipalService<P>>
        extends AuthFilter<HeaderCredentials, P> {

    private HeaderAuthenticator<P, S> authenticator;
    private HeaderAuthConfiguration configuration;
    private S principalService;

    public HeaderAuthenticationFilter(final HeaderAuthenticator<P, S> authenticator,
                                      final HeaderAuthConfiguration configuration, final S principalService)
    {
        this.authenticator = authenticator;
        this.configuration = configuration;
        this.principalService = principalService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        final HeaderCredentials credentials = getCredentials(requestContext);
        final Optional<P> authenticatedPrincipal = authenticator.authenticate(credentials);

        if (authenticatedPrincipal.isPresent())
        {
            final P principal = authenticatedPrincipal.get();
            final List<String> principalRoles = principalService.getPrincipalRoles(principal);
            final SecurityContext requestSecurityContext = requestContext.getSecurityContext();
            final HeaderSecurityContext<P> securityContext = new HeaderSecurityContext<>(principal,
                    principalRoles, requestSecurityContext);

            requestContext.setSecurityContext(securityContext);
        }
        else
        {
            throw new WebApplicationException("Principal not found", Response.Status.UNAUTHORIZED);
        }
    }

    private HeaderCredentials getCredentials(final ContainerRequestContext requestContext)
    {
        return new HeaderCredentials(requestContext.getHeaderString(configuration.getHeaderName()));
    }
}
