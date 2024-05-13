package io.secutity.oauth2jwt.service;


import io.secutity.oauth2jwt.dto.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registritionId =userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        //각각의 리소스 서버가 제공해주는 데이터 형태가 다르기 때문에
        if(registritionId.equals("naver")){

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
            
        } else if (registritionId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
            
        }
        else {
            return null;
        }

        //리소스 서버에서는 같은 유저 이름이 있을 수 있지만 우리 서버에서는 구별해주어야됨
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        UserDTO userDTO = new UserDTO();
        userDTO.setName(oAuth2Response.getName());
        userDTO.setRole("ROLE_USER");
        userDTO.setUsername(username);

        return new CustomOAuth2User(userDTO);

    }
}
