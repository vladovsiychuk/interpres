package letscode.interpres.services;

import letscode.interpres.domain.User;
import letscode.interpres.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserDetailsRepo userRepository;

    User MyUser;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        try {
            return processOidcUser(userRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oidcUser.getAttributes());

        Optional<User> userOptional = userRepository.findById(googleUserInfo.getId());
        if (!userOptional.isPresent()) {
            User user = new User();
            user.setId(googleUserInfo.getId());
            user.setEmail(googleUserInfo.getEmail());
            user.setName(googleUserInfo.getName());

            userRepository.save(user);

            this.MyUser = user;
        }else {
            this.MyUser = userOptional.get();
        }

        return oidcUser;
    }

    public User getMyUser() {
        return MyUser;
    }

    public void setMyUser(User myUser) {
        MyUser = myUser;
    }
}
