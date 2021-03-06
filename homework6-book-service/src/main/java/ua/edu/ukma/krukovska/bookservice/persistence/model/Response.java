package ua.edu.ukma.krukovska.bookservice.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Response<T> {

    private T object;
    private List<String> errors;

    public boolean isOkay() {
        return errors.size() == 0;
    }

}
