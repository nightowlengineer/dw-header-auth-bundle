package engineer.nightowl.dwheaderbundle.internals;

import java.security.Principal;
import java.util.List;

public abstract class PrincipalService<P extends Principal> {

    /**
     * Return a {@link Principal} based on a given ID
     * @param principalId representing some ID
     * @return the associated {@link Principal}
     */
    public abstract P getPrincipalById(final String principalId);

    /**
     * Get a list of roles for a given {@link Principal}
     * @param principal to fetch roles for
     * @return a list of roles for the given {@link Principal}
     */
    public abstract List<String> getPrincipalRoles(final P principal);
}
