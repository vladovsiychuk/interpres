package letscode.interpres.controller;

import letscode.interpres.domain.User;
import letscode.interpres.repo.MessageRepo;
import letscode.interpres.services.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageRepo messageRepo;

    @Value("${spring.profiles.active}")
    private String profile;


    @Autowired
    CustomOidcUserService customOidcUserService;

    @Autowired
    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public String main(Model model) {
        HashMap<Object, Object> data = new HashMap<>();

        User user = customOidcUserService.getMyUser();


        data.put("profile", user);
        data.put("messages", messageRepo.findAll());

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }

    @GetMapping("/login")
    public RedirectView login() {

        return new RedirectView("/");
    }

    @GetMapping("/cleanUser")
    public RedirectView logout() {

        customOidcUserService.setMyUser(null);

        return new RedirectView("/");
    }
}
