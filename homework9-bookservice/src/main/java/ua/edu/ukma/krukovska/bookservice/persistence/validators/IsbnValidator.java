package ua.edu.ukma.krukovska.bookservice.persistence.validators;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class IsbnValidator implements ConstraintValidator<IsbnConstraint, String> {

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {

        isbn = isbn.replace("-", "");

        boolean isCorrect = false;
        if (isbn.length() == 10) {
            isCorrect = checkIsbn10(isbn);
        } else if (isbn.length() == 13) {
            isCorrect = checkIsbn13(isbn);
        }

        return isCorrect;
    }

    private boolean checkIsbn10(String isbn) {
        char[] digits = isbn.toCharArray();

        int sum = 0;
        int multiplications = 0;

        for (int i = 0; i < 10; i++) {
            multiplications += digits[i];
            sum += multiplications;
        }
        return (sum % 11) == 0;
    }

    private boolean checkIsbn13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int number = Integer.parseInt(isbn.substring(i, i + 1));
            sum += number * Math.pow(3, i % 2);
        }
        int checkDigit = Integer.parseInt(isbn.substring(12));
        int calculatedCheckDigit = (10 - sum % 10) % 10;

        return checkDigit == calculatedCheckDigit;
    }

}
