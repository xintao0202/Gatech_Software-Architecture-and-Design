package edu.gatech.cs6310.project7.service;

import edu.gatech.cs6310.project7.model.CommandResult;

/**
 * Base service class for commands.
 */
public interface Command<T extends CommandResult> {
    T execute(String... parameters);
}
