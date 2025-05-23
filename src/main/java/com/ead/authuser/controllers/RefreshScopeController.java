package com.ead.authuser.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class RefreshScopeController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 07/02/2025
 */
@RestController
@RefreshScope
public class RefreshScopeController {

    @Value("${authuser.refreshscope.name}")
    private String name;

    @RequestMapping("/refreshscope")
    public String refreshscope() {
        return this.name;
    }

}
