package letscode.interpres.controller;


import com.fasterxml.jackson.annotation.JsonView;
import letscode.interpres.domain.Message;
import letscode.interpres.domain.Views;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String test() {
        return "hi";
    }
}
