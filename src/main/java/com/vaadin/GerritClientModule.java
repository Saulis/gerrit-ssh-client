package com.vaadin;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.vaadin.factories.CommandFactory;
import com.vaadin.factories.GerritConnectionFactory;

public class GerritClientModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(CommandFactory.class));
        install(new FactoryModuleBuilder().build(GerritConnectionFactory.class));
    }
}
