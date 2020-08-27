package com.revature.web;

/**
 * 
 * @author William Gentry
 * 
 *         Model Object used to accept JSON input from our index.jsp page
 * 
 *         PLEASE NOTE: For Jackson to work properly, the Key of the JS Object
 *         you send must match the name of the Java field
 *
 */
public class FormData {

  private String terminalCommand;

  // For Jackson to work, it needs all getter/setters, and a no-arg constructor
  public FormData() {
  }

  public String getTerminalCommand() {
    return terminalCommand;
  }

  public void setTerminalCommand(String terminalCommand) {
    this.terminalCommand = terminalCommand;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((terminalCommand == null) ? 0 : terminalCommand.hashCode());
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
    FormData other = (FormData) obj;
    if (terminalCommand == null) {
      if (other.terminalCommand != null)
        return false;
    } else if (!terminalCommand.equals(other.terminalCommand))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("FormData [terminalCommand=");
    builder.append(terminalCommand);
    builder.append("]");
    return builder.toString();
  }

}
