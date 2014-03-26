package net.nokok.twitduke.model.listener;

/**
 * MIT License. http://opensource.org/licenses/mit-license.php
 * Copyright (c) 2014 noko
 */
public interface ParserStateListener {
    void enabled();

    void disabled();

    void ready();

    void error();
}