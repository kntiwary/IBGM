package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

/**
 * Created by umakant.angadi on 08-01-2016.
 */
public class CHMResponse {

    private String status;
    private String message;
    private int response_code;
    private int record_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }


    @Override
    public String toString() {
        return "CHMResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", response_code=" + response_code +
                ", record_id=" + record_id +
                '}';
    }
}
