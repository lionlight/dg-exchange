package ru.alfa.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/doc")
@Api(value = "DocumentationController", description = "Documentation/Документация",tags = {"Documentation"})
public class DocumentationController {

    @ApiOperation(value = "Documentation",notes = "get documentation")
    @GetMapping
    public RedirectView doc(){
        return new RedirectView("/api/swagger-ui/");
    }
}
