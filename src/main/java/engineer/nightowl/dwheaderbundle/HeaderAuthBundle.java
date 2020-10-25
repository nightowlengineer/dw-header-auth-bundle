package engineer.nightowl.dwheaderbundle;

import engineer.nightowl.dwheaderbundle.internals.HeaderAuthBundleConfiguration;
import engineer.nightowl.dwheaderbundle.internals.HeaderAuthenticationFilter;
import engineer.nightowl.dwheaderbundle.internals.HeaderAuthenticator;
import engineer.nightowl.dwheaderbundle.internals.PrincipalService;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.security.Principal;

public class HeaderAuthBundle<P extends Principal, S extends PrincipalService<P>>
        implements ConfiguredBundle<HeaderAuthBundleConfiguration> {

    private final Class<P> principalClass;
    private final S principalService;

    public HeaderAuthBundle(final Class<P> principalClass, final S principalService)
    {
        this.principalClass = principalClass;
        this.principalService = principalService;
    }

    @Override
    public void run(HeaderAuthBundleConfiguration headerAuthBundleConfiguration, Environment environment) {
        final HeaderAuthConfiguration configuration = headerAuthBundleConfiguration.getHeaderAuthConfiguration();
        final HeaderAuthenticator<P, S> headerAuthenticator = new HeaderAuthenticator<>(principalService);
        final HeaderAuthenticationFilter<P, S> headerAuthenticationFilter =
                new HeaderAuthenticationFilter<>(headerAuthenticator, configuration, principalService);

        environment.jersey().register(new AuthDynamicFeature(headerAuthenticationFilter));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(principalClass));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
    }

}
