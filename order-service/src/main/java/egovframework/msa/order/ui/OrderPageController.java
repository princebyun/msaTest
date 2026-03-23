package egovframework.msa.order.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order-ui")
public class OrderPageController {

    @GetMapping
    public String page() {
        return "order-page";
    }
}
