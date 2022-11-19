package pe.edu.unprg.javaee.cruddemo.utils;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JSONResponse {

    private static final String CONTENT_TYPE = "application/json";
    private static final String CHARACTER_ENCODING = "UTF-8";

    private JSONResponse() {}

    public static void writeFromServlet(HttpServletResponse response, JsonObject json) throws IOException {
        response.setContentType(JSONResponse.CONTENT_TYPE);
        response.setCharacterEncoding(JSONResponse.CHARACTER_ENCODING);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

}
