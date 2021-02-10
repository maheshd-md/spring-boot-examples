package com.filehandler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UploadController {

	@RequestMapping("/home.jsp")
	public String getView(Model model) {
        model.addAttribute("msg", "Hello there, This message has been injected from the controller method");
        return "home";
    }
}
