package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.janus.schema.transport.Request;
import im.wangbo.bj58.janus.schema.transport.TransactionId;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class GlobalRequest {
    public abstract Request.Type request();

    public abstract TransactionId transaction();

    public static GlobalRequest create(final Request.Type request, final TransactionId transaction) {
        return new AutoValue_GlobalRequest(request, transaction);
    }
}
