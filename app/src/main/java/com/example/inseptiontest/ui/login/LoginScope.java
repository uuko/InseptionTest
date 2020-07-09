package com.example.inseptiontest.ui.login;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginScope {
}
