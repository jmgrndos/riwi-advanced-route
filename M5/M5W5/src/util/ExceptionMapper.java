package util;

import exceptions.BadRequestException;
import exceptions.ConflictException;
import exceptions.NotFoundException;
import exceptions.ServiceException;

public class ExceptionMapper {

    public static int getHttpStatusCode(Throwable e) {
        return switch (e) {
            case BadRequestException badRequestException -> 400;
            case NotFoundException notFoundException -> 404;
            case ConflictException conflictException -> 409;
            case ServiceException serviceException -> 500;
            case null, default ->
                // Any other exception
                    500;
        };
    }
}