package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks!");
        functionality.add("Provides connection with Trello account!");
        functionality.add("Application allows sending tasks to Trello!");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost/tasks_frontend/");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message", "Have a wonderful day!");
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("company_email", adminConfig.getCompanyEmail());
        context.setVariable("company_phone", adminConfig.getCompanyPhone());
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildTasksNumberShedulerEmail(String message) {

        List<String> appCredentials = new ArrayList<>();
        appCredentials.add(adminConfig.getAppName());
        appCredentials.add("Version: " + adminConfig.getAppVersion());

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("button_message", "You can check your tasks by pressing this button:");
        context.setVariable("button", "check tasks");
        context.setVariable("tasks_url", "http://localhost/tasks_frontend/");
        context.setVariable("show_button", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("appName", adminConfig.getAppName());
        context.setVariable("appVersion", adminConfig.getAppVersion());
        context.setVariable("goodbye_message", "Cheers!");
        context.setVariable("app_credentials", appCredentials);
        context.setVariable("is_automatic", true);
        context.setVariable("automatic_message", "[This mail has been generated automatically]");

        return templateEngine.process("mail/tasks-number-scheduler-mail", context);
    }
}
