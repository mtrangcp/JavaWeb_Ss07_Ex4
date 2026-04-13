package org.example.ptit_cntt2_it210_session07_ex4.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    private final Long MAX_FILE_SIZE = 2*1024*1024L;
    private final Long MAX_REQUEST_SIZE = 2*1024*1024L;
    private final String TMP_LOCATION = "";
    private final Integer FILE_SIZE_THRESHOLD = 0;
    @Override
    protected Class<?> @Nullable [] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?> @Nullable [] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};

    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(TMP_LOCATION,MAX_FILE_SIZE,MAX_REQUEST_SIZE,FILE_SIZE_THRESHOLD));
    }
}

