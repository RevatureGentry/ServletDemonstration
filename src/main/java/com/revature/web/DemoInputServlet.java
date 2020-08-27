package com.revature.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DemoInputServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 8318618866696012632L;

  static final String HTML_COMMAND_INPUT_NAME = "terminalCommand";
  static final String APPLICATION_JSON = "application/json";

  // ObjectMapper is the core object from Jackson that writes JSON <-> POJO
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("Inside DemoInputServlet#doPost " + req.getContentType());

    if (req.getContentType().equals(APPLICATION_JSON)) {
      FormData formData = mapper.readValue(req.getInputStream(), FormData.class);

      if (formData.getTerminalCommand() != null) {
        // Start our process
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", formData.getTerminalCommand());

        // Get the execution directory
        File executionDirectory = Paths.get("/home/william/Desktop").toFile();
        processBuilder.directory(executionDirectory);

        // Start the process
        Process process = processBuilder.start();
        int exitCode;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
          exitCode = process.waitFor();
          String line = bufferedReader.lines().collect(Collectors.joining());
          SimpleResponse simpleResponse = new SimpleResponse(line, exitCode);
          resp.setContentType(APPLICATION_JSON);
          resp.getOutputStream().write(mapper.writeValueAsBytes(simpleResponse));
          return;
        } catch (InterruptedException e) {
          e.printStackTrace();
          exitCode = 1;
        }

      }

      SimpleResponse simpleResponseMessage = new SimpleResponse();
      simpleResponseMessage.setMessage(String.format("Found terminal command '%s'", formData.getTerminalCommand()));

//      Map<String, String> simpleResponse = Collections.singletonMap("message",
//          String.format("Found terminal command '%s'", formData.getTerminalCommand()));

      resp.setContentType(APPLICATION_JSON);
      resp.getOutputStream().write(mapper.writeValueAsBytes(simpleResponseMessage));
      return;
    }

    printSessionCurrentTime(req);

    // Couple of ways to get the content of a request body
    // req.getParameter(String) => this gets you a reference to some form input,
    // what you pass as the arg is the `name` value of your input

    // Maintain session is through a Server-Side Session, Cookies,
    // hidden input form (do not recommend this)

    String terminalCommand = getTerminalCommandFromForm(req);
    System.out.println("Found terminal command: " + terminalCommand);

    // req.getAttribute(String) returns a value attached to the session
    setCurrentTime(req);

    if (terminalCommand != null) {
      resp.getOutputStream().write(terminalCommand.getBytes());
    }
  }

  private void printSessionCurrentTime(HttpServletRequest req) {
    // Get Attribute find an Object attached to the current session
    Object foundInSession = req.getSession().getAttribute("currentTime");
    if (foundInSession == null) {
      System.out.println("No currentTime available in Session");
    } else {
      System.out.println("Current Time is " + (LocalDateTime) foundInSession);
    }
  }

  private void setCurrentTime(HttpServletRequest req) {
    req.getSession().setAttribute("currentTime", LocalDateTime.now());
  }

  private String getTerminalCommandFromForm(HttpServletRequest req) {
    return req.getParameter(HTML_COMMAND_INPUT_NAME);
  }
}
