package letscode.interpres.controller;


import letscode.interpres.services.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    CustomOidcUserService customOidcUserService;

    @GetMapping
    public RedirectView test() {

        return new RedirectView("/");
//        return "hi user " + customOidcUserService.getMyUser().getEmail();
    }
}
