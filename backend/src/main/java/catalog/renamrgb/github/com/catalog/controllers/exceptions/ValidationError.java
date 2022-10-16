package catalog.renamrgb.github.com.catalog.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandarError {
    List<FieldMessage> details = new ArrayList<>();

    public List<FieldMessage> getDetails() {
        return details;
    }

    public void addError(String fieldName, String message) {
        details.add(new FieldMessage(fieldName, message));
    }
}
