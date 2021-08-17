package letscode.interpres.controller;

import letscode.interpres.domain.User;
import letscode.interpres.repo.MessageRepo;
import letscode.interpres.repo.UserDetailsRepo;
import letscode.interpres.services.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageRepo messageRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    HttpSession session;

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Autowired
    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public String main(Model model, HttpServletRequest request, HttpSession session) {
        HashMap<Object, Object> data = new HashMap<>();

        String userId = "";
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userObject instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) userObject;
            userId = (String) oidcUser.getClaims().get("sub");
        }

        data.put("profile", userDetailsRepo.findById(userId).orElse(null));
        data.put("messages", messageRepo.findAll());

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }

    @GetMapping("/login")
    public RedirectView login() {

        return new RedirectView("/");
    }
}
