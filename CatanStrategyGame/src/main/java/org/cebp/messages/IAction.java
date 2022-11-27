package org.cebp.messages;

import java.io.IOException;

public interface IAction {
    public ActionResult executeAction() throws IOException;
}
