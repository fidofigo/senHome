package com.senhome.web.view;

import lombok.Getter;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Getter
public class CompressSupportStringView extends AbstractView {

    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String GZIP = "gzip";
    public static final String CONTENT_TYPE = "application/json;charset=";

    /**
     * jsonp的callback名称
     */
    public static final String ATTR_JSONP_CALLBACK = "JSONP_CALLABCK";

    private static final String DEFAULT_CAHRSET = CharEncoding.UTF_8;

    protected String html;
    protected String encoding = DEFAULT_CAHRSET;
    protected String jsonpFormat = "%s(%s)";

    public CompressSupportStringView(String html) {
        super();
        this.html = html;
    }

    public CompressSupportStringView setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public CompressSupportStringView setHtml(String html) {
        this.html = html;
        return this;
    }

    public CompressSupportStringView setJsonpFormat(String jsonpFormat) {
        this.jsonpFormat = jsonpFormat;
        return this;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        if (html == null || StringUtils.EMPTY.equals(html)) {
            return;
        }
        //
        response.setContentType(CONTENT_TYPE + encoding);
        response.setCharacterEncoding(encoding);
        //

        String jsonpCallbackName = request.getAttribute(ATTR_JSONP_CALLBACK) != null ? String.valueOf(request.getAttribute(ATTR_JSONP_CALLBACK)) : null;
        if (StringUtils.isNotBlank(jsonpCallbackName)) {
            html = String.format(jsonpFormat, jsonpCallbackName, html);
        }
        byte[] buf = html.getBytes(encoding);

        response.setContentType(CONTENT_TYPE);
        response.setContentLength(buf.length);
        response.setCharacterEncoding(encoding);
        response.getWriter().write(html);
    }

}
