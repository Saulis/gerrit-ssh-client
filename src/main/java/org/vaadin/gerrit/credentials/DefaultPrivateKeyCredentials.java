package org.vaadin.gerrit.credentials;

import java.io.File;

public class DefaultPrivateKeyCredentials extends Credentials {
    public DefaultPrivateKeyCredentials(String username) {
        super(username);
    }

    @Override
    public String getPrivateKey() {
        String home = System.getProperty("user.home");
        home = home == null ? new File(".").getAbsolutePath() : new File(home).getAbsolutePath();

        return new File(new File(home, ".ssh"), "id_rsa").getAbsolutePath();
    }
}

