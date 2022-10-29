package com.peakoapp.core.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peakoapp.core.model.dto.FriendRequestPayload;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * The {@code FriendRequestStatus} class is a subset of {@link FriendRequestPayload} used to update
 * the state of the request.
 *
 * @version 0.1.0
 */
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FriendRequestStatus implements Details<FriendRequestPayload>, Serializable {
    private static final long serialVersionUID = -6424535200549568714L;

    /**
     * The request id.
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    /**
     * The status of the request (true == accepted, false == denied).
     * This field can be directly set by user through the passed JSON value.
     */
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean status;

    public FriendRequestStatus() {
    }

    public FriendRequestStatus(Long id, Boolean status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public FriendRequestPayload as() {
        if (status) {
            return new FriendRequestPayload(id, null, null, true, false, null);
        } else {
            return new FriendRequestPayload(id, null, null, false, true, null);
        }
    }
}
