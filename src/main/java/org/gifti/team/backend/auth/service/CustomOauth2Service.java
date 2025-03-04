package org.gifti.team.backend.auth.service;

import lombok.extern.java.Log;
import org.gifti.team.backend.auth.dto.PrincipalDetails;
import org.gifti.team.backend.auth.entity.User;
import org.gifti.team.backend.auth.repository.UserRepository;
import org.gifti.team.backend.auth.type.Provider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CustomOauth2Service extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Autowired
    public CustomOauth2Service(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> oAuthUserAttributes = super.loadUser(userRequest).getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String registrationName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        Optional<User> user = userRepository.findAllByEmail((String) oAuthUserAttributes.get("email"));

        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        }

        User insertUser = userRepository.save(User.builder()
                .email((String) oAuthUserAttributes.get("email"))
                .name((String) oAuthUserAttributes.get("name"))
                .provider(Provider.valueOf(registrationId.toUpperCase()))
                .sub((String) oAuthUserAttributes.get(registrationName))
                .build());

        return new PrincipalDetails(insertUser, oAuthUserAttributes, registrationName);
    }
}
