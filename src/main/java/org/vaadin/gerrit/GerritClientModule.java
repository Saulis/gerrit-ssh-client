package org.vaadin.gerrit;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.vaadin.gerrit.factories.CommandFactory;
import org.vaadin.gerrit.factories.GerritConnectionFactory;

public class GerritClientModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(CommandFactory.class));
        install(new FactoryModuleBuilder().build(GerritConnectionFactory.class));
    }
}
