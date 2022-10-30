package com.peakoapp.core.exception;

/**
 * The {@code AlreadyFriendException} is thrown when a user attempts to send a friend request to a
 * friend.
 *
 * @version 0.1.0
 */
public class AlreadyFriendException extends RuntimeException {
    private static final long serialVersionUID = 8968246066547105642L;

    public AlreadyFriendException(String msg) {
        super(msg);
    }
}
