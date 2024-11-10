package xyz.f3l1x.open_audio_books_api.config;

import jakarta.faces.FacesException;
import jakarta.faces.application.ResourceHandler;
import jakarta.faces.application.ResourceHandlerWrapper;
import jakarta.faces.application.ViewResource;
import jakarta.faces.context.FacesContext;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

// https://stackoverflow.com/questions/13292272/obtaining-facelets-templates-files-from-an-external-filesystem-or-database
public class TemplatesResourceHandler extends ResourceHandlerWrapper {

    private static final String TEMPLATE_PATH = "/WEB-INF/templates/";
    private ResourceHandler wrapped;

    public TemplatesResourceHandler(ResourceHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ViewResource createViewResource(FacesContext context, final String resourceName) {
        ViewResource resource = super.createViewResource(context, resourceName);

        if (resource == null && resourceName.startsWith(TEMPLATE_PATH)) {
            resource = new ViewResource() {
                @Override
                public URL getURL() {
                    try {
                        final String projectRootPath = new FileSystemResource("webapp/src/main/resources" + TEMPLATE_PATH).getFile().getAbsolutePath();
                        final File resourceFile = new File(projectRootPath, resourceName.replaceFirst(TEMPLATE_PATH, ""));
                        if (!resourceFile.exists()) {
                            return null;
                        }
                        return resourceFile.toURI().toURL();
                    } catch (MalformedURLException e) {
                        throw new FacesException(e);
                    }
                }
            };
        }

        return resource;
    }

    @Override
    public ResourceHandler getWrapped() {
        return wrapped;
    }
}
