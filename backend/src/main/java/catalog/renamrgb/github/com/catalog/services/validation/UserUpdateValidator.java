package catalog.renamrgb.github.com.catalog.services.validation;

import catalog.renamrgb.github.com.catalog.controllers.exceptions.FieldMessage;
import catalog.renamrgb.github.com.catalog.dto.UserUpdateDTO;
import catalog.renamrgb.github.com.catalog.entities.User;
import catalog.renamrgb.github.com.catalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserUpdateValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = userRepository.findByEmail(dto.getEmail());

        if (Objects.nonNull(user)) {
            list.add(new FieldMessage("email", "email já cadastrado"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
