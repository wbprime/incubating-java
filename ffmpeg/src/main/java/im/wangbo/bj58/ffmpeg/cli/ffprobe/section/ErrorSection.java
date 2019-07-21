package im.wangbo.bj58.ffmpeg.cli.ffprobe.section;

import javax.json.bind.annotation.JsonbProperty;
import java.util.Objects;

/**
 * Sample data:
 * <p>
 * "error": {
 * "code": -1094995529,
 * "string": "Invalid data found when processing input"
 * }
 *
 * @author Elvis Wang
 */
public class ErrorSection {
    @JsonbProperty("code")
    private int code;

    @JsonbProperty("string")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorSection that = (ErrorSection) o;
        return code == that.code &&
            message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    @Override
    public String toString() {
        return "ErrorSection{" +
            "code=" + code +
            ", message='" + message + '\'' +
            '}';
    }
}
