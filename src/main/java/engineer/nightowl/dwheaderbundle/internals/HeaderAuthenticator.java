package engineer.nightowl.dwheaderbundle.internals;

import io.dropwizard.auth.Authenticator;

import java.security.Principal;
import java.util.Optional;

public class HeaderAuthenticator<P extends Principal, S extends PrincipalService<P>>
        implements Authenticator<HeaderCredentials, P> {

    private S principalService;

    public HeaderAuthenticator(final S principalService)
    {
        this.principalService = principalService;
    }

    @Override
    public Optional<P> authenticate(final HeaderCredentials headerCredentials) {
        return Optional.of(principalService.getPrincipalById(headerCredentials.getPrincipalId()));
    }
}
