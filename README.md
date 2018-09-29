# Header authentication bundle for Dropwizard

This bundle provides the ability to define a header to take a user (or 'principal') ID from, and use as an authenticated
user throughout the application. This is mainly intended for use in applications that sit behind a reverse proxy that
will extract or provide the user information as required. This bundle also registers the RolesAllowedDynamic feature,
meaning you can add @RolesAllowed annotations to restrict calls as needed.

It's important that any application that relies on authentication done in this manner is **properly secured** from
malicious requests (for example, by locking down the application to only accept connections from the upstream proxy)

[![Build Status](https://travis-ci.org/nightowlengineer/dw-header-auth-bundle.svg?branch=master)](https://travis-ci.org/nightowlengineer/dw-header-auth-bundle)

## Maven Setup

```xml
<dependency>
  <groupId>engineer.nightowl</groupId>
  <artifactId>dw-header-auth-bundle</artifactId>
  <version>1.3.5-1</version>
</dependency>
```

## Getting Started

Implement the HeaderAuthConfiguration:
```java
public class SampleConfiguration extends Configuration implements HeaderAuthConfiguration {

  @NotNull
  @JsonProperty("authentication")
  private HeaderAuthConfiguration headerAuthConfiguration;

  @Override
  public HeaderAuthConfiguration getHeaderAuthConfiguration() {
    return headerAuthConfiguration;
  }
}
```

Add the bundle:
```java
public class SampleService extends Application<SampleConfiguration> {
    public static void main(String[] args) throws Exception {
        new SampleService().run(args);
    }

    @Override
    public void initialize(Bootstrap<SampleConfiguration> bootstrap) {
        
        // User represents your internal user representation which extends Principal
        // UserService represents your internal user information source which extends PrincipalService
        final HeaderAuthBundle<User, UserService> headerAuthBundle =
            new HeaderAuthBundle<>(User.class, new UserService());
        
        bootstrap.addBundle(headerAuthBundle);
    }

    @Override
    public void run(SampleConfiguration configuration, Environment environment) {
        ...
    }
}
```

Inside your service's configuration yml file, add the header name that should be inspected:
```yml
authentication:
  headerName: USER_ID
```

And that's it! Any request that is sent to your service will inspect the header USER_ID and use this to return a single
user and their roles from your UserService.