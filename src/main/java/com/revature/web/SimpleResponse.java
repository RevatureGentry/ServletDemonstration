package com.revature.web;

/**
 * @author William Gentry
 * 
 *         POJO intended to act as a HTTP Response Wrapper for the client
 *         application. Resembles the output of the terminal command, as well as
 *         the exit code
 */
public class SimpleResponse {

  private String message;
  private int exitCode;

  public SimpleResponse() {
  }

  public SimpleResponse(String message, int exitCode) {
    this.message = message;
    this.exitCode = exitCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getExitCode() {
    return exitCode;
  }

  public void setExitCode(int exitCode) {
    this.exitCode = exitCode;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((message == null) ? 0 : message.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SimpleResponse other = (SimpleResponse) obj;
    if (message == null) {
      if (other.message != null)
        return false;
    } else if (!message.equals(other.message))
      return false;
    return true;
  }

}
