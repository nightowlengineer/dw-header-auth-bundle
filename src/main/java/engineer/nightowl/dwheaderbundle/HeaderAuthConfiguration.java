package engineer.nightowl.dwheaderbundle;

public class HeaderAuthConfiguration {

    private String headerName;

    public HeaderAuthConfiguration(){}

    public HeaderAuthConfiguration(final String headerName)
    {
        this.headerName = headerName;
    }

    public String getHeaderName()
    {
        return headerName;
    }

    public void setHeaderName(final String headerName)
    {
        this.headerName = headerName;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("headerName", headerName)
                .toString();
    }
}
