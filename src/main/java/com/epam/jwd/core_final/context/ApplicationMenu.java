package com.epam.jwd.core_final.context;

// todo replace Object with your own types

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidInputException;

public interface ApplicationMenu {

    ApplicationContext context = NassaContext.getInstance();

    boolean printAvailableOptions() throws InvalidInputException;

    boolean handleUserInput() throws InvalidInputException;

}
