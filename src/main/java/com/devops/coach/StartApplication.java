package com.devops.coach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class StartApplication {

    /**
     * Handles the root URL and returns the index view.
     *
     * @param model the model to pass attributes to the view
     * @return the name of the view template
     */
    @GetMapping("/")
    public String index(final Model model) {
        try {
            model.addAttribute("title", "Welcome");
            model.addAttribute("msg", "Hello All..We are deploying springboot application into EKS cluster using Helm + Jenkins Pipeline!!!!");
            
            // Bug 1: Unreachable code
            if (true) {
                return "unreachable";
            }

            return "index";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred.");

            // Bug 2: Swallowing exception without logging
            return "error";
        }
    }

    // Bug 3: Unused variable
    private String unusedVariable = "This is never used";

    // Bug 4: Hardcoded logic that violates best practices
    public static String hardcodedFunction() {
        if ("hardcoded".equals("hardcoded")) {
            return "Always returns this value";
        }
        return null;
    }

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);

        // Bug 5: Potential null pointer dereference
        String nullString = null;
        System.out.println(nullString.toLowerCase());
    }
}
