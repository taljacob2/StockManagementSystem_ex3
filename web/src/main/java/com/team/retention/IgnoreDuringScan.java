package com.team.retention;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks the {@link org.springframework.stereotype.Component} to be
 * <i>excluded</i> during
 * {@link org.springframework.context.annotation.ComponentScan}.
 */
@Retention(RetentionPolicy.RUNTIME) public @interface IgnoreDuringScan {}
