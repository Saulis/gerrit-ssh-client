# gerrit-ssh-client

A small client for using [Gerrit](https://code.google.com/p/gerrit/) SSH Command Line Tools. Built with Java8.

### Supported Feature(s)

- Get Members of a Group (ls-members)
- Get Member of a Group with UserId (ls-members)

## Usage

```java
// Use the default private key for a user. Theres also
Credentials credentials = new DefaultPrivateKeyCredentials("username");

// Create a client using the credentials
GerritClient client = new GerritClient("review.foobar.com", credentials);

// Gets all the members of a specific group.
Member[] allMembers = client.getGroupMembers("All Users");

// Get a member from a group using the user id.
Member member = client.getMemberFromGroup(1234, "The Best of Users");
```

## Contributing

Need a feature? Just send in a pull request.

Oh and when running *mvn integration-test*, remember to use -Dusername=&lt;username&gt; and -Dhost=&lt;host&gt;
