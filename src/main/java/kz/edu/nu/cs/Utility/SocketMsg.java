package kz.edu.nu.cs.Utility;

import kz.edu.nu.cs.Model.Message;

import java.util.List;
import java.util.Objects;

public class SocketMsg {
    private String type;
    private List<Message> data;
    private int eventId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocketMsg socketMsg = (SocketMsg) o;
        return Objects.equals(type, socketMsg.type) &&
                Objects.equals(data, socketMsg.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, data);
    }

    @Override
    public String toString() {
        return "SocketMsg{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}

