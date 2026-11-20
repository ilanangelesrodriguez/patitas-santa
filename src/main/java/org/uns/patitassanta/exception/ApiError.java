package org.uns.patitassanta.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(LocalDateTime timestamp, int status, String mensaje,
                       Map<String, String> errores) {}
