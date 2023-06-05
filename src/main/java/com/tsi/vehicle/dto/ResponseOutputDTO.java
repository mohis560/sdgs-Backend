package com.tsi.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResponseOutputDTO {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("successMsg")
    private String successMsg;

    @JsonProperty("successFlag")
    private boolean successFlag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public boolean isSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(boolean successFlag) {
        this.successFlag = successFlag;
    }

    public ResponseOutputDTO(String userId, String successMsg, boolean successFlag) {
        this.userId = userId;
        this.successMsg = successMsg;
        this.successFlag = successFlag;
    }
}
