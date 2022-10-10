package com.peakoapp.core.factory.jwt;

import com.peakoapp.core.exception.InvalidJwtTokenException;
import com.peakoapp.core.model.dto.JwtToken;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * The {@code JwtTokenParser} provides the apis for parsing a user provided JWT token.
 *
 * @version 0.1.0
 */
public interface JwtTokenParser {
    JwtToken parse() throws ExpiredJwtException, InvalidJwtTokenException;
}
