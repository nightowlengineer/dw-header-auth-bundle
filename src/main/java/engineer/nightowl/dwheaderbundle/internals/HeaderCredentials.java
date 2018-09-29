package engineer.nightowl.dwheaderbundle.internals;

public class HeaderCredentials {
    private String principalId;

    public HeaderCredentials(final String principalId)
    {
        this.principalId = principalId;
    }

    public String getPrincipalId()
    {
        return principalId;
    }

    public void setPrincipalId(final String principalId)
    {
        this.principalId = principalId;
    }
}
