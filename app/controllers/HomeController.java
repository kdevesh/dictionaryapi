package controllers;

import businesslayer.BusinessLayer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    final private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result save(Http.Request request) {
        log.info("inside save method of HomeController");
        ObjectNode result = Json.newObject();
        Http.MultipartFormData<File> body = request.body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> file;
        try {
            file = body.getFile("file");
        } catch (Exception e) {
            result.put("status", Http.Status.BAD_REQUEST);
            result.put("message", "file not found");
            log.error(e.getClass().descriptorString());
            return badRequest(result);
        }
        if (file != null) {
            Result check = fileValidation(file);
            if (check.status() != 200) {
                return check;
            }
            File data = file.getFile();
            try {
                boolean res = BusinessLayer.saveWord(data);
                if (res) {
                    result.put("status", Http.Status.OK);
                    result.put("message", "file saved successfully");
                    log.info("file saved successfully");
                    return ok(result);
                } else {
                    result.put("status", Http.Status.INTERNAL_SERVER_ERROR);
                    result.put("mesage", "file not saved");
                    log.error("file not saved");
                    return internalServerError(result);
                }
            } catch (IOException e) {
                result.put("status", Http.Status.INTERNAL_SERVER_ERROR);
                result.put("message", e.getMessage());
                log.error(e.getClass().descriptorString());
                return internalServerError(result);
            }
        } else {
            result.put("status", Http.Status.BAD_REQUEST);
            result.put("message", "file is empty");
            log.error("file is empty");
            return badRequest(result);
        }
    }

    public Result search(String word) {
        log.info("inside search method of HomeController");
        ObjectNode result = Json.newObject();
        boolean output = BusinessLayer.searchWord(word);
        if (output) {
            result.put("status", Http.Status.OK);
            result.put("message", "found");
            log.info("word is present");
            return ok(result);
        } else {
            result.put("status", Http.Status.NOT_FOUND);
            result.put("message", "not found");
            log.error("word not present");
            return notFound(result);
        }
    }

    public Result fileValidation(Http.MultipartFormData.FilePart<File> file) {
        log.info("inside fileValidation method of HomeController");
        final long MAX_FILE_SIZE = 200 * 1024 * 1024;
        Set<String> supportedFormats = new HashSet<>();
        supportedFormats.add("text/plain");
        supportedFormats.add("application/msword");
        supportedFormats.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        ObjectNode result = Json.newObject();
        String contentType = file.getContentType();
        long fileSize = file.getFileSize();
        if (!supportedFormats.contains(contentType)) {
            result.put("status", Http.Status.UNSUPPORTED_MEDIA_TYPE);
            result.put("message", "This api supports only text/doc files");
            log.error("invalid file type");
            return unsupportedMediaType(result);
        } else if (fileSize > MAX_FILE_SIZE) {
            result.put("status", Http.Status.BAD_REQUEST);
            result.put("message", "file size greater than 200 MB");
            log.error("invalid file size");
            return badRequest(result);
        } else
            return ok();
    }
}
