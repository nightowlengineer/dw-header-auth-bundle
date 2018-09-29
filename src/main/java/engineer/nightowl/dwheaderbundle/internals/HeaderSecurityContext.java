package engineer.nightowl.dwheaderbundle.internals;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;

public class HeaderSecurityContext<P extends Principal> implements SecurityContext {

    private final P principal;
    private final List<String> groups;
    private final SecurityContext securityContext;

    public HeaderSecurityContext(final P principal, final List<String> groups, final SecurityContext securityContext)
    {
        this.principal = principal;
        this.groups = groups;
        this.securityContext = securityContext;
    }

    @Override
    public P getUserPrincipal()
    {
        return principal;
    }

    @Override
    public boolean isUserInRole(final String role)
    {
        return groups.contains(role);
    }

    @Override
    public boolean isSecure()
    {
        return securityContext.isSecure();
    }

    @Override
    public String getAuthenticationScheme()
    {
        return "HEADER_AUTH";
    }
}
