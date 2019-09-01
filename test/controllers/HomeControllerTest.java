package controllers;

import akka.stream.Materializer;
import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.io.File;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void testSaveValid() {
        Application app = fakeApplication();
        running(app, () -> {
            try {
                Materializer materializer = app.injector().instanceOf(Materializer.class);
                File file = new File("test/resources/file1.txt");
                Http.MultipartFormData.Part<Source<ByteString, ?>> part =
                        new Http.MultipartFormData.FilePart<>(
                                "file", "file1.txt", "text/plain", FileIO.fromFile(file));
                Http.RequestBuilder request = fakeRequest()
                        .method(POST)
                        .bodyMultipart(singletonList(part), play.libs.Files.singletonTemporaryFileCreator(), materializer)
                        .uri("/save");

                Result result = route(app, request);
                assertEquals(OK, result.status());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
    }

    @Test
    public void testSaveInvalid1() {
        Application app = fakeApplication();
        running(app, () -> {
            try {
                Materializer materializer = app.injector().instanceOf(Materializer.class);
                File file = new File("test/resources/file2.png");
                Http.MultipartFormData.Part<Source<ByteString, ?>> part =
                        new Http.MultipartFormData.FilePart<>(
                                "file", "file2.png", "image/png", FileIO.fromFile(file));
                Http.RequestBuilder request = fakeRequest()
                        .method(POST)
                        .bodyMultipart(singletonList(part), play.libs.Files.singletonTemporaryFileCreator(), materializer)
                        .uri("/save");

                Result result = route(app, request);
                assertEquals(UNSUPPORTED_MEDIA_TYPE, result.status());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
    }

    @Test
    public void testSaveInvalid2() {
        Application app = fakeApplication();
        running(app, () -> {
            try {
                Materializer materializer = app.injector().instanceOf(Materializer.class);
                Http.MultipartFormData.Part<Source<ByteString, ?>> part =
                        new Http.MultipartFormData.FilePart<>(
                                "file", "file3.txt", "text/plain", Source.empty());
                Http.RequestBuilder request = fakeRequest()
                        .method(POST)
                        .bodyMultipart(singletonList(part), play.libs.Files.singletonTemporaryFileCreator(), materializer)
                        .uri("/save");

                Result result = route(app, request);
                assertEquals(BAD_REQUEST, result.status());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
    }

    @Test
    public void testSearch1() {
        Application app = fakeApplication();
        running(app, () -> {
            try {
                Http.RequestBuilder request = fakeRequest()
                        .method(GET)
                        .uri("/search/kafka");

                Result result = route(app, request);
                assertEquals(OK, result.status());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
    }

    @Test
    public void testSearch2() {
        Application app = fakeApplication();
        running(app, () -> {
            try {
                Http.RequestBuilder request = fakeRequest()
                        .method(GET)
                        .uri("/search/hsakj");

                Result result = route(app, request);
                assertEquals(NOT_FOUND, result.status());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
    }

}
