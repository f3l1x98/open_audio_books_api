package xyz.f3l1x.open_audio_books_api.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @Getter
    private static final String errorPage = "/error.xhtml";
    @Getter
    private static final String notFoundPage = "/not_found.xhtml";

    @RequestMapping(errorPage)
    public String handleError(HttpServletRequest request, Model model) {
        // Get error attributes from request
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        // Set error attributes for the view
        model.addAttribute("errorCode", status != null ? status.toString() : "N/A");
        model.addAttribute("errorMessage", message != null ? message.toString() : "No specific message available.");
        model.addAttribute("errorException", exception != null ? exception.toString() : "N/A");

        // Forward to the error page
        return errorPage;
    }

}
