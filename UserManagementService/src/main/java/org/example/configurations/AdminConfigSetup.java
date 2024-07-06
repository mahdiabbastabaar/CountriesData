package org.example.configurations;

import org.example.dto.auth.RegisterRequestDto;
import org.example.models.Role;
import org.example.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AdminConfigSetup {

    private final AdminConfiguration adminConfiguration;
    private final AuthenticationService authenticationService;

    @EventListener(ApplicationReadyEvent.class)
    public void setupAdminConfigs() throws Exception {
        RegisterRequestDto requestDto =
            new RegisterRequestDto(adminConfiguration.getUsername(), adminConfiguration.getPassword());
        //TODO handle exception later
        try {
            authenticationService.register(requestDto, Role.ADMIN);
        } catch (Exception e) {
            System.out.println("hi");
        }


    }
}
